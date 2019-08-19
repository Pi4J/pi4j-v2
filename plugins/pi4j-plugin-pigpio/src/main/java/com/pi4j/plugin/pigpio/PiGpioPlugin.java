package com.pi4j.plugin.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  PiGpioPlugin.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
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

import com.pi4j.context.Context;
import com.pi4j.extension.Plugin;
import com.pi4j.extension.PluginService;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.i2c.PiGpioI2CProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioHardwarePwmProvider;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.provider.Provider;

import java.io.IOException;

public class PiGpioPlugin implements Plugin {

    public static final String NAME = "PiGpio";
    public static final String ID = "pigpio";

    // Digital Input (GPIO) Provider name and unique ID
    public static final String DIGITAL_INPUT_PROVIDER_NAME = NAME +  " Digital Input (GPIO) Provider";
    public static final String DIGITAL_INPUT_PROVIDER_ID = ID + "-digital-input";

    // Digital Output (GPIO) Provider name and unique ID
    public static final String DIGITAL_OUTPUT_PROVIDER_NAME = NAME +  " Digital Output (GPIO) Provider";
    public static final String DIGITAL_OUTPUT_PROVIDER_ID = ID + "-digital-output";

    // PWM Provider name and unique ID
    public static final String PWM_PROVIDER_NAME = NAME + " PWM Provider";
    public static final String PWM_PROVIDER_ID = ID + "-pwm";

    // PWM Provider name and unique ID
    public static final String HW_PWM_PROVIDER_NAME = NAME + " Hardware PWM Provider";
    public static final String HW_PWM_PROVIDER_ID = ID + "-hardware-pwm";

    // I2C Provider name and unique ID
    public static final String I2C_PROVIDER_NAME = NAME + " I2C Provider";
    public static final String I2C_PROVIDER_ID = ID + "-i2c";

    // SPI Provider name and unique ID
    public static final String SPI_PROVIDER_NAME = NAME + " SPI Provider";
    public static final String SPI_PROVIDER_ID = ID + "-spi";

    // Serial Provider name and unique ID
    public static final String SERIAL_PROVIDER_NAME = NAME + " Serial Provider";
    public static final String SERIAL_PROVIDER_ID = ID + "-serial";


    protected PiGpio piGpio = null;

    @Override
    public void initialize(PluginService service) throws IOException {

        // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
        piGpio = PiGpio.newSocketInstance("rpi3bp");

        // initialize the PIGPIO library
        piGpio.initialize();

        Provider providers[] = {
                PiGpioPwmProvider.newInstance(piGpio),
                PiGpioHardwarePwmProvider.newInstance(piGpio),
                PiGpioI2CProvider.newInstance(piGpio),
        };

        // register all PiGpio I/O Providers with the plugin service
        service.register(providers);
    }

    @Override
    public void shutdown(Context context) throws IOException {
        // shutdown the PiGpio library
        piGpio.terminate();
    }
}
