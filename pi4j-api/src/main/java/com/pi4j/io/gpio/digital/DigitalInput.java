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


import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.Input;
import com.pi4j.io.gpio.digital.impl.DigitalInputFactory;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;

public interface DigitalInput extends Digital<DigitalInput, DigitalInputConfig>, Input {

    default PullResistance pull() { return config().pull(); }

    // ---------------------------------------------------------------------------
    // INSTANCE ACCESSOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static boolean exists(Context context, String id) throws ProviderException, NotInitializedException {
        return DigitalInputFactory.exists(context, id);
    }

    static <T extends DigitalInput> T get(Context context, String id) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalInputFactory.get(context, id);
    }

    // ---------------------------------------------------------------------------
    // BUILDER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static DigitalInputBuilder builder() throws ProviderException {
        return DigitalInputFactory.builder();
    }

    // ---------------------------------------------------------------------------
    // FRIENDLY HELPER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends DigitalInput> T create(Context context, Integer address) throws Pi4JException {
        DigitalInputBuilder builder = DigitalInput.builder();
        builder.address(address);
        return (T)DigitalInput.create(context, builder.build());
    }

    static <T extends DigitalInput> T create(Context context, Integer address, String id) throws Pi4JException {
        DigitalInputBuilder builder = DigitalInput.builder();
        builder.id(id).address(address).id(id);
        return (T)DigitalInput.create(context, builder.build());
    }

    static <T extends DigitalInput> T create(Context context, Integer address, String id, String name) throws Pi4JException {
        DigitalInputBuilder builder = DigitalInput.builder();
        builder.id(id).address(address).id(id).name(name);
        return (T)DigitalInput.create(context, builder.build());
    }

    static <T extends DigitalInput> T create(Context context, String providerId, Integer address) throws Pi4JException {
        DigitalInputBuilder builder = DigitalInput.builder();
        builder.address(address);
        return (T)DigitalInput.create(context, providerId, builder.build());
    }

    static <T extends DigitalInput> T create(Context context, DigitalInputProvider provider, Integer address) throws Pi4JException {
        DigitalInputBuilder builder = DigitalInput.builder();
        builder.address(address);
        return (T)DigitalInput.create(context, provider, builder.build());
    }

    // ---------------------------------------------------------------------------
    // RAW FACTORY CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends DigitalInput> T create(Context context, DigitalInputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalInputFactory.create(context, config);
    }

    static <T extends DigitalInput> T create(Context context, String providerId, DigitalInputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalInputFactory.create(context, providerId, config);
    }

    static <T extends DigitalInput> T create(Context context, DigitalInputProvider provider, DigitalInputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalInputFactory.create(context, provider, config);
    }

    // ---------------------------------------------------------------------------
    // SPECIFIED RETURN CLASS HELPER METHODS
    // ---------------------------------------------------------------------------

    static <T extends DigitalInput> T create(Context context, Integer address, Class<T> clazz) throws Pi4JException {
        return (T)DigitalInput.create(context, address);
    }

    static <T extends DigitalInput> T create(Context context, Integer address, String id, Class<T> clazz) throws Pi4JException {
        return (T)DigitalInput.create(context, address, id);
    }

    static <T extends DigitalInput> T create(Context context, Integer address, String id, String name, Class<T> clazz) throws Pi4JException {
        return (T)DigitalInput.create(context, address, id, name);
    }

    static <T extends DigitalInput> T create(Context context, String providerId, Integer address, Class<T> clazz) throws Pi4JException {
        return (T)DigitalInput.create(context, providerId, address);
    }

    static <T extends DigitalInput> T create(Context context, DigitalInputProvider provider, Integer address, Class<T> clazz) throws Pi4JException {
        return (T)DigitalInput.create(context, provider, address);
    }

    static <T extends DigitalInput> T create(Context context, DigitalInputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalInput.create(context, config);
    }

    static <T extends DigitalInput> T create(Context context, String providerId, DigitalInputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalInput.create(context, providerId, config);
    }

    static <T extends DigitalInput> T create(Context context, DigitalInputProvider provider, DigitalInputConfig config, Class<T> clazz) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalInput.create(context, provider, config);
    }

}
