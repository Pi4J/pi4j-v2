package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalMultipurposeConfig.java
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

/**
 * <p>DigitalInputConfig interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface DigitalMultipurposeConfig extends DigitalConfig<DigitalMultipurposeConfig> {

    /** Constant <code>PULL_RESISTANCE_KEY="pull"</code> */
    String PULL_RESISTANCE_KEY = DigitalInputConfig.PULL_RESISTANCE_KEY;
    /** Constant <code>DEBOUNCE_RESISTANCE_KEY="debounce"</code> */
    String DEBOUNCE_RESISTANCE_KEY = DigitalInputConfig.DEBOUNCE_RESISTANCE_KEY;
    /** Constant <code>SHUTDOWN_STATE_KEY="shutdown"</code> */
    String SHUTDOWN_STATE_KEY = DigitalOutputConfig.SHUTDOWN_STATE_KEY;
    /** Constant <code>INITIAL_STATE_KEY="initial"</code> */
    String INITIAL_STATE_KEY = DigitalOutputConfig.INITIAL_STATE_KEY;
    /** Constant <code>INITIAL_MODE_KEY="mode"</code> */
    String INITIAL_MODE_KEY = "mode";

    /**
     * <p>pull.</p>
     *
     * @return a {@link PullResistance} object.
     */
    PullResistance pull();
    /**
     * <p>getPull.</p>
     *
     * @return a {@link PullResistance} object.
     */
    default PullResistance getPull(){
        return pull();
    }

    /**
     * <p>debounce.</p>
     *
     * @return a {@link Long} object.
     */
    Long debounce();
    /**
     * <p>getDebounce.</p>
     *
     * @return a {@link Long} object.
     */
    default Long getDebounce(){ return debounce(); }

    /**
     * <p>shutdownState.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     */
    DigitalState shutdownState();
    /**
     * <p>shutdownState.</p>
     *
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutputConfig} object.
     */
    DigitalMultipurposeConfig shutdownState(DigitalState state);

    /**
     * <p>getShutdownState.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     */
    default DigitalState getShutdownState(){
        return shutdownState();
    }

    /**
     * <p>setShutdownState.</p>
     *
     * @param state a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     */
    default void setShutdownState(DigitalState state){
        this.shutdownState(state);
    }

    /**
     * <p>initialState.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     */
    DigitalState initialState();

    /**
     * <p>getInitialState.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     */
    default DigitalState getInitialState(){
        return initialState();
    }

    /**
     * <p>initialMode.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalMode} object.
     */
    DigitalMode initialMode();

    /**
     * <p>getInitialMode.</p>
     *
     * @return a {@link com.pi4j.io.gpio.digital.DigitalState} object.
     */
    default DigitalMode getInitialMode(){
        return initialMode();
    }

    /**
     * <p>newBuilder.</p>
     *
     * @param context {@link Context}
     * @return a {@link DigitalMultipurposeConfigBuilder} object.
     */
    static DigitalMultipurposeConfigBuilder newBuilder(Context context)  {
        return DigitalMultipurposeConfigBuilder.newInstance(context);
    }
}
