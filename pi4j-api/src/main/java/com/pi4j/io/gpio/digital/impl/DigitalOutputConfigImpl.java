package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputConfigImpl.java
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

import com.pi4j.config.AbstractAddressConfig;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;

import java.util.Properties;

public class DigitalOutputConfigImpl extends AbstractAddressConfig<DigitalOutputConfig> implements DigitalOutputConfig {

    DigitalState shutdownState = DigitalState.UNKNOWN;

    public DigitalOutputConfigImpl(){
    }

    public DigitalState shutdownState(){
        return this.shutdownState;
    }

    public DigitalOutputConfig shutdownState(DigitalState state){
        this.shutdownState = state;
        return this;
    }

    @Override
    public DigitalOutputConfig load(Properties properties, String prefix){

        // ensure properties is not empty
        super.load(properties, prefix);

        // load any optional properties
        if(properties.containsKey(prefix + ".shutdown")){
            DigitalState shutdownState = DigitalState.parse(properties.get(prefix + ".shutdown").toString());
            shutdownState(shutdownState);
        }

        return this;
    }
}
