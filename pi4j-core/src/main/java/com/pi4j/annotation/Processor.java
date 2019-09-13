package com.pi4j.annotation;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Processor.java
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

import com.pi4j.context.Context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <p>Processor interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Processor<A extends Annotation, T> {
    /**
     * <p>annotationType.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    Class<A> annotationType();
    /**
     * <p>isEligible.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @param instance a {@link java.lang.Object} object.
     * @param annotation a A object.
     * @param method a {@link java.lang.reflect.Method} object.
     * @return a boolean.
     * @throws java.lang.Exception if any.
     */
    boolean isEligible(Context context, Object instance, A annotation, Method method) throws Exception;
    /**
     * <p>isEligible.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @param instance a {@link java.lang.Object} object.
     * @param annotation a A object.
     * @param field a {@link java.lang.reflect.Field} object.
     * @return a boolean.
     * @throws java.lang.Exception if any.
     */
    boolean isEligible(Context context,Object instance, A annotation, Field field) throws Exception;
    /**
     * <p>process.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @param instance a {@link java.lang.Object} object.
     * @param annotation a A object.
     * @param method a {@link java.lang.reflect.Method} object.
     * @throws java.lang.Exception if any.
     */
    void process(Context context, Object instance, A annotation, Method method) throws Exception;
    /**
     * <p>process.</p>
     *
     * @param context a {@link com.pi4j.context.Context} object.
     * @param instance a {@link java.lang.Object} object.
     * @param annotation a A object.
     * @param field a {@link java.lang.reflect.Field} object.
     * @return a T object.
     * @throws java.lang.Exception if any.
     */
    T process(Context context, Object instance, A annotation, Field field) throws Exception;
}
