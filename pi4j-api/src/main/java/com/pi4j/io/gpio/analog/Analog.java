package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Analog.java
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


import com.pi4j.io.IO;
import com.pi4j.io.gpio.Gpio;
import com.pi4j.io.gpio.analog.binding.AnalogBinding;

public interface Analog<ANALOG_TYPE extends Analog, CONFIG_TYPE extends AnalogConfig<CONFIG_TYPE>>
        extends Gpio {

    Number value();

    @Override
    CONFIG_TYPE config();

    ANALOG_TYPE addListener(AnalogChangeListener... listener);
    ANALOG_TYPE removeListener(AnalogChangeListener... listener);

    ANALOG_TYPE bind(AnalogBinding... binding);
    ANALOG_TYPE unbind(AnalogBinding ... binding);

    default boolean equals(Number value) {
        return this.value().doubleValue() == value.doubleValue();
    }
    default Number getValue() { return value(); };
}
