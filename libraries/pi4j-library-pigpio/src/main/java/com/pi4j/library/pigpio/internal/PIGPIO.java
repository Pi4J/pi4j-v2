package com.pi4j.library.pigpio.internal;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PIGPIO.java
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


import java.util.concurrent.Callable;

/**
 * <h1>WiringPi Shift Library</h1>
 *
 * <p>
 * WiringPi includes a shift library which more or less mimics the one in the Arduino system. This
 * allows you to shift 8-bit data values out of the Pi, or into the Pi from devices such as
 * shift-registers (e.g. 74,595) and so-on, although it can also be used in some bit-banging
 * scenarios.
 * </p>
 *
 * <p>
 * Before using the Pi4J library, you need to ensure that the Java VM in configured with access to
 * the following system libraries:
 * <ul>
 * <li>pi4j</li>
 * <li>wiringPi</li>
 * </ul>
 * <blockquote> This library depends on the wiringPi native system library. (developed by
 * Gordon Henderson @ <a href="http://wiringpi.com">http://wiringpi.com</a>)
 * </blockquote>
 * </p>
 *
 * @see <a href="https://pi4j.com/">https://pi4j.com/</a>
 * @see <a
 *      href="http://wiringpi.com/reference/shift-library/">http://wiringpi.com/reference/shift-library/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class PIGPIO {

    // private constructor
    private PIGPIO() {
        // forbid object construction
    }

    static {
        System.loadLibrary("pi4j-pigpio");
    }

    public static native int gpioInitialise();
    public static native void gpioTerminate();
    public static native int gpioSetMode(int gpio, int mode);
    public static native int gpioGetMode(int gpio);
    public static native int gpioSetPullUpDown(int gpio, int pud);
    public static native int gpioRead(int gpio);
    public static native int gpioWrite(int gpio, int level);
    public static native int gpioPWM(int user_gpio, int dutycycle);
    public static native int gpioGetPWMdutycycle(int user_gpio);
    public static native int gpioSetPWMrange(int user_gpio, int range);
    public static native int gpioGetPWMrange(int user_gpio);
    public static native int gpioGetPWMrealRange(int user_gpio);
    public static native int gpioSetPWMfrequency(int user_gpio, int frequency);
    public static native int gpioGetPWMfrequency(int user_gpio);
    public static native int gpioServo(int user_gpio, int pulsewidth);
    public static native int gpioGetServoPulsewidth(int user_gpio);
    public static native int gpioSetAlertFunc(int user_gpio, Callable f);
    public static native int gpioSetAlertFuncEx(int user_gpio, Callable f, Object userdata);
    public static native int gpioSetISRFunc(int gpio, int edge, int timeout, Callable f);
    public static native int gpioSetISRFuncEx(int gpio, int edge, int timeout, Callable f, Object userdata);
    public static native int gpioNotifyOpen();
    public static native int gpioNotifyOpenWithSize(int bufSize);
    public static native int gpioNotifyBegin(int handle, long bits);
    public static native int gpioNotifyPause(int handle);
    public static native int gpioNotifyClose(int handle);
//    public static native int gpioWaveClear();
//    public static native int gpioWaveAddNew();
//    public static native int gpioWaveAddGeneric(int numPulses, gpioPulse_t *pulses)
//    public static native int gpioWaveAddSerial(int user_gpio, int baud, int data_bits, int stop_bits, int offset, int numBytes, char *str);
//    public static native int gpioWaveCreate();
//    public static native int gpioWaveDelete(int wave_id);
//    public static native int gpioWaveTxSend(int wave_id, int wave_mode);
//    public static native int gpioWaveChain(byte[] buf, int bufSize);
//    public static native int gpioWaveTxAt();
//    public static native int gpioWaveTxBusy();
//    public static native int gpioWaveTxStop();
//    public static native int gpioWaveGetMicros();
//    public static native int gpioWaveGetHighMicros();
//    public static native int gpioWaveGetMaxMicros();
//    public static native int gpioWaveGetPulses();
//    public static native int gpioWaveGetHighPulses();
//    public static native int gpioWaveGetMaxPulses();
//    public static native int gpioWaveGetCbs();
//    public static native int gpioWaveGetHighCbs();
//    public static native int gpioWaveGetMaxCbs();
    public static native int gpioSerialReadOpen(int user_gpio, int baud, int data_bits);
    public static native int gpioSerialReadInvert(int user_gpio, int invert);
    public static native int gpioSerialRead(int user_gpio, byte[] buffer, int bufSize);
    public static native int gpioSerialReadClose(int user_gpio);
    public static native int i2cOpen(int i2cBus, int i2cAddr, int i2cFlags);
    public static native int i2cClose(int handle);
    public static native int i2cWriteQuick(int handle, boolean bit);
    public static native int i2cWriteByte(int handle, byte bVal);
    public static native int i2cReadByte(int handle);
    public static native int i2cWriteByteData(int handle, int i2cReg, byte bVal);
    public static native int i2cWriteWordData(int handle, int i2cReg, int wVal);
    public static native int i2cReadByteData(int handle, int i2cReg);
    public static native int i2cReadWordData(int handle, int i2cReg);
    public static native int i2cProcessCall(int handle, int i2cReg, byte wVal);
    public static native int i2cWriteBlockData(int handle, int i2cReg, byte[] buf, int count);
    public static native int i2cReadBlockData(int handle, int i2cReg, byte[] buf);
    public static native int i2cBlockProcessCall(int handle, int i2cReg, byte[] buf, int count);
    public static native int i2cReadI2CBlockData(int handle, int i2cReg, byte[] buf, int count);
    public static native int i2cWriteI2CBlockData(int handle, int i2cReg, byte[] buf, int count);
    public static native int i2cReadDevice(int handle, byte[] buf, int count);
    public static native int i2cWriteDevice(int handle, byte[] buf, int count);
    public static native void i2cSwitchCombined(int setting);
//    public static native int i2cSegments(int handle, pi_i2c_msg_t *segs, int numSegs);
    public static native int i2cZip(int handle, byte[] inBuf, int inLen, byte[] outBuf, int outLen);
    public static native int bbI2COpen(int SDA, int SCL, int baud);
    public static native int bbI2CClose(int SDA);
    public static native int bbI2CZip(int SDA, byte[] inBuf, int inLen, byte[] outBuf, int outLen);
//    public static native int bscXfer(bsc_xfer_t *bsc_xfer);
    public static native int bbSPIOpen(int CS, int MISO, int MOSI, int SCLK, int baud, int spiFlags);
    public static native int bbSPIClose(int CS);
    public static native int bbSPIXfer(int CS, byte[] inBuf, byte[] outBuf, int count);
    public static native int spiOpen(int spiChan, int baud, int spiFlags);
    public static native int spiClose(int handle);
    public static native int spiRead(int handle, byte[] buf, int count);
    public static native int spiWrite(int handle, byte[] buf, int count);
    public static native int spiXfer(int handle, byte[] txBuf, byte[] rxBuf, int count);
    public static native int serOpen(String sertty, int baud, int serFlags);
    public static native int serClose(int handle);
    public static native int serWriteByte(int handle, byte bVal);
    public static native int serReadByte(int handle);
    public static native int serWrite(int handle, byte[] buf, int count);
    public static native int serRead(int handle, byte[] buf, int count);
    public static native int serDataAvailable(int handle);
    public static native int gpioTrigger(int user_gpio, int pulseLen, int level);
    public static native int gpioSetWatchdog(int user_gpio, int timeout);
    public static native int gpioNoiseFilter(int user_gpio, int steady, int active);
    public static native int gpioGlitchFilter(int user_gpio, int steady);
//    public static native int gpioSetGetSamplesFunc(gpioGetSamplesFunc_t f, int bits);
//    public static native int gpioSetGetSamplesFuncEx(gpioGetSamplesFuncEx_t f, int bits, void *userdata);
//    public static native int gpioSetTimerFunc(int timer, int millis, gpioTimerFunc_t f);
//    public static native int gpioSetTimerFuncEx(int timer, int millis, gpioTimerFuncEx_t f, void *userdata);
//    public static native pthread_t *gpioStartThread(gpioThreadFunc_t f, void *userdata);
//    public static native void gpioStopThread(pthread_t *pth);
    public static native int gpioStoreScript(String script);
    public static native int gpioRunScript(int script_id, int numPar, int[] param);
    public static native int gpioUpdateScript(int script_id, int numPar, int[] param);
    public static native int gpioScriptStatus(int script_id, int[] param);
    public static native int gpioStopScript(int script_id);
    public static native int gpioDeleteScript(int script_id);
    public static native int gpioSetSignalFunc(int signum, Callable f);
    public static native int gpioSetSignalFuncEx(int signum, Callable f, Object userdata);
    public static native int gpioRead_Bits_0_31();
    public static native int gpioRead_Bits_32_53();
    public static native int gpioWrite_Bits_0_31_Clear(int bits);
    public static native int gpioWrite_Bits_32_53_Clear(int bits);
    public static native int gpioWrite_Bits_0_31_Set(int bits);
    public static native int gpioWrite_Bits_32_53_Set(int bits);
    public static native int gpioHardwareClock(int gpio, long clkfreq);
    public static native int gpioHardwarePWM(int gpio, int PWMfreq, int PWMduty);
    public static native int gpioTime(int timetype, int seconds, int micros);
    public static native int gpioSleep(int timetype, int seconds, int micros);
    public static native long gpioDelay(long micros);
    public static native long gpioTick();
    public static native int gpioHardwareRevision();
    public static native int gpioVersion();
    public static native int gpioSetPad(int pad, int padStrength);
    public static native int eventMonitor(int handle, int bits);
    public static native int eventSetFunc(int event, Callable f);
    public static native int eventSetFuncEx(int event, Callable f, Object userdata);
    public static native int eventTrigger(int event);
    public static native int shell(String scriptName, String scriptString);
    public static native int fileOpen(String file, int mode);
    public static native int fileClose(int handle);
    public static native int fileWrite(int handle, byte[] buf, int count);
    public static native int fileRead(int handle, byte[] buf, int count);
    public static native int fileSeek(int handle, int seekOffset, int seekFrom);
    public static native int fileList(String fpat, String buf, int count);
    public static native int gpioCfgBufferSize(int cfgMillis);
    public static native int gpioCfgClock(int cfgMicros, int cfgPeripheral, int cfgSource);
    public static native int gpioCfgDMAchannel(int DMAchannel);
    public static native int gpioCfgDMAchannels(int primaryChannel, int secondaryChannel);
    public static native int gpioCfgPermissions(long updateMask);
    public static native int gpioCfgSocketPort(int port);
    public static native int gpioCfgInterfaces(int ifFlags);
    public static native int gpioCfgMemAlloc(int memAllocMode);
    public static native int gpioCfgNetAddr(int numSockAddr, int[] sockAddr);
    public static native int gpioCfgInternals(int cfgWhat, int cfgVal);
    public static native long gpioCfgGetInternals();
    public static native int gpioCfgSetInternals(long cfgVal);
    public static native int gpioCustom1(int arg1, int arg2, byte[] argx, int argc);
    public static native int gpioCustom2(int arg1, byte[] argx, int argc, byte[] retBuf, int retMax);
//    public static native int rawWaveAddSPI(rawSPI_t *spi, int offset, int spiSS, byte[] buf, int spiTxBits, int spiBitFirst, int spiBitLast, int spiBits);
//    public static native int rawWaveAddGeneric(int numPulses, rawWave_t *pulses);
//    public static native int rawWaveCB();
//    public static native rawCbs_t *rawWaveCBAdr(int cbNum);
//    public static native long rawWaveGetOOL(int pos);
//    public static native void rawWaveSetOOL(int pos, long lVal);
//    public static native long rawWaveGetOut(int pos);
//    public static native void rawWaveSetOut(int pos, long lVal);
//    public static native long rawWaveGetIn(int pos);
//    public static native void rawWaveSetIn(int pos, long lVal);
//    public static native rawWaveInfo_t rawWaveInfo(int wave_id);
    public static native int getBitInBytes(int bitPos, byte[] buf, int numBits);
    public static native void putBitInBytes(int bitPos, byte[] buf, int bit);
    public static native double time_time();
    public static native void time_sleep(double seconds);
    public static native void rawDumpWave();
    public static native void rawDumpScript(int script_id);
}
