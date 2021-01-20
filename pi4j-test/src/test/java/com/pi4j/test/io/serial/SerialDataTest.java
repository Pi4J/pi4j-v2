package com.pi4j.test.io.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  SerialDataTest.java
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
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.serial.Serial;
import com.pi4j.plugin.mock.provider.serial.MockSerialProvider;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SerialDataTest {

    private static Context pi4j;
    private static Serial serial;
    private static String SERIAL_DEVICE = "mock-serial-port";

    private static byte SAMPLE_BYTE = 0x0d;
    private static byte[] SAMPLE_BYTE_ARRAY = new byte[] { 0,1,2,3,4,5,6,7,8,9 };
    private static char[] SAMPLE_CHAR_ARRAY = new char[] { '0','1','2','3','4','5','6','7','8','9' };
    private static byte[] SAMPLE_BUFFER_ARRAY = new byte[] { 10,11,12,13,14,15,16,17,18,19 };
    private static ByteBuffer SAMPLE_BUFFER = ByteBuffer.wrap(SAMPLE_BUFFER_ARRAY);
    private static String SAMPLE_STRING = "Hello World!";

    @BeforeAll
    public static void beforeAllTests() throws Exception {
        // Initialize Pi4J with Mock Serial Provider only
        pi4j = Pi4J.newContextBuilder()
                .add(MockSerialProvider.newInstance())
                .build();

        // create serial instance
        serial = pi4j.create(Serial.newConfigBuilder(pi4j)
                .id("my-i2c-bus")
                .device(SERIAL_DEVICE)
                .build());
    }

    @AfterAll
    public static void afterAllTests() throws IOException, LifecycleException {
            // close serial port
            if(serial.isOpen()) serial.close();

            // shutdown Pi4J context
            pi4j.shutdown();
    }

    // --------------------------------------------------------------------
    // WRITE TESTS
    // --------------------------------------------------------------------

    @DisplayName("SERIAL :: Verify Serial Instance")
    @Order(1)
    @Test
    public void testSerialInstance() throws Exception {
        // ensure that the serial instance is not null;
        assertNotNull(serial);
    }

    @DisplayName("SERIAL :: Write Single Byte")
    @Order(2)
    @Test
    public void writeByte() throws Exception {
        // write a single byte to the serial device
        serial.write(SAMPLE_BYTE);
    }


    @DisplayName("SERIAL :: Write Byte Array")
    @Order(3)
    @Test
    public void writeByteArray() throws Exception {
        // write an array of bytes to the serial device
        serial.write(SAMPLE_BYTE_ARRAY);
    }

    @DisplayName("SERIAL :: Write Byte Buffer")
    @Order(4)
    @Test
    public void writeByteBuffer() throws Exception {
        // write a buffer of data bytes to the serial device.
        serial.write(SAMPLE_BUFFER);
    }

    @DisplayName("SERIAL :: Write ASCII String")
    @Order(5)
    @Test
    public void writeString() throws Exception {
        // write a string of data to the serial device.
        serial.write(SAMPLE_STRING);
    }

    @DisplayName("SERIAL :: Write Byte Stream")
    @Order(6)
    @Test
    public void writeStream() throws Exception {
        // write a stream to the serial device
        ByteArrayInputStream bis = new ByteArrayInputStream(SAMPLE_BYTE_ARRAY);
        serial.write(bis);
    }

    @DisplayName("SERIAL :: Write Output Stream")
    @Order(7)
    @Test
    public void writeOutStream() throws Exception {
        // write directly to the output stream of the serial device
        serial.out().write(SAMPLE_BYTE_ARRAY);
    }

    @DisplayName("SERIAL :: Write Char Array")
    @Order(9)
    @Test
    public void writeCharArray() throws Exception {
        // write char array to the serial device
        serial.write(SAMPLE_CHAR_ARRAY);
    }

    // --------------------------------------------------------------------
    // READ TESTS
    // --------------------------------------------------------------------

    @DisplayName("SERIAL :: Read Single Byte")
    @Order(11)
    @Test
    public void readByte() throws Exception {
        // read single byte from serial device and check for expected value
        byte b = (byte)serial.read();
        assertEquals(SAMPLE_BYTE, b);
    }

    @DisplayName("SERIAL :: Read Byte Array")
    @Order(12)
    @Test
    public void readByteArray() throws Exception {
        // read an array of data bytes from the serial device and check for expected value
        byte byteArray[] = new byte[SAMPLE_BYTE_ARRAY.length];
        serial.read(byteArray, 0, byteArray.length);
        assertArrayEquals(SAMPLE_BYTE_ARRAY, byteArray);
    }

    @DisplayName("SERIAL :: Read Byte Buffer")
    @Order(13)
    @Test
    public void readByteBuffer() throws Exception {
        // read a buffer of data bytes from the serial device and check for expected value
        ByteBuffer buffer = ByteBuffer.allocate(SAMPLE_BUFFER.capacity());
        serial.read(buffer, 0, buffer.capacity());
        assertArrayEquals(SAMPLE_BUFFER_ARRAY, buffer.array());
    }

    @DisplayName("SERIAL :: Read ASCII String")
    @Order(14)
    @Test
    public void readString() throws Exception {
        // read a string of data from the serial device and check for expected value
        String testString = serial.readString(SAMPLE_STRING.length());
        assertEquals(SAMPLE_STRING, testString);
    }

    @DisplayName("SERIAL :: Read Byte Stream")
    @Order(15)
    @Test
    public void readStream() throws Exception {
        // read a stream of data from the serial device and check for expected value
        byte[] byteArray = new byte[SAMPLE_BYTE_ARRAY.length];
        serial.read(byteArray);
        assertArrayEquals(SAMPLE_BYTE_ARRAY, byteArray);
    }

    @DisplayName("SERIAL :: Read Input Stream")
    @Order(16)
    @Test
    public void readInputStream() throws Exception {
        // read  the input stream directly from serial device and check for expected value
        InputStream is = serial.in();
        byte[] byteArray = new byte[SAMPLE_BYTE_ARRAY.length];
        is.read(byteArray, 0, byteArray.length);
        assertArrayEquals(SAMPLE_BYTE_ARRAY, byteArray);
    }

    @DisplayName("SERIAL :: Read Char Array")
    @Order(17)
    @Test
    public void readCharArray() throws Exception {
        // read an array of data chars from the serial device and check for expected value
        char charArray[] = new char[SAMPLE_CHAR_ARRAY.length];
        serial.read(charArray, 0, charArray.length);
        assertArrayEquals(SAMPLE_CHAR_ARRAY, charArray);
    }

    // --------------------------------------------------------------------
    // BUFFER WRITE/READ TESTS
    // --------------------------------------------------------------------

    @DisplayName("SERIAL :: Write/Read Char Buffer (1)")
    @Order(21)
    @Test
    public void readCharBuffer1() throws Exception {

        // drain any existing data in the serial RX buffer
        serial.drain();

        // write sample data
        serial.write("HELLO");

        // create char buffer to hold read data
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.put("1234567890");
        buffer.rewind();

        // read data into char buffer using explicit offset in data
        serial.read(buffer, 2, 5);
        assertEquals("12HELLO890", buffer.rewind().toString());

    }

    @DisplayName("SERIAL :: Write/Read Char Buffer (2)")
    @Order(22)
    @Test
    public void readCharBuffer2() throws Exception {

        // drain any existing data in the serial RX buffer
        serial.drain();

        // create a character buffer from the sample data array
        char[] charArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        // change char at explicit index; this should not change buffer position
        charBuffer.put(5, '#');

        // write 3 chars into the buffer from position 0
        // this will offset the buffer position by 3
        charBuffer.put("---");

        // write sample data; should be "34#67890ABCDEFGHIJ"
        serial.write(charBuffer);

        // create char buffer to hold read data
        CharBuffer readBuffer = CharBuffer.allocate(14);

        // add some initial chars to the buffer which will create an offset of 2
        readBuffer.put("**");

        // read data into char buffer using explicit offset in data
        serial.read(readBuffer);
        assertEquals("**34#6789ABCDE", readBuffer.rewind().toString());
    }

    @DisplayName("SERIAL :: Write/Read Byte Buffer (1)")
    @Order(23)
    @Test
    public void readByteBuffer1() throws Exception {

        // drain any existing data in the serial RX buffer
        serial.drain();

        // write sample data
        serial.write("HELLO");

        // create char buffer to hold read data
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("1234567890".getBytes());
        buffer.rewind();

        // read data into char buffer using explicit offset in data
        serial.read(buffer, 2, 5);
//        System.out.println("[SAMPLE DATA] - 0x" + StringUtil.toHexString("12HELLO890".getBytes()));
//        System.out.println("[READ DATA  ] - 0x" + StringUtil.toHexString(buffer));
        assertArrayEquals("12HELLO890".getBytes(), buffer.array());
    }

    @DisplayName("SERIAL :: Write/Read Byte Buffer (2)")
    @Order(24)
    @Test
    public void readByteBuffer2() throws Exception {

        // drain any existing data in the serial RX buffer
        serial.drain();

        // create a byte buffer for the sample data array
        byte[] byteArray = new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        ByteBuffer writeBuffer  = ByteBuffer.wrap(byteArray);

        // change byte at explicit index; this should not change buffer position
        writeBuffer.put(5, (byte)99);

        // write 3 bytes nto the buffer from position 0
        // this will offset the buffer position by 3
        writeBuffer.put(new byte[] { (byte)0, (byte)0, (byte)0});

        // write sample data; should be "4,5,99,7,8,9,10,11,12,13,14,15"
        serial.write(writeBuffer);

        // create byte buffer to hold read data
        ByteBuffer readBuffer = ByteBuffer.allocate(12);

        // add some initial bytes to the buffer which will create an offset of 2
        readBuffer.put((byte)0xFF);
        readBuffer.put((byte)0xFF);

        // read data into char buffer using explicit offset in data
        serial.read(readBuffer);

        assertArrayEquals(
                new byte[]{ (byte)0xFF, (byte)0xFF, (byte)4, (byte)5, (byte)99, (byte)7, (byte)8, (byte)9, (byte)10, (byte)11, (byte)12, (byte)13 },
                readBuffer.array());
    }

//    @Test
//    public void testRawDataStream() throws Exception {
//        // create random set of sample data
//        Random rand = new Random();
//        byte sample[] = new byte[1024];
//        rand.nextBytes(sample);
//
//        // create I2C config
//        var config  = I2C.newConfigBuilder()
//                .id("my-i2c-bus")
//                .name("My I2C Bus")
//                .bus(I2C_BUS)
//                .device(I2C_DEVICE)
//                .build();
//
//        // use try-with-resources to auto-close I2C when complete
//        try (var i2c = pi4j.i2c().create(config);) {
//
//            // write sample data using output stream
//            i2c.out().write(sample);
//
//            // read sample data using input stream
//            byte[] result = i2c.in().readNBytes(sample.length);
//
//            System.out.println("[SAMPLE DATA] - 0x" + StringUtil.toHexString(sample));
//            System.out.println("[READ DATA  ] - 0x" + StringUtil.toHexString(result));
//
//            // copare sample data against returned read data
//            assertArrayEquals(sample, result);
//        }
//    }
}
