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
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serOpen"
     */
    int serOpen(CharSequence device, int baud, int flags) throws IOException;

    /**
     * This function opens a serial device at a specified baud rate and with specified flags.
     * The device name must start with "/dev/tty" or "/dev/serial".
     *
     * @param device the serial device to open (Example: "/dev/ttyAMA0")
     * @param baud  the baud rate in bits per second, see below
     *              The baud rate must be one of 50, 75, 110, 134, 150, 200, 300, 600, 1200,
     *              1800, 2400, 4800, 9600, 19200, 38400, 57600, 115200, or 230400.
     * @return Returns a handle (>=0) if OK, otherwise PI_NO_HANDLE, or PI_SER_OPEN_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serOpen"
     */
    default int serOpen(CharSequence device, int baud) throws IOException {
        return serOpen(device, baud, 0);
    };

    /**
     * This function closes the serial device associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serClose"
     */
    int serClose(int handle) throws IOException;

    /**
     * This function writes a single byte "value" to the serial port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param value byte value to write to serial port
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWriteByte"
     */
    int serWriteByte(int handle, byte value) throws IOException;

    /**
     * This function reads a byte from the serial port associated with handle.
     * If no data is ready PI_SER_READ_NO_DATA is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns the read byte (>=0) if OK, otherwise PI_BAD_HANDLE, PI_SER_READ_NO_DATA, or PI_SER_READ_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serReadByte"
     */
    int serReadByte(int handle) throws IOException;

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the array of bytes to write
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    int serWrite(int handle, byte[] data, int offset, int length) throws IOException;

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the array of bytes to write
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, byte[] data, int length) throws IOException{
        return serWrite(handle, data, 0, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the array of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, byte[] data) throws IOException{
        return serWrite(handle, data, 0, data.length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer the byte buffer of data to write
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, ByteBuffer buffer, int offset, int length) throws IOException{
        return serWrite(handle, buffer.array(), offset, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer the byte buffer of data to write
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, ByteBuffer buffer, int length) throws IOException{
        return serWrite(handle, buffer, 0, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer the byte buffer of data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, ByteBuffer buffer) throws IOException{
        return serWrite(handle, buffer, 0, buffer.capacity());
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param charset the character set/type used to decode the character sequence/string to bytes
     * @param data the character/string data to write
     * @param offset the starting offset position in the provided data to start writing from.
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, Charset charset, CharSequence data, int offset, int length) throws IOException{
        return serWrite(handle, data.toString().getBytes(charset), offset, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param charset the character set/type used to decode the character sequence/string to bytes
     * @param data the character/string data to write
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, Charset charset, CharSequence data, int length) throws IOException{
        return serWrite(handle, charset, data, 0, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param charset the character set/type used to decode the character sequence/string to bytes
     * @param data the character/string data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, CharSequence data, Charset charset) throws IOException{
        return serWrite(handle, charset, data, 0, data.length());
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the ASCII character/string data to write
     * @param offset the starting offset position in the provided data to start writing from.
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, CharSequence data, int offset, int length) throws IOException{
        return serWrite(handle, StandardCharsets.US_ASCII, data, offset, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the ASCII character/string data to write
     * @param length the number of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, CharSequence data, int length) throws IOException{
        return serWrite(handle, data, length);
    }

    /**
     * This function writes multiple bytes from the buffer array ('data') to the the serial
     * port associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param data the ASCII character/string data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_WRITE_FAILED.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serWrite"
     */
    default int serWrite(int handle, CharSequence data) throws IOException{
        return serWrite(handle, data, data.length());
    }

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte array to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serRead"
     */
    int serRead(int handle, byte[] buffer, int offset, int length) throws IOException;

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte array to receive the read data
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serRead"
     */
    default int serRead(int handle, byte[] buffer, int length) throws IOException{
        return serRead(handle, buffer, 0, length);
    }

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte array to receive the read data
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serRead"
     */
    default int serRead(int handle, byte[] buffer) throws IOException{
        return serRead(handle, buffer, 0, buffer.length);
    }

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte buffer (with pre-allocated capacity) to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serRead"
     */
    default int serRead(int handle, ByteBuffer buffer, int offset, int length) throws IOException{
        return serRead(handle, buffer.array(), offset, length);
    }

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte buffer (with pre-allocated capacity) to receive the read data
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serRead"
     */
    default int serRead(int handle, ByteBuffer buffer, int length) throws IOException{
        return serRead(handle, buffer, 0, length);
    }

    /**
     * This function reads up count bytes from the the serial port associated with handle and
     * writes them to the buffer parameter.   If no data is ready, zero is returned.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @param buffer a byte buffer (with pre-allocated capacity) to receive the read data
     * @return Returns the number of bytes read (>0=) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_SER_READ_NO_DATA.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serRead"
     */
    default int serRead(int handle, ByteBuffer buffer) throws IOException{
        return serRead(handle, buffer, 0, buffer.capacity());
    }

    /**
     * This function returns the number of bytes available to be read from the device associated with handle.
     *
     * @param handle the open serial device handle; (>=0, as returned by a call to serOpen)
     * @return Returns the number of bytes of data available (>=0) if OK, otherwise PI_BAD_HANDLE.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#serDataAvailable"
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
