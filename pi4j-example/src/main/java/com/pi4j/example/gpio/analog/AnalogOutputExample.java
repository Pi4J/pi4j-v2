package com.pi4j.example.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  AnalogOutputExample.java
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
import com.pi4j.io.gpio.analog.AnalogOutputConfigBuilder;
import com.pi4j.util.Console;

public class AnalogOutputExample {

    public static String ANALOG_OUTPUT_PIN_ID = "my.unique.analog.output.pin.id";
    public static String ANALOG_OUTPUT_PIN_NAME = "My Unique Output Pin";
    public static int ANALOG_OUTPUT_PIN = 4;
    public static int SHUTDOWN_ANALOG_VALUE = -1;

    public AnalogOutputExample() {
    }

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();


        // print program title/header
        console.title("<-- The Pi4J Project -->", "Basic Analog Output Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        AnalogOutputConfigBuilder builder = AnalogOutputConfigBuilder.newInstance();
        builder.id(ANALOG_OUTPUT_PIN_ID)
               .name(ANALOG_OUTPUT_PIN_NAME)
               .address(ANALOG_OUTPUT_PIN);
        var output = pi4j.aout().create(builder.build());

        // create an analog output instance using the default analog output provider
        //var output = AnalogOutput.create(ANALOG_OUTPUT_PIN_ID, ANALOG_OUTPUT_PIN);
        output.config().shutdownValue(SHUTDOWN_ANALOG_VALUE);

        // setup a analog output listener to listen for any state changes on the analog output
        output.addListener((AnalogChangeListener) event -> {
            System.out.println(event);
        });

        // lets invoke some changes on the analog output
        output.value(101)
              .value(34)
              .value(999)
              .value(45);

        // lets read the analog output state
        System.out.print("CURRENT ANALOG OUTPUT [" + output + "] VALUE IS [");
        System.out.println(output.value() + "]");

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");
        pi4j.shutdown();
    }
}
