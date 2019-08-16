package com.pi4j.library.pigpio.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioSocketImpl.java
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

import com.pi4j.library.pigpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.pi4j.library.pigpio.PiGpioCmd.*;
import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_HOST;
import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_PORT;

public class PiGpioSocketImpl extends PiGpioSocketBase implements PiGpio {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    public static PiGpio newInstance(String host, String port) throws IOException {
        return new PiGpioSocketImpl(host, Integer.parseInt(port));
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    public static PiGpio newInstance(String host, int port) throws IOException {
        return new PiGpioSocketImpl(host, port);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to a user specified socket hostname/ip address using the default port (8888).
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    public static PiGpio newInstance(String host) throws IOException {
        return new PiGpioSocketImpl(host, DEFAULT_PORT);
    }

    /**
     * Creates a PiGpio instance using TCP Socket communication for remote I/O access.
     * Connects to the local system (127.0.0.1) using the default port (8888).
     *
     * @throws IOException
     */
    public static PiGpio newInstance() throws IOException {
        return new PiGpioSocketImpl(DEFAULT_HOST, DEFAULT_PORT);
    }

    /**
     * DEFAULT PRIVATE CONSTRUCTOR
     *
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    private PiGpioSocketImpl(String host, int port) throws IOException {
        super(host, port);
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
    @Override
    public long gpioInitialise() {
        // TODO :: SETUP NOTIFICATIONS
        return 0;
    }

    /**
     * Terminates the library.
     *
     * Returns nothing.
     * Call before program exit.
     * This function resets the used DMA channels, releases memory, and terminates any running threads.
     */
    @Override
    public void gpioTerminate() throws IOException {
        // TODO :: REMOVE ALL NOTIFICATIONS
        socket.close();
    }

    /**
     * Returns the pigpio library version.
     *
     * @return pigpio version.
     * @throws IOException
     * @throws InterruptedException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioVersion"
     */
    @Override
    public long gpioVersion() throws IOException {
        logger.trace("[PIGPIO] -> GET VERSION");
        PiGpioPacket result = sendCommand(PIGPV);
        long version = result.result();
        logger.trace("[PIGPIO] <- VERSION: {}", version);
        return version;
    }

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
    @Override
    public long gpioHardwareRevision() throws IOException {
        logger.trace("[HARDWARE] -> GET REVISION");
        PiGpioPacket result = sendCommand(HWVER);
        long revision = result.result();
        logger.trace("[HARDWARE] <- REVISION: {}", revision);
        if(revision <= 0) throw new IOException("Hardware revision could not be determined.");
        return revision;
    }

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
    @Override
    public String gpioHardwareRevisionString() throws IOException {
        logger.trace("[HARDWARE] -> GET REVISION (STRING)");
        PiGpioPacket result = sendCommand(HWVER);
        long revision = result.result();
        String revisionString = Integer.toHexString((int)revision);
        logger.trace("[HARDWARE] <- REVISION (STRING): {}", revisionString);
        return revisionString;
    }

    /**
     * Sets or clears resistor pull ups or downs on the GPIO.
     *
     * @param pin gpio pin address
     * @param pud pull-up, pull-down, pull-off
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetPullUpDown"
     */
    @Override
    public void gpioSetPullUpDown(int pin, PiGpioPud pud) throws IOException {
        logger.trace("[GPIO::PUD-SET] -> PIN: {}; PUD={}({});", pin, pud.name(), pud.value());
        validatePin(pin);
        PiGpioPacket result = sendCommand(MODES, pin, pud.value());
        logger.trace("[GPIO::PUD-SET] <- PIN: {}; PUD={}({}); SUCCESS={}", pud.name(), pud.value(), result.success());
        validateResult(result); // Returns 0 if OK, otherwise PI_BAD_GPIO or PI_BAD_MODE.
    }

    /**
     * Gets the GPIO mode.
     *
     * @param pin
     * @return pin mode
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetMode"
     */
    @Override
    public PiGpioMode gpioGetMode(int pin) throws IOException {
        logger.trace("[GPIO::MODE-GET] -> PIN: {};", pin);
        validatePin(pin);
        PiGpioPacket result = sendCommand(MODEG, pin);
        validateResult(result); // Returns the GPIO mode if OK, otherwise PI_BAD_GPIO.
        PiGpioMode mode = PiGpioMode.from(result.result());
        logger.trace("[GPIO::MODE-GET] <- PIN: {}; MODE={}({})", pin, mode.name(), mode.value());
        return mode;
    }

    /**
     * Sets the GPIO mode, typically input or output.
     *
     * gpio: 0-53
     * mode: 0-7
     *
     * @param pin
     * @param mode
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetMode"
     */
    @Override
    public void gpioSetMode(int pin, PiGpioMode mode) throws IOException {
        logger.trace("[GPIO::MODE-SET] -> PIN: {}; MODE={}({});", pin, mode.name(), mode.value());
        validatePin(pin);
        PiGpioPacket result = sendCommand(MODES, pin, mode.value());
        logger.trace("[GPIO::MODE-SET] <- PIN: {}; MODE={}({}); SUCCESS={}", mode.name(), mode.value(), result.success());
        validateResult(result); // Returns 0 if OK, otherwise PI_BAD_GPIO or PI_BAD_PUD.
    }

    /**
     * Reads the GPIO level, on (HIGH) or off (LOW).
     * @param pin gpio pin address
     * @return
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioRead"
     */
    @Override
    public PiGpioState gpioRead(int pin) throws IOException {
        logger.trace("[GPIO::GET] -> PIN: {}", pin);
        validatePin(pin);
        PiGpioPacket result = sendCommand(READ, pin);
        validateResult(result); // Returns the GPIO level if OK, otherwise PI_BAD_GPIO.
        PiGpioState state = PiGpioState.from(result.p3()); // result value stored in P3
        logger.trace("[GPIO::GET] <- PIN: {} is {}({})", pin, state.name(), state.value());
        return state;
    }

    /**
     * Sets the GPIO level, on (HIGH) or off (LOW).
     *
     * @param pin gpio pin address
     * @param state HIGH or LOW
     * @throws IOException
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioWrite"
     */
    @Override
    public void gpioWrite(int pin, PiGpioState state) throws IOException {
        logger.trace("[GPIO::SET] -> PIN: {}; {}({});", pin, state.name(), state.value());
        validatePin(pin);
        PiGpioPacket result = sendCommand(WRITE, pin, state.value());
        logger.trace("[GPIO::SET] <- PIN: {}; {}({}); SUCCESS={}",  pin, state.name(), state.value(), result.success());
        validateResult(result);  // Returns 0 if OK, otherwise PI_BAD_GPIO or PI_BAD_LEVEL.
    }

    /**
     * Starts PWM on the GPIO, dutycycle between 0 (off) and range (fully on). Range defaults to 255.
     *
     * This and the servo functionality use the DMA and PWM or PCM peripherals to control and schedule
     * the pulse lengths and duty cycles.
     *
     * @param pin user_gpio: 0-31
     * @param dutyCycle dutycycle: 0-range
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioPWM"
     */
    @Override
    public void gpioPWM(int pin, int dutyCycle) throws IOException {
        logger.trace("[PWM::SET] -> PIN: {}; DUTY-CYCLE={};", pin, dutyCycle);
        validateUserPin(pin);
        validateDutyCycle(dutyCycle);
        PiGpioPacket result = sendCommand(PWM, pin, dutyCycle);
        logger.trace("[PWM::SET] <- PIN: {}; DUTY-CYCLE={}; SUCCESS={}",  pin, dutyCycle, result.success());
        validateResult(result);  // Returns 0 if OK, otherwise PI_BAD_GPIO or PI_BAD_LEVEL.
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
    @Override
    public int gpioGetPWMdutycycle(int pin) throws IOException {
        logger.trace("[PWM::GET] -> PIN: {}", pin);
        validateUserPin(pin);
        PiGpioPacket result = sendCommand(GDC, pin);
        var dutyCycle = result.result();
        logger.trace("[PWM::GET] <- PIN: {}; DUTY-CYCLE={}; SUCCESS={}",  pin, dutyCycle, result.success());
        validateResult(result);  // Returns 0 if OK, otherwise PI_BAD_USER_GPIO or PI_NOT_PWM_GPIO.
        return dutyCycle;
    }

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
     * @return Returns the real range for the given GPIO's frequency if OK.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetPWMrange"
     */
    @Override
    public int gpioSetPWMrange(int pin, int range) throws IOException {
        logger.trace("[PWM-RANGE::SET] -> PIN: {}; RANGE={}", pin, range);
        validateUserPin(pin);
        validateDutyCycleRange(range);
        PiGpioPacket result = sendCommand(PRS, pin, range);
        var actualRange = result.result();
        logger.trace("[PWM-RANGE::SET] <- PIN: {}; RANGE={}; SUCCESS={}",  pin, actualRange, result.success());
        validateResult(result);  // Returns 0 if OK, otherwise PI_BAD_USER_GPIO or PI_BAD_DUTYRANGE.
        return actualRange;
    }

    /**
     * Returns the duty-cycle range used for the GPIO if OK.
     * If a hardware clock or hardware PWM is active on the GPIO the reported range will be 1000000 (1M).
     *
     * @param pin user_gpio: 0-31
     * @return duty-cycle range
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetPWMrange"
     */
    @Override
    public int gpioGetPWMrange(int pin) throws IOException {
        logger.trace("[PWM-RANGE::GET] -> PIN: {}", pin);
        validateUserPin(pin);
        PiGpioPacket result = sendCommand(PRG, pin);
        var range = result.result();
        logger.trace("[PWM-RANGE::GET] <- PIN: {}; RANGE={}; SUCCESS={}",  pin, range, result.success());
        validateResult(result);  // Returns 0 if OK, otherwise PI_BAD_USER_GPIO or PI_BAD_DUTYRANGE.
        return range;
    }

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
    @Override
    public int gpioGetPWMrealRange(int pin) throws IOException {
        logger.trace("[PWM-REAL-RANGE::GET] -> PIN: {}", pin);
        validateUserPin(pin);
        PiGpioPacket result = sendCommand(PRRG, pin);
        var range = result.result();
        logger.trace("[PWM-REAL-RANGE::GET] <- PIN: {}; RANGE={}; SUCCESS={}",  pin, range, result.success());
        validateResult(result);  // Returns 0 if OK, otherwise PI_BAD_USER_GPIO or PI_BAD_DUTYRANGE.
        return range;
    }

    /**
     * Sets the frequency in hertz to be used for the GPIO.
     *
     * If PWM is currently active on the GPIO it will be switched off and then back on at the new frequency.
     * Each GPIO can be independently set to one of 18 different PWM frequencies.
     * The selectable frequencies depend upon the sample rate which may be 1, 2, 4, 5, 8, or 10 microseconds (default 5).
     *
     * The frequencies for each sample rate are:
     *
     *                        Hertz
     *
     *        1: 40000 20000 10000 8000 5000 4000 2500 2000 1600
     *            1250  1000   800  500  400  250  200  100   50
     *
     *        2: 20000 10000  5000 4000 2500 2000 1250 1000  800
     *             625   500   400  250  200  125  100   50   25
     *
     *        4: 10000  5000  2500 2000 1250 1000  625  500  400
     *             313   250   200  125  100   63   50   25   13
     * sample
     *  rate
     *  (us)  5:  8000  4000  2000 1600 1000  800  500  400  320
     *             250   200   160  100   80   50   40   20   10
     *
     *        8:  5000  2500  1250 1000  625  500  313  250  200
     *             156   125   100   63   50   31   25   13    6
     *
     *       10:  4000  2000  1000  800  500  400  250  200  160
     *             125   100    80   50   40   25   20   10    5
     *
     *
     * Example:
     *    gpioSetPWMfrequency(23, 0); // Set GPIO23 to lowest frequency.
     *    gpioSetPWMfrequency(24, 500); // Set GPIO24 to 500Hz.
     *    gpioSetPWMfrequency(25, 100000); // Set GPIO25 to highest frequency.
     *
     * @param pin user_gpio: 0-31
     * @param frequency frequency: >=0
     * @return Returns the numerically closest frequency if OK
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioSetPWMrange"
     */
    @Override
    public int gpioSetPWMfrequency(int pin, int frequency) throws IOException {
        logger.trace("[PWM-FREQ::SET] -> PIN: {}; FREQUENCY={}", pin, frequency);
        validateUserPin(pin);
        // validateFrequency(frequency); TODO :: IMPLEMENT 'validateFrequency()'
        PiGpioPacket result = sendCommand(PRS, pin, frequency);
        var actualRange = result.result();
        logger.trace("[PWM-FREQ::SET] <- PIN: {}; FREQUENCY={}; SUCCESS={}",  pin, frequency, result.success());
        validateResult(result);  // Returns the numerically closest frequency if OK, otherwise PI_BAD_USER_GPIO.
        return actualRange;
    }

    /**
     * Returns the frequency (in hertz) used for the GPIO
     *
     * For normal PWM the frequency will be that defined for the GPIO by gpioSetPWMfrequency.
     * If a hardware clock is active on the GPIO the reported frequency will be that set by gpioHardwareClock.
     * If hardware PWM is active on the GPIO the reported frequency will be that set by gpioHardwarePWM.
     *
     * Example:
     *    f = gpioGetPWMfrequency(23); // Get frequency used for GPIO23.
     *
     * @param pin user_gpio: 0-31
     * @return Returns the frequency (in hertz) used for the GPIO if OK.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioGetPWMfrequency"
     */
    @Override
    public int gpioGetPWMfrequency(int pin) throws IOException {
        logger.trace("[PWM-FREQ::GET] -> PIN: {}", pin);
        validateUserPin(pin);
        PiGpioPacket result = sendCommand(PRRG, pin);
        var frequency = result.result();
        logger.trace("[PWM-FREQ::GET] <- PIN: {}; FREQUENCY={}; SUCCESS={}",  pin, frequency, result.success());
        validateResult(result);  // Returns the frequency (in hertz) used for the GPIO if OK, otherwise PI_BAD_USER_GPIO.
        return frequency;
    }

    /**
     * Delays for at least the number of microseconds specified by micros.
     * (Delays of 100 microseconds or less use busy waits.)
     *
     * @param micros micros: the number of microseconds to sleep
     * @return Returns the actual length of the delay in microseconds.
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioDelay"
     */
    @Override
    public int gpioDelay(int micros) throws IOException {
        logger.trace("[DELAY] -> MICROS: {}", micros);
        validateDelayMicroseconds(micros);
        PiGpioPacket result = sendCommand(MICS, (int)micros);
        logger.trace("[DELAY] <- MICROS: {}; SUCCESS={}",  micros, result.success());
        validateResult(result); // Upon success nothing is returned. On error a negative status code will be returned.
        return micros;
    }

    /**
     * Delays for at least the number of milliseconds specified by micros. (between 1 and 60000 <1 minute>)
     *
     * @param millis millis: the number of milliseconds to sleep (between 1 and 60000 <1 minute>)
     * @return Returns the actual length of the delay in milliseconds.
     * @see "http://abyz.me.uk/rpi/pigpio/pigs.html#MILS"
     */
    @Override
    public int gpioDelayMilliseconds(int millis) throws IOException{
        logger.trace("[DELAY] -> MILLIS: {}", millis);
        validateDelayMilliseconds(millis);
        PiGpioPacket result = sendCommand(MILS, (int)millis);
        logger.trace("[DELAY] <- MILLIS: {}; SUCCESS={}",  millis, result.success());
        validateResult(result); // Upon success nothing is returned. On error a negative status code will be returned.
        return millis;
    }

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
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioTick"
     */
    @Override
    public long gpioTick() throws IOException {
        logger.trace("[TICK::GET] -> Get current tick");
        PiGpioPacket tx = new PiGpioPacket(TICK);
        PiGpioPacket rx = sendPacket(tx);
        long tick = Integer.toUnsignedLong(rx.result()); // convert (UInt32) 32-bit unsigned value to long
        logger.trace("[TICK::GET] <- TICK: {}; SUCCESS={}",  tick, rx.success());
        return tick;
    }

    @Override
    public int i2cOpen(int bus, int device) throws IOException {
        logger.trace("[I2C::OPEN] -> Open I2C Bus [{}] and Device [{}]", bus, device);
        validateI2cBus(bus);
        validateI2cDeviceAddress(device);
        PiGpioPacket tx = new PiGpioPacket(I2CO, bus, device);
        PiGpioPacket rx = sendPacket(tx);
        int handle = rx.result();
        logger.trace("[I2C::OPEN] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return handle;
    }

    @Override
    public void i2cClose(int handle) throws IOException {
        logger.trace("[I2C::CLOSE] -> HANDLE={}, Close I2C Bus", handle);
        validateI2cHandle(handle);
        PiGpioPacket tx = new PiGpioPacket(I2CC, handle);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::CLOSE] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public void i2cWriteQuick(int handle, boolean bit) throws IOException {
        logger.trace("[I2C::WRITE] -> HANDLE={}; R/W Bit [{}]", handle, bit ? 1 : 0);
        validateI2cHandle(handle);
        PiGpioPacket tx = new PiGpioPacket(I2CWQ, handle, bit ? 1 : 0);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public void i2cWriteByte(int handle, byte value) throws IOException {
        logger.trace("[I2C::WRITE] -> HANDLE={}; Byte [{}]", handle, Byte.toUnsignedInt(value));
        validateI2cHandle(handle);
        PiGpioPacket tx = new PiGpioPacket(I2CWS, handle, Byte.toUnsignedInt(value));
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public byte i2cReadByte(int handle) throws IOException {
        logger.trace("[I2C::READ] -> [{}]; Byte", handle);
        validateI2cHandle(handle);
        PiGpioPacket tx = new PiGpioPacket(I2CRS, handle);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::READ] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return (byte)rx.result();
    }

    @Override
    public void i2cWriteByteData(int handle, int register, byte value) throws IOException {
        logger.trace("[I2C::WRITE] -> [{}]; Register [{}]; Byte [{}]", handle ,register, value);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CWB, handle, register).data(value);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public void i2cWriteWordData(int handle, int register, int value) throws IOException {
        logger.trace("[I2C::WRITE] -> [{}]; Register [{}]; Word [{}]", handle ,register, value);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CWW, handle, register).data(value);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public byte i2cReadByteData(int handle, int register) throws IOException {
        logger.trace("[I2C::READ] -> [{}]; Register [{}]; Byte", handle ,register);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CRB, handle, register);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::READ] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return (byte)rx.result();
    }

    @Override
    public int i2cReadWordData(int handle, int register) throws IOException {
        logger.trace("[I2C::READ] -> [{}]; Register [{}]; Word", handle ,register);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CRW, handle, register);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::READ] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return rx.result();
    }

