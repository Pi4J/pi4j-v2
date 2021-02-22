package com.pi4j.plugin.pigpio.test.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestSpiUsingTestHarness.java
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

import static org.junit.jupiter.api.Assertions.*;

import java.nio.ByteBuffer;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.spi.Spi;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProvider;
import com.pi4j.plugin.pigpio.provider.spi.PiGpioSpiProviderImpl;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DisplayName("PIGPIO Plugin :: Test SPI Communication using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSpiUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestSpiUsingTestHarness.class);

    private static int SPI_CHANNEL = 0;
    private static int BAUD_RATE = 100000;
    private static int TEST_HARNESS_SPI_CHANNEL = 10;

    private static byte SAMPLE_BYTE = 0x0d;
    private static int SAMPLE_WORD = 0xFFFA;
    private static byte[] SAMPLE_BYTE_ARRAY = new byte[] { 0,1,2,3,4,5,6,7,8,9 };
    private static byte[] SAMPLE_BUFFER_ARRAY = new byte[] { 10,11,12,13,14,15,16,17,18,19 };
    private static ByteBuffer SAMPLE_BUFFER = ByteBuffer.wrap(SAMPLE_BUFFER_ARRAY);
    private static String SAMPLE_STRING = "Hello World!";

    private static PiGpio piGpio;
    private static Context pi4j;
    private static Spi spi;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestSpiUsingTestHarness.class.getName() + ")");
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

            // enable the SPI Echo (Loopback) function on the test harness for these tests
            harness.enableSpiEcho(TEST_HARNESS_SPI_CHANNEL);
            logger.info("");
            logger.info("ENABLE SPI CHANNEL [" + TEST_HARNESS_SPI_CHANNEL + "] ON TEST HARNESS; BAUD=" + BAUD_RATE);

            // close connection to test harness
            harness.close();

            // create SPI config
            var config  = Spi.newConfigBuilder(pi4j)
                    .id("my-spi")
                    .name("My SPI")
                    .address(SPI_CHANNEL)
                    .baud(BAUD_RATE)
                    .build();

            // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
            piGpio = TestEnv.createPiGpio();

            // initialize the PiGpio library
            piGpio.initialize();


            // create SPI provider instance to test with
            PiGpioSpiProvider provider = new PiGpioSpiProviderImpl(piGpio);

            // initialize Pi4J instance with this single provider
            pi4j = Pi4J.newContextBuilder().add(provider).build();

            // create SPI instance
            spi = pi4j.create(config);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterAll
    public static void terminate() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestSpiUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // close down SPI channel
        spi.close();

        // shutdown the PiGpio library
        piGpio.shutdown();

        // shutdown Pi4J
        pi4j.shutdown();
    }

    @BeforeEach
    public void beforeEach() {

    }

    @AfterEach
    public void afterEach() {
    }

    @Test
    @DisplayName("SPI :: Verify SPI Instance")
    @Order(1)
    public void testSpiInstance() {
        // ensure that the SPI instance is not null;
        assertNotNull(spi);

        // ensure connection is open
        assertTrue(spi.isOpen(), "SPI INSTANCE IS NOT OPEN");
    }

    @Test
    @DisplayName("SPI :: Test BYTE (WRITE)")
    @Order(2)
    public void testSpiSingleByteWrite() {
        // write a single byte to the SPI device
        spi.write(SAMPLE_BYTE);
    }

    @Test
    @DisplayName("SPI :: Test BYTE (READ)")
    @Order(3)
    public void testSpiSingleByteRead() {
        // read single byte from the SPI device
        assertEquals(SAMPLE_BYTE, spi.readByte());
    }

    @Test
    @DisplayName("SPI :: Test BYTE-ARRAY (WRITE)")
    @Order(4)
    public void testSpiByteArrayWrite() {
        // write an array of data bytes to the SPI device
        spi.write(SAMPLE_BYTE_ARRAY);
    }

    @Test
    @DisplayName("SPI :: Test BYTE-ARRAY (READ)")
    @Order(5)
    public void testSpiByteArrayRead() {
        // read an array of data bytes from the SPI device
        byte[] byteArray = spi.readNBytes(SAMPLE_BYTE_ARRAY.length);
        assertEquals(SAMPLE_BYTE_ARRAY[SAMPLE_BYTE_ARRAY.length-1], byteArray[0]);
    }

    @Test
    @DisplayName("SPI :: Test BYTE-BUFFER (WRITE)")
    @Order(6)
    public void testSpiByteBufferWrite() {
        // write a buffer of data bytes to the SPI device
        spi.write(SAMPLE_BUFFER);
    }

    @Test
    @DisplayName("SPI :: Test BYTE-BUFFER (READ)")
    @Order(7)
    public void testSpiByteBufferRead() {
        // read a buffer of data bytes from the SPI device
        ByteBuffer buffer = spi.readByteBuffer(SAMPLE_BUFFER.capacity());
        assertEquals(SAMPLE_BUFFER.get(SAMPLE_BUFFER.capacity()-1), buffer.get(0));
    }

    @Test
    @DisplayName("SPI :: Test ASCII STRING (WRITE)")
    @Order(8)
    public void testSpiAsciiStringWrite() {
        // write a string of data to the SPI device
        spi.write(SAMPLE_STRING);
    }

    @Test
    @DisplayName("SPI :: Test ASCII STRING (READ)")
    @Order(9)
    public void testSpiAsciiStringRead() {
        // read a string of data from the SPI device
        String testString = spi.readString(SAMPLE_STRING.length());
        assertEquals(SAMPLE_STRING.substring(SAMPLE_STRING.length()-1), testString.substring(0, 1));
    }
}
