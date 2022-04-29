package com.pi4j.plugin.linuxfs.provider.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  LinuxFsDigitalOutput.java
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
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.plugin.linuxfs.provider.gpio.LinuxGpio;


/**
 * <p>LinuxFsDigitalOutput class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class LinuxFsDigitalOutput extends DigitalOutputBase implements DigitalOutput {
    protected final LinuxGpio gpio;

    /**
     * <p>Constructor for LinuxFsDigitalOutput.</p>
     *
     * @param provider a {@link com.pi4j.io.gpio.digital.DigitalOutputProvider} object.
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalOutputConfig} object.
     */
    public LinuxFsDigitalOutput(LinuxGpio gpio, DigitalOutputProvider provider, DigitalOutputConfig config){
        super(provider, config);
        this.gpio = gpio;
    }

    @Override
    public DigitalOutput initialize(Context context) throws InitializeException {
        try {
            // export GPIO pin
            gpio.export();

            // set GPIO pin direction [OUTPUT]
            gpio.direction(LinuxGpio.Direction.OUT);

        } catch (java.io.IOException e) {
            throw new InitializeException(e);
        }

        // initialize GPIO pin state (in superclass)
        super.initialize(context);

        // return this instance
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutput state(DigitalState state) throws IOException {
        try {
            // apple updated GPIO state via Linux FS
            gpio.state(state);
        } catch (java.io.IOException e) {
            throw new IOException(e.getMessage(), e);
        }
        return super.state(state);
    }

    @Override
    public DigitalState state() {

        // update internal state tracking variable with actual state from Linux FS
        try {
            this.state = gpio.state();
        } catch (java.io.IOException e) {
            throw new IOException(e.getMessage(), e);
        }

        // return current GPIO state
        return super.state();
    }
}
