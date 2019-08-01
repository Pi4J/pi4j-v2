package com.pi4j.context;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ContextConfig.java
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
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;

import java.util.Collection;

public interface ContextConfig {
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

    String defaultPlatform();
    default String getDefaultPlatform(){
        return defaultPlatform();
    }
    default boolean hasDefaultPlatform(){ return getDefaultPlatform() != null;};

    boolean autoDetectPlatforms();
    default boolean getAutoDetectPlatforms() { return autoDetectPlatforms(); };
    default boolean isAutoDetectPlatforms() { return autoDetectPlatforms(); };

    boolean autoDetectProviders();
    default boolean getAutoDetectProviders() { return autoDetectProviders(); };
    default boolean isAutoDetectProviders() { return autoDetectProviders(); };

    boolean autoDetectBindings();
    default boolean getAutoDetectBindings() { return autoDetectBindings(); };
    default boolean isAutoDetectBindings() { return autoDetectProviders(); };
}
