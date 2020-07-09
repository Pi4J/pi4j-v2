package com.pi4j.test.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  DigitalInputTest.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalMode;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInput;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DigitalInputTest {
    private Context pi4j;

    @Before
    public void beforeTest() throws Pi4JException {
        // Initialize Pi4J with auto context
        // An auto context enabled AUTO-DETECT loading
        // which will load any detected Pi4J extension
        // libraries (Platforms and Providers) from the class path
        pi4j = Pi4J.newAutoContext();
    }

    @After
    public void afterTest() {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testInputStates() throws Exception {

        // create I2C config
        var config  = DigitalInput.newConfigBuilder(pi4j)
                .id("my-digital-input")
                .name("My Digital Input")
                .address(1)
                .build();

        // create Digital Input instance from config
        MockDigitalInput digitalInput = (MockDigitalInput) pi4j.digitalInput().create(config);

        // ensure that the Digital Input instance is not null;
        assertNotNull(digitalInput);

        // test MODE
        Assert.assertEquals(DigitalMode.INPUT, digitalInput.mode());

        // test HIGH state
        digitalInput.mockState(DigitalState.HIGH);
        Assert.assertEquals(DigitalState.HIGH, digitalInput.state());

        // test LOW state
        digitalInput.mockState(DigitalState.LOW);
        Assert.assertEquals(DigitalState.LOW, digitalInput.state());
    }
}
