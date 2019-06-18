package com.pi4j.io;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  IO.java
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

import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.provider.exception.ProviderException;

import java.io.PrintStream;

public interface IO {

    // MARKER INTERFACE

    static Serial instance(SerialConfig config) throws ProviderException {
        return Serial.instance(config);
    }

    static Pwm instance(PwmConfig config) throws ProviderException {
        return Pwm.instance(config);
    }

    static Spi instance(SpiConfig config) throws ProviderException {
        return Spi.instance(config);
    }

    static I2C instance(I2CConfig config) throws ProviderException {
        return I2C.instance(config);
    }

    default void print(PrintStream stream) {
        stream.print(getClass().getName());
    }

    default void println(PrintStream stream) {
        print(stream);
        stream.println();
    }
}
