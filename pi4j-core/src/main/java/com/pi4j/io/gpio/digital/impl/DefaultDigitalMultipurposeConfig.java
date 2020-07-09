package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultDigitalMultipurposeConfig.java
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

import com.pi4j.io.gpio.digital.*;
import com.pi4j.io.impl.IOAddressConfigBase;
import com.pi4j.util.StringUtil;

import java.util.Map;

/**
 * <p>DefaultDigitalInputConfig class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultDigitalMultipurposeConfig
        extends IOAddressConfigBase<DigitalMultipurposeConfig>
        implements DigitalMultipurposeConfig {

    /**
     * PRIVATE CONSTRUCTOR
     */
    private DefaultDigitalMultipurposeConfig(){
        super();
    }

    // private configuration properties
    protected PullResistance pullResistance = PullResistance.OFF;
    protected Long debounce = DigitalInput.DEFAULT_DEBOUNCE;
    protected DigitalState shutdownState = null;
    protected DigitalState initialState = null;
    protected DigitalMode initialMode = DigitalMode.OUTPUT;

    /**
     * PRIVATE CONSTRUCTOR
     *
     * @param properties a {@link Map} object.
     */
    protected DefaultDigitalMultipurposeConfig(Map<String,String> properties){
        super(properties);

        // define default property values if any are missing (based on the required address value)
        this.id = StringUtil.setIfNullOrEmpty(this.id, "DIN-" + this.address, true);
        this.name = StringUtil.setIfNullOrEmpty(this.name, "DIN-" + this.address, true);
        this.description = StringUtil.setIfNullOrEmpty(this.description, "DIN-" + this.address, true);

        // load optional pull resistance from properties
        if(properties.containsKey(PULL_RESISTANCE_KEY)){
            this.pullResistance = PullResistance.parse(properties.get(PULL_RESISTANCE_KEY));
        }

        // load optional pull resistance from properties
        if(properties.containsKey(DEBOUNCE_RESISTANCE_KEY)){
            this.debounce = Long.parseLong(properties.get(DEBOUNCE_RESISTANCE_KEY));
        }

        // load initial value property
        if(properties.containsKey(INITIAL_STATE_KEY)){
            this.initialState = DigitalState.parse(properties.get(INITIAL_STATE_KEY));
        }

        // load shutdown value property
        if(properties.containsKey(SHUTDOWN_STATE_KEY)){
            this.shutdownState = DigitalState.parse(properties.get(SHUTDOWN_STATE_KEY));
        }

        // load initial mode property
        if(properties.containsKey(INITIAL_MODE_KEY)){
            this.initialMode = DigitalMode.parse(properties.get(INITIAL_MODE_KEY));
        }
    }

    /** {@inheritDoc} */
    @Override
    public PullResistance pull() {
        return this.pullResistance;
    }

    /** {@inheritDoc} */
    @Override
    public Long debounce() { return this.debounce; }

    /** {@inheritDoc} */
    @Override
    public DigitalMode initialMode(){
        return this.initialMode;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalState shutdownState(){
        return this.shutdownState;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfig shutdownState(DigitalState state){
        this.shutdownState = state;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalState initialState() {
        return this.initialState;
    }
}
