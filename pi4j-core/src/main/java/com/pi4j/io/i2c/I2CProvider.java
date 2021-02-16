package com.pi4j.io.i2c;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  I2CProvider.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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

import com.pi4j.provider.Provider;

/**
 * <p>I2CProvider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface I2CProvider extends Provider<I2CProvider, I2C, I2CConfig> {

    /**
     * <p>create.</p>
     *
     * @param builder a {@link com.pi4j.io.i2c.I2CConfigBuilder} object.
     * @param <T> a T object.
     * @return a T object.
     */
    default <T extends I2C> T create(I2CConfigBuilder builder) {
        return (T)create(builder.build());
    }

    /**
     * <p>create.</p>
     *
     * @param bus a {@link java.lang.Integer} object.
     * @param device a {@link java.lang.Integer} object.
     * @param <T> a T object.
     * @return a T object.
     */
    default <T extends I2C> T create(Integer bus, Integer device) {
        var config = I2C.newConfigBuilder(context())
                .bus(bus)
                .device(device)
                .build();
        return (T)create(config);
    }

    /**
     * <p>create.</p>
     *
     * @param bus a {@link java.lang.Integer} object.
     * @param device a {@link java.lang.Integer} object.
     * @param id a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     */
    default <T extends I2C> T create(Integer bus, Integer device, String id) {
        var config = I2C.newConfigBuilder(context())
                .bus(bus)
                .device(device)
                .id(id)
                .build();
        return (T)create(config);
    }

    /**
     * <p>create.</p>
     *
     * @param bus a {@link java.lang.Integer} object.
     * @param device a {@link java.lang.Integer} object.
     * @param id a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     */
    default <T extends I2C> T create(Integer bus, Integer device, String id, String name) {
        var config = I2C.newConfigBuilder(context())
                .bus(bus)
                .device(device)
                .id(id)
                .name(name)
                .build();
        return (T)create(config);
    }

    /**
     * <p>create.</p>
     *
     * @param bus a {@link java.lang.Integer} object.
     * @param device a {@link java.lang.Integer} object.
     * @param id a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     */
    default <T extends I2C> T create(Integer bus, Integer device, String id, String name, String description) {
        var config = I2C.newConfigBuilder(context())
                .bus(bus)
                .device(device)
                .id(id)
                .name(name)
                .description(description)
                .build();
        return (T)create(config);
    }

}
