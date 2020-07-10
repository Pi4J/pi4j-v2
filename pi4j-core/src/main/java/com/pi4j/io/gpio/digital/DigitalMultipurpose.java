package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalMultipurpose.java
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
import com.pi4j.io.InputOutput;
import com.pi4j.io.OnOff;
import com.pi4j.io.exception.IOException;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <p>DigitalInput interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface DigitalMultipurpose extends
    Digital<DigitalMultipurpose, DigitalMultipurposeConfig, DigitalMultipurposeProvider>,
    InputOutput, OnOff<DigitalMultipurpose> {
    /** Constant <code>DEFAULT_DEBOUNCE=10000</code> */
    long DEFAULT_DEBOUNCE = 10000;

    /**
     * <p>newConfigBuilder.</p>
     *
     * @param context {@link Context}
     * @return a {@link DigitalInputConfigBuilder} object.
     */
    static DigitalMultipurposeConfigBuilder newConfigBuilder(Context context){
        return DigitalMultipurposeConfigBuilder.newInstance(context);
    }

    /**
     * <p>pull.</p>
     *
     * @return a {@link PullResistance} object.
     */
    default PullResistance pull() { return config().pull(); }

    /**
     * Get digital mode (input|output)
     * @return digital mode (input|output)
     */
    DigitalMode mode();

    /**
     * Set digital mode (input|output)
     * @param mode DigitalMode (input|output)
     * @return this I/O instance
     */
    DigitalMultipurpose mode(DigitalMode mode) throws IOException;

    /**
     * Set digital mode to INPUT
     * @return this I/O instance
     */
    default DigitalMultipurpose input() throws IOException {
        return this.mode(DigitalMode.INPUT);
    }

    /**
     * Return TRUE if mode is set to INPUT
     * @return 'true' if mode is INPUT; else 'false'
     */
    default boolean isInput(){
        return this.mode().isInput();
    }

    /**
     * Return TRUE if mode is set to OUTPUT
     * @return 'true' if mode is OUTPUT; else 'false'
     */
    default boolean isOutput(){
        return this.mode().isOutput();
    }

    /**
     * Set digital mode to OUTPUT
     * @return this I/O instance
     */
    default DigitalMultipurpose output() throws IOException {
        return this.mode(DigitalMode.OUTPUT);
    }

    /**
     * <p>state.</p>
     *
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    DigitalMultipurpose state(DigitalState state) throws IOException;

    /**
     * <p>pulse.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @param callback a {@link java.util.concurrent.Callable} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    DigitalMultipurpose pulse(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback) throws IOException;
    /**
     * <p>pulseAsync.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @param callback a {@link java.util.concurrent.Callable} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    Future<?> pulseAsync(int interval, TimeUnit unit, DigitalState state, Callable<Void> callback);
    /**
     * <p>blink.</p>
     *
     * @param delay a int.
     * @param duration a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @param callback a {@link java.util.concurrent.Callable} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     */
    DigitalMultipurpose blink(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback);
    /**
     * <p>blinkAsync.</p>
     *
     * @param delay a int.
     * @param duration a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @param callback a {@link java.util.concurrent.Callable} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    Future<?> blinkAsync(int delay, int duration, TimeUnit unit, DigitalState state, Callable<Void> callback);

    /**
     * <p>setState.</p>
     *
     * @param state a boolean.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(boolean state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>setState.</p>
     *
     * @param state a byte.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(byte state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>setState.</p>
     *
     * @param state a short.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(short state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>setState.</p>
     *
     * @param state a int.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(int state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>setState.</p>
     *
     * @param state a long.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(long state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>setState.</p>
     *
     * @param state a float.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(float state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>setState.</p>
     *
     * @param state a double.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose setState(double state) throws IOException {
        return this.state(DigitalState.getState(state));
    }
    /**
     * <p>high.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose high() throws IOException {
        return this.state(DigitalState.HIGH);
    }
    /**
     * <p>low.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose low() throws IOException {
        return this.state(DigitalState.LOW);
    }
    /**
     * <p>toggle.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose toggle() throws IOException {
        return this.state(DigitalState.getInverseState(this.state()));
    }

    /**
     * <p>pulseHigh.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose pulseHigh(int interval, TimeUnit unit) throws IOException {
        return pulse(interval, unit, DigitalState.HIGH);
    }
    /**
     * <p>pulseLow.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose pulseLow(int interval, TimeUnit unit) throws IOException {
        return pulse(interval, unit, DigitalState.LOW);
    }

    /**
     * <p>pulseHighAsync.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param callback a {@link java.util.concurrent.Callable} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> pulseHighAsync(int interval, TimeUnit unit, Callable<Void> callback){
        return pulseAsync(interval, unit, DigitalState.HIGH, callback);
    }

    /**
     * <p>pulseLowAsync.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param callback a {@link java.util.concurrent.Callable} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> pulseLowAsync(int interval, TimeUnit unit, Callable<Void> callback){
        return pulseAsync(interval, unit, DigitalState.LOW, callback);
    }

    /**
     * <p>pulse.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose pulse(int interval, TimeUnit unit) throws IOException {
        return pulse(interval, unit, DigitalState.HIGH);
    }
    /**
     * <p>pulse.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws com.pi4j.io.exception.IOException if any.
     */
    default DigitalMultipurpose pulse(int interval, TimeUnit unit, DigitalState state) throws IOException {
        return pulse(interval, unit, state, null);
    }

    /**
     * <p>pulseAsync.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> pulseAsync(int interval, TimeUnit unit){
        return pulseAsync(interval, unit, DigitalState.HIGH);
    }
    /**
     * <p>pulseAsync.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> pulseAsync(int interval, TimeUnit unit, DigitalState state){
        return pulseAsync(interval, unit, DigitalState.HIGH, null);
    }

    /**
     * <p>blink.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     */
    default DigitalMultipurpose blink(int interval, TimeUnit unit){
        return this.blink(interval, interval, unit);
    }
    /**
     * <p>blink.</p>
     *
     * @param delay a int.
     * @param duration a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     */
    default DigitalMultipurpose blink(int delay, int duration, TimeUnit unit){
        return this.blink(delay, duration, unit, DigitalState.HIGH);
    }
    /**
     * <p>blink.</p>
     *
     * @param delay a int.
     * @param duration a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     */
    default DigitalMultipurpose blink(int delay, int duration, TimeUnit unit, DigitalState state){
        return this.blink(delay, duration, unit, state, null);
    }

    /**
     * <p>blinkAsync.</p>
     *
     * @param interval a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> blinkAsync(int interval, TimeUnit unit){
        return this.blinkAsync(interval, interval, unit, DigitalState.HIGH);
    }
    /**
     * <p>blinkAsync.</p>
     *
     * @param delay a int.
     * @param duration a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> blinkAsync(int delay, int duration, TimeUnit unit){
        return this.blinkAsync(delay, duration, unit, DigitalState.HIGH);
    }
    /**
     * <p>blinkAsync.</p>
     *
     * @param delay a int.
     * @param duration a int.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    default Future<?> blinkAsync(int delay, int duration, TimeUnit unit, DigitalState state){
        return this.blinkAsync(delay, duration, unit, state, null);
    }
}
