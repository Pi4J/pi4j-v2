package com.pi4j.io.gpio.analog.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogInputFactory.java
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

import com.pi4j.Pi4J;
import com.pi4j.io.gpio.analog.*;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInstantiateException;

import java.util.Properties;

/**
 * AnalogInput factory - it returns instances of {@link AnalogInput} interface.
 *
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class AnalogInputFactory {

    // private constructor
    private AnalogInputFactory() {
        // forbid object construction
    }

    public static boolean exists(String id) throws ProviderException {
        return false;
    }

    public static AnalogInput get(String id) throws ProviderException {
        return null;
    }

    public static AnalogInputBuilder builder() throws ProviderException {
        return new DefaultAnalogInputBuilder();
    }

    public static AnalogInputBuilder builder(Properties properties) throws ProviderException {
        return new DefaultAnalogInputBuilder(properties);
    }

    public static AnalogInput create(AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.create((AnalogInputProvider)null, config);
    }

    public static AnalogInput create(String providerId, AnalogInputConfig config) throws ProviderException {
        // if provided, lookup the specified io provider; else use the default io provider
        if(providerId == null) {
            return create(config);
        }
        else{
            var provider = Pi4J.providers().analogInput().get(providerId);
            return create(provider, config);
        }
    }

    public static AnalogInput create(AnalogInputProvider provider, AnalogInputConfig config) throws ProviderException {
        try {
            // get default provider if provider argument is null
            if(provider == null){
                provider = Pi4J.providers().analogInput().getDefault();
            }
            // create an instance using the io provider
            return provider.instance(config);
        } catch(ProviderException pe){
            throw pe;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ProviderInstantiateException(provider, e);
        }
    }
}
