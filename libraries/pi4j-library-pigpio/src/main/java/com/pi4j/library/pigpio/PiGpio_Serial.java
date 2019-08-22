package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpio_Serial.java
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

import java.io.IOException;

public interface PiGpio_Serial {

    /**
     * This function opens a serial device at a specified baud rate and with specified flags.
     * The device name must start with "/dev/tty" or "/dev/serial".
     *
     * @param device the serial device to open (Example: "/dev/ttyAMA0")
     * @param baud  the baud rate in bits per second, see below
     *              The baud rate must be one of 50, 75, 110, 134, 150, 200, 300, 600, 1200,
     *              1800, 2400, 4800, 9600, 19200, 38400, 57600, 115200, or 230400.
     * @param flags  No flags are currently defined. This parameter should be set to zero.
     * @return Returns a handle (>=0) if OK, otherwise PI_NO_HANDLE, or PI_SER_OPEN_FAILED.
     */
    int serOpen(CharSequence device, int baud, int flags) throws IOException;
    default int serOpen(CharSequence device, int baud) throws IOException {
        return serOpen(device, baud, 0);
    };

    /**
     * This function closes the serial device associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE.
     */
    int serClose(int handle) throws IOException;


    /**
     * This function writes a single byte "value" to the serial port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param value byte value to write to serial port
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     */
    int serWriteByte(int handle, byte value) throws IOException;


    /**
     * This function reads a byte from the serial port associated with handle.
     * If no data is ready PI_SER_READ_NO_DATA is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns the read byte (>=0) if OK, otherwise PI_BAD_HANDLE, PI_SER_READ_NO_DATA, or PI_SER_READ_FAILED.
     */
    int serReadByte(int handle) throws IOException;


    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the array of bytes to write
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     */
    int serWrite(int handle, byte[] data, int offset, int length) throws IOException;
    default int serWrite(int handle, byte[] data) throws IOException{
        return serWrite(handle, data, 0, data.length);
    }
    default int serWrite(int handle, byte[] data, int length) throws IOException{
        return serWrite(handle, data, 0, length);
    }

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte array to receive the read data
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     */
    int serRead(int handle, byte[] buffer, int offset, int length) throws IOException;
    default int serRead(int handle, byte[] buffer) throws IOException{
        return serRead(handle, buffer, 0, buffer.length);
    }
    default int serRead(int handle, byte[] buffer, int length) throws IOException{
        return serRead(handle, buffer, 0, length);
    }

    /**
     * This function returns the number of bytes available to be read from the device associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns the number of bytes of data available (>=0) if OK, otherwise PI_BAD_HANDLE.
     */
    int serDataAvailable(int handle) throws IOException;


    /**
     * This function will drain the current serial receive buffer of any lingering bytes.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns the number of bytes of data drained (>=0) if OK, otherwise PI_BAD_HANDLE.
     */
    int serDrain(int handle) throws IOException;
}
