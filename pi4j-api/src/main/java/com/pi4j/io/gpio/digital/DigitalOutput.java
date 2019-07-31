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

import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.Output;
import com.pi4j.io.gpio.digital.impl.DigitalOutputFactory;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;

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


    // ---------------------------------------------------------------------------
    // INSTANCE ACCESSOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static boolean exists(Context context, String id) throws ProviderException, NotInitializedException {
        return DigitalOutputFactory.exists(context, id);
    }

    static <T extends DigitalOutput> T get(Context context, String id) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutputFactory.get(context, id);
    }

    // ---------------------------------------------------------------------------
    // BUILDER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static DigitalOutputBuilder builder() throws ProviderException {
        return DigitalOutputFactory.builder();
    }

    // ---------------------------------------------------------------------------
    // FRIENDLY HELPER CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends DigitalOutput> T create(Context context, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        DigitalOutputBuilder builder = DigitalOutput.builder();
        builder.address(address);
        return (T)DigitalOutput.create(context, builder.build());
    }

    static <T extends DigitalOutput> T create(Context context, Integer address, String id) throws ProviderException, NotInitializedException, RegistryException {
        DigitalOutputBuilder builder = DigitalOutput.builder();
        builder.id(id).address(address).id(id);
        return (T)DigitalOutput.create(context, builder.build());
    }

    static <T extends DigitalOutput> T create(Context context, Integer address, String id, String name) throws ProviderException, NotInitializedException, RegistryException {
        DigitalOutputBuilder builder = DigitalOutput.builder();
        builder.id(id).address(address).id(id).name(name);
        return (T)DigitalOutput.create(context, builder.build());
    }

    static <T extends DigitalOutput> T create(Context context, String providerId, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        DigitalOutputBuilder builder = DigitalOutput.builder();
        builder.address(address);
        return (T)DigitalOutput.create(context, providerId, builder.build());
    }

    static <T extends DigitalOutput> T create(Context context, DigitalOutputProvider provider, Integer address) throws ProviderException, NotInitializedException, RegistryException {
        DigitalOutputBuilder builder = DigitalOutput.builder();
        builder.address(address);
        return (T)DigitalOutput.create(context, provider, builder.build());
    }

    // ---------------------------------------------------------------------------
    // RAW FACTORY CREATOR STATIC METHODS
    // ---------------------------------------------------------------------------

    static <T extends DigitalOutput> T create(Context context, DigitalOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalOutputFactory.create(context, config);
    }

    static <T extends DigitalOutput> T create(Context context, String providerId, DigitalOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalOutputFactory.create(context, providerId, config);
    }

    static <T extends DigitalOutput> T create(Context context, DigitalOutputProvider provider, DigitalOutputConfig config) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalOutputFactory.create(context, provider, config);
    }

    // ---------------------------------------------------------------------------
    // SPECIFIED RETURN CLASS HELPER METHODS
    // ---------------------------------------------------------------------------

    static <T extends DigitalOutput> T create(Context context, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, address);
    }

    static <T extends DigitalOutput> T create(Context context, Integer address, String id, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, address, id);
    }

    static <T extends DigitalOutput> T create(Context context, Integer address, String id, String name, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, address, id, name);
    }

    static <T extends DigitalOutput> T create(Context context, String providerId, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, providerId, address);
    }

    static <T extends DigitalOutput> T create(Context context, DigitalOutputProvider provider, Integer address, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, provider, address);
    }

    static <T extends DigitalOutput> T create(Context context, DigitalOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, config);
    }

    static <T extends DigitalOutput> T create(Context context, String providerId, DigitalOutputConfig config, Class<T> clazz) throws ProviderException, NotInitializedException, RegistryException {
        return (T)DigitalOutput.create(context, providerId, config);
    }

    static <T extends DigitalOutput> T create(Context context, DigitalOutputProvider provider, DigitalOutputConfig config, Class<T> clazz) throws NotInitializedException, ProviderException, RegistryException {
        return (T)DigitalOutput.create(context, provider, config);
    }

}
