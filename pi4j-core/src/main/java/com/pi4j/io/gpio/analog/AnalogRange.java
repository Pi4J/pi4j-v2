package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  AnalogRange.java
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

/**
 * <p>AnalogRange interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface AnalogRange{
    /**
     * <p>min.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    Integer min();
    /**
     * <p>max.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    Integer max();

    /**
     * <p>getMin.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    default Integer getMin() {
        return this.min();
    }

    /**
     * <p>getMax.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    default Integer getMax() {
        return this.max();
    }

    /**
     * Bounds check the provided value against the range (min, max)
     *
     * @param value value to test
     * @return 'true' if the provided value is inside the defined range
     */
    default boolean validate(Integer value){
        if(value == null)
            return false;

        // perform minimum bounds checking in range
        if(min() != null)
            if(value < min()) return false;

        // perform maximum bounds checking in range
        if(max() != null)
            if(value > max()) return false;

        // success
        return true;
    }

    /**
     * <p>sanitize.</p>
     *
     * @param value a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Integer} object.
     */
    default Integer sanitize(Integer value){

        if(value == null)
            return 0;

        // perform minimum bounds checking in range
        if(min() != null)
            if(value < min()) return min();

        // perform maximum bounds checking in range
        if(max() != null)
            if(value > max()) return max();

        return value;
    }
}
