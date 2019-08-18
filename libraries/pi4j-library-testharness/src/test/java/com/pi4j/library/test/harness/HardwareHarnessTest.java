package com.pi4j.library.test.harness;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Arduino Test Harness
 * FILENAME      :  HardwareHarnessTest.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.fazecast.jSerialComm.SerialPort.*;


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

        // close test harness
        harness.terminate();
    }
}
