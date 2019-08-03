package com.pi4j.test.provider;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: UNITTEST :: Unit/Integration Tests
 * FILENAME      :  TestAnalogOutput.java
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

import com.pi4j.common.Metadata;
import com.pi4j.common.exception.LifecycleException;
import com.pi4j.context.Context;
import com.pi4j.io.exception.IOBoundsException;
import com.pi4j.io.exception.IOIllegalValueException;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogOutputConfig;
import com.pi4j.io.gpio.analog.binding.AnalogBinding;
import com.pi4j.provider.Provider;

public class TestAnalogOutput implements AnalogOutput {

    @Override
    public AnalogOutput value(Integer value) throws IOIllegalValueException, IOBoundsException {
        return null;
    }

    @Override
    public AnalogOutput stepUp() {
        return null;
    }

    @Override
    public AnalogOutput stepDown() {
        return null;
    }

    @Override
    public AnalogOutput step(Integer value) throws IOIllegalValueException, IOBoundsException {
        return null;
    }

    @Override
    public Integer value() {
        return null;
    }

    @Override
    public AnalogOutput addListener(AnalogChangeListener... listener) {
        return null;
    }

    @Override
    public AnalogOutput removeListener(AnalogChangeListener... listener) {
        return null;
    }

    @Override
    public AnalogOutput bind(AnalogBinding... binding) {
        return null;
    }

    @Override
    public AnalogOutput unbind(AnalogBinding... binding) {
        return null;
    }

    @Override
    public AnalogOutputConfig config() {
        return null;
    }

    @Override
    public AnalogOutput name(String name) {
        return null;
    }

    @Override
    public AnalogOutput description(String description) {
        return null;
    }

    @Override
    public Provider provider() {
        return null;
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Metadata metadata() {
        return null;
    }

    @Override
    public Object initialize(Context context) throws LifecycleException {
        return null;
    }

    @Override
    public Object shutdown(Context context) throws LifecycleException {
        return null;
    }
}
