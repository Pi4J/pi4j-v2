package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutput.java
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

import com.pi4j.io.Output;
import com.pi4j.io.gpio.analog.impl.AnalogOutputFactory;
import com.pi4j.provider.exception.ProviderException;

import java.util.Properties;

public interface AnalogOutput extends Analog<AnalogOutput, AnalogOutputConfig>, Output {
    AnalogOutput value(Number value);

    default AnalogOutput setValue(Number value) { return value(value); };

    // ---------------------------------------------------------------------------
    // INSTANCE ACCESSOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static boolean exists(String id) throws ProviderException {
        return AnalogOutputFactory.exists(id);
    }

    static AnalogOutput get(String id) throws ProviderException {
        return AnalogOutputFactory.get(id);
    }

    // ---------------------------------------------------------------------------
    // BUILDER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogOutputBuilder builder() throws ProviderException {
        return AnalogOutputFactory.builder();
    }

    static AnalogOutputBuilder builder(Properties properties) throws ProviderException {
        return AnalogOutputFactory.builder(properties);
    }

    // ---------------------------------------------------------------------------
    // FRIENDLY HELPER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogOutput create(Integer address) throws ProviderException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.address(address);
        return AnalogOutput.create(builder.build());
    }

    static AnalogOutput create(Integer address, String id) throws ProviderException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.id(id).address(address);
        return AnalogOutput.create(builder.build());
    }

    static AnalogOutput create(Integer address, String id, String name) throws ProviderException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.id(id).address(address).name(name);
        return AnalogOutput.create(builder.build());
    }

    static AnalogOutput create(Properties properties) throws ProviderException {
        AnalogOutputBuilder builder = AnalogOutput.builder(properties);
        return AnalogOutput.create(builder.build());
    }

    static AnalogOutput create(String providerId, Integer address) throws ProviderException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.address(address);
        return AnalogOutput.create(providerId, builder.build());
    }

    static AnalogOutput create(AnalogOutputProvider provider, Integer address) throws ProviderException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.address(address);
        return AnalogOutput.create(provider, builder.build());
    }

    // ---------------------------------------------------------------------------
    // RAW FACTORY CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogOutput create(AnalogOutputConfig config) throws ProviderException {
        return AnalogOutputFactory.create(config);
    }

    static AnalogOutput create(String providerId, AnalogOutputConfig config) throws ProviderException {
        return AnalogOutputFactory.create(providerId, config);
    }

    static AnalogOutput create(AnalogOutputProvider provider, AnalogOutputConfig config) throws ProviderException {
        return AnalogOutputFactory.create(provider, config);
    }

    // ---------------------------------------------------------------------------
    // SPECIFIED RETURN CLASS HELPER METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogOutput> T create(Integer address, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(address);
    }

    static <T extends AnalogOutput> T create(Integer address, String id, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(address, id);
    }

    static <T extends AnalogOutput> T create(Integer address, String id, String name, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(address, id, name);
    }

    static <T extends AnalogOutput> T create(Properties properties, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(properties);
    }

    static <T extends AnalogOutput> T create(String providerId, Integer address, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(providerId, address);
    }

    static <T extends AnalogOutput> T create(AnalogOutputProvider provider, Integer address, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(provider, address);
    }

    static <T extends AnalogOutput> T create(AnalogOutputConfig config, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(config);
    }

    static <T extends AnalogOutput> T create(String providerId, AnalogOutputConfig config, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(providerId, config);
    }

    static <T extends AnalogOutput> T create(AnalogOutputProvider provider, AnalogOutputConfig config, Class<T> clazz) throws ProviderException {
        return (T)AnalogOutput.create(provider, config);
    }
}
