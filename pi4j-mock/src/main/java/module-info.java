import com.pi4j.mock.platform.MockPlatform;
import com.pi4j.mock.provider.gpio.analog.MockAnalogInputProviderImpl;
import com.pi4j.mock.provider.gpio.analog.MockAnalogOutputProviderImpl;
import com.pi4j.mock.provider.gpio.digital.MockDigitalInputProviderImpl;
import com.pi4j.mock.provider.gpio.digital.MockDigitalOutputProviderImpl;
import com.pi4j.mock.provider.i2c.MockI2CProviderImpl;
import com.pi4j.mock.provider.pwm.MockPwmProviderImpl;
import com.pi4j.mock.provider.serial.MockSerialProviderImpl;
import com.pi4j.mock.provider.spi.MockSpiProviderImpl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: MOCK     :: Mock Platform & Mock I/O Providers
 * FILENAME      :  module-info.java
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
module pi4j.mock {
    requires pi4j.api;

    exports com.pi4j.mock.platform;
    exports com.pi4j.mock.provider.gpio.digital;
    exports com.pi4j.mock.provider.gpio.analog;
    exports com.pi4j.mock.provider.pwm;
    exports com.pi4j.mock.provider.serial;
    exports com.pi4j.mock.provider.spi;
    exports com.pi4j.mock.provider.i2c;

    provides com.pi4j.provider.Provider
            with MockAnalogInputProviderImpl,
                    MockAnalogOutputProviderImpl,
                    MockDigitalInputProviderImpl,
                    MockDigitalOutputProviderImpl,
                    MockPwmProviderImpl,
                    MockSpiProviderImpl,
                    MockI2CProviderImpl,
                    MockSerialProviderImpl;

    provides com.pi4j.platform.Platform
            with MockPlatform;
}
