package com.pi4j.io.gpio.analog;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  AnalogOutputProviderBase.java
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
 * <p>Abstract AnalogOutputProviderBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class AnalogOutputProviderBase
        extends AnalogProviderBase<AnalogOutputProvider, AnalogOutput, AnalogOutputConfig>
        implements AnalogOutputProvider {

    /**
     * <p>Constructor for AnalogOutputProviderBase.</p>
     */
    public AnalogOutputProviderBase(){
    }

    /**
     * <p>Constructor for AnalogOutputProviderBase.</p>
     *
     * @param id a {@link java.lang.String} object.
     */
    public AnalogOutputProviderBase(String id){
        this();
        this.id = id;
    }

    /**
     * <p>Constructor for AnalogOutputProviderBase.</p>
     *
     * @param id a {@link java.lang.String} object.
     * @param name a {@link java.lang.String} object.
     */
    public AnalogOutputProviderBase(String id, String name){
        this(id);
        this.name = name;
    }
}
