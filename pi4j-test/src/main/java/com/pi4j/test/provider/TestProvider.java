package com.pi4j.test.provider;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  TestProvider.java
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
import com.pi4j.config.Config;
import com.pi4j.context.Context;
import com.pi4j.io.IO;
import com.pi4j.provider.Provider;
import com.pi4j.provider.ProviderBase;

public abstract class TestProvider<PROVIDER_TYPE extends Provider, IO_TYPE extends IO, CONFIG_TYPE extends Config>
        extends ProviderBase<PROVIDER_TYPE, IO_TYPE, CONFIG_TYPE>
        implements Provider<IO_TYPE, CONFIG_TYPE> {

    public boolean initializeFail = false;
    public boolean shutdownFail = false;
    protected String name = null;
    protected String id = null;

    public TestProvider(){

    }

    public TestProvider(String id){
        this.id = id;
    }

    public TestProvider(String id, String name){
        this(id);
        this.name = name;
    }

    @Override
    public String name() {
        if(name != null) return name;
        return this.getClass().getName();
    }

    @Override
    public String id() {
        if(id != null) return id;
        return this.getClass().getName();
    }

    @Override
    public String description() { return null; }

    @Override
    public PROVIDER_TYPE initialize(Context context) throws LifecycleException {
        if(initializeFail) throw new LifecycleException("");
        return (PROVIDER_TYPE) this;
    }

    @Override
    public PROVIDER_TYPE shutdown(Context context) throws LifecycleException {
        if(shutdownFail) throw new LifecycleException("");
        return (PROVIDER_TYPE) this;
    }
}
