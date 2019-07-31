package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  DigitalInputSyncToOutputExample.java
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
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.binding.DigitalBindingSync;
import com.pi4j.util.Console;

public class DigitalInputSyncToOutputExample {

    public static int DIGITAL_INPUT_PIN = 4;
    public static int DIGITAL_OUTPUT_PIN = 5;

    public DigitalInputSyncToOutputExample() {
    }

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Digital Input Sync To Output Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // initialize the Pi4J library
        var pi4j = Pi4J.initialize();

        // create a digital input instance using the default digital input provider
        var input = DigitalInput.create(pi4j, DIGITAL_INPUT_PIN);

        // create a digital output instance using the default digital output provider
        var output = DigitalOutput.create(pi4j, DIGITAL_OUTPUT_PIN);

        // setup a digital output listener to listen for any state changes on the digital output
        // we will just print out the detected state changes
        output.addListener((DigitalChangeListener) event -> {
            console.print("DIGITAL OUTPUT [");
            console.print(event.source().address());
            console.print("] STATE CHANGE: ");
            console.println(event.state());
        });

        // bind the digital output state to synchronize with the digital input state
        // this means that anytime a change on the input pin is detected, the digital output
        // pin(s) will be updated to match the state of the digital input
        input.bind(new DigitalBindingSync(output));

        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
