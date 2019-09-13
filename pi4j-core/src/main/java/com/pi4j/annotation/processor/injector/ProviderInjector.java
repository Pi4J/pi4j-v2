package com.pi4j.annotation.processor.injector;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
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

import com.pi4j.annotation.Inject;
import com.pi4j.context.Context;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderNotFoundException;

import java.lang.reflect.Field;

/**
 * <p>ProviderInjector class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class ProviderInjector implements InjectorProcessor<Provider> {

    /** {@inheritDoc} */
    @Override
    public Class<Provider> getTargetType() { return Provider.class; }

    /** {@inheritDoc} */
    @Override
    public Provider process(Context context, Object instance, Inject annotation, Field field) throws Exception {

        // get optional user specified platform ID from the annotation 'value' attribute
        String providerId = (annotation.value() != null && !annotation.value().isEmpty()) ? annotation.value().trim() : null;

        // get provider class from explict annotated 'type' attribute or inferred from field type
        Class<? extends Provider> providerClass = (annotation.type() != null && annotation.type() != void.class &&
                Provider.class.isAssignableFrom(annotation.type())) ? annotation.type() : (Class<? extends Provider>) field.getType();


        // <<1>> inject instance by user defined ID property and class
        if(providerId != null && providerClass != null){
            // get provider instance using ID and Provider Class
            return context.providers().get(providerId, providerClass);
        }

        // <<2>> inject instance by explicit provider ID
        else if(providerId != null){
            // get provider instance using ID only
            return context.providers().get(providerId);
        }

        // <<3>> inject instance by explicit or inferred class only
        else if(providerClass != null){

            // first attempt to get the provider class from the default platform
            if(context.platform().hasProvider(providerClass)){
                // get provider instance from the default platform using only Provider Class
                return context.platform().provider(providerClass);
            } else {
                // get provider instance from all available providers using only Provider Class
                return context.providers().get(providerClass);
            }
        }

        // unable to inject anything
        throw new ProviderNotFoundException("ID=" + providerId + "; CLASS=" + providerClass);
    }
}
