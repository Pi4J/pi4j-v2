package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultDigitalInputConfig.java
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

import com.pi4j.config.impl.AddressConfigBase;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.util.StringUtil;

import java.util.Map;

public class DefaultDigitalInputConfig
        extends AddressConfigBase<DigitalInputConfig>
        implements DigitalInputConfig {

    /**
     * PRIVATE CONSTRUCTOR
     */
    private DefaultDigitalInputConfig(){
        super();
    }

    // private configuration properties
    protected PullResistance pullResistance = PullResistance.OFF;

    /**
     * PRIVATE CONSTRUCTOR
     * @param properties
     */
    protected DefaultDigitalInputConfig(Map<String,String> properties){
        super(properties);

        // define default property values if any are missing (based on the required address value)
        this.id = StringUtil.setIfNullOrEmpty(this.id, "DIN-" + this.address, true);
        this.name = StringUtil.setIfNullOrEmpty(this.name, "DIN-" + this.address, true);
        this.description = StringUtil.setIfNullOrEmpty(this.description, "DIN-" + this.address, true);

        // load optional pull resistance from properties
        if(properties.containsKey(PULL_RESISTANCE_KEY)){
            this.pullResistance = PullResistance.parse(properties.get(PULL_RESISTANCE_KEY));
        }
    }

    @Override
    public PullResistance pull() {
        return this.pullResistance;
    }
}
