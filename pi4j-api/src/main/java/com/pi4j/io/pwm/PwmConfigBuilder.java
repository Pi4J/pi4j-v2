package com.pi4j.io.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  PwmConfigBuilder.java
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

import com.pi4j.io.gpio.GpioConfigBuilder;
import com.pi4j.io.pwm.impl.DefaultPwmConfigBuilder;

public interface PwmConfigBuilder extends GpioConfigBuilder<PwmConfigBuilder, PwmConfig> {
    static PwmConfigBuilder newInstance()  {
        return DefaultPwmConfigBuilder.newInstance();
    }

    PwmConfigBuilder range(Integer range);
    PwmConfigBuilder frequency(Integer frequency);
    PwmConfigBuilder dutyCycle(Integer dutyCycle);
    PwmConfigBuilder dutyCyclePercent(Integer percent);
    PwmConfigBuilder pwmType(PwmType pwmType);
    PwmConfigBuilder shutdown(Integer value);
    PwmConfigBuilder initial(Integer value);
}
