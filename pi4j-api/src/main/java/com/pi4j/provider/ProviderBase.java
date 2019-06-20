package com.pi4j.provider;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Provider.java
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

import com.pi4j.binding.BindingBase;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.config.Config;
import com.pi4j.context.Context;
import com.pi4j.io.IO;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class ProviderBase<PROVIDER_TYPE extends Provider, IO_TYPE extends IO, CONFIG_TYPE extends Config>
        extends BindingBase
        implements Provider<IO_TYPE, CONFIG_TYPE> {

    protected Map<String, IO_TYPE> instances = Collections.synchronizedMap(new HashMap<>());

    public ProviderBase(){
        super();
    }

    public ProviderBase(String id){
        super(id);
    }

    public ProviderBase(String id, String name){
        super(id, name);
    }

    @Override
    public Collection<IO_TYPE> instances() {
        return this.instances.values();
    }

    @Override
    public PROVIDER_TYPE initialize(Context context) throws LifecycleException {
        return (PROVIDER_TYPE)this;
    }

    @Override
    public PROVIDER_TYPE terminate(Context context) throws LifecycleException {

        // perform a shutdown on each digital I/O instance that is tracked in the internal cache
        instances.forEach((address, instance)->{
            try {
                instance.terminate(context);
            } catch (LifecycleException e) {
                e.printStackTrace();
            }
        });

        // remove all managed instance from internal cache
        instances.clear();

        return (PROVIDER_TYPE)this;
    }

    @Override
    public IO_TYPE instance(CONFIG_TYPE config) throws Exception {
        var newInstance = create(config);
        instances.put(newInstance.id(), newInstance);
        return newInstance;
    }

    public abstract IO_TYPE create(CONFIG_TYPE config) throws Exception;

    @Override
    public IO_TYPE get(String id){
        return instances.get(id);
    }

    @Override
    public boolean has(String id){
        return instances.containsKey(id);
    }
}
