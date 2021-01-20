package com.pi4j.test.io.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  I2CRegisterDataTest.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CRegister;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class I2CRegisterDataTest {

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int NUMBER_OF_REGISTERS = 100;

    private Context pi4j;
    private List<TestData> samples = new ArrayList<>();
    private List<I2CRegister> registers = new ArrayList<>();

    private static byte SAMPLE_BYTE = 0x0d;
    private static byte[] SAMPLE_BYTE_ARRAY = new byte[] { 0,1,2,3,4,5,6,7,8,9 };
    private static byte[] SAMPLE_BUFFER_ARRAY = new byte[] { 10,11,12,13,14,15,16,17,18,19 };
    private static ByteBuffer SAMPLE_BUFFER = ByteBuffer.wrap(SAMPLE_BUFFER_ARRAY);
    private static String SAMPLE_STRING = "Hello World!";

    @BeforeAll
    public void beforeTest() throws Pi4JException {
        // Initialize Pi4J with auto context
        // An auto context enabled AUTO-DETECT loading
        // which will load any detected Pi4J extension
        // libraries (Platforms and Providers) from the class path
        pi4j = Pi4J.newAutoContext();
    }

    @AfterAll
    public void afterTest() {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    public class TestData{
        public final byte byt;
        public final int word;
        public final byte[] array;
        public final ByteBuffer buffer;
        public final String str;

        public TestData(){
            Random rand = new Random();
            byt = (byte) rand.nextInt(255);
            word = rand.nextInt(65536);
            this.array = new byte[rand.nextInt(32)];
            rand.nextBytes(this.array);
            byte bufferArray[] = new byte[rand.nextInt(32)];
            rand.nextBytes(bufferArray);
            this.buffer = ByteBuffer.wrap(bufferArray);
            this.str = UUID.randomUUID().toString();
        }
    }

    @Test
    public void testRegisterDataWriteRead() throws Exception {

        // create I2C config
        var config  = I2C.newConfigBuilder(pi4j)
                .id("my-i2c-bus")
                .name("My I2C Bus")
                .bus(I2C_BUS)
                .device(I2C_DEVICE)
                .build();

        // use try-with-resources to auto-close I2C when complete
        try (var i2c = pi4j.i2c().create(config);) {

            // ensure that the I2C instance is not null;
            assertNotNull(i2c);

            // initialize sample data sets and register objects
            for(int n = 0; n < NUMBER_OF_REGISTERS; n++) {
                samples.add(new TestData());
                registers.add(i2c.register(n));
            }

            // write randomized sample data to all test registers
            for(int n = 0; n < NUMBER_OF_REGISTERS; n++) {
                writeRegister(registers.get(n), samples.get(n));
            }

            // read and compare sample data from all test registers
            for(int n = 0; n < NUMBER_OF_REGISTERS; n++) {
                readRegister(registers.get(n), samples.get(n));
            }
        }
    }

    public void writeRegister(I2CRegister register, TestData sample) throws Exception {

        // write a single byte (8-bit) value to the raw I2C device (not to a register)
        register.write(sample.byt);

        // write a single word (16-bit) value to the raw I2C device (not to a register)
        register.writeWord(sample.word);

        // write an array of data bytes to the raw I2C device (not to a register)
        register.write(sample.array);

        // write a buffer of data bytes to the raw I2C device (not to a register)
        register.write(sample.buffer);

        // write a string of data to the raw I2C device (not to a register)
        register.write(sample.str);
    }

    public void readRegister(I2CRegister register, TestData sample) throws Exception {

        // read single byte (8-bit) value from the raw I2C device (not from a register)
        byte b = (byte)register.read();
        assertEquals(sample.byt, b);

        // read single word (16-bit) value from the raw I2C device (not from a register)
        int w = register.readWord();
        assertEquals(sample.word, w);

        // read an array of data bytes from the raw I2C device (not from a register)
        byte byteArray[] = new byte[sample.array.length];
        register.read(byteArray, 0, byteArray.length);
        assertArrayEquals(sample.array, byteArray);

        // read a buffer of data bytes from the raw I2C device (not from a register)
        ByteBuffer buffer = ByteBuffer.allocate(sample.buffer.capacity());
        register.read(buffer, 0, buffer.capacity());
        assertArrayEquals(sample.buffer.array(), buffer.array());

        // read a string of data from the raw I2C device (not from a register)
        String testString = register.readString(sample.str.length());
        assertEquals(sample.str, testString);
    }

}
