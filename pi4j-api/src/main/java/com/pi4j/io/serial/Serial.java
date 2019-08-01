package com.pi4j.io.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Serial.java
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

import com.pi4j.context.Context;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.IO;
import com.pi4j.io.serial.impl.SerialFactory;
import com.pi4j.provider.exception.ProviderException;

import java.io.IOException;

public interface Serial extends IO<Serial, SerialConfig>, AutoCloseable {

    static final String ID = "SERIAL";

    static final int DEFAULT_BAUD = 9600;
    static final String DEFAULT_DEVICE = "{DEFAULT}";

    static Serial instance(Context context, SerialConfig config) throws ProviderException, NotInitializedException {
        return SerialFactory.instance(context, config);
    }

    static Serial instance(Context context, String device) throws ProviderException, NotInitializedException {
        return SerialFactory.instance(context, device);
    }

    static Serial instance(Context context, String device, int baud) throws ProviderException, NotInitializedException {
        return SerialFactory.instance(context, device, baud);
    }

    static Serial instance(Context context, String providerId, String device) throws ProviderException, NotInitializedException {
        return SerialFactory.instance(context, providerId, device);
    }

    static Serial instance(Context context, String providerId, String device, int baud) throws ProviderException, NotInitializedException {
        return SerialFactory.instance(context, providerId, device);
    }

    static Serial instance(Context context, String providerId, SerialConfig config) throws ProviderException, NotInitializedException {
        return SerialFactory.instance(context, providerId, config);
    }

    static Serial instance(SerialProvider provider, String device) throws ProviderException {
        return SerialFactory.instance(provider, device);
    }

    static Serial instance(SerialProvider provider, String device, int baud) throws ProviderException {
        return SerialFactory.instance(provider, device);
    }

    static Serial instance(SerialProvider provider, SerialConfig config) throws ProviderException {
        return SerialFactory.instance(provider, config);
    }


    public void open() throws IOException;
    public void close() throws IOException;
    public void send(CharSequence data);
}
