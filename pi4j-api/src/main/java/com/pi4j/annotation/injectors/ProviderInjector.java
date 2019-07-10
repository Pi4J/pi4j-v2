package com.pi4j.annotation.injectors;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ProviderInjector.java
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
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderNotFoundException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ProviderInjector implements Injector<Inject, Provider> {

    @Override
    public boolean isAnnotationType(Annotation annotation) {
        return annotation instanceof Inject;
    }

    @Override
    public Class<Inject> getAnnotationType() {
        return Inject.class;
    }

    @Override
    public Class<Provider> getTargetType() { return Provider.class; }

    @Override
    public Provider instance(Field field, Inject annotation) throws Exception {
        String id = null;
        Class<? extends Provider> providerClass = null;

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
            providerClass = (Class<? extends Provider>) field.getType();
        }

        if(id != null && !id.isEmpty()) {
            if(providerClass == null) {
                // get provider instance using ID only
                return Pi4J.context().providers().get(id);
            }
            else {
                // get provider instance using ID and Provider Class
                return Pi4J.context().providers().get(id, providerClass);
            }
        }

        if(providerClass != null){
            // get default provider instance using only Provider Class
            return Pi4J.context().providers().getDefault(providerClass);
        }

        // unable to inject anything
        throw new ProviderNotFoundException("ID=" + id + "; CLASS=" + providerClass);
    }
}
