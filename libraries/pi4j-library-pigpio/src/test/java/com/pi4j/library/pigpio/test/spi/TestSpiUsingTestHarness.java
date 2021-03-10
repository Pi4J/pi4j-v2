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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Random;

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.library.pigpio.util.StringUtil;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("PIGPIO Library :: Test SPI Communication")
public class TestSpiUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestSpiUsingTestHarness.class);

    private static int SPI_CHANNEL = 0;
    private static int BAUD_RATE = 50000; // .5 MHz
    //private static int BAUD_RATE = 100000; // 1 MHz
    //private static int BAUD_RATE = 200000; // 2 MHz
    private static int TEST_HARNESS_SPI_CHANNEL = 10;

    private PiGpio pigpio;
    private int handle;

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
        logger.info("TERMINATE TEST (" + TestSpiUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");
    }

    @BeforeEach
    public void beforeEach() {
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
    public void afterEach() {
        // CLOSE SPI PORT
        pigpio.spiClose(handle);

        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @DisplayName("SPI :: Test SINGLE-BYTE (W/R)")
    public void testSerialSingleByteTxRx() throws InterruptedException {
        logger.info("");
        logger.info("--------------------------------------------");
        logger.info("TEST SPI PORT SINGLE BYTE READ/WRITE");
        logger.info("--------------------------------------------");

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            logger.info("[TEST WRITE/READ SINGLE BYTE]");
            // WRITE :: SINGLE RAW BYTE
            logger.info(" (WRITE) >> VALUE = 0x" + Integer.toHexString(b));
            pigpio.spiWriteByte(handle, (byte)b);

            Thread.sleep(20);

             // READ :: SINGLE RAW BYTE
            int rx = pigpio.spiReadByte(handle);
            logger.info(" (READ)  << VALUE = 0x" + Integer.toHexString(rx));
            assertEquals(b, rx, "SPI BYTE VALUE MISMATCH");
            logger.info("");

            Thread.sleep(20);
        }
    }

    @Test
    @DisplayName("SPI :: Test SINGLE-BYTE (XFER)")
    public void testSerialSingleByteXfer() throws InterruptedException {
        logger.info("");
        logger.info("--------------------------------------------");
        logger.info("TEST SPI PORT SINGLE BYTE XFER");
        logger.info("--------------------------------------------");

        int lastByte = -1;

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            logger.info("[TEST XFER SINGLE BYTE]");
            // XFER :: SINGLE RAW BYTE
            logger.info(" (WRITE) >> VALUE = 0x" + Integer.toHexString(b));
            int rx = pigpio.spiXferByte(handle, (byte)b);
            logger.info(" (READ)  << VALUE = 0x" + Integer.toHexString(rx));
            if(lastByte > 0) {
                assertEquals(lastByte, rx, "SPI BYTE VALUE MISMATCH");
            }
            lastByte = b;
            logger.info("");
            Thread.sleep(20);
        }
    }


    @Test
    @DisplayName("SPI :: Test MULTI-BYTE (W/R)")
    public void testSerialMultiByteTxRx() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST SPI MULTI-BYTE READ/WRITE");
        logger.info("----------------------------------------");

        Thread.sleep(30);

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 1; x < 50; x++) {
            logger.info("[TEST WRITE/READ MULTI-BYTE]");

            // Arduino max serial buffer length is 32
            // create a random series of bytes up to 32 bytes long
            Random r = new Random();
            int len = r.nextInt((5)) + 2; // minimum of 2 bytes
            byte[] testData = new byte[len];
            r.nextBytes(testData);

            // WRITE :: MULTI-BYTE
            logger.info(" (WRITE) >> VALUE = 0x" + StringUtil.toHexString(testData));
            pigpio.spiWrite(handle, testData);

            Thread.sleep(20);

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[len];
            int bytesRead = pigpio.spiRead(handle, readBuffer, readBuffer.length);
            logger.info(" (READ)  << BYTES READ = " + bytesRead);
            logger.info(" (READ)  << VALUE = 0x" + StringUtil.toHexString(readBuffer));

            // the test harness will return a byte array where the first byte value in the array
            // is the last byte written to the SPI device and all remaining bytes will be zero
            assertEquals(len, bytesRead, "SPI MULTI-BYTE VALUE LENGTH MISMATCH");
            assertEquals(testData[testData.length-1], readBuffer[0], "SPI MULTI-BYTE VALUE MISMATCH");
        }
    }

    @Test
    @DisplayName("SPI :: Test MULTI-BYTE (XFER)")
    public void testSerialMultiByteXfer() throws InterruptedException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST SPI MULTI-BYTE XFER");
        logger.info("----------------------------------------");

        Thread.sleep(30);

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 1; x < 50; x++) {
            logger.info("[TEST XFER MULTI-BYTE]");

            // Arduino max serial buffer length is 32
            // create a random series of bytes up to 32 bytes long
            Random r = new Random();
            int len = r.nextInt((5)) + 2; // minimum of 2 bytes
            byte[] writeBuffer = new byte[len];
            r.nextBytes(writeBuffer);

            // WRITE :: MULTI-BYTE
            logger.info(" (XFER-) >> VALUE = 0x" + StringUtil.toHexString(writeBuffer));

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[len];
            int bytesRead = pigpio.spiXfer(handle, writeBuffer, readBuffer);
            logger.info(" (READ)  << BYTES READ = " + bytesRead);
            logger.info(" (READ)  << VALUE = 0x" + StringUtil.toHexString(readBuffer));

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
            //logger.info(" (EXPECT)>> VALUE = 0x" + StringUtil.toHexString(expectBuffer));
            //Assert.assertArrayEquals("SPI MULTI-BYTE VALUE MISMATCH", expectBuffer.array(), readBuffer);
        }
    }
}
