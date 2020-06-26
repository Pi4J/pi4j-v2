package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  DependencyInjectionDigitalInputTest.java
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pi4j.Pi4J;
import com.pi4j.annotation.*;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.plugin.mock.platform.MockPlatform;
import com.pi4j.test.platform.TestPlatform;
import com.pi4j.test.provider.TestDigitalInputProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
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

    @BeforeAll
    public void beforeTest() throws Pi4JException {
        System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        TestDigitalInputProvider provider = TestDigitalInputProvider.newInstance("test-digital-input-provider");
        TestPlatform platform = new TestPlatform();
        platform.setProviders(provider);

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
                .add(provider)
                .build().inject(this);
    }

    @AfterAll
    public void afterTest() {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testDIContextAcquisition() throws Exception {
        System.out.println("-----------------------------------------------------");
        System.out.println("Pi4J DIGITAL INPUT <acquired via dependency injection>");
        System.out.println("-----------------------------------------------------");

        pi4j.describe().print(System.out);

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
