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
import com.pi4j.test.provider.TestDigitalInputProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class DependencyInjectionDigitalInputTest {

    public static final int PIN_ADDRESS1 = 1;
    public static final int PIN_ADDRESS2 = 2;

    @Inject
    Context pi4j;

    @Register("my.digital.input-default")
    @Address(PIN_ADDRESS1)
    @Name("My Digital Input #1")
    DigitalInput inputUsingDefaultProvider;

    @Register("my.digital.input-custom")
    @Address(PIN_ADDRESS2)
    @Name("My Digital Input #2")
    @Provider(type = TestDigitalInputProvider.class)
    DigitalInput inputUsingCustomProvider;

    @Before
    public void beforeTest() throws Pi4JException {

        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        // Initialize Pi4J with AUTO-DETECT enabled
        // we do want to load all detected Pi4J binding/io libraries
        // in the class path for this test case
        // Also, inject this class instance into the Pi4J context
        // for annotation processing and dependency injection
        Pi4J.initialize(true, new TestDigitalInputProvider()).inject(this);
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
        assertNotNull(inputUsingDefaultProvider);
        System.out.println("INPUT FROM DEFAULT PROVIDER:");
        System.out.print("  INPUT --> ");
        inputUsingDefaultProvider.describe().print(System.out);
        System.out.print("  PROVIDER --> ");
        inputUsingDefaultProvider.provider().describe().print(System.out);

        assertNotNull(inputUsingDefaultProvider);
        System.out.println("INPUT FROM CUSTOM PROVIDER:");
        System.out.print("  INPUT --> ");
        inputUsingCustomProvider.describe().print(System.out);
        System.out.print("  PROVIDER --> ");
        inputUsingCustomProvider.provider().describe().print(System.out);

        // make sure we get get two unique I/O instances from different providers
        //assertFalse(inputUsingDefaultProvider.provider().id().equalsIgnoreCase(inputUsingDefaultProvider.provider().id()));
    }
}
