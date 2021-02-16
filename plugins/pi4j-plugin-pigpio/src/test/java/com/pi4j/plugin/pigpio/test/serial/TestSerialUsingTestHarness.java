package com.pi4j.plugin.pigpio.test.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestSerialUsingTestHarness.java
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

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.LifecycleException;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.Serial;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProvider;
import com.pi4j.plugin.pigpio.provider.serial.PiGpioSerialProviderImpl;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PIGPIO Plugin :: Test Serial Communication using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSerialUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestSerialUsingTestHarness.class);

    private static String SERIAL_DEVICE = "/dev/ttyS0";
    private static int BAUD_RATE = Baud._38400.value();
    private static int TEST_HARNESS_UART = 3;

    private static byte SAMPLE_BYTE = 0x0d;
    private static int SAMPLE_WORD = 0xFFFA;
    private static byte[] SAMPLE_BYTE_ARRAY = new byte[] { 0,1,2,3,4,5,6,7,8,9 };
    private static byte[] SAMPLE_BUFFER_ARRAY = new byte[] { 10,11,12,13,14,15,16,17,18,19 };
    private static ByteBuffer SAMPLE_BUFFER = ByteBuffer.wrap(SAMPLE_BUFFER_ARRAY);
    private static String SAMPLE_STRING = "Hello World!";

    private static PiGpio piGpio;
    private static Context pi4j;
    private static Serial serial;

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
            logger.info("");
            logger.info("ENABLE SERIAL UART [" + TEST_HARNESS_UART + "] ON TEST HARNESS; BAUD=" + BAUD_RATE);

            // close connection to test harness
            harness.close();

            // create SERIAL config
            var config  = Serial.newConfigBuilder(pi4j)
                    .id("my-serial-port")
                    .name("My Serial Port")
                    .device(SERIAL_DEVICE)
                    .baud8N1(BAUD_RATE)
                    .build();


            // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
            piGpio = TestEnv.createPiGpio();

            // initialize the PiGpio library
            piGpio.initialize();

            // create SERIAL provider instance to test with
            PiGpioSerialProvider provider = new PiGpioSerialProviderImpl(piGpio);

            // initialize Pi4J instance with this single provider
            pi4j = Pi4J.newContextBuilder().add(provider).build();

            // create serial instance
            serial = pi4j.create(config);
        } catch (IOException e){
            e.printStackTrace();
        } catch (Pi4JException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException, LifecycleException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestSerialUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // close down serial
        //if(serial.isOpen())
        serial.close();;

        // shutdown the PiGpio library
        piGpio.shutdown();

        // shutdown Pi4J
        pi4j.shutdown();
    }

    @BeforeEach
    public void beforeEach() throws Exception {

    }

    @AfterEach
    public void afterEach() throws Exception {
    }

    @Test
    @DisplayName("SERIAL :: Verify SERIAL Instance")
    @Order(1)
    public void testSerialInstance() throws Exception {
        // ensure that the SERIAL instance is not null;
        assertNotNull(serial);

        // ensure connection is open
        assertTrue(serial.isOpen(), "SERIAL INSTANCE IS NOT OPEN");
    }

    @Test
    @DisplayName("SERIAL :: Test BYTE (WRITE)")
    @Order(2)
    public void testSerialSingleByteWrite() throws Exception {
        // write a single byte to the serial device
        serial.write(SAMPLE_BYTE);
    }

    @Test
    @DisplayName("SERIAL :: Test BYTE (READ)")
    @Order(3)
    public void testSerialSingleByteRead() throws Exception {
        // read single byte from the serial device
        assertEquals(SAMPLE_BYTE, serial.readByte());
    }

    @Test
    @DisplayName("SERIAL :: Test BYTE-ARRAY (WRITE)")
    @Order(4)
    public void testSerialByteArrayWrite() throws Exception {
        // write an array of data bytes to the serial device
        serial.write(SAMPLE_BYTE_ARRAY);
    }

    @Test
    @DisplayName("SERIAL :: Test BYTE-ARRAY (READ)")
    @Order(5)
    public void testSerialByteArrayRead() throws Exception {
        // read an array of data bytes from the serial device
        byte[] byteArray = serial.readNBytes(SAMPLE_BYTE_ARRAY.length);
        assertArrayEquals(SAMPLE_BYTE_ARRAY, byteArray);
    }

    @Test
    @DisplayName("SERIAL :: Test BYTE-BUFFER (WRITE)")
    @Order(6)
    public void testSerialByteBufferWrite() throws Exception {
        // write a buffer of data bytes to the serial device
        serial.write(SAMPLE_BUFFER);
    }

    @Test
    @DisplayName("SERIAL :: Test BYTE-BUFFER (READ)")
    @Order(7)
    public void testSerialByteBufferRead() throws Exception {
        // read a buffer of data bytes from the serial device
        ByteBuffer buffer = serial.readByteBuffer(SAMPLE_BUFFER.capacity());
        assertArrayEquals(SAMPLE_BUFFER.array(), buffer.array());
    }

    @Test
    @DisplayName("SERIAL :: Test ASCII STRING (WRITE)")
    @Order(8)
    public void testSerialAsciiStringWrite() throws Exception {
        // write a string of data to the serial device
        serial.write(SAMPLE_STRING);
    }

    @Test
    @DisplayName("SERIAL :: Test ASCII STRING (READ)")
    @Order(9)
    public void testSerialAsciiStringRead() throws Exception {
        // read a string of data from the serial device
        String testString = serial.readString(SAMPLE_STRING.length());
        assertEquals(SAMPLE_STRING, testString);
    }

}
