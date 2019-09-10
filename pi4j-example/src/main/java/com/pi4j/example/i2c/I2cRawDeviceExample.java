package com.pi4j.example.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  I2cRawDeviceExample.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.util.Console;
import com.pi4j.util.StringUtil;

import java.nio.ByteBuffer;

/**
 * <p>I2cRawDeviceExample class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class I2cRawDeviceExample {

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        //System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        System.setProperty("pi4j.host", "rpi3bp.savage.lan");

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic I2C Raw Device Example");

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // create I2C config
        var config  = I2C.newConfigBuilder(pi4j)
                .id("my-i2c-bus")
                .name("My I2C Bus")
                .bus(I2C_BUS)
                .device(I2C_DEVICE)
                .build();

        // get a serial I/O provider from the Pi4J context
        I2CProvider i2CProvider = pi4j.provider("pigpio-i2c");

        // use try-with-resources to auto-close I2C when complete
        try (var i2c = i2CProvider.create(config);) {

            // --> write a single (8-bit) byte value to the raw I2C device (not to a register)
            i2c.write(0x0D);

            // <-- read a single (8-bit) byte value from the raw I2C device (not to a register)
            byte readByte = i2c.readByte();

            console.println("I2C READ BYTE: 0x" + Integer.toHexString(readByte));

            // --> write an array of data bytes to the raw I2C device (not to a register)
            i2c.write(new byte[] { 0,1,2,3,4,5,6,7,8,9 });

            // <-- read a byte array of specified length from the raw I2C device (not to a register)
            byte[] readArray = i2c.readNBytes(10);

            console.println("I2C READ ARRAY: 0x" + StringUtil.toHexString(readArray));

            // --> write a buffer of data bytes to the raw I2C device (not to a register)
            ByteBuffer buffer = ByteBuffer.allocate(10);
            i2c.write(buffer);

            // <-- read ByteBuffer of specified length from the raw I2C device (not to a register)
            ByteBuffer readBuffer = i2c.readByteBuffer(10);

            console.println("I2C READ BUFFER: 0x" + StringUtil.toHexString(readBuffer));

            // --> write a string of data to the raw I2C device (not to a register)
            i2c.write("This is a test");

            // <-- read string of data of specified length from the raw I2C device (not to a register)
            String readString = i2c.readString(14);

            console.println("I2C READ STRING: " + readString);
        }

        // shutdown Pi4J
        console.println();
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
