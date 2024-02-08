package com.pi4j.registry.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultRuntimeRegistry.java
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

import com.pi4j.exception.InitializeException;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.IO;
import com.pi4j.io.exception.*;
import com.pi4j.runtime.Runtime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>DefaultRuntimeRegistry class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultRuntimeRegistry implements RuntimeRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRuntimeRegistry.class);
    private final Runtime runtime;
    private final Map<String, IO> instances;

    // static singleton instance
    /**
     * <p>newInstance.</p>
     *
     * @param runtime a {@link com.pi4j.runtime.Runtime} object.
     * @return a {@link com.pi4j.registry.impl.RuntimeRegistry} object.
     */
    public static RuntimeRegistry newInstance(Runtime runtime){
        return new DefaultRuntimeRegistry(runtime);
    }

    // private constructor
    private DefaultRuntimeRegistry(Runtime runtime) {
        // set local runtime reference
        this.instances = new ConcurrentHashMap<>();
        this.runtime = runtime;
	}

    @Override
    public RuntimeRegistry add(IO instance) throws IOInvalidIDException, IOAlreadyExistsException {
        // validate target I/O instance id
        String _id = validateId(instance.id());

        // first test to make sure this id does not already exist in the registry
        synchronized (this.instances) {
            if (instances.containsKey(_id))
                throw new IOAlreadyExistsException(_id);

            // add instance to collection
            instances.put(_id, instance);
        }

        return this;
    }

    /** {@inheritDoc} */
    @Override
    public <T extends IO> T get(String id, Class<T> type) throws IOInvalidIDException, IONotFoundException {
        String _id = validateId(id);
        T t = (T) instances.get(_id);
        if(t == null)
            throw new IONotFoundException(_id);
        return t;
    }

    /** {@inheritDoc} */
    @Override
    public <T extends IO> T get(String id) throws IOInvalidIDException, IONotFoundException {
        String _id = validateId(id);
        T t = (T) instances.get(_id);
        if(t == null)
            throw new IONotFoundException(_id);
        return t;
    }

    /** {@inheritDoc} */
    @Override
    public <T extends IO> T remove(String id) throws IONotFoundException, IOInvalidIDException, IOShutdownException {
        synchronized (this.instances) {
            String _id = validateId(id);

            // remove the instance from the registry
            IO io = this.instances.remove(_id);
            if (io == null)
                throw new IONotFoundException(_id);

            // shutdown instance
            try {
                io.shutdown(runtime.context());
            } catch (LifecycleException e) {
                logger.error(e.getMessage(), e);
                throw new IOShutdownException(io, e);
            }

            // return the shutdown I/O provider instances
            return (T) io;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean exists(String id) {
        return instances.containsKey(validateId(id));
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, ? extends IO> all() {
        return Collections.unmodifiableMap(this.instances);
    }

    private String validateId(String id) throws IOInvalidIDException {
        if(id == null)
             throw new IOInvalidIDException();
        String validatedId = id.trim();
        if(validatedId.isEmpty())
            throw new IOInvalidIDException();
        return validatedId;
    }

    /** {@inheritDoc} */
    @Override
    public RuntimeRegistry shutdown() {
        all().values().forEach(instance->{
            try {
                remove(instance.id());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public RuntimeRegistry initialize() throws InitializeException {
        // NOTHING TO INITIALIZE
        return this;
    }
}
