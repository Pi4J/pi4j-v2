package com.pi4j.plugin.pigpio.provider.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioPwmBase.java
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
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmBase;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.library.pigpio.PiGpio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Abstract PiGpioPwmBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class PiGpioPwmBase extends PwmBase implements Pwm {

    protected final PiGpio piGpio;
    protected final int range;
    protected int actualFrequency = -1;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>Constructor for PiGpioPwmBase.</p>
     *
     * @param piGpio a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @param provider a {@link com.pi4j.io.pwm.PwmProvider} object.
     * @param config a {@link com.pi4j.io.pwm.PwmConfig} object.
     * @param range a int.
     */
    public PiGpioPwmBase(PiGpio piGpio, PwmProvider provider, PwmConfig config, int range){
        super(provider, config);
        this.piGpio = piGpio;
        this.range = range;
    }

    /**
     * <p>calculateActualDutyCycle.</p>
     *
     * @param dutyCycle a float.
     * @return a int.
     */
    protected int calculateActualDutyCycle(float dutyCycle){
        return Math.round((this.range * dutyCycle) / 100);
    }

    /** {@inheritDoc} */
    @Override
    public int getActualFrequency() {
        return this.actualFrequency;
    }

    /** {@inheritDoc} */
    @Override
    public Pwm initialize(Context context) throws InitializeException {
        return super.initialize(context);
    }
}
