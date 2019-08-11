package com.pi4j.library.pigpio.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioBase.java
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

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioError;
import com.pi4j.library.pigpio.PiGpioPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.pi4j.library.pigpio.PiGpioConst.*;

public abstract class PiGpioBase implements PiGpio {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * --------------------------------------------------------------------------
     * GPIO PINS
     * --------------------------------------------------------------------------
     * A Broadcom numbered GPIO, in the range 0-53.
     *
     * There are 54 General Purpose Input Outputs (GPIO) named GPIO0 through GPIO53.
     *
     * They are split into two banks. Bank 1 consists of GPIO0 through GPIO31.
     * Bank 2 consists of GPIO32 through GPIO53.
     *
     * All the GPIO which are safe for the user to read and write are in bank 1.
     * Not all GPIO in bank 1 are safe though. Type 1 boards have 17 safe GPIO.
     * Type 2 boards have 21. Type 3 boards have 26.
     *
     * See gpioHardwareRevision.
     *
     * The user GPIO are marked with an X in the following table.
     *
     *           0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15
     * Type 1    X  X  -  -  X  -  -  X  X  X  X  X  -  -  X  X
     * Type 2    -  -  X  X  X  -  -  X  X  X  X  X  -  -  X  X
     * Type 3          X  X  X  X  X  X  X  X  X  X  X  X  X  X
     *
     *          16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
     * Type 1    -  X  X  -  -  X  X  X  X  X  -  -  -  -  -  -
     * Type 2    -  X  X  -  -  -  X  X  X  X  -  X  X  X  X  X
     * Type 3    X  X  X  X  X  X  X  X  X  X  X  X  -  -  -  -
     */

    protected void validateUserPin(int pin) throws IllegalArgumentException {
        validatePin(pin, true);
    }

    protected void validatePin(int pin) throws IllegalArgumentException {
        validatePin(pin, false);
    }

    protected void validatePin(int pin, boolean userPin) throws IllegalArgumentException {
        int min = PI_MIN_GPIO;
        int max = ((userPin ? PI_MAX_USER_GPIO : PI_MAX_GPIO));
        if(pin < min || pin > max)
            throw new IllegalArgumentException("Invalid PIN number: " + pin + "; (supported pins: " + min + "-" + max + ")");
    }

    protected void validateDutyCycle(int dutyCycle) throws IllegalArgumentException{
        int min = 0;
        int max = PI_MAX_DUTYCYCLE_RANGE;
        if(dutyCycle < min || dutyCycle > max)
            throw new IllegalArgumentException("Invalid Duty Cycle: " + dutyCycle +
                    "; (supported duty-cycle: " + min + " - " + max + ")");
    }

    protected void validateDutyCycleRange(int range) throws IllegalArgumentException{
        int min = PI_MIN_DUTYCYCLE_RANGE;
        int max = PI_MAX_DUTYCYCLE_RANGE;
        if(range < min || range > max)
            throw new IllegalArgumentException("Invalid Duty Cycle Range: " + range +
                    "; (supported range: " + min + " - " + max + ")");
    }

    protected void validateDelayMicroseconds(int micros){
        int min = 0;
        int max = PI_MAX_MICS_DELAY;
        if(micros < min || micros > max)
            throw new IllegalArgumentException("Invalid microseconds delay: " + micros +
                    "; (supported range: " + min + " - " + max + ")");
    }

    protected void validateDelayMilliseconds(int millis){
        int min = 0;
        int max = PI_MAX_MILS_DELAY;
        if(millis < min || millis > max)
            throw new IllegalArgumentException("Invalid milliseconds delay: " + millis +
                    "; (supported range: " + min + " - " + max + ")");
    }

    protected void validateResult(PiGpioPacket result) throws IOException{
        validateResult(result.result());
    }

    protected void validateResult(long value) throws IOException {
        if(value < 0) {
            PiGpioError err = PiGpioError.from(value);
            throw new IOException("PIGPIO ERROR: " + err.name() + "; " + err.message());
        }
    }


}
