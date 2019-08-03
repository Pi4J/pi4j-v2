package com.pi4j.test.annotations;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  DependencyInjectionRegisterCustomProviderTest.java
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

public class DependencyInjectionRegisterCustomProviderTest {

    // TODO :: REVISIT THE IMPLEMENTATION ON @Register OF <Provider> INSTANCES FOR IMMUTABLE CONTEXT


//    // create our own custom provider implementation classes
//    // use the "@Register" annotation to automatically register this custom provider
//
//    @Register
//    PwmProvider pwmProvider = new TestPwmProvider();
//
//    @Register
//    AnalogInputProvider ainProvider = new TestAnalogInputProvider();
//
//    @Inject
//    Context pi4j;
//
//    @Before
//    public void beforeTest() throws Pi4JException {
//        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
//
//        // Initialize Pi4J with an empty context
//        // An empty context disables AUTO-DETECT loading
//        // which will not load any detected Pi4J binding libraries
//        // in the class path for this test case
//        // ...
//        // Also, inject this class instance into the Pi4J context
//        // for annotation processing and dependency injection
//        Pi4J.newContext().inject(this);
//    }
//
//    @After
//    public void afterTest() {
//        try {
//            pi4j.shutdown();
//        } catch (Pi4JException e) { /* do nothing */ }
//    }
//
//    @Test
//    public void testDIRegisterCustomProviderNotNull() throws Pi4JException {
//        assertNotNull(pi4j);
//        assertNotNull(pi4j.providers());
//    }
//
//    @Test
//    public void testDIRegisterCustomProviderNotEmpty() throws Exception {
//        // ensure that 1 or more providers were detected/loaded into the Pi4J context
//        assertFalse(pi4j.providers().all().isEmpty());
//
//        System.out.println("-------------------------------------------------");
//        System.out.println(this.getClass().getSimpleName());
//        System.out.println("Pi4J I/O CUSTOM PROVIDERS DETECTED");
//        System.out.println("-------------------------------------------------");
//        pi4j.providers().describe().print(System.out);
//    }
}
