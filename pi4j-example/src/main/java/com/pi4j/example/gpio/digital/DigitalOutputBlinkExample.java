package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  DigitalOutputBlinkExample.java
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
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.digital.DigitalChangeListener;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.platform.Platform;

import java.util.concurrent.TimeUnit;

public class DigitalOutputBlinkExample {

    public static int DIGITAL_OUTPUT_PIN = 4;

    public DigitalOutputBlinkExample() {
    }

    public static void main(String[] args) throws Exception {

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J binding libraries
        // in the class path
        var pi4j = Pi4J.newAutoContext();

        // create a digital output instance using the default digital output provider
        //var output = DigitalOutput.create(DIGITAL_OUTPUT_PIN);

        // get default runtime platform
        Platform platform = pi4j.platforms().getDefault();

        // get default digital output provide for this platform
        DigitalOutputProvider provider = platform.provider(IOType.DIGITAL_OUTPUT);

        // create I/O instance configuration using the config builder
        DigitalOutputConfig config = DigitalOutputConfig.newBuilder().address(3).build();

        // use factory to create/register  I/O instance
        DigitalOutput output = provider.create(config);

        // setup a digital output listener to listen for any state changes on the digital output
        output.addListener((DigitalChangeListener) event -> {
            System.out.println(event);
        });


        // lets toggle the digital output state a few times
        output.toggle()
              .toggle()
              .toggle();

        // another friendly method of setting output state
        output.high()
              .low();

        // blink the output for 10 seconds
        output.blink(1, 10, TimeUnit.SECONDS);

        // shutdown Pi4J
        pi4j.shutdown();
    }
}


