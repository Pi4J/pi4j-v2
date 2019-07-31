package com.pi4j.binding;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Bindings.java
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

import com.pi4j.binding.exception.BindingException;
import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.context.Context;
import com.pi4j.provider.exception.ProviderException;

import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public interface Bindings extends Describable {

    /**
     * Get all bindings
     * @return
     */
    Map<String, Binding> all();

    /**
     * Get all providers of a specified io class/interface.
     *
     * @param bindingClass
     * @param <T>
     * @return
     * @throws ProviderException
     */
    <T extends Binding> Map<String, T> all(Class<T> bindingClass) throws BindingException;

    boolean exists(String bindingId) throws BindingException;
    <T extends Binding> boolean exists(String bindingId, Class<T> bindingClass) throws BindingException;
    Binding get(String bindingId) throws BindingException;
    <T extends Binding> T get(String bindingId, Class<T> bindingClass) throws BindingException;
    <T extends Binding> Bindings add(T... binding) throws BindingException;
    <T extends Binding> void replace(T binding) throws BindingException;
    <T extends Binding> void remove(String bindingId) throws BindingException;
    void initialize(Context context, boolean autoDetect) throws BindingException;
    void shutdown(Context context) throws BindingException;

    // DEFAULT METHODS
    default Map<String, Binding> getAll() { return all(); }
    default <T extends Binding> Map<String, T> getAll(Class<T> bindingClass) throws BindingException {
        return all(bindingClass);
    }

    default Descriptor describe() {
        var bindings = all();

        Descriptor descriptor = Descriptor.create()
                .category("BINDINGS")
                .name("Plugins & Extensions")
                .quantity((bindings == null) ? 0 : bindings.size())
                .type(this.getClass());

        if(bindings != null && !bindings.isEmpty()) {
            bindings.forEach((id, binding) -> {
                descriptor.add(binding.describe());
            });

        }
        return descriptor;
    }
}
