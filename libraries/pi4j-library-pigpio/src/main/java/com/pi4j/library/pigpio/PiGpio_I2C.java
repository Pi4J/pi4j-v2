package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpio_I2C.java
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

public interface PiGpio_I2C {

    /**
     * Opens a I2C device on a given I2C bus for communications.
     * This returns a handle for the device at the address on the I2C bus.
     * Physically buses 0 and 1 are available on the Pi.
     * Higher numbered buses will be available if a kernel supported bus multiplexor is being used.
     *
     * The GPIO used are given in the following table.
     *         SDA   SCL
     * I2C0     0     1
     * I2C1     2     3
     *
     * @param bus the I2C bus address to open/access for reading and writing. (>=0)
     * @param device the I2C device address to open/access for reading and writing. (0-0x7F)
     * @param flags no flags are currently defined. This parameter should be set to zero.
     * @return Returns a handle (>=0) if OK, otherwise PI_BAD_I2C_BUS, PI_BAD_I2C_ADDR, PI_BAD_FLAGS, PI_NO_HANDLE, or PI_I2C_OPEN_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cOpen"
     */
    int i2cOpen(int bus, int device, int flags) throws IOException;

    /**
     * Opens a I2C device on a given I2C bus for communications.
     * This returns a handle for the device at the address on the I2C bus.
     * Physically buses 0 and 1 are available on the Pi.
     * Higher numbered buses will be available if a kernel supported bus multiplexor is being used.
     *
     * The GPIO used are given in the following table.
     *         SDA   SCL
     * I2C0     0     1
     * I2C1     2     3
     *
     * @param bus the I2C bus address to open/access for reading and writing. (>=0)
     * @param device the I2C device address to open/access for reading and writing. (0-0x7F)
     * @return Returns a handle (>=0) if OK, otherwise PI_BAD_I2C_BUS, PI_BAD_I2C_ADDR, PI_BAD_FLAGS, PI_NO_HANDLE, or PI_I2C_OPEN_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cOpen"
     */
    default int i2cOpen(int bus, int device) throws IOException{
        return i2cOpen(bus, device, 0);
    }

    /**
     * This closes the I2C device associated with the handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cClose"
     */
    int i2cClose(int handle) throws IOException;

    /**
     * This sends a single bit (in the Rd/Wr bit) to the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param bit 0-1, the value to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteQuick"
     */
    int i2cWriteQuick(int handle, boolean bit) throws IOException;

    /**
     * This sends a single byte to the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param value raw byte value (0-0xFF) to write to I2C device
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteByte"
     */
    int i2cWriteByte(int handle, byte value) throws IOException;

    /**
     * This reads a single byte from the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @return Returns the byte read (>=0) if OK, otherwise PI_BAD_HANDLE, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadByte"
     */
    int i2cReadByte(int handle) throws IOException;

    /**
     * This writes a single byte to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param value raw byte value (0-0xFF) to write to I2C device
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteByteData"
     */
    int i2cWriteByteData(int handle, int register, byte value) throws IOException;

    /**
     * This writes a single 16 bit word to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param value raw word (2-byte) value (0-0xFFFF) to write to I2C device
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteWordData"
     */
    int i2cWriteWordData(int handle, int register, int value) throws IOException;

    /**
     * This reads a single byte from the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @return Returns the byte read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadByteData"
     */
    int i2cReadByteData(int handle, int register) throws IOException;

    /**
     * This reads a single 16 bit word from the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @return Returns the word (2-byte value) read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadWordData"
     */
    int i2cReadWordData(int handle, int register) throws IOException;

    /**
     * This writes 16 bits of data to the specified register of the device associated with
     * handle and reads 16 bits of data in return. (in a single transaction)
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to and read from. (0-255)
     * @param value raw word (2-byte) value (0-0xFFFF) to write to I2C device
     * @return Returns the word read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cProcessCall"
     */
    int i2cProcessCall(int handle, int register, int value) throws IOException;

