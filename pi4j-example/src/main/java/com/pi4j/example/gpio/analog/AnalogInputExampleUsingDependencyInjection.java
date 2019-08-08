package com.pi4j.example.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  AnalogInputExampleUsingDependencyInjection.java
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
import com.pi4j.io.gpio.analog.AnalogChangeEvent;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;

public class AnalogInputExampleUsingDependencyInjection {

    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        new AnalogInputExampleUsingDependencyInjection.RuntimeContainer().call();
    }

    public static class RuntimeContainer implements Callable<Void> {

        public static final int ANALOG_INPUT_PIN_ID_ADDRESS = 4;
        public static final String ANALOG_INPUT_PIN_ID = "my.analog.input.pin.four";

        // create a digital output instance using the default digital output provider
        @Register(ANALOG_INPUT_PIN_ID)
        @Address(ANALOG_INPUT_PIN_ID_ADDRESS)
        @Name("My Analog Input Pin")
        private AnalogInput input;

        @Inject
        private Context pi4j;

        // register an analog input listener to listen for any value changes on the analog input pin
        @Register(ANALOG_INPUT_PIN_ID)
        private AnalogChangeListener changeListener = event -> System.out.println(" (LISTENER #1) :: " + event);

        // setup an analog input event listener to listen for any value changes on the analog input
        // using a custom method with a single event parameter
        @OnEvent(ANALOG_INPUT_PIN_ID)
        private void onAnalogInputChange(AnalogChangeEvent event){
            System.out.println(" (LISTENER #2) :: " + event);
        }

        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Basic Analog Input Example Using Dependency Injection");

            // allow for user to exit program using CTRL-C
            console.promptForExit();

            // Initialize Pi4J with an auto context
            // An auto context includes AUTO-DETECT BINDINGS enabled
            // which will load all detected Pi4J extension libraries
            // (Platforms and Providers) in the class path
            // ...
            // Also, inject this class instance into the Pi4J context
            // for annotation processing and dependency injection
            Pi4J.newAutoContext().inject(this);

            // lets read the analog output state
            console.print("THE STARTING ANALOG INPUT [" + input + "] VALUE IS [");
            console.println(input.value() + "]");

            console.println("CHANGE INPUT VALUES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");

            // we are done
            return null;
        }
    }
}
