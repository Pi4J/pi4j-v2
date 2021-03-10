package com.pi4j.io.serial;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DataBits.java
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
 * <p>DataBits class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum DataBits {

    _5(5),
    _6(6),
    _7(7),
    _8(8);

    private int dataBits = 0;

    private DataBits(int dataBits){
        this.dataBits = dataBits;
    }

    /**
     * <p>getValue.</p>
     *
     * @return a int.
     */
    public int getValue(){
        return this.dataBits;
    }
    /**
     * <p>value.</p>
     *
     * @return a int.
     */
    public int value(){
        return this.dataBits;
    }

    /**
     * <p>getInstance.</p>
     *
     * @param data_bits a int.
     * @return a {@link com.pi4j.io.serial.DataBits} object.
     */
    public static DataBits getInstance(int data_bits){
        for(DataBits db : DataBits.values()){
            if(db.getValue() == data_bits){
                return db;
            }
        }
        return null;
    }

    /**
     * <p>parse.</p>
     *
     * @param parity a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.serial.DataBits} object.
     */
    public static DataBits parse(String parity) {
        if(parity.equalsIgnoreCase("5")) return DataBits._5;
        if(parity.equalsIgnoreCase("6")) return DataBits._6;
        if(parity.equalsIgnoreCase("7")) return DataBits._7;
        if(parity.equalsIgnoreCase("8")) return DataBits._8;
        return DataBits._8;
    }
}
