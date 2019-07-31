package com.pi4j.mock.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: MOCK     :: Mock Platform & Mock I/O Providers
 * FILENAME      :  MockPlatform.java
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
import com.pi4j.mock.Mock;
import com.pi4j.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.mock.provider.i2c.MockI2CProvider;
import com.pi4j.mock.provider.pwm.MockPwmProvider;
import com.pi4j.mock.provider.serial.MockSerialProvider;
import com.pi4j.mock.provider.spi.MockSpiProvider;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformBase;

public class MockPlatform extends PlatformBase<MockPlatform> implements Platform {


    public MockPlatform(){
        super(Mock.PLATFORM_ID,
              Mock.PLATFORM_NAME,
              Mock.PLATFORM_DESCRIPTION);
    }

    @Override
    public int weight() {
        // the MOCK platform is weighted at zero to indicate that it has a very
        // low priority and should only be used in the case where other platforms
        // are not found in the classpath
        return 0;
    }

    @Override
    public boolean enabled(Context context) {
        // the Mock Platform is always available when detected
        // there are no logic checked required to determine when
        // and if the mock platforms should be enabled
        return true;
    }

    @Override
    protected String[] getProviders() {
        return new String[] {
            MockAnalogInputProvider.ID,
            MockAnalogOutputProvider.ID,
            MockDigitalInputProvider.ID,
            MockDigitalOutputProvider.ID,
            MockPwmProvider.ID,
            MockSpiProvider.ID,
            MockI2CProvider.ID,
            MockSerialProvider.ID };
    }
}
