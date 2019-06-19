package com.pi4j.provider;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Providers.java
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
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.io.gpio.analog.AnalogOutputProvider;
import com.pi4j.io.gpio.digital.DigitalInputProvider;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.util.Descriptor;
import com.pi4j.util.StringUtil;

import java.io.PrintStream;
import java.util.Map;

/**
 * <p>
 * This class provides static methods to configure the Pi4J library's default
 * platform.  Pi4J supports the following platforms:  RaspberryPi, BananaPi, BananaPro, Odroid.
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public interface Providers {

    ProviderGroup<AnalogInputProvider> analogInput();
    ProviderGroup<AnalogOutputProvider> analogOutput();
    ProviderGroup<DigitalInputProvider> digitalInput();
    ProviderGroup<DigitalOutputProvider> digitalOutput();
    ProviderGroup<PwmProvider> pwm();
    ProviderGroup<SpiProvider> spi();
    ProviderGroup<I2CProvider> i2c();
    ProviderGroup<SerialProvider> serial();

    /**
     * Get all providers
     * @return
     */
    Map<String, Provider> all();

    /**
     * Get all providers of a specified io class/interface.
     *
     * @param providerClass
     * @param <T>
     * @return
     * @throws ProviderException
     */
    <T extends Provider> Map<String, T> all(Class<T> providerClass) throws ProviderException;

    /**
     * Get all providers of a specified io type.
     *
     * @param providerType
     * @param <T>
     * @return
     * @throws ProviderException
     */
    <T extends Provider> Map<String, T> all(ProviderType providerType) throws ProviderException;

    boolean exists(String providerId) throws ProviderException;
    <T extends Provider> boolean exists(String providerId, Class<T> providerClass) throws ProviderException;
    <T extends Provider> boolean exists(String providerId, ProviderType providerType) throws ProviderException;
    Provider get(String providerId) throws ProviderException;
    <T extends Provider> T get(String providerId, Class<T> providerClass) throws ProviderException;
    <T extends Provider> T get(String providerId, ProviderType providerType) throws ProviderException;
    <T extends Provider> Providers add(T... provider) throws ProviderException;
    <T extends Provider> void replace(T provider) throws ProviderException;
    <T extends Provider> void remove(String providerId) throws ProviderException;
    <T extends Provider> Map<ProviderType, T> defaults() throws ProviderException;
    <T extends Provider> T getDefault(Class<T> providerClass) throws ProviderException;
    <T extends Provider> T getDefault(ProviderType providerType) throws ProviderException;
    <T extends Provider> boolean hasDefault(Class<T> providerClass) throws ProviderException;
    <T extends Provider> boolean hasDefault(ProviderType providerType) throws ProviderException;
    void setDefault(String providerId) throws ProviderException;
    void removeDefault(String providerId) throws ProviderException;
    void removeDefault(ProviderType providerType) throws ProviderException;
    <T extends Provider> void removeDefault(Class<T> providerClass) throws ProviderException;
    void initialize(Context context, boolean autoDetect) throws ProviderException;
    void terminate(Context context) throws ProviderException;

    // DEFAULT METHODS
    default ProviderGroup<AnalogInputProvider> getAnalogInput() { return analogInput(); }
    default ProviderGroup<AnalogOutputProvider> getAnalogOutput() { return analogOutput(); }
    default ProviderGroup<DigitalInputProvider> getDigitalInput() { return digitalInput(); }
    default ProviderGroup<DigitalOutputProvider> getDigitalOutput() { return digitalOutput(); }
    default ProviderGroup<PwmProvider> getPwm() { return pwm(); }
    default ProviderGroup<SpiProvider> getSpi() { return spi(); }
    default ProviderGroup<I2CProvider> getI2C() { return i2c(); }
    default ProviderGroup<SerialProvider> getSerial() { return serial(); }
    default Map<String, Provider> getAll() { return all(); }
    default <T extends Provider> Map<String, T> getAll(Class<T> providerClass) throws ProviderException {
        return all(providerClass);
    }
    default <T extends Provider> Map<String, T> getAll(ProviderType providerType) throws ProviderException {
        return all(providerType);
    }

    default void describe(Descriptor descriptor) {
        var providers = all();
        var child = descriptor.add("PROVIDERS [" + providers.size() + "]");
        if(providers != null && !providers.isEmpty()) {
            providers.forEach((id, provider) -> {
                provider.describe(child);
            });
        }
    }

    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create("-----------------------------------\r\n" + "Pi4J - Providers Information\r\n" + "-----------------------------------");
        describe(descriptor);
        return descriptor;
    }

}
