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
import com.pi4j.config.Config;
import com.pi4j.io.IO;
import com.pi4j.util.Descriptor;
import com.pi4j.util.StringUtil;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

public interface Provider<IO_TYPE extends IO, CONFIG_TYPE extends Config> extends Binding {
    IO_TYPE instance(CONFIG_TYPE config) throws Exception;
    Collection<IO_TYPE> instances();

    default ProviderType type() { return ProviderType.getProviderType(this.getClass()); };
    default ProviderType getType() { return type(); }
    default boolean isType(ProviderType type) { return this.type().isType(type); }

    default void print(PrintStream stream) {
        stream.print(getId());
        stream.print(" <");
        stream.print(getType().name());
        stream.print(">");
        stream.print(" (");
        stream.print(getClass().getName());
        stream.print(")");
    }

    default void println(PrintStream stream) {
        print(stream);
        stream.println();
    }

    @Override
    default void describe(Descriptor descriptor) {
        var child = descriptor.add(name() + " (" + id() + ") <" + getClass().getName() + ">");
        var instances = instances();
        if(instances != null && !instances.isEmpty()) {
            instances().forEach((instance) -> {
                instance.describe(child);
            });
        }
    }

    @Override
    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create("-----------------------------------\r\n" + "Pi4J - Provider Information\r\n" + "-----------------------------------");
        describe(descriptor);
        return descriptor;
    }
}
