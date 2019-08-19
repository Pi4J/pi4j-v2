package com.pi4j.plugin.pigpio.provider.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioPwm.java
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

public class PiGpioPwm extends PiGpioPwmBase implements Pwm {


    public PiGpioPwm(PiGpio piGpio, PwmProvider provider, PwmConfig config) throws IOException {
        super(piGpio, provider, config);

        // set pin mode to output
        piGpio.gpioSetMode(this.address(), PiGpioMode.OUTPUT);

        // get existing range and and duty-cycle
        this.range = piGpio.gpioGetPWMrange(this.address());
        this.frequency = piGpio.gpioGetPWMfrequency(this.address());

        // get updated duty-cycle value from PiGpio
        this.dutyCycle = this.range / 2;  // default duty-cycle is 50% of total range

        // determine existing state
        if(this.dutyCycle > 0){
            this.onState = true;  // update tracking state
        }
    }

    public Pwm on() throws IOException{

        // set PWM range
        piGpio.gpioSetPWMrange(this.address(), range);
        this.range = piGpio.gpioGetPWMrange(this.address());

        // set PWM frequency
        piGpio.gpioSetPWMfrequency(this.address(), frequency);

        // set PWM duty-cycle and enable PWM
        piGpio.gpioPWM(this.address(), this.dutyCycle);

        // get updated duty-cycle value from PiGpio
        this.dutyCycle = piGpio.gpioGetPWMdutycycle(this.address());

        // determine existing state
        if(this.dutyCycle > 0){
            this.onState = true;  // update tracking state
        }

        return this;
    }

    public Pwm off() throws IOException{

        // set PWM duty-cycle and enable PWM
        piGpio.gpioPWM(this.address(), 0);

        // update tracking state
        this.onState = false;

        return this;
    }
}
