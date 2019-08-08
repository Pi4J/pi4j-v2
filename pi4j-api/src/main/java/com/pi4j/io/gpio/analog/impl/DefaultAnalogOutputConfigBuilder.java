package com.pi4j.io.gpio.analog.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultAnalogOutputConfigBuilder.java
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

import com.pi4j.io.gpio.analog.AnalogOutputConfig;
import com.pi4j.io.gpio.analog.AnalogOutputConfigBuilder;

public class DefaultAnalogOutputConfigBuilder
        extends AnalogConfigBuilderBase<AnalogOutputConfigBuilder, AnalogOutputConfig>
        implements AnalogOutputConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    private DefaultAnalogOutputConfigBuilder(){
        super();
    }

    public static AnalogOutputConfigBuilder newInstance() {
        return new DefaultAnalogOutputConfigBuilder();
    }

    @Override
    public AnalogOutputConfigBuilder shutdown(Integer value) {
        this.properties.put(AnalogOutputConfig.SHUTDOWN_VALUE_KEY, value.toString());
        return this;
    }

    @Override
    public AnalogOutputConfigBuilder initial(Integer value) {
        this.properties.put(AnalogOutputConfig.INITIAL_VALUE_KEY, value.toString());
        return this;
    }

    @Override
    public AnalogOutputConfig build() {
        AnalogOutputConfig config = new DefaultAnalogOutputConfig(properties);
        return config;
    }

    @Override
    public AnalogOutputConfigBuilder step(Integer value) {
        this.properties.put(AnalogOutputConfig.STEP_VALUE_KEY, value.toString());
        return this;
    }
}
