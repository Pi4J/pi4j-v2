package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE :: Sample Code
 * FILENAME      :  DigitalInputExampleWithMockProvider.java
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
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.provider.mock.io.gpio.digital.MockDigitalInput;
import com.pi4j.provider.mock.io.gpio.digital.MockDigitalInputProvider;
import com.pi4j.util.Console;


public class DigitalInputExampleWithMockProvider {

    public static int DIGITAL_INPUT_PIN = 4;

    public DigitalInputExampleWithMockProvider() {
    }

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Digital Input Example With Mock Provider");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // initialize the Pi4J library
        var pi4j = Pi4J.initialize();

        // get the Mock Digital Input provider by ID
        console.println("ATTEMPTING TO GET MOCK DIGITAL INPUT PROVIDER FROM Pi4J");
        var provider = pi4j.providers().get(MockDigitalInputProvider.ID, MockDigitalInputProvider.class);

        // display acquired provider
        console.println("--> ACQUIRED PROVIDER: ");
        console.print("--> ");
        console.print(provider.id());
        console.println();

        // create a digital input instance using the default digital input provider
        console.println("ATTEMPTING TO CREATE A MOCK DIGITAL INPUT INSTANCE");
        var input = DigitalInput.instance(provider, DIGITAL_INPUT_PIN, MockDigitalInput.class);

        // display created instance
        console.println("--> CREATED IO INSTANCE: ");
        console.print("--> ");
        console.print(input.id());
        console.println();

        // setup a digital output listener to listen for any state changes on the digital input
        input.addListener((DigitalChangeListener) event -> {
            console.print("DIGITAL INPUT [");
            console.print(event.source().address());
            console.print("] STATE CHANGE: ");
            console.println(event.state());
        });

        // lets read the digital output state
        console.print("CURRENT DIGITAL INPUT STATE IS [");
        console.println(input.state() + "]");

        // change mock state of mock digital input so we can see some events firing
        input.mockState(DigitalState.HIGH)
             .mockState(DigitalState.LOW)
             .mockState(DigitalState.HIGH)
             .mockState(DigitalState.LOW);

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        Pi4J.terminate();
    }
}
