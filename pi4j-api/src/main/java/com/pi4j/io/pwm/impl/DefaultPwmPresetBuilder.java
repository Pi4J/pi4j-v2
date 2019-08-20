package com.pi4j.io.pwm.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultPwmPresetBuilder.java
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

import com.pi4j.io.pwm.PwmPreset;
import com.pi4j.io.pwm.PwmPresetBuilder;

public class DefaultPwmPresetBuilder implements PwmPresetBuilder{
    protected Integer dutyCycle = null;
    protected Integer frequency = null;
    protected final String name;

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultPwmPresetBuilder(String name){
        super(); this.name = name;
    }

    public static PwmPresetBuilder newInstance(String name) {
        return new DefaultPwmPresetBuilder(name);
    }

    @Override
    public PwmPresetBuilder frequency(Integer frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public PwmPresetBuilder dutyCycle(Integer dutyCycle) {
        this.dutyCycle = dutyCycle;
        return this;
    }
    @Override
    public PwmPreset build() {
        return new DefaultPwmPreset(this.name, this.dutyCycle, this.frequency);
    }
}
