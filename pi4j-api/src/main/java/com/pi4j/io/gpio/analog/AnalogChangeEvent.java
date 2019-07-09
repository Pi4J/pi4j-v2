package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogChangeEvent.java
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


import com.pi4j.io.gpio.digital.DigitalState;

public class AnalogChangeEvent<ANALOG_TYPE extends Analog> implements AnalogEvent {

    // internal event copy of the changed analog values and analog I/O source
    protected Number oldValue;
    protected Number value;
    protected ANALOG_TYPE source;

    /**
     * Default constructor
     * @param newValue the value changed for this event instance
     */
    public AnalogChangeEvent(ANALOG_TYPE source, Number newValue, Number oldValue){
        this.value = newValue; // cache a copy of the event instance new value
        this.oldValue = oldValue; // cache a copy of the event instance old value
        this.source = source; // cache analog I/O source
    }

    /**
     * The old/prior value change for this event instance
     * @return
     */
    public Number oldValue() {
        return this.oldValue;
    }

    /**
     * The current/new value change for this event instance
     * @return
     */
    public Number value() {
        return this.value;
    }

    @Override
    public ANALOG_TYPE source() {
        return this.source;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("<<ANALOG CHANGE EVENT>> [\"");
        result.append(source().name());
        result.append("\" (#");
        result.append(source().address());
        result.append(")");
        result.append("] VALUE: [");
        result.append(this.oldValue().toString());
        result.append(" -> ");
        result.append(this.value().toString());
        result.append("]");
        return result.toString();
    }
}
