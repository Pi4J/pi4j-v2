package com.pi4j.platform.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultPlatforms.java
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

import com.pi4j.binding.exception.BindingException;
import com.pi4j.context.Context;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.platform.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DefaultPlatforms implements Platforms {

    protected boolean initialized = false;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Context context = null;
    protected Platform defaultPlatform = null;

    // all detected/available providers
    private Map<String, Platform> platforms = new ConcurrentHashMap<>();

    // static singleton instance
    private static Platforms singleton = null;
    public static Platforms singleton(Context context){
        if(singleton == null){
            singleton = new DefaultPlatforms(context);
        }
        return singleton;
    }

    // private constructor
    private DefaultPlatforms(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    protected void initializePlatform(Platform platform) throws PlatformInitializeException {

        // ensure the platform object is valid
        if(platform == null) return;

        // attempt to initialize the binding instance
        try {
            logger.trace("initializing platform [id={}; name={}; class={}]",
                    platform.id(), platform.name(), platform.getClass().getName());
            platform.initialize(context);
        } catch (Exception e) {
            logger.error("unable to 'initialize()' platform: [id={}; name={}]; {}",
                    platform.id(), platform.name(), e.getMessage());
            logger.error(e.getMessage(), e);
            throw new PlatformInitializeException(platform.id(), e);
        }
    }

    protected void shutdownPlatform(Platform platform) throws PlatformTerminateException {

        // ensure the platform object is valid
        if(platform == null) return;

        // attempt to shutdown the platform instance
        try {
            logger.trace("terminating platform [id={}; name={}; class={}]",
                    platform.id(), platform.name(), platform.getClass().getName());
            platform.shutdown(context);
        } catch (Exception e) {
            logger.error("unable to 'shutdown()' platform: [id={}; name={}]; {}",
                    platform.id(), platform.name(), e.getMessage());
            logger.error(e.getMessage(), e);
            throw new PlatformTerminateException(platform.id(), e);
        }
    }

    /**
     * Get all bindings
     * @return
     */
    @Override
    public Map<String, Platform> all(){
        return Collections.unmodifiableMap(this.platforms);
    }

    /**
     * Get all bindings of a specified binding class type.
     *
     * @param platformClass
     * @param <T>
     * @return
     * @throws BindingException
     */
    @Override
    public <T extends Platform> Map<String, T> all(Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        // create a map <platform-id, platform-instance> of platforms that extend of the given platform class/interface
        var result = new ConcurrentHashMap<String, T>();
        platforms.values().stream().filter(platformClass::isInstance).forEach(p -> {
            result.put(p.id(), platformClass.cast(p));
        });

        if(result.size() <= 0) throw new PlatformNotFoundException(platformClass);
        return Collections.unmodifiableMap(result);
    }

    @Override
    public boolean exists(String platformId) throws PlatformsNotInitialized {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        // return true if the managed platform map contains the given platform-id
        if(platforms.containsKey(platformId)){
            return true;
        }
        return false;
    }

    @Override
    public <T extends Platform> boolean exists(String platformId, Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        // return true if the managed platform map contains the given platform-id and platform-class
        var subset = all(platformClass);
        if(subset.containsKey(platformId)){
            return true;
        }
        return false;
    }

    @Override
    public Platform get(String platformId) throws PlatformsNotInitialized, PlatformNotFoundException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        // return the platform instance from the managed platform map that contains the given platform-id
        if(platforms.containsKey(platformId)){
            return platforms.get(platformId);
        }
        throw new PlatformNotFoundException(platformId);
    }

    @Override
    public <T extends Platform> T get(String platformId, Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        // return the platform instance from the managed platform map that contains the given platform-id and platform-class
        var subset = all(platformClass);
        if(subset.containsKey(platformId)){
            return (T)subset.get(platformId);
        }
        throw new PlatformNotFoundException(platformId);
    }

    @Override
    public <T extends Platform> T get(Class<T> platformClass) throws PlatformsNotInitialized, PlatformNotFoundException{

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        // return the platform instance from the managed platform map that contains the given platform-id and platform-class
        var subset = all(platformClass);
        if(subset.isEmpty()){
            throw new PlatformNotFoundException(platformClass);
        }
        // return first instance found
        return (T)subset.values().iterator().next();
    }

    @Override
    public <T extends Platform > Platforms add(Collection<T> platform) throws PlatformsNotInitialized, PlatformAlreadyExistsException, PlatformInitializeException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        logger.trace("invoked 'add()' platform [count={}]", platform.size());

        // iterate the given platform array
        for(var platformInstance : platform) {

            logger.trace("adding platform to managed platform map [id={}; name={}; class={}]",
                    platformInstance.id(), platformInstance.name(), platformInstance.getClass().getName());

            // perform check to see if this platform reports as enabled/supported
            if(!platformInstance.enabled(this.context)){
                logger.trace("not adding platform [id={}; name={}; class={}] as it reports as [DISABLED] on this system;",
                        platformInstance.id(), platformInstance.name(), platformInstance.getClass().getName());
                continue;
            }

            // ensure requested binding id does not already exist in the managed set
            if (exists(platformInstance.id())) {
                throw new PlatformAlreadyExistsException(platformInstance.id());
            }

            // attempt to initialize the new platform instance
            initializePlatform(platformInstance);

            // add new platform to managed set
            platforms.put(platformInstance.id(), platformInstance);
            logger.debug("added platform to managed platform map [id={}; name={}; class={}]",
                    platformInstance.id(), platformInstance.name(), platformInstance.getClass().getName());

            // set system wide default platform
            if(defaultPlatform == null) {
                this.defaultPlatform = platformInstance;

                logger.debug("default platform is now [id={}; name={}; class={}]",
                        platformInstance.id(), platformInstance.name(), platformInstance.getClass().getName());

            }
            else{
                this.defaultPlatform = platformInstance;
                if(platformInstance.weight() > this.defaultPlatform.weight()){
                    logger.debug("default platform is now [id={}; name={}; class={}; weight={}]",
                            platformInstance.id(), platformInstance.name(), platformInstance.getClass().getName(), platformInstance.weight());
                }
            }
        }

        return this;
    }

    @Override
    public <T extends Platform> void replace(T platform) throws PlatformsNotInitialized, PlatformNotFoundException, PlatformTerminateException, PlatformInitializeException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        logger.trace("invoked 'replace()' platform [id={}] with [{}]", platform.id(), platform);

        // ensure requested platform id does exist in the managed set
        if(exists(platform.id())){
            throw new PlatformNotFoundException(platform.id());
        }

        // get existing platform instance
        Platform oldPlatform = platforms.get(platform.id());

        // attempt to shutdown old platform instance
        shutdownPlatform(oldPlatform);

        // attempt to initialize the new platform instance
        initializePlatform(platform);

        // add new platform to managed set
        platforms.put(platform.id(), platform);

        logger.debug("replaced platform in managed platform map [id={}; name={}; class={}]",
                platform.id(), platform.name(), platform.getClass().getName());
    }

    @Override
    public <T extends Platform> void remove(String platformId) throws PlatformsNotInitialized, PlatformNotFoundException, PlatformTerminateException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        logger.trace("invoked 'remove() platform' [id={}]", platformId);

        // ensure requested platform id does exist in the managed set
        if(!platforms.containsKey(platformId)){
            logger.warn("unable to remove platform [id={}]; id not found in managed platform map.", platformId);
            throw new PlatformNotFoundException(platformId);
        }

        // get existing platform instance
        Platform oldPlatform = platforms.get(platformId);

        // attempt to shutdown old platform instance
        shutdownPlatform(oldPlatform);

        // remove platform from managed set
        var removedPlatform = platforms.remove(platformId);
        if(removedPlatform != null) {
            logger.debug("removed platform from managed platform map [id={}; name={}; class={}]",
                    removedPlatform.id(), removedPlatform.name(), removedPlatform.getClass().getName());
        }
    }

    @Override
    public void initialize(Context context, boolean autoDetect) throws PlatformsAlreadyInitialized, PlatformAlreadyExistsException, PlatformInitializeException, PlatformsNotInitialized {

        // ensure bindings have not been initialized
        if(initialized) throw new PlatformsAlreadyInitialized();

        // set initialized flag
        initialized = true;
        logger.trace("invoked 'initialize()' [autoDetect={}]", autoDetect);

        // process auto-detect?
        if(autoDetect) {
            logger.trace("auto-detecting Pi4J platforms from the classpath.");

            // detect available platforms by scanning the classpath looking for service io instances
            ServiceLoader<Platform> detectedPlatforms = ServiceLoader.load(Platform.class);
            for (Platform platformInstance : detectedPlatforms) {
                if (platformInstance != null) {
                    logger.trace("auto-detected platform: [id={}; name={}; class={}]",
                            platformInstance.id(), platformInstance.name(), platformInstance.getClass().getName());
//                    try {
                        // add platform instance
                        add(platformInstance);
//                    } catch (PlatformException ex) {
//                        // unable to initialize this platform instance
//                        logger.error("unable to 'initialize()' auto-detected platform: [id={}; name={}]; {}",
//                                platformInstance.id(), platformInstance.name(), ex.getMessage());
//                        continue;
//                    }
                }
            }

            // if no platforms found, throw exception
            logger.debug("auto-detected and loaded [{}] platforms", platforms.size());
        }
    }

    @Override
    public void shutdown(Context context) throws PlatformsNotInitialized, PlatformTerminateException {

        // ensure platforms have been initialized
        if(!initialized) throw new PlatformsNotInitialized();

        logger.trace("invoked 'shutdown();'");

        PlatformTerminateException platformException = null;
        // iterate over all providers and invoke the shutdown method on each
        var platformIds = platforms.keySet();
        for(var platformId : platformIds){
            try {
                remove(platformId);
            } catch (PlatformException e) {
                platformException = new PlatformTerminateException(platformId, e);
            }
        }

        // clear all platforms
        platforms.clear();

        // set initialized flag
        initialized = false;

        // throw exception if
        if(platformException != null) throw platformException;
    }

    @Override
    public Platform defaultPlatform() {
        return this.defaultPlatform;
    }

    @Override
    public Platform defaultPlatform(String platformId) throws PlatformNotFoundException {
        if(platforms.containsKey(platformId)){
            this.defaultPlatform = platforms.get(platformId);
            return this.defaultPlatform;
        }
        throw new PlatformNotFoundException(platformId);
    }
}