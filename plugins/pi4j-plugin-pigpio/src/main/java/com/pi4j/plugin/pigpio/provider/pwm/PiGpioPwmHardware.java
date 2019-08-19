package com.pi4j.plugin.pigpio.provider.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioPwmHardware.java
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

import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioMode;

import java.io.IOException;

public class PiGpioPwmHardware extends PiGpioPwmBase implements Pwm {

    public PiGpioPwmHardware(PiGpio piGpio, PwmProvider provider, PwmConfig config) throws IOException {
        super(piGpio, provider, config);

//        12  PWM channel 0  All models but A and B
//        13  PWM channel 1  All models but A and B
//        18  PWM channel 0  All models
//        19  PWM channel 1  All models but A and B
//
//        40  PWM channel 0  Compute module only
//        41  PWM channel 1  Compute module only
//        45  PWM channel 1  Compute module only
//        52  PWM channel 0  Compute module only
//        53  PWM channel 1  Compute module only

        // TODO :: SET PIN MODES FOR HARDWARE PWM
        if(this.address() == 12 || this.address() == 13 || this.address() == 41 || this.address() == 42 || this.address() == 45) {
            piGpio.gpioSetMode(this.address(), PiGpioMode.ALT0);
        }
        else if(this.address() == 18 || this.address() == 19) {
            piGpio.gpioSetMode(this.address(), PiGpioMode.ALT0);
        }

        // get initial values
        this.range = 1000000;  // fixed range for hardware PWM
        this.frequency = piGpio.gpioGetPWMfrequency(this.address());
        this.dutyCycle = 0;
    }

    @Override
    public void setDutyCycle(int dutyCycle) throws IOException {
            piGpio.gpioHardwarePWM(this.address(), this.frequency, dutyCycle);
        this.dutyCycle = dutyCycle;
    }

    @Override
    public void setFrequency(int frequency) throws IOException {
        piGpio.gpioHardwarePWM(this.address(), frequency, this.dutyCycle);
        this.frequency = frequency;
    }

    @Override
    public void setRange(int range) throws IOException {
        // NOT SUPPORTED
        throw new UnsupportedOperationException("Hardware PWM does not support custom ranges; rage will always be 0-1M");
    }
}
