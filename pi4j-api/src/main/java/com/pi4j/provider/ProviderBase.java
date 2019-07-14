package com.pi4j.provider;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ProviderBase.java
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

import com.pi4j.Pi4J;
import com.pi4j.binding.BindingBase;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.config.Config;
import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.IO;
import com.pi4j.registry.exception.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class ProviderBase<PROVIDER_TYPE extends Provider, IO_TYPE extends IO, CONFIG_TYPE extends Config>
        extends BindingBase
        implements Provider<IO_TYPE, CONFIG_TYPE> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public PROVIDER_TYPE initialize(Context context) throws LifecycleException {
        return (PROVIDER_TYPE)this;
    }

    @Override
    public PROVIDER_TYPE terminate(Context context) throws LifecycleException {

        // TODO :: ABSTRACT VIA PROXY IMPL

        // perform a shutdown on each digital I/O instance that is tracked in the internal cache
        Map<String, IO> instances = null;
        try {
            instances = Pi4J.registry().allByProvider(this.id(), IO.class);
            instances.forEach((address, instance)->{
                try {
                    instance.terminate(context);
                } catch (LifecycleException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        } catch (RegistryException e) {
            logger.error(e.getMessage(), e);
            throw new LifecycleException(e);
        } catch (NotInitializedException e) {
            logger.error(e.getMessage(), e);
            throw new LifecycleException(e);
        }
        return (PROVIDER_TYPE)this;
    }

    @Override
    public IO_TYPE instance(CONFIG_TYPE config) throws Exception {
        var newInstance = create(config);
        newInstance.provider(this); // TODO :: PROXY IMPL, REMOVE PROVIDER SETTER
        return newInstance;
    }

    public abstract IO_TYPE create(CONFIG_TYPE config) throws Exception;
}
