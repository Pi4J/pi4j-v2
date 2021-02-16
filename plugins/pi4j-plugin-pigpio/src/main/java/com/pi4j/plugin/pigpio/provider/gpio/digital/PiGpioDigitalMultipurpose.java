package com.pi4j.plugin.pigpio.provider.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioDigitalMultipurpose.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
 * %%
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
import com.pi4j.io.exception.IOModeException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.library.pigpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>PiGpioDigitalMultipurpose class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class PiGpioDigitalMultipurpose extends DigitalMultipurposeBase implements DigitalMultipurpose {
    private final PiGpio piGpio;
    private final int pin;
    private DigitalState state = DigitalState.LOW;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Default Constructor
     *
     * @param piGpio a {@link PiGpio} object.
     * @param provider a {@link DigitalInputProvider} object.
     * @param config a {@link DigitalInputConfig} object.
     * @throws IOException if any.
     */
    public PiGpioDigitalMultipurpose(PiGpio piGpio, DigitalMultipurposeProvider provider, DigitalMultipurposeConfig config) throws IOException {
        super(provider, config);
        this.piGpio = piGpio;
        this.pin = config.address().intValue();
    }

    /**
     * PIGPIO Pin Change Event Handler
     *
     * This listener implementation will forward pin change events received from PIGPIO
     * to registered Pi4J 'DigitalChangeEvent' event listeners on this digital pin.
     */
    private PiGpioStateChangeListener piGpioPinListener =
            event -> dispatch(new DigitalStateChangeEvent(PiGpioDigitalMultipurpose.this, DigitalState.getState(event.state().value())));

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose initialize(Context context) throws InitializeException {
        try {
            // configure GPIO pin as an INPUT|OUTPUT pin
            PiGpioMode pgpiomode = (mode.isOutput()) ? PiGpioMode.OUTPUT : PiGpioMode.INPUT;
            this.piGpio.gpioSetMode(pin, pgpiomode);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InitializeException(e);
        }

        super.initialize(context);

        try {
            // if configured, set GPIO pin pull resistance
            switch(config.pull()){
                case PULL_DOWN:{
                    this.piGpio.gpioSetPullUpDown(pin, PiGpioPud.DOWN);
                    break;
                }
                case PULL_UP:{
                    this.piGpio.gpioSetPullUpDown(pin, PiGpioPud.UP);
                    break;
                }
            }

            // if configured, set GPIO debounce
            if(this.config.debounce() != null) {
                int steadyInterval = 0;
                if(this.config.debounce() > 300000){
                    steadyInterval = 300000;
                } else{
                    steadyInterval = this.config.debounce().intValue();
                }
                this.piGpio.gpioNoiseFilter(pin, 0, 0);
                this.piGpio.gpioGlitchFilter(pin, steadyInterval);
            }

            // add this pin listener
            this.piGpio.addPinListener(pin, piGpioPinListener);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InitializeException(e);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose mode(DigitalMode mode) throws com.pi4j.io.exception.IOException {
        try {
            // configure GPIO pin as an INPUT|OUTPUT pin
            PiGpioMode pgpiomode = (mode.isOutput()) ? PiGpioMode.OUTPUT : PiGpioMode.INPUT;
            this.piGpio.gpioSetMode(pin, pgpiomode);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IOModeException(e.getMessage());
        }
        return super.mode(mode);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose state(DigitalState state) throws com.pi4j.io.exception.IOException {
        // ensure the pin is in the OUPUT  mode before attempting to set state
        if(!this.isOutput()){
            throw new IOModeException("Unable to set state [" + state.getName() +
                "] for I/O instance [" + toString() + "]; Invalid Mode: " + mode.getName());
        }
        try {
            this.piGpio.gpioWrite(pin, PiGpioState.from(state.value()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new com.pi4j.io.exception.IOException(e.getMessage(), e);
        }
        return super.state(state);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalState state() {
        try {
            switch (this.piGpio.gpioRead(pin)) {
                case LOW: {
                    this.state = DigitalState.LOW;
                    break;
                }
                case HIGH: {
                    this.state = DigitalState.HIGH;
                    break;
                }
                default: {
                    this.state = DigitalState.UNKNOWN;
                    break;
                }
            }
            return this.state;
        }
        catch (Exception e){
            logger.error(e.getMessage(), e);
            return DigitalState.UNKNOWN;
        }
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose shutdown(Context context) throws ShutdownException {
        // remove this pin listener
        this.piGpio.removePinListener(pin, piGpioPinListener);
        return super.shutdown(context);
    }
}
