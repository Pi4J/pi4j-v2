package com.pi4j.plugin.gpiod.provider.gpio.digital;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioDigitalOutputProviderImpl.java
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
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.gpio.digital.DigitalOutputProviderBase;
import com.pi4j.library.gpiod.internal.GpioChip;
import com.pi4j.library.gpiod.internal.GpioChipIterator;
import com.pi4j.library.gpiod.internal.GpioLine;

import java.util.Map;

/**
 * <p>PiGpioDigitalOutputProviderImpl class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class GpioDDigitalOutputProviderImpl extends DigitalOutputProviderBase implements GpioDDigitalOutputProvider {
    private GpioChip gpioChip;


    /**
     * <p>Constructor for PiGpioDigitalOutputProviderImpl.</p>
     *
     */
    public GpioDDigitalOutputProviderImpl(){
        this.id = ID;
        this.name = NAME;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalOutput create(DigitalOutputConfig config) {
        // create new I/O instance based on I/O config
        GpioLine line = this.gpioChip.getLine(config.address());
        return new GpioDDigitalOutput(line, this, config);
    }

    @Override
    public DigitalOutputProvider initialize(Context context) throws InitializeException {
        DigitalOutputProvider provider = super.initialize(context);
        GpioChipIterator iterator = new GpioChipIterator();
        GpioChip found = null;
        while (iterator.hasNext()) {
            GpioChip current = iterator.next();
            if(current.getName().contains("pinctrl")) {
                found = current;
                iterator.noCloseCurrent();
                break;
            }
        }
        if(found == null) {
            throw new IllegalStateException("Couldn't identify gpiochip!");
        }
        this.gpioChip = found;
        return provider;
    }

    @Override
    public GpioDDigitalOutputProviderImpl shutdown(Context context) throws ShutdownException {
        if(gpioChip != null) {
            this.gpioChip.close();
        }
        super.shutdown(context);
    }

}
