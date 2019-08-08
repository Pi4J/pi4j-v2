package com.pi4j.context;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ContextBuilder.java
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

import com.pi4j.config.Builder;
import com.pi4j.context.impl.DefaultContextBuilder;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;

public interface ContextBuilder extends Builder<Context> {

    static ContextBuilder newInstance(){
        return DefaultContextBuilder.newInstance();
    }

    ContextBuilder add(Platform ... platform);
    ContextBuilder add(Provider ... provider);

    String defaultPlatform();
    ContextBuilder defaultPlatform(String platformId);

    ContextBuilder autoDetectPlatforms();
    ContextBuilder noAutoDetectPlatforms();

    ContextBuilder autoDetectProviders();
    ContextBuilder noAutoDetectProviders();

    ContextConfig toConfig();

    default ContextBuilder setAutoDetect(boolean autoDetect){
        if(autoDetect)
            return autoDetect();
        else
            return noAutoDetect();
    }

    default ContextBuilder autoDetect(){
        // auto detect all extensibility modules in the classpath
        return  autoDetectPlatforms().
                autoDetectProviders();
    }

    default ContextBuilder noAutoDetect(){
        // do not auto detect all extensibility modules in the classpath
        return  noAutoDetectPlatforms().
                noAutoDetectProviders();
    }

    default ContextBuilder addPlatform(Platform ... platform){
        return add(platform);
    }
    default ContextBuilder addPlatform(Provider ... provider){
        return add(provider);
    }

    default ContextBuilder addDefaultPlatform(Platform platform){
        return this.add(platform).defaultPlatform(platform.id());
    }

    default ContextBuilder defaultPlatform(Platform platform){
        return defaultPlatform(platform.id());
    }
    default ContextBuilder setDefaultPlatform(String platformId){
        return defaultPlatform(platformId);
    }
    default ContextBuilder setDefaultPlatform(Platform platform){
        return defaultPlatform(platform);
    }
}
