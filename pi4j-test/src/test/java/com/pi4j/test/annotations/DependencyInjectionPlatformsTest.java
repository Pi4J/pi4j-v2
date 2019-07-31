package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  DependencyInjectionPlatformsTest.java
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
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import com.pi4j.platform.Platforms;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;

public class DependencyInjectionPlatformsTest {

    @Inject
    Context pi4j;

    @Inject
    Platforms platforms;

    @Before
    public void beforeTest() throws Pi4JException {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        // Initialize Pi4J with AUTO-DETECT enabled
        // we want to load all detected Pi4J binding/io libraries
        // in the class path for this test case
        // Also, inject this class instance into the Pi4J context
        // for annotation processing and dependency injection
        Pi4J.initialize().inject(this);
    }

    @After
    public void afterTest() {
        try {
            pi4j.shutdown();
        } catch (Pi4JException e) { /* do nothing */ }
    }

    @Test
    public void testDIPlatformsNotNull() throws Pi4JException {
        assertNotNull(platforms);
    }

    @Test
    public void testDIPlatforms() throws Exception {

        // ensure that 1 or more platforms were detected/loaded into the Pi4J context
        assertFalse(platforms.all().isEmpty());

        System.out.println("-------------------------------------------------");
        System.out.println(this.getClass().getSimpleName());
        System.out.println("Pi4J I/O PROVIDERS ACQUIRED");
        System.out.println("-------------------------------------------------");
        platforms.describe().print(System.out);
    }
}
