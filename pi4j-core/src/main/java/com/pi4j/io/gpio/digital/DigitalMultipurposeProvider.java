package com.pi4j.io.gpio.digital;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalMultipurposeProvider.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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

/**
 * <p>DigitalMultipurposeProvider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface DigitalMultipurposeProvider extends DigitalProvider<DigitalMultipurposeProvider, DigitalMultipurpose, DigitalMultipurposeConfig> {

    /**
     * <p>create.</p>
     *
     * @param builder a {@link DigitalMultipurposeConfigBuilder} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws Exception if any.
     */
    default <T extends DigitalMultipurpose> T create(DigitalMultipurposeConfigBuilder builder) throws Exception {
        return (T)create(builder.build());
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link Integer} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws Exception if any.
     */
    default <T extends DigitalMultipurpose> T create(Integer address) throws Exception {
        var config = DigitalMultipurpose.newConfigBuilder(context())
                .address(address)
                .build();
        return (T)create(config);
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link Integer} object.
     * @param id a {@link String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws Exception if any.
     */
    default <T extends DigitalMultipurpose> T create(Integer address, String id) throws Exception {
        var config = DigitalMultipurpose.newConfigBuilder(context())
                .address(address)
                .id(id)
                .build();
        return (T)create(config);
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link Integer} object.
     * @param id a {@link String} object.
     * @param name a {@link String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws Exception if any.
     */
    default <T extends DigitalMultipurpose> T create(Integer address, String id, String name) throws Exception {
        var config = DigitalMultipurpose.newConfigBuilder(context())
                .address(address)
                .id(id)
                .name(name)
                .build();
        return (T)create(config);
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link Integer} object.
     * @param id a {@link String} object.
     * @param name a {@link String} object.
     * @param description a {@link String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws Exception if any.
     */
    default <T extends DigitalMultipurpose> T create(Integer address, String id, String name, String description) throws Exception {
        var config = DigitalMultipurpose.newConfigBuilder(context())
                .address(address)
                .id(id)
                .name(name)
                .description(description)
                .build();
        return (T)create(config);
    }
}
