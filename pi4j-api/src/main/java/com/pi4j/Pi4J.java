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

import com.pi4j.context.Context;
import com.pi4j.context.ContextBuilder;
import com.pi4j.exception.Pi4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi4J {
    private static Logger logger = LoggerFactory.getLogger(Pi4J.class);

    // private constructor
    private Pi4J() {
        // forbid object construction
    }

//    public static Context initialize() throws Pi4JException {
//        return initialize(ContextBuilder.create());
//    }
//
//    public static Context initialize(boolean autoDetect) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                setAutoDetect(autoDetect));
//    }
//
//    public static Context initialize(Provider... provider) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                noAutoDetectProviders().
//                add(provider));
//    }
//
//    public static Context initialize(Binding ... binding) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                noAutoDetectBindings().
//                add(binding));
//    }
//
//    public static Context initialize(boolean autoDetect, Binding... binding) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                setAutoDetect(autoDetect).
//                add(binding));
//    }
//
//    public static Context initialize(boolean autoDetect, Platform... platform) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                setAutoDetect(autoDetect).
//                add(platform));
//    }
//
//    public static Context initialize(boolean autoDetect, Provider... provider) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                setAutoDetect(autoDetect).
//                add(provider));
//    }
//
//    public static Context initialize(Platform platform, Provider... provider) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                noAutoDetectPlatforms().
//                noAutoDetectProviders().
//                addDefaultPlatform(platform).add(provider));
//    }
//
//    public static Context initialize(boolean autoDetect, Platform platform, Provider... provider) throws Pi4JException {
//        return initialize(ContextBuilder.create().
//                setAutoDetect(autoDetect).
//                addDefaultPlatform(platform).
//                add(provider));
//    }
//
//    public static Context initialize(ContextBuilder builder) throws Pi4JException {
//        return builder.build();
//    }
//
//    public static Context initialize(ContextConfig config) throws Pi4JException {
//        logger.trace("invoked 'initialize()'");
//
//        System.out.println(config.getProviders());
//
//        // create new context
//        Context context = DefaultContext.instance();
//
//        // initialize new Pi4J runtime context with a Pi4J configuration object
//        context.initialize(config);
//
//        logger.debug("Pi4J successfully initialized.'");
//        return context;
//    }

    public static ContextBuilder newContext(){
        return ContextBuilder.create();
    }

    public static Context newDefaultContext() throws Pi4JException {
        return newContext().build();
    }

    public static Context newEmptyContext() throws Pi4JException {
        return newContext().noAutoDetect().build();
    }
}
