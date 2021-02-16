package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalMultipurposeBase.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.OnOff;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.exception.IOModeException;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <p>Abstract DigitalMultipurposeBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class DigitalMultipurposeBase extends DigitalBase<DigitalMultipurpose, DigitalMultipurposeConfig,
    DigitalMultipurposeProvider> implements DigitalMultipurpose, OnOff<DigitalMultipurpose> {

    protected DigitalState state = DigitalState.UNKNOWN;
    protected DigitalMode mode = DigitalMode.INPUT;

    /**
     * <p>Constructor for DigitalMultipurposeBase.</p>
     *
     * @param provider a {@link DigitalMultipurposeProvider} object.
     * @param config a {@link DigitalMultipurposeConfig} object.
     */
    public DigitalMultipurposeBase(DigitalMultipurposeProvider provider, DigitalMultipurposeConfig config){
        super(provider, config);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMode mode(){
        return this.mode;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose mode(DigitalMode mode) throws com.pi4j.io.exception.IOException {
        this.mode = mode;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose initialize(Context context) throws InitializeException {
        super.initialize(context);

        // set initial mode
        if(config().initialMode() != null){
            try {
                mode(config().initialMode());
            } catch (Exception e) {
                throw new InitializeException(e);
            }
        }

        // update the digital state value to the initial value if an initial value was configured
        if(config().initialState() != null){
            try {
                DigitalMode existingMode = mode();
                if(!existingMode.isOutput()) mode(DigitalMode.OUTPUT);
                state(config().initialState());
                if(!existingMode.isOutput()) mode(existingMode);
            } catch (IOException e) {
                throw new InitializeException(e);
            }
        }

        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose state(DigitalState state) throws IOException {
        if(!this.isOutput()){
            throw new IOModeException("Unable to set state [" + state.getName() +
                "] for I/O instance [" + toString() + "]; Invalid Mode: " + mode.getName());
        }
        if(!this.equals(state)){
            this.state = state;
            this.dispatch(new DigitalStateChangeEvent<DigitalMultipurposeBase>(this, this.state));
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose pulse(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback) throws IOException {
        int millis = 0;

        // validate arguments
        if(interval <= 0) throw new IllegalArgumentException("A time interval of zero or less is not supported.");
        if(unit == TimeUnit.MICROSECONDS) throw new IllegalArgumentException("TimeUnit.MICROSECONDS is not supported.");
        else if(unit == TimeUnit.DAYS) throw new IllegalArgumentException("TimeUnit.DAYS is not supported.");
        else if(unit == TimeUnit.MILLISECONDS) millis = interval;
        else if(unit == TimeUnit.SECONDS) millis = interval * 1000;
        else if(unit == TimeUnit.MINUTES) millis = interval * 60000;
        else if(unit == TimeUnit.HOURS) millis = interval * 360000;
        else throw new IllegalArgumentException("TimeUnit provided is not supported.");

        // start the pulse state
        this.state(state);

        // block the current thread for the pulse duration
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException("Pulse blocking thread interrupted.", e);
        }

        // end the pulse state
        this.state(DigitalState.getInverseState(state));

        // invoke callback if one was defined
        if (callback != null) {
            try {
                callback.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Future<?> pulseAsync(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback) {
        // TODO :: IMPLEMENT DIGITAL OUTPUT PULSE ASYNC
        throw new UnsupportedOperationException("PULSE ASYNC has not yet been implemented!");
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose blink(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback) {
        // TODO :: IMPLEMENT DIGITAL OUTPUT BLINK
        throw new UnsupportedOperationException("BLINK has not yet been implemented!");
    }

    /** {@inheritDoc} */
    @Override
    public Future<?> blinkAsync(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback) {
        // TODO :: IMPLEMENT DIGITAL OUTPUT BLINK ASYNC
        throw new UnsupportedOperationException("BLINK ASYNC has not yet been implemented!");
    }

    /** {@inheritDoc} */
    @Override
    public DigitalState state() {
        return this.state;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose shutdown(Context context) throws ShutdownException {
        // set pin state to shutdown state if a shutdown state is configured
        if(config().shutdownState() != null && config().shutdownState() != DigitalState.UNKNOWN){
            try {
                state(config().shutdownState());
            } catch (IOException e) {
                throw new ShutdownException(e);
            }
        }
        return super.shutdown(context);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose on() throws IOException {
        // TODO :: REVISIT STATE VS ON/OFF
        return high();
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurpose off() throws IOException {
        // TODO :: REVISIT STATE VS ON/OFF
        return low();
    }
}
