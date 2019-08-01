package com.pi4j.annotation.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ProviderAnnotationProcessor.java
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
import com.pi4j.exception.NotInitializedException;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderNotFoundException;

import java.lang.reflect.Field;

public class ProviderAnnotationProcessor {

    public static <T extends Provider> T instance(Context context, Field field, Class<T> providerClass) throws ProviderException, NotInitializedException {
        String id = null;

        // if the '@Provider' annotation is missing, then just return
        // the default provider based on the requested provider class
        if(!field.isAnnotationPresent(com.pi4j.annotation.Provider.class)){
            return null;
        }

        // get the '@Provider' annotation from the provided field
        com.pi4j.annotation.Provider annotation = field.getAnnotation(com.pi4j.annotation.Provider.class);

        // <<1>> inject instance by user defined ID property
        if(annotation.id() != null || !annotation.id().isEmpty()){
            id = annotation.id().trim();
        }

        // <<2>> alternatively, inject by user defined class type property
        if(annotation.type() != null && annotation.type() != void.class && Provider.class.isAssignableFrom(annotation.type())){
            providerClass = annotation.type();
        }

        // <<3>> alternatively, if no user defined class type property was defined, then we can infer the type based on the target field
        if(providerClass == null && field.getType() != null && Provider.class.isAssignableFrom(field.getType())){
            providerClass = (Class<T>) field.getType();
        }

        // get provider instance
        if(id != null && !id.isEmpty()) {
            // get provider instance using ID and Provider Class
            return context.providers().get(id, providerClass);
        }
        else if(providerClass != null){
            // get default provider instance using only Provider Class
            return (T)context.platform().provider(providerClass);
        }

        // unable to locate requested provider
        throw new ProviderNotFoundException("ID=" + id + "; CLASS=" + providerClass);
    }
}