    /**
     * This writes up to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data the array of bytes to write
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    int i2cWriteBlockData(int handle, int register, byte[] data, int offset, int length) throws IOException;

    /**
     * This writes up to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data the array of bytes to write
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, byte[] data, int length) throws IOException{
        return i2cWriteBlockData(handle, register, data, 0, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2c register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data the array of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, byte[] data) throws IOException{
        return i2cWriteBlockData(handle, register, data, data.length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param buffer the byte buffer of data to write
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, ByteBuffer buffer, int offset, int length) throws IOException{
        return i2cWriteBlockData(handle, register, buffer.array(), offset, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param buffer the byte buffer of data to write
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, ByteBuffer buffer, int length) throws IOException{
        return i2cWriteBlockData(handle, register, buffer, 0, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param buffer the byte buffer of data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, ByteBuffer buffer) throws IOException{
        return i2cWriteBlockData(handle, register, buffer, buffer.capacity());
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param charset the character set/type used to decode the character sequence/string to bytes
     * @param data the character/string data to write
     * @param offset the starting offset position in the provided data to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, Charset charset, CharSequence data, int offset, int length) throws IOException{
        return i2cWriteBlockData(handle, register, data.toString().getBytes(charset), offset, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param charset the character set/type used to decode the character sequence/string to bytes
     * @param data the character/string data to write
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, Charset charset, CharSequence data, int length) throws IOException{
        return i2cWriteBlockData(handle, register, charset, data, 0, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param charset the character set/type used to decode the character sequence/string to bytes
     * @param data the character/string data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, Charset charset, CharSequence data) throws IOException{
        return i2cWriteBlockData(handle, register, charset, data, data.length());
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data the ASCII character/string data to write
     * @param offset the starting offset position in the provided data to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, CharSequence data, int offset, int length) throws IOException{
        return i2cWriteBlockData(handle, register, data.toString().getBytes(StandardCharsets.US_ASCII), offset, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data the ASCII character/string data to write
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, CharSequence data, int length) throws IOException{
        return i2cWriteBlockData(handle, register, data, 0, length);
    }

    /**
     * This writes up to 32 bytes to the specified I2C register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data the ASCII character/string data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteBlockData"
     */
    default int i2cWriteBlockData(int handle, int register, CharSequence data) throws IOException{
        return i2cWriteBlockData(handle, register, data, data.length());
    }

    /**
     * This reads a block of up to 32 bytes from the specified register of the device associated with handle.
     * The amount of returned data is set by the device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte array to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadBlockData"
     */
    int i2cReadBlockData(int handle, int register, byte[] buffer, int offset, int length) throws IOException;

    /**
     * This reads a block of up to 32 bytes from the specified register of the device associated with handle.
     * The amount of returned data is set by the device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte array to receive the read data
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadBlockData"
     */
    default int i2cReadBlockData(int handle, int register, byte[] buffer, int length) throws IOException {
        return i2cReadBlockData(handle, register, buffer, 0, length);
    }

    /**
     * This reads a block of up to 32 bytes from the specified register of the device associated with handle.
     * The amount of returned data is set by the device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte array to receive the read data
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadBlockData"
     */
    default int i2cReadBlockData(int handle, int register, byte[] buffer) throws IOException {
        return i2cReadBlockData(handle, register, buffer, buffer.length);
    }

    /**
     * This reads a block of up to 32 bytes from the specified register of the device associated with handle.
     * The amount of returned data is set by the device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadBlockData"
     */
    default int i2cReadBlockData(int handle, int register, ByteBuffer buffer, int offset, int length) throws IOException{
        return i2cReadBlockData(handle, register, buffer.array(), 0, length);
    }

    /**
     * This reads a block of up to 32 bytes from the specified register of the device associated with handle.
     * The amount of returned data is set by the device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @param length the maximum number of bytes to read
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadBlockData"
     */
    default int i2cReadBlockData(int handle, int register, ByteBuffer buffer, int length) throws IOException {
        return i2cReadBlockData(handle, register, buffer, 0, length);
    }

