package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalInput.java
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
import com.pi4j.io.gpio.digital.impl.DigitalInputFactory;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.util.Descriptor;

public interface DigitalInput extends Digital<DigitalInput, DigitalInputConfig>, Input {

    default PullResistance pull() { return config().pull(); }

    static DigitalInput instance(int address) throws ProviderException {
        return DigitalInputFactory.instance(address);
    }
    static <T extends DigitalInput> T instance(int address, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(address, clazz);
    }

    static DigitalInput instance(int address, PullResistance pull) throws ProviderException {
        return DigitalInputFactory.instance(address, pull);
    }
    static <T extends DigitalInput> T instance(int address, PullResistance pull, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(address, pull, clazz);
    }

    static DigitalInput instance(DigitalInputConfig config) throws ProviderException {
        return DigitalInputFactory.instance(config);
    }
    static <T extends DigitalInput> T instance(DigitalInputConfig config, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(config, clazz);
    }

    static DigitalInput instance(String providerId, int address) throws ProviderException {
        return DigitalInputFactory.instance(providerId, address);
    }
    static <T extends DigitalInput> T instance(String providerId, int address, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(providerId, address, clazz);
    }

    static DigitalInput instance(String providerId, int address, PullResistance pull) throws ProviderException {
        return DigitalInputFactory.instance(providerId, address, pull);
    }
    static <T extends DigitalInput> T instance(String providerId, int address, PullResistance pull, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(providerId, address, pull, clazz);
    }

    static DigitalInput instance(String providerId, DigitalInputConfig config) throws ProviderException {
        return DigitalInputFactory.instance(providerId, config);
    }
    static <T extends DigitalInput> T instance(String providerId, DigitalInputConfig config, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(providerId, config, clazz);
    }

    static DigitalInput instance(DigitalInputProvider provider, int address) throws ProviderException {
        return DigitalInputFactory.instance(provider, address);
    }
    static <T extends DigitalInput> T instance(DigitalInputProvider provider, int address, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(provider, address,clazz);
    }

    static DigitalInput instance(DigitalInputProvider provider, int address, PullResistance pull) throws ProviderException {
        return DigitalInputFactory.instance(provider, address, pull);
    }
    static <T extends DigitalInput> T instance(DigitalInputProvider provider, int address, PullResistance pull, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(provider, address, pull, clazz);
    }

    static DigitalInput instance(DigitalInputProvider provider, DigitalInputConfig config) throws ProviderException {
        return DigitalInputFactory.instance(provider, config);
    }
    static <T extends DigitalInput> T instance(DigitalInputProvider provider, DigitalInputConfig config, Class<T> clazz) throws ProviderException {
        return DigitalInputFactory.instance(provider, config, clazz);
    }
}
