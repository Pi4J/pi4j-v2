package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  DependencyInjectionProviderDefaultTest.java
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
import com.pi4j.annotation.Inject;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.test.About;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class DependencyInjectionProviderDefaultTest {

    @Inject
    Context pi4j;

    @Inject
    PwmProvider pwmProvider;

    @Inject
    AnalogInputProvider ainProvider;

    @BeforeAll
    public void beforeTest() throws Pi4JException {
        System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        // initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        // ...
        // Also, inject this class instance into the Pi4J context
        // for annotation processing and dependency injection
        Pi4J.newAutoContext().inject(this);
    }

    @AfterAll
    public void afterTest() {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testDIProviderDefaultNotNull() throws Pi4JException {
        assertNotNull(pwmProvider);
        assertNotNull(ainProvider);
    }

    @Test
    public void testDIProviderDefault() throws Exception {
        About about = new About();

        // ensure that 1 or more providers were detected/loaded into the Pi4J context
        assertFalse(pi4j.providers().all().isEmpty());

        System.out.println("-------------------------------------------------");
        System.out.println(this.getClass().getSimpleName());
        System.out.println("Pi4J I/O PROVIDERS ACQUIRED");
        System.out.println("-------------------------------------------------");
        pwmProvider.describe().print(System.out);
        ainProvider.describe().print(System.out);
    }
}
