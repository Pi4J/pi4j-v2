package com.pi4j.test.provider.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  TestAnalogOutputProviderImpl.java
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

import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogOutputConfig;
import com.pi4j.io.gpio.analog.AnalogOutputProviderBase;
import com.pi4j.test.provider.TestAnalogOutput;
import com.pi4j.test.provider.TestAnalogOutputProvider;

public class TestAnalogOutputProviderImpl extends AnalogOutputProviderBase implements TestAnalogOutputProvider {

    public TestAnalogOutputProviderImpl(){ super(); }

    public TestAnalogOutputProviderImpl(String id){
        super(id);
    }

    public TestAnalogOutputProviderImpl(String id, String name){
        super(id, name);
    }

    @Override
    public AnalogOutput create(AnalogOutputConfig config) throws Exception {
        return new TestAnalogOutput();
    }
}
