package com.pi4j.plugin.pigpio.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestSoftwarePwmUsingTestHarness.java
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

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.pwm.PiGpioPwmProvider;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessFrequency;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;

@DisplayName("PIGPIO Plugin :: Test PWM (Software-Generated Signals) using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSoftwarePwmUsingTestHarness {

    private static int I2C_BUS = 1;
    private static int I2C_DEVICE = 0x04;
    private static int I2C_TEST_HARNESS_BUS    = 0;
    private static int I2C_TEST_HARNESS_DEVICE = 0x04;

    private PiGpio piGpio;
    private Context pi4j;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        // configure logging output
        //System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("INITIALIZE TEST (" + TestSoftwarePwmUsingTestHarness.class.getName() + ")");
        System.out.println("************************************************************************");
        System.out.println();

        try {
            // create test harness and PIGPIO instances
            harness = new ArduinoTestHarness(System.getProperty("pi4j.test.harness.port", "tty.usbmodem142301"));

            // initialize test harness and PIGPIO instances
            harness.initialize();

            // get test harness info
            TestHarnessInfo info = harness.getInfo();
            System.out.println("... we are connected to test harness:");
            System.out.println("----------------------------------------");
            System.out.println("NAME       : " + info.name);
            System.out.println("VERSION    : " + info.version);
            System.out.println("DATE       : " + info.date);
            System.out.println("COPYRIGHT  : " + info.copyright);
            System.out.println("----------------------------------------");

            // reset all pins on test harness before proceeding with this test
            TestHarnessPins reset = harness.reset();
            System.out.println();
            System.out.println("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("TERMINATE TEST (" + TestSoftwarePwmUsingTestHarness.class.getName() + ") ");
        System.out.println("************************************************************************");
        System.out.println();

        // terminate connection to test harness
        harness.terminate();;
    }

    @BeforeEach
    public void beforeEach() throws Exception {

        // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
        piGpio = PiGpio.newSocketInstance("rpi3bp");

        // initialize the PiGpio library
        piGpio.initialize();

        // create PWM provider instance to test with
        var provider = PiGpioPwmProvider.newInstance(piGpio);

        // initialize Pi4J instance with this single provider
        pi4j = Pi4J.newContextBuilder().add(provider).build();
    }

    @AfterEach
    public void afterEach() throws Exception {
        // terminate the PiGpio library after each test
        piGpio.terminate();
    }

    @Test
    @Order(1)
    @DisplayName("PWM :: Test Software PWM @ 50 Hz")
    public void testPwmAt50Hertz() throws Exception {
        testPwm(50);
    }

    @Test
    @Order(2)
    @DisplayName("PWM :: Test Software PWM @ 100 Hz")
    public void testPwmAt100Hertz() throws Exception {
        testPwm(100);
    }

    @Test
    @Order(3)
    @DisplayName("PWM :: Test Software PWM @ 700 Hz")
    public void testPwmAt700Hertz() throws Exception {
        testPwm(700);
    }

    @Test
    @Order(4)
    @DisplayName("PWM :: Test Software PWM @ 1000 Hz (1 KHz)")
    public void testPwmAt1000Hertz() throws Exception {
        testPwm(1000);
    }

    @Test
    @Order(5)
    @DisplayName("PWM :: Test Software PWM @ 5000 Hz (5 KHz)")
    public void testPwmAt5000Hertz() throws Exception {
        testPwm(5000);
    }

    @Test
    @Order(6)
    @DisplayName("PWM :: Test Software PWM @ 10000 Hz (10 KHz)")
    public void testPwmAt10000Hertz() throws Exception {
        testPwm(10000);
    }

    public void testPwm(int frequency) throws Exception {
        testPwm(frequency, 50); // 80% duty-cycle by default
    }
    public void testPwm(int frequency, int dutyCycle) throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST PWM SIGNALS AT " + frequency + " HZ");
        System.out.println("----------------------------------------");

        for(int p = 2; p < 20; p++) {

            // create PWM instance config
            var config = Pwm.newConfigBuilder()
                    .id("my-pwm-pin-" + p)
                    .name("My Test PWM Pin #" + p)
                    .address(p)
                    .build();

            // create PWM I/O instance
            Pwm pwm = pi4j.create(config);

            System.out.println();
            System.out.println("[TEST SOFT PWM] :: PIN=" + p);

            // turn on PWM pulses with specified frequency and duty-cycle
            pwm.dutyCyclePercent(dutyCycle).frequency(frequency).on();

            System.out.println(" (PWM) >> SET FREQUENCY  = " + frequency);
            System.out.println(" (PWM) >> SET DUTY-CYCLE = " + dutyCycle + "%");
            System.out.println(" (PWM) << GET FREQUENCY  = " + pwm.frequency());
            System.out.println(" (PWM) << GET DUTY-CYCLE = " + pwm.dutyCycle() + " (" + pwm.dutyCyclePercent() + "%)");
            System.out.println(" (PWM) << GET RANGE MAX  = " + pwm.range());

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
                        Assert.fail("PWM MEASURED FREQUENCY OUT OF ACCEPTABLE MARGIN OF ERROR");
                    }
                }
            }

            // turn off PWM pin
            pwm.off();
        }
    }

    private boolean measureFrequency(Pwm pwm) throws IOException {
        int frequency = pwm.frequency();
        TestHarnessFrequency measured = harness.getFrequency(pwm.address());
        System.out.println(" (TEST)  << MEASURED FREQUENCY = " + measured.frequency);

        // we allow a 60% margin of error, the testing harness uses a simple pulse counter to crudely
        // measure the PWM signal, its not very accurate but should provide sufficient validation testing
        // just to verify the applied PWM signal is close to the expected frequency
        // calculate margin of error offset value
        long marginOfError = Math.round(frequency * .60);

        // test measured value against HI/LOW offsets to determine acceptable range
        if(measured.frequency < frequency-marginOfError) return false;
        if(measured.frequency > frequency+marginOfError) return false;

        // success
        return true;
    }


}
