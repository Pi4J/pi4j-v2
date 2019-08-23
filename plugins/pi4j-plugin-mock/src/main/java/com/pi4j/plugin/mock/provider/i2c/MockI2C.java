package com.pi4j.plugin.mock.provider.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockI2C.java
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


import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CBase;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.plugin.mock.Mock;
import com.pi4j.util.StringUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Objects;

public class MockI2C extends I2CBase implements I2C {

    /**
     * ATTENTION:  The storage and management of the byte arrays
     *  are terribly inefficient and not intended for real-world
     *  usage.  These are only intended to unit testing the
     *  Pi4J I2C APIs.
     */
    protected ArrayDeque<Byte>[] registers = new ArrayDeque[256]; // 256 supported registers (0-255)
    protected ArrayDeque<Byte> raw = new ArrayDeque<>();

    public MockI2C(I2CProvider provider, I2CConfig config){
        super(provider, config);
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: CREATE(BUS=" + config.bus() + "; DEVICE=" + config.device() + ")");
        System.out.println();
    }

    @Override
    public void close() throws IOException {
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: CLOSE(BUS=" + config.bus() + "; DEVICE=" + config.device() + ")");
        System.out.println();
        super.close();
    }

    // -------------------------------------------------------------------
    // RAW DEVICE WRITE FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int write(byte b) throws IOException {
        raw.add(b);
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(0x");
        System.out.print(StringUtil.toHexString(b));
        System.out.println(")");
        return 0;
    }

    @Override
    public int write(byte[] data, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, data.length);
        for(int p = offset; p-offset < length; p++){
            raw.add(data[p]); // add to internal buffer
        }
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(0x");
        System.out.print(StringUtil.toHexString(data, offset, length));
        System.out.println(")");
        return length;
    }

    @Override
    public int write(Charset charset, CharSequence data) throws IOException {
        byte[] buffer = data.toString().getBytes(charset);
        for(int p = 0; p < buffer.length; p++){
            raw.add(buffer[p]); // add to internal buffer
        }
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(\"");
        System.out.print(data);
        System.out.println("\")");
        return data.length();
    }

    // -------------------------------------------------------------------
    // RAW DEVICE READ FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int read() throws IOException{
        if(raw.isEmpty()) return -1;
        byte b = raw.pop();
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ(0x");
        System.out.print(StringUtil.toHexString(b));
        System.out.println(")");
        return b;
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException{
        Objects.checkFromIndexSize(offset, length, buffer.length);

        if(raw.isEmpty()) return -1;
        int counter = 0;
        for(int p = 0; p < length; p++) {
            if(p+offset > buffer.length) break;
            if(raw.isEmpty()) break;;
            buffer[offset + p] = raw.pop();
            counter++;
        }

        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ(0x");
        System.out.print(StringUtil.toHexString(buffer, offset, length));
        System.out.println(")");

        return counter;
    }

    @Override
    public String readString(int length, Charset charset) throws IOException{
        if(raw.isEmpty()) return null;
        byte[] buffer = new byte[length];
        for(int p = 0; p < length; p++) {
            if(raw.isEmpty()) break;;
            buffer[p] = raw.pop();
        }
        String result = new String(buffer, charset);

        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ(\"");
        System.out.print(result);
        System.out.println("\")");

        return result;
    }

    // -------------------------------------------------------------------
    // DEVICE REGISTER WRITE FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int writeRegister(int register, byte b) throws IOException {

        if(registers[register] == null) registers[register] = new ArrayDeque<Byte>();
        registers[register].add(b);

        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(");
        System.out.print("REG=");
        System.out.print(register);
        System.out.print(", 0x");
        System.out.print(StringUtil.toHexString(b));
        System.out.println(")");
        return 0;
    }

    @Override
    public int writeRegister(int register, byte[] data, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, data.length);

        // add to internal buffer
        if(registers[register] == null) registers[register] = new ArrayDeque<Byte>();
        for(int p = offset; p-offset < length; p++){
            registers[register].add(data[p]);
        }

        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(");
        System.out.print("REG=");
        System.out.print(register);
        System.out.print(", 0x");
        System.out.print(StringUtil.toHexString(data, offset, length));
        System.out.println(")");

        return length;
    }

    @Override
    public int writeRegister(int register, Charset charset, CharSequence data) throws IOException{

        if(registers[register] == null) registers[register] = new ArrayDeque<Byte>();
        byte[] buffer = data.toString().getBytes(charset);
        for(int p = 0; p < buffer.length; p++){
            registers[register].add(buffer[p]); // add to internal buffer
        }

        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(");
        System.out.print("REG=");
        System.out.print(register);
        System.out.print(", \"");
        System.out.print(data);
        System.out.println("\")");
        return data.length();
    }

    // -------------------------------------------------------------------
    // DEVICE REGISTER READ FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int readRegister(int register) throws IOException {
        if(registers[register] == null) throw new IOException("No available data to read");
        if(registers[register].isEmpty()) throw new IOException("No available data to read");
        byte b = registers[register].pop();
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ(");
        System.out.print("REG=");
        System.out.print(register);
        System.out.print(", 0x");
        System.out.print(StringUtil.toHexString(b));
        System.out.println(")");
        return b;
    }

    @Override
    public int readRegister(int register, ByteBuffer buffer, int offset, int length) throws IOException {
        if(registers[register] == null) return -1;
        if(registers[register].isEmpty()) return -1;

        int counter = 0;
        for(int p = 0; p < length; p++) {
            if(p+offset > buffer.capacity()) break;
            if(registers[register].isEmpty()) break;;
            buffer.put(offset + p, registers[register].pop());
            counter++;
        }
        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ(");
        System.out.print("REG=");
        System.out.print(register);
        System.out.print(", 0x");
        System.out.print(StringUtil.toHexString(buffer, offset, length));
        System.out.println(")");
        return counter;
    }

    @Override
    public String readRegisterString(int register, int length, Charset charset) throws IOException {
        if(registers[register] == null) return null;
        if(registers[register].isEmpty()) return null;

        byte[] buffer = new byte[length];
        for(int p = 0; p < length; p++) {
            if(registers[register].isEmpty()) break;;
            buffer[p] = registers[register].pop();
        }
        String result = new String(buffer, charset);

        System.out.print(" [");
        System.out.print(Mock.I2C_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ(");
        System.out.print("REG=");
        System.out.print(register);
        System.out.print(", \"");
        System.out.print(result);
        System.out.println("\")");

        return result;
    }
}
