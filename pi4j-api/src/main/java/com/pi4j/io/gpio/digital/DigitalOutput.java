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
import com.pi4j.io.gpio.digital.impl.DigitalOutputFactory;
import com.pi4j.provider.exception.ProviderException;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface DigitalOutput extends Digital<DigitalOutput, DigitalOutputConfig>, Output {

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

    static DigitalOutput instance(int address) throws ProviderException {
        return DigitalOutputFactory.instance(address);
    }
    static <T extends DigitalOutput> T instance(int address, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(address, clazz);
    }

    static DigitalOutput instance(int address, DigitalState shutdownState) throws ProviderException {
        return DigitalOutputFactory.instance(address, shutdownState);
    }
    static <T extends DigitalOutput> T instance(int address, DigitalState shutdownState, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(address, shutdownState, clazz);
    }

    static DigitalOutput instance(DigitalOutputConfig config) throws ProviderException {
        return DigitalOutputFactory.instance(config);
    }
    static <T extends DigitalOutput> T instance(DigitalOutputConfig config, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(config, clazz);
    }

    static DigitalOutput instance(String providerId, int address) throws ProviderException {
        return DigitalOutputFactory.instance(providerId, address);
    }
    static <T extends DigitalOutput> T instance(String providerId, int address, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(providerId, address, clazz);
    }

    static DigitalOutput instance(String providerId, int address, DigitalState shutdownState) throws ProviderException {
        return DigitalOutputFactory.instance(providerId, address, shutdownState);
    }
    static <T extends DigitalOutput> T instance(String providerId, int address, DigitalState shutdownState, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(providerId, address, shutdownState, clazz);
    }

    static DigitalOutput instance(String providerId, DigitalOutputConfig config) throws ProviderException {
        return DigitalOutputFactory.instance(providerId, config);
    }
    static <T extends DigitalOutput> T instance(String providerId, DigitalOutputConfig config, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(providerId, config, clazz);
    }

    static DigitalOutput instance(DigitalOutputProvider provider, int address) throws ProviderException {
        return DigitalOutputFactory.instance(provider, address);
    }
    static <T extends DigitalOutput> T instance(DigitalOutputProvider provider, int address, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(provider, address,clazz);
    }

    static DigitalOutput instance(DigitalOutputProvider provider, int address, DigitalState shutdownState) throws ProviderException {
        return DigitalOutputFactory.instance(provider, address, shutdownState);
    }
    static <T extends DigitalOutput> T instance(DigitalOutputProvider provider, int address, DigitalState shutdownState, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(provider, address, shutdownState, clazz);
    }

    static DigitalOutput instance(DigitalOutputProvider provider, DigitalOutputConfig config) throws ProviderException {
        return DigitalOutputFactory.instance(provider, config);
    }
    static <T extends DigitalOutput> T instance(DigitalOutputProvider provider, DigitalOutputConfig config, Class<T> clazz) throws ProviderException {
        return DigitalOutputFactory.instance(provider, config, clazz);
    }

}
