package com.pi4j.io.gpio.analog.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutputFactory.java
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
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.gpio.analog.*;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;

import java.util.Properties;

/**
 * Analog Output Factory - it returns instances of {@link AnalogOutput} interface.
 *
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class AnalogOutputFactory {

    // private constructor
    private AnalogOutputFactory() {
        // forbid object construction
    }

    public static boolean exists(String id) throws ProviderException, NotInitializedException {
        return Pi4J.context().registry().exists(id, AnalogOutput.class);
    }

    public static <T extends AnalogOutput> T get(String id) throws ProviderException, NotInitializedException, RegistryException {
        return (T)Pi4J.context().registry().get(id, AnalogOutput.class);
    }

    public static AnalogOutputBuilder builder() throws ProviderException {
        return new DefaultAnalogOutputBuilder();
    }

    public static AnalogOutputBuilder builder(Properties properties) {
        return new DefaultAnalogOutputBuilder(properties);
    }

    public static <T extends AnalogOutput> T  create(AnalogOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)Pi4J.context().registry().create(config, AnalogOutput.class);
    }

    public static <T extends AnalogOutput> T  create(AnalogOutputProvider provider, AnalogOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)Pi4J.context().registry().create(provider, config, AnalogOutput.class);
    }

    public static  <T extends AnalogOutput> T create(String providerId, AnalogOutputConfig config) throws ProviderException, NotInitializedException, RegistryException {
        return (T)Pi4J.context().registry().create(providerId, config, AnalogOutput.class);
    }

    public static <T extends AnalogOutput> T create(AnalogOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)Pi4J.context().registry().create(config, clazz);
    }

    public static <T extends AnalogOutput> T create(String providerId, AnalogOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)Pi4J.context().registry().create(providerId, config, clazz);
    }

    public static <T extends AnalogOutput> T create(AnalogOutputProvider provider, AnalogOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)Pi4J.context().registry().create(provider, config, clazz);
    }
}
