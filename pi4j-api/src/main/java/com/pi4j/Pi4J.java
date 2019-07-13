package com.pi4j;

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

import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.exception.DependencyInjectionException;
import com.pi4j.binding.exception.BindingException;
import com.pi4j.common.Descriptor;
import com.pi4j.context.Context;
import com.pi4j.context.impl.DefaultContext;
import com.pi4j.exception.AlreadyInitializedException;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.exception.Pi4JException;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi4J {

    private static Context context = null;
    private static Logger logger = LoggerFactory.getLogger(Pi4J.class);

    public static final Context context() throws NotInitializedException {
        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // return initialized context
        return context;
    }

    public static final Registry registry() throws NotInitializedException {
        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // return registry
        return context.registry();
    }

    public static Providers providers() throws NotInitializedException {
        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // return initialized providers
        return context.providers();
    }

    public static Context initialize() throws Pi4JException {
        return initialize(true);
    }

    public static Context initialize(boolean autoDetect) throws Pi4JException {
        return initialize(autoDetect, (Provider) null);
    }

    public static Context initialize(Provider... provider) throws Pi4JException {
        return initialize(false, provider);
    }

    public static Context initialize(boolean autoDetect, Provider... provider) throws Pi4JException {
        logger.trace("invoked 'initialize()' [auto-detect={}]", autoDetect);

        // throw exception if Pi4J has not been initialized
        if(context != null) throw new AlreadyInitializedException();

        // create context singleton
        if(context == null) {
            context = DefaultContext.singleton();
        }

        // initialize bindings
        context().bindings().initialize(context, true);

        // initialize providers
        providers().initialize(context, autoDetect);
        if(provider != null) {
            logger.trace("adding explicit providers: [count={}]", provider.length);
            providers().add(provider);
        }

        logger.debug("Pi4J successfully initialized.'");
        return context;
    }

    public static Context terminate() throws NotInitializedException,ProviderException, BindingException {
        logger.trace("invoked 'terminate();'");

        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // terminate all providers
        providers().terminate(context);

        // terminate all bindings
        context.bindings().terminate(context);

        // destroy/reset context singleton
        context = null;

        logger.debug("Pi4J successfully terminated.'");
        return context;
    }

    public static Context inject(Object... objects) throws NotInitializedException, AnnotationException, DependencyInjectionException {
        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        context.inject(objects);
        return context;
    }

    public static Descriptor describe() throws NotInitializedException {

        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        return context().describe();
    }
}
