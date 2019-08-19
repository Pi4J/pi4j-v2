package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutput.java
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

import com.pi4j.io.Output;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface DigitalOutput extends Digital<DigitalOutput, DigitalOutputConfig, DigitalOutputProvider>, Output {

    static DigitalOutputConfigBuilder newConfigBuilder(){
        return DigitalOutputConfigBuilder.newInstance();
    }

    DigitalOutput state(DigitalState state);
    DigitalOutput pulse(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback);
    Future<?> pulseAsync(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback);
    DigitalOutput blink(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback);
    Future<?> blinkAsync(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback);

    default DigitalOutput setState(boolean state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput setState(byte state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput setState(short state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput setState(int state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput setState(long state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput setState(float state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput setState(double state){
        return this.state(DigitalState.getState(state));
    }
    default DigitalOutput high(){
        return this.state(DigitalState.HIGH);
    }
    default DigitalOutput low(){
        return this.state(DigitalState.LOW);
    }
    default DigitalOutput toggle(){
        return this.state(DigitalState.getInverseState(this.state()));
    }


    default DigitalOutput pulseHigh(int interval, TimeUnit unit){
        return pulse(interval, unit, DigitalState.HIGH);
    }
    default DigitalOutput pulseLow(int interval, TimeUnit unit){
        return pulse(interval, unit, DigitalState.LOW);
    }

    default Future<?> pulseHighAsync(int interval, TimeUnit unit, Callable<Void> callback){
        return pulseAsync(interval, unit, DigitalState.HIGH, callback);
    }

    default Future<?> pulseLowAsync(int interval, TimeUnit unit, Callable<Void> callback){
        return pulseAsync(interval, unit, DigitalState.LOW, callback);
    }

    default DigitalOutput pulse(int interval, TimeUnit unit){
        return pulse(interval, unit, DigitalState.HIGH);
    }
    default DigitalOutput pulse(int interval, TimeUnit unit, DigitalState state){
        return pulse(interval, unit, state, null);
    }

    default Future<?> pulseAsync(int interval, TimeUnit unit){
        return pulseAsync(interval, unit, DigitalState.HIGH);
    }
    default Future<?> pulseAsync(int interval, TimeUnit unit, DigitalState state){
        return pulseAsync(interval, unit, DigitalState.HIGH, null);
    }

    default DigitalOutput blink(int interval, TimeUnit unit){
        return this.blink(interval, interval, unit);
    }
    default DigitalOutput blink(int delay, int duration, TimeUnit unit){
        return this.blink(delay, duration, unit, DigitalState.HIGH);
    }
    default DigitalOutput blink(int delay, int duration, TimeUnit unit, DigitalState state){
        return this.blink(delay, duration, unit, state, null);
    }

    default Future<?> blinkAsync(int interval, TimeUnit unit){
        return this.blinkAsync(interval, interval, unit, DigitalState.HIGH);
    }
    default Future<?> blinkAsync(int delay, int duration, TimeUnit unit){
        return this.blinkAsync(delay, duration, unit, DigitalState.HIGH);
    }
    default Future<?> blinkAsync(int delay, int duration, TimeUnit unit, DigitalState state){
        return this.blinkAsync(delay, duration, unit, state, null);
    }
}
