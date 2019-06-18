package com.pi4j.example;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE :: Sample Code
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
import com.pi4j.context.Context;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.binding.AnalogBindingSync;
import com.pi4j.io.gpio.digital.DigitalInput;

public class Main {

    public Main() {
    }

    public static void main(String[] args) throws Exception {

        // initialize Pi4J
        Context pi4j = Pi4J.initialize();


//        Serial serial = Serial.instance("/dev/ttyUSB1");
//        serial.open();
//        serial.send("TEST DATA");
//        serial.close();



        DigitalInput din1 = DigitalInput.instance(11);
        AnalogInput ain1 = AnalogInput.instance(21);



        AnalogInput input = AnalogInput.instance(98);
        AnalogOutput output1 = AnalogOutput.instance(99);
        AnalogOutput output2 = AnalogOutput.instance(100);

        input.addListener((AnalogChangeListener) event -> {
            System.out.print("ANALOG INPUT [");
            System.out.print(event.source().address());
            System.out.print("] VALUE CHANGE: ");
            System.out.println(event.value());
        });


        output1.addListener((AnalogChangeListener) event -> {
            System.out.print("ANALOG OUTPUT [");
            System.out.print(event.source().address());
            System.out.print("] VALUE CHANGE: ");
            System.out.println(event.value());
        });
        output2.addListener((AnalogChangeListener) event -> {
            System.out.print("ANALOG OUTPUT [");
            System.out.print(event.source().address());
            System.out.print("] VALUE CHANGE: ");
            System.out.println(event.value());
        });

        input.bind(new AnalogBindingSync(output1, output2));


        //((TestAnalogInput)input).test(21).test(22).test(23);


        //output.value(12);
        //output.setValue(78);
        //output.value(0x01);


        //AnalogOutput aout1 = AnalogOutput.in
//        DigitalOutput dout1 = DigitalOutput;

        // shutdown Pi4J
        Pi4J.terminate();
    }
}
