package com.pi4j.registry.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultRegistry.java
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

import com.pi4j.io.IO;
import com.pi4j.registry.Registry;
import com.pi4j.registry.exception.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class DefaultRegistry implements Registry {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RegistryManager registry = null;

    // static singleton instance
    public static Registry newInstance(RegistryManager registry){
        return new DefaultRegistry(registry);
    }

    // private constructor
    private DefaultRegistry(RegistryManager registry) {
        // set local registry reference
        this.registry = registry;
    }

    @Override
    public boolean exists(String id, Class<? extends IO> type) {
        return registry.exists(id, type);
    }

    @Override
    public boolean exists(String id) {
        return registry.exists(id);
    }

    @Override
    public Map<String, ? extends IO> all() throws RegistryException {
        return registry.all();
    }

    @Override
    public <T extends IO> T get(String id) throws RegistryException {
        return registry.get(id);
    }

    @Override
    public <T extends IO> T get(String id, Class<T> type) throws RegistryException {
        return registry.get(id, type);
    }
}
