package com.pi4j.io.i2c;

import com.pi4j.io.IOBase;

public abstract class I2CBase extends IOBase<I2C, I2CConfig> implements I2C {

    public I2CBase(I2CConfig config) {
        super(config);
    }
}
