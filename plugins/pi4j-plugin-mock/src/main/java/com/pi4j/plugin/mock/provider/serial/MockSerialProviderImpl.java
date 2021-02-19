package com.pi4j.plugin.mock.provider.serial;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: Mock Platform & Providers
 * FILENAME      :  MockSerialProviderImpl.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialProviderBase;


/**
 * <p>MockSerialProviderImpl class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class MockSerialProviderImpl extends SerialProviderBase implements MockSerialProvider {

    /**
     * <p>Constructor for MockSerialProviderImpl.</p>
     */
    public MockSerialProviderImpl(){
        this.id = ID;
        this.name = NAME;
    }

    /** {@inheritDoc} */
    @Override
    public Serial create(SerialConfig config) {
        return new MockSerial(this, config);
    }
}
