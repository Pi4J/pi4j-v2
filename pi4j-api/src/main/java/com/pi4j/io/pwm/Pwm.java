package com.pi4j.io.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Pwm.java
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
import com.pi4j.io.pwm.impl.PwmFactory;
import com.pi4j.provider.exception.ProviderException;

public interface Pwm extends IO<Pwm, PwmConfig> {

    static final String ID = "PWM";

    static Pwm instance(Context context, PwmConfig config) throws ProviderException, NotInitializedException {
        return PwmFactory.instance(context, config);
    }

    static Pwm instance(Context context, int address) throws ProviderException, NotInitializedException {
        return PwmFactory.instance(context, address);
    }

    static Pwm instance(Context context, String providerId, int address) throws ProviderException, NotInitializedException {
        return PwmFactory.instance(context, providerId, address);
    }

    static Pwm instance(Context context, String providerId, PwmConfig config) throws ProviderException, NotInitializedException {
        return PwmFactory.instance(context, providerId, config);
    }

    static Pwm instance(Context context, PwmProvider provider, int address) throws ProviderException {
        return PwmFactory.instance(context, provider, address);
    }

    static Pwm instance(Context context, PwmProvider provider, PwmConfig config) throws ProviderException {
        return PwmFactory.instance(context, provider, config);
    }
}
