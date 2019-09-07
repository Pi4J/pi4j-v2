package com.pi4j.platform;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Platform.java
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

import com.pi4j.common.Descriptor;
import com.pi4j.context.Context;
import com.pi4j.extension.Extension;
import com.pi4j.internal.IOCreator;
import com.pi4j.internal.ProviderProvider;
import com.pi4j.io.IO;
import com.pi4j.io.IOConfig;
import com.pi4j.io.IOType;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderInterfaceException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.provider.impl.ProviderProxyHandler;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * <p>Platform interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Platform extends IOCreator, ProviderProvider, Extension<Platform> {

    /**
     * <p>weight.</p>
     *
     * @return a int.
     */
    int weight();

    /**
     *
     * <p>enabled.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @return a boolean.
     */
    boolean enabled(Context context);


    // ------------------------------------------------------------------------
    // PROVIDER ACCESSOR METHODS
    // ------------------------------------------------------------------------

    /**
     * <p>providers.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    Map<IOType, Provider> providers();

    /** {@inheritDoc} */
    @Override
    default <T extends Provider> T provider(Class<T> providerClass) throws ProviderNotFoundException, ProviderInterfaceException {
        for(Provider p : providers().values()){
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
            throw new ProviderInterfaceException(providerClass);
        }
    }

    /** {@inheritDoc} */
    @Override
    default <T extends Provider> T provider(IOType ioType) throws ProviderNotFoundException {
        if(providers().containsKey(ioType))
            return (T)providers().get(ioType);
        throw new ProviderNotFoundException(ioType);
    }


    /** {@inheritDoc} */
    @Override
    default <T extends Provider> T provider(String providerId, Class<T> providerClass) throws ProviderNotFoundException{
        for(Provider provider : providers().values()){
            if(provider.id().equalsIgnoreCase(providerId) && providerClass.isInstance(provider)){
                return (T)provider;
            }
        }
        throw new ProviderNotFoundException(providerId, providerClass);
    }

    /** {@inheritDoc} */
    default <T extends Provider> T provider(String providerId) throws ProviderNotFoundException {
        for(Provider provider : providers().values()){
            if(provider.id().equalsIgnoreCase(providerId)){
                return (T)provider;
            }
        }
        throw new ProviderNotFoundException(providerId);
    }

    /** {@inheritDoc} */
    @Override
    default boolean hasProvider(String providerId) {
        for(Provider provider : providers().values()){
            if(provider.id().equalsIgnoreCase(providerId)){
                return true;
            }
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    default <T extends Provider> boolean hasProvider(Class<T> providerClass) {
        try {
            return provider(providerClass) != null;
        }
        catch (Exception e){
            return false;
        }
    }

    /** {@inheritDoc} */
    @Override
    default <T extends Provider> boolean hasProvider(IOType ioType) {
        try {
            return provider(ioType) != null;
        }
        catch (Exception e){
            return false;
        }
    }

    // ------------------------------------------------------------------------
    // I/O INSTANCE ACCESSOR/CREATOR METHODS
    // ------------------------------------------------------------------------

    @Override
    default <I extends IO>I create(IOConfig config, IOType ioType) throws Exception{

        // get default provider (defined by IO type) for this platform
        // (this is the platform defined provider for this particular IO type)
        if(hasProvider(ioType)){
            Provider provider = this.provider(ioType);
            if(provider == null) {
                throw new ProviderNotFoundException(ioType);
            }
            // create IO instance
            return (I)provider.create(config);
        } else {
            throw new ProviderNotFoundException(ioType);
        }
    }

    /** {@inheritDoc} */
    @Override
    <T extends IO>T create(String id) throws Exception;

    /** {@inheritDoc} */
    @Override
    <T extends IO>T create(String id, IOType ioType) throws Exception;

    // ------------------------------------------------------------------------
    // DESCRIPTOR
    // ------------------------------------------------------------------------

    /** {@inheritDoc} */
    /**
     * <p>describe.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    default Descriptor describe() {
        return Descriptor.create()
                .id(this.id())
                .name(this.name())
                .category("PLATFORM")
                .description(this.description()).type(this.getClass());
    }
}
