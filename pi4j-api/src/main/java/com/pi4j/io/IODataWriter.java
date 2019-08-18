package com.pi4j.io;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  IODataWriter.java
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
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Data Writer Interface for Pi4J Data Communications
 *
 * @author Robert Savage
 *
 * Based on previous contributions from:
 *        Daniel Sendula,
 *        <a href="http://raspelikan.blogspot.co.at">RasPelikan</a>
 */
public interface IODataWriter {

    /**
     * Write a single raw byte value.
     *
     * @param b byte to be written
     * @throws IOException thrown on write error
     */
    void write(byte b) throws IOException;

    /**
     * Write a buffer of byte values with given offset (starting position) and length in the provided data buffer.
     *
     * @param buffer byte buffer of data to be written
     * @param offset offset in data buffer to start at
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    int write(ByteBuffer buffer, int offset, int length) throws IOException;

    /**
     * Write a single raw byte value.
     *
     * @param b byte to be written
     * @throws IOException thrown on write error
     */
    default void write(int b) throws IOException{
        write((byte)b);
    }

    /**
     * Write a single word value (16-bit) to the raw I2C device.
     *
     * @param word 16-bit word value to be written
     * @throws IOException thrown on write error
     */
    default void writeWord(int word) throws IOException{
        byte[] buffer = new byte[] { (byte)(word >> 8), (byte)word };
        this.write(buffer);
    }

    /**
     * Write an array of byte values with given offset (starting position) and length in the provided data array.
     *
     * @param data data array of bytes to be written
     * @param offset offset in data buffer to start at
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(byte[] data, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, data.length);
        return write(ByteBuffer.wrap(data), offset, length);
    }

    /**
     * Write an array of byte values starting with the first byte in the array up to the provided length.
     *
     * @param data data array of bytes to be written
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(byte[] data, int length) throws IOException{
        return write(data, 0, length);
    }

    /**
     * Write an array of byte values (all bytes in array).
     *
     * @param data data array of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(byte[] data) throws IOException{
        return write(data, 0, data.length);
    }

    /**
     * Write a buffer of byte values starting with the first byte in the array up to the provided length.
     *
     * @param buffer byte buffer of data to be written
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(ByteBuffer buffer, int length) throws IOException{
        return write(buffer, 0, length);
    }

    /**
     * Write a buffer of byte values (all bytes in buffer).
     *
     * @param buffer byte buffer of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(ByteBuffer buffer) throws IOException{
        return write(buffer, 0, buffer.capacity());
    }

    /**
     * Write a buffer of byte values (all bytes in buffer).
     *
     * @param stream stream of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(InputStream stream) throws IOException{
        return write(stream.readAllBytes());
    }

    /**
     * Writes an ASCII data string.
     *
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(String data) throws IOException{
        return write(data, StandardCharsets.US_ASCII);
    }

    /**
     * Writes a data string with specified character set (encoding).
     *
     * @param data string data (US_ASCII) to be written
     * @param charset character set to use for byte encoding
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(String data, Charset charset) throws IOException{
        return write(data.getBytes(charset));
    }

    /**
     * Get an output stream to write data to
     * @return new output stream instance to write to
     */
    default OutputStream getOutputStream(){
        var t = this;
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                t.write((byte)b);;
            }

            @Override
            public void write(byte b[]) throws IOException {
                t.write(b);
            }

            @Override
            public void write(byte b[], int off, int len) throws IOException {
                this.write(b, off, len);
            }
        };
    }

    /**
     * Get an output stream to write data to
     * @return new output stream instance to write to
     */
    default OutputStream out() {
        return getOutputStream();
    }
}
