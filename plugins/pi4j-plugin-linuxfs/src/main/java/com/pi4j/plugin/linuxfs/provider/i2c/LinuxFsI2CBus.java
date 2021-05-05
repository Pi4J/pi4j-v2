package com.pi4j.plugin.linuxfs.provider.i2c;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.pi4j.exception.Pi4JException;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.library.linuxfs.LinuxFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinuxFsI2CBus {

    public static final long DEFAULT_LOCK_ACQUIRE_TIMEOUT = 1000;
    public static final TimeUnit DEFAULT_LOCK_ACQUIRE_TIMEOUT_UNITS = TimeUnit.MILLISECONDS;
    private final Integer bus;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * File handle for this i2c bus
     */
    protected LinuxFile file;
    private int lastAddress;

    protected long lockAquireTimeout;
    protected TimeUnit lockAquireTimeoutUnit;
    private final ReentrantLock lock = new ReentrantLock(true);

    public LinuxFsI2CBus(I2CConfig config) {

        this.bus = config.getBus();
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

        this.lockAquireTimeout = DEFAULT_LOCK_ACQUIRE_TIMEOUT;
        this.lockAquireTimeoutUnit = DEFAULT_LOCK_ACQUIRE_TIMEOUT_UNITS;
    }

    /**
     * Selects the slave device if not already selected on this bus. Runs the required ioctl's via JNI.
     *
     * @param i2c
     *     Device to select
     */
    protected void selectBusSlave(I2C i2c) throws IOException {
        if (this.lastAddress == i2c.device())
            return;

        this.lastAddress = i2c.device();
        this.file.ioctl(I2CConstants.I2C_SLAVE, i2c.device() & 0xFF);
    }

    public <R> R execute(final I2C i2c, final CheckedFunction<LinuxFile, R> action) {
        if (i2c == null)
            throw new NullPointerException("Parameter 'i2c' is mandatory!");
        if (action == null)
            throw new NullPointerException("Parameter 'action' is mandatory!");

        try {
            if (this.lock.tryLock() || this.lock.tryLock(this.lockAquireTimeout, this.lockAquireTimeoutUnit)) {

                try {
                    selectBusSlave(i2c);
                    return action.apply(this.file);
                } finally {
                    while (this.lock.isHeldByCurrentThread())
                        this.lock.unlock();
                }

            } else {
                throw new Pi4JException(
                    "Failed to get I2C lock on bus " + this.bus + " after " + this.lockAquireTimeout + " "
                        + this.lockAquireTimeoutUnit);
            }
        } catch (InterruptedException e) {
            logger.error("Failed locking " + getClass().getSimpleName() + "-" + this.bus, e);
            throw new RuntimeException("Could not obtain an access-lock!", e);
        } catch (Exception e) {
            throw new Pi4JException("Failed to execute action for device " + i2c.device() + " on bus " + this.bus, e);
        }
    }
}
