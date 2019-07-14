package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultDigitalOutputConfig.java
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

import com.pi4j.config.impl.AddressConfigBase;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.util.StringUtil;

import java.util.Properties;

public class DefaultDigitalOutputConfig
        extends AddressConfigBase<DigitalOutputConfig>
        implements DigitalOutputConfig {

    // private configuration properties
    protected DigitalState shutdownState = null;
    protected DigitalState initialState = null;

    /**
     * PRIVATE CONSTRUCTOR
     */
    private DefaultDigitalOutputConfig(){
        super();
    }

    /**
     * PRIVATE CONSTRUCTOR
     * @param properties
     */
    protected DefaultDigitalOutputConfig(Properties properties){
        super(properties);

        // define default property values if any are missing (based on the required address value)
        this.id = StringUtil.setIfNullOrEmpty(this.id, "DOUT-" + this.address, true);
        this.name = StringUtil.setIfNullOrEmpty(this.name, "DOUT-" + this.address, true);
        this.description = StringUtil.setIfNullOrEmpty(this.description, "DOUT-" + this.address, true);

        // load initial value property
        if(properties.containsKey(INITIAL_STATE_KEY)){
            this.initialState = DigitalState.parse(properties.getProperty(INITIAL_STATE_KEY));
        }

        // load shutdown value property
        if(properties.containsKey(SHUTDOWN_STATE_KEY)){
            this.shutdownState = DigitalState.parse(properties.getProperty(SHUTDOWN_STATE_KEY));
        }
    }

    @Override
    public DigitalState shutdownState(){
        return this.shutdownState;
    }

    @Override
    public DefaultDigitalOutputConfig shutdownState(DigitalState state){
        this.shutdownState = state;
        return this;
    }

    @Override
    public DigitalState initialState() {
        return this.initialState;
    }
}
