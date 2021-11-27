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

import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CBase;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.plugin.mock.Mock;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Objects;

/**
 * <p>MockI2C class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MockI2C extends I2CBase implements I2C {

    private static final Logger logger = LoggerFactory.getLogger(MockI2C.class);

    /**
     * ATTENTION:  The storage and management of the byte arrays
     *  are terribly inefficient and not intended for real-world
     *  usage.  These are only intended to unit testing the
     *  Pi4J I2C APIs.
     */
    protected ArrayDeque<Byte>[] registers = new ArrayDeque[256]; // 256 supported registers (0-255)
    protected ArrayDeque<Byte> raw = new ArrayDeque<>();

    /**
     * <p>Constructor for MockI2C.</p>
     *
     * @param provider a {@link com.pi4j.io.i2c.I2CProvider} object.
     * @param config a {@link com.pi4j.io.i2c.I2CConfig} object.
     */
    public MockI2C(I2CProvider provider, I2CConfig config){
        super(provider, config);
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: CREATE(BUS=" + config.bus() + "; DEVICE=" + config.device() + ")");
        logger.info("");
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: CLOSE(BUS=" + config.bus() + "; DEVICE=" + config.device() + ")");
        logger.info("");
        super.close();
    }

    // -------------------------------------------------------------------
    // RAW DEVICE WRITE FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int write(byte b) {
        raw.add(b);
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: WRITE(0x");
        logger.info(StringUtil.toHexString(b));
        logger.info(")");
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int write(byte[] data, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, data.length);
        for(int p = offset; p-offset < length; p++){
            raw.add(data[p]); // add to internal buffer
        }
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: WRITE(0x");
        logger.info(StringUtil.toHexString(data, offset, length));
        logger.info(")");
        return length;
    }

    /** {@inheritDoc} */
    @Override
    public int write(Charset charset, CharSequence data) {
        byte[] buffer = data.toString().getBytes(charset);
        for(int p = 0; p < buffer.length; p++){
            raw.add(buffer[p]); // add to internal buffer
        }
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: WRITE(\"");
        logger.info(data.toString());
        logger.info("\")");
        return data.length();
    }

    // -------------------------------------------------------------------
    // RAW DEVICE READ FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int read() {
        if(raw.isEmpty()) return -1;
        byte b = raw.pop();
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: READ(0x");
        logger.info(StringUtil.toHexString(b));
        logger.info(")");
        return b & 0xff;
    }

    /** {@inheritDoc} */
    @Override
    public int read(byte[] buffer, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, buffer.length);

        if(raw.isEmpty()) return -1;
        int counter = 0;
        for(int p = 0; p < length; p++) {
            if(p+offset > buffer.length) break;
            if(raw.isEmpty()) break;
            buffer[offset + p] = raw.pop();
            counter++;
        }

        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: READ(0x");
        logger.info(StringUtil.toHexString(buffer, offset, length));
        logger.info(")");

        return counter;
    }

    /** {@inheritDoc} */
    @Override
    public String readString(Charset charset, int length) {
        if(raw.isEmpty()) return null;
        byte[] buffer = new byte[length];
        for(int p = 0; p < length; p++) {
            if(raw.isEmpty()) break;
            buffer[p] = raw.pop();
        }
        String result = new String(buffer, charset);

        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: READ(\"");
        logger.info(result);
        logger.info("\")");

        return result;
    }

    // -------------------------------------------------------------------
    // DEVICE REGISTER WRITE FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int writeRegister(int register, byte b) {

        if(registers[register] == null) registers[register] = new ArrayDeque<Byte>();
        registers[register].add(b);

        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: WRITE(");
        logger.info("REG=");
        logger.info(String.valueOf(register));
        logger.info(", 0x");
        logger.info(StringUtil.toHexString(b));
        logger.info(")");
        return 0;
    }

    /** {@inheritDoc} */
    @Override
    public int writeRegister(int register, byte[] data, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, data.length);

        // add to internal buffer
        if(registers[register] == null) registers[register] = new ArrayDeque<Byte>();
        for(int p = offset; p-offset < length; p++){
            registers[register].add(data[p]);
        }

        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: WRITE(");
        logger.info("REG=");
        logger.info(String.valueOf(register));
        logger.info(", 0x");
        logger.info(StringUtil.toHexString(data, offset, length));
        logger.info(")");

        return length;
    }

    /** {@inheritDoc} */
    @Override
    public int writeRegister(int register, Charset charset, CharSequence data) {

        if(registers[register] == null) registers[register] = new ArrayDeque<Byte>();
        byte[] buffer = data.toString().getBytes(charset);
        for(int p = 0; p < buffer.length; p++){
            registers[register].add(buffer[p]); // add to internal buffer
        }

        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: WRITE(");
        logger.info("REG=");
        logger.info(String.valueOf(register));
        logger.info(", \"");
        logger.info(String.valueOf(data));
        logger.info("\")");
        return data.length();
    }

    // -------------------------------------------------------------------
    // DEVICE REGISTER READ FUNCTIONS
    // -------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public int readRegister(int register) {
        if(registers[register] == null) throw new IllegalStateException("No available data to read");
        if(registers[register].isEmpty()) throw new IllegalStateException("No available data to read");
        byte b = registers[register].pop();
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: READ(");
        logger.info("REG=");
        logger.info(String.valueOf(register));
        logger.info(", 0x");
        logger.info(StringUtil.toHexString(b));
        logger.info(")");
        return b;
    }

    /** {@inheritDoc} */
    @Override
    public int readRegister(int register, byte[] buffer, int offset, int length) {
        if(registers[register] == null) return -1;
        if(registers[register].isEmpty()) return -1;

        int counter = 0;
        for(int p = 0; p < length; p++) {
            if(p+offset > buffer.length) break;
            if(registers[register].isEmpty()) break;
            buffer[offset + p] = registers[register].pop();
            counter++;
        }
        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: READ(");
        logger.info("REG=");
        logger.info(String.valueOf(register));
        logger.info(", 0x");
        logger.info(StringUtil.toHexString(buffer, offset, length));
        logger.info(")");
        return counter;
    }

    /** {@inheritDoc} */
    @Override
    public String readRegisterString(int register, Charset charset, int length) {
        if(registers[register] == null) return null;
        if(registers[register].isEmpty()) return null;

        byte[] buffer = new byte[length];
        for(int p = 0; p < length; p++) {
            if(registers[register].isEmpty()) break;
            buffer[p] = registers[register].pop();
        }
        String result = new String(buffer, charset);

        logger.info(" [");
        logger.info(Mock.I2C_PROVIDER_NAME);
        logger.info("::");
        logger.info(this.id);
        logger.info("] :: READ(");
        logger.info("REG=");
        logger.info(String.valueOf(register));
        logger.info(", \"");
        logger.info(result);
        logger.info("\")");

        return result;
    }
}
