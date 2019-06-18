package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutput.java
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

import com.pi4j.io.Output;
import com.pi4j.io.gpio.analog.impl.AnalogOutputFactory;
import com.pi4j.provider.exception.ProviderException;

public interface AnalogOutput extends Analog<AnalogOutput, AnalogOutputConfig>, Output {
    AnalogOutput value(Number value);

    default AnalogOutput setValue(Number value) { return value(value); };

    static AnalogOutput instance(AnalogOutputConfig config) throws ProviderException {
        return AnalogOutputFactory.instance(config);
    }

    static AnalogOutput instance(int address) throws ProviderException {
        return AnalogOutputFactory.instance(address);
    }

    static AnalogOutput instance(String providerId, int address) throws ProviderException {
        return AnalogOutputFactory.instance(providerId, address);
    }

    static AnalogOutput instance(String providerId, AnalogOutputConfig config) throws ProviderException {
        return AnalogOutputFactory.instance(providerId, config);
    }

    static AnalogOutput instance(AnalogOutputProvider provider, int address) throws ProviderException {
        return AnalogOutputFactory.instance(provider, address);
    }

    static AnalogOutput instance(AnalogOutputProvider provider, AnalogOutputConfig config) throws ProviderException {
        return AnalogOutputFactory.instance(provider, config);
    }
}
