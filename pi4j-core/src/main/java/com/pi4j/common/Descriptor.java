package com.pi4j.common;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Descriptor.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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

import com.pi4j.common.impl.DescriptorImpl;

import java.io.PrintStream;

/**
 * <p>Descriptor interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Descriptor {
    /**
     * <p>id.</p>
     *
     * @param id a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor id(String id);
    /**
     * <p>name.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor name(String name);
    /**
     * <p>description.</p>
     *
     * @param description a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor description(String description);
    /**
     * <p>category.</p>
     *
     * @param category a {@link java.lang.String} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor category(String category);
    /**
     * <p>quantity.</p>
     *
     * @param quantity a {@link java.lang.Integer} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor quantity(Integer quantity);
    /**
     * <p>type.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor type(Class type);

    /**
     * <p>parent.</p>
     *
     * @param parent a {@link com.pi4j.common.Descriptor} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor parent(Descriptor parent);

    /**
     * <p>value.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor value(Object value);

    /**
     * <p>id.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String id();
    /**
     * <p>name.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String name();
    /**
     * <p>category.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String category();
    /**
     * <p>description.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String description();
    /**
     * <p>quantity.</p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    Integer quantity();
    /**
     * <p>value.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    Object value();
    /**
     * <p>type.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    Class type();
    /**
     * <p>parent.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor parent();

    /**
     * <p>create.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    static Descriptor create(){
        return new DescriptorImpl();
    }

    /**
     * <p>add.</p>
     *
     * @param descriptor a {@link com.pi4j.common.Descriptor} object.
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    Descriptor add(Descriptor descriptor);

    /**
     * <p>size.</p>
     *
     * @return a int.
     */
    int size();
    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    boolean isEmpty();
    /**
     * <p>isNotEmpty.</p>
     *
     * @return a boolean.
     */
    boolean isNotEmpty();

    /**
     * <p>print.</p>
     *
     * @param stream a {@link java.io.PrintStream} object.
     */
    void print(PrintStream stream);
}
