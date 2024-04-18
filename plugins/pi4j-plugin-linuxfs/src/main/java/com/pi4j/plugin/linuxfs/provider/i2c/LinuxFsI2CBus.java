package com.pi4j.plugin.linuxfs.provider.i2c;

import com.pi4j.common.CheckedFunction;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CBusBase;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.library.linuxfs.LinuxFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.Callable;

public class LinuxFsI2CBus extends I2CBusBase {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * File handle for this i2c bus
     */
    protected LinuxFile file;
    private int lastAddress;

    public LinuxFsI2CBus(I2CConfig config) {
        super(config);

        final File sysfs = new File("/sys/bus/i2c/devices/i2c-" + this.bus);
        if (!sysfs.exists() || !sysfs.isDirectory())
            throw new Pi4JException("I2C bus " + this.bus + " does not exist.");

        final File devfs = new File("/dev/i2c-" + this.bus);
        if (!devfs.exists() || !devfs.canRead() || !devfs.canWrite())
            throw new Pi4JException("I2C bus " + this.bus + " does not exist.");

        try {
            String fileName = devfs.getCanonicalPath();
            this.file = new LinuxFile(fileName, "rw");
        } catch (IOException e) {
            throw new Pi4JException(e);
        }
    }

    @Override
    public <R> R execute(I2C i2c, Callable<R> action) {
        return _execute(i2c, () -> {
            try {
                selectBusSlave(i2c);
                return action.call();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new Pi4JException("Failed to execute action for device " + i2c.device() + " on bus " + this.bus,
                    e);
            }
        });
    }

    public <R> R execute(final I2C i2c, final CheckedFunction<LinuxFile, R> action) {
        return _execute(i2c, () -> {
            try {
                selectBusSlave(i2c);
                return action.apply(this.file);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new Pi4JException("Failed to execute action for device " + i2c.device() + " on bus " + this.bus,
                    e);
            }
        });
    }

    /**
     * @param i2c     the device to select before performing the ioctl command
     * @param command From I2CConstants
     * @param data    values in bytes for all structures, with 4 or 8 byte alignment enforced by filling holes before
     *                pointers
     * @param offsets ByteBuffer: offsets of pointer/ byte offset of pointedToData
     */
    public void executeIOCTL(final I2C i2c, long command, ByteBuffer data, IntBuffer offsets) {
        _execute(i2c, () -> {
            try {
                selectBusSlave(i2c);
                this.file.ioctl(command, data, offsets);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new Pi4JException("Failed to execute ioctl for device " + i2c.device() + " on bus " + this.bus,
                    e);
            }
            return null;
        });
    }

    /**
     * Selects the slave device if not already selected on this bus. Runs the required ioctl's via JNI.
     *
     * @param i2c Device to select
     */
    protected void selectBusSlave(I2C i2c) throws IOException {
        if (this.lastAddress == i2c.device())
            return;

        this.lastAddress = i2c.device();
        this.file.ioctl(I2CConstants.I2C_SLAVE, i2c.device() & 0xFF);
    }

    public void close() {
        if (this.file != null) {
            try {
                this.file.close();
            } catch (IOException e) {
                logger.error("Failed to close file {} for {}-{}", this.file, getClass().getSimpleName(), this.bus, e);
            }
        }
    }
}
