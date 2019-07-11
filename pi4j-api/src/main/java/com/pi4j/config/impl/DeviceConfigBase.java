package com.pi4j.config.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AbstractDeviceConfig.java
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

import com.pi4j.config.DeviceConfig;
import com.pi4j.config.exception.ConfigMissingRequiredKeyException;

import java.util.Properties;

public abstract class DeviceConfigBase<CONFIG_TYPE extends DeviceConfig<CONFIG_TYPE>>
        extends AddressConfigBase<CONFIG_TYPE>
        implements DeviceConfig<CONFIG_TYPE> {

    // private configuration variables
    protected String device =  null;

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DeviceConfigBase(){
    }

    /**
     * PRIVATE CONSTRUCTOR
     * @param properties
     */
    protected DeviceConfigBase(Properties properties){
        super(properties);

        // load address property
        if(properties.containsKey(DEVICE_KEY)){
            this.device = properties.getProperty(DEVICE_KEY);
        }
    }

    public String device() { return this.device; };

    @Override
    public CONFIG_TYPE load(Properties properties, String prefix){

        // ensure properties is not empty
        super.load(properties, prefix);

        // ensure required configuration properties are present
        if(!properties.containsKey(prefix + "." + DEVICE_KEY))
            throw new ConfigMissingRequiredKeyException(prefix + "." + DEVICE_KEY);

        // set local configuration properties
        if(properties.containsKey(prefix + "." + DEVICE_KEY))
            this.device = properties.get(prefix + "." + DEVICE_KEY).toString();

        return (CONFIG_TYPE) this;
    }
}
