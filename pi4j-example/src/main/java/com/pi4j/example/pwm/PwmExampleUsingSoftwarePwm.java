package com.pi4j.example.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  I2cDeviceExample.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.Pi4J;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwm;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.util.Console;

import java.nio.ByteBuffer;

public class PwmExampleUsingSoftwarePwm {

    public static int PWM_PIN = 4;

    public static void main(String[] args) throws Exception {

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "PWM Example using Software-Emulated PWM");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        var pi4j = Pi4J.newAutoContext();

        // TODO :: UPDATE THIS IMPL
        PwmConfig config = new PwmConfig(PWM_PIN);

        // use try-with-resources to auto-close I2C when complete
        var pwm = pi4j.providers().get(PiGpioPwmProvider.class).create(config);

        // listen for shutdown to properly clean up
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // turn off the pwm signal
                if(pwm.isOn()) pwm.off();

                // shutdown Pi4J
                pi4j.shutdown();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }));

        // <OPTIONAL>
        // configure PWM frequency; set PWM frequency to 1KHz
        pwm.frequency(5000);

        // <OPTIONAL>
        // the default duty-cycle range is 0-255; this function sets the upper limit of the range
        pwm.setRange(255);

        // <OPTIONAL>
        // set an explict duty-cycle value; this is in relation to the previously defined rage value
        pwm.setDutyCycle(128);

        // <OPTIONAL>
        // alternatively, you can also just simply set the duty-cycle as a percent value
        //pwm.setDutyCyclePercent(50); // 50%

        // enable the PWM signal
        pwm.on();

        // create a digital input instance using the default digital input provider
        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // turn off the pwm signal
        if(pwm.isOn()) pwm.off();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");

        // shutdown Pi4J
        pi4j.shutdown();
    }
}