    /**
     * This reads a block of up to 32 bytes from the specified register of the device associated with handle.
     * The amount of returned data is set by the device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadBlockData"
     */
    default int i2cReadBlockData(int handle, int register, ByteBuffer buffer) throws IOException {
        return i2cReadBlockData(handle, register, buffer, buffer.capacity());
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte array containing data to write
     * @param writeOffset the starting offset position in the provided byte array to start writing from.
     * @param writeLength the number of bytes to write (maximum 32 bytes supported)
     * @param read a byte array to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @param readOffset the starting offset position in the provided read array/buffer to start copying the data bytes read.
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    int i2cBlockProcessCall(int handle, int register, byte[] write, int writeOffset, int writeLength, byte[] read, int readOffset) throws IOException;

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte array containing data to write
     * @param writeOffset the starting offset position in the provided byte array to start writing from.
     * @param writeLength the number of bytes to write (maximum 32 bytes supported)
     * @param read a byte array to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, byte[] write, int writeOffset, int writeLength, byte[] read) throws IOException{
        return i2cBlockProcessCall(handle, register, write, writeOffset, writeLength, read, 0);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte array containing data to write
     * @param writeLength the number of bytes to write (maximum 32 bytes supported)
     * @param read a byte array to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, byte[] write, int writeLength, byte[] read) throws IOException{
        return i2cBlockProcessCall(handle, register, write, 0, writeLength, read, 0);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte array containing data to write
     * @param read a byte array to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, byte[] write, byte[] read) throws IOException{
        return i2cBlockProcessCall(handle, register, write, 0, write.length, read, 0);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param data a single byte array/buffer containing data to write and the array contents will be
     *             overwritten with the data read from the I2C device register.
     * @param offset the starting offset position in the provided byte array to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, byte[] data, int offset, int length) throws IOException{
        return i2cBlockProcessCall(handle, register, data, offset, length, data, offset);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param data a single byte array/buffer containing data to write and the array contents will be
     *             overwritten with the data read from the I2C device register.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, byte[] data, int length) throws IOException {
        return i2cBlockProcessCall(handle, register, data, 0, length);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param data a single byte array/buffer containing data to write and the array contents will be
     *             overwritten with the data read from the I2C device register.
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, byte[] data) throws IOException {
        return i2cBlockProcessCall(handle, register, data, data.length);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte buffer containing data to write
     * @param writeOffset the starting offset position in the provided byte buffer to start writing from.
     * @param writeLength the number of bytes to write (maximum 32 bytes supported)
     * @param read a byte buffer to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @param readOffset the starting offset position in the provided read buffer to start copying the data bytes read.
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer write, int writeOffset, int writeLength, ByteBuffer read, int readOffset) throws IOException{
        return i2cBlockProcessCall(handle, register, write.array(), writeOffset, writeLength, read.array(), readOffset);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte buffer containing data to write
     * @param writeOffset the starting offset position in the provided byte buffer to start writing from.
     * @param writeLength the number of bytes to write (maximum 32 bytes supported)
     * @param read a byte buffer to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer write, int writeOffset, int writeLength, ByteBuffer read) throws IOException{
        return i2cBlockProcessCall(handle, register, write, writeOffset, writeLength, read, 0);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte buffer containing data to write
     * @param writeLength the number of bytes to write (maximum 32 bytes supported)
     * @param read a byte buffer to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer write, int writeLength, ByteBuffer read) throws IOException{
        return i2cBlockProcessCall(handle, register, write, 0, writeLength, read, 0);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param write a byte buffer containing data to write
     * @param read a byte buffer to receive the read data; note the size must be pre-allocated and must be at
     *             is determined by the actual I2C device  (a pre-allocated array/buffer of 32 bytes is safe)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer write, ByteBuffer read) throws IOException{
        return i2cBlockProcessCall(handle, register, write, 0, write.capacity(), read, 0);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param data a single byte array/buffer containing data to write and the array contents will be
     *             overwritten with the data read from the I2C device register.
     * @param offset the starting offset position in the provided byte array to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer data, int offset, int length) throws IOException{
        return i2cBlockProcessCall(handle, register, data, offset, length, data, offset);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param data a single byte array/buffer containing data to write and the array contents will be
     *             overwritten with the data read from the I2C device register.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer data, int length) throws IOException {
        return i2cBlockProcessCall(handle, register, data, 0, length);
    }

    /**
     * This writes data bytes to the specified register of the device associated with handle and reads a
     * device specified number of bytes of data in return.
     *
     * The SMBus 2.0 documentation states that a minimum of 1 byte may be sent and a minimum of 1 byte may be received.
     * The total number of bytes sent/received must be 32 or less.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to/read from. (0-255)
     * @param data a single byte array/buffer containing data to write and the array contents will be
     *             overwritten with the data read from the I2C device register.
     * @return Returns the number of bytes read (>=0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cBlockProcessCall"
     */
    default int i2cBlockProcessCall(int handle, int register, ByteBuffer data) throws IOException {
        return i2cBlockProcessCall(handle, register, data, data.capacity());
    }

    /**
     * This reads count bytes from the specified register of the device associated with handle .
     * The maximum length of data that can be read is 32 bytes.
     * The minimum length of data that can be read is 1 byte.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte array (pre-allocated) to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns the number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadI2CBlockData"
     */
    int i2cReadI2CBlockData(int handle, int register, byte[] buffer, int offset, int length) throws IOException;

    /**
     * This reads count bytes from the specified register of the device associated with handle .
     * The maximum length of data that can be read is 32 bytes.
     * The minimum length of data that can be read is 1 byte.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte array (pre-allocated) to receive the read data
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns the number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadI2CBlockData"
     */
    default int i2cReadI2CBlockData(int handle, int register, byte[] buffer, int length) throws IOException{
        return i2cReadI2CBlockData(handle, register, buffer, 0, length);
    }

