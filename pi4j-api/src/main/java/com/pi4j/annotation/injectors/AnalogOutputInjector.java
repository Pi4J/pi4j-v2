package com.pi4j.annotation.injectors;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutputInjector.java
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
import com.pi4j.io.gpio.analog.*;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.provider.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnalogOutputInjector implements Injector<Inject, AnalogOutput> {

    @Override
    public boolean isAnnotationType(Annotation annotation) {
        return annotation instanceof Inject;
    }

    @Override
    public Class<Inject> getAnnotationType() {
        return Inject.class;
    }

    @Override
    public Class<AnalogOutput> getTargetType() { return AnalogOutput.class; }

    @Override
    public AnalogOutput instance(Field field, Inject annotation) throws Exception {

        Class<? extends Provider> providerClass = null;

        // test for required peer annotations
        if(!field.isAnnotationPresent(Address.class)){
            throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");
        }

        // all supported additional annotations for configuring the digital output
        Address address = field.getAnnotation(Address.class);
        Name name = null;
        Description description = null;
        ShutdownValue shutdownValue = null;

        if(field.isAnnotationPresent(Name.class)){
            name = field.getAnnotation(Name.class);
        }

        if(field.isAnnotationPresent(Description.class)){
            description = field.getAnnotation(Description.class);
        }

        if(field.isAnnotationPresent(ShutdownValue.class)){
            shutdownValue = field.getAnnotation(ShutdownValue.class);
        }

        AnalogOutputBuilder builder = AnalogOutput.builder();
        if(annotation.id() != null) builder.id(annotation.id());
        builder.address(address.value());

        if(name != null) builder.name(name.value());
        if(description != null) builder.description(description.value());
        if(shutdownValue != null) builder.shutdownValue(shutdownValue.value());

        AnalogOutputProvider provider = null;
        if(field.isAnnotationPresent(com.pi4j.annotation.Provider.class)){
            provider = ProviderAnnotationProcessor.instance(field, AnalogOutputProvider.class);
        }
        else{
            provider = Pi4J.providers().getDefault(AnalogOutputProvider.class);
        }

        return AnalogOutput.create(provider, builder.build());

        // unable to inject anything
        //throw new ProviderNotFoundException("ID=" + id + "; CLASS=" + providerClass);
    }
}
