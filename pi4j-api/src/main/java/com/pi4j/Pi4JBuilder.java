package com.pi4j;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Pi4JBuilder.java
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
import com.pi4j.config.Builder;
import com.pi4j.impl.DefaultPi4JBuilder;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;

public interface Pi4JBuilder extends Builder<Pi4JConfig> {

    static Pi4JBuilder create(){
        return DefaultPi4JBuilder.instance();
    }

    Pi4JBuilder add(Binding ... binding);
    Pi4JBuilder add(Platform ... platform);
    Pi4JBuilder add(Provider ... provider);

    Pi4JBuilder addDefaultPlatform(Platform platform);

    String defaultPlatform();
    Pi4JBuilder defaultPlatform(String platformId);
    default Pi4JBuilder defaultPlatform(Platform platform){
        return defaultPlatform(platform.id());
    }

    default Pi4JBuilder setDefaultPlatform(String platformId){
        return defaultPlatform(platformId);
    }
    default Pi4JBuilder setDefaultPlatform(Platform platform){
        return defaultPlatform(platform);
    }

    Pi4JBuilder autoDetectPlatforms();
    Pi4JBuilder noAutoDetectPlatforms();

    Pi4JBuilder autoDetectProviders();
    Pi4JBuilder noAutoDetectProviders();

    Pi4JBuilder autoDetectBindings();
    Pi4JBuilder noAutoDetectBindings();

    default Pi4JBuilder setAutoDetect(boolean autoDetect){
        if(autoDetect)
            return autoDetect();
        else
            return noAutoDetect();
    }

    default Pi4JBuilder autoDetect(){
        // auto detect all extensibility modules in the classpath
        return  autoDetectBindings().
                autoDetectPlatforms().
                autoDetectProviders();
    }

    default Pi4JBuilder noAutoDetect(){
        // do not auto detect all extensibility modules in the classpath
        return  noAutoDetectBindings().
                noAutoDetectPlatforms().
                noAutoDetectProviders();
    }

    default Pi4JBuilder addPlatform(Platform ... platform){
        return add(platform);
    }
    default Pi4JBuilder addPlatform(Provider ... provider){
        return add(provider);
    }
    default Pi4JBuilder addBinding(Binding ... binding){
        return add(binding);
    }

    Pi4JConfig build();
}
