package com.pi4j.io.serial;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  StopBits.java
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
 * <p>StopBits class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum StopBits {

    _1(1),
    _2(2);

    private int stopBits = 0;

    private StopBits(int stopBits){
        this.stopBits = stopBits;
    }

    /**
     * <p>getValue.</p>
     *
     * @return a int.
     */
    public int getValue(){
        return this.stopBits;
    }
    /**
     * <p>value.</p>
     *
     * @return a int.
     */
    public int value(){
        return this.stopBits;
    }

    /**
     * <p>getInstance.</p>
     *
     * @param stop_bits a int.
     * @return a {@link com.pi4j.io.serial.StopBits} object.
     */
    public static StopBits getInstance(int stop_bits){
        for(StopBits sb : StopBits.values()){
            if(sb.getValue() == stop_bits){
                return sb;
            }
        }
        return null;
    }

    /**
     * <p>parse.</p>
     *
     * @param parity a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.serial.StopBits} object.
     */
    public static StopBits parse(String parity) {
        if(parity.equalsIgnoreCase("1")) return StopBits._1;
        if(parity.equalsIgnoreCase("2")) return StopBits._2;
        return StopBits._1;
    }
}
