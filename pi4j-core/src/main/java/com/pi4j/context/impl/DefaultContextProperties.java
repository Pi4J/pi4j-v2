package com.pi4j.context.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultContextProperties.java
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

import com.pi4j.context.ContextProperties;
import com.pi4j.runtime.RuntimeProperties;

import java.util.Map;

/**
 * <p>DefaultContextProperties class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultContextProperties implements ContextProperties {

    private final RuntimeProperties properties;

    /**
     * <p>newInstance.</p>
     *
     * @param properties a {@link com.pi4j.runtime.RuntimeProperties} object.
     * @return a {@link com.pi4j.context.ContextProperties} object.
     */
    public static ContextProperties newInstance(RuntimeProperties properties){
        return new DefaultContextProperties(properties);
    }

    private DefaultContextProperties(RuntimeProperties properties){
        this.properties = properties;
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(String key) {
        return this.properties.has(key);
    }

    /** {@inheritDoc} */
    @Override
    public String get(String key) {
        return this.properties.get(key);
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, String> all() {
        return this.properties.all();
    }

    /** {@inheritDoc} */
    @Override
    public int count() {
        return this.properties.count();
    }
}
