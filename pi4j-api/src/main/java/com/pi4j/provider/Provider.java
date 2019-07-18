package com.pi4j.provider;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Provider.java
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

import com.pi4j.binding.Binding;
import com.pi4j.common.Descriptor;
import com.pi4j.config.Config;
import com.pi4j.context.Context;
import com.pi4j.io.IO;

public interface Provider<IO_TYPE extends IO, CONFIG_TYPE extends Config> extends Binding {

    IO_TYPE register(Context context, CONFIG_TYPE config) throws Exception;

    default ProviderType type() { return ProviderType.getProviderType(this.getClass()); };
    default ProviderType getType() { return type(); }
    default boolean isType(ProviderType type) { return this.type().isType(type); }

    @Override
    default Descriptor describe() {
        Descriptor descriptor = Binding.super.describe();
        descriptor.category(this.type().name());
        return descriptor;
    }
}
