package com.pi4j.io.sensor;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Sensor.java
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
import com.pi4j.io.IO;

/**
 * <p>Sensor interface.</p>
 *
 * @author Tom Aarts
 * @version $Id: $Id
 */
public interface Sensor extends IO<Sensor, SensorConfig, SensorProvider> {


    int DEFAULT_BUS = 1;
    int DEFAULT_ADDRESS = 0x76;

    /**
     * <p>newConfigBuilder.</p>
     *
     * @param context {@link Context}
     * @return a {@link com.pi4j.io.sensor.SensorConfigBuilder} object.
     */
    static SensorConfigBuilder newConfigBuilder(Context context) {
        return SensorConfigBuilder.newInstance(context);
    }


    /**
     * Perform any/all actions to reset the sensor
     */
    default void resetSensor() {
    }

    /**
     * Perform any/all actions to initialize  the sensor
     */
    default void initSensor() {
    }

    /*** @return temperature in centigrade
     */
    default double temperatureC() {
        return 0;
    }

    /** @return temperature in fahrenheit
     */
    default double temperatureF() {
        return 0;
    }


    /**
     * @return presure in Pa units
     */
    default double pressurePa() {
        return 0;
    }

    /**
     * @return pressure in Inches Mercury
     */
    default double pressureIn() {
        return 0;
    }

    /**
     * @return pressure in millibar
     */
    default double pressureMb() {
        return 0;
    }

}
