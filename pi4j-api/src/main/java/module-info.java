/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  module-info.java
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
module pi4j.api {
    exports com.pi4j;
    exports com.pi4j.annotation;
    exports com.pi4j.binding;
    exports com.pi4j.binding.exception;
    exports com.pi4j.common;
    exports com.pi4j.common.exception;
    exports com.pi4j.config;
    exports com.pi4j.config.exception;
    exports com.pi4j.context;
    exports com.pi4j.exception;
    exports com.pi4j.event;
    exports com.pi4j.io;
    exports com.pi4j.io.gpio.analog;
    exports com.pi4j.io.gpio.analog.binding;
    exports com.pi4j.io.gpio.digital;
    exports com.pi4j.io.gpio.digital.binding;
    exports com.pi4j.io.exception;
    exports com.pi4j.io.i2c;
    exports com.pi4j.io.pwm;
    exports com.pi4j.io.serial;
    exports com.pi4j.io.spi;
    exports com.pi4j.provider;
    exports com.pi4j.provider.exception;
    exports com.pi4j.registry;
    exports com.pi4j.registry.exception;
    exports com.pi4j.util;

    uses com.pi4j.binding.Binding;
    uses com.pi4j.provider.Provider;
    uses com.pi4j.annotation.Processor;

    provides com.pi4j.annotation.Processor
            with com.pi4j.annotation.processor.injector.ContextInjector,
                    com.pi4j.annotation.processor.injector.AnalogInputInjector,
                    com.pi4j.annotation.processor.injector.AnalogOutputInjector,
                    com.pi4j.annotation.processor.injector.DigitalInputInjector,
                    com.pi4j.annotation.processor.injector.DigitalOutputInjector,
                    com.pi4j.annotation.processor.injector.I2CInjector,
                    com.pi4j.annotation.processor.injector.ProviderGroupInjector,
                    com.pi4j.annotation.processor.injector.ProviderInjector,
                    com.pi4j.annotation.processor.injector.ProvidersInjector,
                    com.pi4j.annotation.processor.injector.PwmInjector,
                    com.pi4j.annotation.processor.injector.RegistryInjector,
                    com.pi4j.annotation.processor.injector.SerialInjector,
                    com.pi4j.annotation.processor.injector.SpiInjector,
                    com.pi4j.annotation.processor.register.AnalogChangeListenerProcessor,
                    com.pi4j.annotation.processor.register.AnalogInputProcessor,
                    com.pi4j.annotation.processor.register.AnalogOutputProcessor,
                    com.pi4j.annotation.processor.register.DigitalChangeListenerProcessor,
                    com.pi4j.annotation.processor.register.DigitalOutputProcessor,
                    com.pi4j.annotation.processor.register.ProviderProcessor,
                    com.pi4j.annotation.processor.event.AnalogChangeEventProcessor,
                    com.pi4j.annotation.processor.event.DigitalChangeEventProcessor;

    requires slf4j.api;
}
