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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

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


    // ------------------------------------------------------------------------------------
    // SINGLE BYTE
    // ------------------------------------------------------------------------------------

    /**
     * Write a single raw byte (8-bit) value to the I2C device register.
     *
     * @param register the register address to write to
     * @param b byte to be written
     * @throws IOException thrown on write error
     */
    int writeRegister(int register, byte b) throws IOException;

    /**
     * Write a single raw byte (8-bit) value to the I2C device register.
     * (The integer value provided will be cast to a byte thus only using the lowest 8 bits)
     *
     * @param register the register address to write to
     * @param b byte to be written; the provided Integer wll be cast to a Byte.
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, int b) throws IOException{
        return writeRegister(register, (byte)b);
    }

    // ------------------------------------------------------------------------------------
    // SINGLE WORD (2-byte)
    // ------------------------------------------------------------------------------------

    /**
     * Write a single word value (16-bit) to the I2C device register.
     *
     * @param register the register address to write to
     * @param word 16-bit word value to be written
     * @throws IOException thrown on write error
     */
    default int writeRegisterWord(int register, int word) throws IOException{
        byte[] buffer = new byte[] { (byte)(word >> 8), (byte)word };
        return this.writeRegister(register, buffer);
    }

    // ------------------------------------------------------------------------------------
    // BYTE ARRAY
    // ------------------------------------------------------------------------------------

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
    int writeRegister(int register, byte[] data, int offset, int length) throws IOException;

    /**
     * Write an array of byte values starting with the first byte in the array
     * up to the provided length to a specific I2C device register.
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
     * Write an array of byte values to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, byte ... data) throws IOException {
        return writeRegister(register, data, 0, data.length);
    }

    /**
     * Write multiple byte arrays of data to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, byte[] ... data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (byte[] ba : data) {
            os.write(ba);
        }
        return writeRegister(register, os.toByteArray());
    }

    /**
     * Write a collection of byte arrays of data to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Collection<byte[]> data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (byte[] ba : data) {
            os.write(ba);
        }
        return writeRegister(register, os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // BYTE BUFFER
    // ------------------------------------------------------------------------------------

    /**
     * Write a buffer of byte values starting from a given offset index in the
     * array up to the provided length to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @param offset offset in buffer
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, ByteBuffer buffer, int offset, int length) throws IOException{
        return writeRegister(register, buffer.array(), offset, length);
    }

    /**
     * Write a buffer of byte values starting with the first byte in the array
     * up to the provided length to a specific I2C device register.
     *
     * @param buffer byte buffer of data to be written
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */

    /**
     * Write a buffer of byte values starting from the first byte in the buffer
     * up to the provided length to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, ByteBuffer buffer, int length) throws IOException{
        return writeRegister(register, buffer, buffer.position(), length);
    }

    /**
     * Write a buffer of byte values (all bytes in buffer) to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, ByteBuffer buffer) throws IOException{
        return writeRegister(register, buffer, buffer.remaining());
    }

    /**
     * Write multiple byte buffers to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param buffer byte buffer of data to be written to the i2c device in one go
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, ByteBuffer ... buffer) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (ByteBuffer bb : buffer) {
            os.write(bb.array());
        }
        return writeRegister(register, os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // INPUT STREAM
    // ------------------------------------------------------------------------------------

    /**
     * Write a stream of data bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param stream stream of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, InputStream stream) throws IOException{
        return writeRegister(register, stream.readAllBytes());
    }

    /**
     * Write multiple streams of data bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param stream stream of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, InputStream ... stream) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (InputStream is : stream) {
            os.write(is.readAllBytes());
        }
        return writeRegister(register, os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // CHARACTER SEQUENCE (Strings)
    // ------------------------------------------------------------------------------------

    /**
     * Writes a data string with specified character set (encoding)
     * to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, CharSequence data) throws IOException{
        return writeRegister(register, data.toString().getBytes(charset));
    }

    /**
     * Writes a data string with specified character set (encoding)
     * to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, CharSequence ... data) throws IOException{
        StringBuilder builder = new StringBuilder();
        for(var d : data){
            builder.append(d);
        }
        return writeRegister(register, charset, builder);
    }

    /**
     * Write a collection of ASCII character sequences with specified
     * character set (encoding) to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data collection of character sequences of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, Collection<CharSequence> ... data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (Collection<CharSequence> csc : data) {
            for (CharSequence cs : csc) {
                os.write(cs.toString().getBytes(charset));
            }
        }
        return writeRegister(register, os.toByteArray());
    }

    /**
     * Writes an ASCII data string/character sequence to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, CharSequence data) throws IOException{
        return writeRegister(register, StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes multiple ASCII data strings/character sequences to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, CharSequence ... data) throws IOException{
        return writeRegister(register, StandardCharsets.US_ASCII, data);
    }

    /**
     * Write a collection of ASCII character sequences to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data collection of character sequences of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Collection<CharSequence> ... data) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data);
    }

    // ------------------------------------------------------------------------------------
    // CHARACTER ARRAYS
    // ------------------------------------------------------------------------------------

    /**
     * Writes an ASCII based character array (1 or more chars) with
     * a given offset and length to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, char[] data, int offset, int length) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data, offset, length);
    }

    /**
     * Writes an ASCII based character array (1 or more chars) with a given length
     * starting from the 0 index position to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, char[] data, int length) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data, length);
    }

    /**
     * Writes an ASCII based character array (1 or more chars) to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, char ... data) throws IOException{
        return writeRegister(register, StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes a character based array with a given offset and length.  Specify the encoding
     * to be used to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, char[] data, int offset, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, offset, length));
        return writeRegister(register, bb.array());
    }

    /**
     * Writes a character based array starting at the first index with a given length.  Specify
     * the encoding to be used to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, char[] data, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, 0, length));
        return writeRegister(register, bb.array());
    }

    /**
     * Writes a character array (1 or more chars) to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data character array (1 or more chars) to be written
     * @param charset character set to use for byte encoding
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, char ... data) throws IOException{
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data));
        return writeRegister(register, bb.array());
    }

    /**
     * Write a collection of character arrays.  Specify the encoding to be
     * used to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data collection of character sequences of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, Collection<char[]> data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (char[] ca : data) {
            ByteBuffer bb = charset.encode(CharBuffer.wrap(ca));
            os.write(bb.array());
        }
        return writeRegister(register, os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // CHARACTER BUFFERS
    // ------------------------------------------------------------------------------------

    /**
     * Writes an ASCII based character buffer with a given offset
     * and length to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, CharBuffer data, int offset, int length) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data, offset, length);
    }

    /**
     * Writes an ASCII based character buffer starting at first
     * index to a given length to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, CharBuffer data, int length) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data, length);
    }

    /**
     * Writes an ASCII based character buffer to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, CharBuffer data) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes an ASCII based character buffer to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, CharBuffer ... data) throws IOException {
        return writeRegister(register, StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes an ASCII based character buffer with a given offset and length using a specified
     * character set to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, CharBuffer data, int offset, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, offset, length));
        return writeRegister(register, bb.array());
    }

    /**
     * Writes an ASCII based character buffer starting at first index to a given length using
     * a specified character set to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, CharBuffer data, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, 0, length));
        return writeRegister(register, bb.array());
    }

    /**
     * Writes an ASCII based character buffer using a specified character set
     * to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, CharBuffer data) throws IOException {
        ByteBuffer bb = charset.encode(data);
        return writeRegister(register, bb.array());
    }

    /**
     * Writes an ASCII based character buffer using a specified character
     * set to encode the chars into bytes to a specific I2C device register.
     *
     * @param register the register address to write to
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int writeRegister(int register, Charset charset, CharBuffer ... data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (CharBuffer cb : data) {
            ByteBuffer bb = charset.encode(cb);
            os.write(bb.array());
        }
        return writeRegister(register, os.toByteArray());
    }
}
