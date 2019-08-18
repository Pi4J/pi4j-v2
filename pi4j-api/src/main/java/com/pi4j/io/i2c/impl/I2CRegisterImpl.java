package com.pi4j.io.i2c.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  I2CRegisterImpl.java
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

import com.pi4j.io.exception.IOReadException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CRegister;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

public class I2CRegisterImpl implements I2CRegister {

    protected final int address;
    protected final I2C i2c;

    public I2CRegisterImpl(I2C i2c, int address){
        this.i2c = i2c;
        this.address = address;
    }

    @Override
    public int getAddress() {
        return this.address;
    }

    @Override
    public void write(byte b) throws IOException {
        this.i2c.writeRegister(this.address, b);
    }

    /**
     * Write a single word value (16-bit) to the I2C device register.
     *
     * @param word 16-bit word value to be written
     * @return The number of bytes written, possibly zero; typically 2
     * @throws IOException thrown on write error
     */
    @Override
    public void writeWord(int word) throws IOException{
        this.i2c.writeRegisterWord(this.address, word);
    }

    @Override
    public int readWord() throws IOException, IOReadException {
        return this.i2c.readRegisterWord(this.address);
    }

    @Override
    public int writeReadWord(int word) throws IOException, IOReadException {
        return this.i2c.writeReadRegisterWord(this.address, word);
    }

    @Override
    public int write(ByteBuffer buffer, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, buffer.capacity());
        return this.i2c.writeRegister(this.address, buffer, offset, length);
    }

    @Override
    public int write(String data, Charset charset) throws IOException {
        return this.i2c.writeRegister(this.address, data, charset);
    }

    @Override
    public int read() throws IOException {
        return this.i2c.readRegister(this.address);
    }

    @Override
    public int read(ByteBuffer buffer, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, buffer.capacity());
        return this.i2c.readRegister(this.address, buffer, offset, length);
    }

    @Override
    public String readString(int length, Charset charset) throws IOException, IOReadException {
        return this.i2c.readRegisterString(this.address, length, charset);
    }
}
