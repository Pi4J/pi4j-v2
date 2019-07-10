package com.pi4j.io.gpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  GpioBase.java
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

import com.pi4j.io.IOBase;

public abstract class GpioBase<IO_TYPE extends Gpio<IO_TYPE, CONFIG_TYPE>, CONFIG_TYPE extends GpioConfig<CONFIG_TYPE>>
        extends IOBase<IO_TYPE, CONFIG_TYPE>
        implements Gpio<IO_TYPE, CONFIG_TYPE> {

    public GpioBase(CONFIG_TYPE config){
        super(config);
        this.id = Integer.toString(config.address());
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("\"");
        result.append(this.name());
        result.append("\" (#");
        result.append(this.address());
        result.append(")");
        return result.toString();
    }
}
