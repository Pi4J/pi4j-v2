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
import com.pi4j.platform.exception.*;

import java.util.Arrays;
import java.util.Collection;
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
     * Get all bindings
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

    <T extends Platform> Platforms add(Collection<T> platform) throws PlatformsNotInitialized, PlatformAlreadyExistsException, PlatformInitializeException;
    <T extends Platform> void replace(T platform) throws PlatformsNotInitialized, PlatformNotFoundException, PlatformTerminateException, PlatformInitializeException;
    <T extends Platform> void remove(String platformId) throws PlatformsNotInitialized, PlatformNotFoundException, PlatformTerminateException;

    void initialize(Context context, boolean autoDetect) throws PlatformsAlreadyInitialized, PlatformAlreadyExistsException, PlatformInitializeException, PlatformsNotInitialized;
    void shutdown(Context context) throws PlatformsNotInitialized, PlatformTerminateException;

    Platform defaultPlatform();
    Platform defaultPlatform(String platformId) throws PlatformNotFoundException;

    default <T extends Platform> Platforms add(T ... platform) throws PlatformAlreadyExistsException, PlatformInitializeException, PlatformsNotInitialized {
        return add(Arrays.asList(platform));
    }


    default boolean hasDefault(){
        return defaultPlatform() != null;
    }


    default Platform setDefault(String platformId) throws PlatformNotFoundException {
        return defaultPlatform(platformId);
    }

    default Platform getDefault(){
        return defaultPlatform();
    }

    // DEFAULT METHODS
    default Map<String, Platform> getAll() { return all(); }
    default <T extends Platform> Map<String, T> getAll(Class<T> platformClass) throws PlatformNotFoundException, PlatformsNotInitialized {
        return all(platformClass);
    }

    default Descriptor describe() {
        var bindings = all();

        Descriptor descriptor = Descriptor.create()
                .category("PLATFORMS")
                .name("Pi4J Runtime Platforms")
                .quantity((bindings == null) ? 0 : bindings.size())
                .type(this.getClass());

        if(bindings != null && !bindings.isEmpty()) {
            bindings.forEach((id, binding) -> {
                descriptor.add(binding.describe());
            });

        }
        return descriptor;
    }
}
