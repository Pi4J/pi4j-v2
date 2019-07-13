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

import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.Output;
import com.pi4j.io.gpio.analog.impl.AnalogOutputFactory;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;

import java.util.Properties;

public interface AnalogOutput extends Analog<AnalogOutput, AnalogOutputConfig>, Output {
    AnalogOutput value(Integer value);

    default AnalogOutput setValue(Integer value) { return value(value); };

    // ---------------------------------------------------------------------------
    // INSTANCE ACCESSOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static boolean exists(String id) throws ProviderException, NotInitializedException {
        return AnalogOutputFactory.exists(id);
    }

    static <T extends AnalogOutput> T get(String id) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutputFactory.get(id);
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

    static <T extends AnalogOutput> T create(Integer address) throws ProviderException, NotInitializedException, RegistryException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.address(address);
        return (T)AnalogOutput.create(builder.build());
    }

    static <T extends AnalogOutput> T create(Integer address, String id) throws ProviderException, NotInitializedException, RegistryException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.id(id).address(address).id(id);
        return (T)AnalogOutput.create(builder.build());
    }

    static <T extends AnalogOutput> T create(Integer address, String id, String name) throws ProviderException, NotInitializedException, RegistryException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.id(id).address(address).id(id).name(name);
        return (T)AnalogOutput.create(builder.build());
    }

    static <T extends AnalogOutput> T create(Properties properties) throws ProviderException, NotInitializedException, RegistryException {
        AnalogOutputBuilder builder = AnalogOutput.builder(properties);
        return (T)AnalogOutput.create(builder.build());
    }

    static <T extends AnalogOutput> T create(String providerId, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.address(address);
        return (T)AnalogOutput.create(providerId, builder.build());
    }

    static <T extends AnalogOutput> T create(AnalogOutputProvider provider, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        AnalogOutputBuilder builder = AnalogOutput.builder();
        builder.address(address);
        return (T)AnalogOutput.create(provider, builder.build());
    }

    // ---------------------------------------------------------------------------
    // RAW FACTORY CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogOutput> T create(AnalogOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogOutputFactory.create(config);
    }

    static <T extends AnalogOutput> T create(String providerId, AnalogOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogOutputFactory.create(providerId, config);
    }

    static <T extends AnalogOutput> T create(AnalogOutputProvider provider, AnalogOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogOutputFactory.create(provider, config);
    }

    // ---------------------------------------------------------------------------
    // SPECIFIED RETURN CLASS HELPER METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogOutput> T create(Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(address);
    }

    static <T extends AnalogOutput> T create(Integer address, String id, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(address, id);
    }

    static <T extends AnalogOutput> T create(Integer address, String id, String name, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(address, id, name);
    }

    static <T extends AnalogOutput> T create(Properties properties, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(properties);
    }

    static <T extends AnalogOutput> T create(String providerId, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(providerId, address);
    }

    static <T extends AnalogOutput> T create(AnalogOutputProvider provider, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(provider, address);
    }

    static <T extends AnalogOutput> T create(AnalogOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(config);
    }

    static <T extends AnalogOutput> T create(String providerId, AnalogOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogOutput.create(providerId, config);
    }

    static <T extends AnalogOutput> T create(AnalogOutputProvider provider, AnalogOutputConfig config, Class<T> clazz) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogOutput.create(provider, config);
    }
}
