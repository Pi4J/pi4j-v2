package com.pi4j.platform;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Platforms.java
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

import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.context.Context;
import com.pi4j.platform.exception.PlatformNotFoundException;
import com.pi4j.platform.exception.PlatformTerminateException;
import com.pi4j.platform.exception.PlatformsNotInitialized;

import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public interface Platforms extends Describable {

    /**
     * Get all platforms
     * @return
     */
    Map<String, Platform> all();

    /**
     * Get all platforms of a specified io class/interface.
     *
     * @param platformClass
     * @param <T>
     * @return
     * @throws com.pi4j.platform.exception.PlatformException
     */
    <T extends Platform> Map<String, T> all(Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException;

    boolean exists(String platformId) throws PlatformsNotInitialized;
    <T extends Platform> boolean exists(String platformId, Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException;

    Platform get(String platformId) throws PlatformsNotInitialized, PlatformNotFoundException;
    <T extends Platform> T get(String platformId, Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException;
    <T extends Platform> T get(Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException;

    void shutdown(Context context) throws PlatformsNotInitialized, PlatformTerminateException;

    <T extends Platform> T defaultPlatform();
    default boolean hasDefault(){
        return defaultPlatform() != null;
    }
    default <T extends Platform> T  getDefault(){
        return defaultPlatform();
    }

    // DEFAULT METHODS
    default Map<String, Platform> getAll() { return all(); }
    default <T extends Platform> Map<String, T> getAll(Class<T> platformClass) throws PlatformNotFoundException, PlatformsNotInitialized {
        return all(platformClass);
    }

    default Descriptor describe() {
        var platforms = all();

        Descriptor descriptor = Descriptor.create()
                .category("PLATFORMS")
                .name("Pi4J Runtime Platforms")
                .quantity((platforms == null) ? 0 : platforms.size())
                .type(this.getClass());

        if(platforms != null && !platforms.isEmpty()) {
            platforms.forEach((id, platform) -> {
                descriptor.add(platform.describe());
            });

        }
        return descriptor;
    }
}
