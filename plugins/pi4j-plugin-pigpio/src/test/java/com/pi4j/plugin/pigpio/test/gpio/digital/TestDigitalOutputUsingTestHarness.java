package com.pi4j.plugin.pigpio.test.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestDigitalOutputUsingTestHarness.java
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalOutputProvider;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("PIGPIO Plugin :: Test Digital Output Pins using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDigitalOutputUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestDigitalOutputUsingTestHarness.class);

    public static int PIN_MIN = 2;
    public static int PIN_MAX = 27;

    private PiGpio piGpio;
    private Context pi4j;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestDigitalOutputUsingTestHarness.class.getName() + ")");
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
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterAll
    public static void terminate() throws IOException {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("TERMINATE TEST (" + TestDigitalOutputUsingTestHarness.class.getName() + ") ");
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
        var provider = PiGpioDigitalOutputProvider.newInstance(piGpio);

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
    @DisplayName("DIN :: Test GPIO Digital Output Pins")
    public void testDigitalOutputsHigh() throws IOException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST DIGITAL OUTPUT PINS ");
        logger.info("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // configure input pin on testing harness
            harness.setInputPin(p, true);

            // create Digital Output instance config
            var config = DigitalOutput.newConfigBuilder(pi4j)
                    .id("my-dout-pin-" + p)
                    .name("My Digital Output Pin #" + p)
                    .address(p)
                    .initial(DigitalState.HIGH)
                    .shutdown(DigitalState.LOW)
                    .build();

            // create Digital Output I/O instance
            DigitalOutput dout = pi4j.create(config);

            logger.info("");
            logger.info("[TEST DIGITAL OUTPUT] :: PIN=" + p);
            logger.info("");
            logger.info("  (PIN #" + p + "; INIT)  >> STATE  = " + dout.config().getInitialState());

            // read input state from test harness; compare with expected initial state
            int readState = harness.getPin(p).value;
            logger.info("  (PIN #" + p + "; READ)  << STATE  = " + DigitalState.getState(readState));
            assertEquals(dout.config().getInitialState().value(), readState, "DIGITAL OUTPUT STATE MISMATCH: " + p);

            // set state low
            dout.low();
            logger.info("  (PIN #" + p + "; WRITE) >> STATE  = " + "LOW");

            // read input state from test harness; compare with expected LOW state
            readState = harness.getPin(p).value;
            logger.info("  (PIN #" + p + "; READ)  << STATE  = " + DigitalState.getState(readState));
            assertEquals(DigitalState.LOW.value(), readState, "DIGITAL OUTPUT STATE MISMATCH: " + p);

            // set state high
            dout.high();
            logger.info("  (PIN #" + p + "; WRITE) >> STATE  = " + "HIGH");

            // read input state from test harness; compare with expected HIGH state
            readState = harness.getPin(p).value;
            logger.info("  (PIN #" + p + "; READ)  << STATE  = " + DigitalState.getState(readState));
            assertEquals(DigitalState.HIGH.value(), readState, "DIGITAL OUTPUT STATE MISMATCH: " + p);

            // toggle pin state
            dout.toggle();
            logger.info("  (PIN #" + p + "; WRITE) >> STATE  = " + "LOW");

            // read input state from test harness; compare with expected LOW state
            readState = harness.getPin(p).value;
            logger.info("  (PIN #" + p + "; READ)  << STATE  = " + DigitalState.getState(readState));
            assertEquals(DigitalState.LOW.value(), readState, "DIGITAL OUTPUT STATE MISMATCH: " + p);

            // set state high
            dout.setState(1);
            logger.info("  (PIN #" + p + "; WRITE) >> STATE  = " + "HIGH");

            // read input state from test harness; compare with expected HIGH state
            readState = harness.getPin(p).value;
            logger.info("  (PIN #" + p + "; READ)  << STATE  = " + DigitalState.getState(readState));
            assertEquals(DigitalState.HIGH.value(), readState, "DIGITAL OUTPUT STATE MISMATCH: " + p);

            // perform a shutdown on this pin
            dout.shutdown(pi4j);
            logger.info("  (PIN #" + p + "; SHUTD) >> STATE  = " + dout.config().getShutdownState());

            // read input state from test harness; compare with expected shutdown state
            readState = harness.getPin(p).value;
            logger.info("  (PIN #" + p + "; READ)  << STATE  = " + DigitalState.getState(readState));
            assertEquals(dout.config().getShutdownState().value(), readState, "DIGITAL OUTPUT STATE MISMATCH: " + p);
        }
    }
}
