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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

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

    // ------------------------------------------------------------------------------------
    // SINGLE BYTE
    // ------------------------------------------------------------------------------------

    /**
     * Write a single raw byte value.
     *
     * @param b byte to be written
     * @throws IOException thrown on write error
     */
    int write(byte b) throws IOException;

    /**
     * Write a single raw byte value.
     *
     * @param b integer value that will be cast to a byte and written
     * @throws IOException thrown on write error
     */
    default int write(int b) throws IOException{
        return write((byte)b);
    }

    // ------------------------------------------------------------------------------------
    // BYTE ARRAY
    // ------------------------------------------------------------------------------------

    /**
     * Write an array of byte values with given offset (starting position) and length in the provided data array.
     *
     * @param data data array of bytes to be written
     * @param offset offset in data buffer to start at
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    int write(byte[] data, int offset, int length) throws IOException;

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
    default int write(byte ... data) throws IOException {
        return write(data, 0, data.length);
    }

    /**
     * Write multiple byte arrays.
     *
     * @param data data array of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(byte[] ... data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (byte[] ba : data) {
            os.write(ba);
        }
        return write(os.toByteArray());
    }

    /**
     * Write a collection of byte arrays.
     *
     * @param data collection of byte array of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Collection<byte[]> data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (byte[] ba : data) {
            os.write(ba);
        }
        return write(os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // BYTE BUFFER
    // ------------------------------------------------------------------------------------

    /**
     * Write a buffer of byte values with given offset (starting position) and length in the provided data buffer.
     *
     * @param buffer byte buffer of data to be written
     * @param offset offset in data buffer to start at
     * @param length number of bytes to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(ByteBuffer buffer, int offset, int length) throws IOException{
        return write(buffer.array(), offset, length);
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
        return write(buffer, buffer.position(), length);
    }

    /**
     * Write a buffer of byte values (all bytes in buffer).
     *
     * (The byte buffer is read from the current position up to the 'limit' value, not the 'capacity'.
     *  You may need to rewind() or flip() the byte buffer if you have just written to it.)
     *
     * @param buffer byte buffer of data to be written (from current position to limit)
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(ByteBuffer buffer) throws IOException{
        return write(buffer, buffer.remaining());
    }

    /**
     * Write multiple byte buffers of data.
     *
     * @param buffer byte buffer of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(ByteBuffer ... buffer) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (ByteBuffer bb : buffer) {
            os.write(bb.array());
        }
        return write(os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // INPUT STREAM
    // ------------------------------------------------------------------------------------

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
     * Write a buffer of byte values (all bytes in buffer).
     *
     * @param stream stream of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(InputStream ... stream) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (InputStream is : stream) {
            os.write(is.readAllBytes());
        }
        return write(os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // CHARACTER SEQUENCE (Strings)
    // ------------------------------------------------------------------------------------

    /**
     * Writes a data string with specified character set (encoding).
     *
     * @param data string data (US_ASCII) to be written
     * @param charset character set to use for byte encoding
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, CharSequence data) throws IOException{
        return write(data.toString().getBytes(charset));
    }

    /**
     * Writes a data string with specified character set (encoding).
     *
     * @param data string data (US_ASCII) to be written
     * @param charset character set to use for byte encoding
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, CharSequence ... data) throws IOException{
        StringBuilder builder = new StringBuilder();
        for(var d : data){
            builder.append(d);
        }
        return write(charset, builder);
    }

    /**
     * Write a collection of ASCII character sequences
     * with specified character set (encoding).
     *
     * @param charset character set to use for byte encoding
     * @param data collection of character sequences of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, Collection<CharSequence> ... data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (Collection<CharSequence> csc : data) {
            for (CharSequence cs : csc) {
                os.write(cs.toString().getBytes(charset));
            }
        }
        return write(os.toByteArray());
    }

    /**
     * Writes an ASCII data string/character sequence.
     *
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(CharSequence data) throws IOException{
        return write(StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes multiple ASCII data strings/character sequences.
     *
     * @param data string data (US_ASCII) to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(CharSequence ... data) throws IOException{
        return write(StandardCharsets.US_ASCII, data);
    }

    /**
     * Write a collection of ASCII character sequences.
     *
     * @param data collection of character sequences of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Collection<CharSequence> ... data) throws IOException {
        return write(StandardCharsets.US_ASCII, data);
    }

    // ------------------------------------------------------------------------------------
    // CHARACTER ARRAYS
    // ------------------------------------------------------------------------------------

    /**
     * Writes an ASCII based character array (1 or more chars) with
     * a given offset and length.
     *
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(char[] data, int offset, int length) throws IOException {
        return write(StandardCharsets.US_ASCII, data, offset, length);
    }

    /**
     * Writes an ASCII based character array (1 or more chars) with
     * a given length starting from the 0 index position.
     *
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(char[] data, int length) throws IOException {
        return write(StandardCharsets.US_ASCII, data, length);
    }

    /**
     * Writes an ASCII based character array (1 or more chars).
     *
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(char ... data) throws IOException{
        return write(StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes a character based array with a given offset and length.
     * Specify the encoding to be used to encode the chars into bytes.
     *
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, char[] data, int offset, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, offset, length));
        return write(bb.array());
    }

    /**
     * Writes a character based array starting at the first index with a given length.
     * Specify the encoding to be used to encode the chars into bytes.
     *
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, char[] data, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, 0, length));
        return write(bb.array());
    }

    /**
     * Writes a character array (1 or more chars).
     *
     * @param data character array (1 or more chars) to be written
     * @param charset character set to use for byte encoding
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, char ... data) throws IOException{
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data));
        return write(bb.array());
    }

    /**
     * Write a collection of character arrays.  Specify the encoding
     * to be used to encode the chars into bytes.
     *
     * @param data collection of character sequences of data to be written
     * @return The number of bytes written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, Collection<char[]> data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (char[] ca : data) {
            ByteBuffer bb = charset.encode(CharBuffer.wrap(ca));
            os.write(bb.array());
        }
        return write(os.toByteArray());
    }

    // ------------------------------------------------------------------------------------
    // CHARACTER BUFFERS
    // ------------------------------------------------------------------------------------

    /**
     * Writes an ASCII based character buffer with a given offset and length.
     *
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(CharBuffer data, int offset, int length) throws IOException {
        return write(StandardCharsets.US_ASCII, data, offset, length);
    }

    /**
     * Writes an ASCII based character buffer starting at first index to a given length.
     *
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(CharBuffer data, int length) throws IOException {
        return write(StandardCharsets.US_ASCII, data, length);
    }

    /**
     * Writes an ASCII based character buffer.
     *
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(CharBuffer data) throws IOException {
        return write(StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes an ASCII based character buffer.
     *
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(CharBuffer ... data) throws IOException {
        return write(StandardCharsets.US_ASCII, data);
    }

    /**
     * Writes an ASCII based character buffer with a given offset and length
     * using a specified character set to encode the chars into bytes.
     *
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param offset offset in data character array to start at
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, CharBuffer data, int offset, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, offset, length));
        return write(bb.array());
    }

    /**
     * Writes an ASCII based character buffer starting at first index to a
     * given length using a specified character set to encode the chars into bytes.
     *
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @param length number of character in character array to be written
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, CharBuffer data, int length) throws IOException {
        ByteBuffer bb = charset.encode(CharBuffer.wrap(data, 0, length));
        return write(bb.array());
    }

    /**
     * Writes an ASCII based character buffer using a specified
     * character set to encode the chars into bytes.
     *
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, CharBuffer data) throws IOException {
        ByteBuffer bb = charset.encode(data);
        return write(bb.array());
    }

    /**
     * Writes an ASCII based character buffer using a specified
     * character set to encode the chars into bytes.
     *
     * @param charset character set to use for byte encoding
     * @param data ASCII character array used for data write
     * @return The number of bytes (not characters) written, possibly zero
     * @throws IOException thrown on write error
     */
    default int write(Charset charset, CharBuffer ... data) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for (CharBuffer cb : data) {
            ByteBuffer bb = charset.encode(cb);
            os.write(bb.array());
        }
        return write(os.toByteArray());
    }


    // ------------------------------------------------------------------------------------
    // OUTPUT STREAM
    // ------------------------------------------------------------------------------------

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
