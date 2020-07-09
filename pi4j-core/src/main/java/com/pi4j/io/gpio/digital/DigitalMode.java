package com.pi4j.io.gpio.digital;

import java.util.EnumSet;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalMode.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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

/**
 * Digital Mode Enumerations
 *
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum DigitalMode {

    UNKNOWN(-1, "UNKNOWN"),
    INPUT(0, "INPUT"),
    OUTPUT(1, "OUTPUT");

    private final Integer value;
    private final String name;

    private DigitalMode(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * <p>isInput.</p>
     *
     * @return a boolean.
     */
    public boolean isInput() {
        return (this == INPUT);
    }

    /**
     * <p>input.</p>
     *
     * @return a boolean.
     */
    public boolean input() {
        return (this.isInput());
    }

    /**
     * <p>isOutput.</p>
     *
     * @return a boolean.
     */
    public boolean isOutput() {
        return (this == OUTPUT);
    }

    /**
     * <p>output.</p>
     *
     * @return a boolean.
     */
    public boolean output() {
        return (this.isOutput());
    }

    /**
     * <p>value.</p>
     *
     * @return a {@link Number} object.
     */
    public Number value() {
        return getValue();
    }

    /**
     * <p>Getter for the field <code>value</code>.</p>
     *
     * @return a {@link Number} object.
     */
    public Number getValue() {
        return value;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>equals.</p>
     *
     * @param mode a {@link DigitalMode} object.
     * @return a boolean.
     */
    public boolean equals(DigitalMode mode) {
        return this == mode;
    }

    /**
     * <p>equals.</p>
     *
     * @param mode a {@link Number} object.
     * @return a boolean.
     */
    public boolean equals(Number mode) {
        return this == DigitalMode.getMode(mode);
    }

    public boolean equals(byte mode){
        return equals(DigitalMode.getMode(mode));
    }
    /**
     * <p>equals.</p>
     *
     * @param mode a short.
     * @return a boolean.
     */
    public boolean equals(short mode){
        return equals(DigitalMode.getMode(mode));
    }

    /**
     * <p>equals.</p>
     *
     * @param mode a int.
     * @return a boolean.
     */
    public boolean equals(int mode){
        return equals(DigitalMode.getMode(mode));
    }

    /**
     * <p>equals.</p>
     *
     * @param mode a long.
     * @return a boolean.
     */
    public boolean equals(long mode){
        return equals(DigitalMode.getMode(mode));
    }

    /**
     * <p>equals.</p>
     *
     * @param mode a float.
     * @return a boolean.
     */
    public boolean equals(float mode){
        return equals(DigitalMode.getMode(mode));
    }

    /**
     * <p>equals.</p>
     *
     * @param mode a double.
     * @return a boolean.
     */
    public boolean equals(double mode){
        return equals(DigitalMode.getMode(mode));
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return name;
    }

    /**
     * <p>state.</p>
     *
     * @param mode a {@link Number} object.
     * @return a {@link DigitalMode} object.
     */
    public static DigitalMode state(Number mode) {
        return getMode(mode);
    }

    /**
     * <p>getState.</p>
     *
     * @param mode a {@link Number} object.
     * @return a {@link DigitalMode} object.
     */
    public static DigitalMode getMode(Number mode) {
        for (var item : DigitalMode.values()) {
            if (item.getValue().intValue() == mode.intValue()) {
                return item;
            }
        }
        return null;
    }

    /**
     * <p>allStates.</p>
     *
     * @return an array of {@link DigitalMode} objects.
     */
    public static DigitalMode[] allStates() {
        return DigitalMode.values();
    }

    /**
     * <p>all.</p>
     *
     * @return a {@link EnumSet} object.
     */
    public static EnumSet<DigitalMode> all() {
        return EnumSet.of(DigitalMode.INPUT, DigitalMode.OUTPUT);
    }

    /**
     * <p>parse.</p>
     *
     * @param state a {@link String} object.
     * @return a {@link DigitalMode} object.
     */
    public static DigitalMode parse(String state) {
        if(state.equalsIgnoreCase("0")) return DigitalMode.INPUT;
        if(state.equalsIgnoreCase("1")) return DigitalMode.OUTPUT;
        if(state.toLowerCase().startsWith("i")) return DigitalMode.INPUT;
        if(state.toLowerCase().startsWith("o")) return DigitalMode.OUTPUT;
        return DigitalMode.UNKNOWN;
    }
}
