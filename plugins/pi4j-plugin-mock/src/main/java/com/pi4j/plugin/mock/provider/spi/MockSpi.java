package com.pi4j.plugin.mock.provider.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockSpi.java
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
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.plugin.mock.Mock;
import com.pi4j.util.StringUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Objects;

/**
 * <p>MockSpi class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MockSpi extends SpiBase implements Spi {

    protected ArrayDeque<Byte> raw = new ArrayDeque<>();

    /**
     * <p>Constructor for MockSpi.</p>
     *
     * @param provider a {@link com.pi4j.io.spi.SpiProvider} object.
     * @param config a {@link com.pi4j.io.spi.SpiConfig} object.
     */
    public MockSpi(SpiProvider provider, SpiConfig config) {
        super(provider, config);
        System.out.print(" [");
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: OPEN(CHANNEL=" + config.address() + "; BAUD=" + config.baud() + ")");
        System.out.println();
    }
    /** {@inheritDoc} */
    @Override
    public void close() throws IOException {
        System.out.print(" [");
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: CLOSE(CHANNEL=" + config.address() + "; BAUD=" + config.baud() + ")");
        System.out.println();
        super.close();
    }

    /** {@inheritDoc} */
    @Override
    public int transfer(byte[] write, int writeOffset, byte[] read, int readOffset, int numberOfBytes) throws IOException {
        int readIndex = readOffset;
        // simply just echo out the write data back to the read buffer
        for(int n = writeOffset; n < numberOfBytes - writeOffset; n++){
            read[readIndex] = write[n];
            readIndex++;
        }
        // return number of byte returned
        return readIndex - readOffset;
    }

    /** {@inheritDoc} */
    @Override
    public int write(byte b) throws IOException {
        raw.add(b);
        System.out.print(" [");
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(0x");
        System.out.print(StringUtil.toHexString(b));
        System.out.println(")");
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int write(byte[] data, int offset, int length) throws IOException {
        Objects.checkFromIndexSize(offset, length, data.length);
        for(int p = offset; p-offset < length; p++){
            raw.add(data[p]); // add to internal buffer
        }
        System.out.print(" [");
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(0x");
        System.out.print(StringUtil.toHexString(data, offset, length));
        System.out.println(")");
        return length;
    }

    /** {@inheritDoc} */
    @Override
    public int write(Charset charset, CharSequence data) throws IOException {
        byte[] buffer = data.toString().getBytes(charset);
        for(int p = 0; p < buffer.length; p++){
            raw.add(buffer[p]); // add to internal buffer
        }
        System.out.print(" [");
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: WRITE(\"");
        System.out.print(data);
        System.out.println("\")");
        return data.length();
    }

    /** {@inheritDoc} */
    @Override
    public int read() throws IOException {
        if(raw.isEmpty()) return -1;
        byte b = raw.pop();
        System.out.print(" [");
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ (0x");
        System.out.print(StringUtil.toHexString(b));
        System.out.println(")");
        return b;
    }

    /** {@inheritDoc} */
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
        System.out.print(Mock.SPI_PROVIDER_NAME);
        System.out.print("::");
        System.out.print(this.id);
        System.out.print("] :: READ (0x");
        System.out.print(StringUtil.toHexString(buffer, offset, length));
        System.out.println(")");

        return counter;
    }
}
