package com.pi4j.io;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  BaseSerial.java
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

import com.pi4j.common.Descriptor;
import com.pi4j.common.IdentityBase;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.context.Context;


public abstract class IOBase<IO_TYPE extends IO, CONFIG_TYPE extends IOConfig> extends IdentityBase implements IO<IO_TYPE,CONFIG_TYPE> {

    protected CONFIG_TYPE config = null;

    public IOBase(CONFIG_TYPE config){
        super();
        this.config = config;
//        // if this config supports and address, then set the id field to the address
//        if(config instanceof AddressConfig){
//            this.id = Integer.toString(((AddressConfig) config).address());
//        }
    }

    @Override
    public IO_TYPE name(String name){
        this.name = name;
        return (IO_TYPE)this;
    }

    @Override
    public IO_TYPE description(String description){
        this.description = description;
        return (IO_TYPE)this;
    }

    @Override
    public CONFIG_TYPE config(){
        return this.config;
    }


    @Override
    public IO_TYPE initialize(Context context) throws LifecycleException {
        return (IO_TYPE)this;
    }

    @Override
    public IO_TYPE terminate(Context context) throws LifecycleException {

//        // perform a shutdown on each digital I/O instance that is tracked in the internal cache
//        instances.forEach((address, instance)->{
//            try {
//                instance.terminate(context);
//            } catch (LifecycleException e) {
//                e.printStackTrace();
//            }
//        });
//
//        // remove all managed instance from internal cache
//        instances.clear();

        return (IO_TYPE)this;
    }

    @Override
    public Descriptor describe() {
        return super.describe().category("IO");
    }
}