    /**
     * This reads count bytes from the specified register of the device associated with handle .
     * The maximum length of data that can be read is 32 bytes.
     * The minimum length of data that can be read is 1 byte.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte array (pre-allocated) to receive the read data
     * @return Returns the number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadI2CBlockData"
     */
    default int i2cReadI2CBlockData(int handle, int register, byte[] buffer) throws IOException{
        return i2cReadI2CBlockData(handle, register, buffer, buffer.length);
    }

    /**
     * This reads count bytes from the specified register of the device associated with handle .
     * The maximum length of data that can be read is 32 bytes.
     * The minimum length of data that can be read is 1 byte.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns the number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadI2CBlockData"
     */
    default int i2cReadI2CBlockData(int handle, int register, ByteBuffer buffer, int offset, int length) throws IOException{
        return i2cReadI2CBlockData(handle, register, buffer.array(), offset, length);
    }

    /**
     * This reads count bytes from the specified register of the device associated with handle .
     * The maximum length of data that can be read is 32 bytes.
     * The minimum length of data that can be read is 1 byte.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns the number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadI2CBlockData"
     */
    default int i2cReadI2CBlockData(int handle, int register, ByteBuffer buffer, int length) throws IOException{
        return i2cReadI2CBlockData(handle, register, buffer, 0, length);
    }

