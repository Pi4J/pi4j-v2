package com.pi4j.library.pigpio.test;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestI2CRegister.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
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
import com.pi4j.library.pigpio.util.StringUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class TestI2CRegister {

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int MAX_REGISTERS = 10;

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link String} objects.
     * @throws Exception if any.
     */
    public static void main(String[] args) throws Exception {

        PiGpio piGpio = PiGpio.newNativeInstance();

        piGpio.gpioInitialise();
        System.out.println("PIGPIO INITIALIZED");

        System.out.println("PIGPIO VERSION   : " + piGpio.gpioVersion());
        System.out.println("PIGPIO HARDWARE  : " + piGpio.gpioHardwareRevision());

        // open I2C channel/bus/device
        int handle = piGpio.i2cOpen(I2C_BUS, I2C_DEVICE, 0);
        System.out.println("PIGPIO I2C OPEN  : " + handle);
        if(handle < 0) {
            System.err.println("ERROR; I2C OPEN FAILED: ERROR CODE: " + handle);
            System.exit(handle);
        }

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER WRITE/READ BYTE");
        System.out.println("----------------------------------------");

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {

            // iterate over BYTE range of values, WRITE the byte then immediately
            // READ back the byte value and compare to make sure they are the same values.
            for (int b = 0; b < 255; b++) {
                System.out.print("[REG #" + register + "][W/R BYTE]");

                // WRITE :: SINGLE RAW BYTE
                System.out.print(" (WRITE) 0x" + Integer.toHexString(b));
                int result = piGpio.i2cWriteByteData(handle, register, (byte) b);
                if (result < 0) {
                    System.err.println("\nERROR; I2C WRITE BYTE FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }

                // READ :: SINGLE RAW BYTE
                result = piGpio.i2cReadByteData(handle, register);
                if (result < 0) {
                    System.err.println("\nERROR; I2C READ BYTE FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }
                System.out.print(" (READ) 0x" + Integer.toHexString(result));
                System.out.println();

                int expected = b;
                int received = result;
                if (received != expected) {
                    System.err.println("\nERROR; I2C READ BYTE FAILED: BYTE MISMATCH: expected=" + expected + "; received=" + received);
                    System.exit(0);
                }
            }
        }

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER WRITE/READ WORD");
        System.out.println("----------------------------------------");

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {

            // iterate over sample number of tests, WRITE the WORD value then immediately
            // READ back the WORD value and compare to make sure they are the same values.
            for (int b = 0; b < 100; b++) {
                System.out.print("[REG #" + register + "][W/R WORD]");

                Random rand = new Random();
                int word = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)

                // WRITE :: WORD VALUE (2-bytes)
                System.out.print(" (WRITE) 0x" + Integer.toHexString(word));
                int result = piGpio.i2cWriteWordData(handle, register, word);
                if (result < 0) {
                    System.err.println("\nERROR; I2C WRITE WORD FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }

                // READ :: WORD VALUE (2-bytes)
                result = piGpio.i2cReadWordData(handle, register);
                if (result < 0) {
                    System.err.println("\nERROR; I2C READ WORD FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }
                System.out.print(" (READ) 0x" + Integer.toHexString(result));
                System.out.println();

                if (result != word) {
                    System.err.println("\nERROR; I2C READ WORD FAILED: BYTE MISMATCH: expected=" + word + "; received=" + result);
                    System.exit(0);
                }
            }
        }

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER PROCESS WORD (W/R)    ");
        System.out.println("----------------------------------------");

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {

            // iterate over sample number of tests, WRITE the WORD value then immediately
            // READ back the WORD value and compare to make sure they are the same values.
            for (int b = 0; b < 100; b++) {
                System.out.print("[REG #" + register + "][XFER WORD]");
                Random rand = new Random();
                int word = rand.nextInt(0xFFFF); // max 16 bits (2 bytes)
                // WRITE :: WORD VALUE (2-bytes)
                // READ :: WORD VALUE (2-bytes)
                System.out.print(" (WRITE) 0x" + Integer.toHexString(word));
                int result = piGpio.i2cProcessCall(handle, register, word);
                if (result < 0) {
                    System.err.println("\nERROR; I2C PROCESS WORD FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }
                System.out.print(" (READ) 0x" + Integer.toHexString(result));
                System.out.println();
                if (result != word) {
                    System.err.println("\nERROR; I2C PROCESS WORD FAILED: BYTE MISMATCH: expected=" + word + "; received=" + result);
                    System.exit(0);
                }
            }
        }

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST I2C REGISTER WRITE/READ ARRAY      ");
        System.out.println("----------------------------------------");

        // WRITE random values to the I2C storage registers on the test harness.
        // the test harness contains 256 registers from address 0 to 255;
        for(int register = 0; register < MAX_REGISTERS; register++) {

            // iterate over series of test values, WRITE the byte array then immediately
            // READ back the byte array values and compare to make sure they are the same values.
            for (int x = 1; x < 100; x++) {
                System.out.print("[REG #" + register + "][W/R BUFFER]");

                Random r = new Random();
                int len = r.nextInt((20)) + 2; // minimum of 2 bytes
                byte[] writeBuffer = new byte[len];
                r.nextBytes(writeBuffer);

                // WRITE :: MULTI-BYTE
                System.out.print(" (WRITE) 0x" + StringUtil.toHexString(writeBuffer));
                int result = piGpio.i2cWriteI2CBlockData(handle, register, writeBuffer);
                if (result < 0) {
                    System.err.println("\nERROR; I2C WRITE FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }

                // READ :: MULTI-BYTE
                byte[] readBuffer = new byte[len];
                result = piGpio.i2cReadI2CBlockData(handle, register, readBuffer);
                System.out.print(" (READ) 0x" + StringUtil.toHexString(readBuffer));
                System.out.println();
                if (result < 0) {
                    System.err.println("\nERROR; I2C READ FAILED: ERROR CODE: " + result);
                    System.exit(result);
                }

                // validate read length
                if (result != len) {
                    System.err.println("\nERROR; I2C READ FAILED: LENGTH MISMATCH: " + result);
                    System.exit(result);
                }

                //validate data read back is same as written
                if (!Arrays.equals(writeBuffer, readBuffer)) {
                    System.err.println("\nERROR; I2C READ FAILED: BYTE MISMATCH: expected=" +
                            StringUtil.toHexString(writeBuffer) + "; received=" +
                            StringUtil.toHexString(readBuffer));
                    System.exit(0);
                }
            }
        }

        // close I2C channel
        piGpio.i2cClose(handle);

        piGpio.gpioTerminate();
        System.out.println("PIGPIO TERMINATED");
        System.out.println("ALL I2C DEVICE REGISTER TESTS COMPLETED SUCCESSFULLY");
    }
}
