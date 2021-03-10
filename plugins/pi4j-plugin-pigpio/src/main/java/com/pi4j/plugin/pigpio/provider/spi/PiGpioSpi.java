package com.pi4j.plugin.pigpio.provider.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioSpi.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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

/**
 * <p>PiGpioSpi class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class PiGpioSpi extends SpiBase implements Spi {

    protected final PiGpio piGpio;
    protected final int handle;

    /**
     * <p>Constructor for PiGpioSpi.</p>
     *
     * @param piGpio a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @param provider a {@link com.pi4j.io.spi.SpiProvider} object.
     * @param config a {@link com.pi4j.io.spi.SpiConfig} object.
     */
    public PiGpioSpi(PiGpio piGpio, SpiProvider provider, SpiConfig config) {
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

    /** {@inheritDoc} */
    @Override
    public Spi initialize(Context context) throws InitializeException {
        super.initialize(context);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        piGpio.spiClose(this.handle);
        super.close();
    }

    // -------------------------------------------------------------------
    // DEVICE TRANSFER FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int transfer(byte[] write, int writeOffset, byte[] read, int readOffset, int numberOfBytes) {
        return piGpio.spiXfer(this.handle, write, writeOffset, read, readOffset, numberOfBytes);
    }

    // -------------------------------------------------------------------
    // DEVICE WRITE FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int write(byte b) {
        return piGpio.spiWriteByte(this.handle, b);
    }

    /** {@inheritDoc} */
    @Override
    public int write(byte[] data, int offset, int length) {
        return piGpio.spiWrite(this.handle, data, offset, length);
    }


    // -------------------------------------------------------------------
    // RAW DEVICE READ FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int read() {
        return piGpio.spiReadByte(this.handle);
    }

    /** {@inheritDoc} */
    @Override
    public int read(byte[] buffer, int offset, int length) {
        return piGpio.spiRead(this.handle, buffer, offset, length);
    }
}
