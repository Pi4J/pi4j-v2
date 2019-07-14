package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutputConfig.java
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

public interface AnalogOutputConfig
        extends AnalogConfig<AnalogOutputConfig> {

    String SHUTDOWN_VALUE_KEY = "shutdown";
    String INITIAL_VALUE_KEY = "initial";
    String STEP_VALUE_KEY = "step";

    Integer shutdownValue();
    AnalogOutputConfig shutdownValue(Integer value);
    default Number getShutdownValue() {
        return this.shutdownValue();
    }
    default void setShutdownValue(Integer value) {
        this.shutdownValue(value);
    }

    Integer stepValue();
    AnalogOutputConfig stepValue(Integer value);
    default Number getStepValue() {
        return this.stepValue();
    }
    default void setStepValue(Integer value) {
        this.setStepValue(value);
    }

    Integer initialValue();
    default Number getInitialValue() {
        return this.initialValue();
    }
}
