package com.pi4j.library.pigpio.test.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestSoftwarePwmUsingTestHarness.java
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("PIGPIO Library :: Test Software Emulated PWM Pins")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSoftwarePwmUsingTestHarness {

    private PiGpio pigpio;
    private int handle;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        //System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("INITIALIZE TEST (" + TestSoftwarePwmUsingTestHarness.class.getName() + ")");
        System.out.println("************************************************************************");
        System.out.println();

        try {
            // create test harness and PIGPIO instances
            harness = TestEnv.createTestHarness();

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
        //harness.reset();
    }

    @AfterEach
    public void afterEach() throws IOException {
        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @Order(1)
    @DisplayName("PWM :: Test PWM @ 50 Hz")
    public void testPwmAt50Hertz() throws IOException, InterruptedException {
        testPwm(50);
    }

    @Test
    @Order(2)
    @DisplayName("PWM :: Test PWM @ 100 Hz")
    public void testPwmAt100Hertz() throws IOException, InterruptedException {
        testPwm(100);
    }

    @Test
    @Order(3)
    @DisplayName("PWM :: Test PWM @ 700 Hz")
    public void testPwmAt700Hertz() throws IOException, InterruptedException {
        testPwm(700);
    }

    @Test
    @Order(4)
    @DisplayName("PWM :: Test PWM @ 1000 Hz (1 KHz)")
    public void testPwmAt1000Hertz() throws IOException, InterruptedException {
        testPwm(1000);
    }

    @Test
    @Order(5)
    @DisplayName("PWM :: Test PWM @ 5000 Hz (5 KHz)")
    public void testPwmAt5000Hertz() throws IOException, InterruptedException {
        testPwm(5000);
    }

    @Test
    @Order(6)
    @DisplayName("PWM :: Test PWM @ 10000 Hz (10 KHz)")
    public void testPwmAt10000Hertz() throws IOException, InterruptedException {
        testPwm(10000);
    }

    public void testPwm(int frequency) throws IOException, InterruptedException {
        testPwm(frequency, 128); // 50% duty-cycle by default
    }
    public void testPwm(int frequency, int dutyCycle) throws IOException, InterruptedException {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST PWM SIGNALS AT " + frequency + " HZ");
        System.out.println("----------------------------------------");

        for(int p = 2; p <= 27; p++) {

            // set pin to output pin
            pigpio.gpioSetMode(p, PiGpioMode.OUTPUT);

            System.out.println();
            System.out.println("[TEST SOFT PWM] :: PIN=" + p);
            int actualFrequency = pigpio.gpioSetPWMfrequency(p, frequency);

            // write PWM frequency and duty-cycle
            pigpio.gpioPWM(p, dutyCycle);
            System.out.println(" (WRITE) >> PWM FREQUENCY  = " + frequency  +
                    ((actualFrequency != frequency) ? " (ACTUAL FREQ SET: " + actualFrequency + ")" : ""));
            System.out.println(" (WRITE) >> PWM DUTY-CYCLE = " + dutyCycle);

            // read back frequency and duty cycle
            int readFrequency = pigpio.gpioGetPWMfrequency(p);
            int readDutyCycle = pigpio.gpioGetPWMdutycycle(p);

            System.out.println(" (READ)  << PWM FREQUENCY  = " + readFrequency + "; (EXPECTED=" + actualFrequency + ")");
            System.out.println(" (READ)  << PWM DUTY-CYCLE = " + readDutyCycle + "; (EXPECTED=" + dutyCycle + ")");

            assertEquals(actualFrequency, readFrequency, "PWM FREQUENCY MISMATCH");
            assertEquals(dutyCycle, readDutyCycle, "PWM DUTY-CYCLE MISMATCH");

            Thread.sleep(100);

            // test once ..
            if(measureFrequency(p, actualFrequency) == false){
                // test twice ..
                Thread.sleep(1000);
                if(measureFrequency(p, actualFrequency) == false){
                    // test thrice ..
                    Thread.sleep(2000);
                    if(measureFrequency(p, actualFrequency) == false){
                        // turn off PWM pin
                        pigpio.gpioPWM(p, 0);;

                        // give up and fail
                        fail("PWM MEASURED FREQUENCY OUT OF ACCEPTABLE MARGIN OF ERROR");
                    }
                }
            }

            // turn off PWM pin
            pigpio.gpioPWM(p, 0);;
        }
    }

    private boolean measureFrequency(int pin, int frequency) throws IOException {
        TestHarnessFrequency measured = harness.getFrequency(pin);

        float deviation = (measured.frequency - frequency) * 100/(float)frequency;
        System.out.println(" (TEST)  << MEASURED FREQUENCY = " + measured.frequency + "; (EXPECTED=" + frequency + "; DEVIATION: " + deviation + "%)");

        // we allow a 25% margin of error, the testing harness uses a simple pulse counter to crudely
        // measure the PWM signal, its not very accurate but should provide sufficient validation testing
        // just to verify the applied PWM signal is close to the expected frequency
        // calculate margin of error offset value
        long marginOfError = Math.round(frequency * 25);

        // test measured value against HI/LOW offsets to determine acceptable range
        if(measured.frequency < frequency-marginOfError) return false;
        if(measured.frequency > frequency+marginOfError) return false;

        // success
        return true;
    }
}
