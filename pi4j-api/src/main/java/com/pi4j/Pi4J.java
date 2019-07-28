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
import com.pi4j.annotation.impl.DefaultAnnotationEngine;
import com.pi4j.binding.Binding;
import com.pi4j.common.Descriptor;
import com.pi4j.context.Context;
import com.pi4j.context.impl.DefaultContext;
import com.pi4j.exception.AlreadyInitializedException;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.exception.Pi4JException;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.platform.exception.PlatformException;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import com.pi4j.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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

    public static Platforms platforms() throws NotInitializedException, PlatformException {
        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // return platforms manager
        return context.platforms();
    }

    public static Platform platform() throws NotInitializedException, PlatformException {
        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // return initialized platform
        return context.platform();
    }

    public static Context initialize() throws Pi4JException {
        return initialize(true);
    }

    public static Context initialize(boolean autoDetect) throws Pi4JException {
        return initialize(autoDetect, Collections.emptyList(), Collections.emptyList());
    }

    public static Context initialize(Provider... provider) throws Pi4JException {
        return initialize(false, null,
                (provider == null || provider.length ==0) ? Collections.emptyList() : Arrays.asList(provider));
    }

    public static Context initialize(Platform... platform) throws Pi4JException {
        return initialize(false,
                (platform == null || platform.length == 0) ?
                        Collections.emptyList() : Arrays.asList(platform), Collections.emptyList());
    }

    public static Context initialize(Binding ... binding) throws Pi4JException {
        return initialize(false, binding);
    }

    public static Context initialize(Collection<Binding> binding) throws Pi4JException {
        return initialize(false, binding);
    }

    public static Context initialize(boolean autoDetect, Binding... binding) throws Pi4JException {
        return initialize(autoDetect,
                (binding == null || binding.length == 0) ? Collections.emptyList() : Arrays.asList(binding));
    }

    public static Context initialize(boolean autoDetect, Collection<Binding> binding) throws Pi4JException {
        List<Platform> platforms = new ArrayList<>();
        List<Provider> providers = new ArrayList<>();

        // iterate and filter for the 'Platform' bindings and add each platform binding to collection
        binding.stream().filter(Platform.class::isInstance).forEach(p -> {
            platforms.add((Platform) p);
        });

        // iterate and filter for the 'Provider' bindings and add each provider binding to collection
        binding.stream().filter(Provider.class::isInstance).forEach(p -> {
            providers.add((Provider) p);
        });

        return initialize(autoDetect, platforms, providers);
    }

    public static Context initialize(boolean autoDetect, Platform platform, Provider... provider) throws Pi4JException {
        return initialize(autoDetect,
                (platform == null) ? Collections.emptyList() : Collections.singletonList(platform),
                (provider == null || provider.length == 0) ? Collections.emptyList() : Arrays.asList(provider));
    }

    public static Context initialize(Platform platform, Provider... provider) throws Pi4JException {
        return initialize(false, platform, provider);
    }

    public static Context initialize(Collection<Platform> platform, Collection<Provider> provider) throws Pi4JException {
        return initialize(false, platform, provider);
    }

    public static Context initialize(boolean autoDetect, Collection<Platform> platform, Collection<Provider> provider) throws Pi4JException {
        logger.trace("invoked 'initialize()' [auto-detect={}]", autoDetect);

        // throw exception if Pi4J has not been initialized
        if(context != null) throw new AlreadyInitializedException();

        // create context singleton
        context = DefaultContext.singleton();

        // initialize bindings
        context().bindings().initialize(context, true);

        // initialize providers, then add the provided I/O providers to the managed collection
        context.providers().initialize(context, autoDetect);
        if(provider != null && !provider.isEmpty()) {
            logger.trace("adding explicit providers: [count={}]", provider.size());
            providers().add(provider);
        }

        // initialize platforms, then add the provided I/O platforms to the managed collection
        context.platforms().initialize(context, autoDetect);
        if(platform != null && !platform.isEmpty()) {
            logger.trace("adding explicit platforms: [count={}]", platform.size());
            platforms().add(platform);
        }

        logger.debug("Pi4J successfully initialized.'");
        return context;
    }

    public static Context terminate() throws Pi4JException {
        logger.trace("invoked 'terminate();'");

        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // terminate all I/O instances
        registry().terminate(context);

        // terminate all providers
        context.providers().terminate(context);

        // terminate platforms
        context.platforms().terminate(context);

        // terminate all bindings
        context.bindings().terminate(context);

        // destroy/reset context singleton
        context = null;

        logger.debug("Pi4J successfully terminated.'");
        return context;
    }

    public static Context inject(Object... objects) throws NotInitializedException, AnnotationException {

        // if Pi4J has not been initialized, then use the 'preinject' static method
        // of the DefaultAnnotationEngine to look for @Initialize annotations to perform
        // initialization
        if(context == null && objects != null && objects.length > 0) {
            DefaultAnnotationEngine.processInitialize(objects);
        }

        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        // inject remaining (if objects exist)
        if(objects != null && objects.length > 0) {
            context.inject(objects);
        }
        return context;
    }

    public static Descriptor describe() throws NotInitializedException {

        // throw exception if Pi4J has not been initialized
        if(context == null) throw new NotInitializedException();

        return context().describe();
    }
}
