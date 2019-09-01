package com.pi4j.io.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultSerialConfig.java
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

/**
 * <p>DefaultSerialConfig class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultSerialConfig extends DeviceConfigBase<DefaultSerialConfig> implements IOConfig<DefaultSerialConfig> {

    int baud = Serial.DEFAULT_BAUD;

    /**
     * <p>Constructor for DefaultSerialConfig.</p>
     */
    public DefaultSerialConfig(){
        //super(Serial.DEFAULT_DEVICE);
        super();
    }

    /**
     * <p>Constructor for DefaultSerialConfig.</p>
     *
     * @param device a {@link java.lang.String} object.
     */
    public DefaultSerialConfig(String device) {
        //super(device);
    }

    /**
     * <p>Constructor for DefaultSerialConfig.</p>
     *
     * @param device a {@link java.lang.String} object.
     * @param baud a int.
     */
    public DefaultSerialConfig(String device, int baud) {
        //super(device);
        this.baud = baud;
    }

    /**
     * <p>baud.</p>
     *
     * @return a int.
     */
    public int baud() { return this.baud; };
    /**
     * <p>baud.</p>
     *
     * @param baud a int.
     * @return a {@link com.pi4j.io.serial.DefaultSerialConfig} object.
     */
    public DefaultSerialConfig baud(int baud) { this.baud = baud; return this; }

    /** {@inheritDoc} */
    @Override
    public String toString(){
        return String.format("[device=%s; baud=%d]", device(), baud());
    }

}
