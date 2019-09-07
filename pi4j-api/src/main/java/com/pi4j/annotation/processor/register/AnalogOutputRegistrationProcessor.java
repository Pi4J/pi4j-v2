package com.pi4j.annotation.processor.register;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutputRegistrationProcessor.java
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
import com.pi4j.annotation.impl.WithAnnotationProcessor;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogOutputConfigBuilder;
import com.pi4j.io.gpio.analog.AnalogOutputProvider;
import com.pi4j.platform.Platform;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <p>AnalogOutputRegistrationProcessor class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class AnalogOutputRegistrationProcessor implements RegisterProcessor<AnalogOutput> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** {@inheritDoc} */
    @Override
    public boolean isEligible(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure this field is of type 'AnalogOutput'
        if(!AnalogOutput.class.isAssignableFrom(field.getType()))
            return false;

        // this processor can process this annotated instance
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public AnalogOutput process(Context context, Object instance, Register annotation, Field field) throws Exception {

        // validate that the 'ID' (value) attribute is not empty on this field annotation
        if (StringUtil.isNullOrEmpty(annotation.value()))
            throw new AnnotationException("Missing required 'value <ID>' attribute");

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = AnalogOutputConfigBuilder.newInstance();
        if (annotation.value() != null) builder.id((annotation).value());

        // test for required additional annotations
        if (!field.isAnnotationPresent(Address.class))
            throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");

        // all supported additional annotations for configuring the digital output
        Address address = field.getAnnotation(Address.class);
        builder.address(address.value());

        if (field.isAnnotationPresent(Name.class)) {
            Name name = field.getAnnotation(Name.class);
            if (name != null) builder.name(name.value());
        }

        if (field.isAnnotationPresent(Description.class)) {
            Description description = field.getAnnotation(Description.class);
            if (description != null) builder.description(description.value());
        }

        if (field.isAnnotationPresent(Range.class)) {
            Range range = field.getAnnotation(Range.class);
            if (range != null) builder.min(range.min());
            if (range != null) builder.max(range.max());
        }

        if (field.isAnnotationPresent(ShutdownValue.class)) {
            ShutdownValue shutdownValue = field.getAnnotation(ShutdownValue.class);
            if (shutdownValue != null) builder.shutdown(shutdownValue.value());
        }

        if (field.isAnnotationPresent(InitialValue.class)) {
            InitialValue initialValue = field.getAnnotation(InitialValue.class);
            if (initialValue != null) builder.initial((int)Math.round(initialValue.value()));
        }

        if (field.isAnnotationPresent(StepValue.class)) {
            StepValue stepValue = field.getAnnotation(StepValue.class);
            if (stepValue != null) builder.step(stepValue.value());
        }

        if (field.isAnnotationPresent(InheritProperties.class)) {
            InheritProperties inheritProperties = field.getAnnotation(InheritProperties.class);
            if (inheritProperties != null) builder.inheritProperties(inheritProperties.value());
        }

        // get designated platform to use to register this IO (if provided)
        Platform platform = null;
        if (field.isAnnotationPresent(WithPlatform.class)) {
            platform = WithAnnotationProcessor.getPlatform(context, field);
        }

        // get designated provider to use to register this IO (if provided)
        AnalogOutputProvider provider = null;
        if (field.isAnnotationPresent(WithProvider.class)) {
            provider = WithAnnotationProcessor.getProvider(context, platform, field, AnalogOutputProvider.class);
        }

        // if a provider was found, then create analog output IO instance using that provider
        if(provider != null){
            return provider.create(builder.build());
        }

        // if no provider was found, then create analog output IO instance using defaults
        else {
            if(platform != null)
                return platform.provider(AnalogOutputProvider.class).create(builder.build());
            else
                return context.provider(AnalogOutputProvider.class).create(builder.build());
        }
    }
}
