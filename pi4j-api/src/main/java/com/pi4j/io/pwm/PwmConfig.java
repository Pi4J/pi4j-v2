package com.pi4j.io.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  PwmConfig.java
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

import com.pi4j.config.AddressConfig;
import com.pi4j.io.gpio.GpioConfig;

import java.util.Collection;

public interface PwmConfig extends GpioConfig<PwmConfig>, AddressConfig<PwmConfig> {

    String PWM_TYPE_KEY = "pwm-type";
    String FREQUENCY_KEY = "frequency";
    String RANGE_KEY = "range";
    String DUTY_CYCLE_KEY = "duty-cycle";
    String DUTY_CYCLE_PERCENT_KEY = "duty-cycle-percent";
    String SHUTDOWN_VALUE_KEY = "shutdown";
    String INITIAL_VALUE_KEY = "initial";
    String PRESET_KEY = "applyPreset";

    Integer dutyCycle();
    default Integer getDutyCycle() {
        return dutyCycle();
    }

    Integer dutyCyclePercent();
    default Integer getDutyCyclePercent() {
        return dutyCyclePercent();
    }

    Integer range();
    default Integer getRange() {
        return range();
    }

    Integer frequency();
    default Integer getFrequency() {
        return frequency();
    }

    PwmType pwmType();
    default PwmType getPwmType(){
        return pwmType();
    }

    Integer shutdownValue();
    PwmConfig shutdownValue(Integer value);
    default Integer getShutdownValue(){
        return shutdownValue();
    }
    default void setShutdownValue(Integer value){
        this.shutdownValue(value);
    }

    Integer initialValue();
    default Integer getInitialValue(){
        return initialValue();
    }

    Collection<PwmPreset> presets();
    default Collection<PwmPreset> getPresets(){
        return presets();
    }

}
