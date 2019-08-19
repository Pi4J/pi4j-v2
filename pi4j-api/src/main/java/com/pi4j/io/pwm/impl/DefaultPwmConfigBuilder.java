package com.pi4j.io.pwm.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultPwmConfigBuilder.java
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
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmConfigBuilder;
import com.pi4j.io.pwm.PwmType;

public class DefaultPwmConfigBuilder
        extends AddressConfigBuilderBase<PwmConfigBuilder, PwmConfig>
        implements PwmConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultPwmConfigBuilder(){
        super();
    }

    public static PwmConfigBuilder newInstance() {
        return new DefaultPwmConfigBuilder();
    }

    @Override
    public PwmConfigBuilder range(Integer range) {
        this.properties.put(PwmConfig.RANGE_KEY, range.toString());
        return this;
    }

    @Override
    public PwmConfigBuilder frequency(Integer frequency) {
        this.properties.put(PwmConfig.FREQUENCY_KEY, frequency.toString());
        return this;
    }

    @Override
    public PwmConfigBuilder dutyCycle(Integer dutyCycle) {
        this.properties.put(PwmConfig.DUTY_CYCLE_KEY, dutyCycle.toString());
        return this;
    }

    @Override
    public PwmConfigBuilder dutyCyclePercent(Integer percent) {

        // bounds checking
        if(percent != null && percent > 100)
            percent = 100;

        // bounds checking
        if(percent != null && percent < 0)
            percent = 0;

        this.properties.put(PwmConfig.DUTY_CYCLE_PERCENT_KEY, percent.toString());
        return this;
    }

    @Override
    public PwmConfigBuilder pwmType(PwmType pwmType) {
        this.properties.put(PwmConfig.PWM_TYPE_KEY, pwmType.toString());
        return this;
    }

    @Override
    public PwmConfigBuilder shutdown(Integer value) {
        this.properties.put(PwmConfig.SHUTDOWN_VALUE_KEY, value.toString());
        return this;
    }

    @Override
    public PwmConfigBuilder initial(Integer value) {
        this.properties.put(PwmConfig.INITIAL_VALUE_KEY, value.toString());
        return this;
    }

    @Override
    public PwmConfig build() {
        PwmConfig config = new DefaultPwmConfig(properties);
        return config;
    }
}
