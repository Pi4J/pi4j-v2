package com.pi4j.io.sensor.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultSensorConfigBuilder.java
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

import com.pi4j.context.Context;
import com.pi4j.io.impl.IOConfigBuilderBase;
import com.pi4j.io.sensor.SensorConfig;
import com.pi4j.io.sensor.SensorConfigBuilder;

/**
 * <p>DefaultSensorConfigBuilder class.</p>
 *
 * @author  Tom Aarts
 * @version $Id: $Id
 */
public class DefaultSensorConfigBuilder
    extends IOConfigBuilderBase<SensorConfigBuilder, SensorConfig>
    implements SensorConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultSensorConfigBuilder(Context context) {
        super(context);
    }

    /**
     * <p>newInstance.</p>
     *
     * @return a {@link com.pi4j.io.sensor.SensorConfigBuilder} object.
     */
    public static SensorConfigBuilder newInstance(Context context) {
        return new DefaultSensorConfigBuilder(context);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SensorConfigBuilder bus(Integer bus) {
        this.properties.put(SensorConfig.BUS_KEY, bus.toString());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SensorConfigBuilder address(Integer address) {
        this.properties.put(SensorConfig.ADDRESS_KEY, address.toString());
        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SensorConfig build() {
        SensorConfig config = new DefaultSensorConfig(this.properties);
        return config;
    }
}
