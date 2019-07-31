package com.pi4j.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Pi4J.java
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

import com.pi4j.Pi4JConfig;
import com.pi4j.binding.Binding;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DefaultPi4JConfig implements Pi4JConfig {

    protected Logger logger = LoggerFactory.getLogger(DefaultPi4JConfig.class);

    // auto detection flags
    protected boolean autoDetectBindings = true;
    protected boolean autoDetectPlatforms = true;
    protected boolean autoDetectProviders = true;

    // default platform identifier
    protected String defaultPlatformId = null;

    // extensibility modules
    protected Collection<Binding> bindings = Collections.synchronizedList(new ArrayList<>());
    protected Collection<Platform> platforms = Collections.synchronizedList(new ArrayList<>());
    protected Collection<Provider> providers = Collections.synchronizedList(new ArrayList<>());

    /**
     * Default Constructor
     */
    public DefaultPi4JConfig(){
    }

    @Override
    public Pi4JConfig add(Binding... binding) {
        if(binding != null && binding.length > 0)
            this.bindings.addAll(Arrays.asList(binding));
        return this;
    }

    @Override
    public Pi4JConfig add(Platform... platform) {
        if(platform != null && platform.length > 0)
            this.platforms.addAll(Arrays.asList(platform));
        return this;
    }

    @Override
    public Pi4JConfig add(Provider... provider) {
        if(provider != null && provider.length > 0)
            this.providers.addAll(Arrays.asList(provider));
        return this;
    }

    @Override
    public Collection<Binding> bindings() {
        return Collections.unmodifiableCollection(this.bindings);
    }

    @Override
    public Collection<Platform> platforms() {
        return Collections.unmodifiableCollection(this.platforms);
    }

    @Override
    public Collection<Provider> providers() {
        return Collections.unmodifiableCollection(this.providers);
    }

    @Override
    public Pi4JConfig addDefaultPlatform(Platform platform) {
        if(platform != null) {
            this.platforms.add(platform);
            this.setDefaultPlatform(platform);
        }
        return this;
    }

    @Override
    public Pi4JConfig defaultPlatform(String platformId) {
        this.defaultPlatformId = platformId;
        return this;
    }

    @Override
    public String defaultPlatform() {
        return this.defaultPlatformId;
    }

    @Override
    public Pi4JConfig autoDetectPlatforms() {
        this.autoDetectPlatforms = true;
        return this;
    }

    @Override
    public Pi4JConfig noAutoDetectPlatforms() {
        this.autoDetectPlatforms = false;
        return this;
    }

    @Override
    public boolean getAutoDetectPlatforms() {
        return this.autoDetectPlatforms;
    }

    @Override
    public Pi4JConfig autoDetectProviders() {
        this.autoDetectProviders = true;
        return this;
    }

    @Override
    public Pi4JConfig noAutoDetectProviders() {
        this.autoDetectProviders = false;
        return this;
    }

    @Override
    public boolean getAutoDetectProviders() {
        return this.autoDetectProviders;
    }

    @Override
    public Pi4JConfig autoDetectBindings() {
        this.autoDetectBindings = true;
        return this;
    }

    @Override
    public Pi4JConfig noAutoDetectBindings() {
        this.autoDetectBindings = false;
        return this;
    }

    @Override
    public boolean getAutoDetectBindings() {
        return this.autoDetectBindings;
    }
}
