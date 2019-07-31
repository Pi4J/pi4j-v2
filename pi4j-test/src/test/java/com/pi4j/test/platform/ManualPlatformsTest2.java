package com.pi4j.test.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  ManualPlatformsTest2.java
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManualPlatformsTest2 {

    private Context pi4j;

    @Before
    public void beforeTest() throws Pi4JException {
        // Initialize Pi4J with AUTO-DETECT disabled
        // we don't want to load any detected Pi4J
        // binding/provider/platform libraries
        // in the class path for this test case
        pi4j = Pi4J.initialize(false);
    }

    @After
    public void afterTest() throws Pi4JException {
        pi4j.shutdown();
    }

    @Test
    public void testPlatformsNotNull() throws Pi4JException {
        // ensure that the io collection in the Pi4J context is not NULL
        assertNotNull(pi4j.providers());
    }

    @Test
    public void testPlatformsCount() throws Exception {

        // create our own custom platform implementation classes
        TestPlatform platform1 = new TestPlatform("test-platform-1", "Test Platform #1");
        TestPlatform platform2 = new TestPlatform("test-platform-2", "Test Platform #2");
        TestPlatform platform3 = new TestPlatform("test-platform-3", "Test Platform #3");
        TestPlatform platform4 = new TestPlatform("test-platform-4", "Test Platform #4");
        TestPlatform platform5 = new TestPlatform("test-platform-5", "Test Platform #5");

        // add the custom platform to the Pi4J context
        pi4j.platforms().add(platform1, platform2, platform3, platform4, platform5);

        // ensure that no io were detected/loaded into the Pi4J context
        assertEquals(5, pi4j.platforms().all().size());

        // print out the detected Pi4J io libraries found on the class path
        pi4j.describe().print(System.out);

        // set the default platforms in the platforms manager
        pi4j.platforms().setDefault("test-platform-2");

        // test the ensure the Pi4J includes a default platform
        assertNotNull(pi4j.platforms().getDefault());

        // test the ensure the Pi4J includes the configured default platform
        assertSame(platform2, pi4j.platforms().getDefault());
    }
}
