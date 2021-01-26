package com.pi4j.io.gpio.analog;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  AnalogOutputProvider.java
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

/**
 * <p>AnalogOutputProvider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface AnalogOutputProvider extends AnalogProvider<AnalogOutputProvider, AnalogOutput, AnalogOutputConfig> {

    // ---------------------------------------------------------------------------
    // FRIENDLY HELPER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    /**
     * <p>create.</p>
     *
     * @param address a {@link java.lang.Integer} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws java.lang.Exception if any.
     */
    default <T extends AnalogOutput> T create(Integer address) throws Exception {
        var builder = AnalogOutputConfigBuilder.newInstance(context());
        builder.address(address);
        return (T)create(builder.build());
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link java.lang.Integer} object.
     * @param id a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws java.lang.Exception if any.
     */
    default <T extends AnalogOutput> T create(Integer address, String id) throws Exception {
        var builder = AnalogOutputConfigBuilder.newInstance(context());
        builder.id(id).address(address).id(id);
        return (T)create(builder.build());
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link java.lang.Integer} object.
     * @param id a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws java.lang.Exception if any.
     */
    default <T extends AnalogOutput> T create(Integer address, String id, String name) throws Exception {
        var builder = AnalogOutputConfigBuilder.newInstance(context());
        builder.id(id).address(address).id(id).name(name);
        return (T)create(builder.build());
    }

    /**
     * <p>create.</p>
     *
     * @param address a {@link java.lang.Integer} object.
     * @param id a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws java.lang.Exception if any.
     */
    default <T extends AnalogOutput> T create(Integer address, String id, String name, String description) throws Exception {
        var builder = AnalogOutputConfigBuilder.newInstance(context());
        builder.id(id).address(address).id(id).name(name).description(description);
        return (T)create(builder.build());
    }
}
