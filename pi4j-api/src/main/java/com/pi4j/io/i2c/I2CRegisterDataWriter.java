package com.pi4j.io.i2c;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  I2CRegisterDataWriter.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * I2C Register Data Writer Interface for Pi4J Data Communications
 *
 * @author Robert Savage
 *
 * Based on previous contributions from:
 *        Daniel Sendula,
 *        <a href="http://raspelikan.blogspot.co.at">RasPelikan</a>
 */
public interface I2CRegisterDataWriter {

    /**
     * Write a single raw byte (8-bit) value to the I2C device register.
     *
     * @param register the register address to write to
     * @param b byte to be written
     * @throws IOException thrown on write error
     */
    void writeRegister(int register, byte b) throws IOException;

    /**
     * Write a single raw byte (8-bit) value to the I2C device register.
     *
     * @param register the register address to write to
     * @param b byte to be written; the provided Integer wll be cast to a Byte.
     * @throws IOException thrown on write error
     */
    default void writeRegister(int register, int b) throws IOException{
        writeRegister(register, (byte)b);
    }

    /**
     * Write a single word value (16-bit) to the I2C device register.
     *
     * @param register the register address to write to
     * @param word 16-bit word value to be written
     * @throws IOException thrown on write error
     */
    default void writeRegisterWord(int register, int word) throws IOException{
        byte[] buffer = new byte[] { (byte)(word >> 8), (byte)word };
        this.writeRegister(register, buffer);
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @param offset offset in buffer
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    int writeRegister(int register, ByteBuffer buffer, int offset, int length) throws IOException;

    /**
     * Write a array of byte values with given offset (starting position)
     * and length in the provided data array to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data data array of bytes to be written
     * @param offset offset in data buffer to start at
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, byte[] data, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, data.length);
        return writeRegister(register, ByteBuffer.wrap(data), offset, length);
    }

    /**
     * Write a array of byte values starting with the first byte in the array up to the provided length.
     *
     * @param register the register address to write to
     * @param data data array of bytes to be written
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, byte[] data, int length) throws IOException{
        return writeRegister(register, data, 0, length);
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param data data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, byte[] data) throws IOException{
        return writeRegister(register, data, 0, data.length);
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, ByteBuffer buffer, int length) throws IOException{
        return writeRegister(register, buffer, 0, length);
    }


    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, ByteBuffer buffer) throws IOException{
        return writeRegister(register, buffer, 0, buffer.capacity());
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param stream stream of data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, InputStream stream) throws IOException{
        return writeRegister(register, stream.readAllBytes());
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param data string data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, String data) throws IOException{
        return writeRegister(register, data.getBytes(StandardCharsets.US_ASCII));
    }

    /**
     * This method writes all bytes included in the given buffer directly to the i2c device.
     *
     * @param register the register address to write to
     * @param data string data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, String data, Charset charset) throws IOException{
        return writeRegister(register, data.getBytes(charset));
    }
}
