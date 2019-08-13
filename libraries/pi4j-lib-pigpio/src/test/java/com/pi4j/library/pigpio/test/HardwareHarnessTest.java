package com.pi4j.library.pigpio.test;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
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

        final BlockingQueue<Object> values = new LinkedBlockingQueue<Object>();

        // get port instance
        SerialPort port = SerialPort.getCommPort("tty.usbmodem142301");

        // configure port
        port.setBaudRate(115200);
        port.setNumDataBits(8);
        port.setNumStopBits(1);
        port.setParity(0);
        port.setFlowControl(FLOW_CONTROL_DISABLED);

        port.setComPortTimeouts(TIMEOUT_NONBLOCKING, 0, 0);

        // open serial port
        port.openPort();

        // test serial port
        Assert.assertTrue("Serial port to test harness failed.", port.isOpen());

        // if the port is open, then proceed with tests
        if(port.isOpen()) {

            // send the "info" command to the test harness
            port.getOutputStream().write("info\r\n".getBytes());
            port.getOutputStream().flush();

//            Scanner in = new Scanner(port.getInputStream());
//            while(!in.hasNextLine()){
//            }
//            System.out.println(in.nextLine());

//            Gson gson = new Gson();
//            Info info = gson.fromJson(received, Info.class);
//            System.out.println(info.id);
//            System.out.println(info.name);
//            System.out.println(info.version);
//            System.out.println(info.date);
//            System.out.println(info.copyright);


            SerialPortMessageListener listener = new SerialPortMessageListener() {
                @Override
                public int getListeningEvents() {
                    return LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    try {
                        byte[] data = serialPortEvent.getReceivedData();
                        String received  = new String(data, StandardCharsets.US_ASCII);

                        //System.out.println(received);

                        JSONObject payload = new JSONObject(received);
                        if(payload.has("id") && payload.get("id").toString().equalsIgnoreCase("info")){
                            // return the info payload to the blocking queue
                            values.offer(payload);
                            return;
                        }
                        Assert.fail("The response data received is missing the 'info' identifier: " + received);

                        // close serial port
                        port.closePort();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Assert.fail(e.getMessage());
                    }
                }

                @Override
                public byte[] getMessageDelimiter() {
                    return new byte[] { 0x0D, 0x0A };
                }

                @Override
                public boolean delimiterIndicatesEndOfMessage() {
                    return true;
                }
            };

            // add event listener
            port.addDataListener(listener);

            // wait here for "info" payload to be received
            JSONObject response  = (JSONObject) values.take();

            System.out.println("... we are connected to test harness:");
            System.out.println("----------------------------------------");
            System.out.println("NAME       : " + response.get("name"));
            System.out.println("VERSION    : " + response.get("version"));
            System.out.println("DATE       : " + response.get("date"));
            System.out.println("COPYRIGHT  : " + response.get("copyright"));
            System.out.println("----------------------------------------");

            // success; close port
            port.closePort();
        }
    }
}
