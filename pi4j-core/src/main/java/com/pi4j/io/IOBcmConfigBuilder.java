package com.pi4j.io;

import com.pi4j.config.BcmConfigBuilder;

/**
 * Builder contract for I/O configurations addressed by a Broadcom (BCM) GPIO pin number.
 * <p>
 * It combines the provider-selection capability of {@link IOConfigBuilder} with the BCM pin
 * addressing of {@link BcmConfigBuilder}, and is the basis for builders of pin-based I/O such as
 * digital input/output and PWM.
 *
 * @param <BUILDER_TYPE> the concrete builder type, returned for fluent method chaining
 * @param <CONFIG_TYPE>  the configuration type produced by this builder
 */
public interface IOBcmConfigBuilder<BUILDER_TYPE, CONFIG_TYPE>
    extends IOConfigBuilder<BUILDER_TYPE, CONFIG_TYPE>,
    BcmConfigBuilder<BUILDER_TYPE, CONFIG_TYPE> {

}
