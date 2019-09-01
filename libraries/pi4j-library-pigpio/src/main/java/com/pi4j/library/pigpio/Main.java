package com.pi4j.library.pigpio;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  Main.java
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

import com.pi4j.library.pigpio.internal.PIGPIO;

import static com.pi4j.library.pigpio.PiGpioConst.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("PIGPIO VERSION   : " + PIGPIO.gpioVersion());
        System.out.println("PIGPIO HARDWARE  : " + PIGPIO.gpioHardwareRevision());
        System.out.println("PIGPIO INITIALIZE: " + PIGPIO.gpioInitialise());
        System.out.println("PIGPIO SET MODE  : " + PIGPIO.gpioSetMode(2, PI_OUTPUT));
        //System.out.println("PIGPIO READ 0    : " + PIGPIO.gpioRead(2));
        //System.out.println("PIGPIO WRITE 0-1 : " + PIGPIO.gpioWrite(2, PI_HIGH));
        //System.out.println("PIGPIO READ 0    : " + PIGPIO.gpioRead(2));

        PIGPIO.gpioSetPWMfrequency(2, 5000);
        PIGPIO.gpioPWM(2, 128);

//        while(System.in.available() == 0){
//            PIGPIO.gpioWrite(2, PI_HIGH);
//            PIGPIO.gpioWrite(2, PI_LOW);
//        }

        System.in.read();
        System.out.println("PIGPIO WRITE 0-1 : " + PIGPIO.gpioWrite(2, PI_LOW));
        PIGPIO.gpioTerminate();
        System.out.println("PIGPIO TERMINATED");
    }
}
