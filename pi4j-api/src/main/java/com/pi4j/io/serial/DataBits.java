package com.pi4j.io.serial;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Java Library (Core)
 * FILENAME      :  DataBits.java
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

public enum DataBits {

    _5(5),
    _6(6),
    _7(7),
    _8(8);

    private int dataBits = 0;

    private DataBits(int dataBits){
        this.dataBits = dataBits;
    }

    public int getValue(){
        return this.dataBits;
    }
    public int value(){
        return this.dataBits;
    }

    public static DataBits getInstance(int data_bits){
        for(DataBits db : DataBits.values()){
            if(db.getValue() == data_bits){
                return db;
            }
        }
        return null;
    }

    public static DataBits parse(String parity) {
        if(parity.equalsIgnoreCase("5")) return DataBits._5;
        if(parity.equalsIgnoreCase("6")) return DataBits._6;
        if(parity.equalsIgnoreCase("7")) return DataBits._7;
        if(parity.equalsIgnoreCase("8")) return DataBits._8;
        return DataBits._8;
    }
}
