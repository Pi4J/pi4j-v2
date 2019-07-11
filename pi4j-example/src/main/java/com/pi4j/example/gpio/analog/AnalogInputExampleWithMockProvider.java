package com.pi4j.example.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE :: Sample Code
 * FILENAME      :  AnalogInputExampleWithMockProvider.java
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
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.provider.mock.io.gpio.analog.MockAnalogInput;
import com.pi4j.provider.mock.io.gpio.analog.MockAnalogInputProvider;
import com.pi4j.util.Console;


public class AnalogInputExampleWithMockProvider {

    public static int ANALOG_INPUT_PIN = 4;

    public AnalogInputExampleWithMockProvider() {
    }

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Analog Input Example With Mock Provider");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // initialize the Pi4J library
        var pi4j = Pi4J.initialize();

        // get the Mock Analog Input provider by ID
        console.println("ATTEMPTING TO GET MOCK ANALOG INPUT PROVIDER FROM Pi4J");
        MockAnalogInputProvider provider = pi4j.providers().get(MockAnalogInputProvider.ID, MockAnalogInputProvider.class);

        // display acquired provider
        console.print("--> ACQUIRED PROVIDER: ");
        console.println(provider.id());
        console.println();

        // create an analog input instance using the mock analog input provider
        console.println("ATTEMPTING TO CREATE A MOCK ANALOG INPUT INSTANCE");
        var input = AnalogInput.create(provider, ANALOG_INPUT_PIN, MockAnalogInput.class);

        // display created instance
        console.print("--> CREATED IO INSTANCE: [");
        console.print(input);
        console.println("]");
        console.println();

        // setup an analog output listener to listen for any value changes on the analog input
        input.addListener((AnalogChangeListener) event -> {
            console.print("--> ");
            console.println(event);
        });

        // lets read the analog input value
        console.println("ATTEMPTING TO READ CURRENT MOCK ANALOG INPUT VALUE");
        console.print("--> CURRENT ANALOG INPUT VALUE IS [");
        console.println(input.value() + "]");
        console.println();

        console.println("ATTEMPTING TO UPDATE MOCK ANALOG INPUT VALUES TO GENERATE CHANGE EVENTS (4)");

        // change mock value of mock analog input so we can see some events firing
        input.mockValue(145)
             .mockValue(99)
             .mockValue(6787)
             .mockValue(345);

        // lets read the analog input value
        console.println();
        console.println("ATTEMPTING TO READ FINAL MOCK ANALOG INPUT VALUE");
        console.print("--> FINAL ANALOG INPUT VALUE IS [");
        console.println(input.value() + "]");
        console.println();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        Pi4J.terminate();
    }
}
