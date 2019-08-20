package com.pi4j.context;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ContextProperties.java
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

import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.util.StringUtil;

import java.util.Map;

public interface ContextProperties extends Describable {

    boolean has(String key);
    String get(String key);

    Map<String,String> all();
    int count();

    default String get(String key, String defaultValue){
        String value = get(key);
        if(StringUtil.isNotNullOrEmpty(value)) {
            return value;
        }
        return defaultValue;
    }

    default boolean exists(String key){
        return has(key);
    }

    default Integer getInteger(String key){
        return getInteger(key, null);
    }

    default Integer getInteger(String key, Integer defaultValue){
        if(has(key)) return StringUtil.parseInteger(get(key), defaultValue);
        return defaultValue;
    }

    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create()
                .category("PROPERTIES")
                .name("Properties")
                .quantity(this.count())
                .type(this.getClass());

        for(Map.Entry e : this.all().entrySet()){
            descriptor.add(
            Descriptor.create()
                    .name(e.getKey().toString())
                    .category("PROPERTY")
                    .description(e.getValue().toString())
            );
        }
        return descriptor;
    }
}
