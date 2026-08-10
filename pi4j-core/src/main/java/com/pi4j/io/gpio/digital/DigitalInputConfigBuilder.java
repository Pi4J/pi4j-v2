package com.pi4j.io.gpio.digital;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.impl.DefaultDigitalInputConfigBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Fluent builder for assembling a {@link DigitalInputConfig}, extending {@link DigitalConfigBuilder}
 * with the input-specific pull resistance and debounce settings.
 */
public interface DigitalInputConfigBuilder extends DigitalConfigBuilder<DigitalInputConfigBuilder, DigitalInputConfig> {

    DigitalInputConfigBuilder bcm(Integer bcm);

    /**
     * Sets the pull resistance (pull-up, pull-down, or none) to apply to the input pin.
     *
     * @param value the desired {@link PullResistance}
     * @return this builder for method chaining
     */
    DigitalInputConfigBuilder pull(PullResistance value);

    /**
     * Sets the debounce interval, in microseconds, used to filter spurious state transitions.
     *
     * @param microseconds the debounce interval in microseconds
     * @return this builder for method chaining
     * @see com.pi4j.io.gpio.digital.DigitalInput#DEFAULT_DEBOUNCE DEFAULT_DEBOUNCE
     */
    DigitalInputConfigBuilder debounce(Long microseconds);

    /**
     * Sets the debounce interval expressed in the given time unit, converting it internally to microseconds.
     *
     * @param interval the debounce interval value
     * @param units the {@link TimeUnit} in which {@code interval} is expressed
     * @return this builder for method chaining
     */
    DigitalInputConfigBuilder debounce(Long interval, TimeUnit units);

    /**
     * Creates a new digital input config builder instance.
     *
     * @param context the Pi4J context
     * @return a new builder instance
     * @deprecated use DigitalInptu.newConfigBuilder() instead.
     */
    @Deprecated(since="5.0")
    static DigitalInputConfigBuilder newInstance(Context context) {
        return DefaultDigitalInputConfigBuilder.newInstance(context);
    }

    /**
     * Internal method that creates a new digital input config builder instance. Please don't call this method
     * directly; use DigitalInput.newConfigBuilder() instead.
     *
     * @return a new builder instance
     */
    static DigitalInputConfigBuilder newInstance() {
        return DefaultDigitalInputConfigBuilder.newInstance();
    }
}
