package com.pi4j.config;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AbstractConfig.java
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

import com.pi4j.config.exception.ConfigEmptyException;
import com.pi4j.config.exception.ConfigMissingPrefixException;

import java.util.Properties;

public abstract class AbstractConfig<CONFIG_TYPE extends Config> implements Config<CONFIG_TYPE> {

    @Override
    public CONFIG_TYPE load(Properties properties, String prefix){
        // ensure a prefix was provided
        if(prefix == null || prefix.isEmpty()) throw new ConfigMissingPrefixException();

        // ensure properties is not empty
        if(properties.isEmpty()) throw new ConfigEmptyException();

        return (CONFIG_TYPE) this;
    }

//    protected List<String> propertyKeys = new ArrayList<>();
//    protected Map<String, String> properties = new ConcurrentHashMap<>();
//
//    @Override
//    public void setProperties(Map<String, String> properties) {
//        this.properties.putAll(properties);
//    }
//
//    @Override
//    public Map<String, String> getProperties() {
//        return properties;
//    }
//
//    @Override
//    public String[] getPropertyKeys() {
//        return new String[0];
//    }
}
