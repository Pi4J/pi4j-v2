package com.pi4j.library.pigpio.test.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestI2cUsingTestHarness.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioMode;
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("PIGPIO Library :: Test I2C Communication")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestI2cUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestI2cUsingTestHarness.class);

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int I2C_TEST_HARNESS_BUS    = 0;
    private static int I2C_TEST_HARNESS_DEVICE = 0x04;
    private static int MAX_REGISTERS = 100;

    private PiGpio pigpio;
    private int handle;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestI2cUsingTestHarness.class.getName() + ")");
        logger.info("************************************************************************");
        logger.info("");

        try {
            // create test harness and PIGPIO instances
            ArduinoTestHarness harness = TestEnv.createTestHarness();

            // initialize test harness and PIGPIO instances
            harness.initialize();

            // get test harness info
            TestHarnessInfo info = harness.getInfo();
            logger.info("... we are connected to test harness:");
            logger.info("----------------------------------------");
            logger.info("NAME       : " + info.name);
            logger.info("VERSION    : " + info.version);
            logger.info("DATE       : " + info.date);
            logger.info("COPYRIGHT  : " + info.copyright);
            logger.info("----------------------------------------");

            // reset all pins on test harness before proceeding with this test
            TestHarnessPins reset = harness.reset();
            logger.info("");
            logger.info("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");

            // enable the I2C bus and device on the test harness hardware
            harness.enableI2C(I2C_TEST_HARNESS_BUS, I2C_TEST_HARNESS_DEVICE);
            logger.info("");
            logger.info("ENABLE I2C BUS [" + I2C_BUS + "] ON TEST HARNESS;");

            // close connection to  test harness
            harness.close();
        } catch (IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    @AfterAll
    public static void terminate() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestI2cUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");
    }

    @BeforeEach
    public void beforeEach() {
        // create test harness and PIGPIO instances
        pigpio = TestEnv.createPiGpio();

        // initialize test harness and PIGPIO instances
        pigpio.initialize();

        // set pin ALT0 modes for I2C BUS<1> usage on RPI3B
        pigpio.gpioSetMode(2, PiGpioMode.ALT0);
        pigpio.gpioSetMode(3, PiGpioMode.ALT0);

        // OPEN I2C
        handle = pigpio.i2cOpen(I2C_BUS, I2C_DEVICE);
    }

    @AfterEach
    public void afterEach() {
        // CLOSE I2C
        pigpio.i2cClose(handle);

        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @DisplayName("I2C :: Test register: BYTE (W/R)")
    @Order(1)
    public void testI2CSingleByteTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE BYTE");
        logger.info("----------------------------------------");

        // value cache
        byte[] values = new byte[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(5);
            logger.info("[TEST WRITE BYTE] :: REGISTER=" + register);

            Random rand = new Random();
            values[register] = (byte)rand.nextInt(0xFF); // max 8 bits (1 byte)
            logger.info(" (WRITE) >> VALUE = " + Byte.toUnsignedInt(values[register]));

            // WRITE :: SINGLE WORD TO REGISTER
            pigpio.i2cWriteByteData(handle, register, values[register]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(5);
            logger.info("[TEST READ BYTE] :: REGISTER=" + register);

            // READ :: SINGLE RAW WORD
            int value = pigpio.i2cReadByteData(handle, register);
            logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + Byte.toUnsignedInt(values[register]) + ")");

            // validate read value match with expected value that was writted to this register
            assertEquals(values[register], (byte) value, "I2C WORD VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: WORD (W/R)")
    @Order(1)
    public void testI2CSingleWordTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE WORD");
        logger.info("----------------------------------------");

        // value cache
        int[] values = new int[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(5);
            logger.info("[TEST WRITE WORD] :: REGISTER=" + register);

            Random rand = new Random();
            values[register] = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
            logger.info(" (WRITE) >> VALUE = " + values[register]);

            // WRITE :: SINGLE WORD TO REGISTER
            pigpio.i2cWriteWordData(handle, register, values[register]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(5);
            logger.info("[TEST READ WORD] :: REGISTER=" + register);

            // READ :: SINGLE RAW WORD
            int value = pigpio.i2cReadWordData(handle, register);
            logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ")");

            // validate read value match with expected value that was writted to this register
            assertEquals(values[register], value, "I2C WORD VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: WORD process (W/R)")
    @Order(2)
    public void testI2CProcessWordTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER PROCESS (W/R) <WORD>");
        logger.info("----------------------------------------");

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        // the process call should immediately return the same values back.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(10);
            logger.info("[TEST PROCESS WORD] :: REGISTER=" + register);

            Random rand = new Random();
            int generatedValue = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
            logger.info(" (WRITE) >> VALUE = " + generatedValue);

            // WRITE/READ :: SINGLE WORD TO REGISTER
            int returnedValue = pigpio.i2cProcessCall(handle, register, generatedValue);
            logger.info(" (READ)  << VALUE = " + returnedValue);

            // validate read value match with expected value that was writted to this register
            assertEquals(generatedValue, returnedValue, "I2C WORD VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: BLOCK (W/R)")
    @Order(3)
    public void testI2CBlockDataTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE BLOCK");
        logger.info("----------------------------------------");

        // value cache
        String[] values = new String[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(10);
            logger.info("[TEST WRITE BLOCK] :: REGISTER=" + register);
            values[register] = UUID.randomUUID().toString().substring(0, 30);
            logger.info(" (WRITE) >> VALUE = " + values[register]);

            // WRITE :: BLOCK TO REGISTER
            pigpio.i2cWriteBlockData(handle, register, values[register]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < 10; register++) {
            Thread.sleep(50);
            logger.info("[TEST READ BLOCK] :: REGISTER=" + register);


            // TODO ::PIGPIO ERROR: PI_BAD_SMBUS_CMD; SMBus command not supported by driver

//            // READ :: BLOCK
//            String value = pigpio.i2cReadBlockDataToString(handle, register);
//            logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[register] + ")");
//
//            // validate read value match with expected value that was writted to this register
//            Assert.assertEquals("I2C BLOCK VALUE MISMATCH",  values[register], value);
        }
    }

    @Test
    @DisplayName("I2C :: Test register: BLOCK process (W/R)")
    @Order(4)
    @Disabled("This test is disabled until we can research the problem with the 'I2CPK' command response.")
    public void testI2CProcessBlockTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER PROCESS (W/R) <BLOCK>");
        logger.info("----------------------------------------");

        // TODO :: THIS TEST IS NOT WORKING?  IS THIS SUPPORTED BY THE I2C HARDWARE/DRIVER?

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        // the process call should immediately return the same values back.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(10);
            logger.info("[TEST PROCESS BLOCK] :: REGISTER=" + register);

            // create a random test array of bytes between 5 and 25 bytes in length
            Random r = new Random();
            byte[] writeBuffer = new byte[r.nextInt(20) + 5];
            r.nextBytes(writeBuffer);
            logger.info(" (WRITE) >> VALUE = " + Arrays.toString(writeBuffer));

            // WRITE/READ :: BLOCK TO REGISTER
            byte[] readBuffer = new byte[writeBuffer.length];
            var result = pigpio.i2cBlockProcessCall(handle, register, writeBuffer, readBuffer);

            logger.info(" (READ)  << RESULT = " + result);
            logger.info(" (READ)  << VALUE = " + Arrays.toString(readBuffer));

            // validate read value match with expected value that was written to this register
            assertArrayEquals(writeBuffer, readBuffer, "I2C BLOCK VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: I2C BLOCK (W/R)")
    @Order(5)
    public void testI2CBlockI2CDataTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE BLOCK");
        logger.info("----------------------------------------");

        // value cache
        String[] values = new String[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {
            Thread.sleep(50);
            logger.info("[TEST WRITE BLOCK] :: REGISTER=" + register);
            values[register] = UUID.randomUUID().toString().substring(0, 30);
            logger.info(" (WRITE) >> VALUE = " + values[register]);

            // WRITE :: BLOCK TO REGISTER
            pigpio.i2cWriteI2CBlockData(handle, register, values[register]);
        }

        // READ back the random values from the I2C storage registers on the test harness and compare them.
        for(int register = 0; register < MAX_REGISTERS; register++) {
            logger.info("[TEST READ BLOCK] :: REGISTER=" + register + "; LENGTH=" + values[register].length());

            String valueString = "";

            // READ :: BLOCK
            Thread.sleep(100);
            byte[] buffer = new byte[values[register].length()];
            int result = pigpio.i2cReadI2CBlockData(handle, register, buffer);
            logger.info(" (READ)  << RESULT = " + result);
            if(result > 0) {
                valueString = new String(buffer, StandardCharsets.US_ASCII);
                logger.info(" (READ)  << VALUE = " + valueString + "; (EXPECTED=" + values[register] + ")");
            }

            // attempt #2
            if(!values[register].equals(valueString)){
                Thread.sleep(100);
                result = pigpio.i2cReadI2CBlockData(handle, register, buffer);
                logger.info(" (READ)  << RESULT = " + result);
                if(result > 0) {
                    valueString = new String(buffer, StandardCharsets.US_ASCII);
                    logger.info(" (READ)  << VALUE = " + valueString + "; (EXPECTED=" + values[register] + ")");
                }
            }

            // attempt #3
            if(!values[register].equals(valueString)){
                Thread.sleep(500);
                result = pigpio.i2cReadI2CBlockData(handle, register, buffer);
                logger.info(" (READ)  << RESULT = " + result);
                if(result > 0) {
                    valueString = new String(buffer, StandardCharsets.US_ASCII);
                    logger.info(" (READ)  << VALUE = " + valueString + "; (EXPECTED=" + values[register] + ")");
                }
            }

            // validate read value match with expected value that was writted to this register
            assertEquals(values[register], valueString, "I2C BLOCK VALUE MISMATCH");
        }
    }
}
