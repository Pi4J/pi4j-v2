package com.pi4j.io.spi.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultSpiConfig.java
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

import com.pi4j.io.impl.IOAddressConfigBase;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiMode;
import com.pi4j.util.StringUtil;

import java.util.Map;

/**
 * <p>DefaultSpiConfig class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultSpiConfig
        extends IOAddressConfigBase<SpiConfig>
        implements SpiConfig {

    // private configuration properties
    protected final Integer baud;
    protected final SpiMode mode;

    /**
     * PRIVATE CONSTRUCTOR
     *
     * @param properties a {@link java.util.Map} object.
     */
    protected DefaultSpiConfig(Map<String,String> properties){
        super(properties);

        // load optional BAUD RATE from properties
        if(properties.containsKey(BAUD_KEY)){
            this.baud = StringUtil.parseInteger(properties.get(BAUD_KEY), Spi.DEFAULT_BAUD);
        } else {
            this.baud = Spi.DEFAULT_BAUD;
        }

        // load optional DATA BITS from properties
        if(properties.containsKey(MODE_KEY)){
            this.mode = SpiMode.parse(properties.get(MODE_KEY));
        } else {
            this.mode = Spi.DEFAULT_MODE;
        }

        // define default property values if any are missing (based on the required address value)
        this.id = StringUtil.setIfNullOrEmpty(this.id, "SPI-" + this.address(), true);
        this.name = StringUtil.setIfNullOrEmpty(this.name, "SPI-" + this.address(), true);
        this.description = StringUtil.setIfNullOrEmpty(this.description, "SPI-" + this.address(), true);
    }

    /** {@inheritDoc} */
    @Override
    public Integer baud() {
        return this.baud;
    }

    /** {@inheritDoc} */
    @Override
    public SpiMode mode() {
        return this.mode;
    }
}
