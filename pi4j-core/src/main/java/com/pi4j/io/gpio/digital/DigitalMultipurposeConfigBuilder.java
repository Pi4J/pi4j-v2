package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalMultipurposeConfigBuilder.java
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
import com.pi4j.io.gpio.digital.impl.DefaultDigitalMultipurposeConfigBuilder;

import java.util.concurrent.TimeUnit;

/**
 * <p>DigitalInputConfigBuilder interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface DigitalMultipurposeConfigBuilder extends DigitalConfigBuilder<DigitalMultipurposeConfigBuilder, DigitalMultipurposeConfig> {
    /**
     * <p>pull.</p>
     *
     * @param value a {@link PullResistance} object.
     * @return a {@link DigitalMultipurposeConfigBuilder} object.
     */
    DigitalMultipurposeConfigBuilder pull(PullResistance value);

    /**
     * <p>debounce.</p>
     *
     * @param microseconds a {@link Long} object.
     * @return a {@link DigitalMultipurposeConfigBuilder} object.
     */
    DigitalMultipurposeConfigBuilder debounce(Long microseconds);
    /**
     * <p>debounce.</p>
     *
     * @param interval a {@link Long} object.
     * @param units a {@link TimeUnit} object.
     * @return a {@link DigitalMultipurposeConfigBuilder} object.
     */
    DigitalMultipurposeConfigBuilder debounce(Long interval, TimeUnit units);

    /**
     * <p>shutdown.</p>
     *
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalMultipurposeConfigBuilder} object.
     */
    DigitalMultipurposeConfigBuilder shutdown(DigitalState state);

    /**
     * <p>initial state.</p>
     *
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalMultipurposeConfigBuilder} object.
     */
    DigitalMultipurposeConfigBuilder initial(DigitalState state);

    /**
     * <p>initial mode.</p>
     *
     * @param  mode a {@link com.pi4j.io.gpio.digital.DigitalMode} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalMultipurposeConfigBuilder} object.
     */
    DigitalMultipurposeConfigBuilder mode(DigitalMode mode);

    /**
     * <p>newInstance.</p>
     *
     * @param context {@link Context}
     * @return a {@link DefaultDigitalMultipurposeConfigBuilder} object.
     */
    static DigitalMultipurposeConfigBuilder newInstance(Context context)  {
        return DefaultDigitalMultipurposeConfigBuilder.newInstance(context);
    }
}
