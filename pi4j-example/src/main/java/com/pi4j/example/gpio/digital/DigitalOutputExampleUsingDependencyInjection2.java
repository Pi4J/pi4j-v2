package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE :: Sample Code
 * FILENAME      :  DigitalOutputExample.java
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
import com.pi4j.annotation.Inject;
import com.pi4j.annotation.InjectDigitalOutput;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalChangeListener;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class DigitalOutputExampleUsingDependencyInjection2 {

    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container class to run our example
        new ExampleRuntime().call();
    }

    public static class ExampleRuntime implements Callable<Void> {

        public static final int DIGITAL_OUTPUT_PIN = 4;

        // create a digital output instance using the default digital output provider
        @InjectDigitalOutput(address = DIGITAL_OUTPUT_PIN)
        public DigitalOutput output;

        @Inject
        public Context pi4j;

        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Basic Digital Output Example");

            // allow for user to exit program using CTRL-C
            console.promptForExit();

            // initialize the Pi4J library then inject this class for dependency injection on annotations
            Pi4J.initialize().inject(this);

            // create a digital output instance using the default digital output provider
            //var output = DigitalOutput.instance(DIGITAL_OUTPUT_PIN);
            //output.config().shutdownState(DigitalState.HIGH);
            output.config().shutdownState(DigitalState.HIGH);

            // setup a digital output listener to listen for any state changes on the digital output
            output.addListener((DigitalChangeListener) event -> {
                System.out.println(event);
            });

            // lets invoke some changes on the digital output
            output.state(DigitalState.HIGH)
                    .state(DigitalState.LOW)
                    .state(DigitalState.HIGH)
                    .state(DigitalState.LOW);

            // lets toggle the digital output state a few times
            output.toggle()
                    .toggle()
                    .toggle();


            // additional friendly methods for setting output state
            output.high()
                  .low();

            // lets read the digital output state
            console.print("CURRENT DIGITAL OUTPUT STATE IS [");
            console.println(output.state() + "]");

            // pulse to HIGH state for 3 seconds
            console.println("PULSING OUTPUT STATE TO HIGH FOR 3 SECONDS");
            output.pulse(3, TimeUnit.SECONDS, DigitalState.HIGH);

            // friendly pulsing methods for setting output state
            console.println("PULSING OUTPUT STATE TO HIGH FOR 2 SECONDS");
            output.pulseHigh(2, TimeUnit.SECONDS);
            console.println("PULSING OUTPUT STATE COMPLETE");

            // shutdown Pi4J
            console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
            Pi4J.terminate();

            // we are done
            return null;
        }
    }
}
