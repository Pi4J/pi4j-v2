package com.pi4j.provider.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ProviderProxyHandler.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
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

import com.pi4j.io.IOConfig;
import com.pi4j.provider.Provider;
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

public class ProviderProxyHandler implements InvocationHandler {
    private Provider provider = null;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProviderProxyHandler(Provider provider){
        this.provider = provider;
    }

    public Provider provider() {
        return this.provider;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // ignore any method that is not named "create", no need to intercept,
        // just delegate invocation to the actual provider instance
        if (!method.getName().equalsIgnoreCase("create")){
            // delegate method invocation to real provider instance
            return method.invoke(provider, args);
        }

        // we need to intercept the "create(IOConfig config)" method with the single IOConfig type argument
        if(args != null && args.length == 1 && args[0] instanceof IOConfig) {
            IOConfig config = (IOConfig) args[0];
            System.out.println("-->> PROVIDER [" +  provider.id() + "] CREATE IO INSTANCE: " + config.id());

            // DO Pi4J MAGIC HERE

            // delegate method invocation to real provider instance
            return method.invoke(provider, args);
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
            interfaces.addAll(Arrays.asList(provider.getClass().getInterfaces()));

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
            catch (NoSuchMethodException e){
                logger.trace(e.getMessage(), e);
                continue;
            }
            catch (Throwable e){
                logger.trace(e.getMessage(), e);
                continue;
            }
        }

        // unable to resolve this default instance of the "create()" method.  not sure why?  look at trace messages.
        throw new UnsupportedOperationException("Method [" + method.getName() +
                "(" + Arrays.toString(method.getParameterTypes()) + ")] could not be resolved in provider [" +
                provider.id() + "(" + provider.getClass().getName() + ")]; Overridden instances of 'create()' " +
                "are not supported unless defined as default methods in the provider interface.");
    }
}
