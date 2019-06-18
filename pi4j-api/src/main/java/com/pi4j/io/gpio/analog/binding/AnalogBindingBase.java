package com.pi4j.io.gpio.analog.binding;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogBindingBase.java
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


import com.pi4j.io.gpio.analog.Analog;
import com.pi4j.io.gpio.analog.AnalogConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AnalogBindingBase<ANALOG_TYPE extends Analog<ANALOG_TYPE, CONFIG_TYPE>, CONFIG_TYPE extends AnalogConfig<CONFIG_TYPE>> {

    protected List<Analog<ANALOG_TYPE, CONFIG_TYPE>> outputs;

    /**
     * Default Constructor
     * @param output Variable argument list of analog outputs
     */
    public AnalogBindingBase(Analog<ANALOG_TYPE, CONFIG_TYPE>... output){
        outputs  = Collections.synchronizedList(Arrays.asList(output));
    }
}
