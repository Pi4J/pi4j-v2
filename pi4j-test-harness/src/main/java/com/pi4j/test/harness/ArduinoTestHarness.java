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


/**
 * <p>ArduinoTestHarness class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class ArduinoTestHarness {

    protected String comport;
    protected SerialPort com = null;
    protected Gson gson = new Gson();

    /**
     * <p>Constructor for ArduinoTestHarness.</p>
     *
     * @param comport a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
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
        com.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 50, 0);
    }

    /**
     * <p>reset.</p>
     *
     * @return a {@link com.pi4j.test.harness.TestHarnessPins} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPins reset() throws IOException {
        send("reset");
        TestHarnessPins response = read(TestHarnessPins.class);
        return response;
    }

    /**
     * <p>reboot.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void reboot() throws IOException {
        send("reboot");
    }

    /**
     * <p>getInfo.</p>
     *
     * @return a {@link com.pi4j.test.harness.TestHarnessInfo} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessInfo getInfo() throws IOException {
        send("info");
        TestHarnessInfo response = read(TestHarnessInfo.class);
        return response;
    }

    /**
     * <p>disablePin.</p>
     *
     * @param pin a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessPin} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPin disablePin(int pin) throws IOException {
        send("pin " + pin + " disable");
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    /**
     * <p>getPin.</p>
     *
     * @param pin a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessPin} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPin getPin(int pin) throws IOException {
        send("pin " + pin);
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    /**
     * <p>setInputPin.</p>
     *
     * @param pin a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessPin} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPin setInputPin(int pin) throws IOException {
        return setInputPin(pin, false);
    }

    /**
     * <p>setInputPin.</p>
     *
     * @param pin a int.
     * @param pullUp a boolean.
     * @return a {@link com.pi4j.test.harness.TestHarnessPin} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPin setInputPin(int pin, boolean pullUp) throws IOException {
        String mode = (pullUp)? "input_pullup" : "input";
        send(String.format("pin %d %s", pin, mode));
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    /**
     * <p>setOutputPin.</p>
     *
     * @param pin a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessPin} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPin setOutputPin(int pin) throws IOException {
        return setOutputPin(pin, false);
    }

    /**
     * <p>setOutputPin.</p>
     *
     * @param pin a int.
     * @param state a boolean.
     * @return a {@link com.pi4j.test.harness.TestHarnessPin} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessPin setOutputPin(int pin, boolean state) throws IOException {
        String stateString = (state)? "high" : "low";
        send(String.format("pin %d output %s", pin, stateString));
        TestHarnessPin response = read(TestHarnessPin.class);
        return response;
    }

    /**
     * <p>getFrequency.</p>
     *
     * @param pin a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessFrequency} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessFrequency getFrequency(int pin) throws IOException {
        send("frequency " + pin);
        TestHarnessFrequency response = read(TestHarnessFrequency.class);
        return response;
    }

    /**
     * <p>enableI2C.</p>
     *
     * @param bus a int.
     * @param device a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessResponse} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessResponse enableI2C(int bus, int device) throws IOException {
        return enableI2C(bus, device, false);
    }

    /**
     * <p>enableI2C.</p>
     *
     * @param bus a int.
     * @param device a int.
     * @param rawMode a boolean.
     * @return a {@link com.pi4j.test.harness.TestHarnessResponse} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessResponse enableI2C(int bus, int device, boolean rawMode) throws IOException {
        send(String.format("i2c %d %d %b", bus, device, rawMode));
        TestHarnessResponse response = read(TestHarnessResponse.class);
        return response;
    }

    /**
     * <p>enableSerialEcho.</p>
     *
     * @param port a int.
     * @param baudRate a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessResponse} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessResponse enableSerialEcho(int port, int baudRate) throws IOException {
        send(String.format("serial %d %d", port, baudRate));
        TestHarnessResponse response = read(TestHarnessResponse.class);
        return response;
    }

    /**
     * <p>enableSpiEcho.</p>
     *
     * @param channel a int.
     * @return a {@link com.pi4j.test.harness.TestHarnessResponse} object.
     * @throws java.io.IOException if any.
     */
    public TestHarnessResponse enableSpiEcho(int channel) throws IOException {
        send(String.format("spi %d", channel));
        TestHarnessResponse response = read(TestHarnessResponse.class);
        return response;
    }

    /**
     * <p>send.</p>
     *
     * @param command a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public void send(String command) throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        // drain any previous data
        drain();

        com.getOutputStream().write(0x0D); // <CR>
        com.getOutputStream().write(0x0A); // <LF>
        com.getOutputStream().write(0x0D); // <CR>
        com.getOutputStream().write(0x0A); // <LF>
        com.getOutputStream().write(command.getBytes(StandardCharsets.US_ASCII));
        com.getOutputStream().write(0x0D); // <CR>
        com.getOutputStream().write(0x0A); // <LF>
        com.getOutputStream().flush();
    }

    /**
     * <p>read.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws java.io.IOException if any.
     */
    protected <T extends TestHarnessResponse> T read(Class<T> type) throws IOException {


        List<TestHarnessResponse> responses = read();

        try {
            if (responses.isEmpty()) {
                Thread.sleep(1000);
                responses = read();
            }
            if (responses.isEmpty()) {
                Thread.sleep(1000);
                responses = read();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

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

    /**
     * <p>read.</p>
     *
     * @return a {@link java.util.List} object.
     * @throws java.io.IOException if any.
     */
    protected List<TestHarnessResponse> read() throws IOException {
        List<TestHarnessResponse> responses = new ArrayList<>();

        try {
            Thread.sleep(50);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

//        int counter = 0;
//        try {
//            while (com.bytesAvailable() <= 0) {
//                counter++;
//                if(counter > 20)
//                     return responses;
//                Thread.sleep(50);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

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
                   case "i2c": {
                       TestHarnessResponse response = gson.fromJson(received, TestHarnessResponse.class);
                       responses.add(response);
                       break;
                   }
                   case "serial": {
                       TestHarnessResponse response = gson.fromJson(received, TestHarnessResponse.class);
                       responses.add(response);
                       break;
                   }
                   case "spi": {
                       TestHarnessResponse response = gson.fromJson(received, TestHarnessResponse.class);
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

    /**
     * <p>drain.</p>
     *
     * @throws java.io.IOException if any.
     */
    protected void drain() throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        Scanner in = new Scanner(com.getInputStream());
        while(in.hasNextByte()){
            var received  = in.nextByte();
            System.out.println("[DRAINED]" + received);
        }
    }

    /**
     * <p>close.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void close() throws IOException {

        // validate serial port is connected
        if(!com.isOpen()) throw new IOException("Serial port is not open;");

        // close serial communications port
        com.closePort();
    }

    /**
     * <p>shutdown.</p>
     *
     * @throws java.io.IOException if any.
     */
    public void shutdown() throws IOException {

        // reset all pin states
        reset();

        // close serial communications port
        if(com.isOpen()) com.closePort();
    }

    /**
     * <p>initialize.</p>
     *
     * @throws java.io.IOException if any.
     */
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
