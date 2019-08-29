package com.pi4j.plugin.pigpio.gpio.digital;

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
import com.pi4j.io.gpio.digital.*;
import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.plugin.pigpio.provider.gpio.digital.PiGpioDigitalInputProvider;
import com.pi4j.test.harness.ArduinoTestHarness;
import com.pi4j.test.harness.TestHarnessInfo;
import com.pi4j.test.harness.TestHarnessPins;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;

@DisplayName("PIGPIO Plugin :: Test Digital Input Pins using Test Harness")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDigitalInputUsingTestHarness {

    public static int PIN_MIN = 0;
    public static int PIN_MAX = 27;

    private PiGpio piGpio;
    private Context pi4j;
    private static ArduinoTestHarness harness;

    @BeforeAll
    public static void initialize() {
        // configure logging output
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        System.out.println();
        System.out.println("************************************************************************");
        System.out.println("INITIALIZE TEST (" + TestDigitalInputUsingTestHarness.class.getName() + ")");
        System.out.println("************************************************************************");
        System.out.println();

        try {
            // create test harness and PIGPIO instances
            harness = new ArduinoTestHarness(System.getProperty("pi4j.test.harness.port", "tty.usbserial-00000000"));

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
        System.out.println("TERMINATE TEST (" + TestDigitalInputUsingTestHarness.class.getName() + ") ");
        System.out.println("************************************************************************");
        System.out.println();

        // shutdown connection to test harness
        harness.shutdown();;
    }

    @BeforeEach
    public void beforeEach() throws Exception {

        // TODO :: THIS WILL NEED TO CHANGE WHEN NATIVE PIGPIO SUPPORT IS ADDED
        piGpio = PiGpio.newSocketInstance("rpi3bp");

        // initialize the PiGpio library
        piGpio.initialize();

        // create PWM provider instance to test with
        var provider = PiGpioDigitalInputProvider.newInstance(piGpio);

        // initialize Pi4J instance with this single provider
        pi4j = Pi4J.newContextBuilder().add(provider).build();
    }

    @AfterEach
    public void afterEach() throws Exception {
        // shutdown Pi4J after each test
        pi4j.shutdown();

        // shutdown the PiGpio library after each test
        piGpio.shutdown();
    }

    @Test
    @Order(1)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <HIGH>")
    public void testDigitalInputsHigh() throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST DIGITAL INPUT PINS - HIGH");
        System.out.println("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder()
                    .id("my-din-pin-" + p)
                    .name("My Digital Input Pin #" + p)
                    .address(p)
                    .build();

            // create Digital Input I/O instance
            DigitalInput din = pi4j.create(config);

            // register event handler
            din.addListener(new DigitalChangeListener() {
                @Override
                public void onChange(DigitalChangeEvent event) {
                    System.out.println(event.toString());
                }
            });

            // configure output pin to HIGH state on testing harness
            harness.setOutputPin(p, true);

            DigitalState state = din.state();
            System.out.println("(PIN #" + p + ") >> STATE  = " + state);
            Assert.assertEquals("DIGITAL INPUT STATE MISMATCH: " + p, DigitalState.HIGH, state);
        }
    }

    @Test
    @Order(2)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <LOW>")
    public void testDigitalInputsLow() throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST DIGITAL INPUT PINS - LOW");
        System.out.println("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder()
                    .id("my-din-pin-" + p)
                    .name("My Digital Input Pin #" + p)
                    .address(p)
                    .build();

            // create Digital Input I/O instance
            DigitalInput din = pi4j.create(config);

            // configure output pin to LOW state on testing harness
            harness.setOutputPin(p, false);

            DigitalState state = din.state();
            System.out.println("(PIN #" + p + ") >> STATE  = " + state);
            Assert.assertEquals("DIGITAL INPUT STATE MISMATCH: " + p, DigitalState.LOW, state);
        }
    }

    @Test
    @Order(3)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <PULL-UP>")
    public void testDigitalInputsPullUp() throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST DIGITAL INPUT PINS - PULL UP");
        System.out.println("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder()
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

            System.out.println("(PIN #" + p + ") >> PULL = " + pull);
            Assert.assertEquals("DIGITAL INPUT PULL MISMATCH: " + p, 1, pull);
        }
    }

    @Test
    @Order(4)
    @DisplayName("DIN :: Test GPIO Digital Input Pins <PULL-DOWN>")
    public void testDigitalInputsPullDown() throws Exception {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("TEST DIGITAL INPUT PINS - PULL DOWN");
        System.out.println("----------------------------------------");

        for(int p = PIN_MIN; p <= PIN_MAX; p++) {

            // the following inputs are skipped because they always fail; possible
            // because they are tied to other things that override the software
            // configurable pull-up/down resistors
            if(p == 2) continue; // RPi I2C PINS have on-board pull-up resistors
            if(p == 3) continue; // RPi I2C PINS have on-board pull-up resistors

            // create Digital Input instance config
            var config = DigitalInput.newConfigBuilder()
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

            System.out.println("(PIN #" + p + ") >> PULL = " + pull);
            Assert.assertEquals("DIGITAL INPUT PULL MISMATCH: " + p, 0, pull);
        }
    }
}
