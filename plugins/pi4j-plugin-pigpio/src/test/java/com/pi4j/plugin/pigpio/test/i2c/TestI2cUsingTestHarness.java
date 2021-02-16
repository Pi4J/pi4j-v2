package com.pi4j.plugin.pigpio.test.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
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

import com.pi4j.io.exception.IOReadException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CRegister;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.i2c.PiGpioI2CProvider;
import com.pi4j.plugin.pigpio.provider.i2c.PiGpioI2CProviderImpl;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import com.pi4j.util.StringUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PIGPIO Plugin :: Test I2C Communication using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestI2cUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestI2cUsingTestHarness.class);

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int I2C_TEST_HARNESS_BUS    = 0;
    private static int I2C_TEST_HARNESS_DEVICE = 0x04;
    private static int MAX_REGISTERS = 100;

    private PiGpio piGpio;
    private I2C i2c;

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
        logger.info("TERMINATE TEST (" + TestI2cUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");
    }

    @BeforeEach
    public void beforeEach() throws Exception {
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
    public void afterEach() throws Exception {
        // close I2C (if needed)
        if(i2c.isOpen())
            i2c.close();

        // shutdown the PiGpio library after each test
        piGpio.shutdown();
    }

    @Test
    @DisplayName("I2C :: Test register: BYTE (W/R)")
    @Order(1)
    public void testI2CByteWriteRead() throws IOException, InterruptedException, IOReadException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE BYTE");
        logger.info("----------------------------------------");

        // value cache
        byte[] values = new byte[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST WRITE BYTE] :: REGISTER=" + register.address());

            Random rand = new Random();
            values[r] = (byte) rand.nextInt(0xFF); // max 8 bits (1 bytes)
            logger.info(" (WRITE) >> VALUE = 0x" + StringUtil.toHexString(values[r]));

            // WRITE :: SINGLE BYTE TO REGISTER
            register.write(values[r]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST READ BYTE] :: REGISTER=" + register.address());

            // READ :: SINGLE RAW BYTE
            byte value = register.readByte();
            logger.info(" (READ)  << VALUE = 0x" + StringUtil.toHexString(value) +
                    "; (EXPECTED=0x" + StringUtil.toHexString(values[r]) + ")");

            // SECOND ATTEMPT
            if(value != values[r]){
                Thread.sleep(500);
                value = register.readByte();
                logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + StringUtil.toHexString(values[r]) + ") <2ND ATTEMPT>");

                // THIRD ATTEMPT
                if(value != values[r]){
                    Thread.sleep(1000);
                    value = register.readByte();
                    logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + StringUtil.toHexString(values[r]) + ") <3RD ATTEMPT>");
                }
            }

            // validate read value match with expected value that was writted to this register
            assertEquals(values[r], value, "I2C BYTE VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: WORD (W/R)")
    @Order(2)
    public void testI2CWordWriteRead() throws IOException, InterruptedException, IOReadException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE WORD");
        logger.info("----------------------------------------");

        // value cache
        int[] values = new int[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST WRITE WORD] :: REGISTER=" + register.address());

            Random rand = new Random();
            values[r] = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
            logger.info(" (WRITE) >> VALUE = " + values[r]);

            // WRITE :: SINGLE WORD TO REGISTER
            register.writeWord(values[r]);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST READ WORD] :: REGISTER=" + register.address());

            // READ :: SINGLE RAW WORD
            int value = register.readWord();
            logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[r] + ")");

            // SECOND ATTEMPT
            if(value != values[r]){
                Thread.sleep(500);
                value = register.readWord();
                logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[r] + ") <2ND ATTEMPT>");

                // THIRD ATTEMPT
                if(value != values[r]){
                    Thread.sleep(1000);
                    value = register.readWord();
                    logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[r] + ") <3RD ATTEMPT>");
                }
            }

            // validate read value match with expected value that was writted to this register
            assertEquals(values[r], value, "I2C WORD VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: BYTE-ARRAY (W/R)")
    @Order(3)
    public void testI2CByteArrayWriteRead() throws IOException, InterruptedException, IOReadException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE BYTE-ARRAY");
        logger.info("----------------------------------------");

        // value cache
        List<byte[]> values = new ArrayList<>();

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST WRITE BYTE-ARRAY] :: REGISTER=" + register.address());

            UUID.randomUUID().toString().substring(0, 30);

            Random rand = new Random();
            byte[] temp = new byte[10];
            rand.nextBytes(temp);
            values.add(temp);
            logger.info(" (WRITE) >> VALUE = [0x" + StringUtil.toHexString(temp) + "]");

            // WRITE :: BYTE-ARRAY TO REGISTER
            register.write(temp);
            Thread.sleep(10);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST READ BYTE-ARRAY] :: REGISTER=" + register.address());

            // READ :: BYTE-ARRAY
            byte[] value = register.readNBytes(10);
            byte[] expected = values.get(r);

            logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(value) +
                    "; (EXPECTED=" + StringUtil.toHexString(expected) + ")");

            if(!Arrays.equals(expected, value)){
                Thread.sleep(500);
                // READ :: BYTE-BUFFER
                register.readNBytes(10);
                logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(value) +
                        "; (EXPECTED=" + StringUtil.toHexString(expected) + ") <ATTEMPT #2>");

                if(!Arrays.equals(expected, value)){
                    Thread.sleep(1000);
                    // READ :: BYTE-BUFFER
                    register.readNBytes(10);
                    logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(value) +
                            "; (EXPECTED=" + StringUtil.toHexString(expected) + ") <ATTEMPT #3>");

                }
            }
            // validate read value match with expected value that was writted to this register
            assertArrayEquals(expected, value, "I2C BYTE-ARRAY VALUE MISMATCH");
            Thread.sleep(20);
        }
    }

    @Test
    @DisplayName("I2C :: Test register: BYTE-BUFFER (W/R)")
    @Order(4)
    public void testI2CByteBufferWriteRead() throws IOException, InterruptedException, IOReadException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER READ/WRITE BYTE BUFFER");
        logger.info("----------------------------------------");

        // value cache
        List<ByteBuffer> values = new ArrayList<>();

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST WRITE BYTE-BUFFER] :: REGISTER=" + register.address());

            UUID.randomUUID().toString().substring(0, 30);

            Random rand = new Random();
            byte[] temp = new byte[10];
            rand.nextBytes(temp);
            ByteBuffer buffer = ByteBuffer.wrap(temp);
            values.add(buffer);
            logger.info(" (WRITE) >> VALUE = [0x" + StringUtil.toHexString(buffer) + "]");

            // WRITE :: BYTE-BUFFER TO REGISTER
            register.write(temp);
        }

        // READ back the 10 random values from the I2C storage registers on the test harness and compare them.
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST READ BYTE-BUFFER] :: REGISTER=" + register.address());

            // READ :: BYTE-BUFFER
            ByteBuffer value = register.readByteBuffer(10);
            ByteBuffer expected = values.get(r);

            logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(value) +
                    "; (EXPECTED=" + StringUtil.toHexString(expected) + ")");

            if(!Arrays.equals(expected.array(), value.array())){
                Thread.sleep(500);
                // READ :: BYTE-BUFFER
                value = register.readByteBuffer(10);
                logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(value) +
                        "; (EXPECTED=" + StringUtil.toHexString(expected) + ") <ATTEMPT #2>");

                if(!Arrays.equals(expected.array(), value.array())){
                    Thread.sleep(500);
                    // READ :: BYTE-BUFFER
                    value = register.readByteBuffer(10);
                    logger.info(" (READ)  << VALUE = " + StringUtil.toHexString(value) +
                            "; (EXPECTED=" + StringUtil.toHexString(expected) + ") <ATTEMPT #3>");

                }
            }

            // validate read value match with expected value that was writted to this register
            assertArrayEquals(expected.array(), value.array(), "I2C BYTE-BUFFER VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("I2C :: Test register: (EXCHANGE) WORD (W->R)")
    @Order(5)
    public void testI2CWordExchange() throws IOException, InterruptedException, IOReadException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST I2C REGISTER EXCHANGE WORD");
        logger.info("----------------------------------------");

        // value cache
        int[] values = new int[MAX_REGISTERS];

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int r = 0; r < MAX_REGISTERS; r++) {
            I2CRegister register = i2c.register(r);
            logger.info("[TEST WRITE WORD] :: REGISTER=" + register.address());

            Random rand = new Random();
            values[r] = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
            logger.info(" (WRITE) >> VALUE = " + values[r]);

            // EXCHANGE :: SINGLE WORD TO REGISTER (this will write a word value and immediately read back the word value)
            int value = register.writeReadWord(values[r]);
            logger.info(" (READ)  << VALUE = " + value + "; (EXPECTED=" + values[r] + ")");

            // validate read value match with expected value that was writted to this register
            assertEquals(values[r], value, "I2C WORD VALUE MISMATCH");
        }
    }
}
