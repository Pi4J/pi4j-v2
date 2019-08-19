package com.pi4j.test.harness;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Arduino Test Harness
 * FILENAME      :  ArduinoTestHarness.java
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
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ArduinoTestHarness {

    protected String comport;
    protected SerialPort com = null;
    protected Gson gson = new Gson();

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
        com.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);

        // configure read timeout
        com.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 250, 0);
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


    public TestHarnessResponse enableI2C(int bus, int device) throws IOException {
        return enableI2C(bus, device, false);
    }

    public TestHarnessResponse enableI2C(int bus, int device, boolean rawMode) throws IOException {
        send(String.format("i2c %d %d %b", bus, device, rawMode));
        TestHarnessResponse response = read(TestHarnessResponse.class);
        return response;
    }

    public TestHarnessFrequency getFrequency(int pin) throws IOException {
        send("frequency " + pin);
        TestHarnessFrequency response = read(TestHarnessFrequency.class);
        return response;
    }

    public void send(String command) throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        // drain any previous data
        drain();

        com.getOutputStream().write(command.getBytes(StandardCharsets.US_ASCII));
        com.getOutputStream().write(0x0D); // <CR>
        com.getOutputStream().write(0x0A); // <LF>
        com.getOutputStream().flush();
    }

    protected <T extends TestHarnessResponse> T read(Class<T> type) throws IOException {
        List<TestHarnessResponse> responses = read();
        TestHarnessError err = null;
        for (var response : responses){
            if(TestHarnessError.class.isInstance(response)){
                err = (TestHarnessError)response;
            }
            else if(type.isInstance(response)){
                return (T)response;
            }
        }
        if(err != null){
            throw new IOException("TEST HARNESS ERROR: [" + err.errno + "] " + err.msg);
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
                   case "error": {
                       TestHarnessError response = gson.fromJson(received, TestHarnessError.class);
                       responses.add(response);
                       break;
                   }
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
                   case "frequency": {
                       TestHarnessFrequency response = gson.fromJson(received, TestHarnessFrequency.class);
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

    protected void drain() throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        Scanner in = new Scanner(com.getInputStream());
        while(in.hasNextByte()){
            var received  = in.nextByte();
            System.out.println("[DRAINED]" + received);
        }
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

        // drain serial port buffer
        //drain();
    }
}
