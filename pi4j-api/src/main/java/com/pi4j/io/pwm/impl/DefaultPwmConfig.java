package com.pi4j.io.pwm.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultPwmConfig.java
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
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmPreset;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.util.StringUtil;

import java.util.*;

public class DefaultPwmConfig
        extends AddressConfigBase<PwmConfig>
        implements PwmConfig {

    // private configuration properties
    protected Integer dutyCycle = null;
    protected Integer dutyCyclePercent = null;
    protected Integer range = null;
    protected Integer frequency = null;
    protected PwmType pwmType = PwmType.SOFTWARE;
    protected Integer shutdownValue = null;
    protected Integer initialValue = null;
    protected List<PwmPreset> presets = new ArrayList<>();

    /**
     * PRIVATE CONSTRUCTOR
     */
    private DefaultPwmConfig(){
        super();
    }

    // private configuration properties
    protected PullResistance pullResistance = PullResistance.OFF;

    /**
     * PRIVATE CONSTRUCTOR
     * @param properties
     */
    protected DefaultPwmConfig(Map<String,String> properties, Collection<PwmPreset> presets){
        this(properties);
        this.presets.addAll(presets);
    }

    /**
     * PRIVATE CONSTRUCTOR
     * @param properties
     */
    protected DefaultPwmConfig(Map<String,String> properties){
        super(properties);

        // define default property values if any are missing (based on the required address value)
        this.id = StringUtil.setIfNullOrEmpty(this.id, "PWM-" + this.address, true);
        this.name = StringUtil.setIfNullOrEmpty(this.name, "PWM-" + this.address, true);
        this.description = StringUtil.setIfNullOrEmpty(this.description, "PWM-" + this.address, true);

        // load optional pwm duty-cycle from properties
        if(properties.containsKey(DUTY_CYCLE_KEY)){
            this.dutyCycle = Integer.parseInt(properties.get(DUTY_CYCLE_KEY));
        }

        // load optional pwm duty-cycle from properties
        if(properties.containsKey(DUTY_CYCLE_PERCENT_KEY)){
            this.dutyCyclePercent = Integer.parseInt(properties.get(DUTY_CYCLE_PERCENT_KEY));
        }

        // load optional pwm frequency from properties
        if(properties.containsKey(FREQUENCY_KEY)){
            this.frequency = Integer.parseInt(properties.get(FREQUENCY_KEY));
        }

        // load optional pwm range from properties
        if(properties.containsKey(RANGE_KEY)){
            this.range = Integer.parseInt(properties.get(RANGE_KEY));
        }

        // load optional pwm type from properties
        if(properties.containsKey(PWM_TYPE_KEY)){
            this.pwmType = PwmType.parse(properties.get(PWM_TYPE_KEY));
        }

        // load initial value property
        if(properties.containsKey(INITIAL_VALUE_KEY)){
            this.initialValue = Integer.parseInt(properties.get(INITIAL_VALUE_KEY));
        }

        // load shutdown value property
        if(properties.containsKey(SHUTDOWN_VALUE_KEY)){
            this.shutdownValue = Integer.parseInt(properties.get(SHUTDOWN_VALUE_KEY));
        }

        // bounds checking
        if(this.dutyCyclePercent != null && this.dutyCyclePercent > 100)
            this.dutyCyclePercent = 100;

        // bounds checking
        if(this.dutyCyclePercent != null && this.dutyCyclePercent < 0)
            this.dutyCyclePercent = 0;
    }

    @Override
    public Integer dutyCycle() {
        return this.dutyCycle;
    }

    @Override
    public Integer range() {
        return this.range;
    }

    @Override
    public Integer frequency() {
        return this.frequency;
    }

    @Override
    public PwmType pwmType() {
        return this.pwmType;
    }

    @Override
    public Integer shutdownValue(){
        return this.shutdownValue;
    }

    @Override
    public Integer dutyCyclePercent(){
        // bounds checking
        if(this.dutyCyclePercent != null && this.dutyCyclePercent > 100)
            this.dutyCyclePercent = 100;

        // bounds checking
        if(this.dutyCyclePercent != null && this.dutyCyclePercent < 0)
            this.dutyCyclePercent = 0;

        return this.dutyCyclePercent;
    }

    @Override
    public PwmConfig shutdownValue(Integer value){
        this.shutdownValue = value;
        return this;
    }

    @Override
    public Integer initialValue() {
        return this.initialValue;
    }

    @Override
    public Collection<PwmPreset> presets(){
        return this.presets;
    }
}
