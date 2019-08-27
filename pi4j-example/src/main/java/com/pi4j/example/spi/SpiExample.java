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
import com.pi4j.plugin.mock.provider.spi.MockSpiProvider;
import com.pi4j.util.Console;
import com.pi4j.util.StringUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class SpiExample {

    private static int SPI_CHANNEL = 0;

    public static void main(String[] args) throws Exception {

        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        System.setProperty("pi4j.host", "rpi3bp.savage.lan");

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

        // use try-with-resources to auto-close SPI when complete
        try (var spi = pi4j.provider(MockSpiProvider.class).create(config);) {

            // open SPI communications
            spi.open();

            // write data to the SPI channel
            spi.write("THIS IS A TEST");

            // read data back from the SPI channel
            ByteBuffer buffer = spi.readByteBuffer(14);

            console.println("--------------------------------------");
            console.println("--------------------------------------");
            console.println("SPI [WRITE/READ] RETURNED THE FOLLOWING: ");
            console.println("[BYTES]  0x" + StringUtil.toHexString(buffer.array()));
            console.println("[STRING] " + new String(buffer.array()));
            console.println("--------------------------------------");

            // read data back from the SPI channel
            ByteBuffer writeBuffer = ByteBuffer.wrap("Hello World!".getBytes(StandardCharsets.US_ASCII));
            ByteBuffer readBuffer = ByteBuffer.allocate(writeBuffer.capacity());
            spi.transfer(writeBuffer, readBuffer, writeBuffer.capacity());

            console.println("--------------------------------------");
            console.println("SPI [TRANSFER] RETURNED THE FOLLOWING: ");
            console.println("[BYTES]  0x" + StringUtil.toHexString(readBuffer.array()));
            console.println("[STRING] " + new String(readBuffer.array()));
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