    /**
     * This reads count bytes from the specified register of the device associated with handle .
     * The maximum length of data that can be read is 32 bytes.
     * The minimum length of data that can be read is 1 byte.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to read from. (0-255)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @return Returns the number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadI2CBlockData"
     */
    default int i2cReadI2CBlockData(int handle, int register, ByteBuffer buffer) throws IOException{
        return i2cReadI2CBlockData(handle, register, buffer, buffer.capacity());
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data a byte array containing the data to write to the I2C device register
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    int i2cWriteI2CBlockData(int handle, int register, byte[] data, int offset, int length) throws IOException;

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data a byte array containing the data to write to the I2C device register
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, byte[] data, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, data, 0, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data a byte array containing the data to write to the I2C device register
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, byte[] data) throws IOException{
        return i2cWriteI2CBlockData(handle, register, data, data.length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param buffer a byte buffer containing the data to write to the I2C device register
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, ByteBuffer buffer, int offset, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, buffer.array(), offset, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param buffer a byte buffer containing the data to write to the I2C device register
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, ByteBuffer buffer, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, buffer, 0, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param buffer a byte buffer containing the data to write to the I2C device register
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, ByteBuffer buffer) throws IOException{
        return i2cWriteI2CBlockData(handle, register, buffer, buffer.capacity());
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param charset the character set/type used to encode the character sequence/string to bytes
     * @param data a byte array containing the data to write to the I2C device register
     * @param offset the starting offset position in the provided buffer to start writing from.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, Charset charset, CharSequence data, int offset, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, data.toString().getBytes(charset), offset, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param charset the character set/type used to encode the character sequence/string to bytes
     * @param data a byte array containing the data to write to the I2C device register
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, Charset charset, CharSequence data, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, charset, data, 0, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param charset the character set/type used to encode the character sequence/string to bytes
     * @param data a byte array containing the data to write to the I2C device register
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, Charset charset, CharSequence data) throws IOException{
        return i2cWriteI2CBlockData(handle, register, charset, data, data.length());
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data an ASCII string containing the data to write to the I2C device register
     * @param offset the starting offset position in the provided character sequence/string to start writing from.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, CharSequence data, int offset, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, data.toString().getBytes(StandardCharsets.US_ASCII), offset, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data an ASCII string containing the data to write to the I2C device register
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, CharSequence data, int length) throws IOException{
        return i2cWriteI2CBlockData(handle, register, data, 0, length);
    }

    /**
     * This writes 1 to 32 bytes to the specified register of the device associated with handle.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param register the I2C register address to write to. (0-255)
     * @param data an ASCII string containing the data to write to the I2C device register
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteI2CBlockData"
     */
    default int i2cWriteI2CBlockData(int handle, int register, CharSequence data) throws IOException{
        return i2cWriteI2CBlockData(handle, register, data, data.length());
    }

    /**
     * This reads count bytes from the raw device into byte buffer array.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer a byte array (pre-allocated) to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadDevice"
     */
    int i2cReadDevice(int handle, byte[] buffer, int offset, int length) throws IOException;

    /**
     * This reads count bytes from the raw device into byte buffer array.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer a byte array (pre-allocated) to receive the read data
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadDevice"
     */
    default int i2cReadDevice(int handle, byte[] buffer, int length) throws IOException{
        return i2cReadDevice(handle, buffer, 0, length);
    }

    /**
     * This reads count bytes from the raw device into byte buffer array.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer a byte array (pre-allocated) to receive the read data
     * @return Returns number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadDevice"
     */
    default int i2cReadDevice(int handle, byte[] buffer) throws IOException{
        return i2cReadDevice(handle, buffer, buffer.length);
    }

    /**
     * This reads count bytes from the raw device into byte buffer.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @param offset the starting offset position in the provided buffer to start copying the data bytes read.
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadDevice"
     */
    default int i2cReadDevice(int handle, ByteBuffer buffer, int offset, int length) throws IOException{
        return i2cReadDevice(handle, buffer.array(), offset, length);
    }

    /**
     * This reads count bytes from the raw device into byte buffer.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @param length the maximum number of bytes to read (1-32)
     * @return Returns number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadDevice"
     */
    default int i2cReadDevice(int handle, ByteBuffer buffer, int length) throws IOException{
        return i2cReadDevice(handle, buffer, 0, length);
    }

    /**
     * This reads count bytes from the raw device into byte buffer.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer a byte buffer (pre-allocated) to receive the read data
     * @return Returns number of bytes read (>0) if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_READ_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cReadDevice"
     */
    default int i2cReadDevice(int handle, ByteBuffer buffer) throws IOException{
        return i2cReadDevice(handle, buffer, buffer.capacity());
    }

    /**
     * This writes the length of bytes from the provided data array to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param data the array of bytes to write
     * @param offset the starting offset position in the provided array/buffer to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    int i2cWriteDevice(int handle, byte[] data, int offset, int length) throws IOException;

    /**
     * This writes the length of bytes from the provided data array to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param data the array of bytes to write
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, byte[] data, int length) throws IOException{
        return i2cWriteDevice(handle, data, 0, length);
    }

    /**
     * This writes the length of bytes from the provided data array to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param data the array of bytes to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, byte[] data) throws IOException{
        return i2cWriteDevice(handle, data, data.length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer the byte buffer of data to write
     * @param offset the starting offset position in the provided array/buffer to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, ByteBuffer buffer, int offset, int length) throws IOException{
        return i2cWriteDevice(handle, buffer.array(), offset ,length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer the byte buffer of data to write
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, ByteBuffer buffer, int length) throws IOException{
        return i2cWriteDevice(handle, buffer, 0 ,length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param buffer the byte buffer of data to write
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, ByteBuffer buffer) throws IOException{
        return i2cWriteDevice(handle, buffer, buffer.capacity());
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param charset the character set/type used to encode the character sequence/string to bytes
     * @param data the character sequence or string of data to write to the I2C device
     * @param offset the starting offset position in the provided character sequence/string to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, Charset charset, CharSequence data, int offset, int length) throws IOException{
        return i2cWriteDevice(handle, data.toString().getBytes(charset), offset ,length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param charset the character set/type used to encode the character sequence/string to bytes
     * @param data the character sequence or string of data to write to the I2C device
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, Charset charset, CharSequence data, int length) throws IOException{
        return i2cWriteDevice(handle, charset, data, 0, length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param charset the character set/type used to encode the character sequence/string to bytes
     * @param data the character sequence or string of data to write to the I2C device
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, Charset charset, CharSequence data) throws IOException{
        return i2cWriteDevice(handle, charset, data, data.length());
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param data the character sequence or string of data to write to the I2C device
     * @param offset the starting offset position in the provided character sequence/string to start writing from.
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, CharSequence data, int offset, int length) throws IOException{
        return i2cWriteDevice(handle, data.toString().getBytes(StandardCharsets.US_ASCII), offset ,length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param data the character sequence or string of data to write to the I2C device
     * @param length the number of bytes to write (maximum 32 bytes supported)
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, CharSequence data, int length) throws IOException{
        return i2cWriteDevice(handle, data, 0, length);
    }

    /**
     * This writes the length of bytes from the provided byte buffer to the raw I2C device.
     *
     * @param handle the open I2C device handle; (>=0, as returned by a call to i2cOpen)
     * @param data the character sequence or string of data to write to the I2C device
     * @return Returns 0 if OK, otherwise PI_BAD_HANDLE, PI_BAD_PARAM, or PI_I2C_WRITE_FAILED.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#i2cWriteDevice"
     */
    default int i2cWriteDevice(int handle, CharSequence data) throws IOException{
        return i2cWriteDevice(handle, data, data.length());
    }
}
