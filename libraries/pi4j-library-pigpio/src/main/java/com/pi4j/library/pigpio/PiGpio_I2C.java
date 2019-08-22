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
import java.util.Arrays;
import java.util.Objects;

public interface PiGpio_I2C {

    int i2cOpen(int bus, int device) throws IOException;
    void i2cClose(int handle) throws IOException;
    void i2cWriteQuick(int handle, boolean bit) throws IOException;
    void i2cWriteByte(int handle, byte value) throws IOException;
    int i2cReadByte(int handle) throws IOException;
    void i2cWriteByteData(int handle, int register, byte value) throws IOException;
    void i2cWriteWordData(int handle, int register, int value) throws IOException;
    int i2cReadByteData(int handle, int register) throws IOException;
    int i2cReadWordData(int handle, int register) throws IOException;
    int i2cProcessCall(int handle, int register, int value) throws IOException;

    void i2cWriteBlockData(int handle, int register, byte[] data) throws IOException;

    default void i2cWriteBlockData(int handle, int register, byte[] data, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, data.length);
        byte[] temp = Arrays.copyOfRange(data, offset, length);
        i2cWriteBlockData(handle, register, temp);
    }
    default void i2cWriteBlockData(int handle, int register, byte[] data, int length) throws IOException{
        i2cWriteBlockData(handle, register, data, 0 ,length);
    }
    default void i2cWriteBlockData(int handle, int register, ByteBuffer buffer) throws IOException{
        i2cWriteBlockData(handle, register, buffer.array());
    }
    default void i2cWriteBlockData(int handle, int register, ByteBuffer buffer, int offset, int length) throws IOException{
        i2cWriteBlockData(handle, register, buffer.array(), offset, length);
    }
    default void i2cWriteBlockData(int handle, int register, ByteBuffer buffer, int length) throws IOException{
        i2cWriteBlockData(handle, register, buffer, 0, length);
    }
    default void i2cWriteBlockData(int handle, int register, CharSequence data) throws IOException{
        i2cWriteBlockData(handle, register, data, StandardCharsets.US_ASCII);
    }
    default void i2cWriteBlockData(int handle, int register, CharSequence data, Charset charset) throws IOException{
        i2cWriteBlockData(handle, register, data.toString().getBytes(charset));
    }

    byte[] i2cReadBlockData(int handle, int register) throws IOException;
    default String i2cReadBlockDataToString(int handle, int register) throws IOException{
        byte[] rx = i2cReadBlockData(handle, register);
        return new String(rx, StandardCharsets.US_ASCII);
    }

    byte[] i2cBlockProcessCall(int handle, int register, byte[] data) throws IOException;
    default String i2cBlockProcessCallToString(int handle, int register, byte[] data) throws IOException{
        byte[] rx = i2cBlockProcessCall(handle, register, data);
        return new String(rx, StandardCharsets.US_ASCII);
    }
    default byte[] i2cBlockProcessCall(int handle, int register, CharSequence data) throws IOException{
        return i2cBlockProcessCall(handle, register, data.toString().getBytes(StandardCharsets.US_ASCII));
    }
    default String i2cBlockProcessCallToString(int handle, int register, CharSequence data) throws IOException{
        byte[] rx = i2cBlockProcessCall(handle, register, data);
        return new String(rx, StandardCharsets.US_ASCII);
    }

    byte[] i2cReadI2CBlockData(int handle, int register, int length) throws IOException;
    default String i2cReadI2CBlockDataToString(int handle, int register, int length) throws IOException{
        byte[] rx = i2cReadI2CBlockData(handle, register, length);
        return new String(rx, StandardCharsets.US_ASCII);
    }

    void i2cWriteI2CBlockData(int handle, int register, byte[] data) throws IOException;

    default void i2cWriteI2CBlockData(int handle, int register, byte[] data, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, data.length);
        byte[] temp = Arrays.copyOfRange(data, offset, length);
        i2cWriteI2CBlockData(handle, register, temp);
    }
    default void i2cWriteI2CBlockData(int handle, int register, byte[] data, int length) throws IOException{
        i2cWriteI2CBlockData(handle, register, data, 0 ,length);
    }
    default void i2cWriteI2CBlockData(int handle, int register, ByteBuffer buffer) throws IOException{
        i2cWriteI2CBlockData(handle, register, buffer.array());
    }
    default void i2cWriteI2CBlockData(int handle, int register, ByteBuffer buffer, int offset, int length) throws IOException{
        i2cWriteI2CBlockData(handle, register, buffer.array(), offset, length);
    }
    default void i2cWriteI2CBlockData(int handle, int register, ByteBuffer buffer, int length) throws IOException{
        i2cWriteI2CBlockData(handle, register, buffer, 0, length);
    }
    default void i2cWriteI2CBlockData(int handle, int register, CharSequence data) throws IOException{
        i2cWriteI2CBlockData(handle, register, data, StandardCharsets.US_ASCII);
    }
    default void i2cWriteI2CBlockData(int handle, int register, CharSequence data, Charset charset) throws IOException{
        i2cWriteI2CBlockData(handle, register, data.toString().getBytes(charset));
    }

    byte[] i2cReadDevice(int handle, int length) throws IOException;
    default String i2cReadDeviceToString(int handle, int length) throws IOException{
        byte[] rx = i2cReadDevice(handle, length);
        return new String(rx, StandardCharsets.US_ASCII);
    }

    int i2cWriteDevice(int handle, byte[] data) throws IOException;
    default int i2cWriteDevice(int handle, byte[] data, int length) throws IOException{
        return i2cWriteDevice(handle, data, 0, length);
    }
    default int i2cWriteDevice(int handle, byte[] data, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, data.length);
        return i2cWriteDevice(handle, Arrays.copyOfRange(data, offset, length));
    }

    default void i2cWriteDevice(int handle, ByteBuffer buffer) throws IOException{
        i2cWriteDevice(handle, buffer.array());
    }
    default void i2cWriteDevice(int handle, ByteBuffer buffer, int length) throws IOException{
        i2cWriteDevice(handle, buffer, 0, length);
    }
    default void i2cWriteDevice(int handle, ByteBuffer buffer, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, buffer.capacity());
        i2cWriteDevice(handle, buffer.array(), offset, length);
    }
    default void i2cWriteDevice(int handle, CharSequence data) throws IOException{
        i2cWriteDevice(handle, data, StandardCharsets.US_ASCII);
    }
    default void i2cWriteDevice(int handle, CharSequence data, Charset charset) throws IOException{
        i2cWriteDevice(handle, data.toString().getBytes(charset));
    }
}
