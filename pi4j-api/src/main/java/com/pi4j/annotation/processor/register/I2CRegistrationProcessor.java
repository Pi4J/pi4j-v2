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

import com.pi4j.annotation.I2CAddress;
import com.pi4j.annotation.Register;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.IOConfigAnnotations;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
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

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = I2C.newConfigBuilder(context);
        if (StringUtil.isNotNullOrEmpty(annotation.value())) {
            builder.id((annotation).value());
        } else {
            builder.id(field.getName());
        }

        // process all supported optional configuration annotations for this I/O type
        IOConfigAnnotations.processIOConfigAnnotations(builder, field);

        // process additional optional configuration annotations that
        // may be unique to this particular I/O instance type
        if (field.isAnnotationPresent(I2CAddress.class)) {
            I2CAddress address = field.getAnnotation(I2CAddress.class);
            builder.bus(address.bus());
            builder.device(address.device());
        }

        // use the Pi4J context to create this IO instance
        return context.create(builder.build());
    }
}
