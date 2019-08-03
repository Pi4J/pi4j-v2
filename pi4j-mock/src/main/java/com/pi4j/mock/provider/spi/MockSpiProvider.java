package com.pi4j.mock.provider.spi;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: MOCK     :: Mock Platform & Mock I/O Providers
 * FILENAME      :  MockSpiProvider.java
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

import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.io.spi.SpiProviderBase;
import com.pi4j.mock.Mock;

public class MockSpiProvider extends SpiProviderBase implements SpiProvider{

    public static final String NAME = Mock.SPI_PROVIDER_NAME;
    public static final String ID = Mock.SPI_PROVIDER_ID;

    public MockSpiProvider(){
        this.id = ID;
        this.name = NAME;
    }

    @Override
    public Spi newInstance(SpiConfig config) throws Exception {
        return new MockSpi(this, config);
    }
}
