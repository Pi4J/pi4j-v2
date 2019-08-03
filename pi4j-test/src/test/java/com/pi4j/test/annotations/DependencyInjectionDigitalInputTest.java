package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  DependencyInjectionDigitalInputTest.java
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
import com.pi4j.annotation.*;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.mock.platform.MockPlatform;
import com.pi4j.test.platform.TestPlatform;
import com.pi4j.test.provider.TestDigitalInputProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

public class DependencyInjectionDigitalInputTest {

    public static final int PIN_ADDRESS1 = 1;
    public static final int PIN_ADDRESS2 = 2;

    @Inject
    Context pi4j;

    @Register("my.digital.input-mock")
    @Address(PIN_ADDRESS1)
    @Name("My Digital Input #1")
    @WithPlatform(type = MockPlatform.class)
    DigitalInput inputUsingMockProvider;

    @Register("my.digital.input-custom")
    @Address(PIN_ADDRESS2)
    @Name("My Digital Input #2")
    @WithProvider(type = TestDigitalInputProvider.class)
    @WithPlatform(type = TestPlatform.class)
    DigitalInput inputUsingCustomProvider;

    @Before
    public void beforeTest() throws Pi4JException {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        TestPlatform platform = new TestPlatform();
        platform.setProviders(new TestDigitalInputProvider());

        // Initialize Pi4J with a default context
        // enable the AUTO-DETECT (Platforms & Providers) flag
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        // ...
        // Also, inject this class instance into the Pi4J context
        // for annotation processing and dependency injection
        Pi4J.newContextBuilder()
                .autoDetect()
                .add(platform)
                .add(new TestDigitalInputProvider())
                .build().inject(this);
    }

    @After
    public void afterTest() {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testDIContextAcquisition() throws Pi4JException {
        System.out.println("-----------------------------------------------------");
        System.out.println("Pi4J DIGITAL INPUT <acquired via dependency injection>");
        System.out.println("-----------------------------------------------------");
        assertNotNull(inputUsingMockProvider);
        System.out.println("INPUT FROM DEFAULT PROVIDER:");
        System.out.print("  INPUT --> ");
        inputUsingMockProvider.describe().print(System.out);
        System.out.print("  PROVIDER --> ");
        inputUsingMockProvider.provider().describe().print(System.out);

        assertNotNull(inputUsingMockProvider);
        System.out.println("INPUT FROM CUSTOM PROVIDER:");
        System.out.print("  INPUT --> ");
        inputUsingCustomProvider.describe().print(System.out);
        System.out.print("  PROVIDER --> ");
        inputUsingCustomProvider.provider().describe().print(System.out);

        // make sure we get get two unique I/O instances from different providers
        assertFalse(inputUsingMockProvider.provider().id().equalsIgnoreCase(inputUsingCustomProvider.provider().id()));
    }
}
