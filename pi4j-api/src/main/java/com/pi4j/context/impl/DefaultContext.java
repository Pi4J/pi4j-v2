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

import com.pi4j.Pi4JConfig;
import com.pi4j.annotation.AnnotationEngine;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.DefaultAnnotationEngine;
import com.pi4j.binding.Binding;
import com.pi4j.binding.Bindings;
import com.pi4j.binding.impl.DefaultBindings;
import com.pi4j.common.Lifecycle;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.platform.impl.DefaultPlatforms;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import com.pi4j.provider.impl.DefaultProviders;
import com.pi4j.registry.Registry;
import com.pi4j.registry.impl.DefaultRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class DefaultContext implements Context {

    private Bindings bindings = DefaultBindings.singleton(this);
    private Providers providers = DefaultProviders.singleton(this);
    private Registry registry = DefaultRegistry.singleton(this);
    private Platforms platforms = DefaultPlatforms.singleton(this);
    private AnnotationEngine annotationEngine = DefaultAnnotationEngine.singleton(this);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // TODO :: REMOVE ME
//    private static Context singleton = null;
//    public static Context singleton(){
//        if(singleton == null){
//            singleton = new DefaultContext();
//        }
//        return singleton;
//    }

    public static Context instance(){
        return new DefaultContext();
    }

    // private constructor
    private DefaultContext() {
        // forbid object construction
    }

    @Override
    public Providers providers() {
        return providers;
    }

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

    @Override
    public Context initialize(Pi4JConfig config) throws LifecycleException {

        logger.trace("invoked 'initialize()' [config={}]", config);

        if(config == null){
            throw new LifecycleException("Unable to 'initialize()' Pi4J Context; missing (Pi4JConfig) config object.");
        }

        // initialize bindings
        try {
            // initialize providers, then add the provided I/O providers to the managed collection
            bindings.initialize(this, config.getAutoDetectBindings());
            Collection<Binding> additionalBindings = config.getBindings();
            if(additionalBindings != null && !additionalBindings.isEmpty()) {
                logger.trace("adding explicit bindings: [count={}]", additionalBindings.size());
                //providers().add(additionalBindings);
                // TODO :: HANDLE EXPLICIT BINDINGS
            }

            // initialize providers, then add the provided I/O providers to the managed collection
            providers.initialize(this, config.getAutoDetectProviders());
            Collection<Provider> additionalProviders = config.getProviders();
            if(additionalProviders != null && !additionalProviders.isEmpty()) {
                logger.trace("adding explicit providers: [count={}]", additionalProviders.size());
                providers().add(additionalProviders);
            }

            // initialize platforms, then add the provided I/O platforms to the managed collection
            platforms.initialize(this, config.getAutoDetectProviders());
            Collection<Platform> additionalPlatforms = config.getPlatforms();
            if(additionalPlatforms != null && !additionalPlatforms.isEmpty()) {
                logger.trace("adding explicit platforms: [count={}]", additionalPlatforms.size());
                platforms().add(additionalPlatforms);
            }

            // set default platform
            if(config.hasDefaultPlatform())
                this.platforms().setDefault(config.getDefaultPlatform());

        } catch (Pi4JException e) {
            logger.error(e.getMessage(), e);
            throw new LifecycleException(e);
        }


        logger.debug("Pi4J successfully initialized.'");

        return this;
    }
}
