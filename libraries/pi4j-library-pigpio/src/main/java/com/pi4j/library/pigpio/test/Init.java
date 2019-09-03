package com.pi4j.library.pigpio.test;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  Init.java
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
import org.slf4j.event.Level;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class Init {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link String} objects.
     * @throws Exception if any.
     */
    public static void main(String[] args) throws Exception {
        String loglevel = "INFO";
        if(args != null && args.length > 0){
            Level lvl = Level.valueOf(args[0].toUpperCase());
            loglevel = lvl.name();
        }
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", loglevel);

        // create native PiGpio instance
        PiGpio piGpio = PiGpio.newNativeInstance();
        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("Pi4J Library :: PIGPIO (Native) JNI Wrapper Library");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        int init = piGpio.gpioInitialise();
        if(init < 0){
            System.err.println("ERROR; PIGPIO INIT FAILED; ERROR CODE: " + init);
        } else {
            System.out.println("PIGPIO INITIALIZED SUCCESSFULLY");
        }
        System.out.println("PIGPIO VERSION   : " + piGpio.gpioVersion());
        System.out.println("PIGPIO HARDWARE  : " + Long.toHexString(piGpio.gpioHardwareRevision()));
        piGpio.gpioTerminate();
        System.out.println("PIGPIO TERMINATED");
        System.out.println("-----------------------------------------------------");
        System.out.println();
        System.out.println();
    }
}
