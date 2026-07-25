package com.pi4j.io.gpio.digital;

/**
 * Abstract base class for {@link DigitalInput} implementations, specializing {@link DigitalBase}
 * with the digital-input type parameters. Provider-specific subclasses extend this to supply the
 * actual hardware or expander read behaviour.
 */
public abstract class DigitalInputBase extends DigitalBase<DigitalInput, DigitalInputConfig> implements DigitalInput {
    /**
     * Creates a digital input bound to the given provider and configuration.
     *
     * @param config the configuration describing this input (pin, pull resistance, debounce, etc.)
     */
    public DigitalInputBase(DigitalInputConfig config){
        super(config);
    }
}
