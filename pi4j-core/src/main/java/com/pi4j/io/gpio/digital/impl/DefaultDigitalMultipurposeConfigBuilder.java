package com.pi4j.io.gpio.digital.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultDigitalMultipurposeConfigBuilder.java
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

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;

import java.util.concurrent.TimeUnit;

/**
 * <p>DefaultDigitalInputConfigBuilder class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultDigitalMultipurposeConfigBuilder
        extends DigitalConfigBuilderBase<DigitalMultipurposeConfigBuilder, DigitalMultipurposeConfig>
        implements DigitalMultipurposeConfigBuilder {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultDigitalMultipurposeConfigBuilder(Context context){
        super(context);
    }

    /**
     * <p>newInstance.</p>
     *
     * @return a {@link DigitalInputConfigBuilder} object.
     */
    public static DigitalMultipurposeConfigBuilder newInstance(Context context) {
        return new DefaultDigitalMultipurposeConfigBuilder(context);
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfig build() {
        DigitalMultipurposeConfig config = new DefaultDigitalMultipurposeConfig(getResolvedProperties());
        return config;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfigBuilder pull(PullResistance value) {
        this.properties.put(DigitalInputConfig.PULL_RESISTANCE_KEY, value.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfigBuilder debounce(Long microseconds) {
        if(microseconds != null) {
            this.properties.put(DigitalInputConfig.DEBOUNCE_RESISTANCE_KEY, microseconds.toString());
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfigBuilder shutdown(DigitalState state) {
        this.properties.put(DigitalMultipurposeConfig.SHUTDOWN_STATE_KEY, state.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfigBuilder initial(DigitalState state) {
        this.properties.put(DigitalMultipurposeConfig.INITIAL_STATE_KEY, state.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfigBuilder mode(DigitalMode mode) {
        this.properties.put(DigitalMultipurposeConfig.INITIAL_MODE_KEY, mode.toString());
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalMultipurposeConfigBuilder debounce(Long interval, TimeUnit units) {
        return debounce(units.toMicros(interval));
    }
}
