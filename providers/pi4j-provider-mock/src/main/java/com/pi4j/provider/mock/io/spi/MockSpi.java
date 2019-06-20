package com.pi4j.provider.mock.io.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PROVIDER :: Mock Provider
 * FILENAME      :  MockSpiDevice.java
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

import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiBase;
import com.pi4j.io.spi.SpiConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class MockSpi extends SpiBase implements Spi {

    public MockSpi(SpiConfig config){
        super(config);
    }

    @Override
    public String write(String data, Charset charset) throws IOException {
        return null;
    }

    @Override
    public String write(String data, String charset) throws IOException {
        return null;
    }

    @Override
    public ByteBuffer write(ByteBuffer data) throws IOException {
        return null;
    }

    @Override
    public byte[] write(InputStream input) throws IOException {
        return new byte[0];
    }

    @Override
    public int write(InputStream input, OutputStream output) throws IOException {
        return 0;
    }

    @Override
    public byte[] write(byte[] data, int start, int length) throws IOException {
        return new byte[0];
    }

    @Override
    public byte[] write(byte... data) throws IOException {
        return new byte[0];
    }

    @Override
    public short[] write(short[] data, int start, int length) throws IOException {
        return new short[0];
    }

    @Override
    public short[] write(short... data) throws IOException {
        return new short[0];
    }
}
