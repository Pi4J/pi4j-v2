package com.pi4j.provider.mock.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PROVIDER :: Mock Provider
 * FILENAME      :  MockAnalogInput.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */


import com.pi4j.io.gpio.analog.AnalogChangeEvent;
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.io.gpio.analog.AnalogInputBase;
import com.pi4j.io.gpio.analog.AnalogInputConfig;

public class MockAnalogInput extends AnalogInputBase implements AnalogInput {

    private Number value = 0;

    public MockAnalogInput(AnalogInputConfig config){
        super(config);
    }

    @Override
    public Number value() {
        return this.value;
    }

    public MockAnalogInput mockValue(Number value){

        // check to see of there is a value change; if there is then we need
        // to update the internal value variable and dispatch the change event
        if(this.value().doubleValue() != value.doubleValue()) {

            // cache copy of old value for change event
            Number oldValue = this.value();

            // update current/new value
            this.value = value;

            // dispatch value change event
            this.dispatch(new AnalogChangeEvent(this, this.value(), oldValue));
        }
        return this;
    }
}
