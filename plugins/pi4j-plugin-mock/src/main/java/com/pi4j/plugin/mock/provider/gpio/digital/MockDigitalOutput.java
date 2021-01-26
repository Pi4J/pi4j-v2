package com.pi4j.plugin.mock.provider.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockDigitalOutput.java
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


import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.*;


/**
 * <p>MockDigitalOutput class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MockDigitalOutput extends DigitalOutputBase implements DigitalOutput {
    /**
     * <p>Constructor for MockDigitalOutput.</p>
     *
     * @param provider a {@link com.pi4j.io.gpio.digital.DigitalOutputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalOutputConfig} object.
     */
    public MockDigitalOutput(DigitalOutputProvider provider, DigitalOutputConfig config){
        super(provider, config);
    }

    /**
     * <p>mockState.</p>
     *
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    public MockDigitalOutput mockState(DigitalState state) throws IOException {
        this.state(state);
        return this;
    }

    @Override
    public DigitalOutput on() throws IOException {
        return high();
    }

    @Override
    public DigitalOutput off() throws IOException {
        return low();
    }

}
