package com.pi4j.config;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AbstractNameConfig.java
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

import java.util.Properties;

public abstract class AbstractNameConfig<CONFIG_TYPE extends Config>
        extends AbstractConfig<CONFIG_TYPE>
        implements NameConfig<CONFIG_TYPE> {

    private String name = null;

    public AbstractNameConfig(){
        this.name(null);
    }
    public AbstractNameConfig(String name){
        this.name = name;
    }

    public String name() { return this.name; };
    public CONFIG_TYPE name(String name) { this.name = name; return (CONFIG_TYPE)this; }

    @Override
    public CONFIG_TYPE load(Properties properties, String prefix){

        // ensure properties is not empty
        super.load(properties, prefix);

        // determine if any optional configuration properties are present
        if(properties.containsKey(prefix + ".name")){
            // set name property
            String name  = properties.get(prefix + ".name").toString();
            this.name(name);
        }

        return (CONFIG_TYPE) this;
    }
}
