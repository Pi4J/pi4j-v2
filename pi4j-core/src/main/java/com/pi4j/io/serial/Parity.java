package com.pi4j.io.serial;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  Parity.java
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

/**
 * <p>Parity class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum Parity {

    NONE(0),
    ODD(1),
    EVEN(2),

    // NOT ALL UNIX SYSTEM SUPPORT 'MARK' PARITY; THIS IS EXPERIMENTAL
    MARK(3),

    //NOT ALL UNIX SYSTEM SUPPORT 'SPACE' PARITY; THIS IS EXPERIMENTAL
    SPACE(4);

    private int index = 0;

    private Parity(int index){
        this.index = index;
    }

    /**
     * <p>Getter for the field <code>index</code>.</p>
     *
     * @return a int.
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * <p>getInstance.</p>
     *
     * @param parity a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.serial.Parity} object.
     */
    public static Parity getInstance(String parity) {
        return Parity.valueOf(parity.toUpperCase());
    }

    /**
     * <p>getInstance.</p>
     *
     * @param parity a int.
     * @return a {@link com.pi4j.io.serial.Parity} object.
     */
    public static Parity getInstance(int parity){
        for(Parity p : Parity.values()){
            if(p.getIndex() == parity){
                return p;
            }
        }
        return null;
    }
    /**
     * <p>parse.</p>
     *
     * @param parity a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.serial.Parity} object.
     */
    public static Parity parse(String parity) {
        if(parity.equalsIgnoreCase("0")) return Parity.NONE;
        if(parity.equalsIgnoreCase("1")) return Parity.ODD;
        if(parity.equalsIgnoreCase("2")) return Parity.EVEN;
        if(parity.equalsIgnoreCase("3")) return Parity.MARK;
        if(parity.equalsIgnoreCase("4")) return Parity.SPACE;
        if(parity.toLowerCase().startsWith("n")) return Parity.NONE;
        if(parity.toLowerCase().startsWith("o")) return Parity.ODD;
        if(parity.toLowerCase().startsWith("e")) return Parity.EVEN;
        if(parity.toLowerCase().startsWith("m")) return Parity.MARK;
        if(parity.toLowerCase().startsWith("s")) return Parity.SPACE;
        return Parity.NONE;
    }
}
