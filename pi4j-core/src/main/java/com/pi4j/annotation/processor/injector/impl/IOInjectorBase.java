package com.pi4j.annotation.processor.injector.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  IOInjectorBase.java
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
import com.pi4j.annotation.processor.injector.InjectorProcessor;
import com.pi4j.context.Context;
import com.pi4j.io.IO;
import com.pi4j.util.StringUtil;

import java.lang.reflect.Field;

/**
 * <p>Abstract IOInjectorBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class IOInjectorBase<T extends IO> implements InjectorProcessor<T> {

    /** {@inheritDoc} */
    @Override
    public T process(Context context, Object instance, Inject annotation, Field field) throws Exception {
        // get target I/O instance from the Pi4J registry
        String id = annotation.value();  // use the annotation value if one was provided; else use the field's name
        if(StringUtil.isNullOrEmpty(id)){
            id = field.getName();
        }
        return context.registry().get(id, getTargetType());
    }
}
