package com.pi4j.io;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  IODataReader.java
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
 * Data Writer Interface for Pi4J Data Communications
 *
 * @author Robert Savage
 *
 * Based on previous contributions from:
 *        Daniel Sendula,
 *        <a href="http://raspelikan.blogspot.co.at">RasPelikan</a>
 */
public interface IODataReader {

    int read() throws IOException;
    int read(ByteBuffer buffer, int offset, int length) throws IOException;

    default ByteBuffer read(int length) throws IOException {
        byte[] temp = new byte[length];
        int actual = read(temp, 0, length);
        return ByteBuffer.wrap(temp, 0, actual);
    }

    default int read(byte[] buffer, int offset, int length) throws IOException{
        ByteBuffer bb = ByteBuffer.wrap(buffer);
        return read(bb, offset, length);
    }

    default int read(byte[] buffer, int length) throws IOException{
        return read(buffer, 0, length);
    }
    default int read(byte[] buffer) throws IOException{
        return read(buffer, 0, buffer.length);
    }

    default int read(ByteBuffer buffer, int length) throws IOException{
        return read(buffer, 0, length);
    }
    default int read(ByteBuffer buffer) throws IOException{
        return read(buffer, 0, buffer.capacity());
    }

    default String readString(int length, Charset charset) throws IOException{
        byte[] temp = new byte[length];
        int actual = read(temp, 0, length);
        return new String(temp, 0, actual, charset);
    }

    default String readString(int length) throws IOException{
        return readString(length, StandardCharsets.US_ASCII);
    }

    default InputStream getInputStream(){
        var t = this;
        return new InputStream() {
            @Override
            public int read() throws IOException {
                return t.read();
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                Objects.checkFromIndexSize(off, len, b.length);
                return t.read(b, off, len);
            }
        };
    }

    default InputStream in() {
        return getInputStream();
    }
}
