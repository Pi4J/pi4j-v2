package com.pi4j.plugin.pigpio.test.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: PIGPIO I/O Providers
 * FILENAME      :  TestDigitalInputUsingTestHarness.java
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
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.io.gpio.digital.PullResistance;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.plugin.pigpio.test.TestEnv;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("PIGPIO Plugin :: Test Digital Input Pins using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDigitalInputUsingTestHarness {

    private static final Logger logger = LoggerFactory.getLogger(TestDigitalInputUsingTestHarness.class);

    public static int PIN_MIN = 2;
    public static int PIN_MAX = 27;

    private PiGpio piGpio;
    private Context pi4j;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        logger.info("");
        logger.info("************************************************************************");
        logger.info("INITIALIZE TEST (" + TestDigitalInputUsingTestHarness.class.getName() + ")");
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
        logger.info("TERMINATE TEST (" + TestDigitalInputUsingTestHarness.class.getName() + ") ");
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
        var provider = PiGpioDigitalInputProvider.newInstance(piGpio);

        // initialize Pi4J instance with this single provider
        pi4j = Pi4J.newContextBuilder().add(provider).build();
    }

    @AfterEach
    public void afterEach() {
        // shutdown Pi4J after each test
        pi4j.shutdown();

        // shutdown the PiGpio library after each test
        piGpio.shutdown();
    }

    @Test
    @Order(1)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <HIGH>")
    public void testDigitalInputsHigh() throws IOException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST DIGITAL INPUT PINS - HIGH");
        logger.info("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder(pi4j)
                    .id("my-din-pin-" + p)
                    .name("My Digital Input Pin #" + p)
                    .address(p)
                    .build();

            // create Digital Input I/O instance
            DigitalInput din = pi4j.create(config);

            // register event handler
            din.addListener((DigitalStateChangeListener) event -> logger.info(event.toString()));

            // configure output pin to HIGH state on testing harness
            harness.setOutputPin(p, true);

            DigitalState state = din.state();
            logger.info("(PIN #" + p + ") >> STATE  = " + state);
            assertEquals(DigitalState.HIGH, state, "DIGITAL INPUT STATE MISMATCH: " + p);
        }
    }

    @Test
    @Order(2)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <LOW>")
    public void testDigitalInputsLow() throws IOException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST DIGITAL INPUT PINS - LOW");
        logger.info("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder(pi4j)
                    .id("my-din-pin-" + p)
                    .name("My Digital Input Pin #" + p)
                    .address(p)
                    .build();

            // create Digital Input I/O instance
            DigitalInput din = pi4j.create(config);

            // configure output pin to LOW state on testing harness
            harness.setOutputPin(p, false);

            DigitalState state = din.state();
            logger.info("(PIN #" + p + ") >> STATE  = " + state);
            assertEquals(DigitalState.LOW, state, "DIGITAL INPUT STATE MISMATCH: " + p);
        }
    }

    @Test
    @Order(3)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <PULL-UP>")
    public void testDigitalInputsPullUp() throws IOException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST DIGITAL INPUT PINS - PULL UP");
        logger.info("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder(pi4j)
                    .id("my-din-pin-" + p)
                    .name("My Digital Input Pin #" + p)
                    .address(p)
                    .pull(PullResistance.PULL_UP)
                    .build();

            // create Digital Input I/O instance
            DigitalInput din = pi4j.create(config);

            // configure input pin and read value/state on testing harness
            harness.setInputPin(p, false);
            int pull = harness.getPin(p).value;

            logger.info("(PIN #" + p + ") >> PULL = " + pull);
            assertEquals(1, pull, "DIGITAL INPUT PULL MISMATCH: " + p);
        }
    }

    @Test
    @Order(4)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <PULL-DOWN>")
    public void testDigitalInputsPullDown() throws IOException {
        logger.info("");
        logger.info("----------------------------------------");
        logger.info("TEST DIGITAL INPUT PINS - PULL DOWN");
        logger.info("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // the following inputs are skipped because they always fail; possible
            // because they are tied to other things that override the software
            // configurable pull-up/down resistors
            if(p == 2) continue; // RPi I2C PINS have on-board pull-up resistors
            if(p == 3) continue; // RPi I2C PINS have on-board pull-up resistors

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder(pi4j)
                    .id("my-din-pin-" + p)
                    .name("My Digital Input Pin #" + p)
                    .address(p)
                    .pull(PullResistance.PULL_DOWN)
                    .build();

            // create Digital Input I/O instance
            DigitalInput din = pi4j.create(config);

            // configure input pin and read value/state on testing harness
            harness.setInputPin(p, false);
            int pull = harness.getPin(p).value;

            logger.info("(PIN #" + p + ") >> PULL = " + pull);
            assertEquals(0, pull, "DIGITAL INPUT PULL MISMATCH: " + p);
        }
    }
}
