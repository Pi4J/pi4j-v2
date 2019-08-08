package com.pi4j.io.pwm.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  PwmFactory.java
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
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.provider.exception.ProviderException;

/**
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www .savagehomeautomation.com</a>)
 */
public class PwmFactory {

    // private constructor
    private PwmFactory() {
        // forbid object construction
    }

    public static Pwm instance(Context context, PwmConfig config) throws ProviderException {
        // get I2C instance using default io
        var provider = context.platform().pwm();
        return instance(provider, config);
    }

    public static Pwm instance(Context context, int address) throws ProviderException {
        return instance(context, new PwmConfig(address));
    }

    public static Pwm instance(Context context, String providerId, int address) throws ProviderException {
        return instance(context, providerId, new PwmConfig(address));
    }

    public static Pwm instance(Context context, String providerId, PwmConfig config) throws ProviderException {
        // if provided, lookup the specified io; else use the default io
        if(providerId == null) {
            return instance(context, config);
        }
        else{
            PwmProvider provider = context.providers().pwm().get(providerId);
            return instance(provider, config);
        }
    }

    public static Pwm instance(PwmProvider provider, int address) throws ProviderException {
        return instance(provider, new PwmConfig(address));
    }

    public static Pwm instance(PwmProvider provider, PwmConfig config) throws ProviderException {
        try {
            // create a PWM instance using the io
            return provider.create(config);
        } catch(ProviderException pe){
            throw pe;
        } catch (Exception e) {
            throw new ProviderException(provider, e);
        }
    }
}
