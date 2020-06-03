package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  DigitalInputExampleUsingDependencyInjection.java
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
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;

/**
 * <p>DigitalInputExampleUsingDependencyInjection class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DigitalInputExampleUsingDependencyInjection {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // configure logging output
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "TRACE");

        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        System.setProperty("pi4j.host", "rpi3bp.savage.lan");

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        new DigitalInputExampleUsingDependencyInjection.RuntimeContainer().call();
    }

    public static class RuntimeContainer implements Callable<Void> {

        public static final int DIGITAL_INPUT_PIN_ID_ADDRESS = 0;
        public static final String DIGITAL_INPUT_PIN_ID = "my.digital.input.pin.zero";

        // create & register a digital input instance using annotations and dependency injection
        @Register(DIGITAL_INPUT_PIN_ID)
        @Address(DIGITAL_INPUT_PIN_ID_ADDRESS)
        @Name("My Digital Input Pin")
        @Debounce(300000) // microseconds
        @WithProvider(type=PiGpioDigitalInputProvider.class)
        private DigitalInput input;

        @Inject
        private Context pi4j;

        // register a digital input listener to listen for any value changes on the digital input pin
        @Register(DIGITAL_INPUT_PIN_ID)
        private DigitalStateChangeListener changeListener = event -> System.out.println(" (LISTENER #1) :: " + event);

        // setup a digital input event listener to listen for any value changes on the digital input
        // using a custom method with a single event parameter
        @OnEvent(DIGITAL_INPUT_PIN_ID)
        private void onDigitalInputChange(DigitalStateChangeEvent event){
            System.out.println(" (LISTENER #2) :: " + event);
        }

        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Basic Digital Input Example Using Dependency Injection");

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
            console.print("THE STARTING DIGITAL INPUT [" + input + "] STATE IS [");
            console.println(input.state() + "]");

            console.println("CHANGE INPUT VALUES VIA I/O HARDWARE AND CHANGE EVENTS WILL BE PRINTED BELOW:");

            // we are done
            return null;
        }
    }
}
