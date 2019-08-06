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

import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.platform.exception.PlatformNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DefaultPlatforms implements Platforms {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RuntimePlatforms platforms = null;

    public static Platforms newInstance(RuntimePlatforms platforms) throws PlatformNotFoundException {
        return new DefaultPlatforms(platforms);
    }

    // private constructor
    private DefaultPlatforms(RuntimePlatforms platforms) throws PlatformNotFoundException {
        // set local reference
        this.platforms = platforms;
    }

    @Override
    public Map<String, Platform> all() {
        return platforms.all();
    }

    @Override
    public boolean exists(String platformId) {
        return platforms.exists(platformId);
    }

    @Override
    public Platform get(String platformId) throws PlatformNotFoundException {
        return platforms.get(platformId);
    }

    @Override
    public <T extends Platform> T defaultPlatform() {
        return platforms.defaultPlatform();
    }
}
