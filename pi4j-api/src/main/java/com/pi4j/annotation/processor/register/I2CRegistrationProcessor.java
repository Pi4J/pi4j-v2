package com.pi4j.annotation.processor.register;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  I2CRegistrationProcessor.java
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
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.platform.Platform;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * <p>I2CRegistrationProcessor class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class I2CRegistrationProcessor implements RegisterProcessor<I2C> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** {@inheritDoc} */
    @Override
    public boolean isEligible(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure this field is of type 'I2C'
        if(!I2C.class.isAssignableFrom(field.getType()))
            return false;

        // this processor can process this annotated instance
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public I2C process(Context context, Object instance, Register annotation, Field field) throws Exception {

        // validate that the 'ID' (value) attribute is not empty on this field annotation
        if (StringUtil.isNullOrEmpty(annotation.value()))
            throw new AnnotationException("Missing required 'value <ID>' attribute");

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = I2C.newConfigBuilder();
        if (annotation.value() != null) builder.id((annotation).value());

        // test for required additional annotations
        if (!field.isAnnotationPresent(I2CAddress.class))
            throw new AnnotationException("Missing required '@I2CAddress' annotation for this I/O type.");

        // all supported additional annotations for configuring the digital output
        I2CAddress address = field.getAnnotation(I2CAddress.class);
        builder.bus(address.bus());
        builder.device(address.device());

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

        // get designated platform to use to register this IO (if provided)
        Platform platform = null;
        if (field.isAnnotationPresent(WithPlatform.class)) {
            platform = WithAnnotationProcessor.getPlatform(context, field);
        }

        // get designated provider to use to register this IO (if provided)
        I2CProvider provider = null;
        if (field.isAnnotationPresent(WithProvider.class)) {
            provider = WithAnnotationProcessor.getProvider(context, platform, field, I2CProvider.class);
        }

        // if a provider was found, then create PWM IO instance using that provider
        if(provider != null){
            return provider.create(builder.build());
        }

        // if no provider was found, then create PWM IO instance using defaults
        else {
            if(platform != null)
                return platform.provider(I2CProvider.class).create(builder.build());
            else
                return context.provider(I2CProvider.class).create(builder.build());
        }
    }
}
