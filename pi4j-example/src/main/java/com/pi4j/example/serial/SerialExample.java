package com.pi4j.example.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  SerialExample.java
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
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.util.Console;
import com.pi4j.util.StringUtil;

import java.nio.ByteBuffer;

/**
 * <p>SerialExample class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class SerialExample {

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        System.setProperty("pi4j.host", "rpi3bp.savage.lan");
        System.setProperty("pi4j.remote", "true");

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Serial Communications Example");

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // create SERIAL config
        var config  = Serial.newConfigBuilder(pi4j)
                .id("my-serial-port")
                .name("My Serial Port")
                .device("/dev/ttyS0")
                .use_9600_N81()
                .build();

        // get a serial I/O provider from the Pi4J context
        SerialProvider serialProvider = pi4j.provider("pigpio-serial");

        // use try-with-resources to auto-close SERIAL when complete
        try (var serial = serialProvider.create(config);) {
            String data = "THIS IS A TEST";

            // open serial port communications
            serial.open();

            // write data to the SPI channel
            serial.write(data);

            // take a breath to allow time for the serial data
            // to get updated in the serial receive buffer
            Thread.sleep(100);

            // read data back from the SPI channel
            ByteBuffer buffer = serial.readByteBuffer(data.length());

            console.println("--------------------------------------");
            console.println("--------------------------------------");
            console.println("SERIAL [WRITE] :");
            console.println("  [BYTES]  0x" + StringUtil.toHexString(data));
            console.println("  [STRING] " + data);
            console.println("SERIAL [READ] :");
            console.println("  [BYTES]  0x" + StringUtil.toHexString(buffer.array()));
            console.println("  [STRING] " + new String(buffer.array()));
            console.println("--------------------------------------");
            console.println("--------------------------------------");

            // serial port will be closed when this block goes
            // out of scope by the AutoCloseable interface on Serial
        }

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
