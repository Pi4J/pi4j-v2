package com.pi4j.example.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  PwmExampleUsingHardwarePwm.java
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
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.util.Console;

public class PwmExampleUsingHardwarePwm {

    public static int PWM_PIN = 13; // MUST BE A HARDWARE PWM SUPPORTED PIN

    public static void main(String[] args) throws Exception {

        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        System.setProperty("pi4j.host", "rpi3bp.savage.lan");

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

        // create PWM instance config
        var config = Pwm.newConfigBuilder()
                .id("my-pwm-pin")
                .name("My Test PWM Pin")
                .address(PWM_PIN)
                .pwmType(PwmType.HARDWARE) // USE HARDWARE PWM
                .frequency(1000)    // optionally pre-configure the desired frequency to 1KHz
                .shutdown(0)        // optionally pre-configure a shutdown duty-cycle value (on terminate)
                //.initial(125)     // optionally pre-configure an initial duty-cycle value (on startup)
                .build();

        // use try-with-resources to auto-close I2C when complete
        var pwm = pi4j.providers().get(PiGpioPwmProvider.class).create(config);

        // <OPTIONAL>
        // optionally override pre-configured PWM frequency; set PWM frequency to 1KHz
        //pwm.frequency(1000);

        // <OPTIONAL>
        // optionally override pre-configured duty-cycle value;
        // this is in relation to the previously defined rage value
        //pwm.setDutyCycle(500000);   // hardware PWM range is 0-1M; this value is 50% of range

        // <OPTIONAL>
        // alternatively, you can also just simply set the duty-cycle as a percent value
        pwm.setDutyCyclePercent(50); // 50%

        // enable the PWM signal
        pwm.on();

        console.println("[PWM I/O INSTANCE] : ");
        pwm.describe().print(System.out);
        console.println();
        console.println(" - GPIO PIN   : " + pwm.address());
        console.println(" - PWM TYPE   : " + pwm.pwmType());
        console.println(" - FREQUENCY  : " + pwm.frequency() + " Hz");
        console.println(" - RANGE      : 0-" + pwm.range());
        console.println(" - DUTY-CYCLE : " + pwm.dutyCycle() + " (" + pwm.dutyCyclePercent()  + "%)");
        console.println(" - IS-ON      : " + pwm.isOn());

        // create a digital input instance using the default digital input provider
        // wait (block) for user to exit program using CTRL-C
        console.waitForExit();

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");

        // shutdown Pi4J
        pi4j.shutdown();
    }
}
