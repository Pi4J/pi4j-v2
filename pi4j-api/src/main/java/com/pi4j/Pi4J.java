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

import com.pi4j.binding.exception.BindingException;
import com.pi4j.context.Context;
import com.pi4j.context.impl.DefaultContext;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi4J {

    private static final Context context = DefaultContext.singleton();

    private static Logger logger = LoggerFactory.getLogger(Pi4J.class);

    public static final Context context(){
        return context;
    }

    public static Providers providers(){
        return context.providers();
    }

    public static Context initialize() throws BindingException {
        return initialize(true);
    }

    public static Context initialize(boolean autoDetect) throws BindingException {
        return initialize(autoDetect, (Provider) null);
    }

    public static Context initialize(Provider... provider) throws BindingException {
        return initialize(false, provider);
    }

    public static Context initialize(boolean autoDetect, Provider... provider) throws BindingException {
        logger.trace("invoked 'initialize()' [auto-detect={}]", autoDetect);

        context().bindings().initialize(context, true);

        providers().initialize(context, autoDetect);
        if(provider != null) {
            logger.trace("adding explicit providers: [count={}]", provider.length);
            providers().add(provider);
        }

        logger.debug("Pi4J successfully initialized.'");
        return context;
    }

    public static Context terminate() throws BindingException {
        logger.trace("invoked 'terminate();'");

        providers().terminate(context);

        context.bindings().terminate(context);

        logger.debug("Pi4J successfully terminated.'");
        return context;
    }
}
