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

import com.pi4j.binding.Binding;
import com.pi4j.context.Context;
import com.pi4j.context.impl.DefaultContext;
import com.pi4j.exception.Pi4JException;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Pi4J {

    private static Logger logger = LoggerFactory.getLogger(Pi4J.class);

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

        // TODO :: REMOVE ME
        // throw exception if Pi4J has not been initialized
        //if(context != null) throw new AlreadyInitializedException();

        // create new context
        Context context = DefaultContext.instance();

        // initialize context
        context.initialize(autoDetect, platform, provider);

        logger.debug("Pi4J successfully initialized.'");
        return context;
    }
}
