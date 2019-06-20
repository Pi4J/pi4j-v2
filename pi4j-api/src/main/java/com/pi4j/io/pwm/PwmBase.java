package com.pi4j.io.pwm;

import com.pi4j.io.IOBase;

public abstract class PwmBase extends IOBase<Pwm, PwmConfig> implements Pwm {

    public PwmBase(PwmConfig config) {
        super(config);
        this.name = "PWM-" + config.address();
    }
}
