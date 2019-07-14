package com.pi4j.annotation.processor.register;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogInputProcessor.java
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

import com.pi4j.Pi4J;
import com.pi4j.annotation.*;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.ProviderAnnotationProcessor;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.gpio.analog.AnalogInput;
import com.pi4j.io.gpio.analog.AnalogInputBuilder;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class AnalogInputProcessor implements RegisterProcessor<AnalogInput> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Class<AnalogInput> getTargetType() { return AnalogInput.class; }

    @Override
    public AnalogInput process(Object instance, Field field, Register annotation) throws Exception {

        Class<? extends Provider> providerClass = null;

        // test for required peer annotations
        if (!field.isAnnotationPresent(Address.class)) {
            throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");
        }
        try {
            boolean accessible = field.canAccess(instance);
            if (!accessible) field.trySetAccessible();

            // all supported additional annotations for configuring the digital output
            Address address = field.getAnnotation(Address.class);

            Name name = null;
            if (field.isAnnotationPresent(Name.class)) {
                name = field.getAnnotation(Name.class);
            }

            Description description = null;
            if (field.isAnnotationPresent(Description.class)) {
                description = field.getAnnotation(Description.class);
            }

            Range range = null;
            if (field.isAnnotationPresent(Range.class)) {
                range = field.getAnnotation(Range.class);
            }

            AnalogInputBuilder builder = AnalogInput.builder();
            if (annotation.value() != null) builder.id((annotation).value());
            builder.address(address.value());

            if (name != null) builder.name(name.value());
            if (description != null) builder.description(description.value());
            if (range != null) builder.min(range.min());
            if (range != null) builder.max(range.max());

            AnalogInputProvider provider = null;
            if (field.isAnnotationPresent(com.pi4j.annotation.Provider.class)) {
                provider = ProviderAnnotationProcessor.instance(field, AnalogInputProvider.class);
            } else {
                provider = Pi4J.providers().getDefault(AnalogInputProvider.class);
            }

            // create I/O instance
            AnalogInput output = AnalogInput.create(provider, builder.build());

            // return the I/O instance reference back on the annotated object
            return output;
        } catch (NotInitializedException e) {
            logger.error(e.getMessage(), e);
            throw new AnnotationException(e);
        } catch (ProviderException e) {
            logger.error(e.getMessage(), e);
            throw new AnnotationException(e);
        } catch (RegistryException e) {
            logger.error(e.getMessage(), e);
            throw new AnnotationException(e);
        }
    }
}
