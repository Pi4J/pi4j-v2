package com.pi4j.test.provider;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  TestDigitalOutputProvider.java
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

import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.test.provider.impl.TestDigitalOutputProviderImpl;

/**
 * <p>TestDigitalInputProvider interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface TestDigitalOutputProvider extends DigitalOutputProvider {
    /**
     * <p>newInstance.</p>
     *
     * @return a {@link TestDigitalOutputProvider} object.
     */
    static TestDigitalOutputProvider newInstance(){
        return new TestDigitalOutputProviderImpl();
    }
    /**
     * <p>newInstance.</p>
     *
     * @param id a {@link String} object.
     * @return a {@link TestDigitalOutputProvider} object.
     */
    static TestDigitalOutputProvider newInstance(String id){
        return new TestDigitalOutputProviderImpl(id);
    }
    /**
     * <p>newInstance.</p>
     *
     * @param id a {@link String} object.
     * @param name a {@link String} object.
     * @return a {@link TestDigitalOutputProvider} object.
     */
    static TestDigitalOutputProvider newInstance(String id, String name){
        return new TestDigitalOutputProviderImpl(id, name);
    }

    /**
     * <p>create.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws Exception if any.
     */
    default <T extends DigitalOutput> T create() throws Exception {
        return create(0);
    }
}
