package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpio.java
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

import com.pi4j.library.pigpio.impl.PiGpioSocketImpl;

import java.io.IOException;

public interface PiGpio {

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    static PiGpio newSocketInstance(String host, int port) throws IOException {
        return PiGpioSocketImpl.newInstance(host, port);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address using the default port (8888).
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    static PiGpio newSocketInstance(String host) throws IOException {
        return PiGpioSocketImpl.newInstance(host);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to the local system (127.0.0.1) using the default port (8888).
     *
     * @throws IOException
     */
    static PiGpio newSocketInstance() throws IOException {
        return PiGpioSocketImpl.newInstance();
    }

    /**
     * Initialises the library.
     *
     * Returns the pigpio version number if OK, otherwise PI_INIT_FAILED.
     * gpioInitialise must be called before using the other library functions with the following exceptions:
     * - gpioCfg*
     * - gpioVersion
     * - gpioHardwareRevision
     *
     * @return result value
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioInitialise"
     */
    long gpioInitialise();
    default long gpioInitialize(){ return gpioInitialise(); } // US spelling variant
    default long initialise(){ return gpioInitialise(); }
    default long initialize(){ return gpioInitialise(); }

    /**
     * Terminates the library.
     *
     * Returns nothing.
     * Call before program exit.
     * This function resets the used DMA channels, releases memory, and terminates any running threads.
     */
    void gpioTerminate();
    default void terminate(){ gpioTerminate(); }

    /**
     * Returns the pigpio library version.
     *
     * @return pigpio version.
     * @throws IOException
     * @throws InterruptedException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioVersion"
     */
    long gpioVersion() throws IOException;

    /**
     * Returns the hardware revision.
     *
     * If the hardware revision can not be found or is not a valid hexadecimal number the function returns 0.
     * The hardware revision is the last few characters on the Revision line of /proc/cpuinfo.
     * The revision number can be used to determine the assignment of GPIO to pins (see gpio).
     *
     * There are at least three types of board.
     *  - Type 1 boards have hardware revision numbers of 2 and 3.
     *  - Type 2 boards have hardware revision numbers of 4, 5, 6, and 15.
     *  - Type 3 boards have hardware revision numbers of 16 or greater.
     *
     *     for "Revision : 0002" the function returns 2.
     *     for "Revision : 000f" the function returns 15.
     *     for "Revision : 000g" the function returns 0.
     *
     * @return hardware revision as raw 32-bit UINT
     * @throws IOException
     * @seel "http://abyz.me.uk/rpi/pigpio/cif.html#gpioHardwareRevision"
     */
    long gpioHardwareRevision() throws IOException;


    /**
     * Returns the hardware revision (as hexadecimal string).
     *
     * If the hardware revision can not be found or is not a valid hexadecimal number the function returns 0.
     * The hardware revision is the last few characters on the Revision line of /proc/cpuinfo.
     * The revision number can be used to determine the assignment of GPIO to pins (see gpio).
     *
     * There are at least three types of board.
     *  - Type 1 boards have hardware revision numbers of 2 and 3.
     *  - Type 2 boards have hardware revision numbers of 4, 5, 6, and 15.
     *  - Type 3 boards have hardware revision numbers of 16 or greater.
     *
     *     for "Revision : 0002" the function returns 2.
     *     for "Revision : 000f" the function returns 15.
     *     for "Revision : 000g" the function returns 0.
     *
     * @return hardware revision in hexadecimal string
     * @throws IOException
     * @seel "http://abyz.me.uk/rpi/pigpio/cif.html#gpioHardwareRevision"
     */
    String gpioHardwareRevisionString() throws IOException;

    /**
     * Sets or clears resistor pull ups or downs on the GPIO.
     *
     * @param pin gpio pin address
     * @param pud pull-up, pull-down, pull-off
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetPullUpDown"
     */
    void gpioSetPullUpDown(int pin, PiGpioPud pud) throws IOException;

    /**
     * Gets the GPIO mode.
     *
     * @param pin gpio pin address
     * @return  pin mode: input, output, etc.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetMode"
     */
    PiGpioMode gpioGetMode(int pin) throws IOException;

    /**
     * Sets the GPIO mode, typically input or output.
     *
     * @param pin gpio pin address
     * @param mode pin mode: input, output, etc.
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetMode"
     */
    void gpioSetMode(int pin, PiGpioMode mode) throws IOException;

    /**
     * Reads the GPIO level, on (HIGH) or off (LOW).
     * @param pin gpio pin address
     * @return pin state: HIGH or LOW
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioRead"
     */
    PiGpioState gpioRead(int pin) throws IOException;


    /**
     * Sets the GPIO level, on (HIGH) or off (LOW).
     *
     * @param pin gpio pin address
     * @param state HIGH or LOW
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioWrite"
     */
    void gpioWrite(int pin, PiGpioState state) throws IOException;


    /**
     * Starts PWM on the GPIO, duty-cycle between 0 (off) and range (fully on). Range defaults to 255.
     *
     * This and the servo functionality use the DMA and PWM or PCM peripherals to control and schedule
     * the pulse lengths and duty cycles.
     *
     * @param pin user_gpio: 0-31
     * @param dutyCycle dutycycle: 0-range
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioPWM"
     */
    void gpioPWM(int pin, int dutyCycle) throws IOException;
    default void gpioSetPWMdutycycle(int pin, int dutyCycle) throws IOException{
        gpioPWM(pin,dutyCycle);
    }


    /**
     * Returns the PWM dutycycle setting for the GPIO.
     *
     * For normal PWM the dutycycle will be out of the defined range for the GPIO (see gpioGetPWMrange).
     * If a hardware clock is active on the GPIO the reported dutycycle will be 500000 (500k) out of 1000000 (1M).
     * If hardware PWM is active on the GPIO the reported dutycycle will be out of a 1000000 (1M).
     *
     * Normal PWM range defaults to 255.
     *
     * @param pin user_gpio: 0-31
     * @return Returns between 0 (off) and range (fully on) if OK.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetPWMdutycycle"
     */
    int gpioGetPWMdutycycle(int pin) throws IOException;


    /**
     * Selects the dutycycle range to be used for the GPIO. Subsequent calls to gpioPWM will use a dutycycle
     * between 0 (off) and range (fully on.  If PWM is currently active on the GPIO its dutycycle will be
     * scaled to reflect the new range.
     *
     * The real range, the number of steps between fully off and fully on for each frequency,
     * is given in the following table.
     *
     *  -------------------------------------------------------
     *   #1	   #2	 #3	   #4	 #5	   #6	 #7	    #8	   #9
     *   25,   50,  100,  125,  200,  250,  400,   500,   625,
     *  -------------------------------------------------------
     *  #10   #11   #12   #13   #14   #15    #16   #17    #18
     *  800, 1000, 1250, 2000, 2500, 4000, 5000, 10000, 20000
     *  -------------------------------------------------------
     *
     * The real value set by gpioPWM is (dutycycle * real range) / range.
     *
     * Example
     *   gpioSetPWMrange(24, 2000); // Now 2000 is fully on
     *                              //     1000 is half on
     *                              //      500 is quarter on, etc.
     * @param pin user_gpio: 0-31
     * @param range range: 25-40000
     * @return real range for the given GPIO's frequency if OK.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetPWMrange"
     */
    int gpioSetPWMrange(int pin, int range) throws IOException;

    /**
     * Returns the duty-cycle range used for the GPIO if OK.
     * If a hardware clock or hardware PWM is active on the GPIO the reported range will be 1000000 (1M).
     *
     * @param pin user_gpio: 0-31
     * @return duty-cycle range
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetPWMrange"
     */
    int gpioGetPWMrange(int pin) throws IOException;

    /**
     * Returns the real range used for the GPIO if OK.
     * If a hardware clock is active on the GPIO the reported real range will be 1000000 (1M).
     * If hardware PWM is active on the GPIO the reported real range will be approximately 250M
     * divided by the set PWM frequency.
     *
     * @param pin user_gpio: 0-31
     * @return real range used for the GPIO if OK.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetPWMrealRange"
     */
    int gpioGetPWMrealRange(int pin) throws IOException;

}
