package com.pi4j.library.pigpio.test.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestSpiUsingTestHarness.java
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
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.library.pigpio.util.StringUtil;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PIGPIO Library :: Test SPI Communication")
public class TestSpiUsingTestHarness {

    private static int SPI_CHANNEL = 0;
    private static int BAUD_RATE = 50000; // .5 MHz
    //private static int BAUD_RATE = 100000; // 1 MHz
    //private static int BAUD_RATE = 200000; // 2 MHz
    private static int TEST_HARNESS_SPI_CHANNEL = 10;

    private PiGpio pigpio;
    private int handle;

    @BeforeAll
    public static void initialize() {
        //System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("INITIALIZE TEST (" + TestSpiUsingTestHarness.class.getName() + ")");
        System.out.println("************************************************************************");
        System.out.println();

        try {
            // create test harness and PIGPIO instances
            ArduinoTestHarness harness = TestEnv.createTestHarness();

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

            // enable the SPI Echo (Loopback) function on the test harness for these tests
            harness.enableSpiEcho(TEST_HARNESS_SPI_CHANNEL);

            // close connection to test harness
            harness.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("TERMINATE TEST (" + TestSpiUsingTestHarness.class.getName() + ") ");
        System.out.println("************************************************************************");
        System.out.println();
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        // create test harness and PIGPIO instances
        pigpio = TestEnv.createPiGpio();

        // initialize test harness and PIGPIO instances
        pigpio.initialize();

        // set pin ALT5 modes for SERIAL RX & TX PINS on RPI3B
        //pigpio.gpioSetMode(14, PiGpioMode.ALT5);
        //pigpio.gpioSetMode(15, PiGpioMode.ALT5);

        // OPEN SPI CHANNEL
        handle = pigpio.spiOpen(SPI_CHANNEL, BAUD_RATE);
    }

    @AfterEach
    public void afterEach() throws IOException {

        // CLOSE SPI PORT
        pigpio.spiClose(handle);

        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @DisplayName("SPI :: Test SINGLE-BYTE (W/R)")
    public void testSerialSingleByteTxRx() throws IOException, InterruptedException {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("TEST SPI PORT SINGLE BYTE READ/WRITE");
        System.out.println("--------------------------------------------");

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            System.out.println("[TEST WRITE/READ SINGLE BYTE]");
            // WRITE :: SINGLE RAW BYTE
            System.out.println(" (WRITE) >> VALUE = 0x" + Integer.toHexString(b));
            pigpio.spiWriteByte(handle, (byte)b);

            Thread.sleep(20);

             // READ :: SINGLE RAW BYTE
            int rx = pigpio.spiReadByte(handle);
            System.out.println(" (READ)  << VALUE = 0x" + Integer.toHexString(rx));
            assertEquals(b, rx, "SPI BYTE VALUE MISMATCH");
            System.out.println();

            Thread.sleep(20);
        }
    }

    @Test
    @DisplayName("SPI :: Test SINGLE-BYTE (XFER)")
    public void testSerialSingleByteXfer() throws IOException, InterruptedException {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("TEST SPI PORT SINGLE BYTE XFER");
        System.out.println("--------------------------------------------");
        int lastByte = -1;

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            System.out.println("[TEST XFER SINGLE BYTE]");
            // XFER :: SINGLE RAW BYTE
            System.out.println(" (WRITE) >> VALUE = 0x" + Integer.toHexString(b));
            int rx = pigpio.spiXferByte(handle, (byte)b);
            System.out.println(" (READ)  << VALUE = 0x" + Integer.toHexString(rx));
            if(lastByte > 0) {
                assertEquals(lastByte, rx, "SPI BYTE VALUE MISMATCH");
            }
            lastByte = b;
            System.out.println();
            Thread.sleep(20);
        }
    }


    @Test
    @DisplayName("SPI :: Test MULTI-BYTE (W/R)")
    public void testSerialMultiByteTxRx() throws IOException, InterruptedException {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST SPI MULTI-BYTE READ/WRITE");
        System.out.println("----------------------------------------");
        Thread.sleep(30);

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 1; x < 50; x++) {
            System.out.println("[TEST WRITE/READ MULTI-BYTE]");

            // Arduino max serial buffer length is 32
            // create a random series of bytes up to 32 bytes long
            Random r = new Random();
            int len = r.nextInt((5)) + 2; // minimum of 2 bytes
            byte[] testData = new byte[len];
            r.nextBytes(testData);

            // WRITE :: MULTI-BYTE
            System.out.println(" (WRITE) >> VALUE = 0x" + StringUtil.toHexString(testData));
            pigpio.spiWrite(handle, testData);

            Thread.sleep(20);

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[len];
            int bytesRead = pigpio.spiRead(handle, readBuffer, readBuffer.length);
            System.out.println(" (READ)  << BYTES READ = " + bytesRead);
            System.out.println(" (READ)  << VALUE = 0x" + StringUtil.toHexString(readBuffer));

            // the test harness will return a byte array where the first byte value in the array
            // is the last byte written to the SPI device and all remaining bytes will be zero
            assertEquals(len, bytesRead, "SPI MULTI-BYTE VALUE LENGTH MISMATCH");
            assertEquals(testData[testData.length-1], readBuffer[0], "SPI MULTI-BYTE VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("SPI :: Test MULTI-BYTE (XFER)")
    public void testSerialMultiByteXfer() throws IOException, InterruptedException {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST SPI MULTI-BYTE XFER");
        System.out.println("----------------------------------------");
        Thread.sleep(30);

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 1; x < 50; x++) {
            System.out.println("[TEST XFER MULTI-BYTE]");

            // Arduino max serial buffer length is 32
            // create a random series of bytes up to 32 bytes long
            Random r = new Random();
            int len = r.nextInt((5)) + 2; // minimum of 2 bytes
            byte[] writeBuffer = new byte[len];
            r.nextBytes(writeBuffer);

            // WRITE :: MULTI-BYTE
            System.out.println(" (XFER-) >> VALUE = 0x" + StringUtil.toHexString(writeBuffer));

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[len];
            int bytesRead = pigpio.spiXfer(handle, writeBuffer, readBuffer);
            System.out.println(" (READ)  << BYTES READ = " + bytesRead);
            System.out.println(" (READ)  << VALUE = 0x" + StringUtil.toHexString(readBuffer));

            // validate read length
            assertEquals(len, bytesRead, "SPI MULTI-BYTE VALUE LENGTH MISMATCH");

            Thread.sleep(20);

            // TODO :: The Arduino Test harness needs to be updated to better handle SPI data
            // We are not currently getting reliable test data back on the transfer routine

            // the test harness will return a byte array where the first byte value in the array
            // is the last byte written to the SPI device followed by bytes from the original write
            // buffer from left to right (excluding the last byte)
            // example : WRITE="ABCDEF" / READ="FABCDE"
            //ByteBuffer expectBuffer = ByteBuffer.allocate(len);
            //expectBuffer.put(writeBuffer[writeBuffer.length-1]);
            //expectBuffer.put(Arrays.copyOfRange(writeBuffer, 0, writeBuffer.length-1));
            //System.out.println(" (EXPECT)>> VALUE = 0x" + StringUtil.toHexString(expectBuffer));
            //Assert.assertArrayEquals("SPI MULTI-BYTE VALUE MISMATCH", expectBuffer.array(), readBuffer);
        }
    }
}
