package com.pi4j.plugin.pigpio.provider.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioPwmSoftware.java
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

public class PiGpioPwmSoftware extends PiGpioPwmBase implements Pwm {


    public PiGpioPwmSoftware(PiGpio piGpio, PwmProvider provider, PwmConfig config) throws IOException {
        super(piGpio, provider, config);

        // set pin mode to output
        piGpio.gpioSetMode(this.address(), PiGpioMode.OUTPUT);

        // get current frequency from config or from actual PWM pin
        if(config.frequency() != null){
            this.frequency = config.frequency();
        } else {
            this.frequency = piGpio.gpioGetPWMfrequency(this.address());
        }

        // get current range from config or from actual PWM pin
        if(config.range() != null){
            this.range = config.range();
        } else {
            this.range = piGpio.gpioGetPWMrange(this.address());
        }

        // get current duty-cycle from config or set to default 50%
        if(config.dutyCycle() != null){
            this.dutyCycle = config.dutyCycle();
        }
        else if(config.dutyCyclePercent() != null){
            dutyCyclePercent(config.dutyCyclePercent());
        }
        else {
            // get updated duty-cycle value from PiGpio
            this.dutyCycle = this.range / 2;  // default duty-cycle is 50% of total range
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

        // update tracking state
        this.onState = (this.frequency > 0 && this.dutyCycle > 0);

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
