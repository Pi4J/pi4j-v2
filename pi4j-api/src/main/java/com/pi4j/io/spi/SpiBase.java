package com.pi4j.io.spi;

import com.pi4j.io.IOBase;

public abstract class SpiBase extends IOBase<Spi, SpiConfig> implements Spi {

    public SpiBase(SpiConfig config) {
        super(config);
    }
}
