package com.pi4j.annotation.processor.register;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputProcessor.java
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

import com.pi4j.annotation.*;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.ProviderAnnotationProcessor;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class DigitalOutputRegistrationProcessor implements RegisterProcessor<DigitalOutput> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean isEligible(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure this field is of type 'DigitalOutput'
        if(!DigitalOutput.class.isAssignableFrom(field.getType()))
            return false;

        // this processor can process this annotated instance
        return true;
    }

    @Override
    public DigitalOutput process(Context context, Object instance, Register annotation, Field field) throws Exception {

        // validate that the 'ID' (value) attribute is not empty on this field annotation
        if (StringUtil.isNullOrEmpty(annotation.value()))
            throw new AnnotationException("Missing required 'value <ID>' attribute");

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = DigitalOutput.builder();
        if (annotation.value() != null) builder.id((annotation).value());

        // test for required additional annotations
        if (!field.isAnnotationPresent(Address.class))
            throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");

        // all supported additional annotations for configuring the digital output
        Address address = field.getAnnotation(Address.class);
        builder.address(address.value());

        Name name = null;
        if (field.isAnnotationPresent(Name.class)) {
            name = field.getAnnotation(Name.class);
            if (name != null) builder.name(name.value());
        }

        Description description = null;
        if (field.isAnnotationPresent(Description.class)) {
            description = field.getAnnotation(Description.class);
            if (description != null) builder.description(description.value());
        }

        ShutdownState shutdownState = null;
        if (field.isAnnotationPresent(ShutdownState.class)) {
            shutdownState = field.getAnnotation(ShutdownState.class);
            if (shutdownState != null) builder.shutdown(shutdownState.value());
        }

        InitialState initialState = null;
        if (field.isAnnotationPresent(InitialState.class)) {
            initialState = field.getAnnotation(InitialState.class);
            if (initialState != null) builder.initial(initialState.value());
        }

        DigitalOutputProvider provider = null;
        if (field.isAnnotationPresent(com.pi4j.annotation.Provider.class)) {
            provider = ProviderAnnotationProcessor.instance(field, DigitalOutputProvider.class);
        } else {
            provider = context.providers().getDefault(DigitalOutputProvider.class);
        }

        // create and return I/O instance from registry
        return DigitalOutput.create(provider, builder.build());
    }
}
