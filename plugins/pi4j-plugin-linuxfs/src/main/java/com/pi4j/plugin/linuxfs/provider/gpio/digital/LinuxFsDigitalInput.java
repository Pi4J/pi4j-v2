package com.pi4j.plugin.linuxfs.provider.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  LinuxFsDigitalInput.java
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
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.plugin.linuxfs.provider.gpio.LinuxGpio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>LinuxFsDigitalInput class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class LinuxFsDigitalInput extends DigitalInputBase implements DigitalInput {

    protected final LinuxGpio gpio;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>Constructor for LinuxFsDigitalInput.</p>
     *
     * @param gpio a {@link com.pi4j.plugin.linuxfs.provider.gpio.LinuxGpio} linux file system GPIO object.
     * @param provider a {@link com.pi4j.io.gpio.digital.DigitalInputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalInputConfig} object.
     */
    public LinuxFsDigitalInput(LinuxGpio gpio, DigitalInputProvider provider, DigitalInputConfig config){
        super(provider, config);
        this.gpio = gpio;
    }

    @Override
    public DigitalInput initialize(Context context) throws InitializeException {
        logger.trace("initializing GPIO [" + this.config.address() + "]; " + gpio.getPinPath());

        // [EXPORT] requested GPIO pin if its not already exported
        try {
            if(!gpio.isExported()) {
                logger.trace("exporting GPIO [" + this.config.address() + "]; " + gpio.getPinPath());
                gpio.export();
            } else{
                logger.trace("GPIO [" + this.config.address() + "] is already exported; " + gpio.getPinPath());
            }
        } catch (java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new InitializeException("Unable to export GPIO [" + config.address() + "] @ <" + gpio.systemPath() + ">; " + e.getMessage(), e);
        }

        // [INPUT] configure GPIO pin direction as digital input
        try {
            logger.trace("set direction [IN] on GPIO " + gpio.getPinPath());
            gpio.direction(LinuxGpio.Direction.IN);
        } catch (java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new InitializeException("Unable to set GPIO [" + config.address() + "] DIRECTION=[IN] @ <" + gpio.pinPath() + ">; " + e.getMessage(), e);
        }

        // GPIO PIN PULL UP/DOWN
        if(this.config.pull() == PullResistance.PULL_DOWN){
            // TODO :: IMPLEMENT GPIO PULL DOWN
        }
        else if(this.config.pull() == PullResistance.PULL_UP){
            // TODO :: IMPLEMENT GPIO PULL UP
        }
        else if(this.config.pull() == PullResistance.OFF){
            // TODO :: IMPLEMENT GPIO PULL OFF
        }

        // [INITIALIZE] perform any further initialization on GPIO  via superclass impl
        return super.initialize(context);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalInput shutdown(Context context) throws ShutdownException {
        logger.trace("shutdown GPIO [" + this.config.address() + "]; " + gpio.getPinPath());

        // perform any shutdown cleanup via superclass
        super.shutdown(context);

        // un-export the GPIO pin from the Linux file system impl
        try {
            logger.trace("un-exporting GPIO [" + this.config.address() + "]; " + gpio.getPinPath());
            gpio.unexport();
        } catch (java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new ShutdownException("Failed to UN-EXPORT GPIO [" + config().address() + "] @ <" + gpio.systemPath() + ">; " + e.getMessage(), e);
        }

        // return this digital input instance
        return this;
    }

    @Override
    public DigitalState state() {
        logger.trace("get state on GPIO [" + this.config.address() + "]; " + gpio.getPinPath());
        try {
            // acquire actual GPIO state directly from Linux file system impl
            return gpio.state();
        } catch (java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new IOException(e.getMessage(), e);
        }
    }
}
