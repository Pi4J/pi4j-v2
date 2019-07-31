package com.pi4j;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Pi4JConfig.java
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

import com.pi4j.binding.Binding;
import com.pi4j.impl.DefaultPi4JConfig;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;

import java.util.Collection;

public interface Pi4JConfig {

    static Pi4JConfig create(){
        return DefaultPi4JConfig.instance();
    }

    Pi4JConfig add(Binding... binding);
    Pi4JConfig add(Platform... platform);
    Pi4JConfig add(Provider... provider);

    Collection<Binding> bindings();
    default Collection<Binding> getBindings(){
        return bindings();
    }

    Collection<Platform> platforms();
    default Collection<Platform> getPlatforms(){
        return platforms();
    }

    Collection<Provider> providers();
    default Collection<Provider> getProviders(){
        return providers();
    }

    Pi4JConfig addDefaultPlatform(Platform platform);

    Pi4JConfig defaultPlatform(String platformId);
    default Pi4JConfig defaultPlatform(Platform platform){
        return defaultPlatform(platform.id());
    }

    default Pi4JConfig setDefaultPlatform(String platformId){
        return defaultPlatform(platformId);
    }
    default Pi4JConfig setDefaultPlatform(Platform platform){
        return defaultPlatform(platform);
    }

    String defaultPlatform();
    default String getDefaultPlatform(){
        return defaultPlatform();
    }
    default boolean hasDefaultPlatform(){ return getDefaultPlatform() != null;};

    Pi4JConfig autoDetectPlatforms();
    Pi4JConfig noAutoDetectPlatforms();
    boolean getAutoDetectPlatforms();
    default boolean isAutoDetectPlatforms(){ return getAutoDetectPlatforms();};

    Pi4JConfig autoDetectProviders();
    Pi4JConfig noAutoDetectProviders();
    boolean getAutoDetectProviders();
    default boolean isAutoDetectProviders(){ return getAutoDetectProviders();};

    Pi4JConfig autoDetectBindings();
    Pi4JConfig noAutoDetectBindings();
    boolean getAutoDetectBindings();
    default boolean isAutoDetectBindings(){ return getAutoDetectBindings();};

    default Pi4JConfig autoDetect(){
        // auto detect all extensibility modules in the classpath
        return  autoDetectBindings().
                autoDetectPlatforms().
                autoDetectProviders();
    }

    default Pi4JConfig noAutoDetect(){
        // do not auto detect all extensibility modules in the classpath
        return  noAutoDetectBindings().
                noAutoDetectPlatforms().
                noAutoDetectProviders();
    }

    default Pi4JConfig addPlatform(Platform... platform){
        return add(platform);
    }
    default Pi4JConfig addPlatform(Provider... provider){
        return add(provider);
    }
    default Pi4JConfig addBinding(Binding... binding){
        return add(binding);
    }
}
