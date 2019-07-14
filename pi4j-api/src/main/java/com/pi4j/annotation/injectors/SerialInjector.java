package com.pi4j.annotation.injectors;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  SerialInjector.java
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
import com.pi4j.annotation.Inject;
import com.pi4j.annotation.Injector;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.io.serial.Serial;
import com.pi4j.util.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SerialInjector implements Injector<Inject, Serial> {

    @Override
    public boolean isAnnotationType(Annotation annotation) {
        return annotation instanceof Inject;
    }

    @Override
    public Class<Inject> getAnnotationType() {
        return Inject.class;
    }

    @Override
    public Class<Serial> getTargetType() { return Serial.class; }

    @Override
    public Serial instance(Field field, Inject annotation) throws Exception {

        // test for required peer annotations
        if(StringUtil.isNullOrEmpty(annotation.value()))
            throw new AnnotationException("Missing required 'value(id)' annotation attribute for this field: " +
                    field.getDeclaringClass().getName() + "::" + field.getName() + "@Inject");

        // get target I/O instance from the Pi4J registry
        return Pi4J.context().registry().get(annotation.value(), Serial.class);
    }
}
