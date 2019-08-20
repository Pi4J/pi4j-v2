package com.pi4j.io.pwm.impl;

import com.pi4j.io.pwm.PwmPreset;

public class DefaultPwmPreset  implements PwmPreset {

    protected final String name;
    protected final Integer dutyCycle;
    protected final Integer frequency;

    public DefaultPwmPreset(String name, Integer dutyCycle){
        this.name = name.toLowerCase().trim();
        this.dutyCycle = dutyCycle;
        this.frequency = null;
    }

    public DefaultPwmPreset(String name, Integer dutyCycle, Integer frequency){
        this.name = name.toLowerCase().trim();
        this.dutyCycle = dutyCycle;
        this.frequency = frequency;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Integer dutyCycle() {
        return this.dutyCycle;
    }

    @Override
    public Integer frequency() {
        return this.frequency;
    }
}
