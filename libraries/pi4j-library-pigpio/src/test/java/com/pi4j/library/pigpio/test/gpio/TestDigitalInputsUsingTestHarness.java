package com.pi4j.library.pigpio.test.gpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestDigitalInputsUsingTestHarness.java
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
import com.pi4j.library.pigpio.PiGpioPud;
import com.pi4j.library.pigpio.PiGpioState;
import com.pi4j.library.pigpio.test.TestEnv;
import com.pi4j.library.pigpio.test.TestGpioAlertRaw;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPin;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("PIGPIO Library :: Test Digital Input Pins")
public class TestDigitalInputsUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestDigitalInputsUsingTestHarness.class);

    private static PiGpio pigpio;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestDigitalInputsUsingTestHarness.class.getName() + ")");
        logger.info("************************************************************************");
        logger.info("");

        try {
            // create test harness and PIGPIO instances
            harness = TestEnv.createTestHarness();
            pigpio = TestEnv.createPiGpio();

            // initialize test harness and PIGPIO instances
            pigpio.initialize();
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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestDigitalInputsUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
        harness.shutdown();
    }

    @Test
    @DisplayName("GPIO :: Test Digital Input Pins")
    public void testGpioDigitalInputs() throws IOException {

        // reset all pins on test harness before proceeding with this test
        TestHarnessPins reset = harness.reset();

        logger.info("");
        logger.info("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");

        // iterate over pins and perform test on each
        // TODO :: IMPLEMENT CORRECT SET OF TEST PINS
        for(int pin = 2; pin <= 27; pin++){
            testDigitalInputPin(pin);
        }
    }

    @Test
    @DisplayName("GPIO :: Test Pull-Up/Down on Digital Pins")
    public void testGpioDigitalPullUpDown() throws IOException {

        // reset all pins on test harness before proceeding with this test
        TestHarnessPins reset = harness.reset();

        logger.info("");
        logger.info("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");

        // iterate over pins and perform test on each
        // TODO :: IMPLEMENT CORRECT SET OF TEST PINS
        for(int pin = 2; pin <= 27; pin++){

            // the following inputs are skipped because they always fail; possible
            // because they are tied to other things that override the software
            // configurable pull-up/down resistors
            if(pin == 2) continue; // RPi I2C PINS have on-board pull-up resistors
            if(pin == 3) continue; // RPi I2C PINS have on-board pull-up resistors

            testDigitalInputPullUpDown(pin);
        }
    }

    public void testDigitalInputPin(int pin) throws IOException{
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST SOC DIGITAL INPUT PIN [" + pin + "]");
        logger.info("----------------------------------------");

        // configure pin as an output pin on the test harness
        TestHarnessPin p = harness.setOutputPin(pin, false);

        // configure input pin on test SoC (RaspberryPi)
        pigpio.gpioSetMode(pin, PiGpioMode.INPUT);

        // do not use internal pull down resistors;
        // these seem to overpower the HIGH signal from the Arduino
        pigpio.gpioSetPullUpDown(pin, PiGpioPud.DOWN);

        // get input pin state from SoC (RaspberryPi)
        PiGpioState state = pigpio.gpioRead(pin);

        logger.info("");
        logger.info("TEST INPUT FOR [LOW] STATE <PULL-OFF>");
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + state);
        assertEquals(p.value, state.value(), "INCORRECT PIN VALUE");

        logger.info("");
        logger.info("TEST INPUT FOR [HIGH] STATE <PULL-OFF>");
        p = harness.setOutputPin(pin, true); // set output pin state on the test harness
        state = pigpio.gpioRead(pin); // get input pin state from SoC (RaspberryPi)
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + state);
        assertEquals(p.value, state.value(), "INCORRECT PIN VALUE");

        logger.info("");
        logger.info("TEST INPUT FOR [LOW] STATE <PULL-OFF>");
        p = harness.setOutputPin(pin, false); // set output pin state on the test harness
        state = pigpio.gpioRead(pin); // get input pin state from SoC (RaspberryPi)
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + state);
        assertEquals(p.value, state.value(), "INCORRECT PIN VALUE");

        // now test the input pins using the PULL UP resistor on the SoC
        pigpio.gpioSetPullUpDown(pin, PiGpioPud.UP);

        logger.info("");
        logger.info("TEST INPUT FOR [HIGH] STATE <PULL-UP>");
        p = harness.setOutputPin(pin, true); // set output pin state on the test harness
        state = pigpio.gpioRead(pin); // get input pin state from SoC (RaspberryPi)
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + state);
        assertEquals(p.value, state.value(), "INCORRECT PIN VALUE");

        logger.info("");
        logger.info("TEST INPUT FOR [LOW] STATE <PULL-UP>");
        p = harness.setOutputPin(pin, false); // set output pin state on the test harness
        state = pigpio.gpioRead(pin); // get input pin state from SoC (RaspberryPi)
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + state);
        assertEquals(p.value, state.value(), "INCORRECT PIN VALUE");

        // disable test pin on the test harness
        p = harness.disablePin(pin);
        logger.info("");
        logger.info("DISABLE TEST PIN [" + p.pin + "] ON TEST HARNESS <" + p.access + ">");
    }

    public void testDigitalInputPullUpDown(int pin) throws IOException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST SOC DIGITAL INPUT PULL on PIN [" + pin + "]");
        logger.info("----------------------------------------");

        // configure pin as an input pin on the test harness
        harness.setInputPin(pin, false);

        // configure input pin on test SoC (RaspberryPi)
        pigpio.gpioSetMode(pin, PiGpioMode.INPUT);

        PiGpioPud pud = PiGpioPud.DOWN;
        pigpio.gpioSetPullUpDown(pin, pud);  // set input pin PUD on Soc (RaspberryPi)
        pigpio.gpioDelayMilliseconds(100);
        TestHarnessPin p = harness.getPin(pin); // get input state from the test harness
        logger.info("");
        logger.info("TEST PULL FOR [DOWN] STATE");
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] PULL = " + pud.name());
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + p.value);
        assertEquals(p.value, 0, "INCORRECT PIN PULL");

        pud = PiGpioPud.UP;
        pigpio.gpioSetPullUpDown(pin, pud);  // set input pin PUD on Soc (RaspberryPi)
        p = harness.getPin(pin); // get input state from the test harness
        logger.info("");
        logger.info("TEST PULL FOR [DOWN] STATE");
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] PULL = " + pud.name());
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + p.value);
        assertEquals(p.value, 1, "INCORRECT PIN PULL");

        pud = PiGpioPud.DOWN;
        pigpio.gpioSetPullUpDown(pin, pud);  // set input pin PUD on Soc (RaspberryPi)
        pigpio.gpioDelayMilliseconds(100);
        p = harness.getPin(pin); // get input state from the test harness
        logger.info("");
        logger.info("TEST PULL FOR [DOWN] STATE");
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] PULL = " + pud.name());
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + p.value);
        assertEquals(p.value, 0, "INCORRECT PIN PULL");

        pud = PiGpioPud.UP;
        pigpio.gpioSetPullUpDown(pin, pud);  // set input pin PUD on Soc (RaspberryPi)
        p = harness.getPin(pin); // get input state from the test harness
        logger.info("");
        logger.info("TEST PULL FOR [DOWN] STATE");
        logger.info(" (SET)  >> TEST PIN [" + p.pin + "] PULL = " + pud.name());
        logger.info(" (READ) << SOC PIN [" + pin + "] VALUE = " + p.value);
        assertEquals(p.value, 1, "INCORRECT PIN PULL");

        // disable test pin on the test harness
        p = harness.disablePin(pin);
        logger.info("");
        logger.info("DISABLE TEST PIN [" + p.pin + "] ON TEST HARNESS <" + p.access + ">");
    }
}
