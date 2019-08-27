package com.pi4j.plugin.mock.provider.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockSerial.java
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

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialBase;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.plugin.mock.Mock;
import com.pi4j.util.StringUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Objects;

public class MockSerial extends SerialBase implements Serial {

    /**
     * ATTENTION:  The storage and management of the byte arrays
     *  are terribly inefficient and not intended for real-world
     *  usage.  These are only intended to unit testing the
     *  Pi4J SERIAL APIs.
     */
    protected ArrayDeque<Byte> raw = new ArrayDeque<>();

    public MockSerial(SerialProvider provider, SerialConfig config){
        super(provider, config);
        System.out.print(" [");
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: OPEN(DEVICE=" + config.device() + "; BAUD=" + config.baud() + ")");
        System.out.println();
    }

    @Override
    public int available() throws IOException {
        return raw.size();
    }

    @Override
    public void close() throws IOException {
        System.out.print(" [");
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: CLOSE(DEVICE=" + config.device() + "; BAUD=" + config.baud() + ")");
        System.out.println();
        super.close();
    }

    @Override
    public int write(byte b) throws IOException {
        raw.add(b);
        System.out.print(" [");
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
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
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
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
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(\"");
        System.out.print(data);
        System.out.println("\")");
        return data.length();
    }

    @Override
    public int read() throws IOException {
        if(raw.isEmpty()) return -1;
        byte b = raw.pop();
        System.out.print(" [");
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ (0x");
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
        System.out.print(Mock.SERIAL_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ (0x");
        System.out.print(StringUtil.toHexString(buffer, offset, length));
        System.out.println(")");

        return counter;
    }
}
