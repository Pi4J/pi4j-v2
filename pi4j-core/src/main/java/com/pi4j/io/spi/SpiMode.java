package com.pi4j.io.spi;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  SpiMode.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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
 * <p>SpiMode class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum SpiMode {
    MODE_0(0), MODE_1(1), MODE_2(2), MODE_3(3);

    private final int mode;

    private SpiMode(int mode) {
        this.mode = mode;
    }

    /**
     * <p>Getter for the field <code>mode</code>.</p>
     *
     * @return a int.
     */
    public int getMode() {
        return mode;
    }

    /**
     * <p>getByNumber.</p>
     *
     * @param modeNumber a short.
     * @return a {@link com.pi4j.io.spi.SpiMode} object.
     */
    public static SpiMode getByNumber(short modeNumber){
        return getByNumber((int)modeNumber);
    }

    /**
     * <p>getByNumber.</p>
     *
     * @param modeNumber a int.
     * @return a {@link com.pi4j.io.spi.SpiMode} object.
     */
    public static SpiMode getByNumber(int modeNumber){
        for(var item : SpiMode.values()){
            if(item.getMode() == modeNumber){
                return item;
            }
        }
        return null;
    }

    /**
     * <p>parse.</p>
     *
     * @param mode a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.spi.SpiMode} object.
     */
    public static SpiMode parse(String mode) {
        if(mode.equalsIgnoreCase("0")) return SpiMode.MODE_0;
        if(mode.equalsIgnoreCase("1")) return SpiMode.MODE_1;
        if(mode.equalsIgnoreCase("2")) return SpiMode.MODE_2;
        if(mode.equalsIgnoreCase("3")) return SpiMode.MODE_3;
        return Spi.DEFAULT_MODE;
    }
}
