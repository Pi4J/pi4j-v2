package com.pi4j.io.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  SerialConfig.java
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

import com.pi4j.config.impl.DeviceConfigBase;
import com.pi4j.io.IOConfig;

public class SerialConfig extends DeviceConfigBase<SerialConfig> implements IOConfig<SerialConfig> {

    int baud = Serial.DEFAULT_BAUD;

    public SerialConfig(){
        //super(Serial.DEFAULT_DEVICE);
        super();
    }

    public SerialConfig(String device) {
        //super(device);
    }

    public SerialConfig(String device, int baud) {
        //super(device);
        this.baud = baud;
    }

    public int baud() { return this.baud; };
    public SerialConfig baud(int baud) { this.baud = baud; return this; }

    @Override
    public String toString(){
        return String.format("[device=%s; baud=%d]", device(), baud());
    }

}
