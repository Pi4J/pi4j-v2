package com.pi4j.provider.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  RuntimeProviders.java
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

import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;

import java.util.Collection;

/**
 * <p>
 * This class provides static methods to configure the Pi4J library's default
 * platform.  Pi4J supports the following platforms:  RaspberryPi, BananaPi, BananaPro, Odroid.
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface RuntimeProviders extends Providers {
    /**
     * <p>shutdown.</p>
     *
     * @return a {@link com.pi4j.provider.impl.RuntimeProviders} object.
     * @throws com.pi4j.exception.ShutdownException if any.
     */
    RuntimeProviders shutdown() throws ShutdownException;
    /**
     * <p>initialize.</p>
     *
     * @param providers a {@link java.util.Collection} object.
     * @return a {@link com.pi4j.provider.impl.RuntimeProviders} object.
     * @throws com.pi4j.exception.InitializeException if any.
     */
    RuntimeProviders initialize(Collection<Provider> providers) throws InitializeException;
}