    @Override
    public int i2cProcessCall(int handle, int register, int value) throws IOException {
        logger.trace("[I2C::W/R] -> [{}]; Register [{}]; Word [{}]", handle ,register, value);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CPC, handle, register).data(value);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::W/R] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return rx.result();
    }

    @Override
    public void i2cWriteBlockData(int handle, int register, byte[] data) throws IOException {
        logger.trace("[I2C::WRITE] -> [{}]; Register [{}]; Block [{} bytes]", handle ,register, data.length);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        validateI2cDataLength(data.length);
        PiGpioPacket tx = new PiGpioPacket(I2CWK, handle, register, data);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public byte[] i2cReadBlockData(int handle, int register) throws IOException {
        logger.trace("[I2C::READ] -> [{}]; Register [{}]; Block", handle ,register);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CRK, handle, register);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::READ] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return rx.data();
    }
    @Override
    public byte[] i2cBlockProcessCall(int handle, int register, byte[] data) throws IOException {
        logger.trace("[I2C::W/R] -> [{}]; Register [{}]; Block [{} bytes]", handle ,register, data.length);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        validateI2cDataLength(data.length);
        PiGpioPacket tx = new PiGpioPacket(I2CPK, handle, register, data);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::W/R] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return rx.data();
    }

    @Override
    public byte[] i2cReadI2CBlockData(int handle, int register, int length) throws IOException {
        logger.trace("[I2C::READ] -> [{}]; Register [{}]; I2C Block [{} bytes]", handle ,register, length);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        PiGpioPacket tx = new PiGpioPacket(I2CRI, handle, register).data(length);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::READ] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return rx.data();
    }

    @Override
    public void i2cWriteI2CBlockData(int handle, int register, byte[] data) throws IOException {
        logger.trace("[I2C::WRITE] -> [{}]; Register [{}]; I2C Block [{} bytes]", handle ,register, data.length);
        validateI2cHandle(handle);
        validateI2cRegister(register);
        validateI2cDataLength(data.length);
        PiGpioPacket tx = new PiGpioPacket(I2CWI, handle, register, data);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
    }

    @Override
    public byte[] i2cReadDevice(int handle, int length) throws IOException {
        logger.trace("[I2C::READ] -> [{}]; I2C Raw Read [{} bytes]", handle, length);
        validateI2cHandle(handle);
        PiGpioPacket tx = new PiGpioPacket(I2CRD, handle, length);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::READ] <- HANDLE={}; SUCCESS={}",  handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return rx.data();
    }

    @Override
    public int i2cWriteDevice(int handle, byte[] data) throws IOException {
        logger.trace("[I2C::WRITE] -> [{}]; I2C Raw Write [{} bytes]", handle, data.length);
        validateI2cHandle(handle);
        validateI2cDataLength(data.length);
        PiGpioPacket tx = new PiGpioPacket(I2CWD, handle, 0, data);
        PiGpioPacket rx = sendPacket(tx);
        logger.trace("[I2C::WRITE] <- HANDLE={}; SUCCESS={}", handle, rx.success());
        validateResult(rx); // Upon success nothing is returned. On error a negative status code will be returned.
        return -1;
    }
}
