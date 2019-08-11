package com.pi4j.io.gpio.digital;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputProvider.java
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
public interface DigitalOutputProvider extends DigitalProvider<DigitalOutputProvider, DigitalOutput, DigitalOutputConfig> {

    default <T extends DigitalOutput> T create(Integer address) throws Exception {
        var builder = DigitalOutputConfigBuilder.newInstance();
        builder.address(address);
        return (T)create(builder.build());
    }

    default <T extends DigitalOutput> T create(Integer address, String id) throws Exception {
        var builder = DigitalOutputConfigBuilder.newInstance();
        builder.id(id).address(address).id(id);
        return (T)create(builder.build());
    }

    default <T extends DigitalOutput> T create(Integer address, String id, String name) throws Exception {
        var builder = DigitalOutputConfigBuilder.newInstance();
        builder.id(id).address(address).id(id).name(name);
        return (T)create(builder.build());
    }

    default <T extends DigitalOutput> T create(Integer address, String id, String name, String description) throws Exception {
        var builder = DigitalOutputConfigBuilder.newInstance();
        builder.id(id).address(address).id(id).name(name).description(description);
        return (T)create(builder.build());
    }
}
