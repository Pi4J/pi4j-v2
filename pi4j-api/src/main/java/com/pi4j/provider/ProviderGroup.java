package com.pi4j.provider;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  ProviderGroup.java
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

import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.io.IOType;
import com.pi4j.provider.exception.ProviderException;

import java.util.Map;

public class ProviderGroup<T extends Provider> implements Describable {

    private IOType type = null;
    private Providers providers;

    /**
     * Default Constructor
     * @param type
     */
    public ProviderGroup(Providers providers, IOType type){
        this.providers = providers;
        this.type = type;
    }
    private Map<String, T> all() throws ProviderException {
        return providers.all(type);
    }

    public T get(String providerId) throws ProviderException {
        return providers.get(providerId, type);
    }

    public boolean exists(String providerId) throws ProviderException {
        return providers.exists(providerId, type);
    }

    @Override
    public Descriptor describe() {
        Descriptor descriptor = Descriptor.create()
                .category("PROVIDER GROUP")
                .name("Provider Group")
                .type(this.getClass());
        Map<String, T> all = null;
        try {
            all = all();
            all.forEach((id, provider)->{
                descriptor.add(provider.describe());
            });
        } catch (ProviderException e) {
            e.printStackTrace();
        }
        return descriptor;
    }
}
