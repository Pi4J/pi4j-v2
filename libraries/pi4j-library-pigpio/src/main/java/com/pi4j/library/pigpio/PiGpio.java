package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  PiGpio.java
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

import com.pi4j.library.pigpio.impl.PiGpioNativeImpl;
import com.pi4j.library.pigpio.impl.PiGpioSocketImpl;

import java.io.IOException;

/**
 * <p>PiGpio interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface PiGpio extends
        PiGpio_I2C,
        PiGpio_GPIO,
        PiGpio_PWM,
        PiGpio_Serial,
        PiGpio_SPI,
        PiGpio_Servo {

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @return a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @throws java.io.IOException if any.
     */
    static PiGpio newSocketInstance(String host, String port) throws IOException {
        return PiGpioSocketImpl.newInstance(host, port);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @return a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @throws java.io.IOException if any.
     */
    static PiGpio newSocketInstance(String host, int port) throws IOException {
        return PiGpioSocketImpl.newInstance(host, port);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address using the default port (8888).
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @return a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @throws java.io.IOException if any.
     */
    static PiGpio newSocketInstance(String host) throws IOException {
        return PiGpioSocketImpl.newInstance(host);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to the local system (127.0.0.1) using the default port (8888).
     *
     * @return a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @throws java.io.IOException if any.
     */
    static PiGpio newSocketInstance() throws IOException {
        return PiGpioSocketImpl.newInstance();
    }

    /**
     * Creates a PiGpio instance using direct (native) JNI access to the
     * libpigpio.so shared library.  This instance may only be used
     * when running directly on the Raspberry Pi hardware and when the
     * PiGpio Daemon is not running.  PiGpio does not support accessing
     * the native shared library while the daemon is running concurrently.
     *
     * @return a {@link com.pi4j.library.pigpio.PiGpio} object.
     * @throws java.io.IOException if any.
     */
    static PiGpio newNativeInstance() throws IOException {
        return PiGpioNativeImpl.newInstance();
    }

    /**
     * Get the initialized state of the PiGpio library
     * @return true or false based on initialized state.
     */
    boolean isInitialised();

    /**
     * Get the initialized state of the PiGpio library
     * @return true or false based on initialized state.
     */
    default boolean isInitialized(){
        return isInitialised();
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
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioInitialise">PIGPIO::gpioInitialise</a>
     * @throws java.io.IOException if any.
     */
    int gpioInitialise() throws IOException;

    /**
     * Initialises the library.
     *
     * Returns the pigpio version number if OK, otherwise PI_INIT_FAILED.
     * gpioInitialise must be called before using the other library functions with the following exceptions:
     * - gpioCfg*
     * - gpioVersion
     * - gpioHardwareRevision
     *
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioInitialise">PIGPIO::gpioInitialise</a>
     * @throws java.io.IOException if any.
     */
    default int initialize() throws IOException{ return gpioInitialise(); }

    /**
     * Initialises the library.
     *
     * Returns the pigpio version number if OK, otherwise PI_INIT_FAILED.
     * gpioInitialise must be called before using the other library functions with the following exceptions:
     * - gpioCfg*
     * - gpioVersion
     * - gpioHardwareRevision
     *
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioInitialise">PIGPIO::gpioInitialise</a>
     * @throws java.io.IOException if any.
     */
    default int gpioInitialize() throws IOException { return gpioInitialise(); } // US spelling variant

    /**
     * Initialises the library.
     *
     * Returns the pigpio version number if OK, otherwise PI_INIT_FAILED.
     * gpioInitialise must be called before using the other library functions with the following exceptions:
     * - gpioCfg*
     * - gpioVersion
     * - gpioHardwareRevision
     *
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioInitialise">PIGPIO::gpioInitialise</a>
     * @throws java.io.IOException if any.
     */
    default int initialise() throws IOException { return gpioInitialise(); }     // UK spelling variant

    /**
     * Shutdown/Terminate the library.
     *
     * Returns nothing.
     * Call before program exit.
     * This function resets the used DMA channels, releases memory, and terminates any running threads.
     *
     * @throws java.io.IOException if any.
     */
    default void shutdown() throws IOException{
        gpioTerminate();
    }

    /**
     * Shutdown/Terminate the library.
     *
     * Returns nothing.
     * Call before program exit.
     * This function resets the used DMA channels, releases memory, and terminates any running threads.
     *
     * @throws java.io.IOException if any.
     */
    default void terminate() throws IOException{
        gpioTerminate();
    }

    /**
     * Shutdown/Terminate the library.
     *
     * Returns nothing.
     * Call before program exit.
     * This function resets the used DMA channels, releases memory, and terminates any running threads.
     *
     * @throws java.io.IOException if any.
     */
    void gpioTerminate() throws IOException;

    /**
     * Returns the pigpio library version.
     *
     * @return pigpio version.
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioVersion">PIGPIO::gpioVersion</a>
     * @throws java.io.IOException if any.
     */
    int gpioVersion() throws IOException;

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
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioHardwareRevision">PIGPIO::gpioHardwareRevision</a>
     * @throws java.io.IOException if any.
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
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioHardwareRevision">PIGPIO::gpioHardwareRevision</a>
     * @throws java.io.IOException if any.
     */
    String gpioHardwareRevisionString() throws IOException;

    /**
     * Delays for at least the number of microseconds specified by micros. (between 1 and 1000000 [1 second])
     * (Delays of 100 microseconds or less use busy waits.)
     *
     * @param micros micros: the number of microseconds to sleep (between 1 and 1000000 [1 second])
     * @return Returns the actual length of the delay in microseconds.
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioDelay">PIGPIO::gpioDelay</a>
     * @throws java.io.IOException if any.
     */
    long gpioDelay(long micros) throws IOException;
    /**
     * <p>gpioDelayMicroseconds.</p>
     *
     * @param micros a int.
     * @return a int.
     * @throws java.io.IOException if any.
     */
    default long gpioDelayMicroseconds(long micros) throws IOException{
        return gpioDelay(micros);
    }

    /**
     * Delays for at least the number of milliseconds specified by micros. (between 1 and 60000 [1 minute])
     *
     * @param millis millis: the number of milliseconds to sleep (between 1 and 60000 [1 minute])
     * @return Returns the actual length of the delay in milliseconds.
     * @see <a href="http://abyz.me.uk/rpi/pigpio/pigs.html#MILS">PIGPIO::MILS</a>
     * @throws java.io.IOException if any.
     */
    int gpioDelayMilliseconds(int millis) throws IOException;

    /**
     * Returns the current system tick.
     * Tick is the number of microseconds since system boot.
     *
     * As tick is an unsigned 32 bit quantity it wraps around after 2^32 microseconds, which is
     * approximately 1 hour 12 minutes.  You don't need to worry about the wrap around as long as you
     * take a tick (uint32_t) from another tick, i.e. the following code will always provide the
     * correct difference.
     *
     * Example
     *   uint32_t startTick, endTick;
     *   int diffTick;
     *   startTick = gpioTick();
     *
     *   // do some processing
     *   endTick = gpioTick();
     *   diffTick = endTick - startTick;
     *   printf("some processing took %d microseconds", diffTick);
     *
     * @return Returns the current system tick.
     * @see <a href="http://abyz.me.uk/rpi/pigpio/cif.html#gpioTick">PIGPIO::gpioTick</a>
     * @throws java.io.IOException if any.
     */
    long gpioTick() throws IOException;

    /**
     * <p>gpioNotifications.</p>
     *
     * @param pin a int.
     * @param enabled a boolean.
     * @throws java.io.IOException if any.
     */
    void gpioNotifications(int pin, boolean enabled) throws IOException;
    /**
     * <p>gpioEnableNotifications.</p>
     *
     * @param pin a int.
     * @throws java.io.IOException if any.
     */
    default void gpioEnableNotifications(int pin) throws IOException{
        gpioNotifications(pin, true);
    }
    /**
     * <p>gpioDisableNotifications.</p>
     *
     * @param pin a int.
     * @throws java.io.IOException if any.
     */
    default void gpioDisableNotifications(int pin) throws IOException{
        gpioNotifications(pin, false);
    }
    /**
     * <p>addPinListener.</p>
     *
     * @param pin a int.
     * @param listener a {@link com.pi4j.library.pigpio.PiGpioStateChangeListener} object.
     */
    void addPinListener(int pin, PiGpioStateChangeListener listener);
    /**
     * <p>removePinListener.</p>
     *
     * @param pin a int.
     * @param listener a {@link com.pi4j.library.pigpio.PiGpioStateChangeListener} object.
     */
    void removePinListener(int pin, PiGpioStateChangeListener listener);
    /**
     * <p>removePinListeners.</p>
     *
     * @param pin a int.
     */
    void removePinListeners(int pin);
    /**
     * <p>removeAllPinListeners.</p>
     */
    void removeAllPinListeners();
    /**
     * <p>addListener.</p>
     *
     * @param listener a {@link com.pi4j.library.pigpio.PiGpioStateChangeListener} object.
     */
    void addListener(PiGpioStateChangeListener listener);
    /**
     * <p>removeListener.</p>
     *
     * @param listener a {@link com.pi4j.library.pigpio.PiGpioStateChangeListener} object.
     */
    void removeListener(PiGpioStateChangeListener listener);
    /**
     * <p>removeAllListeners.</p>
     */
    void removeAllListeners();
}
