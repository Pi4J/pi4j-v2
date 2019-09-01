package com.pi4j.platform;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Platform.java
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

import com.pi4j.common.Descriptor;
import com.pi4j.context.Context;
import com.pi4j.extension.Extension;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.io.gpio.analog.AnalogOutputProvider;
import com.pi4j.io.gpio.digital.DigitalInputProvider;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInterfaceException;
import com.pi4j.provider.exception.ProviderNotFoundException;

import java.util.Map;

/**
 * <p>Platform interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Platform extends Extension<Platform> {

    /**
     * <p>weight.</p>
     *
     * @return a int.
     */
    int weight();
    /**
     * <p>enabled.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @return a boolean.
     */
    boolean enabled(Context context);

    /**
     * <p>providers.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    Map<IOType, Provider> providers();

    /**
     * <p>provider.</p>
     *
     * @param providerClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderNotFoundException if any.
     * @throws com.pi4j.provider.exception.ProviderInterfaceException if any.
     */
    <T extends Provider> T provider(Class<T> providerClass) throws ProviderNotFoundException, ProviderInterfaceException;

    /**
     * <p>provider.</p>
     *
     * @param ioType a {@link com.pi4j.io.IOType} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderNotFoundException if any.
     */
    <T extends Provider> T provider(IOType ioType) throws ProviderNotFoundException;

    /**
     * <p>hasProvider.</p>
     *
     * @param providerClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a boolean.
     */
    default <T extends Provider> boolean hasProvider(Class<T> providerClass) {
        try {
            return provider(providerClass) != null;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * <p>hasProvider.</p>
     *
     * @param ioType a {@link com.pi4j.io.IOType} object.
     * @param <T> a T object.
     * @return a boolean.
     */
    default <T extends Provider> boolean hasProvider(IOType ioType) {
        try {
            return provider(ioType) != null;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * <p>analogInput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogInputProvider> T analogInput() throws ProviderException{
        return this.provider(IOType.ANALOG_INPUT);
    }

    /**
     * <p>analogOutput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogOutputProvider> T analogOutput() throws ProviderException{
        return this.provider(IOType.ANALOG_OUTPUT);
    }

    /**
     * <p>digitalInput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalInputProvider> T digitalInput() throws ProviderException{
        return this.provider(IOType.DIGITAL_INPUT);
    }

    /**
     * <p>digitalOutput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalOutputProvider> T digitalOutput() throws ProviderException{
        return this.provider(IOType.DIGITAL_OUTPUT);
    }

    /**
     * <p>ain.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogInputProvider> T ain() throws ProviderException{
        return analogInput();
    }

    /**
     * <p>aout.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogOutputProvider> T aout() throws ProviderException{
        return analogOutput();
    }

    /**
     * <p>din.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalInputProvider> T din() throws ProviderException{
        return digitalInput();
    }

    /**
     * <p>dout.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalOutputProvider> T dout() throws ProviderException{
        return digitalOutput();
    }

    /**
     * <p>pwm.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends PwmProvider> T pwm() throws ProviderException{
        return this.provider(IOType.PWM);
    }

    /**
     * <p>spi.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends SpiProvider> T spi() throws ProviderException{
        return this.provider(IOType.SPI);
    }

    /**
     * <p>i2c.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends I2CProvider> T i2c() throws ProviderException{
        return this.provider(IOType.I2C);
    }

    /**
     * <p>serial.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends SerialProvider> T serial() throws ProviderException{
        return this.provider(IOType.SERIAL);
    }

    /**
     * <p>describe.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    default Descriptor describe() {
        return Descriptor.create()
                .id(this.id())
                .name(this.name())
                .category("PLATFORM")
                .description(this.description()).type(this.getClass());
    }
}
