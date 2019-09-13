package com.pi4j.annotation.processor.injector;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  PlatformInjector.java
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
import com.pi4j.platform.Platform;

import java.lang.reflect.Field;

/**
 * <p>PlatformInjector class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class PlatformInjector implements InjectorProcessor<Platform> {

    /** {@inheritDoc} */
    @Override
    public Class<Platform> getTargetType() { return Platform.class; }

    /** {@inheritDoc} */
    @Override
    public Platform process(Context context, Object instance, Inject annotation, Field field) throws Exception {

        // get optional user specified platform ID from the annotation 'value' attribute
        String platformId = (annotation.value() != null && !annotation.value().isEmpty()) ? annotation.value().trim() : null;

        // get platform class from explict annotated 'type' attribute or inferred from field type
        Class platformClass = (annotation.type() != null && annotation.type() != void.class &&
                Platform.class.isAssignableFrom(annotation.type())) ? annotation.type() : field.getType();

        if(platformId != null && platformClass != null){
            // return platform by unique ID and type class
            return context.platforms().get(platformId, platformClass);
        }
        else if(platformId != null){
            // return platform by unique ID
            return context.platforms().get(platformId);
        }
        else if(platformClass != null){
            // return platform by unique ID
            return context.platforms().get(platformClass);
        }

        // return default platform instance
        return context.platforms().defaultPlatform();
    }
}
