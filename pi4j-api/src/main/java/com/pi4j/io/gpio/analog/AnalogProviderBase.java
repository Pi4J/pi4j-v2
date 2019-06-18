package com.pi4j.io.gpio.analog;

import com.pi4j.context.Context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogProviderBase.java
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
public abstract class AnalogProviderBase<ANALOG_TYPE extends Analog, CONFIG_TYPE extends AnalogConfig> implements AnalogProvider<ANALOG_TYPE, CONFIG_TYPE> {

    protected Map<Integer, ANALOG_TYPE> instances = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void terminate(Context context) throws Exception {

        // perform a shutdown on each digital I/O instance that is tracked in the internal cache
        instances.forEach((address,instance)->{
            instance.terminate(context);
        });

        // remove all managed instance from internal cache
        instances.clear();
    }

    @Override
    public ANALOG_TYPE instance(CONFIG_TYPE config) throws Exception {
        ANALOG_TYPE newInstance = create(config);
        instances.put(newInstance.address(), newInstance);
        return newInstance;
    }

    @Override
    public abstract ANALOG_TYPE create(CONFIG_TYPE config) throws Exception;

    @Override
    public ANALOG_TYPE get(int address){
        return instances.get(address);
    }

    @Override
    public boolean has(int address){
        return instances.containsKey(address);
    }
}
