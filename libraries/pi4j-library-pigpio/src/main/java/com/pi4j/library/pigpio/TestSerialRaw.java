package com.pi4j.library.pigpio;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestSerialRaw.java
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

import com.pi4j.library.pigpio.internal.PIGPIO;
import com.pi4j.library.pigpio.util.StringUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class TestSerialRaw {

    private static String SERIAL_DEVICE = "/dev/ttyS0";
    private static int BAUD_RATE = 9600;

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link String} objects.
     * @throws Exception if any.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("PIGPIO VERSION   : " + PIGPIO.gpioVersion());
        System.out.println("PIGPIO HARDWARE  : " + PIGPIO.gpioHardwareRevision());

        int init = PIGPIO.gpioInitialise();
        System.out.println("PIGPIO INITIALIZE: " + init);

        if(init < 0){
            System.err.println("ERROR; PIGPIO INIT FAILED; ERROR CODE: " + init);
            System.exit(init);
        }

        int handle = PIGPIO.serOpen(SERIAL_DEVICE, BAUD_RATE, 0);
        System.out.println("PIGPIO SERIAL OPEN  : " + handle);
        if(handle < 0) {
            System.err.println("ERROR; SERIAL OPEN FAILED: ERROR CODE: " + handle);
            System.exit(handle);
        }

        System.out.println("SERIAL DATA DRAINED  : " + PIGPIO.serDrain(handle));

        // iterate over BYTE range of values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int b = 0; b < 256; b++) {
            System.out.print("[SERIAL W/R BYTE]");

            // WRITE :: SINGLE RAW BYTE
            System.out.print(" (WRITE) 0x" + Integer.toHexString(b));
            int result = PIGPIO.serWriteByte(handle, (byte)b);
            if(result < 0) {
                System.err.println("\nERROR; SERIAL WRITE FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            // take a breath to allow time for the serial data
            // to get updated in the serial receive buffer
            Thread.sleep(5);

            // READ :: NUMBER OF BYTES AVAILABLE TO READ
            int available = PIGPIO.serDataAvailable(handle);
            System.out.print(" (AVAIL) " + available);
            if(available != 1) {
                System.err.println("\nERROR; SERIAL AVAILABLE FAILED: expected=1; received=" + available);
                System.exit(result);
            }

            // READ :: SINGLE RAW BYTE
            result = PIGPIO.serReadByte(handle);
            System.out.print(" (READ) 0x" + Integer.toHexString(result));
            System.out.println();
            if(result < 0) {
                System.err.println("\nERROR; SERIAL READ FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            int expected = b;
            int recevied = result;
            if(recevied != expected) {
                System.err.println("\nERROR; SERIAL READ FAILED: BYTE MISMATCH: expected=" + expected + "; received=" + recevied);
                System.exit(0);
            }
        }

        // iterate over series of test values, WRITE the byte then immediately
        // READ back the byte value and compare to make sure they are the same values.
        for(int x = 1; x < 50; x++) {
            System.out.print("[SERIAL W/R BUFFER]");

            Random r = new Random();
            int len = r.nextInt((20)) + 2; // minimum of 2 bytes
            byte[] writeBuffer = new byte[len];
            r.nextBytes(writeBuffer);

            // WRITE :: MULTI-BYTE
            System.out.print(" (WRITE) 0x" + StringUtil.toHexString(writeBuffer));
            int result = PIGPIO.serWrite(handle, writeBuffer, len);
            if(result < 0) {
                System.err.println("\nERROR; SERIAL WRITE FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            // take a breath to allow time for the serial data
            // to get updated in the serial receive buffer
            Thread.sleep(40);

            // READ :: NUMBER OF BYTES AVAILABLE TO READ
            int available = PIGPIO.serDataAvailable(handle);
            System.out.print(" (AVAIL) " + available);
            if(available != len) {
                System.err.println("\nERROR; SERIAL AVAILABLE FAILED: expected=1; received=" + available);
                System.exit(result);
            }

            // READ :: MULTI-BYTE
            byte[] readBuffer = new byte[available];
            result = PIGPIO.serRead(handle, readBuffer, available);
            System.out.print(" (READ) 0x" + StringUtil.toHexString(readBuffer));
            System.out.println();
            if(result < 0) {
                System.err.println("\nERROR; SERIAL READ FAILED: ERROR CODE: " + result);
                System.exit(result);
            }

            // validate read length
            if(result != len) {
                System.err.println("\nERROR; SERIAL READ FAILED: LENGTH MISMATCH: " + result);
                System.exit(result);
            }

            // validate buffer contents
            if(!Arrays.equals(writeBuffer, readBuffer)) {
                System.err.println("\nERROR; SERIAL READ FAILED: BYTE MISMATCH: expected=" +
                        StringUtil.toHexString(writeBuffer) + "; received=" +
                        StringUtil.toHexString(readBuffer));
                System.exit(0);
            }
        }

        // close SERIAL channel
        int closed = PIGPIO.serClose(handle);
        System.out.println("PIGPIO SERIAL CLOSED : " + handle);


        PIGPIO.gpioTerminate();
        System.out.println("PIGPIO TERMINATED");

        System.out.println("ALL SERIAL TESTS COMPLETED SUCCESSFULLY");
    }
}
