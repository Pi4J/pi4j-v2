package com.pi4j.io.serial;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  FlowControl.java
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
 * <p>FlowControl class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum FlowControl {

    NONE(0),
    HARDWARE(1),
    SOFTWARE(2);

    private int index = 0;

    private FlowControl(int index){
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
     * @param flow_control a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.serial.FlowControl} object.
     */
    public static FlowControl getInstance(String flow_control) {
        return FlowControl.valueOf(flow_control.toUpperCase());
    }

    /**
     * <p>getInstance.</p>
     *
     * @param control a int.
     * @return a {@link com.pi4j.io.serial.FlowControl} object.
     */
    public static FlowControl getInstance(int control){
        for(FlowControl fc : FlowControl.values()){
            if(fc.getIndex() == control){
                return fc;
            }
        }
        return null;
    }

    /**
     * <p>parse.</p>
     *
     * @param parity a {@link java.lang.String} object.
     * @return a {@link com.pi4j.io.serial.FlowControl} object.
     */
    public static FlowControl parse(String parity) {
        if(parity.equalsIgnoreCase("0")) return FlowControl.NONE;
        if(parity.equalsIgnoreCase("1")) return FlowControl.HARDWARE;
        if(parity.equalsIgnoreCase("2")) return FlowControl.SOFTWARE;
        if(parity.toLowerCase().startsWith("n")) return FlowControl.NONE;
        if(parity.toLowerCase().startsWith("h")) return FlowControl.HARDWARE;
        if(parity.toLowerCase().startsWith("s")) return FlowControl.SOFTWARE;
        return FlowControl.NONE;
    }
}
