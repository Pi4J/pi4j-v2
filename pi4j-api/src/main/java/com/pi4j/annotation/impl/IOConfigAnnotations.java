package com.pi4j.annotation.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  IOConfigAnnotations.java
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
import com.pi4j.io.IOAddressConfigBuilder;
import com.pi4j.io.IOConfigBuilder;
import com.pi4j.io.IODeviceConfigBuilder;
import com.pi4j.io.gpio.analog.AnalogConfigBuilder;
import com.pi4j.util.StringUtil;

import java.lang.reflect.Field;

public class IOConfigAnnotations {


    public static void processIOConfigAnnotations(AnalogConfigBuilder builder, Field field){
        if (field.isAnnotationPresent(Range.class)) {
            Range range = field.getAnnotation(Range.class);
            if (range != null) builder.min(range.min());
            if (range != null) builder.max(range.max());
        }
        processIOConfigAnnotations((IOAddressConfigBuilder)builder, field);
    }

    public static void processIOConfigAnnotations(IODeviceConfigBuilder builder, Field field){
        processIOConfigAnnotations((IOConfigBuilder)builder, field);
    }

    public static void processIOConfigAnnotations(IOAddressConfigBuilder builder, Field field){
        if (field.isAnnotationPresent(Address.class)) {
            Address address = field.getAnnotation(Address.class);
            builder.address(address.value());
        }
        processIOConfigAnnotations((IOConfigBuilder)builder, field);
    }

    public static void processIOConfigAnnotations(IOConfigBuilder builder, Field field){

        if (field.isAnnotationPresent(Name.class)) {
            Name name = field.getAnnotation(Name.class);
            if (name != null) builder.name(name.value());
        }

        if (field.isAnnotationPresent(Description.class)) {
            Description description = field.getAnnotation(Description.class);
            if (description != null) builder.description(description.value());
        }

        if (field.isAnnotationPresent(InheritProperties.class)) {
            InheritProperties inheritProperties = field.getAnnotation(InheritProperties.class);
            if (inheritProperties != null) builder.inheritProperties(inheritProperties.value());
        }

        // get designated platform to use to register this IO (if provided)
        if (field.isAnnotationPresent(WithPlatform.class)) {
            WithPlatform platformAnnotation = field.getAnnotation(WithPlatform.class);
            if(StringUtil.isNotNullOrEmpty(platformAnnotation.value())){
                builder.platform(platformAnnotation.value());
            }
            else if(platformAnnotation.type() != null){
                builder.platform(platformAnnotation.type().getName());
            }
        }

        // get designated provider to use to register this IO (if provided)
        if (field.isAnnotationPresent(WithProvider.class)) {
            WithProvider providerAnnotation = field.getAnnotation(WithProvider.class);
            if(StringUtil.isNotNullOrEmpty(providerAnnotation.value())){
                builder.provider(providerAnnotation.value());
            }
            else if(providerAnnotation.type() != null){
                builder.provider(providerAnnotation.type().getName());
            }
        }
    }

}
