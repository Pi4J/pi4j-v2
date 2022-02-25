package com.pi4j.io.sensor;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  SensorBase.java
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

import com.pi4j.io.IOBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Abstract SensorBase class.</p>
 *
 * @author Tom Aarts
 * @version $Id: $Id
 */
public abstract class SensorBase extends IOBase<Sensor, SensorConfig, SensorProvider> implements Sensor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>Constructor for SensorBase.</p>
     *
     * @param provider a {@link com.pi4j.io.sensor.SensorProvider} object.
     * @param config   a {@link com.pi4j.io.sensor.SensorConfig} object.
     */
    public SensorBase(SensorProvider provider, SensorConfig config) {
        super(provider, config);
    }


}
