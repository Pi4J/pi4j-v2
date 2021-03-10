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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioException;
import com.pi4j.library.pigpio.PiGpioMode;
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessFrequency;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("PIGPIO Library :: Test Software Emulated PWM Pins")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestSoftwarePwmUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestSoftwarePwmUsingTestHarness.class);

    private PiGpio pigpio;
    private int handle;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestSoftwarePwmUsingTestHarness.class.getName() + ")");
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
            logger.error(e.getMessage(), e);
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestSoftwarePwmUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // shutdown connection to test harness
        harness.shutdown();
    }

    @BeforeEach
    public void beforeEach() {
        // create test harness and PIGPIO instances
        pigpio = TestEnv.createPiGpio();

        // initialize test harness and PIGPIO instances
        pigpio.initialize();

        // reset I/O
        //harness.reset();
    }

    @AfterEach
    public void afterEach() {
        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
    }

    @Test
    @Order(1)
    @DisplayName("PWM :: Test PWM @ 50 Hz")
    public void testPwmAt50Hertz() {
        testPwm(50);
    }

    @Test
    @Order(2)
    @DisplayName("PWM :: Test PWM @ 100 Hz")
    public void testPwmAt100Hertz() {
        testPwm(100);
    }

    @Test
    @Order(3)
    @DisplayName("PWM :: Test PWM @ 700 Hz")
    public void testPwmAt700Hertz() {
        testPwm(700);
    }

    @Test
    @Order(4)
    @DisplayName("PWM :: Test PWM @ 1000 Hz (1 KHz)")
    public void testPwmAt1000Hertz() {
        testPwm(1000);
    }

    @Test
    @Order(5)
    @DisplayName("PWM :: Test PWM @ 5000 Hz (5 KHz)")
    public void testPwmAt5000Hertz() {
        testPwm(5000);
    }

    @Test
    @Order(6)
    @DisplayName("PWM :: Test PWM @ 10000 Hz (10 KHz)")
    public void testPwmAt10000Hertz() {
        testPwm(10000);
    }

    public void testPwm(int frequency) {
        testPwm(frequency, 128); // 50% duty-cycle by default
    }

    public void testPwm(int frequency, int dutyCycle) {
        try {
            logger.info("");
            logger.info("----------------------------------------");
            logger.info("TEST PWM SIGNALS AT " + frequency + " HZ");
            logger.info("----------------------------------------");

            for(int p = 2; p <= 27; p++) {
                // set pin to output pin
                pigpio.gpioSetMode(p, PiGpioMode.OUTPUT);

                logger.info("");
                logger.info("[TEST SOFT PWM] :: PIN=" + p);
                int actualFrequency = pigpio.gpioSetPWMfrequency(p, frequency);

                // write PWM frequency and duty-cycle
                pigpio.gpioPWM(p, dutyCycle);
                logger.info(" (WRITE) >> PWM FREQUENCY  = " + frequency  +
                        ((actualFrequency != frequency) ? " (ACTUAL FREQ SET: " + actualFrequency + ")" : ""));
                logger.info(" (WRITE) >> PWM DUTY-CYCLE = " + dutyCycle);

                // read back frequency and duty cycle
                int readFrequency = pigpio.gpioGetPWMfrequency(p);
                int readDutyCycle = pigpio.gpioGetPWMdutycycle(p);

                logger.info(" (READ)  << PWM FREQUENCY  = " + readFrequency + "; (EXPECTED=" + actualFrequency + ")");
                logger.info(" (READ)  << PWM DUTY-CYCLE = " + readDutyCycle + "; (EXPECTED=" + dutyCycle + ")");

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
                            pigpio.gpioPWM(p, 0);

                            // give up and fail
                            fail("PWM MEASURED FREQUENCY OUT OF ACCEPTABLE MARGIN OF ERROR");
                        }
                    }
                }

                // turn off PWM pin
                pigpio.gpioPWM(p, 0);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean measureFrequency(int pin, int frequency) {
        try {
            TestHarnessFrequency measured = harness.getFrequency(pin);

            float deviation = (measured.frequency - frequency) * 100/(float)frequency;
            logger.info(" (TEST)  << MEASURED FREQUENCY = " + measured.frequency + "; (EXPECTED=" + frequency + "; DEVIATION: " + deviation + "%)");

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
        } catch (IOException e) {
            throw new PiGpioException(e);
        }
    }
}
