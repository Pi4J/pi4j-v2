package com.pi4j.library.pigpio.test.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  TestI2cUsingTestHarness.java
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

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioMode;
import com.pi4j.library.pigpio.test.harness.ArduinoTestHarness;
import com.pi4j.library.pigpio.test.harness.TestHarnessInfo;
import com.pi4j.library.pigpio.test.harness.TestHarnessPins;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

@DisplayName("PIGPIO :: Test I2C Communication")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestI2cUsingTestHarness {

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int I2C_TEST_HARNESS_BUS    = 0;
    private static int I2C_TEST_HARNESS_DEVICE = 0x04;
    private static int MAX_REGISTERS = 100;

    private PiGpio pigpio;
    private int handle;


    @BeforeAll
    public static void initialize() {
        //System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("INITIALIZE TEST (" + TestI2cUsingTestHarness.class.getName() + ")");
        System.out.println("************************************************************************");
        System.out.println();

        try {
            // create test harness and PIGPIO instances
            ArduinoTestHarness harness = new ArduinoTestHarness(System.getProperty("pi4j.test.harness.port", "tty.usbmodem142301"));

            // initialize test harness and PIGPIO instances
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

            // reset all pins on test harness before proceeding with this test
            TestHarnessPins reset = harness.reset();
            System.out.println();
            System.out.println("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");

            // enable the I2C bus and device on the test harness hardware
            harness.enableI2C(I2C_TEST_HARNESS_BUS, I2C_TEST_HARNESS_DEVICE);
            System.out.println();
            System.out.println("ENABLE I2C BUS [" + I2C_BUS + "] ON TEST HARNESS;");

            harness.terminate();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("TERMINATE TEST (" + TestI2cUsingTestHarness.class.getName() + ") ");
        System.out.println("************************************************************************");
        System.out.println();
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        // create test harness and PIGPIO instances
        pigpio = PiGpio.newSocketInstance(System.getProperty("pi4j.pigpio.host", "rpi3bp.savage.lan"),
                                          System.getProperty("pi4j.pigpio.port", "8888"));

        // initialize test harness and PIGPIO instances
        pigpio.initialize();

        // set pin ALT0 modes for I2C BUS<1> usage on RPI3B
        pigpio.gpioSetMode(2, PiGpioMode.ALT0);
        pigpio.gpioSetMode(3, PiGpioMode.ALT0);

        // OPEN I2C
        handle = pigpio.i2cOpen(I2C_BUS, I2C_DEVICE);
    }

    @AfterEach
    public void afterEach() throws IOException {

        // CLOSE I2C
        pigpio.i2cClose(handle);

        // terminate test harness and PIGPIO instances
        pigpio.terminate();
    }


    @Test
    @DisplayName("I2C :: Test register: WORD (W/R)")
    @Order(1)
    public void testI2CSingleWordTxRx() throws IOException, InterruptedException {

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER READ/WRITE WORD");
        System.out.println("----------------------------------------");

        // value cache
        int[] values = new int[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            System.out.println("[TEST WRITE WORD] :: REGISTER=" + register);

            Random rand = new Random();
            values[register] = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
            System.out.println(" (WRITE) >> VALUE = " + values[register]);

            // WRITE :: SINGLE WORD TO REGISTER
            pigpio.i2cWriteWordData(handle, register, values[register]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            System.out.println("[TEST READ WORD] :: REGISTER=" + register);

            // READ :: SINGLE RAW WORD
            int value = pigpio.i2cReadWordData(handle, register);
            System.out.println(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ")");

            // validate read value match with expected value that was writted to this register
            Assert.assertEquals("I2C WORD VALUE MISMATCH",  values[register], value);
        }
    }

    @Test
    @DisplayName("I2C :: Test register: WORD process (W/R)")
    @Order(2)
    public void testI2CProcessWordTxRx() throws IOException, InterruptedException {

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER PROCESS (W/R) <WORD>");
        System.out.println("----------------------------------------");

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        // the process call should immediately return the same values back.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            System.out.println("[TEST PROCESS WORD] :: REGISTER=" + register);

            Random rand = new Random();
            int generatedValue = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
            System.out.println(" (WRITE) >> VALUE = " + generatedValue);

            // WRITE/READ :: SINGLE WORD TO REGISTER
            int returnedValue = pigpio.i2cProcessCall(handle, register, generatedValue);
            System.out.println(" (READ)  << VALUE = " + returnedValue);

            // validate read value match with expected value that was writted to this register
            Assert.assertEquals("I2C WORD VALUE MISMATCH",  generatedValue, returnedValue);
        }
    }

    @Test
    @DisplayName("I2C :: Test register: BLOCK (W/R)")
    @Order(3)
    public void testI2CBlockDataTxRx() throws IOException, InterruptedException {

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER READ/WRITE BLOCK");
        System.out.println("----------------------------------------");

        // value cache
        String[] values = new String[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            System.out.println("[TEST WRITE BLOCK] :: REGISTER=" + register);
            values[register] = UUID.randomUUID().toString().substring(0, 10);
            System.out.println(" (WRITE) >> VALUE = " + values[register]);

            // WRITE :: BLOCK TO REGISTER
            pigpio.i2cWriteBlockData(handle, register, values[register]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < 10; register++) {
            Thread.sleep(50);
            System.out.println("[TEST READ BLOCK] :: REGISTER=" + register);


            // TODO ::PIGPIO ERROR: PI_BAD_SMBUS_CMD; SMBus command not supported by driver

//            // READ :: BLOCK
//            String value = pigpio.i2cReadBlockDataToString(handle, register);
//            System.out.println(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ")");
//
//            // validate read value match with expected value that was writted to this register
//            Assert.assertEquals("I2C BLOCK VALUE MISMATCH",  values[register], value);
        }
    }

    @Test
    @DisplayName("I2C :: Test register: BLOCK process (W/R)")
    @Order(4)
    @Disabled("This test is disabled until we can research the problem with the 'I2CPK' command response.")
    public void testI2CProcessBlockTxRx() throws IOException, InterruptedException {

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER PROCESS (W/R) <BLOCK>");
        System.out.println("----------------------------------------");

        // TODO :: THIS TEST IS NOT WORKING?  IS THIS SUPPORTED BY THE I2C HARDWARE/DRIVER?

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        // the process call should immediately return the same values back.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            System.out.println("[TEST PROCESS BLOCK] :: REGISTER=" + register);

            String generatedValue = UUID.randomUUID().toString().substring(0, 10);
            System.out.println(" (WRITE) >> VALUE = " + generatedValue);

            // WRITE/READ :: BLOCK TO REGISTER
            String returnedValue = pigpio.i2cBlockProcessCallToString(handle, register, generatedValue);
            System.out.println(" (READ)  << VALUE = " + returnedValue);

            // validate read value match with expected value that was written to this register
            Assert.assertEquals("I2C BLOCK VALUE MISMATCH",  generatedValue, returnedValue);
        }
    }

    @Test
    @DisplayName("I2C :: Test register: I2C BLOCK (W/R)")
    @Order(5)
    public void testI2CBlockI2CDataTxRx() throws IOException, InterruptedException, NoSuchAlgorithmException {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER READ/WRITE BLOCK");
        System.out.println("----------------------------------------");

        // value cache
        String[] values = new String[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            System.out.println("[TEST WRITE BLOCK] :: REGISTER=" + register);
            values[register] = UUID.randomUUID().toString().substring(0, 30);
            System.out.println(" (WRITE) >> VALUE = " + values[register]);

            // WRITE :: BLOCK TO REGISTER
            pigpio.i2cWriteI2CBlockData(handle, register, values[register]);
        }

        // READ back the random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            System.out.println("[TEST READ BLOCK] :: REGISTER=" + register + "; LENGTH=" + values[register].length());

            // READ :: BLOCK
            Thread.sleep(100);
            String value = pigpio.i2cReadI2CBlockDataToString(handle, register, values[register].length());
            System.out.println(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ")");

            // attempt #2
            if(!values[register].equals(value)){
                Thread.sleep(500);
                value = pigpio.i2cReadI2CBlockDataToString(handle, register, values[register].length());
                System.out.println(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ") <ATTEMPT #2>");
            }

            // attempt #3
            if(!values[register].equals(value)){
                Thread.sleep(1000);
                value = pigpio.i2cReadI2CBlockDataToString(handle, register, values[register].length());
                System.out.println(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ") <ATTEMPT #3>");
            }

            // validate read value match with expected value that was writted to this register
            Assert.assertEquals("I2C BLOCK VALUE MISMATCH",  values[register], value);
        }
    }
}
