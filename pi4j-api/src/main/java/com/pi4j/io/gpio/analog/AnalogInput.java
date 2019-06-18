package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogInput.java
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

import com.pi4j.io.Input;
import com.pi4j.io.gpio.analog.impl.AnalogInputFactory;
import com.pi4j.provider.exception.ProviderException;

public interface AnalogInput extends Analog<AnalogInput, AnalogInputConfig>, Input {

    static AnalogInput instance(int address) throws ProviderException {
        return AnalogInputFactory.instance(address);
    }
    static <T extends AnalogInput> T instance(int address, Class<T> clazz) throws ProviderException {
        return AnalogInputFactory.instance(address, clazz);
    }

    static AnalogInput instance(AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.instance(config);
    }
    static <T extends AnalogInput> T instance(AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return AnalogInputFactory.instance(config, clazz);
    }

    static AnalogInput instance(String providerId, int address) throws ProviderException {
        return AnalogInputFactory.instance(providerId, address);
    }
    static <T extends AnalogInput> T instance(String providerId, int address, Class<T> clazz) throws ProviderException {
        return AnalogInputFactory.instance(providerId, address, clazz);
    }

    static AnalogInput instance(String providerId, AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.instance(providerId, config);
    }
    static <T extends AnalogInput> T instance(String providerId, AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return AnalogInputFactory.instance(providerId, config, clazz);
    }

    static AnalogInput instance(AnalogInputProvider provider, int address) throws ProviderException {
        return AnalogInputFactory.instance(provider, address);
    }
    static <T extends AnalogInput> T instance(AnalogInputProvider provider, int address, Class<T> clazz) throws ProviderException {
        return AnalogInputFactory.instance(provider, address,clazz);
    }

    static AnalogInput instance(AnalogInputProvider provider, AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.instance(provider, config);
    }
    static <T extends AnalogInput> T instance(AnalogInputProvider provider, AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return AnalogInputFactory.instance(provider, config, clazz);
    }
}

