package com.pi4j.example.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  AnalogInputSyncToOutputExample.java
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
import com.pi4j.io.binding.AnalogOutputBinding;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.util.Console;

/**
 * <p>AnalogInputSyncToOutputExample class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class AnalogInputSyncToOutputExample {

    /** Constant <code>ANALOG_INPUT_PIN=4</code> */
    public static int ANALOG_INPUT_PIN = 4;
    /** Constant <code>ANALOG_OUTPUT_PIN=5</code> */
    public static int ANALOG_OUTPUT_PIN = 5;

    /**
     * <p>Constructor for AnalogInputSyncToOutputExample.</p>
     */
    public AnalogInputSyncToOutputExample() {
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Analog Input Sync To Output Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // create a analog input instance using the default analog input provider
        var input = pi4j.analogInput().create(ANALOG_INPUT_PIN);

        // create a analog output instance using the default analog output provider
        var output = pi4j.aout().create(ANALOG_OUTPUT_PIN);

        // setup a analog output listener to listen for any state changes on the analog output
        // we will just print out the detected state changes
        output.addListener((AnalogChangeListener) event -> {
            console.println(event);
        });

        // bind the analog output state to synchronize with the analog input state
        // this means that anytime a change on the input pin is detected, the analog output
        // pin(s) will be updated to match the state of the analog input
        input.bind(AnalogOutputBinding.newInstance(output));

        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
