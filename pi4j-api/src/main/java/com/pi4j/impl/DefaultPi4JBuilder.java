package com.pi4j.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultPi4JBuilder.java
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

import com.pi4j.Pi4JBuilder;
import com.pi4j.Pi4JConfig;
import com.pi4j.binding.Binding;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultPi4JBuilder implements Pi4JBuilder {

    protected Logger logger = LoggerFactory.getLogger(DefaultPi4JBuilder.class);
    protected Pi4JConfig config = Pi4JConfig.create();

    /**
     * Private Constructor
     */
    private DefaultPi4JBuilder(){
        // forbid object construction
    }

    public static Pi4JBuilder instance(){
        return new DefaultPi4JBuilder();
    }

    @Override
    public Pi4JBuilder add(Binding... binding) {
        this.config.add(binding);
        return this;
    }

    @Override
    public Pi4JBuilder add(Platform... platform) {
        this.config.add(platform);
        return this;
    }

    @Override
    public Pi4JBuilder add(Provider... provider) {
        this.config.add(provider);
        return this;
    }

    @Override
    public Pi4JBuilder addDefaultPlatform(Platform platform) {
        this.config.addDefaultPlatform(platform);
        return this;
    }

    @Override
    public String defaultPlatform() {
        return this.config.defaultPlatform();
    }

    @Override
    public Pi4JBuilder defaultPlatform(String platformId) {
        this.config.defaultPlatform(platformId);
        return this;
    }

    @Override
    public Pi4JBuilder autoDetectPlatforms() {
        this.config.autoDetectPlatforms();
        return this;
    }

    @Override
    public Pi4JBuilder noAutoDetectPlatforms() {
        this.config.noAutoDetectPlatforms();
        return this;
    }

    @Override
    public Pi4JBuilder autoDetectProviders() {
        this.config.autoDetectProviders();
        return this;
    }

    @Override
    public Pi4JBuilder noAutoDetectProviders() {
        this.config.noAutoDetectProviders();
        return this;
    }

    @Override
    public Pi4JBuilder autoDetectBindings() {
        this.config.autoDetectBindings();
        return this;
    }

    @Override
    public Pi4JBuilder noAutoDetectBindings() {
        this.config.noAutoDetectBindings();
        return this;
    }

    @Override
    public Pi4JConfig build() {
        return config;
    }
}
