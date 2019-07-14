package com.pi4j.example.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE :: Sample Code
 * FILENAME      :  AnalogInputExampleWithMockProviderUsingDI.java
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
import com.pi4j.annotation.*;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.provider.mock.io.gpio.analog.MockAnalogInput;
import com.pi4j.provider.mock.io.gpio.analog.MockAnalogInputProvider;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;


public class AnalogInputExampleWithMockProviderUsingDI {

    public AnalogInputExampleWithMockProviderUsingDI() {
    }

    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        new AnalogInputExampleWithMockProviderUsingDI.RuntimeContainer().call();
    }

    public static class RuntimeContainer implements Callable<Void> {

        public static final int ANALOG_INPUT_PIN_ID_ADDRESS = 4;
        public static final String ANALOG_INPUT_PIN_ID = "my.analog.input.pin.four";

        // create a digital output instance using the default digital output provider
        @Register(ANALOG_INPUT_PIN_ID)
        @Address(ANALOG_INPUT_PIN_ID_ADDRESS)
        @Name("My Analog Input Pin")
        @Provider(type = MockAnalogInputProvider.class)
        private MockAnalogInput input;

        @Inject
        private Context pi4j;

        // register an analog input listener to listen for any value changes on the analog input pin
        @Register(ANALOG_INPUT_PIN_ID)
        private AnalogChangeListener changeListener = event -> System.out.println("--> " + event);

        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Basic Analog Input Example With Mock Provider");

            // allow for user to exit program using CTRL-C
            console.promptForExit();

            // initialize the Pi4J library then inject this class for dependency injection on annotations
            var pi4j = Pi4J.initialize().inject(this);

            // display acquired provider
            console.print("--> ACQUIRED PROVIDER: ");
            console.println(input.provider());
            console.println();

            // display created instance
            console.print("--> CREATED IO INSTANCE: [");
            console.print(input);
            console.println("]");
            console.println();

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

            return null;
        }
    }
}
