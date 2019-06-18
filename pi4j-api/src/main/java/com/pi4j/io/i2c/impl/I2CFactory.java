package com.pi4j.io.i2c.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  I2CFactory.java
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
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInstantiateException;

/**
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www .savagehomeautomation.com</a>)
 */
public class I2CFactory {

    // private constructor
    private I2CFactory() {
        // forbid object construction
    }

    public static I2C instance(I2CConfig config) throws ProviderException {
        // get default I2C io
        var provider = Pi4J.providers().i2c().getDefault();

        // get I2C instance using default io
        return instance(provider, config);
    }

    public static I2C instance(String device, int address) throws ProviderException {
        return instance(new I2CConfig(device, address));
    }

    public static I2C instance(String providerId, String device, int address) throws ProviderException {
        return instance(providerId, new I2CConfig(device, address));
    }

    public static I2C instance(String providerId, I2CConfig config) throws ProviderException {
        // if provided, lookup the specified io; else use the default io
        if(providerId == null) {
            return instance(config);
        }
        else{
            var provider = Pi4J.providers().i2c().get(providerId);
            return instance(provider, config);
        }
    }

    public static I2C instance(I2CProvider provider, String device, int address) throws ProviderException {
        return instance(provider, new I2CConfig(device, address));
    }

    public static I2C instance(I2CProvider provider, I2CConfig config) throws ProviderException {
        try {
            // get default I2C io if io is null
            if(provider == null){
                provider = Pi4J.providers().i2c().getDefault();
            }
            // create a I2C instance using the io
            return provider.instance(config);
        } catch(ProviderException pe){
            throw pe;
        } catch (Exception e) {
            throw new ProviderInstantiateException(provider, e);
        }
    }
}
