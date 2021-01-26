package com.pi4j.plugin.mock.provider.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockAnalogInput.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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


import com.pi4j.io.gpio.analog.*;

/**
 * <p>MockAnalogInput class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MockAnalogInput extends AnalogInputBase implements AnalogInput {

    private Integer value = 0;

    /**
     * <p>Constructor for MockAnalogInput.</p>
     *
     * @param provider a {@link com.pi4j.io.gpio.analog.AnalogInputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.analog.AnalogInputConfig} object.
     */
    public MockAnalogInput(AnalogInputProvider provider, AnalogInputConfig config){
        super(provider, config);
    }

    /** {@inheritDoc} */
    @Override
    public Integer value() {
        return this.value;
    }

    /**
     * <p>mockValue.</p>
     *
     * @param value a {@link java.lang.Integer} object.
     * @return a {@link com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogInput} object.
     */
    public MockAnalogInput mockValue(Integer value){

        // check to see of there is a value change; if there is then we need
        // to update the internal value variable and dispatch the change event
        if(this.value().intValue() != value.intValue()) {

            // cache copy of old value for change event
            Integer oldValue = this.value();

            // update current/new value
            this.value = value;

            // dispatch value change event
            this.dispatch(new AnalogValueChangeEvent(this, this.value(), oldValue));
        }
        return this;
    }
}
