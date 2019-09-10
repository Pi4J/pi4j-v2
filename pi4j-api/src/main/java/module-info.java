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
module com.pi4j {
    exports com.pi4j;
    exports com.pi4j.annotation;
    exports com.pi4j.annotation.exception;
    exports com.pi4j.common;
    exports com.pi4j.config;
    exports com.pi4j.config.exception;
    exports com.pi4j.context;
    exports com.pi4j.exception;
    exports com.pi4j.extension;
    exports com.pi4j.extension.exception;
    exports com.pi4j.event;
    exports com.pi4j.io;
    exports com.pi4j.io.binding;
    exports com.pi4j.io.gpio.analog;
    exports com.pi4j.io.gpio.digital;
    exports com.pi4j.io.exception;
    exports com.pi4j.io.group;
    exports com.pi4j.io.i2c;
    exports com.pi4j.io.pwm;
    exports com.pi4j.io.serial;
    exports com.pi4j.io.spi;
    exports com.pi4j.platform;
    exports com.pi4j.platform.exception;
    exports com.pi4j.provider;
    exports com.pi4j.provider.exception;
    exports com.pi4j.registry;
    exports com.pi4j.util;

    uses com.pi4j.extension.Plugin;
    uses com.pi4j.annotation.Processor;

    provides com.pi4j.annotation.Processor
            with com.pi4j.annotation.processor.injector.ContextInjector,
                    com.pi4j.annotation.processor.injector.AnalogInputInjector,
                    com.pi4j.annotation.processor.injector.AnalogOutputInjector,
                    com.pi4j.annotation.processor.injector.DigitalInputInjector,
                    com.pi4j.annotation.processor.injector.DigitalOutputInjector,
                    com.pi4j.annotation.processor.injector.I2CInjector,
                    com.pi4j.annotation.processor.injector.PlatformInjector,
                    com.pi4j.annotation.processor.injector.PlatformsInjector,
                    com.pi4j.annotation.processor.injector.ProviderGroupInjector,
                    com.pi4j.annotation.processor.injector.ProviderInjector,
                    com.pi4j.annotation.processor.injector.ProvidersInjector,
                    com.pi4j.annotation.processor.injector.PwmInjector,
                    com.pi4j.annotation.processor.injector.RegistryInjector,
                    com.pi4j.annotation.processor.injector.SerialInjector,
                    com.pi4j.annotation.processor.injector.SpiInjector,
                    com.pi4j.annotation.processor.register.AnalogInputRegistrationProcessor,
                    com.pi4j.annotation.processor.register.AnalogOutputRegistrationProcessor,
                    com.pi4j.annotation.processor.register.AnalogValueChangeListenerRegistrationProcessor,
                    com.pi4j.annotation.processor.register.BindingRegistrationProcessor,
                    com.pi4j.annotation.processor.register.DigitalStateChangeListenerRegistrationProcessor,
                    com.pi4j.annotation.processor.register.DigitalInputRegistrationProcessor,
                    com.pi4j.annotation.processor.register.DigitalOutputRegistrationProcessor,
                    com.pi4j.annotation.processor.register.I2CRegistrationProcessor,
                    com.pi4j.annotation.processor.register.OnOffGroupRegistrationProcessor,
                    com.pi4j.annotation.processor.register.PlatformRegistrationProcessor,
                    com.pi4j.annotation.processor.register.ProviderRegistrationProcessor,
                    com.pi4j.annotation.processor.register.PwmRegistrationProcessor,
                    com.pi4j.annotation.processor.register.InitializedListenerRegistrationProcessor,
                    com.pi4j.annotation.processor.register.ShutdownListenerRegistrationProcessor,
                    com.pi4j.annotation.processor.event.AnalogValueChangeEventProcessor,
                    com.pi4j.annotation.processor.event.DigitalStateChangeEventProcessor,
                    com.pi4j.annotation.processor.event.InitializedEventProcessor,
                    com.pi4j.annotation.processor.event.ShutdownEventProcessor;

    requires org.slf4j;
}
