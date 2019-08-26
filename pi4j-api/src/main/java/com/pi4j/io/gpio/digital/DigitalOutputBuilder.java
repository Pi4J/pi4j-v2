package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalOutputBuilder.java
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
import com.pi4j.io.gpio.digital.impl.DefaultDigitalOutputBuilder;
import com.pi4j.platform.Platform;
import com.pi4j.provider.Provider;

public interface DigitalOutputBuilder {

    DigitalOutputBuilder id(String id);
    DigitalOutputBuilder name(String name);
    DigitalOutputBuilder description(String description);
    DigitalOutputBuilder address(Integer address);
    DigitalOutputBuilder shutdown(DigitalState state);
    DigitalOutputBuilder initial(DigitalState state);

    DigitalOutputBuilder platform(String platformId);
    DigitalOutputBuilder platform(Class<? extends Platform> platformClass);
    DigitalOutputBuilder provider(String providerId);
    DigitalOutputBuilder provider(Class<? extends Provider> providerClass);

    static DigitalOutputBuilder newInstance(Context context)  {
        return DefaultDigitalOutputBuilder.newInstance(context);
    }

    DigitalOutput build() throws Exception;
}
