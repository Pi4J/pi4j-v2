package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputBase.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
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

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class DigitalOutputBase extends DigitalBase<DigitalOutput, DigitalOutputConfig> implements DigitalOutput {

    protected DigitalState state = DigitalState.UNKNOWN;

    public DigitalOutputBase(DigitalOutputProvider provider, DigitalOutputConfig config){
        super(provider, config);
        this.name = (config.name() != null) ? config.name() : "DOUT-" + config.address();

        // update the analog value to the initial value if an initial value was configured
        if(config().initialState() != null){
            state(config().initialState());
        }
    }

    @Override
    public DigitalOutput state(DigitalState state) {

        if(!this.equals(state)){
            this.state = state;
            this.dispatch(new DigitalChangeEvent<DigitalOutputBase>(this, this.state));
        }
        return this;
    }

    @Override
    public DigitalOutput pulse(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback){
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

    @Override
    public Future<?> pulseAsync(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback) {
        return null;
    }

    @Override
    public DigitalOutput blink(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback) {
        return null;
    }

    @Override
    public Future<?> blinkAsync(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback) {
        return null;
    }

    @Override
    public DigitalState state() {
        return this.state;
    }

    @Override
    public DigitalOutput shutdown(Context context){
        // set pin state to shutdown state if a shutdown state is configured
        if(config().shutdownState() != DigitalState.UNKNOWN){
            state(config().shutdownState());
        }
        return this;
    }
}
