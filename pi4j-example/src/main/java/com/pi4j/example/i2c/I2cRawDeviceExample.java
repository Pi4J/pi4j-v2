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
import com.pi4j.util.Console;

import java.nio.ByteBuffer;

public class I2cRawDeviceExample {

    public I2cRawDeviceExample() {
    }

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic I2C Raw Device Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        var i2c = pi4j.i2c().create(null);

        // write a single byte to the raw I2C device (not to a register)
        byte b = 0x0D;
        i2c.write(b);

        // write an array of data bytes to the raw I2C device (not to a register)
        byte[] array = new byte[] { 0,1,2,3,4,5,6,7,8,9 };
        i2c.write(array);

        // write a buffer of data bytes to the raw I2C device (not to a register)
        ByteBuffer buffer = ByteBuffer.allocate(10);
        i2c.write(buffer);

        // write a string of data to the raw I2C device (not to a register)
        i2c.write("This is a test");


//        // create a digital input instance using the default digital input provider
//        // we will use the PULL_DOWN argument to set the pin pull-down resistance on this GPIO pin
//        DigitalInputConfigBuilder builder = DigitalInputConfigBuilder.newInstance();
//        builder.id("my-digital-input")
//                .address(DIGITAL_INPUT_PIN)
//                .pull(PullResistance.PULL_DOWN);
//        var input = pi4j.din().create(builder.build());
//
//        // setup a digital output listener to listen for any state changes on the digital input
//        input.addListener((DigitalChangeListener) event -> {
//            console.println(event);
//        });
//
//        // lets read the analog output state
//        console.print("THE STARTING DIGITAL INPUT STATE IS [");
//        console.println(input.state() + "]");
//
//        console.println("CHANGE INPUT STATES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");

        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
