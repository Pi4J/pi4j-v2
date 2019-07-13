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

import com.pi4j.config.Config;
import com.pi4j.context.Context;
import com.pi4j.io.IO;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.Registry;
import com.pi4j.registry.exception.RegistryAlreadyExistsException;
import com.pi4j.registry.exception.RegistryException;
import com.pi4j.registry.exception.RegistryInvalidIDException;
import com.pi4j.registry.exception.RegistryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultRegistry implements Registry {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;
    private Map<String, IO> instances = new ConcurrentHashMap<>();

    // static singleton instance
    private static Registry singleton = null;
    public static Registry singleton(Context context){
        if(singleton == null){
            singleton = new DefaultRegistry(context);
        }
        return singleton;
    }

    // private constructor
    private DefaultRegistry(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    @Override
    public <T extends IO> T create(String providerId, Config config, Class<T> type) throws RegistryException, ProviderException {
        return null;
    }

    @Override
    public <T extends IO> T create(Provider provider, Config config, Class<T> type) throws RegistryException, ProviderException {

        String _id = validateId(config.id());

        // first test to make sure this id does not already exist in the registry
        if(instances.containsKey(_id))
            throw new RegistryAlreadyExistsException(_id);

        return (T)instances.put(_id, null);
    }

    @Override
    public <T extends IO> T create(Config config, Class<T> type) throws RegistryException, ProviderException {
        return null;
    }

    @Override
    public <T extends IO> T get(String id, Class<T> type) throws RegistryException {
        String _id = validateId(id);

        // first test to make sure this id is included in the registry
        if(!instances.containsKey(_id))
            throw new RegistryNotFoundException(_id);

        return (T)instances.get(_id);
    }

    @Override
    public <T extends IO> T destroy(String id) throws RegistryException {
        String _id = validateId(id);

        // first test to make sure this id is included in the registry
        if(!instances.containsKey(_id))
            throw new RegistryNotFoundException(_id);

        return null;
    }

    @Override
    public boolean exists(String id) {
        String _id = null;
        try {
            _id = validateId(id);
            return instances.containsKey(_id);
        } catch (RegistryException e) {
            return false;
        }
    }

    private String validateId(String id) throws RegistryException{
        if(id == null)
             throw new RegistryInvalidIDException();
        String validatedId = id.trim().toLowerCase();
        if(validatedId.isEmpty())
            throw new RegistryInvalidIDException();
        return validatedId;
    }
}
