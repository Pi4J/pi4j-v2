package com.pi4j.example;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  Main.java
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
import com.pi4j.io.gpio.analog.binding.AnalogBindingSync;

public class Main {

    public Main() {
    }

    public static void main(String[] args) throws Exception {

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();


//        Serial serial = Serial.instance("/dev/ttyUSB1");
//        serial.open();
//        serial.send("TEST DATA");
//        serial.close();

        var din1 = pi4j.din().create(11);
        var ain1 = pi4j.ain().create(21);

        var input = pi4j.ain().create(98);
        var output1 = pi4j.aout().create(99);
        var output2 = pi4j.aout().create(100);

        input.addListener((AnalogChangeListener) event -> {
            System.out.print(event);
        });


        output1.addListener((AnalogChangeListener) event -> {
            System.out.println(event);
        });
        output2.addListener((AnalogChangeListener) event -> {
            System.out.println(event);
        });

        input.bind(new AnalogBindingSync(output1, output2));


        //((TestAnalogInput)input).test(21).test(22).test(23);


        output1.value(12);
        output1.setValue(78);
        output1.value(0x01);


        //AnalogOutput aout1 = AnalogOutput.in
//        DigitalOutput dout1 = DigitalOutput;

        // shutdown Pi4J
        pi4j.shutdown();
    }
}
