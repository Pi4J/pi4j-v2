package com.pi4j.test.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  ManualPlatformsTest.java
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

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.mock.platform.MockPlatform;
import com.pi4j.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.mock.provider.i2c.MockI2CProvider;
import com.pi4j.mock.provider.pwm.MockPwmProvider;
import com.pi4j.mock.provider.serial.MockSerialProvider;
import com.pi4j.mock.provider.spi.MockSpiProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ManualPlatformsTest {

    private Context pi4j;

    @Before
    public void beforeTest() throws Pi4JException {

        // Initialize Pi4J with AUTO-DETECT disabled
        // we don't want to load any detected Pi4J binding/io libraries
        // in the class path for this test case
        pi4j = Pi4J.newContextBuilder()

                        // add any platforms that we want to work with
                        .add(new MockPlatform())

                        // add any providers that we want to work with
                        .add(new MockAnalogInputProvider(),
                             new MockAnalogOutputProvider(),
                             new MockDigitalInputProvider(),
                             new MockDigitalOutputProvider(),
                             new MockPwmProvider(),
                             new MockI2CProvider(),
                             new MockSpiProvider(),
                             new MockSerialProvider())
                        .build();
    }

    @After
    public void afterTest()  {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testProvidersNotNull() throws Pi4JException {
        // ensure that the io collection in the Pi4J context is not NULL
        assertNotNull(pi4j.providers());
    }

    @Test
    public void testProvidersCount() throws Exception {

        // ensure that no io were detected/loaded into the Pi4J context
        assertEquals(1, pi4j.platforms().all().size());

        // print out the detected Pi4J io libraries found on the class path
        System.out.println("1 'MOCK' PLATFORM (added via API)");
        pi4j.providers().describe().print(System.out);
    }
}
