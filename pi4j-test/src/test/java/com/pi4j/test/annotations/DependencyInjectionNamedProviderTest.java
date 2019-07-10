package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  AutoProvidersTest.java
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
import com.pi4j.annotation.Inject;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.provider.mock.io.pwm.MockPwmProvider;
import com.pi4j.test.About;
import com.pi4j.test.provider.TestPwmProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;

public class DependencyInjectionNamedProviderTest {

    /**
     * Use dependency injection to acquire a specific
     * I/O provider instance using the "id" attribute
     */
    @Inject(id = MockPwmProvider.ID)
    PwmProvider pwmProvider;

    @Before
    public void beforeTest() throws Pi4JException {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        // Initialize Pi4J with AUTO-DETECT enabled
        // we want to load all detected Pi4J binding/io libraries
        // in the class path for this test case
        // Also, inject this class instance into the Pi4J context
        // for annotation processing and dependency injection
        Pi4J.initialize(true, new TestPwmProvider()).inject(this);
    }

    @After
    public void afterTest() {
        try {
            Pi4J.terminate();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testDINamedProviderNotNull() throws Pi4JException {
        assertNotNull(pwmProvider);
    }

    @BeforeClass
    public static void beforeClass(){
        System.out.println("-------------------------------------------------");
        System.out.println("*************************************************");
        System.out.println("-------------------------------------------------");
        System.out.println(DependencyInjectionNamedProviderTest.class.getSimpleName());
        System.out.println("-------------------------------------------------");
        System.out.println("*************************************************");
        System.out.println("-------------------------------------------------");
    }

    @Test
    public void testDINamedProvider() throws Exception {
        About about = new About();

        // ensure that the acquired provider is the same ID & NAME as the one we expected
        assertEquals(pwmProvider.id(), MockPwmProvider.ID);
        assertEquals(pwmProvider.name(), MockPwmProvider.NAME);

        System.out.println("-------------------------------------------------");
        System.out.println("Pi4J I/O PROVIDERS ACQUIRED");
        System.out.println("-------------------------------------------------");
        Pi4J.providers().pwm().describe().print(System.out);

        System.out.println("-------------------------------------------------");
        System.out.println("Pi4J I/O NAMED PROVIDER ACQUIRED");
        System.out.println("-------------------------------------------------");
        pwmProvider.describe().print(System.out);
    }
}
