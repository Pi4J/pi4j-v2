package com.pi4j.io.spi.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultSerialConfigBuilder.java
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

import com.pi4j.config.impl.AddressConfigBuilderBase;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiConfigBuilder;
import com.pi4j.io.spi.SpiMode;

public class DefaultSpiConfigBuilder
        extends AddressConfigBuilderBase<SpiConfigBuilder, SpiConfig>
        implements SpiConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultSpiConfigBuilder(){
        super();
    }

    public static SpiConfigBuilder newInstance() {
        return new DefaultSpiConfigBuilder();
    }

    @Override
    public SpiConfigBuilder baud(Integer rate) {
        this.properties.put(SpiConfig.BAUD_KEY, rate.toString());
        return this;
    }

    @Override
    public SpiConfigBuilder mode(SpiMode mode) {
        this.properties.put(SpiConfig.MODE_KEY, Integer.toString(mode.getMode()));
        return this;
    }

    @Override
    public SpiConfig build() {
        SpiConfig config = new DefaultSpiConfig(this.properties);
        return config;
    }
}
