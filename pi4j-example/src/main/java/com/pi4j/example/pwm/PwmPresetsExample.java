package com.pi4j.example.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  PwmPresetsExample.java
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
import com.pi4j.io.pwm.PwmPreset;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.util.Console;

public class PwmPresetsExample {

    public static int PWM_PIN = 4;

    public static void main(String[] args) throws Exception {

        // TODO :: REMOVE TEMPORARY PROPERTIES WHEN NATIVE PIGPIO LIB IS READY
        // this temporary property is used to tell
        // PIGPIO which remote Raspberry Pi to connect to
        System.setProperty("pi4j.host", "rpi3bp.savage.lan");

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
        final var console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "PWM Presets Example using Software-Emulated PWM");

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
                .frequency(1000)   // optionally pre-configure the desired frequency to 1KHz
                .range(255)        // optionally pre-configure the desired duty-cycle range (0-255)
                .dutyCycle(128)    // optionally pre-configure the desired duty-cycle (50%)
                .shutdown(0)       // optionally pre-configure a shutdown duty-cycle value (on terminate)
                //.initial(125)      // optionally pre-configure an initial duty-cycle value (on startup)
                .build();

        // use try-with-resources to auto-close I2C when complete
        var pwm = pi4j.providers().get(PiGpioPwmProvider.class).create(config);

        // add PWM presets
        pwm.addPreset(PwmPreset.newBuilder("one")
                .frequency(5000)
                .dutyCycle(128)
                .build());

        pwm.addPreset(PwmPreset.newBuilder("two")
                .frequency(80)
                .build());

        pwm.addPreset(PwmPreset.newBuilder("three")
                .dutyCycle(180)
                .build());


        console.println("[PWM I/O INSTANCE] : ");
        pwm.describe().print(System.out);

        // <OPTIONAL>
        // optionally override pre-configured PWM frequency; set PWM frequency to 1KHz
        //pwm.frequency(1000);

        // <OPTIONAL>
        // optionally override pre-configured duty-cycle range (Default is 0-255);
        // this function sets the upper limit of the range
        //pwm.setRange(255);

        // <OPTIONAL>
        // optionally override pre-configured duty-cycle value;
        // this is in relation to the previously defined rage value
        //pwm.setDutyCycle(128);

        // <OPTIONAL>
        // alternatively, you can also just simply set the duty-cycle as a percent value
        //pwm.setDutyCyclePercent(50); // 50%

        // enable the PWM signal (using the configured settings)
        pwm.on();

        console.println();
        console.println(" ... PWM SIGNAL SHOULD BE <--ON-->");
        console.println();
        console.println(" - GPIO PIN   : " + pwm.address());
        console.println(" - PWM TYPE   : " + pwm.pwmType());
        console.println(" - FREQUENCY  : " + pwm.frequency() + " Hz");
        console.println(" - RANGE      : 0-" + pwm.range());
        console.println(" - DUTY-CYCLE : " + pwm.dutyCycle() + " (" + pwm.dutyCyclePercent()  + "%)");
        console.println(" - IS-ON      : " + pwm.isOn());

        // wait 5 seconds then exit
        console.println();
        console.println(" ... WAITING 5 SECONDS ... ");
        Thread.sleep(5000);

        // recall applyPreset one
        pwm.applyPreset("one");

        console.println();
        console.println(" ... RECALLING PWM PRESET <--PRESET('one')-->");
        console.println();
        console.println(" - GPIO PIN   : " + pwm.address());
        console.println(" - PWM TYPE   : " + pwm.pwmType());
        console.println(" - FREQUENCY  : " + pwm.frequency() + " Hz");
        console.println(" - RANGE      : 0-" + pwm.range());
        console.println(" - DUTY-CYCLE : " + pwm.dutyCycle() + " (" + pwm.dutyCyclePercent()  + "%)");
        console.println(" - IS-ON      : " + pwm.isOn());


        // wait 5 seconds then exit
        console.println();
        console.println(" ... WAITING 5 SECONDS ... ");
        Thread.sleep(5000);

        // recall applyPreset two
        pwm.applyPreset("two");

        console.println();
        console.println(" ... RECALLING PWM PRESET <--PRESET('two')-->");
        console.println();
        console.println(" - GPIO PIN   : " + pwm.address());
        console.println(" - PWM TYPE   : " + pwm.pwmType());
        console.println(" - FREQUENCY  : " + pwm.frequency() + " Hz");
        console.println(" - RANGE      : 0-" + pwm.range());
        console.println(" - DUTY-CYCLE : " + pwm.dutyCycle() + " (" + pwm.dutyCyclePercent()  + "%)");
        console.println(" - IS-ON      : " + pwm.isOn());


        // wait 5 seconds then exit
        console.println();
        console.println(" ... WAITING 5 SECONDS ... ");
        Thread.sleep(5000);

        // recall applyPreset two
        pwm.applyPreset("three");

        console.println();
        console.println(" ... RECALLING PWM PRESET <--PRESET('two')-->");
        console.println();
        console.println(" - GPIO PIN   : " + pwm.address());
        console.println(" - PWM TYPE   : " + pwm.pwmType());
        console.println(" - FREQUENCY  : " + pwm.frequency() + " Hz");
        console.println(" - RANGE      : 0-" + pwm.range());
        console.println(" - DUTY-CYCLE : " + pwm.dutyCycle() + " (" + pwm.dutyCyclePercent()  + "%)");
        console.println(" - IS-ON      : " + pwm.isOn());

        // wait 5 seconds then exit
        console.println();
        console.println(" ... WAITING 5 SECONDS ... ");
        Thread.sleep(5000);

        // shutdown Pi4J
        console.println("ATTEMPTING TO SHUTDOWN/TERMINATE THIS PROGRAM");

        // shutdown Pi4J
        pi4j.shutdown();
    }
}
