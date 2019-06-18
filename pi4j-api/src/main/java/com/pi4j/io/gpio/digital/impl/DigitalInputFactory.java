package com.pi4j.io.gpio.digital.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalInputFactory.java
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
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalInputProvider;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInstantiateException;

/**
 * DigitalInput factory - it returns instances of {@link DigitalInput} interface.
 *
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DigitalInputFactory {

    // private constructor
    private DigitalInputFactory() {
        // forbid object construction
    }

    public static DigitalInput instance(int address) throws ProviderException {
        return instance(DigitalInputConfig.instance(address));
    }
    public static <T extends DigitalInput> T instance(int address, Class<T> clazz) throws ProviderException {
        return instance(DigitalInputConfig.instance(address), clazz);
    }

    public static DigitalInput instance(int address, PullResistance pull) throws ProviderException {
        return instance(DigitalInputConfig.instance(address, pull));
    }
    public static <T extends DigitalInput> T instance(int address, PullResistance pull, Class<T> clazz) throws ProviderException {
        return instance(DigitalInputConfig.instance(address, pull), clazz);
    }

    public static DigitalInput instance(DigitalInputConfig config) throws ProviderException {
        return instance((DigitalInputProvider)null, config);
    }
    public static <T extends DigitalInput> T instance(DigitalInputConfig config, Class<T> clazz) throws ProviderException {
        return instance((DigitalInputProvider)null, config, clazz);
    }

    public static DigitalInput instance(String providerId, int address) throws ProviderException {
        return instance(providerId, DigitalInputConfig.instance(address));
    }
    public static <T extends DigitalInput> T instance(String providerId, int address, Class<T> clazz) throws ProviderException {
        return instance(providerId, DigitalInputConfig.instance(address), clazz);
    }

    public static DigitalInput instance(String providerId, int address, PullResistance pull) throws ProviderException {
        return instance(providerId, DigitalInputConfig.instance(address, pull));
    }
    public static <T extends DigitalInput> T instance(String providerId, int address, PullResistance pull, Class<T> clazz) throws ProviderException {
        return instance(providerId, DigitalInputConfig.instance(address, pull), clazz);
    }


    public static DigitalInput instance(DigitalInputProvider provider, int address) throws ProviderException {
        return instance(provider, DigitalInputConfig.instance(address));
    }
    public static <T extends DigitalInput> T instance(DigitalInputProvider provider, int address, Class<T> clazz) throws ProviderException {
        return instance(provider, DigitalInputConfig.instance(address), clazz);
    }

    public static DigitalInput instance(DigitalInputProvider provider, int address, PullResistance pull) throws ProviderException {
        return instance(provider, DigitalInputConfig.instance(address, pull));
    }
    public static <T extends DigitalInput> T instance(DigitalInputProvider provider, int address, PullResistance pull, Class<T> clazz) throws ProviderException {
        return instance(provider, DigitalInputConfig.instance(address, pull), clazz);
    }


    public static DigitalInput instance(String providerId, DigitalInputConfig config) throws ProviderException {
        return instance(providerId, config, DigitalInput.class);
    }
    public static <T extends DigitalInput> T instance(String providerId, DigitalInputConfig config, Class<T> clazz) throws ProviderException {
        // if provided, lookup the specified io provider; else use the default io provider
        if(providerId == null) {
            return (T)instance(config);
        }
        // get the specified digital input provider by ID
        var provider = Pi4J.providers().digitalInput().get(providerId);
        return instance(provider, config, clazz);
    }

    public static DigitalInput instance(DigitalInputProvider provider, DigitalInputConfig config) throws ProviderException {
        return instance(provider, config, DigitalInput.class);
    }
    public static <T extends DigitalInput> T instance(DigitalInputProvider provider, DigitalInputConfig config, Class<T> clazz) throws ProviderException {
        try {
            // get default digital input provider if the specified provider is null
            if(provider == null){
                provider = Pi4J.providers().digitalInput().getDefault();
            }
            // create a digital input instance using the digital input provider
            return (T)provider.instance(config);
        } catch(ProviderException pe){
            throw pe;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ProviderInstantiateException(provider, e);
        }
    }

}
