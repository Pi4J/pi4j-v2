package com.pi4j.config;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ConfigBase.java
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

import com.pi4j.config.exception.ConfigMissingRequiredKeyException;

import java.util.Properties;

public class ConfigBase<CONFIG_TYPE extends Config> implements Config<CONFIG_TYPE> {

    // private configuration variables
    protected String id = null;
    protected String name = null;
    protected String description = null;

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected ConfigBase(){
    }

    /**
     * PRIVATE CONSTRUCTOR
     * @param properties
     */
    protected ConfigBase(Properties properties){

        // load required 'id' property
        if(properties.containsKey(ID_KEY)){
            this.id = properties.getProperty(ID_KEY);
        } else {
            throw new ConfigMissingRequiredKeyException(ID_KEY);
        }

        // load optional 'name' property
        if(properties.containsKey(NAME_KEY))
            this.name = properties.getProperty(NAME_KEY, null);

        // load optional 'description' property
        if(properties.containsKey(DESCRIPTION_KEY))
            this.description = properties.getProperty(DESCRIPTION_KEY, null);

    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public CONFIG_TYPE name(String name){
        this.name = name;
        return (CONFIG_TYPE) this;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public CONFIG_TYPE description(String description){
        this.description = description;
        return (CONFIG_TYPE) this;
    }
}
