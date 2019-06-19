package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Digital.java
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

import com.pi4j.context.Context;
import com.pi4j.io.IO;
import com.pi4j.io.gpio.digital.binding.DigitalBinding;
import com.pi4j.util.Descriptor;

public interface Digital<DIGITAL_TYPE extends Digital, CONFIG_TYPE extends DigitalConfig<CONFIG_TYPE>> extends IO {

    DigitalState state();
    CONFIG_TYPE config();

    DIGITAL_TYPE addListener(DigitalChangeListener... listener);
    DIGITAL_TYPE removeListener(DigitalChangeListener... listener);

    DIGITAL_TYPE bind(DigitalBinding... binding);
    DIGITAL_TYPE unbind(DigitalBinding ... binding);
    DIGITAL_TYPE terminate(Context context);

    default int address() { return config().address(); }
    default int getAddress() { return address(); };
    default boolean equals(DigitalState state){
        return this.state().equals(state);
    }
    default boolean equals(Number state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(boolean state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(byte state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(short state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(int state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(long state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(float state){
        return equals(DigitalState.getState(state));
    }
    default boolean equals(double state){
        return equals(DigitalState.getState(state));
    }
    default boolean isHigh(){
        return this.state().isHigh();
    }
    default boolean isLow(){
        return this.state().isLow();
    }

    @Override
    default void describe(Descriptor descriptor) {
        descriptor.add("[#" + address() +"] <" + getClass().getName() + ">");
    }
}
