package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultAnalogInputBuilder.java
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
import com.pi4j.io.gpio.digital.DigitalInputBuilder;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.PullResistance;

import java.util.Properties;

public class DefaultDigitalInputBuilder
        extends AddressConfigBuilderBase<DigitalInputBuilder, DigitalInputConfig>
        implements DigitalInputBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultDigitalInputBuilder(){
        super();
    }

    protected DefaultDigitalInputBuilder(Properties properties){
        super(properties);
    }

    @Override
    public DigitalInputConfig build() {
        DigitalInputConfig config = new DefaultDigitalInputConfig(properties);
        return config;
    }

    @Override
    public DigitalInputBuilder pull(PullResistance value) {
        this.properties.setProperty(DigitalInputConfig.PULL_RESISTANCE_KEY, value.toString());
        return this;
    }
}
