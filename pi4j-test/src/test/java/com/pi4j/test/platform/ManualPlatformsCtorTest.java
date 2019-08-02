package com.pi4j.test.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  ManualPlatformsCtorTest.java
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
import com.pi4j.test.provider.TestI2CProvider;
import com.pi4j.test.provider.TestPwmProvider;
import com.pi4j.test.provider.TestSerialProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ManualPlatformsCtorTest {

    private Context pi4j;

    @Before
    public void beforeTest() throws Pi4JException {

        // create our own custom provider implementation classes
        var pwmProvider = new TestPwmProvider();
        var i2CProvider = new TestI2CProvider();
        var serialProvider = new TestSerialProvider();

        // create our own custom platform implementation classes
        var testPlatform = new TestPlatform("test-platform", "My Test Platform");
        testPlatform.setProviders(pwmProvider, i2CProvider);

        // Initialize Pi4J with a manually configured context
        // ...
        // Explicitly add the default platforms into this
        // context for testing
        // ...
        // Explicitly add the test providers into the
        // context for testing
        pi4j = Pi4J.newContextBuilder()
                .addDefaultPlatform(testPlatform)
                .add(pwmProvider, i2CProvider, serialProvider)
                .build();

    }

    @After
    public void afterTest() throws Pi4JException {
        pi4j.shutdown();
    }

    @Test
    public void testPlatformsNotNull() throws Pi4JException {
        // ensure that the 'platforms' collection in the Pi4J context is not NULL
        assertNotNull(pi4j.platforms());
    }

    @Test
    public void testProvidersNotNull() throws Pi4JException {
        // ensure that the 'providers' collection in the Pi4J context is not NULL
        assertNotNull(pi4j.providers());
    }

    @Test
    public void testPlatformCount() throws Exception {

        // ensure that only one platform was detected/loaded into the Pi4J context
        assertEquals(pi4j.platforms().all().size(), 1);

        // ensure that only 3 providers were detected/loaded into the Pi4J context
        assertEquals(pi4j.providers().all().size(), 3);

        // print out the detected Pi4J platforms
        pi4j.platforms().describe().print(System.out);

        // print out the detected Pi4J providers
        pi4j.providers().describe().print(System.out);
    }

    @Test
    public void testPlatformProviderCount() throws Exception {

        // ensure that exactly 2 providers are associated with the single default platform in the Pi4J context
        assertEquals(2, pi4j.platform().providers().size());

        // print out the detected Pi4J platforms
        pi4j.platform().describe().print(System.out);
    }

    @Test
    public void testPlatformsRemove() throws Exception {

        // remove the second custom serial provider
        pi4j.platforms().remove("test-platform");

        // ensure the correct provider count
        assertEquals(0, pi4j.platforms().all().size());

        // print out the detected Pi4J platforms
        pi4j.platforms().describe().print(System.out);
    }
}
