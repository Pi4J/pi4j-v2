package com.pi4j.plugin.gpiod.provider.gpio.digital;

import com.pi4j.plugin.gpiod.GpioDPlugin;

/**
 * <p>GpioDDigitalInputProvider interface.</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioDDigitalInputProvider {
    /** Constant <code>NAME="GpioDPlugin.DIGITAL_INPUT_PROVIDER_NAME"</code> */
    String NAME = GpioDPlugin.DIGITAL_INPUT_PROVIDER_NAME;
    /** Constant <code>ID="GpioDPlugin.DIGITAL_INPUT_PROVIDER_ID"</code> */
    String ID = GpioDPlugin.DIGITAL_INPUT_PROVIDER_ID;

    /**
     * <p>newInstance.</p>
     *
     * @return a {@link com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalInputProvider} object.
     */
    static GpioDDigitalInputProvider newInstance() {
        return new GpioDDigitalInputProviderImpl();
    }
}
