package com.pi4j.platform;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Platforms.java
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

import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.platform.exception.PlatformException;
import com.pi4j.platform.exception.PlatformNotFoundException;
import com.pi4j.platform.exception.PlatformTypeException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Platforms</p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Platforms extends Describable {

    /**
     * <p>all.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    Map<String, Platform> all();
    /**
     * <p>exists.</p>
     *
     * @param platformId a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean exists(String platformId);
    /**
     * <p>get.</p>
     *
     * @param platformId a {@link java.lang.String} object.
     * @return a {@link com.pi4j.platform.Platform} object.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     */
    Platform get(String platformId) throws PlatformNotFoundException;
    /**
     * <p>defaultPlatform.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     */
    <T extends Platform> T defaultPlatform();

    // DEFAULT METHODS

    /**
     * <p>exists.</p>
     *
     * @param platformId a {@link java.lang.String} object.
     * @param platformClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a boolean.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     */
    default <T extends Platform> boolean exists(String platformId, Class<T> platformClass) throws PlatformNotFoundException {
        // determine if the requested platform exists by ID and CLASS TYPE
        try {
            return get(platformId, platformClass) != null;
        } catch (PlatformException e) {
            return false;
        }
    }

    /**
     * <p>hasDefault.</p>
     *
     * @return a boolean.
     */
    default boolean hasDefault(){
        return defaultPlatform() != null;
    }

    /**
     * <p>getDefault.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     */
    default <T extends Platform> T  getDefault(){
        return defaultPlatform();
    }

    /**
     * <p>get.</p>
     *
     * @param platformId a {@link java.lang.String} object.
     * @param platformClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     * @throws com.pi4j.platform.exception.PlatformTypeException if any.
     */
    default <T extends Platform> T get(String platformId, Class<T> platformClass) throws PlatformNotFoundException, PlatformTypeException {
        // return the platform instance from the managed platform map that contains the given platform-id and platform-class
        var platform = get(platformId);
        if(platformClass == null) {
            return (T) platform;
        }
        if (platformClass.isAssignableFrom(platform.getClass())) {
            return (T) platform;
        }
        throw new PlatformTypeException(platformId, platformClass);
    }

    default boolean exists(Class<? extends Platform> platformClass) throws PlatformNotFoundException {
        // determine if the requested platform exists by ID and CLASS TYPE
        try {
            return get(platformClass) != null;
        } catch (PlatformException e) {
            return false;
        }
    }

    /**
     * <p>get.</p>
     *
     * @param platformClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     */
    default <T extends Platform> T get(Class<T> platformClass) throws PlatformNotFoundException{
        // return the platform instance from the managed platform map that contains the given platform-id and platform-class
        var subset = all(platformClass);
        if(subset.isEmpty()){
            throw new PlatformNotFoundException(platformClass);
        }
        // return first instance found
        return (T)subset.values().iterator().next();
    }

    /**
     * Get all platforms of a specified io class/interface.
     *
     * @param platformClass a {@link java.lang.Class} object.
     * @param <T> platforms objects extending the {@link com.pi4j.platform.Platform} interface
     * @return a {@link java.util.Map} object.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     */
    default <T extends Platform> Map<String, T> all(Class<T> platformClass) throws PlatformNotFoundException {
        // create a map <platform-id, platform-instance> of platforms that extend of the given platform class/interface
        var result = new ConcurrentHashMap<String, T>();
        all().values().stream().filter(platformClass::isInstance).forEach(p -> {
            result.put(p.id(), platformClass.cast(p));
        });

        if(result.size() <= 0) throw new PlatformNotFoundException(platformClass);
        return Collections.unmodifiableMap(result);
    }


    /**
     * <p>getAll.</p>
     *
     * @return a {@link java.util.Map} object.
     */
    default Map<String, Platform> getAll() { return all(); }
    /**
     * <p>getAll.</p>
     *
     * @param platformClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link java.util.Map} object.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     */
    default <T extends Platform> Map<String, T> getAll(Class<T> platformClass) throws PlatformNotFoundException {
        return all(platformClass);
    }

    /**
     * <p>describe.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
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
