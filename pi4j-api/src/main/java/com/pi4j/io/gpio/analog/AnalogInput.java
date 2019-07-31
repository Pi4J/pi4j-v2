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

import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.Input;
import com.pi4j.io.gpio.analog.impl.AnalogInputFactory;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;

public interface AnalogInput extends Analog<AnalogInput, AnalogInputConfig>, Input {

    // ---------------------------------------------------------------------------
    // INSTANCE ACCESSOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static boolean exists(Context context, String id) throws ProviderException, NotInitializedException {
        return AnalogInputFactory.exists(context, id);
    }

    static <T extends AnalogInput> T get(Context context, String id) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInputFactory.get(context, id);
    }

    // ---------------------------------------------------------------------------
    // BUILDER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static AnalogInputBuilder builder() throws ProviderException {
        return AnalogInputFactory.builder();
    }

    // ---------------------------------------------------------------------------
    // FRIENDLY HELPER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogInput> T create(Context context, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.address(address);
        return (T)AnalogInput.create(context, builder.build());
    }

    static <T extends AnalogInput> T create(Context context, Integer address, String id) throws ProviderException, NotInitializedException, RegistryException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.id(id).address(address).id(id);
        return (T)AnalogInput.create(context, builder.build());
    }

    static <T extends AnalogInput> T create(Context context, Integer address, String id, String name) throws ProviderException, NotInitializedException, RegistryException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.id(id).address(address).id(id).name(name);
        return (T)AnalogInput.create(context, builder.build());
    }

    static <T extends AnalogInput> T create(Context context, String providerId, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.address(address);
        return (T)AnalogInput.create(context, providerId, builder.build());
    }

    static <T extends AnalogInput> T create(Context context, AnalogInputProvider provider, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        AnalogInputBuilder builder = AnalogInput.builder();
        builder.address(address);
        return (T)AnalogInput.create(context, provider, builder.build());
    }

    // ---------------------------------------------------------------------------
    // RAW FACTORY CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogInput> T create(Context context, AnalogInputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogInputFactory.create(context, config);
    }

    static <T extends AnalogInput> T create(Context context, String providerId, AnalogInputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogInputFactory.create(context, providerId, config);
    }

    static <T extends AnalogInput> T create(Context context, AnalogInputProvider provider, AnalogInputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogInputFactory.create(context, provider, config);
    }

    // ---------------------------------------------------------------------------
    // SPECIFIED RETURN CLASS HELPER METHODS
    // ---------------------------------------------------------------------------

    static <T extends AnalogInput> T create(Context context, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, address);
    }

    static <T extends AnalogInput> T create(Context context, Integer address, String id, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, address, id);
    }

    static <T extends AnalogInput> T create(Context context, Integer address, String id, String name, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, address, id, name);
    }

    static <T extends AnalogInput> T create(Context context, String providerId, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, providerId, address);
    }

    static <T extends AnalogInput> T create(Context context, AnalogInputProvider provider, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, provider, address);
    }

    static <T extends AnalogInput> T create(Context context, AnalogInputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, config);
    }

    static <T extends AnalogInput> T create(Context context, String providerId, AnalogInputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)AnalogInput.create(context, providerId, config);
    }

    static <T extends AnalogInput> T create(Context context, AnalogInputProvider provider, AnalogInputConfig config, Class<T> clazz) throws NotInitializedException, ProviderException, RegistryException {
        return (T)AnalogInput.create(context, provider, config);
    }
}

