package com.pi4j.io.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Serial.java
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

import com.pi4j.io.IO;
import com.pi4j.io.IODataReader;
import com.pi4j.io.IODataWriter;

import java.io.IOException;

public interface Serial extends IO<Serial, SerialConfig, SerialProvider>, AutoCloseable, IODataWriter, IODataReader {

    int DEFAULT_BAUD = 9600;
    DataBits DEFAULT_DATA_BITS = DataBits._8;
    Parity DEFAULT_PARITY = Parity.NONE;
    StopBits DEFAULT_STOP_BITS = StopBits._1;
    FlowControl DEFAULT_FLOW_CONTROL = FlowControl.NONE;

    static SerialConfigBuilder newConfigBuilder(){
        return SerialConfigBuilder.newInstance();
    }

    /**
     * Serial Device Communication State is OPEN
     * @return The Serial device communication state
     */
    boolean isOpen();


    /**
     * Get the number of data bytes available in the serial receive buffer
     * @return
     */
    int available() throws IOException;


    void open() throws IOException;
    void close() throws IOException;
}
