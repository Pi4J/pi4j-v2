package com.pi4j.context;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Context.java
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

import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.io.gpio.analog.AnalogOutputProvider;
import com.pi4j.io.gpio.digital.DigitalInputProvider;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.registry.Registry;

public interface Context extends Describable {

    ContextConfig config();
    Providers providers();
    Registry registry();
    Platforms platforms();

    Context shutdown() throws LifecycleException;
    Context inject(Object... objects) throws AnnotationException;

    default <T extends Provider> T provider(String providerId) throws ProviderNotFoundException {
        return (T)providers().get(providerId);
    }

    default <T extends Provider> T provider(Class<T> providerClass) throws ProviderNotFoundException {
        // return the default provider for this type from the default platform
        if(platform().hasProvider(providerClass))
            return platform().provider(providerClass);

        // return the default provider for this type (outside of default platform)
        if(providers().exists(providerClass))
            return (T)providers().get(providerClass);

        // provider not found
        throw new ProviderNotFoundException(providerClass);
    }

    default <T extends Provider> T provider(IOType ioType) throws ProviderNotFoundException {
        // return the default provider for this type from the default platform
        if(platform().hasProvider(ioType))
            return platform().provider(ioType);

        // return the default provider for this type (outside of default platform)
        if(providers().exists(ioType))
            return (T)providers().get(ioType);

        // provider not found
        throw new ProviderNotFoundException(ioType);
    }


    default boolean hasProvider(String providerId) {
        try {
            return providers().exists(providerId);
        }
        catch (Exception e){
            return false;
        }
    }

    default <T extends Provider> boolean hasProvider(Class<T> providerClass) {
        return providers().exists(providerClass);
    }

    default <T extends Provider> boolean hasProvider(IOType ioType) {
        return providers().exists(ioType);
    }

    default <T extends AnalogInputProvider> T ain() throws ProviderException {
        return analogInput();
    }

    default <T extends AnalogOutputProvider> T aout() throws ProviderException{
        return analogOutput();
    }

    default <T extends DigitalInputProvider> T din() throws ProviderException{
        return digitalInput();
    }

    default <T extends DigitalOutputProvider> T dout() throws ProviderException{
        return digitalOutput();
    }

    default <T extends AnalogInputProvider> T analogInput() throws ProviderException {
        return this.provider(IOType.ANALOG_INPUT);
    }

    default <T extends AnalogOutputProvider> T analogOutput() throws ProviderException{
        return this.provider(IOType.ANALOG_OUTPUT);
    }

    default <T extends DigitalInputProvider> T digitalInput() throws ProviderException{
        return this.provider(IOType.DIGITAL_INPUT);
    }

    default <T extends DigitalOutputProvider> T digitalOutput() throws ProviderException{
        return this.provider(IOType.DIGITAL_OUTPUT);
    }

    default <T extends PwmProvider> T pwm() throws ProviderException{
        return this.provider(IOType.PWM);
    }

    default <T extends SpiProvider> T spi() throws ProviderException{
        return this.provider(IOType.SPI);
    }

    default <T extends I2CProvider> T i2c() throws ProviderException{
        return this.provider(IOType.I2C);
    }

    default <T extends SerialProvider> T serial() throws ProviderException{
        return this.provider(IOType.SERIAL);
    }

    default Platform platform(){
        return platforms().getDefault();
    }

    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create()
                .category("CONTEXT")
                .name("Runtime Context")
                .type(this.getClass());

        descriptor.add(registry().describe());
        descriptor.add(platforms().describe());
        descriptor.add(providers().describe());
        return descriptor;
    }
}
