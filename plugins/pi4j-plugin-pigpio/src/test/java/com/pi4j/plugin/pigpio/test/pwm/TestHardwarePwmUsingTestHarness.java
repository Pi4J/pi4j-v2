package com.pi4j.plugin.pigpio.test.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestHardwarePwmUsingTestHarness.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
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

import static org.junit.jupiter.api.Assertions.fail;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmType;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioException;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessFrequency;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DisplayName("PIGPIO Plugin :: Test PWM (Hardware-Generated Signals) using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestHardwarePwmUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestHardwarePwmUsingTestHarness.class);

    private PiGpio piGpio;
    private Context pi4j;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestHardwarePwmUsingTestHarness.class.getName() + ")");
        logger.info("************************************************************************");
        logger.info("");

        try {
            // create test harness and PIGPIO instances
            harness = TestEnv.createTestHarness();

            // initialize test harness and PIGPIO instances
            harness.initialize();

            // get test harness info
            TestHarnessInfo info = harness.getInfo();
            logger.info("... we are connected to test harness:");
            logger.info("----------------------------------------");
            logger.info("NAME       : " + info.name);
            logger.info("VERSION    : " + info.version);
            logger.info("DATE       : " + info.date);
            logger.info("COPYRIGHT  : " + info.copyright);
            logger.info("----------------------------------------");

            // reset all pins on test harness before proceeding with this test
            TestHarnessPins reset = harness.reset();
            logger.info("");
            logger.info("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");
        } catch (java.io.IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterAll
    public static void terminate() throws java.io.IOException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestHardwarePwmUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // shutdown connection to test harness
        harness.shutdown();
    }

    @BeforeEach
    public void beforeEach() {
        // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
        piGpio = TestEnv.createPiGpio();

        // initialize the PiGpio library
        piGpio.initialize();

        // create PWM provider instance to test with
        var provider = PiGpioPwmProvider.newInstance(piGpio);

        // initialize Pi4J instance with this single provider
        pi4j = Pi4J.newContextBuilder().add(provider).build();
    }

    @AfterEach
    public void afterEach() {
        // shutdown the PiGpio library after each test
        piGpio.shutdown();
    }

    @Test
    @Order(1)
    @DisplayName("PWM :: Test Hardware PWM @ 50 Hz")
    public void testPwmAt50Hertz() {
        testPwm(50);
    }

    @Test
    @Order(2)
    @DisplayName("PWM :: Test Hardware PWM @ 100 Hz")
    public void testPwmAt100Hertz() {
        testPwm(100);
    }

    @Test
    @Order(3)
    @DisplayName("PWM :: Test Hardware PWM @ 700 Hz")
    public void testPwmAt700Hertz() {
        testPwm(700);
    }

    @Test
    @Order(4)
    @DisplayName("PWM :: Test Hardware PWM @ 1000 Hz (1 KHz)")
    public void testPwmAt1000Hertz() {
        testPwm(1000);
    }

    @Test
    @Order(5)
    @DisplayName("PWM :: Test Hardware PWM @ 5000 Hz (5 KHz)")
    public void testPwmAt5000Hertz() {
        testPwm(5000);
    }

    @Test
    @Order(6)
    @DisplayName("PWM :: Test Hardware PWM @ 10000 Hz (10 KHz)")
    public void testPwmAt10000Hertz() {
        testPwm(10000);
    }

    @Test
    @Order(6)
    @DisplayName("PWM :: Test Unsupported Hardware PWM Pin")
    public void testUnsupportedPin() {
        // create PWM instance config
        var config = Pwm.newConfigBuilder(pi4j)
                .address(2)
                .pwmType(PwmType.HARDWARE)
                .build();

        // when we attempt to turn on the PWM pin, we expect an exception
        // to occur because this is not a hardware supported PWM pin
        Assertions.assertThrows(IOException.class, () -> {

            // create PWM I/O instance
            Pwm pwm = pi4j.create(config);

            pwm.on();
        });
    }


    public void testPwm(int frequency) {
        testPwm(frequency, 50); // 80% duty-cycle by default
    }
    public void testPwm(int frequency, int dutyCycle) {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST PWM SIGNALS AT " + frequency + " HZ");
        logger.info("----------------------------------------");


        int pins[] = new int[] { 12, 13,18,19 };

//        12  PWM channel 0  All models but A and B
//        13  PWM channel 1  All models but A and B
//        18  PWM channel 0  All models
//        19  PWM channel 1  All models but A and B
//
//        40  PWM channel 0  Compute module only
//        41  PWM channel 1  Compute module only
//        45  PWM channel 1  Compute module only
//        52  PWM channel 0  Compute module only
//        53  PWM channel 1  Compute module only

        for(int p : pins) {

            // create PWM instance config
            var config = Pwm.newConfigBuilder(pi4j)
                    .id("my-pwm-pin-" + p)
                    .name("My Test PWM Pin #" + p)
                    .address(p)
                    .pwmType(PwmType.HARDWARE)
                    .build();

            // create PWM I/O instance
            Pwm pwm = pi4j.create(config);

            logger.info("");
            logger.info("[TEST HARDWARE PWM] :: PIN=" + p);

            // turn on PWM pulses with specified frequency and duty-cycle
            pwm.dutyCycle(dutyCycle).frequency(frequency).on();

            logger.info(" (PWM) >> SET FREQUENCY  = " + frequency);
            logger.info(" (PWM) >> SET DUTY-CYCLE = " + dutyCycle + "%");
            logger.info(" (PWM) << GET FREQUENCY  = " + pwm.frequency());

            try {
                Thread.sleep(10);
                // test once ..
                if(measureFrequency(pwm) == false){
                    // test twice ..
                    Thread.sleep(1000);
                    if(measureFrequency(pwm) == false){
                        // test thrice ..
                        Thread.sleep(2000);
                        if(measureFrequency(pwm) == false){
                            // turn off PWM pin
                            pwm.off();

                            // give up and fail
                            fail("PWM MEASURED FREQUENCY OUT OF ACCEPTABLE MARGIN OF ERROR");
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // turn off PWM pin
            pwm.off();
        }
    }

    private boolean measureFrequency(Pwm pwm) {
        try {
            int frequency = pwm.actualFrequency();
            TestHarnessFrequency measured = harness.getFrequency(pwm.address());
            float deviation = (measured.frequency - frequency) * 100/(float)frequency;
            logger.info(" (TEST)  << MEASURED FREQUENCY = " + measured.frequency + "; (EXPECTED=" + frequency + "; DEVIATION: " + deviation + "%)");

            // we allow a 25% margin of error, the testing harness uses a simple pulse counter to crudely
            // measure the PWM signal, its not very accurate but should provide sufficient validation testing
            // just to verify the applied PWM signal is close to the expected frequency
            // calculate margin of error offset value
            long marginOfError = Math.round(frequency * .25);

            // test measured value against HI/LOW offsets to determine acceptable range
            if(measured.frequency < frequency-marginOfError) return false;
            if(measured.frequency > frequency+marginOfError) return false;

            // success
            return true;
        } catch (java.io.IOException e) {
            throw new PiGpioException(e);
        }
    }


}
