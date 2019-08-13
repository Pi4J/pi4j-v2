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

public class Main {


    public static void main(String[] args) throws Exception {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        PiGpio pig = PiGpio.newSocketInstance("rpi3bp");

        pig.gpioSetMode(4, PiGpioMode.OUTPUT);

        for(int x = 0; x < 100; x++) {
            pig.gpioWrite(4, PiGpioState.LOW);
            //pig.gpioWrite(3, PiGpioState.LOW);
            pig.gpioWrite(4, PiGpioState.HIGH);
            //pig.gpioWrite(3, PiGpioState.HIGH);
        }

//        pig.gpioPWM(2, 50);
//        Thread.sleep(1000);;
//        pig.gpioPWM(2, 0);


//        pig.gpioSetMode(4, PiGpioMode.INPUT);
//        pig.gpioSetPullUpDown(4, PiGpioPud.DOWN);
//
//        for(int x = 0; x < 100000; x++) {
//            var value = pig.gpioRead(4);
//            System.out.println(value);
//        }
//


//        pig.initialize();
//
//        System.out.println("---------------------------------------------------------------------");
//        System.out.println("Raspberry Pi - Hardware Revision  : " + pig.gpioHardwareRevisionString());
//        System.out.println("Raspberry Pi - PIGPIO Lib Version : " + pig.gpioVersion());
//        System.out.println("---------------------------------------------------------------------");
//
//        pig.gpioSetMode(28, PiGpioMode.OUTPUT);
//        pig.gpioWrite(28, PiGpioState.HIGH);
//
//        for (int p = 0; p <= 31; p++) {
//            System.out.println("  GPIO " + p + "; MODE=" + pig.gpioGetMode(p) + "; STATE=" + pig.gpioRead(p));
//        }

        pig.gpioTick();

    }
}
