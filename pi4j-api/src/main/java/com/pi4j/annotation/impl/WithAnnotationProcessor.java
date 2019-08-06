package com.pi4j.annotation.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  WithAnnotationProcessor.java
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

import com.pi4j.annotation.WithPlatform;
import com.pi4j.annotation.WithProvider;
import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.platform.Platform;
import com.pi4j.platform.exception.PlatformNotFoundException;
import com.pi4j.platform.exception.PlatformTypeException;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.util.StringUtil;

import java.lang.reflect.Field;

public class WithAnnotationProcessor {

    public static <T extends Platform> T getPlatform(Context context, Field field) throws PlatformNotFoundException, PlatformTypeException {
        String platformId = null;
        Class<? extends Platform> platformClass = null;
        Platform platform;

        // if the '@WithPlatform' annotation is missing, then just return null
        if(!field.isAnnotationPresent(WithPlatform.class)){
            return null;
        }

        // get the '@WithPlatform' annotation from the provided field
        WithPlatform annotation = field.getAnnotation(WithPlatform.class);

        // get platform unique identifier from declared annotation (if provided)
        if(StringUtil.isNotNullOrEmpty(annotation.id())){
            platformId = annotation.id().trim();
        }

        // get platform class from declared annotation (if provided)
        if(annotation.type() != null && annotation.type() != void.class && Platform.class.isAssignableFrom(annotation.type())){
            platformClass = annotation.type();
        }

        // if no platform unique identifier or platform class was provided,
        // then throw exception about this annotation instance
        if(platformId == null && platformClass == null) {
            throw new PlatformNotFoundException("No platform identifier or class was attributed in the '@WithPlatform' " +
                    " annotation; Remove this annotation or provide additional identifying attributes.)");
        }

        // if a platform unique identifier was provided and a platform class was also provided,
        // then lookup the platform by its unique identifier and class
        if(platformId != null && platformClass == null) {
            return (T) context.platforms().get(platformId, platformClass);
        }

        // if a platform unique identifier was provided, but a platform class was not provided,
        // then lookup the platform by its unique identifier irrespective of the class
        if(StringUtil.isNotNullOrEmpty(platformId)) {
            return (T) context.platforms().get(platformId);
        }

        // if no platform unique identifier was provided, but a platform class was provided,
        // then lookup the platform by the platform class
        if(platformClass != null) {
            return (T) context.platforms().get(platformClass);
        }

        // unable to locate requested platform
        throw new PlatformNotFoundException("ID=" + platformId + "; CLASS=" + platformClass);
    }

    public static <T extends Provider> T getProvider(Context context, Platform platform, Field field, Class<T> providerClass) throws ProviderException, NotInitializedException {
        String id = null;

        // if the '@WithProvider' annotation is missing, then just return
        // the default provider based on the requested provider class
        if(!field.isAnnotationPresent(WithProvider.class)){
            return null;
        }

        // get the '@WithProvider' annotation from the provided field
        WithProvider annotation = field.getAnnotation(WithProvider.class);

        // get provider unique identifier from declared annotation (if provided)
        if(StringUtil.isNullOrEmpty(annotation.id())){
            id = annotation.id().trim();
        }

        // get provider class from declared annotation (if provided)
        if(annotation.type() != null && annotation.type() != void.class && Provider.class.isAssignableFrom(annotation.type())){
            providerClass = annotation.type();
        }

        // if no user specified class attribute was provided in the declared annotation,
        // then we can infer the type based on the target field
        if(providerClass == null && field.getType() != null && Provider.class.isAssignableFrom(field.getType())){
            providerClass = (Class<T>) field.getType();
        }

        // First, if a provider unique identifier was specified, then we can get the concrete
        // provider instance from the managed collection of providers in the current context
        if(StringUtil.isNotNullOrEmpty(id)) {
            // get provider instance using identifier and provider class
            if(providerClass != null) {
                return (T)context.providers().get(id, providerClass);
            }
            // get provider instance using identifier only
            else {
                return (T)context.providers().get(id);
            }
        }

        // if a unique provider identifier was not specified, then we will
        // attempt to access the provider by class against the TARGET (specified) platform
        if(platform != null){
            // if a provider class is found using this target provider, then return the resolved provider instance
            if(platform.hasProvider(providerClass)){
                return (T)platform.provider(providerClass);
            } else {
                throw new ProviderNotFoundException("A provider was not found for provider id=" + id +
                        "; provider class=" + providerClass + "; platform=" + platform.getClass().getName());
            }
        }

        // if a unique provider identifier was not specified, then we will
        // attempt to access the provider by class against the DEFAULT platform
        if(context.platform().hasProvider(providerClass)){
            return (T)context.platform().provider(providerClass);
        }

        // if no platform was assigned, or no provider of this class type was found
        // on the default platform, then attempt to access the provider by class type
        // against all available providers
        if(context.hasProvider(providerClass)){
            return  (T)context.provider(providerClass);
        }

        // unable to locate requested provider
        throw new ProviderNotFoundException("A provider was not found for provider id=" + id +
                "; provider class=" + providerClass );
    }
}
