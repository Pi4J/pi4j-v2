package com.pi4j.example.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  AnalogOutputExampleUsingDependencyInjection.java
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
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogValueChangeEvent;
import com.pi4j.io.gpio.analog.AnalogValueChangeListener;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;

/**
 * <p>AnalogOutputExampleUsingDependencyInjection class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class AnalogOutputExampleUsingDependencyInjection {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        new RuntimeContainer().call();
    }

    private static class RuntimeContainer implements Callable<Void> {

        private static final int ANALOG_OUTPUT_PIN_ADDRESS = 4;
        private static final String ANALOG_OUTPUT_PIN_ID = "uuid.analog.out.four";

        // create an analog output instance using the default analog output provider
        @Register(ANALOG_OUTPUT_PIN_ID)
        @Address(ANALOG_OUTPUT_PIN_ADDRESS)
        @Name("My Analog Out")
        @ShutdownValue(9)
        @InitialValue(10)
        @Range(min = 1, max = 10)
        @StepValue(3)
        private AnalogOutput output1;

        // create an analog output instance using the default analog output provider
        @Inject(ANALOG_OUTPUT_PIN_ID)
        private AnalogOutput output;

        @Inject
        private Context pi4j;

        // register an analog output listener to listen for any state changes on the analog output
        @Register(ANALOG_OUTPUT_PIN_ID)
        private AnalogValueChangeListener analogChangeListener = event -> System.out.println(" (LISTENER #1) :: " + event);

        // setup an analog output event listener to listen for any value changes on the analog output
        // using a custom method with a single event parameter
        @OnEvent(ANALOG_OUTPUT_PIN_ID)
        private void onAnalogOutputChange(AnalogValueChangeEvent event){
            System.out.println(" (LISTENER #2) :: " + event);
        }

        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Basic Analog Output Example Using Dependency Injection");

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

            // lets invoke some changes on the analog output
            output.value(1)
                  .value(2)
                  .value(3)
                  .value(4)
                  .stepUp()
                  .stepDown()
                  .step(100)
                  .step(-100);

            // lets read the analog output value
            console.print("CURRENT ANALOG OUTPUT [" + output + "] VALUE IS [");
            console.println(output.value() + "]");

            // shutdown Pi4J
            console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
            pi4j.shutdown();

            // we are done
            return null;
        }
    }
}
