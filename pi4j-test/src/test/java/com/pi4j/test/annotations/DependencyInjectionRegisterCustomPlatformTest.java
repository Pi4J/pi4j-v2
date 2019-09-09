package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  DependencyInjectionRegisterCustomPlatformTest.java
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

public class DependencyInjectionRegisterCustomPlatformTest {

    // TODO :: REVISIT THE IMPLEMENTATION ON @Register OF <Platform> INSTANCES FOR IMMUTABLE CONTEXT

//    // create our own custom platform implementation classes
//    // use the "@Register" annotation to automatically register this custom platform
//
//    @Register
//    Platform platform = new TestPlatform("test-platform", "My Test Platform");
//
//    @Inject
//    Context pi4j;
//
//    @Before
//    public void beforeTest() throws Pi4JException {
//        System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
//
//        // Initialize Pi4J with an empty context
//        // An empty context disables AUTO-DETECT loading
//        // which will load all detected Pi4J extension libraries
//        // (Platforms and Providers) in the class path
//        // ...
//        // Also, inject this class instance into the Pi4J context
//        // for annotation processing and dependency injection
//        Pi4J.newContext().inject(this);
//    }
//
//    @After
//    public void afterTest() throws Pi4JException {
//        pi4j.shutdown();
//    }
//
//    @Test
//    public void testDIRegisterCustomPlatformsNotNull() throws Pi4JException {
//        assertNotNull(pi4j);
//        assertNotNull(pi4j.platforms());
//    }
//
//    @Test
//    public void testDIRegisterCustomPlatformsNotEmpty() throws Exception {
//        About about = new About();
//
//        // ensure that 1 or more providers were detected/loaded into the Pi4J context
//        assertFalse(pi4j.platforms().all().isEmpty());
//
//        System.out.println("-------------------------------------------------");
//        System.out.println(this.getClass().getSimpleName());
//        System.out.println("Pi4J PLATFORMS DETECTED");
//        System.out.println("-------------------------------------------------");
//        pi4j.platforms().describe().print(System.out);
//    }
//
//    @Test
//    public void testDIRegisterCustomPlatformExists() throws Exception {
//        // ensure that out specific Test platform exists
//        assertTrue("A platform with id 'test-platform' could not be found.",
//                pi4j.platforms().exists("test-platform"));
//    }
//
//    @Test
//    public void testDIRegisterFakePlatformNotExists() throws Exception {
//        // ensure that a named fake platform does not exist
//        assertFalse("A platform with id 'fake-platform' could not be found.",
//                pi4j.platforms().exists("fake-platform"));
//    }
//
//    @Test(expected = PlatformNotFoundException.class)
//    public void testDIRegisterFakePlatformNotExists2() throws Exception {
//        // ensure that a named fake platform does not exist
//        Platform p = pi4j.platforms().get("fake-platform");
//    }
//
//    @Test
//    public void testDIRegisterCustomPlatformDefault() throws Exception {
//        // ensure that out specific Test platform exists as the default platform
//        assertSame(platform, pi4j.platform());
//    }
}
