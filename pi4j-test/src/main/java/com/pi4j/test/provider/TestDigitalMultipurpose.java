package com.pi4j.test.provider;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  TestDigitalMultipurpose.java
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

import com.pi4j.io.gpio.digital.*;

/**
 * <p>TestDigitalMultipurpose class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class TestDigitalMultipurpose extends DigitalMultipurposeBase implements DigitalMultipurpose {

    /** {@inheritDoc} */
    @Override
    public DigitalState state() {
        return this.state;
    }

    /**
     * <p>Constructor for TestDigitalInput.</p>
     *
     * @param provider a {@link DigitalInputProvider} object.
     * @param config a {@link DigitalInputConfig} object.
     */
    public TestDigitalMultipurpose(DigitalMultipurposeProvider provider, DigitalMultipurposeConfig config){
        super(provider, config);
    }
}
