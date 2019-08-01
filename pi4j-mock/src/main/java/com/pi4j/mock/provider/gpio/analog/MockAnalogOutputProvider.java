package com.pi4j.mock.provider.gpio.analog;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: MOCK     :: Mock Platform & Mock I/O Providers
 * FILENAME      :  MockAnalogOutputProvider.java
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

import com.pi4j.context.Context;
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogOutputConfig;
import com.pi4j.io.gpio.analog.AnalogOutputProvider;
import com.pi4j.io.gpio.analog.AnalogOutputProviderBase;
import com.pi4j.mock.Mock;


public class MockAnalogOutputProvider extends AnalogOutputProviderBase implements AnalogOutputProvider {

    public static final String NAME = Mock.ANALOG_OUTPUT_PROVIDER_NAME;
    public static final String ID = Mock.ANALOG_OUTPUT_PROVIDER_ID;

    public MockAnalogOutputProvider(){
        this.id = ID;
        this.name = NAME;
    }

    @Override
    public AnalogOutput newInstance(Context context, AnalogOutputConfig config) throws Exception {
        return new MockAnalogOutput(config);
    }

}
