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
import com.pi4j.io.gpio.analog.impl.AnalogOutputFactory;
import com.pi4j.provider.exception.ProviderException;

import java.util.Properties;

public interface AnalogInput extends Analog<AnalogInput, AnalogInputConfig>, Input {

    // ---------------------------------------------------------------------------
    // INSTANCE ACCESSOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static boolean exists(String id) throws ProviderException {
        return AnalogOutputFactory.exists(id);
    }

    static AnalogInput get(String id) throws ProviderException {
        return AnalogInputFactory.get(id);
    }

    // ---------------------------------------------------------------------------
    // BUILDER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogInputBuilder builder() throws ProviderException {
        return AnalogInputFactory.builder();
    }

    static AnalogInputBuilder builder(Properties properties) throws ProviderException {
        return AnalogInputFactory.builder(properties);
    }

    // ---------------------------------------------------------------------------
    // FRIENDLY HELPER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogInput create(Integer address) throws ProviderException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.address(address);
        return AnalogInput.create(builder.build());
    }

    static AnalogInput create(Integer address, String id) throws ProviderException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.id(id).address(address);
        return AnalogInput.create(builder.build());
    }

    static AnalogInput create(Integer address, String id, String name) throws ProviderException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.id(id).address(address).name(name);
        return AnalogInput.create(builder.build());
    }

    static AnalogInput create(Properties properties) throws ProviderException {
        AnalogInputBuilder builder = AnalogInput.builder(properties);
        return AnalogInput.create(builder.build());
    }

    static AnalogInput create(String providerId, Integer address) throws ProviderException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.address(address);
        return AnalogInput.create(providerId, builder.build());
    }

    static AnalogInput create(AnalogInputProvider provider, Integer address) throws ProviderException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.address(address);
        return AnalogInput.create(provider, builder.build());
    }

    // ---------------------------------------------------------------------------
    // RAW FACTORY CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogInput create(AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.create(config);
    }

    static AnalogInput create(String providerId, AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.create(providerId, config);
    }

    static AnalogInput create(AnalogInputProvider provider, AnalogInputConfig config) throws ProviderException {
        return AnalogInputFactory.create(provider, config);
    }

    // ---------------------------------------------------------------------------
    // SPECIFIED RETURN CLASS HELPER METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogInput> T create(Integer address, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(address);
    }

    static <T extends AnalogInput> T create(Integer address, String id, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(address, id);
    }

    static <T extends AnalogInput> T create(Integer address, String id, String name, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(address, id, name);
    }

    static <T extends AnalogInput> T create(Properties properties, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(properties);
    }

    static <T extends AnalogInput> T create(String providerId, Integer address, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(providerId, address);
    }

    static <T extends AnalogInput> T create(AnalogInputProvider provider, Integer address, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(provider, address);
    }

    static <T extends AnalogInput> T create(AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(config);
    }

    static <T extends AnalogInput> T create(String providerId, AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(providerId, config);
    }

    static <T extends AnalogInput> T create(AnalogInputProvider provider, AnalogInputConfig config, Class<T> clazz) throws ProviderException {
        return (T)AnalogInput.create(provider, config);
    }
}

