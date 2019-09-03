package com.pi4j.example.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  SpiExample.java
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
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.util.Console;
import com.pi4j.util.StringUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * <p>SpiExample class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class SpiExample {

    private static int SPI_CHANNEL = 0;

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
        System.setProperty("pi4j.pigpio.remote", "true");

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic SPI Communications Example");

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // create SPI config
        var config  = Spi.newConfigBuilder()
                .id("my-spi-device")
                .name("My SPI Device")
                .address(SPI_CHANNEL)
                .baud(Spi.DEFAULT_BAUD)
                .build();

        // get a SPI I/O provider from the Pi4J context
        SpiProvider spiProvider = pi4j.provider("pigpio-spi");

        // use try-with-resources to auto-close SPI when complete
        try (var spi = spiProvider.create(config);) {
            String data = "THIS IS A TEST";

            // open SPI communications
            spi.open();

            // write data to the SPI channel
            spi.write(data);

            // take a breath to allow time for the SPI
            // data to get updated in the SPI device
            Thread.sleep(100);

            // read data back from the SPI channel
            ByteBuffer buffer = spi.readByteBuffer(data.length());

            console.println("--------------------------------------");
            console.println("--------------------------------------");
            console.println("SPI [WRITE] :");
            console.println("  [BYTES]  0x" + StringUtil.toHexString(data));
            console.println("  [STRING] " + data);
            console.println("SPI [READ] :");
            console.println("  [BYTES]  0x" + StringUtil.toHexString(buffer.array()));
            console.println("  [STRING] " + new String(buffer.array()));
            console.println("--------------------------------------");

            // read data back from the SPI channel
            ByteBuffer writeBuffer = ByteBuffer.wrap("Hello World!".getBytes(StandardCharsets.US_ASCII));
            ByteBuffer readBuffer = ByteBuffer.allocate(writeBuffer.capacity());
            spi.transfer(writeBuffer, readBuffer, writeBuffer.capacity());

            console.println("--------------------------------------");
            console.println("SPI [TRANSFER] :");
            console.println("  [BYTES]  0x" + StringUtil.toHexString(readBuffer.array()));
            console.println("  [STRING] " + new String(readBuffer.array()));
            console.println("--------------------------------------");
            console.println("--------------------------------------");

            // SPI channel will be closed when this block goes
            // out of scope by the AutoCloseable interface on SPI
        }

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
