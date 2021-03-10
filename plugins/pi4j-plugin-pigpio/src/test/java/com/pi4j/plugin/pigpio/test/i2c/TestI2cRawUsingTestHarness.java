package com.pi4j.plugin.pigpio.test.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestI2cRawUsingTestHarness.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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

import java.io.IOException;
import java.nio.ByteBuffer;

import com.pi4j.io.i2c.I2C;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.i2c.PiGpioI2CProvider;
import com.pi4j.plugin.pigpio.provider.i2c.PiGpioI2CProviderImpl;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DisplayName("PIGPIO Plugin :: Test I2C Raw Communication using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestI2cRawUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestI2cRawUsingTestHarness.class);

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int I2C_TEST_HARNESS_BUS    = 0;
    private static int I2C_TEST_HARNESS_DEVICE = 0x04;

    private static byte SAMPLE_BYTE = 0x0d;
    private static int SAMPLE_WORD = 0xFFFA;
    private static byte[] SAMPLE_BYTE_ARRAY = new byte[] { 0,1,2,3,4,5,6,7,8,9 };
    private static byte[] SAMPLE_BUFFER_ARRAY = new byte[] { 10,11,12,13,14,15,16,17,18,19 };
    private static ByteBuffer SAMPLE_BUFFER = ByteBuffer.wrap(SAMPLE_BUFFER_ARRAY);
    private static String SAMPLE_STRING = "Hello World!";

    private PiGpio piGpio;
    private I2C i2c;

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
        // create I2C config
        var config  = I2C.newConfigBuilder(null)
                .id("my-i2c-bus")
                .name("My I2C Bus")
                .bus(I2C_BUS)
                .device(I2C_DEVICE)
                .build();

        // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
        piGpio = TestEnv.createPiGpio();

        // initialize the PiGpio library
        piGpio.initialize();

        // create I2C provider instance to test with
        PiGpioI2CProvider provider = new PiGpioI2CProviderImpl(piGpio);

        // use the provider to create a I2C instance
        i2c = provider.create(config);
    }

    @AfterEach
    public void afterEach() {
        // close I2C (if needed)
        if(i2c.isOpen())
            i2c.close();

        // shutdown the PiGpio library after each test
        piGpio.shutdown();
    }

    @Test
    @DisplayName("I2C :: Verify I2C Instance")
    @Order(1)
    public void testI2CInstance() {
        // ensure that the I2C instance is not null;
        assertNotNull(i2c);

        // ensure connection is open
        assertTrue(i2c.isOpen(), "I2C INSTANCE IS NOT OPEN");
    }

    @Test
    @DisplayName("I2C :: Test BYTE (WRITE)")
    @Order(2)
    public void testI2CSingleByteWrite() {
        // write a single byte to the raw I2C device (not to a register)
        i2c.write(SAMPLE_BYTE);
    }

    @Test
    @DisplayName("I2C :: Test BYTE (READ)")
    @Order(3)
    public void testI2CSingleByteRead() {
        // read single byte from the raw I2C device (not from a register)
        assertEquals(SAMPLE_BYTE, i2c.readByte());
    }

    @Test
    @DisplayName("I2C :: Test BYTE-ARRAY (WRITE)")
    @Order(6)
    public void testI2CByteArrayWrite() {
        // write an array of data bytes to the raw I2C device (not to a register)
        i2c.write(SAMPLE_BYTE_ARRAY);
    }

    @Test
    @DisplayName("I2C :: Test BYTE-ARRAY (READ)")
    @Order(7)
    public void testI2CByteArrayRead() {
        // read an array of data bytes from the raw I2C device (not from a register)
        byte[] byteArray = i2c.readNBytes(SAMPLE_BYTE_ARRAY.length);
        assertArrayEquals(SAMPLE_BYTE_ARRAY, byteArray);
    }

    @Test
    @DisplayName("I2C :: Test BYTE-BUFFER (WRITE)")
    @Order(8)
    public void testI2CByteBufferWrite() {
        // write a buffer of data bytes to the raw I2C device (not to a register)
        i2c.write(SAMPLE_BUFFER);
    }

    @Test
    @DisplayName("I2C :: Test BYTE-BUFFER (READ)")
    @Order(9)
    public void testI2CByteBufferRead() {
        // read a buffer of data bytes from the raw I2C device (not from a register)
        ByteBuffer buffer = i2c.readByteBuffer(SAMPLE_BUFFER.capacity());
        assertArrayEquals(SAMPLE_BUFFER.array(), buffer.array());
    }

    @Test
    @DisplayName("I2C :: Test ASCII STRING (WRITE)")
    @Order(10)
    public void testI2CAsciiStringWrite() {
        // write a string of data to the raw I2C device (not to a register)
        i2c.write(SAMPLE_STRING);
    }

    @Test
    @DisplayName("I2C :: Test ASCII STRING (READ)")
    @Order(11)
    public void testI2CAsciiStringRead() {
        // read a string of data from the raw I2C device (not from a register)
        String testString = i2c.readString(SAMPLE_STRING.length());
        assertEquals(SAMPLE_STRING, testString);
    }
}
