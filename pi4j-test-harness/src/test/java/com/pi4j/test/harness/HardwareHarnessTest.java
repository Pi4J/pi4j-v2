package com.pi4j.test.harness;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Arduino Test Harness
 * FILENAME      :  HardwareHarnessTest.java
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

import com.fazecast.jSerialComm.SerialPort;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;


@TestMethodOrder(OrderAnnotation.class)
public class HardwareHarnessTest {

    @Before
    public void beforeTest() {
    }

    @After
    public void afterTest() {
    }

    @Test
    @Order(1)
    public void testListSerialPorts() {

        System.out.println("-------------------------------------------------");
        System.out.println("UNIT TEST :: LIST SERIAL PORTS");
        System.out.println("-------------------------------------------------");

        // list all available serial ports
        int index = 0;
        SerialPort[] ports = SerialPort.getCommPorts();
        for(SerialPort port : ports){
            index++;
            System.out.print(index);
            System.out.print(" [");
            System.out.print(port.getSystemPortName());
            System.out.print("] - ");
            System.out.print(port.getPortDescription());
            System.out.println();
        }
    }

    @Test
    @Order(2)
    public void findTestHarnessSerialPort() {

        System.out.println("-------------------------------------------------");
        System.out.println("UNIT TEST :: FIND TEST HARNESS SERIAL PORT");
        System.out.println("-------------------------------------------------");

        // list all available serial ports
        int index = 0;
        SerialPort[] ports = SerialPort.getCommPorts();
        for(SerialPort port : ports){
            //cu.usbmodem142301 "Arduino Due"
            if(port.getPortDescription().equalsIgnoreCase("Arduino Due")){
                System.out.print("TEST HARNESS SERIAL PORTS FOUND [");
                System.out.print(port.getSystemPortName());
                System.out.print("] - ");
                System.out.print(port.getPortDescription());
                System.out.println();
                return;
            }
        }

        Assert.fail("The 'Arduino Due' serial port could not be found; therefore " +
                " we cannot communicate with the testing harness hardware");
    }

    @Test
    @Timeout(5) // seconds
    @Order(3)
    public void testTestHarnessConnection() throws IOException, InterruptedException {

        // create test harness instance
        ArduinoTestHarness harness = new ArduinoTestHarness(System.getProperty("pi4j.test.harness.port", "tty.usbmodem142301"));

        // initialize test harness
        harness.initialize();

        // get test harness info
        TestHarnessInfo info = harness.getInfo();
        System.out.println("... we are connected to test harness:");
        System.out.println("----------------------------------------");
        System.out.println("NAME       : " + info.name);
        System.out.println("VERSION    : " + info.version);
        System.out.println("DATE       : " + info.date);
        System.out.println("COPYRIGHT  : " + info.copyright);
        System.out.println("----------------------------------------");

        // shutdown test harness
        harness.shutdown();
    }
}
