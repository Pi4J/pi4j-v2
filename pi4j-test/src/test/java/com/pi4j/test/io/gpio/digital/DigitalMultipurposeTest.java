package com.pi4j.test.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  DigitalMultipurposeTest.java
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
import com.pi4j.io.exception.IOModeException;
import com.pi4j.io.gpio.digital.DigitalMode;
import com.pi4j.io.gpio.digital.DigitalMultipurpose;
import com.pi4j.io.gpio.digital.DigitalState;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DigitalMultipurposeTest {
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
    public void testMultipurposeModes() throws Exception {

        // create Digital Multipurpose config
        var config  = DigitalMultipurpose.newConfigBuilder(pi4j)
            .id("my-digital-multi")
            .name("My Digital Multipurpose")
            .address(1)
            .mode(DigitalMode.INPUT)
            .build();

        // create Digital Multipurpose instance from config
        var digitalMultipurpose = pi4j.digitalMultipurpose().create(config);

        // set to OUTPUT mode
        digitalMultipurpose.mode(DigitalMode.OUTPUT);
        Assert.assertEquals(DigitalMode.OUTPUT, digitalMultipurpose.mode());

        // set to INPUT mode
        digitalMultipurpose.mode(DigitalMode.INPUT);
        Assert.assertEquals(DigitalMode.INPUT, digitalMultipurpose.mode());

        // set to OUTPUT mode
        digitalMultipurpose.output();
        Assert.assertEquals(DigitalMode.OUTPUT, digitalMultipurpose.mode());

        // set to INPUT mode
        digitalMultipurpose.input();
        Assert.assertEquals(DigitalMode.INPUT, digitalMultipurpose.mode());
    }

    @Test
    public void testMultipurposeStates() throws Exception {

        // create Digital Multipurpose config
        var config  = DigitalMultipurpose.newConfigBuilder(pi4j)
                .id("my-digital-multi")
                .name("My Digital Multipurpose")
                .address(1)
                .mode(DigitalMode.OUTPUT)
                .build();

        // create Digital Multipurpose instance from config
        var digitalMultipurpose = pi4j.digitalMultipurpose().create(config);

        // ensure that the Digital Multipurpose instance is not null;
        assertNotNull(digitalMultipurpose);

        // test HIGH state
        digitalMultipurpose.high();
        Assert.assertEquals(DigitalState.HIGH, digitalMultipurpose.state());
        Assert.assertEquals(DigitalMode.OUTPUT, digitalMultipurpose.mode());

        // test LOW state
        digitalMultipurpose.low();
        Assert.assertEquals(DigitalState.LOW, digitalMultipurpose.state());

        // test HIGH state
        digitalMultipurpose.setState(1);
        Assert.assertEquals(DigitalState.HIGH, digitalMultipurpose.state());

        // test LOW state
        digitalMultipurpose.setState(0);
        Assert.assertEquals(DigitalState.LOW, digitalMultipurpose.state());

        // test HIGH state
        digitalMultipurpose.state(DigitalState.HIGH);
        Assert.assertEquals(DigitalState.HIGH, digitalMultipurpose.state());

        // test LOW state
        digitalMultipurpose.state(DigitalState.LOW);
        Assert.assertEquals(DigitalState.LOW, digitalMultipurpose.state());
    }

    @Test(expected = IOModeException.class)
    public void testMultipurposeInvalidMode() throws Exception {

        // create Digital Multipurpose config
        var config = DigitalMultipurpose.newConfigBuilder(pi4j)
            .id("my-digital-multi")
            .name("My Digital Multipurpose")
            .address(1)
            .mode(DigitalMode.INPUT)
            .build();

        // create Digital Multipurpose instance from config
        var digitalMultipurpose = pi4j.digitalMultipurpose().create(config);

        // ensure that the Digital Multipurpose instance is not null;
        assertNotNull(digitalMultipurpose);

        // ensure correct MODE
        Assert.assertEquals(DigitalMode.INPUT, digitalMultipurpose.mode());

        // attempt to write HIGH state
        // WE EXPECT AN 'IOModeException' BECAUSE THE MODE IS INCORRECT
        digitalMultipurpose.high();
    }
}
