package com.pi4j.plugin.linuxfs.provider.i2c;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  PiGpioI2C.java
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

import java.io.RandomAccessFile;
import java.util.Objects;

import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CBase;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

/**
 * <p>PiGpioI2C class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class LinuxFsI2C extends I2CBase implements I2C {

    private final LinuxFsI2CBus i2CBus;

    /**
     * <p>Constructor for PiGpioI2C.</p>
     *
     * @param provider
     *     a {@link I2CProvider} object.
     * @param config
     *     a {@link I2CConfig} object.
     */
    public LinuxFsI2C(LinuxFsI2CBus i2CBus, I2CProvider provider, I2CConfig config) {
        super(provider, config);
        this.i2CBus = i2CBus;
    }

    // -------------------------------------------------------------------
    // RAW DEVICE WRITE FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int write(byte b) {
        return this.i2CBus.execute(this, file -> {
            file.write(b);
            return 1;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int write(byte[] data, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, data.length);
        return this.i2CBus.execute(this, file -> {
            file.write(data, offset, length);
            return length;
        });
    }

    // -------------------------------------------------------------------
    // RAW DEVICE READ FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() {
        return this.i2CBus.execute(this, RandomAccessFile::read);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] buffer, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, buffer.length);
        return this.i2CBus.execute(this, file -> file.read(buffer, offset, length));
    }

    // -------------------------------------------------------------------
    // DEVICE REGISTER WRITE FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int writeRegister(int register, byte b) {
        return write((byte) register, b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int writeRegister(int register, byte[] data, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, data.length);
        byte[] tmp = new byte[length + 1];
        tmp[0] = (byte) register;
        System.arraycopy(data, 0, tmp, 1, length);
        return write(tmp);
    }

    // -------------------------------------------------------------------
    // DEVICE REGISTER READ FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int readRegister(int register) {
        return this.i2CBus.execute(this, file -> {
            file.write(register);
            return file.read();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int readRegister(int register, byte[] buffer, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, buffer.length);
        return this.i2CBus.execute(this, file -> {
            file.write(register);
            return file.read(buffer, offset, length);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int writeReadRegisterWord(int register, int word) {
        return this.i2CBus.execute(this, file -> {
            writeRegisterWord(register, word);
            return readRegisterWord(register);
        });
    }
}
