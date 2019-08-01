package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  DigitalInputExample.java
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
import com.pi4j.io.gpio.digital.DigitalChangeListener;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputBuilder;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.Console;

public class DigitalInputExample {

    public static int DIGITAL_INPUT_PIN = 4;

    public DigitalInputExample() {
    }

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Digital Input Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // initialize the Pi4J runtime context
        var pi4j = Pi4J.newDefaultContext();

        // create a digital input instance using the default digital input provider
        // we will use the PULL_DOWN argument to set the pin pull-down resistance on this GPIO pin
        DigitalInputBuilder builder = DigitalInput.builder();
        builder.id("my-digital-input")
                .address(DIGITAL_INPUT_PIN)
                .pull(PullResistance.PULL_DOWN);
        var input = DigitalInput.create(pi4j, builder.build());

        // setup a digital output listener to listen for any state changes on the digital input
        input.addListener((DigitalChangeListener) event -> {
            console.println(event);
        });

        // lets read the analog output state
        console.print("THE STARTING DIGITAL INPUT STATE IS [");
        console.println(input.state() + "]");

        console.println("CHANGE INPUT STATES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");

        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
