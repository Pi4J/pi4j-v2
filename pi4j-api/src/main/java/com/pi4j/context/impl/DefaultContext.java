package com.pi4j.context.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultContext.java
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

import com.pi4j.annotation.AnnotationEngine;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.DefaultAnnotationEngine;
import com.pi4j.binding.Bindings;
import com.pi4j.binding.impl.DefaultBindings;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.context.Context;
import com.pi4j.context.ContextConfig;
import com.pi4j.exception.Pi4JException;
import com.pi4j.platform.Platforms;
import com.pi4j.platform.impl.DefaultPlatforms;
import com.pi4j.provider.Providers;
import com.pi4j.provider.impl.DefaultProviders;
import com.pi4j.registry.Registry;
import com.pi4j.registry.impl.DefaultRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultContext implements Context {

    private ContextConfig config = null;
    private Bindings bindings = null;
    private Providers providers = null;
    private Platforms platforms = null;
    private Registry registry = DefaultRegistry.newInstance(this);
    private AnnotationEngine annotationEngine = DefaultAnnotationEngine.newInstance(this);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Context newInstance(ContextConfig config) throws Pi4JException {
        return new DefaultContext(config);
    }

    // private constructor
    private DefaultContext(ContextConfig config) throws Pi4JException {
        logger.trace("new Pi4J runtime context initialized [config={}]", config);

        // validate config object exists
        if(config == null){
            throw new LifecycleException("Unable to create new Pi4J runtime context; missing (ContextConfig) config object.");
        }

        // set context config member reference
        this.config = config;

        // create providers and platforms (manager) objects
        this.bindings = DefaultBindings.newInstance(this);
        this.providers = DefaultProviders.newInstance(this);
        this.platforms = DefaultPlatforms.newInstance(this);

        logger.debug("Pi4J runtime context successfully created & initialized.'");
    }

    @Override
    public ContextConfig config() { return this.config; }

    @Override
    public Providers providers() { return providers; }

    @Override
    public Registry registry() { return this.registry; }

    @Override
    public Platforms platforms() { return this.platforms; }

    @Override
    public Bindings bindings() {
        return bindings;
    }

    @Override
    public Context inject(Object... objects) throws AnnotationException {

        // if Pi4J has not been initialized, then use the 'preinject' static method
        // of the DefaultAnnotationEngine to look for @Initialize annotations to perform
        // initialization
        //DefaultAnnotationEngine.processInitialize(objects);

        // inject remaining (if objects exist)
        annotationEngine.inject(objects);

        return this;
    }

    @Override
    public Context shutdown() throws LifecycleException {
        logger.trace("invoked 'shutdown();'");
        try {
            // shutdown all I/O instances
            registry().shutdown(this);

            // shutdown all providers
            providers().shutdown(this);

            // shutdown platforms
            platforms().shutdown(this);

            // shutdown all bindings
            bindings().shutdown(this);
        } catch (Exception e) {
            logger.error("failed to 'shutdown(); '", e);
            throw new LifecycleException(e);
        }

        logger.debug("Pi4J context/runtime successfully shutdown.'");
        return this;
    }
}
