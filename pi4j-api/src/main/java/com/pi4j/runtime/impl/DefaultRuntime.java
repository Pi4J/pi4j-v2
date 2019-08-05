package com.pi4j.runtime.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultRuntime.java
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
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.registry.impl.DefaultRegistryManager;
import com.pi4j.registry.impl.RegistryManager;
import com.pi4j.runtime.Runtime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRuntime implements Runtime {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;
    private AnnotationEngine annotationEngine = null;
    private RegistryManager registry = null;

    public static Runtime newInstance(Context context) throws Pi4JException {
        return new DefaultRuntime(context);
    }

    // private constructor
    private DefaultRuntime(Context context) throws Pi4JException {

        // set local references
        this.context = context;
        this.annotationEngine = DefaultAnnotationEngine.newInstance(context);
        this.registry = DefaultRegistryManager.newInstance(context);

        logger.debug("Pi4J runtime context successfully created & initialized.'");
    }

    @Override
    public Context context() { return this.context; }

    @Override
    public RegistryManager registry() { return this.registry; }

    @Override
    public void inject(Object... objects) throws AnnotationException {
        annotationEngine.inject(objects);
    }

    @Override
    public void shutdown() throws LifecycleException {
        logger.trace("invoked 'shutdown();'");
        try {
            // remove all I/O instances
            this.registry.shutdown(context);

        } catch (Exception e) {
            logger.error("failed to 'shutdown(); '", e);
            throw new LifecycleException(e);
        }

        logger.debug("Pi4J context/runtime successfully shutdown.'");
    }
}