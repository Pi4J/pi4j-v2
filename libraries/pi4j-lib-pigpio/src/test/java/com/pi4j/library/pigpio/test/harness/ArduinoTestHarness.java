package com.pi4j.library.pigpio.test.harness;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  ArduinoTestHarness.java
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
import com.google.gson.Gson;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioState;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.fazecast.jSerialComm.SerialPort.FLOW_CONTROL_DISABLED;
import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_SEMI_BLOCKING;


public class ArduinoTestHarness {

    protected static String DEFAULT_COM_PORT = "tty.usbmodem142301";
    protected String comport;
    protected SerialPort com = null;
    protected Gson gson = new Gson();

    public ArduinoTestHarness() throws IOException {
        this(DEFAULT_COM_PORT);
    }

    public ArduinoTestHarness(String comport) throws IOException {

        // set local reference
        this.comport = comport;

        // get serial port instance
        com = SerialPort.getCommPort(comport);

        // configure serial port
        com.setBaudRate(115200);
        com.setNumDataBits(8);
        com.setNumStopBits(1);
        com.setParity(0);
        com.setFlowControl(FLOW_CONTROL_DISABLED);

        // configure read timeout
        com.setComPortTimeouts(TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
    }

    public TestHarnessPins reset() throws IOException {
        send("reset");
        TestHarnessPins response = read(TestHarnessPins.class);
        return response;
    }

    public TestHarnessInfo getInfo() throws IOException {
        send("info");
        TestHarnessInfo response = read(TestHarnessInfo.class);
        return response;
    }

    public TestHarnessPin disablePin(int pin) throws IOException {
        send("pin " + pin + " disable");
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    public TestHarnessPin getPin(int pin) throws IOException {
        send("pin " + pin);
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    public TestHarnessPin setInputPin(int pin) throws IOException {
        return setInputPin(pin, false);
    }

    public TestHarnessPin setInputPin(int pin, boolean pullUp) throws IOException {
        String mode = (pullUp)? "input_pullup" : "input";
        send(String.format("pin %d %s", pin, mode));
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    public TestHarnessPin setOutputPin(int pin) throws IOException {
        return setOutputPin(pin, false);
    }

    public TestHarnessPin setOutputPin(int pin, boolean state) throws IOException {
        String stateString = (state)? "high" : "low";
        send(String.format("pin %d output %s", pin, stateString));
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    public void send(String command) throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        com.getOutputStream().write(command.getBytes(StandardCharsets.US_ASCII));
        com.getOutputStream().write(0x0D); // <CR>
        com.getOutputStream().write(0x0A); // <LF>
        com.getOutputStream().flush();
    }

    protected <T extends TestHarnessResponse> T read(Class<T> type) throws IOException {
        List<TestHarnessResponse> responses = read();
        for (var response : responses){
            if(type.isInstance(response)){
               return (T)response;
            }
        }
        return null;
    }

    protected List<TestHarnessResponse> read() throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        List<TestHarnessResponse> responses = new ArrayList<>();
        Scanner in = new Scanner(com.getInputStream());
        while(in.hasNextLine()){
            var received  = in.nextLine();
            //System.out.println(received);
            JSONObject payload = new JSONObject(received);

            if(payload.has("id")){
               var id = payload.getString("id").toLowerCase();

               switch (id){
                   case "info": {
                       TestHarnessInfo response = gson.fromJson(received, TestHarnessInfo.class);
                       responses.add(response);
                       break;
                   }
                   case "get": {
                       TestHarnessPin response = gson.fromJson(received, TestHarnessPin.class);
                       responses.add(response);
                       break;
                   }
                   case "set": {
                       TestHarnessPin response = gson.fromJson(received, TestHarnessPin.class);
                       responses.add(response);
                       break;
                   }
                   case "reset": {
                       TestHarnessPins response = gson.fromJson(received, TestHarnessPins.class);
                       responses.add(response);
                       break;
                   }
                   default:{
                       TestHarnessResponse response = gson.fromJson(received, TestHarnessResponse.class);
                       responses.add(response);
                       break;
                   }
               }
            }
        }

        return responses;
    }

    public void terminate() throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        // close port when done
        com.closePort();
    }


    public void initialize() throws IOException {

        // validate serial port is connected
        //if(com.isOpen()) throw new IOException("Already initialized!");

        // open com port
        com.openPort();

//        // test serial port was opened
//        // if the port is not open, then fail
        if(!com.isOpen()) {
            throw new IOException("Serial port [" + comport + "] failed to open;");
        }
    }

}
