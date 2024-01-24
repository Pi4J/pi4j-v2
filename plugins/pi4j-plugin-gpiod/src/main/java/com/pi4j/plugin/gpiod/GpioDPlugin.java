package com.pi4j.plugin.gpiod;

import com.pi4j.extension.Plugin;
import com.pi4j.extension.PluginService;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutputProvider;
import com.pi4j.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>GpioDPlugin class.</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioDPlugin implements Plugin {

    /**
     * Constant <code>NAME="GpioD"</code>
     */
    public static final String NAME = "GpioD";
    /**
     * Constant <code>ID="gpiod"</code>
     */
    public static final String ID = "gpiod";

    // Digital Output (GPIO) Provider name and unique ID
    /**
     * Constant <code>DIGITAL_OUTPUT_PROVIDER_NAME="NAME +   Digital Output (GPIO) Provider"</code>
     */
    public static final String DIGITAL_OUTPUT_PROVIDER_NAME = NAME + " Digital Output (GPIO) Provider";
    /**
     * Constant <code>DIGITAL_OUTPUT_PROVIDER_ID="ID + -digital-output"</code>
     */
    public static final String DIGITAL_OUTPUT_PROVIDER_ID = ID + "-digital-output";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(PluginService service) {
        Provider[] providers = {
            GpioDDigitalOutputProvider.newInstance()
        };

        service.register(providers);
    }
}