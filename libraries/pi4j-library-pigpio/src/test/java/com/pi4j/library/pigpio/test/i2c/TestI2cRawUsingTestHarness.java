package com.pi4j.library.pigpio.test.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestI2cRawUsingTestHarness.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
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

@DisplayName("PIGPIO Library :: Test I2C Raw Communication")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestI2cRawUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestI2cRawUsingTestHarness.class);

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int I2C_TEST_HARNESS_BUS    = 0;
    private static int I2C_TEST_HARNESS_DEVICE = 0x04;

    private PiGpio pigpio;
    private int handle;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestI2cRawUsingTestHarness.class.getName() + ")");
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
            // (enable RAW mode data processing)
            harness.enableI2C(I2C_TEST_HARNESS_BUS, I2C_TEST_HARNESS_DEVICE, true);
            logger.info("");
            logger.info("ENABLE I2C BUS [" + I2C_BUS + "] ON TEST HARNESS;");

            // close connection to test harness
            harness.close();
        } catch (IOException e){
            logger.error(e.getMessage(), e);
        }
    }

    @AfterAll
    public static void terminate() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestI2cRawUsingTestHarness.class.getName() + ") ");
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
    @DisplayName("I2C :: Test SINGLE-BYTE (W/R)")
    @Order(1)
    public void testI2CSingleByteTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C SINGLE BYTE RAW READ/WRITE");
        logger.info("----------------------------------------");

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 255; b++) {
            logger.info("[TEST WRITE/READ SINGLE BYTE]");

            // WRITE :: SINGLE RAW BYTE
            logger.info(" (WRITE) >> VALUE = " + b);
            pigpio.i2cWriteByte(handle, (byte)b);
            Thread.sleep(5);

            // READ :: SINGLE RAW BYTE
            byte rx = (byte)pigpio.i2cReadByte(handle);
            logger.info(" (READ)  << VALUE = " + Byte.toUnsignedInt(rx));

            assertEquals(b, Byte.toUnsignedInt(rx), "I2C BYTE VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test MULTI-BYTE (W/R)")
    @Order(2)
    public void testI2CMultiByteTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C MULTI-BYTE RAW READ/WRITE");
        logger.info("----------------------------------------");

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 0; x < 50; x++) {
            logger.info("[TEST WRITE/READ MULTI-BYTE]");

            String value = UUID.randomUUID().toString().substring(0, 8);

            // WRITE :: RAW MULTI-BYTE
            logger.info(" (WRITE) >> VALUE = " + value);
            pigpio.i2cWriteDevice(handle, value);
            Thread.sleep(20);

            // READ :: RAW MULTI-BYTE
            byte[] buffer = new byte[value.length()];
            var result = pigpio.i2cReadDevice(handle, buffer);

            logger.info(" (READ)  << RESULT = " + result);
            if(result > 0) {
                String resultString = new String(buffer);
                logger.info(" (READ)  << VALUE = " + resultString);
                assertEquals(value, resultString, "I2C MULTI-BYTE VALUE MISMATCH");
            } else {
                fail("I2C READ FAILED: " + result);
            }

            Thread.sleep(20);
        }
    }
}
