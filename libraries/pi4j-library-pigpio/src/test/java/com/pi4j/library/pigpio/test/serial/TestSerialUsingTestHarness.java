package com.pi4j.library.pigpio.test.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestSerialUsingTestHarness.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.library.pigpio.util.StringUtil;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PIGPIO Library :: Test Serial Communication")
public class TestSerialUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestSerialUsingTestHarness.class);

    private static String SERIAL_DEVICE = "/dev/ttyS0";
    private static int BAUD_RATE = 9600;
    private static int TEST_HARNESS_UART = 3;

    private PiGpio pigpio;
    private int handle;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestSerialUsingTestHarness.class.getName() + ")");
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

            // enable the Serial Echo (Loopback) function on the test harness for these tests
            harness.enableSerialEcho(TEST_HARNESS_UART,  BAUD_RATE);

            // close connection to test harness
            harness.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestSerialUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        // create test harness and PIGPIO instances
        pigpio = TestEnv.createPiGpio();

        // initialize test harness and PIGPIO instances
        pigpio.initialize();

        // set pin ALT5 modes for SERIAL RX & TX PINS on RPI3B
        pigpio.gpioSetMode(14, PiGpioMode.ALT5);
        pigpio.gpioSetMode(15, PiGpioMode.ALT5);

        // OPEN SERIAL PORT
        handle = pigpio.serOpen(SERIAL_DEVICE, BAUD_RATE);
    }

    @AfterEach
    public void afterEach() throws IOException {

        // CLOSE SERIAL PORT
        pigpio.serClose(handle);

        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @DisplayName("SERIAL :: Test SINGLE-BYTE (W/R)")
    public void testSerialSingleByteTxRx() throws IOException {
        logger.info("");
        logger.info("--------------------------------------------");
        logger.info("TEST SERIAL PORT SINGLE BYTE RAW READ/WRITE");
        logger.info("--------------------------------------------");

        // drain any pending bytes in buffer
        pigpio.serDrain(handle);

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            logger.info("[TEST WRITE/READ SINGLE BYTE]");
            // WRITE :: SINGLE RAW BYTE
            logger.info(" (WRITE) >> VALUE = 0x" + Integer.toHexString(b));
            pigpio.serWriteByte(handle, (byte)b);

            // READ :: NUMBER OF BYTES AVAILABLE TO READ
            int available = pigpio.serDataAvailable(handle);
            logger.info(" (READ)  << AVAIL = " + available);
            assertEquals(1, available, "SERIAL BYTE VALUE MISMATCH");

             // READ :: SINGLE RAW BYTE
            int rx = pigpio.serReadByte(handle);
            logger.info(" (READ)  << VALUE = 0x" + Integer.toHexString(rx));
            assertEquals(b, rx, "SERIAL BYTE VALUE MISMATCH");
            logger.info("");
        }
    }

    @Test
    @DisplayName("SERIAL :: Test MULTI-BYTE (W/R)")
    public void testSerialMultiByteTxRx() throws IOException, InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST SERIAL MULTI-BYTE READ/WRITE");
        logger.info("----------------------------------------");

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 0; x < 50; x++) {
            logger.info("[TEST WRITE/READ MULTI-BYTE]");

            // drain any pending bytes in buffer
            pigpio.serDrain(handle);

            // Arduino max serial buffer length is 32
            // create a random series of bytes up to 32 bytes long
            Random r = new Random();
            int len = r.nextInt((32 - 4) + 1) + 4;
            byte[] testData = new byte[len];
            r.nextBytes(testData);

            // WRITE :: MULTI-BYTE
            logger.info(" (WRITE) >> VALUE = " + StringUtil.toHexString(testData));
            pigpio.serWrite(handle, testData);

            // take a breath while buffer catches up
            Thread.sleep(50);

            // READ :: NUMBER OF BYTES AVAILABLE TO READ
            int available = pigpio.serDataAvailable(handle);
            logger.info(" (READ)  << AVAIL = " + available);
            assertEquals(testData.length, available, "SERIAL READ AVAIL MISMATCH");

            // take a breath while buffer catches up
            Thread.sleep(50);

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[available];
            int bytesRead = pigpio.serRead(handle, readBuffer, available);
            logger.info(" (READ)  << BYTES READ = " + bytesRead);
            logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(readBuffer));

            Thread.sleep(50);

            assertArrayEquals(testData, readBuffer, "SERIAL MULTI-BYTE VALUE MISMATCH");
        }
    }
}
