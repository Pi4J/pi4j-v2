package com.pi4j.annotation.processor.injector;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ProviderGroupInjector.java
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
import com.pi4j.provider.ProviderGroup;
import com.pi4j.io.IOType;
import com.pi4j.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ProviderGroupInjector implements InjectorProcessor<ProviderGroup> {

    @Override
    public Class<ProviderGroup> getTargetType() { return ProviderGroup.class; }

    @Override
    public ProviderGroup process(Context context, Object instance, Inject annotation, Field field) throws Exception {

        // <<1>> inject instance by user defined ID property
        if(StringUtil.isNotNullOrEmpty(annotation.value())){
            String id = annotation.value().trim();
            for(IOType providerType : IOType.values()){
                if(id.equalsIgnoreCase(providerType.name())){
                    return new ProviderGroup<Provider>(context.providers(), providerType);
                }
            }
            return null;
        }

        // <<2>> alternatively, inject by user defined class type property
        if(annotation.type() != null && annotation.type() != void.class && Provider.class.isAssignableFrom(annotation.type())){
            return new ProviderGroup<Provider>(context.providers(), IOType.getByProviderClass(annotation.type()));
        }

        // <<3>> alternatively, inject by inferred generic parameter type ... ProviderGroup<~~~PARAMETER-TYPE~~~>
        if(field.getGenericType() != null) {
            Type genericParameterType = field.getGenericType();
            if( genericParameterType instanceof ParameterizedType) {
                Type[] parameters = ((ParameterizedType)genericParameterType).getActualTypeArguments();
                if(parameters != null && parameters.length > 0) {
                    return new ProviderGroup<Provider>(context.providers(), IOType.getByProviderClass((Class<? extends Provider>) parameters[0]));
                }
            }
        }

        // return no provider group
        return null;
    }
}
