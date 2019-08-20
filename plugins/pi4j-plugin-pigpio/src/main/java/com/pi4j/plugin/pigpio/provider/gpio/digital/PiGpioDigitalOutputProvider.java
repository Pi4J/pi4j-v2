package com.pi4j.plugin.pigpio.provider.gpio.digital;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioDigitalOutputProvider.java
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

import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.PiGpioPlugin;

public interface PiGpioDigitalOutputProvider extends DigitalOutputProvider {
    String NAME = PiGpioPlugin.DIGITAL_OUTPUT_PROVIDER_NAME;
    String ID = PiGpioPlugin.DIGITAL_OUTPUT_PROVIDER_ID;

    static PiGpioDigitalOutputProvider newInstance(PiGpio piGpio) {
        return new PiGpioDigitalOutputProviderImpl(piGpio);
    }
}
