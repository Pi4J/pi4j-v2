package com.pi4j.io.serial.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultPwmConfigBuilder.java
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

import com.pi4j.config.impl.DeviceConfigBuilderBase;
import com.pi4j.io.serial.*;

public class DefaultSerialConfigBuilder
        extends DeviceConfigBuilderBase<SerialConfigBuilder, SerialConfig>
        implements SerialConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultSerialConfigBuilder(){
        super();
    }

    public static SerialConfigBuilder newInstance() {
        return new DefaultSerialConfigBuilder();
    }

    @Override
    public SerialConfigBuilder baud(Integer rate) {
        this.properties.put(SerialConfig.BAUD_KEY, rate.toString());
        return this;
    }

    @Override
    public SerialConfigBuilder dataBits(DataBits bits) {
        this.properties.put(SerialConfig.DATA_BITS_KEY, Integer.toString(bits.value()));
        return this;
    }

    @Override
    public SerialConfigBuilder parity(Parity parity) {
        this.properties.put(SerialConfig.PARITY_KEY, parity.name());
        return this;
    }

    @Override
    public SerialConfigBuilder stopBits(StopBits bits) {
        this.properties.put(SerialConfig.STOP_BITS_KEY, Integer.toString(bits.value()));
        return this;
    }

    @Override
    public SerialConfigBuilder flowControl(FlowControl control) {
        this.properties.put(SerialConfig.FLOW_CONTROL_KEY, control.name());
        return this;
    }

    @Override
    public SerialConfig build() {
        SerialConfig config = new DefaultSerialConfig(this.properties);
        return config;
    }
}
