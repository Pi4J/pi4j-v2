package com.pi4j.io.gpio.digital;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.impl.DefaultDigitalOutputBuilder;

/**
 * Fluent builder that collects the settings for a {@link DigitalOutput} and produces a fully initialized output instance via {@link #build()}.
 * Obtain an instance through {@link DigitalOutput#newBuilder(Context)}.
 */
public interface DigitalOutputBuilder {

    /**
     * Sets the unique identifier used to register and later look up the output instance.
     *
     * @param id the unique I/O identifier
     * @return this builder for method chaining
     */
    DigitalOutputBuilder id(String id);
    /**
     * Sets a human-readable name for the output.
     *
     * @param name the descriptive name
     * @return this builder for method chaining
     */
    DigitalOutputBuilder name(String name);
    /**
     * Sets a human-readable description for the output.
     *
     * @param description the free-form description
     * @return this builder for method chaining
     */
    DigitalOutputBuilder description(String description);
    /**
     * Sets the pin address (typically the BCM pin number) that the output controls.
     *
     * @param address the provider-specific pin address
     * @return this builder for method chaining
     */
    DigitalOutputBuilder address(Integer address);
    /**
     * Sets the state the output is driven to when Pi4J shuts down.
     *
     * @param state the desired state to apply on shutdown
     * @return this builder for method chaining
     */
    DigitalOutputBuilder shutdown(DigitalState state);
    /**
     * Sets the state the output is driven to immediately after it is initialized.
     *
     * @param state the desired state to apply on initialization
     * @return this builder for method chaining
     */
    DigitalOutputBuilder initial(DigitalState state);

    /**
     * Creates a new digital output builder bound to the given Pi4J context.
     *
     * @param context the Pi4J runtime context used to resolve the provider and register the output
     * @return a new builder instance
     */
    static DigitalOutputBuilder newInstance(Context context)  {
        return DefaultDigitalOutputBuilder.newInstance(context);
    }

    /**
     * Resolves the provider and builds a configured, initialized {@link DigitalOutput} from the collected
     * settings.
     *
     * @return the new digital output instance
     */
    DigitalOutput build();
}
