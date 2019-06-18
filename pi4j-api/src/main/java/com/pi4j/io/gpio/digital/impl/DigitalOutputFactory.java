package com.pi4j.io.gpio.digital.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputFactory.java
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
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInstantiateException;

/**
 * DigitalOutput factory - it returns instances of {@link DigitalOutput} interface.
 *
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DigitalOutputFactory {

    // private constructor
    private DigitalOutputFactory() {
        // forbid object construction
    }

    public static DigitalOutput instance(int address) throws ProviderException {
        return instance(DigitalOutputConfig.instance(address));
    }
    public static <T extends DigitalOutput> T instance(int address, Class<T> clazz) throws ProviderException {
        return instance(DigitalOutputConfig.instance(address), clazz);
    }

    public static DigitalOutput instance(int address, DigitalState shutdownState) throws ProviderException {
        return instance(DigitalOutputConfig.instance(address, shutdownState));
    }
    public static <T extends DigitalOutput> T instance(int address, DigitalState shutdownState, Class<T> clazz) throws ProviderException {
        return instance(DigitalOutputConfig.instance(address, shutdownState), clazz);
    }

    public static DigitalOutput instance(DigitalOutputConfig config) throws ProviderException {
        return instance((DigitalOutputProvider)null, config);
    }
    public static <T extends DigitalOutput> T instance(DigitalOutputConfig config, Class<T> clazz) throws ProviderException {
        return instance((DigitalOutputProvider)null, config, clazz);
    }

    public static DigitalOutput instance(String providerId, int address) throws ProviderException {
        return instance(providerId, DigitalOutputConfig.instance(address));
    }
    public static <T extends DigitalOutput> T instance(String providerId, int address, Class<T> clazz) throws ProviderException {
        return instance(providerId, DigitalOutputConfig.instance(address), clazz);
    }

    public static DigitalOutput instance(String providerId, int address, DigitalState shutdownState) throws ProviderException {
        return instance(providerId, DigitalOutputConfig.instance(address, shutdownState));
    }
    public static <T extends DigitalOutput> T instance(String providerId, int address, DigitalState shutdownState, Class<T> clazz) throws ProviderException {
        return instance(providerId, DigitalOutputConfig.instance(address, shutdownState), clazz);
    }


    public static DigitalOutput instance(DigitalOutputProvider provider, int address) throws ProviderException {
        return instance(provider, DigitalOutputConfig.instance(address));
    }
    public static <T extends DigitalOutput> T instance(DigitalOutputProvider provider, int address, Class<T> clazz) throws ProviderException {
        return instance(provider, DigitalOutputConfig.instance(address), clazz);
    }

    public static DigitalOutput instance(DigitalOutputProvider provider, int address, DigitalState shutdownState) throws ProviderException {
        return instance(provider, DigitalOutputConfig.instance(address, shutdownState));
    }
    public static <T extends DigitalOutput> T instance(DigitalOutputProvider provider, int address, DigitalState shutdownState, Class<T> clazz) throws ProviderException {
        return instance(provider, DigitalOutputConfig.instance(address, shutdownState), clazz);
    }


    public static DigitalOutput instance(String providerId, DigitalOutputConfig config) throws ProviderException {
        return instance(providerId, config, DigitalOutput.class);
    }
    public static <T extends DigitalOutput> T instance(String providerId, DigitalOutputConfig config, Class<T> clazz) throws ProviderException {
        // if provided, lookup the specified io provider; else use the default io provider
        if(providerId == null) {
            return (T)instance(config);
        }
        // get the specified digital input provider by ID
        var provider = Pi4J.providers().digitalOutput().get(providerId);
        return instance(provider, config, clazz);
    }

    public static DigitalOutput instance(DigitalOutputProvider provider, DigitalOutputConfig config) throws ProviderException {
        return instance(provider, config, DigitalOutput.class);
    }
    public static <T extends DigitalOutput> T instance(DigitalOutputProvider provider, DigitalOutputConfig config, Class<T> clazz) throws ProviderException {
        try {
            // get default digital output provider if the specified provider is null
            if(provider == null){
                provider = Pi4J.providers().digitalOutput().getDefault();
            }
            // create a digital output instance using the digital input provider
            return (T)provider.instance(config);
        } catch(ProviderException pe){
            throw pe;
        } catch (Exception e) {
            throw new ProviderInstantiateException(provider, e);
        }
    }
}
