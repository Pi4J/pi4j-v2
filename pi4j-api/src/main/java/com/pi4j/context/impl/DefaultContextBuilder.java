package com.pi4j.context.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultContextBuilder.java
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
import com.pi4j.context.ContextBuilder;
import com.pi4j.context.ContextConfig;
import com.pi4j.exception.Pi4JException;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DefaultContextBuilder implements ContextBuilder {

    protected Logger logger = LoggerFactory.getLogger(DefaultContextBuilder.class);

    // auto detection flags
    protected boolean autoDetectPlatforms = false;
    protected boolean autoDetectProviders = false;

    // default platform identifier
    protected String defaultPlatformId = null;

    // extensibility modules
    protected Collection<Platform> platforms = Collections.synchronizedList(new ArrayList<>());
    protected Collection<Provider> providers = Collections.synchronizedList(new ArrayList<>());

    /**
     * Private Constructor
     */
    private DefaultContextBuilder(){
        // forbid object construction
    }

    public static ContextBuilder newInstance(){
        return new DefaultContextBuilder();
    }

    @Override
    public ContextBuilder add(Platform... platform) {
        if(platform != null && platform.length > 0)
            this.platforms.addAll(Arrays.asList(platform));
        return this;
    }

    @Override
    public ContextBuilder add(Provider... provider) {
        if(provider != null && provider.length > 0)
            this.providers.addAll(Arrays.asList(provider));
        return this;
    }

    @Override
    public String defaultPlatform() {
        return this.defaultPlatformId;
    }

    @Override
    public ContextBuilder defaultPlatform(String platformId) {
        this.defaultPlatformId = platformId;
        return this;
    }

    @Override
    public ContextBuilder autoDetectPlatforms() {
        this.autoDetectPlatforms = true;
        return this;
    }

    @Override
    public ContextBuilder noAutoDetectPlatforms() {
        this.autoDetectPlatforms = false;
        return this;
    }

    @Override
    public ContextBuilder autoDetectProviders() {
        this.autoDetectProviders = true;
        return this;
    }

    @Override
    public ContextBuilder noAutoDetectProviders() {
        this.autoDetectProviders = false;
        return this;
    }

    @Override
    public ContextConfig toConfig() {
        // set instance reference
        var builder = this;

        // create a new context configuration object
        return new ContextConfig() {
            @Override
            public Collection<Platform> platforms() {
                return Collections.unmodifiableCollection(builder.platforms);
            }

            @Override
            public Collection<Provider> providers() {
                return Collections.unmodifiableCollection(builder.providers);
            }

            @Override
            public String defaultPlatform() {
                return builder.defaultPlatformId;
            }

            @Override
            public boolean autoDetectPlatforms() {
                return builder.autoDetectPlatforms;
            }

            @Override
            public boolean autoDetectProviders() {
                return builder.autoDetectProviders;
            }
        };
    }

    @Override
    public Context build() throws Pi4JException {
        logger.trace("invoked 'build()'");

        // create new context
        Context context = DefaultContext.newInstance(this.toConfig());

        // return the newly created context
        logger.debug("Pi4J successfully created and initialized a new runtime 'Context'.'");
        return context;
    }
}
