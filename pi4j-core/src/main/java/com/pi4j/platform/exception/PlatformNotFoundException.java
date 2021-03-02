package com.pi4j.platform.exception;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  PlatformNotFoundException.java
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


import com.pi4j.platform.Platform;

/**
 * <p>
 * This exception is thrown if a platform assignment is attempted when a
 * platform instance has already been assigned.
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class PlatformNotFoundException extends PlatformException {

    /**
     * Default Constructor
     */
    public PlatformNotFoundException(){
        super("Pi4J platform could not be detected.  Please include the 'platform' JAR in the classpath for your io.");
    }

    /**
     * Alternate Constructor
     *
     * @param platformId a {@link java.lang.String} object.
     */
    public PlatformNotFoundException(String platformId){
        super("Pi4J platform [" + platformId + "] could not be found.  Please include the 'platform' JAR in the classpath for your io.");
    }

    /**
     * Alternate Constructor
     *
     * @param platformClass a {@link java.lang.Class} object.
     */
    public PlatformNotFoundException(Class<? extends Platform> platformClass){
        super("Pi4J platform for [" + platformClass + "] could not be found.  Please include a 'platform' JAR in the classpath for this class type.");
    }

}
