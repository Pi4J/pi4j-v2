package com.pi4j.test.platform;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  NoPlatformsTest.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * 
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.Pi4JException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class NoPlatformsTest {

    private Context pi4j;

    @BeforeAll
    public void beforeTest() throws Pi4JException {
        // Initialize Pi4J with an empty context
        // An empty context disables AUTO-DETECT loading
        // which will not load any detected Pi4J extension
        // libraries (Platforms and Providers) from the class path
        pi4j = Pi4J.newContext();
    }

    @AfterAll
    public void afterTest() throws Pi4JException {
        pi4j.shutdown();
    }

    @Test
    public void testPlatformsNotNull() throws Pi4JException {
        // ensure that the platforms collection in the Pi4J context is not NULL
        assertNotNull(pi4j.platforms());
    }

    @Test
    public void testPlatformsEmpty() throws Pi4JException {
        // ensure that no platforms were detected/loaded into the Pi4J context
        assertTrue(pi4j.platforms().all().isEmpty());
    }
}
