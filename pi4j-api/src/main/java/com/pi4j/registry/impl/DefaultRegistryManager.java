package com.pi4j.registry.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultRegistryManager.java
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

import com.pi4j.common.exception.LifecycleException;
import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.IO;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.registry.exception.RegistryAlreadyExistsException;
import com.pi4j.registry.exception.RegistryException;
import com.pi4j.registry.exception.RegistryInvalidIDException;
import com.pi4j.registry.exception.RegistryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultRegistryManager implements RegistryManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;
    private Map<String, IO> instances = new ConcurrentHashMap<>();

    // static singleton instance
    public static RegistryManager newInstance(Context context){
        return new DefaultRegistryManager(context);
    }

    // private constructor
    private DefaultRegistryManager(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    @Override
    public RegistryManager add(IO instance) throws RegistryException, ProviderException, NotInitializedException {

        // validate target I/O instance id
        String _id = validateId(instance.id());

        // first test to make sure this id does not already exist in the registry
        if(instances.containsKey(_id))
            throw new RegistryAlreadyExistsException(_id);

        try {
            // validate named provider exists
            if(!context.providers().exists(instance.provider().id()))
                throw new ProviderNotFoundException(instance.provider().id());

            // add instance to collection
            instances.put(_id, instance);
            return this;

        } catch (Exception e) {
            throw new ProviderException(e);
        }
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
    public <T extends IO> T get(String id) throws RegistryException {
        String _id = validateId(id);

        // first test to make sure this id is included in the registry
        if(!instances.containsKey(_id))
            throw new RegistryNotFoundException(_id);

        return (T)instances.get(_id);
    }

    @Override
    public <T extends IO> T remove(String id) throws RegistryException, LifecycleException {
        String _id = validateId(id);

        // first test to make sure this id is included in the registry
        if(!instances.containsKey(_id))
            throw new RegistryNotFoundException(_id);

        // shutdown instance
        var shutdownInstances = instances.get(_id).shutdown(this.context);

        // remove the shutdown instance from the registry
        this.instances.remove(_id);

        // return the shutdown I/O provider instances
        return (T)shutdownInstances;
    }

    @Override
    public RegistryManager shutdown(Context context) throws ProviderException, RegistryException {
        all().values().forEach(instance->{
            try {
                remove(instance.id());
            } catch (RegistryException e) {
                logger.error(e.getMessage(), e);
            } catch (LifecycleException e) {
                logger.error(e.getMessage(), e);
            }
        });
        return this;
    }

    @Override
    public boolean exists(String id) {
        String _id = null;
        try {
            _id = validateId(id);
            // return 'false' if the requested ID is not found
            // return 'true' if the requested ID is found
            return instances.containsKey(_id);
        } catch (RegistryException e) {
            return false;
        }
    }

    @Override
    public Map<String, ? extends IO> all() throws RegistryException {
        return Collections.unmodifiableMap(this.instances);
    }

    @Override
    public boolean exists(String id, Class<? extends IO> type){
        String _id = null;
        try {
            _id = validateId(id);

            // return 'false' if the requested ID is not found
            if(!instances.containsKey(_id))
                return false;

            // get the I/O instance
            IO instance = instances.get(id);

            // return true if the I/O instance matches the requested I/O type
            return type.isAssignableFrom(instance.getClass());
        } catch (RegistryException e) {
            return false;
        }
    }


    private String validateId(String id) throws RegistryException{
        if(id == null)
             throw new RegistryInvalidIDException();
        String validatedId = id.trim();
        if(validatedId.isEmpty())
            throw new RegistryInvalidIDException();
        return validatedId;
    }
}
