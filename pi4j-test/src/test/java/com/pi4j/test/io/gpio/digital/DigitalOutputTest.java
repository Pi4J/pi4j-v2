package com.pi4j.test.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  DigitalOutputTest.java
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
import com.pi4j.io.gpio.digital.DigitalMode;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DigitalOutputTest {
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
    public void testOutputStates() throws Exception {

        // create I2C config
        var config  = DigitalOutput.newConfigBuilder(pi4j)
                .id("my-digital-output")
                .name("My Digital Output")
                .address(1)
                .build();

        // create Digital Output instance from config
        var digitalOutput = pi4j.digitalOutput().create(config);

        // ensure that the Digital Output instance is not null;
        assertNotNull(digitalOutput);

        // test MODE
        Assert.assertEquals(DigitalMode.OUTPUT, digitalOutput.mode());

        // test HIGH state
        digitalOutput.high();
        Assert.assertEquals(DigitalState.HIGH, digitalOutput.state());

        // test LOW state
        digitalOutput.low();
        Assert.assertEquals(DigitalState.LOW, digitalOutput.state());

        // test HIGH state
        digitalOutput.setState(1);
        Assert.assertEquals(DigitalState.HIGH, digitalOutput.state());

        // test LOW state
        digitalOutput.setState(0);
        Assert.assertEquals(DigitalState.LOW, digitalOutput.state());

        // test HIGH state
        digitalOutput.state(DigitalState.HIGH);
        Assert.assertEquals(DigitalState.HIGH, digitalOutput.state());

        // test LOW state
        digitalOutput.state(DigitalState.LOW);
        Assert.assertEquals(DigitalState.LOW, digitalOutput.state());
    }
}
