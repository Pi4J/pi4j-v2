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
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.io.gpio.analog.AnalogInputConfig;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInstantiateException;

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

    public static AnalogInput instance(int address) throws ProviderException {
        return instance(AnalogInputConfig.instance(address));
    }
    public static <T extends AnalogInput> T instance(int address, Class<T> clazz) throws ProviderException {
        return instance(AnalogInputConfig.instance(address), clazz);
    }

    public static AnalogInput instance(AnalogInputConfig config) throws ProviderException {
        return instance((AnalogInputProvider)null, config);
    }
    public static <T extends AnalogInput> T instance(AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return instance((AnalogInputProvider)null, config, clazz);
    }

    public static AnalogInput instance(String providerId, int address) throws ProviderException {
        return instance(providerId, AnalogInputConfig.instance(address));
    }
    public static <T extends AnalogInput> T instance(String providerId, int address, Class<T> clazz) throws ProviderException {
        return instance(providerId, AnalogInputConfig.instance(address), clazz);
    }

    public static AnalogInput instance(AnalogInputProvider provider, int address) throws ProviderException {
        return instance(provider, AnalogInputConfig.instance(address));
    }
    public static <T extends AnalogInput> T instance(AnalogInputProvider provider, int address, Class<T> clazz) throws ProviderException {
        return instance(provider, AnalogInputConfig.instance(address), clazz);
    }

    public static AnalogInput instance(String providerId, AnalogInputConfig config) throws ProviderException {
        return instance(providerId, config, AnalogInput.class);
    }
    public static <T extends AnalogInput> T instance(String providerId, AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        // if provided, lookup the specified io provider; else use the default io provider
        if(providerId == null) {
            return (T)instance(config);
        }
        // get the specified analog input provider by ID
        var provider = Pi4J.providers().analogInput().get(providerId);
        return instance(provider, config, clazz);
    }

    public static AnalogInput instance(AnalogInputProvider provider, AnalogInputConfig config) throws ProviderException {
        return instance(provider, config, AnalogInput.class);
    }
    public static <T extends AnalogInput> T instance(AnalogInputProvider provider, AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        try {
            // get default analog input provider if the specified provider is null
            if(provider == null){
                provider = Pi4J.providers().analogInput().getDefault();
            }
            // create a analog input instance using the analog input provider
            return (T)provider.instance(config);
        } catch(ProviderException pe){
            throw pe;
        } catch (Exception e) {
            throw new ProviderInstantiateException(provider, e);
        }
    }
}
