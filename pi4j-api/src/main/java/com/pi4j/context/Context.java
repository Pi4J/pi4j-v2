package com.pi4j.context;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Context.java
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

import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.config.Config;
import com.pi4j.config.ConfigBuilder;
import com.pi4j.exception.LifecycleException;
import com.pi4j.internal.IOCreator;
import com.pi4j.internal.ProviderProvider;
import com.pi4j.io.IO;
import com.pi4j.io.IOConfig;
import com.pi4j.io.IOType;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import com.pi4j.provider.exception.ProviderInterfaceException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.registry.Registry;
import com.pi4j.util.PropertiesUtil;
import com.pi4j.util.StringUtil;

import java.util.Map;

/**
 * <p>Context interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Context extends Describable, IOCreator, ProviderProvider {

    /**
     * <p>config.</p>
     *
     * @return a {@link com.pi4j.context.ContextConfig} object.
     */
    ContextConfig config();
    /**
     * <p>properties.</p>
     *
     * @return a {@link com.pi4j.context.ContextProperties} object.
     */
    ContextProperties properties();
    /**
     * <p>providers.</p>
     *
     * @return a {@link com.pi4j.provider.Providers} object.
     */
    Providers providers();
    /**
     * <p>registry.</p>
     *
     * @return a {@link com.pi4j.registry.Registry} object.
     */
    Registry registry();
    /**
     * <p>platforms.</p>
     *
     * @return a {@link com.pi4j.platform.Platforms} object.
     */
    Platforms platforms();

    /**
     * <p>shutdown.</p>
     *
     * @return a {@link com.pi4j.context.Context} object.
     * @throws com.pi4j.exception.LifecycleException if any.
     */
    Context shutdown() throws LifecycleException;
    /**
     * <p>inject.</p>
     *
     * @param objects a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.context.Context} object.
     * @throws com.pi4j.annotation.exception.AnnotationException if any.
     */
    Context inject(Object... objects) throws AnnotationException;


    // ------------------------------------------------------------------------
    // PLATFORM ACCESSOR METHODS
    // ------------------------------------------------------------------------

    /**
     * <p>platform.</p>
     *
     * @param <P> a P object.
     * @return a P object.
     */
    default <P extends Platform> P platform(){
        return platforms().getDefault();
    }


    // ------------------------------------------------------------------------
    // PROVIDER ACCESSOR METHODS
    // ------------------------------------------------------------------------

    /** {@inheritDoc} */
    default <T extends Provider> T provider(String providerId) throws ProviderNotFoundException {
        return (T)providers().get(providerId);
    }

    /** {@inheritDoc} */
    default <T extends Provider> T provider(String providerId, Class<T> providerClass) throws ProviderNotFoundException {
        return (T)providers().get(providerId);
    }

    /** {@inheritDoc} */
    default boolean hasProvider(String providerId) {
        try {
            return providers().exists(providerId);
        }
        catch (Exception e){
            return false;
        }
    }

    /** {@inheritDoc} */
    default <T extends Provider> boolean hasProvider(IOType ioType) {
        return providers().exists(ioType);
    }

    /** {@inheritDoc} */
    default <T extends Provider> boolean hasProvider(Class<T> providerClass) {
        return providers().exists(providerClass);
    }

    /** {@inheritDoc} */
    default <T extends Provider> T provider(Class<T> providerClass) throws ProviderNotFoundException, ProviderInterfaceException {
        // return the default provider for this type from the default platform
        if(platform() != null && platform().hasProvider(providerClass))
            return platform().provider(providerClass);

        // return the default provider for this type (outside of default platform)
        if(providers().exists(providerClass))
            return providers().get(providerClass);

        // provider not found
        throw new ProviderNotFoundException(providerClass);
    }

    /** {@inheritDoc} */
    default <T extends Provider> T provider(IOType ioType) throws ProviderNotFoundException {
        // return the default provider for this type from the default platform
        if(platform() != null && platform().hasProvider(ioType))
            return platform().provider(ioType);

        // return the default provider for this type (outside of default platform)
        if(providers().exists(ioType))
            return providers().get(ioType);

        // provider not found
        throw new ProviderNotFoundException(ioType);
    }


    // ------------------------------------------------------------------------
    // I/O INSTANCE ACCESSOR/CREATOR METHODS
    // ------------------------------------------------------------------------

    @Override
    default <I extends IO>I create(IOConfig config, IOType type) throws Exception{
        Provider provider = null;

        // get explicitly defined provider (if defined)
        String providerId = config.provider();
        if(StringUtil.isNotNullOrEmpty(providerId)) {
            provider = this.providers().get(providerId, type);
            if(provider == null) {
                throw new ProviderNotFoundException(providerId, type);
            }
        }

        // get implicitly defined provider (defined by IO type)
        // (this is the default or platform defined provider for this particular IO type)
        if(provider == null){
            provider = this.provider(type);
            if(provider == null) {
                throw new ProviderNotFoundException(type);
            }
        }

        // create IO instance
        return (I)provider.create(config);
    }

    default <T extends IO>T create(String id) throws Exception {
        Map<String,String> keys = PropertiesUtil.subKeys(this.properties().all(), id);
        Provider provider = null;

        // create by explicit IO provider
        if(keys.containsKey("provider")){
            String providerId = keys.get("provider");
            provider = providers().get(providerId);
            if(provider == null) {
                throw new ProviderNotFoundException(providerId);
            }
        }

        // create by IO TYPE
        // (use platform provider if one if available for this IO type)
        else if(keys.containsKey("type")){
            String type = keys.get("type");
            IOType ioType = IOType.valueOf(type);
            provider = provider(ioType);
            if(provider == null) {
                throw new ProviderNotFoundException(type);
            }
        }

        // create IO instance
        ConfigBuilder builder = provider.type().newConfigBuilder();
        builder.load(keys);
        return (T)provider.create((Config) builder.build());
    }

    default <T extends IO>T create(String id, IOType ioType) throws Exception {
        Map<String,String> keys = PropertiesUtil.subKeys(this.properties().all(), id);
        Provider provider = null;

        // create by explicit IO provider
        if(keys.containsKey("provider")){
            String providerId = keys.get("provider");
            provider = providers().get(providerId);
            if(provider == null) {
                throw new ProviderNotFoundException(providerId);
            }
        }

        // create by IO TYPE
        // (use platform provider if one if available for this IO type)
        provider = provider(ioType);
        if(provider == null) {
            throw new ProviderNotFoundException(ioType);
        }

        // create IO instance
        ConfigBuilder builder = provider.type().newConfigBuilder();
        builder.load(keys);
        return (T)provider.create((Config) builder.build());
    }

    // ------------------------------------------------------------------------
    // DESCRIPTOR
    // ------------------------------------------------------------------------

    /**
     * <p>describe.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create()
                .category("CONTEXT")
                .name("Runtime Context")
                .type(this.getClass());

        descriptor.add(registry().describe());
        descriptor.add(platforms().describe());
        descriptor.add(providers().describe());
        descriptor.add(properties().describe());
        return descriptor;
    }
}
