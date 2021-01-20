package com.pi4j.library.pigpio.test.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestHardwarePwmUsingTestHarness.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioMode;
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessFrequency;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("PIGPIO Library :: Test Hardware-Supported PWM Pins")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestHardwarePwmUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestHardwarePwmUsingTestHarness.class);

    private PiGpio pigpio;
    private int handle;
    private static ArduinoTestHarness harness;

    private static int ALT0_PINS[] = { 12, 13 };
    private static int ALT5_PINS[] = { 18, 19 };

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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestHardwarePwmUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // shutdown connection to test harness
        harness.shutdown();
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        // create test harness and PIGPIO instances
        pigpio = TestEnv.createPiGpio();

        // initialize test harness and PIGPIO instances
        pigpio.initialize();

        // reset I/O
        harness.reset();
    }

    @AfterEach
    public void afterEach() throws IOException {
        // shutdown PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @Order(0)
    @DisplayName("PWM :: Test HW PWM @ 15 Hz")
    public void testPwmAt15Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 15);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 15);
    }

    @Test
    @Order(1)
    @DisplayName("PWM :: Test HW PWM @ 50 Hz")
    public void testPwmAt50Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 50);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 50);
    }

    @Test
    @Order(2)
    @DisplayName("PWM :: Test HW PWM @ 100 Hz")
    public void testPwmAt100Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 100);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 100);
    }

    @Test
    @Order(3)
    @DisplayName("PWM :: Test HW PWM @ 700 Hz")
    public void testPwmAt700Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 700);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 700);
    }

    @Test
    @Order(4)
    @DisplayName("PWM :: Test HW PWM @ 1000 Hz (1 KHz)")
    public void testPwmAt1000Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 1000);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 1000);
    }

    @Test
    @Order(5)
    @DisplayName("PWM :: Test HW PWM @ 5000 Hz (5 KHz)")
    public void testPwmAt5000Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 5000);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 5000);
    }

    @Test
    @Order(6)
    @DisplayName("PWM :: Test HW PWM @ 10000 Hz (10 KHz)")
    public void testPwmAt10000Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 10000);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 10000);
    }

    @Test
    @Order(7)
    @DisplayName("PWM :: Test HW PWM @ 100000 Hz (100 KHz)")
    public void testPwmAt100000Hertz() throws IOException, InterruptedException {
        testPwm(PiGpioMode.ALT0, ALT0_PINS, 100000);
        testPwm(PiGpioMode.ALT5, ALT5_PINS, 100000);
    }

    public void testPwm(PiGpioMode mode, int[] pins, int frequency) throws IOException, InterruptedException {
        testPwm(mode, pins, frequency, 50); // 50% duty-cycle by default
    }
    public void testPwm(PiGpioMode mode, int[] pins, int frequency, int dutyCycle) throws IOException, InterruptedException {
        logger.info("");
        logger.info("---------------------------------------------------------");
        logger.info("TEST PWM SIGNALS AT " + frequency + " HZ; PINS=" + Arrays.toString(pins) + "; MODE=" + mode.name());
        logger.info("---------------------------------------------------------");

        for(int p : pins) {

            // set pin ALT modes for PWM
            pigpio.gpioSetMode(p, mode);

            logger.info("");
            logger.info("[TEST HARDWARE PWM] :: PIN=" + p + " <" + mode.name() + ">");

            // hardware PWM duty cycle scaling
            int dc = dutyCycle * 10000;

            // write PWM frequency and duty-cycle
            pigpio.gpioHardwarePWM(p, frequency, dc);
            logger.info(" (WRITE) >> PWM FREQUENCY  = " + frequency);
            logger.info(" (WRITE) >> PWM DUTY-CYCLE = " + dc);

            // test once ..
            if(measureFrequency(p, frequency) == false){
                Thread.sleep(500);
                // test twice ..
                if(measureFrequency(p, frequency) == false){
                    Thread.sleep(1000);
                    // test thrice ..
                    if(measureFrequency(p, frequency) == false){
                        // turn off PWM pin
                        pigpio.gpioHardwarePWM(p, 0, 0);

                        // give up and fail
                        fail("PWM MEASURED FREQUENCY OUT OF ACCEPTABLE MARGIN OF ERROR");
                    }
                }
            }

            // turn off PWM pin
            pigpio.gpioHardwarePWM(p, 0, 0);
        }
    }

    private boolean measureFrequency(int pin, int frequency) throws IOException {
        TestHarnessFrequency measured = harness.getFrequency(pin);

        float deviation = (measured.frequency - frequency) * 100/(float)frequency;
        logger.info(" (TEST)  << MEASURED FREQUENCY = " + measured.frequency + "; (EXPECTED=" + frequency + "; DEVIATION: " + deviation + "%)");

        // we allow a 30% margin of error, the testing harness uses a simple pulse counter to crudely
        // measure the PWM signal, its not very accurate and we don't take enough samples to get a
        // better average, but it should provide sufficient validation testing just to verify the
        // applied PWM signal is close to the expected frequency calculate margin of error offset value
        long marginOfError = Math.round(frequency * .30);

        // test measured value against HI/LOW offsets to determine acceptable range
        if(measured.frequency < frequency-marginOfError) return false;
        if(measured.frequency > frequency+marginOfError) return false;

        // success
        return true;
    }
}
