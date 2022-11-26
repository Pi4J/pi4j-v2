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
 * published by the Free Sofcd ta   re Foundation, either version 3 of the
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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Objects;

import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CBase;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

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


    /**
     * {@inheritDoc}
     */
    @Override
    public int writeRegister(byte[] register, byte[] data, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, data.length);
        byte[] tmp = new byte[length + register.length];
        for(int i = 0; i < register.length; i++){
            tmp[i] = register[i];
        }
        System.arraycopy(data, 0, tmp, register.length, length);
        int rc = write(tmp);
        return (rc - register.length);  // do not include the register bytes as what was written...
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


    // this is actually in LinuxFile but not visible,   how to properly handle this ???
    protected static int getWordSize() {
        final String archDataModel = System.getProperty("sun.arch.data.model");
        return "64".equals(archDataModel) ? 8 : 4;
    }


     /**
     * {@inheritDoc}
      *
      *
      * struct i2c_msg {
      *         __u16 addr;
      *         __u16 flags;
      *         __u16 len;
      *         __u8 *buf;    wordSize
      *         u8 buffer
      * };
      */
    @Override
    public int readRegister(byte[] register, byte[] buffer, int offset, int length) {
        Objects.checkFromIndexSize(offset, length, buffer.length);
        // command I2C_RDWR
        long command = I2CConstants.I2C_RDWR;
        // create byte buffer containing the i2c messages
        // address, number of byte
        // First message to write device register
        short deviceAddr = (short) (this.config.device()& 0xff);
        short writeFlags = (short) (I2CConstants.I2C_SMBUS_WRITE & 0xff);
        short writeLength = (short) register.length;  // underlying code enforces max length 32, so it will fit in a short

        IntBuffer offsets = IntBuffer.allocate(4); // two pointers so 2 pairs of entries
         // create ByteBuffer, load with write details
        ByteBuffer ioctlData = ByteBuffer.allocate(500);  // more than enough
        ioctlData.order(ByteOrder.nativeOrder()); // Ensures Pi BCM little_endian .
        ioctlData.putShort(deviceAddr);
        ioctlData.putShort(writeFlags);
        ioctlData.putShort(writeLength);
        // Before creating the pointer entry space, see if we are properly aligned
        int wrtAlignValue = ioctlData.position()%getWordSize();
        if(ioctlData.position() < this.getWordSize()) {  // position currently less than word size
            ioctlData.position( (this.getWordSize() - ioctlData.position()) +  ioctlData.position());   // so move position to word
        }else if(wrtAlignValue != 0){    //  If remainder exists, add value to position
            ioctlData.position(ioctlData.position()+ (this.getWordSize() - wrtAlignValue) );
        }
        offsets.put(ioctlData.position());   // first pair of info, position 0
        byte[] wrtPtrData = new byte[getWordSize()];
        ioctlData.put(wrtPtrData);



        // second message to read back from device
        short readFlags = (short) (I2CConstants.I2C_M_RD & 0xff);
        short readLength = (short) length;
        byte[] readBuff =  new byte[length];
        // create ByteBuffer, load with read details
        //ByteBuffer readData = ByteBuffer.allocate(8*2);

        ioctlData.putShort(deviceAddr);
        ioctlData.putShort(readFlags);
        ioctlData.putShort(readLength);
        // Before creating the pointer entry space, see if we are properly aligned
        int rdAlignValue = ioctlData.position()%getWordSize();
        // Before creating the pointer entry space, see if we are properly aligned
        if(ioctlData.position() < this.getWordSize()) {  // position currently less than word size
            ioctlData.position( (this.getWordSize() - ioctlData.position()) +  ioctlData.position());   // so move position to word
        }else if(rdAlignValue != 0){    //  If remainder exists, add value to position
            ioctlData.position(ioctlData.position()+(this.getWordSize() - rdAlignValue));
        }

        byte[] rdPtrData = new byte[getWordSize()];
        offsets.put(2,ioctlData.position()); // second pair of info, position 2
        ioctlData.put(rdPtrData);



        int regBuffPosition = ioctlData.position();
        offsets.put(1, regBuffPosition);      // part of first pair, position 1
        ioctlData.put(register);
        int readBuffPosition = ioctlData.position();
        offsets.put(3,readBuffPosition);    // last section of second pair, position 3
        ioctlData.put(readBuff);

        offsets.position(4);
        ioctlData.flip();   // set to start of your data
        offsets.flip();

       this.i2CBus.executeIOCTL(this, command, ioctlData, offsets);

        // move results back into user buffer
        for(int i = 0; i <length; i++){   // can I assume length is safe and the readBuff data is not shorter ??   I think yes
            buffer[i] = ioctlData.get(readBuffPosition +i );
;        }

        return (ioctlData.limit());
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
