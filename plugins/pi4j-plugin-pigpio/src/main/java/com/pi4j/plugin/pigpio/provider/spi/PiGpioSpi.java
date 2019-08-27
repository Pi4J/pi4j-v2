package com.pi4j.plugin.pigpio.provider.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioSerial.java
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


import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiBase;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.library.pigpio.PiGpio;

import java.io.IOException;

public class PiGpioSpi extends SpiBase implements Spi {

    protected final PiGpio piGpio;
    protected final int handle;

    public PiGpioSpi(PiGpio piGpio, SpiProvider provider, SpiConfig config) throws IOException {
        super(provider, config);

        // set local reference instance
        this.piGpio = piGpio;

        // create SPI instance of PIGPIO SPI
        this.handle = piGpio.spiOpen(
                config.address(),
                config.baud(),
                config.mode().getMode());

        // set open state flag
        this.isOpen = true;
    }

    @Override
    public Spi initialize(Context context) throws InitializeException {
        super.initialize(context);
        return this;
    }

    @Override
    public void close() throws IOException {
        piGpio.spiClose(this.handle);
        super.close();
    }

    // -------------------------------------------------------------------
    // DEVICE TRANSFER FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int transfer(byte[] write, int writeOffset, byte[] read, int readOffset, int numberOfBytes) throws IOException {
        return piGpio.spiXfer(this.handle, write, writeOffset, read, readOffset, numberOfBytes);
    }

    // -------------------------------------------------------------------
    // DEVICE WRITE FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int write(byte b) throws IOException {
        return piGpio.spiWriteByte(this.handle, b);
    }

    @Override
    public int write(byte[] data, int offset, int length) throws IOException {
        return piGpio.spiWrite(this.handle, data, offset, length);
    }


    // -------------------------------------------------------------------
    // RAW DEVICE READ FUNCTIONS
    // -------------------------------------------------------------------

    @Override
    public int read() throws IOException {
        return piGpio.spiReadByte(this.handle);
    }

    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {
        return piGpio.spiRead(this.handle, buffer, offset, length);
    }
}
