package com.pi4j.library.pigpio.test.gpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  TestDigitalOutputsUsingTestHarness.java
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

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioMode;
import com.pi4j.library.pigpio.PiGpioPud;
import com.pi4j.library.pigpio.PiGpioState;
import com.pi4j.library.pigpio.test.TestEnv;
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

@DisplayName("PIGPIO Library :: Test Digital Ouput Pins")
public class TestDigitalOutputsUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestDigitalOutputsUsingTestHarness.class);

    private static PiGpio pigpio;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestDigitalOutputsUsingTestHarness.class.getName() + ")");
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
        logger.info("TERMINATE TEST (" + TestDigitalOutputsUsingTestHarness.class.getName() + ") ");
        logger.info("************************************************************************");
        logger.info("");

        // shutdown test harness and PIGPIO instances
        pigpio.shutdown();
        harness.shutdown();
    }

    @Test
    @DisplayName("GPIO :: Test Digital Output Pins")
    public void testGpioDigitalOutputs() throws IOException {
        // reset all pins on test harness before proceeding with this test
        TestHarnessPins reset = harness.reset();

        logger.info("");
        logger.info("RESET ALL PINS ON TEST HARNESS; (" + reset.total + " pin reset)");

        // iterate over pins and perform test on each
        // TODO :: IMPLEMENT CORRECT SET OF TEST PINS
        for(int pin = 2; pin <= 27; pin++){
            testDigitalOutputPin(pin);
        }
    }

    public void testDigitalOutputPin(int pin) throws IOException{
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST SOC DIGITAL OUTPUT PIN [" + pin + "]");
        logger.info("----------------------------------------");

        // configure test pin as an input pin on the test harness;
        // use the internal pull-up resistor on the Arduino hardware
        harness.setInputPin(pin, true);

        // configure output pin on test SoC (RaspberryPi)
        pigpio.gpioSetMode(pin, PiGpioMode.OUTPUT);

        logger.info("");
        PiGpioState state = PiGpioState.LOW;
        logger.info("TEST OUTPUT FOR [" + state.name() + "] STATE");
        pigpio.gpioWrite(pin, state); // set output pin state on SoC (RaspberryPi)
        TestHarnessPin p = harness.getPin(pin); // get input pin state from the test harness
        logger.info(" (SET)  >> SOC  PIN [" + pin + "] VALUE = " + state);
        logger.info(" (READ) << TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        assertEquals(state.value(), p.value, "INCORRECT PIN VALUE");

        logger.info("");
        state = PiGpioState.HIGH;
        logger.info("TEST OUTPUT FOR [" + state.name() + "] STATE");
        pigpio.gpioWrite(pin, state); // set output pin state on SoC (RaspberryPi)
        p = harness.getPin(pin); // get input pin state from the test harness
        logger.info(" (SET)  >> SOC  PIN [" + pin + "] VALUE = " + state);
        logger.info(" (READ) << TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        assertEquals(state.value(), p.value, "INCORRECT PIN VALUE");

        logger.info("");
        state = PiGpioState.LOW;
        logger.info("TEST OUTPUT FOR [" + state.name() + "] STATE");
        pigpio.gpioWrite(pin, state); // set output pin state on SoC (RaspberryPi)
        p = harness.getPin(pin); // get input pin state from the test harness
        logger.info(" (SET)  >> SOC  PIN [" + pin + "] VALUE = " + state);
        logger.info(" (READ) << TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        assertEquals(state.value(), p.value, "INCORRECT PIN VALUE");

        logger.info("");
        state = PiGpioState.HIGH;
        logger.info("TEST OUTPUT FOR [" + state.name() + "] STATE");
        pigpio.gpioWrite(pin, state); // set output pin state on SoC (RaspberryPi)
        p = harness.getPin(pin); // get input pin state from the test harness
        logger.info(" (SET)  >> SOC  PIN [" + pin + "] VALUE = " + state);
        logger.info(" (READ) << TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        assertEquals(state.value(), p.value, "INCORRECT PIN VALUE");

        logger.info("");
        state = PiGpioState.LOW;
        logger.info("TEST OUTPUT FOR [" + state.name() + "] STATE");
        pigpio.gpioWrite(pin, state); // set output pin state on SoC (RaspberryPi)
        p = harness.getPin(pin); // get input pin state from the test harness
        logger.info(" (SET)  >> SOC  PIN [" + pin + "] VALUE = " + state);
        logger.info(" (READ) << TEST PIN [" + p.pin + "] VALUE = " + PiGpioState.from(p.value));
        assertEquals(state.value(), p.value, "INCORRECT PIN VALUE");

        // disable test pin on the test harness
        p = harness.disablePin(pin);
        logger.info("");
        logger.info("DISABLE TEST PIN [" + p.pin + "] ON TEST HARNESS <" + p.access + ">");

        // reset pin on SoC (Raspberry Pi) (defaults as an input pin)
        pigpio.gpioSetMode(pin, PiGpioMode.INPUT);
        pigpio.gpioSetPullUpDown(pin, PiGpioPud.OFF);
    }
}
