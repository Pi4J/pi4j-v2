package com.pi4j.io.sensor.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultSensorConfig.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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

import com.pi4j.io.impl.IOConfigBase;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.util.StringUtil;

import java.util.Map;

/**
 * <p>DefaultSensorConfig class.</p>
 *
 * @author  Tom Aarts
 * @version $Id: $Id
 */
// extends IOAddressConfigBase<SensorConfig>
//
public class DefaultSensorConfig
    extends IOConfigBase<SensorConfig>
    implements SensorConfig {

    // private configuration properties

    protected final Integer bus;
    protected final Integer address;

    /**
     * PRIVATE CONSTRUCTOR
     *
     * @param properties a {@link java.util.Map} object.
     */
    protected DefaultSensorConfig(Map<String, String> properties) {
        super(properties);


        if (properties.containsKey(BUS_KEY)) {
            this.bus = StringUtil.parseInteger(properties.get(BUS_KEY), Sensor.DEFAULT_BUS);
        } else {
            this.bus = Sensor.DEFAULT_BUS;
        }


        if (properties.containsKey(SensorConfig.ADDRESS_KEY)) {
            this.address = StringUtil.parseInteger(properties.get(SensorConfig.ADDRESS_KEY), Sensor.DEFAULT_ADDRESS);
        } else {
            this.address = Sensor.DEFAULT_ADDRESS;
        }

        // define default property values if any are missing (based on the required address value)
        this.id = StringUtil.setIfNullOrEmpty(this.id, "SENSOR-" + this.address(), true);
        this.name = StringUtil.setIfNullOrEmpty(this.name, "SENSOR-" + this.address(), true);
        this.description = StringUtil.setIfNullOrEmpty(this.description, "SENSOR-" + this.address(), true);
    }


    @Override
    public Integer bus() {
        return this.bus;
    }

    @Override
    public Integer address() {
        return this.address;
    }

    @Override
    public Integer getAddress() {
        return this.address;
    }
}
