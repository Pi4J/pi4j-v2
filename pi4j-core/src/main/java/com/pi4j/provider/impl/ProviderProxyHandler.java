package com.pi4j.provider.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  ProviderProxyHandler.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.io.IO;
import com.pi4j.io.IOConfig;
import com.pi4j.io.exception.IOAlreadyExistsException;
import com.pi4j.provider.Provider;
import com.pi4j.runtime.Runtime;
import com.pi4j.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>ProviderProxyHandler class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class ProviderProxyHandler implements InvocationHandler {
    private Runtime runtime = null;
    private Provider provider = null;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>Constructor for ProviderProxyHandler.</p>
     *
     * @param runtime a {@link com.pi4j.runtime.Runtime} object.
     * @param provider a {@link com.pi4j.provider.Provider} object.
     */
    public ProviderProxyHandler(Runtime runtime, Provider provider){
        this.runtime = runtime;
        this.provider = provider;
    }

    /**
     * <p>provider.</p>
     *
     * @return a {@link com.pi4j.provider.Provider} object.
     */
    public Provider provider() {
        return this.provider;
    }

    /** {@inheritDoc} */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // ignore any method that is not named "create", no need to intercept,
        // just delegate invocation to the actual provider instance
        if (!method.getName().equalsIgnoreCase("create")){
            // delegate method invocation to real provider instance
            return method.invoke(provider, args);
        }

        logger.trace("provider [{}({})] invoked '{}({})'", provider.getId(), provider.getClass().getName(), method.getName(), Arrays.toString(method.getParameterTypes()));

        // we need to intercept the "create(IOConfig config)" method with the single IOConfig type argument
        if(args != null && args.length == 1 && args[0] instanceof IOConfig) {
            IOConfig ioConfig = (IOConfig) args[0];
            //logger.info("-->> PROVIDER [" +  provider.id() + "] CREATE IO INSTANCE: " + ioConfig.id());

            // check to see if this IO instance is already registered in the IO Registry
            if(runtime.registry().exists(ioConfig.id()))
                throw new IOAlreadyExistsException(ioConfig.id());

            // delegate method invocation to real provider instance to create real IO instance
            IO instance = (IO)method.invoke(provider, args);

            // initialize the IO instance
            instance.initialize(runtime.context());

            // now that the instance has been created, make sure to add it ot the IO Registry
            runtime.registry().add(instance);

            // return newly created instance to the method caller
            return instance;
        }
        // handle overloaded default methods defined by the interface
        if (method.isDefault()) {
            // get the primary interface class for this provider
            Class providerInterface = provider.getType().getProviderClass();

//            // lookup and invoke the default method for this provider's primary IO provider interface
//            return MethodHandles.lookup()
//                    .findSpecial(
//                            provider.getClass().getInterfaces()[0],
//                            "create",
//                            MethodType.methodType(
//                                    method.getReturnType(),
//                                    method.getParameterTypes()),
//                            provider.getClass().getInterfaces()[0])
//                    .bindTo(proxy)
//                    .invokeWithArguments(args);
//

            // create a set of interfaces to use to attempt method invocation
            Set<Class> interfaces = new HashSet<>();
            interfaces.add(provider.getType().getProviderClass());
            if(provider.getClass().isInterface()) interfaces.add(provider.getClass());
            interfaces.addAll(ReflectionUtil.getAllInterfaces(provider.getClass()));
            //interfaces.addAll(Arrays.asList(provider.getClass().getInterfaces()));

            // invoke the default method against the set of defined interfaces
            return invokeDefaultMethod(interfaces, proxy, method, args);

        }

        // we WILL NOT support overridden "create(...)" methods defined in concrete classes
        throw new UnsupportedOperationException("Method [" + method.getName() +
                "(" + Arrays.toString(method.getParameterTypes()) + ")] could not be resolved in provider [" +
                provider.id() + "(" + provider.getClass().getName() + ")]; Overridden instances of 'create()' " +
                "are not supported unless defined as default methods in the provider interface.");
    }

    private Object invokeDefaultMethod(Collection<Class> interfaces, Object proxy, Method method, Object[] args) throws Throwable {

        // iterate over the defined interfaces looking for a valid invocation target
        for(Class ifc : interfaces){
            try {
                // lookup and invoke the default method for this provider's primary IO provider interface
                return MethodHandles.lookup()
                        .findSpecial(
                                ifc,
                                method.getName(),
                                MethodType.methodType(
                                        method.getReturnType(),
                                        method.getParameterTypes()),
                                ifc)
                        .bindTo(proxy)
                        .invokeWithArguments(args);
            }
            catch (Throwable e){
                logger.trace(e.getMessage(), e);
            }
        }

        // unable to resolve this default instance of the "create()" method.  not sure why?  look at trace messages.
        throw new UnsupportedOperationException("Method [" + method.getName() +
                "(" + Arrays.toString(method.getParameterTypes()) + ")] could not be resolved in provider [" +
                provider.id() + "(" + provider.getClass().getName() + ")]; Overridden instances of 'create()' " +
                "are not supported unless defined as default methods in the provider interface.");
    }
}
