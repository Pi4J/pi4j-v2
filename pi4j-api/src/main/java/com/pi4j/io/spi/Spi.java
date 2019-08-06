package com.pi4j.io.spi;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Spi.java
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


import com.pi4j.context.Context;
import com.pi4j.io.IO;
import com.pi4j.io.spi.impl.SpiFactory;
import com.pi4j.provider.exception.ProviderException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface Spi extends IO<Spi, SpiConfig, SpiProvider> {

    static final String ID = "SPI";
    static final SpiMode DEFAULT_SPI_MODE = SpiMode.MODE_0;
    static final int DEFAULT_SPI_SPEED = 1000000; // 1MHz (range is 500kHz - 32MHz)
    static final int MAX_SUPPORTED_BYTES = 2048;

    static Spi instance(Context context, SpiConfig config) throws ProviderException {
        return SpiFactory.instance(context, config);
    }

    static Spi instance(Context context, String device) throws ProviderException {
        return SpiFactory.instance(context, device);
    }

    static Spi instance(Context context, String providerId, String device) throws ProviderException {
        return SpiFactory.instance(context, providerId, device);
    }

    static Spi instance(Context context, String providerId, SpiConfig config) throws ProviderException {
        return SpiFactory.instance(context, providerId, config);
    }

    static Spi instance(SpiProvider provider, String device) throws ProviderException {
        return SpiFactory.instance(provider, device);
    }

    static Spi instance(SpiProvider provider, SpiConfig config) throws ProviderException {
        return SpiFactory.instance(provider, config);
    }

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes (encoded in string) to write to the SPI device
     * @param charset
     *            character encoding for bytes in string
     * @return resulting bytes read from the SPI device after the write operation
     */
    public String write(String data, Charset charset) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes (encoded in string) to write to the SPI device
     * @param charset
     *            character encoding for bytes in string
     * @return resulting bytes read from the SPI device after the write operation
     */
    public String write(String data, String charset) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes to write to the SPI device
     * @return resulting bytes read from the SPI device after the write operation
     */
    public ByteBuffer write(ByteBuffer data) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param input
     *            input stream to read from to get
     *            bytes to write to the SPI device
     * @return resulting bytes read from the SPI device after the write operation
     */
    public byte[] write(InputStream input) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param input
     *            input stream to read from to get
     *            bytes to write to the SPI device
     * @param output
     *            output stream to write bytes
     *            read from the SPI device
     * @return number of resulting bytes read from the SPI device and written to the output stream
     */
    public int write(InputStream input, OutputStream output) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes to write to the SPI device
     * @param start
     *            start index position in the data buffer to start writing from
     * @param length
     *            length of bytes to write from the data buffer
     * @return resulting bytes read from the SPI device after the write operation
     */
    public byte[] write(byte[] data, int start, int length) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes to write to the SPI device
     * @return resulting bytes read from the SPI device after the write operation
     */
    public byte[] write(byte... data) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes to write to the SPI device
     * @param start
     *            start index position in the data buffer to start writing from
     * @param length
     *            length of bytes to write from the data buffer
     * @return resulting bytes read from the SPI device after the write operation
     */
    public short[] write(short[] data, int start, int length) throws IOException;

    /**
     * Attempts to read/write data through this SPI device
     *
     * @param data
     *            bytes to write to the SPI device (Note: short value should not exceed 255.)
     * @return resulting bytes read from the SPI device after the write operation
     */
    public short[] write(short... data) throws IOException;

}
