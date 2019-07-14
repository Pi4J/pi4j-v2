package com.pi4j.annotation.processor.event;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  OnEventProcessor.java
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

import com.pi4j.annotation.OnEvent;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.processor.MethodProcessor;
import com.pi4j.event.Event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface OnEventProcessor extends MethodProcessor<OnEvent> {

    Class<? extends Event> getEventType();

    @Override
    default boolean isAnnotationType(Annotation annotation) {
        return annotation instanceof OnEvent;
    }

    @Override
    default Class<OnEvent> getAnnotationType() {
        return OnEvent.class;
    }

    @Override
    default boolean eligible(Object instance, Method method, OnEvent annotation) throws AnnotationException {

        // validate parameter count
        if(method.getParameterCount() != 1) {
            throw new AnnotationException("The '@" +annotation.annotationType().getSimpleName() + "' annotated method must include (1) parameter extended from `Event`");
        }

        // validate parameter type
        if(!Event.class.isAssignableFrom(method.getParameters()[0].getType())) {
            throw new AnnotationException("The '@OnEvent' annotated method must include a parameter extending from `Event`");
        }

        // get method parameters
        Parameter[] parameters = method.getParameters();

        // handle specific implementations

        return parameters[0].getType().isAssignableFrom(getEventType());
    }
}
