package com.pi4j.plugin.linuxfs;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  LinuxFsPlugin.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.pi4j.extension.Plugin;
import com.pi4j.extension.PluginService;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.linuxfs.provider.gpio.digital.LinuxFsDigitalInputProvider;
import com.pi4j.plugin.linuxfs.provider.gpio.digital.LinuxFsDigitalOutputProvider;
import com.pi4j.provider.Provider;

/**
 * <p>LinuxFsPlugin class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class LinuxFsPlugin implements Plugin {

    /**
     * Constant <code>NAME="LinuxFS"</code>
     */
    public static final String NAME = "LinuxFS";
    /**
     * Constant <code>ID="linuxfs"</code>
     */
    public static final String ID = "linuxfs";

//    // Analog Input (GPIO) Provider name and unique ID
//    public static final String ANALOG_INPUT_PROVIDER_NAME = NAME + " Analog Input (GPIO) Provider";
//    public static final String ANALOG_INPUT_PROVIDER_ID = ID + "-analog-input";

    // Analog Output (GPIO) Provider name and unique ID
    /**
     * Constant <code>ANALOG_OUTPUT_PROVIDER_NAME="NAME +  Analog Output (GPIO) Provider"</code>
     */
    public static final String ANALOG_OUTPUT_PROVIDER_NAME = NAME + " Analog Output (GPIO) Provider";
    /**
     * Constant <code>ANALOG_OUTPUT_PROVIDER_ID="ID + -analog-output"</code>
     */
    public static final String ANALOG_OUTPUT_PROVIDER_ID = ID + "-analog-output";

    // Digital Input (GPIO) Provider name and unique ID
    /**
     * Constant <code>DIGITAL_INPUT_PROVIDER_NAME="NAME +   Digital Input (GPIO) Provider"</code>
     */
    public static final String DIGITAL_INPUT_PROVIDER_NAME = NAME + " Digital Input (GPIO) Provider";
    /**
     * Constant <code>DIGITAL_INPUT_PROVIDER_ID="ID + -digital-input"</code>
     */
    public static final String DIGITAL_INPUT_PROVIDER_ID = ID + "-digital-input";

    // Digital Output (GPIO) Provider name and unique ID
    /**
     * Constant <code>DIGITAL_OUTPUT_PROVIDER_NAME="NAME +   Digital Output (GPIO) Provider"</code>
     */
    public static final String DIGITAL_OUTPUT_PROVIDER_NAME = NAME + " Digital Output (GPIO) Provider";
    /**
     * Constant <code>DIGITAL_OUTPUT_PROVIDER_ID="ID + -digital-output"</code>
     */
    public static final String DIGITAL_OUTPUT_PROVIDER_ID = ID + "-digital-output";

//    // PWM Provider name and unique ID
//    public static final String PWM_PROVIDER_NAME = NAME + " PWM Provider";
//    public static final String PWM_PROVIDER_ID = ID + "-pwm";

    // I2C Provider name and unique ID
    public static final String I2C_PROVIDER_NAME = NAME + " I2C Provider";
    public static final String I2C_PROVIDER_ID = ID + "-i2c";

//    // SPI Provider name and unique ID
//    public static final String SPI_PROVIDER_NAME = NAME + " SPI Provider";
//    public static final String SPI_PROVIDER_ID = ID + "-spi";
//
//    // Serial Provider name and unique ID
//    public static final String SERIAL_PROVIDER_NAME = NAME + " Serial Provider";
//    public static final String SERIAL_PROVIDER_ID = ID + "-serial";

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(PluginService service) {

        Provider[] providers = { LinuxFsDigitalInputProvider.newInstance(),
            LinuxFsDigitalOutputProvider.newInstance(),
            LinuxFsI2CProvider.newInstance()
        };

        // register the LinuxFS I/O Providers with the plugin service
        service.register(providers);

    }
}
