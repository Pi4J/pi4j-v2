package com.pi4j.mock;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: MOCK     :: Mock Platform & Mock I/O Providers
 * FILENAME      :  MockPlugin.java
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

import com.pi4j.extension.Plugin;
import com.pi4j.extension.PluginService;
import com.pi4j.mock.platform.MockPlatform;
import com.pi4j.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.mock.provider.i2c.MockI2CProvider;
import com.pi4j.mock.provider.pwm.MockPwmProvider;
import com.pi4j.mock.provider.serial.MockSerialProvider;
import com.pi4j.mock.provider.spi.MockSpiProvider;
import com.pi4j.provider.Provider;

public class MockPlugin implements Plugin {

    private Provider providers[] = {
            MockAnalogInputProvider.newInstance(),
            MockAnalogOutputProvider.newInstance(),
            MockDigitalInputProvider.newInstance(),
            MockDigitalOutputProvider.newInstance(),
            MockPwmProvider.newInstance(),
            MockI2CProvider.newInstance(),
            MockSpiProvider.newInstance(),
            MockSerialProvider.newInstance(),
    };

    @Override
    public void initialize(PluginService service) {

        // register the Mock Platform and all Mock I/O Providers with the plugin service
        service.register(new MockPlatform())
               .register(providers);

    }
}
