package com.pi4j.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  PlatformBase.java
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

import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.extension.ExtensionBase;
import com.pi4j.io.IOType;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.provider.exception.ProviderTypeException;
import com.pi4j.provider.impl.ProviderProxyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PlatformBase<PLATFORM extends Platform>
        extends ExtensionBase
        implements Platform {

    protected Context context = null;
    protected Map<IOType, Provider> providers = new ConcurrentHashMap<>();
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public PlatformBase(){
        super();
    }

    public PlatformBase(String id){
        super(id);
    }

    public PlatformBase(String id, String name){
        super(id, name);
    }

    public PlatformBase(String id, String name, String description){
        super(id, name, description);
    }

    @Override
    public Map<IOType, Provider> providers() {
        return Collections.unmodifiableMap(this.providers);
    }

    @Override
    public <T extends Provider> T provider(Class<T> providerClass) throws ProviderNotFoundException, ProviderTypeException {

        if(!providerClass.isInterface()){
            logger.warn("Provider type [" + providerClass.getName() + "] requested; this is not an 'Interface'" +
                    " and make not return a valid provider or may not be able to cast to the concrete class.");
        }

        for(Provider p : providers.values()){
            if(providerClass.isAssignableFrom(p.getClass())){
                return (T)p;
            }

            // check for Proxied provider instances, if a Proxy, then also check the underlying handlers source class
            if (Proxy.isProxyClass(p.getClass())) {
                if(Proxy.getInvocationHandler(p).getClass().isAssignableFrom(ProviderProxyHandler.class)){
                    ProviderProxyHandler pp = (ProviderProxyHandler) Proxy.getInvocationHandler(p);
                    if(providerClass.isAssignableFrom(pp.provider().getClass())){
                        return (T) p;
                    }
                }
            }
        }

        if(providerClass.isInterface()){
            throw new ProviderNotFoundException(providerClass);
        } else {
            throw new ProviderTypeException(providerClass);
        }
    }

    @Override
    public <T extends Provider> T provider(IOType ioType) throws ProviderNotFoundException {
        if(providers.containsKey(ioType))
            return (T)providers.get(ioType);
        throw new ProviderNotFoundException(ioType);
    }

    @Override
    public abstract int weight();

    @Override
    public abstract boolean enabled(Context context);

    @Override
    public PLATFORM initialize(Context context) throws InitializeException {
        this.context = context;
        String[] provIds = getProviders();
        for (String provId : provIds) {
            try {
                addProvider(context, provId);
            } catch (ProviderException e) {
                throw new InitializeException(e.getMessage());
            }
        }
        return (PLATFORM) this;
    }

    @Override
    public PLATFORM shutdown(Context context) throws ShutdownException {
        return (PLATFORM)this;
    }

    protected abstract String[] getProviders();

    protected void addProvider(Context context, String providerId) throws ProviderException {
        var provider = context.providers().get(providerId);
        this.providers.put(IOType.getByProviderClass(provider.getClass()), provider);
    }
}
