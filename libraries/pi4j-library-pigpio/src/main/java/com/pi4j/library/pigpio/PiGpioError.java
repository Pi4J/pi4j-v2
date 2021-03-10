package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  PiGpioError.java
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

/**
 * <p>PiGpioError class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public enum PiGpioError {
    UNKNOWN(0),
    PI_INIT_FAILED       (-1),  // gpioInitialise failed
    PI_BAD_USER_GPIO     (-2),  // GPIO not 0-31
    PI_BAD_GPIO          (-3),  // GPIO not 0-53
    PI_BAD_MODE          (-4),  // mode not 0-7
    PI_BAD_LEVEL         (-5),  // level not 0-1
    PI_BAD_PUD           (-6),  // pud not 0-2
    PI_BAD_PULSEWIDTH    (-7),  // pulsewidth not 0 or 500-2500
    PI_BAD_DUTYCYCLE     (-8),  // dutycycle outside set range
    PI_BAD_TIMER         (-9),  // timer not 0-9
    PI_BAD_MS           (-10),  // ms not 10-60000
    PI_BAD_TIMETYPE     (-11),  // timetype not 0-1
    PI_BAD_SECONDS      (-12),  // seconds < 0
    PI_BAD_MICROS       (-13),  // micros not 0-999999
    PI_TIMER_FAILED     (-14),  // gpioSetTimerFunc failed
    PI_BAD_WDOG_TIMEOUT (-15),  // timeout not 0-60000
    PI_NO_ALERT_FUNC    (-16),  // DEPRECATED
    PI_BAD_CLK_PERIPH   (-17),  // clock peripheral not 0-1
    PI_BAD_CLK_SOURCE   (-18),  // DEPRECATED
    PI_BAD_CLK_MICROS   (-19),  // clock micros not 1, 2, 4, 5, 8, or 10
    PI_BAD_BUF_MILLIS   (-20),  // buf millis not 100-10000
    PI_BAD_DUTYRANGE    (-21),  // dutycycle range not 25-40000
    PI_BAD_DUTY_RANGE   (-21),  // DEPRECATED (use PI_BAD_DUTYRANGE)
    PI_BAD_SIGNUM       (-22),  // signum not 0-63
    PI_BAD_PATHNAME     (-23),  // can't open pathname
    PI_NO_HANDLE        (-24),  // no handle available
    PI_BAD_HANDLE       (-25),  // unknown handle
    PI_BAD_IF_FLAGS     (-26),  // ifFlags > 4
    PI_BAD_CHANNEL      (-27),  // DMA channel not 0-15
    PI_BAD_PRIM_CHANNEL (-27),  // DMA primary channel not 0-15
    PI_BAD_SOCKET_PORT  (-28),  // socket port not 1024-32000
    PI_BAD_FIFO_COMMAND (-29),  // unrecognized fifo command
    PI_BAD_SECO_CHANNEL (-30),  // DMA secondary channel not 0-15
    PI_NOT_INITIALISED  (-31),  // function called before gpioInitialise
    PI_INITIALISED      (-32),  // function called after gpioInitialise
    PI_BAD_WAVE_MODE    (-33),  // waveform mode not 0-3
    PI_BAD_CFG_INTERNAL (-34),  // bad parameter in gpioCfgInternals call
    PI_BAD_WAVE_BAUD    (-35),  // baud rate not 50-250K(RX)/50-1M(TX)
    PI_TOO_MANY_PULSES  (-36),  // waveform has too many pulses
    PI_TOO_MANY_CHARS   (-37),  // waveform has too many chars
    PI_NOT_SERIAL_GPIO  (-38),  // no bit bang serial read on GPIO
    PI_BAD_SERIAL_STRUC (-39),  // bad (null) serial structure parameter
    PI_BAD_SERIAL_BUF   (-40),  // bad (null) serial buf parameter
    PI_NOT_PERMITTED    (-41),  // GPIO operation not permitted
    PI_SOME_PERMITTED   (-42),  // one or more GPIO not permitted
    PI_BAD_WVSC_COMMND  (-43),  // bad WVSC subcommand
    PI_BAD_WVSM_COMMND  (-44),  // bad WVSM subcommand
    PI_BAD_WVSP_COMMND  (-45),  // bad WVSP subcommand
    PI_BAD_PULSELEN     (-46),  // trigger pulse length not 1-100
    PI_BAD_SCRIPT       (-47),  // invalid script
    PI_BAD_SCRIPT_ID    (-48),  // unknown script id
    PI_BAD_SER_OFFSET   (-49),  // add serial data offset > 30 minutes
    PI_GPIO_IN_USE      (-50),  // GPIO already in use
    PI_BAD_SERIAL_COUNT (-51),  // must read at least a byte at a time
    PI_BAD_PARAM_NUM    (-52),  // script parameter id not 0-9
    PI_DUP_TAG          (-53),  // script has duplicate tag
    PI_TOO_MANY_TAGS    (-54),  // script has too many tags
    PI_BAD_SCRIPT_CMD   (-55),  // illegal script command
    PI_BAD_VAR_NUM      (-56),  // script variable id not 0-149
    PI_NO_SCRIPT_ROOM   (-57),  // no more room for scripts
    PI_NO_MEMORY        (-58),  // can't allocate temporary memory
    PI_SOCK_READ_FAILED (-59),  // socket read failed
    PI_SOCK_WRIT_FAILED (-60),  // socket write failed
    PI_TOO_MANY_PARAM   (-61),  // too many script parameters (> 10)
    PI_NOT_HALTED       (-62),  // DEPRECATED
    PI_SCRIPT_NOT_READY (-62),  // script initialising
    PI_BAD_TAG          (-63),  // script has unresolved tag
    PI_BAD_MICS_DELAY   (-64),  // bad MICS delay (too large)
    PI_BAD_MILS_DELAY   (-65),  // bad MILS delay (too large)
    PI_BAD_WAVE_ID      (-66),  // non existent wave id
    PI_TOO_MANY_CBS     (-67),  // No more CBs for waveform
    PI_TOO_MANY_OOL     (-68),  // No more OOL for waveform
    PI_EMPTY_WAVEFORM   (-69),  // attempt to create an empty waveform
    PI_NO_WAVEFORM_ID   (-70),  // no more waveforms
    PI_I2C_OPEN_FAILED  (-71),  // can't open I2C device
    PI_SER_OPEN_FAILED  (-72),  // can't open serial device
    PI_SPI_OPEN_FAILED  (-73),  // can't open SPI device
    PI_BAD_I2C_BUS      (-74),  // bad I2C bus
    PI_BAD_I2C_ADDR     (-75),  // bad I2C address
    PI_BAD_SPI_CHANNEL  (-76),  // bad SPI channel
    PI_BAD_FLAGS        (-77),  // bad i2c/spi/ser open flags
    PI_BAD_SPI_SPEED    (-78),  // bad SPI speed
    PI_BAD_SER_DEVICE   (-79),  // bad serial device name
    PI_BAD_SER_SPEED    (-80),  // bad serial baud rate
    PI_BAD_PARAM        (-81),  // bad i2c/spi/ser parameter
    PI_I2C_WRITE_FAILED (-82),  // i2c write failed
    PI_I2C_READ_FAILED  (-83),  // i2c read failed
    PI_BAD_SPI_COUNT    (-84),  // bad SPI count
    PI_SER_WRITE_FAILED (-85),  // ser write failed
    PI_SER_READ_FAILED  (-86),  // ser read failed
    PI_SER_READ_NO_DATA (-87),  // ser read no data available
    PI_UNKNOWN_COMMAND  (-88),  // unknown command
    PI_SPI_XFER_FAILED  (-89),  // spi xfer/read/write failed
    PI_BAD_POINTER      (-90),  // bad (NULL) pointer
    PI_NO_AUX_SPI       (-91),  // no auxiliary SPI on Pi A or B
    PI_NOT_PWM_GPIO     (-92),  // GPIO is not in use for PWM
    PI_NOT_SERVO_GPIO   (-93),  // GPIO is not in use for servo pulses
    PI_NOT_HCLK_GPIO    (-94),  // GPIO has no hardware clock
    PI_NOT_HPWM_GPIO    (-95),  // GPIO has no hardware PWM
    PI_BAD_HPWM_FREQ    (-96),  // invalid hardware PWM frequency
    PI_BAD_HPWM_DUTY    (-97),  // hardware PWM dutycycle not 0-1M
    PI_BAD_HCLK_FREQ    (-98),  // invalid hardware clock frequency
    PI_BAD_HCLK_PASS    (-99),  // need password to use hardware clock 1
    PI_HPWM_ILLEGAL    (-100),  // illegal, PWM in use for main clock
    PI_BAD_DATABITS    (-101),  // serial data bits not 1-32
    PI_BAD_STOPBITS    (-102),  // serial (half) stop bits not 2-8
    PI_MSG_TOOBIG      (-103),  // socket/pipe message too big
    PI_BAD_MALLOC_MODE (-104),  // bad memory allocation mode
    PI_TOO_MANY_SEGS   (-105),  // too many I2C transaction segments
    PI_BAD_I2C_SEG     (-106),  // an I2C transaction segment failed
    PI_BAD_SMBUS_CMD   (-107),  // SMBus command not supported by driver
    PI_NOT_I2C_GPIO    (-108),  // no bit bang I2C in progress on GPIO
    PI_BAD_I2C_WLEN    (-109),  // bad I2C write length
    PI_BAD_I2C_RLEN    (-110),  // bad I2C read length
    PI_BAD_I2C_CMD     (-111),  // bad I2C command
    PI_BAD_I2C_BAUD    (-112),  // bad I2C baud rate, not 50-500k
    PI_CHAIN_LOOP_CNT  (-113),  // bad chain loop count
    PI_BAD_CHAIN_LOOP  (-114),  // empty chain loop
    PI_CHAIN_COUNTER   (-115),  // too many chain counters
    PI_BAD_CHAIN_CMD   (-116),  // bad chain command
    PI_BAD_CHAIN_DELAY (-117),  // bad chain delay micros
    PI_CHAIN_NESTING   (-118),  // chain counters nested too deeply
    PI_CHAIN_TOO_BIG   (-119),  // chain is too long
    PI_DEPRECATED      (-120),  // deprecated function removed
    PI_BAD_SER_INVERT  (-121),  // bit bang serial invert not 0 or 1
    PI_BAD_EDGE        (-122),  // bad ISR edge value, not 0-2
    PI_BAD_ISR_INIT    (-123),  // bad ISR initialisation
    PI_BAD_FOREVER     (-124),  // loop forever must be last command
    PI_BAD_FILTER      (-125),  // bad filter parameter
    PI_BAD_PAD         (-126),  // bad pad number
    PI_BAD_STRENGTH    (-127),  // bad pad drive strength
    PI_FIL_OPEN_FAILED (-128),  // file open failed
    PI_BAD_FILE_MODE   (-129),  // bad file mode
    PI_BAD_FILE_FLAG   (-130),  // bad file flag
    PI_BAD_FILE_READ   (-131),  // bad file read
    PI_BAD_FILE_WRITE  (-132),  // bad file write
    PI_FILE_NOT_ROPEN  (-133),  // file not open for read
    PI_FILE_NOT_WOPEN  (-134),  // file not open for write
    PI_BAD_FILE_SEEK   (-135),  // bad file seek
    PI_NO_FILE_MATCH   (-136),  // no files match pattern
    PI_NO_FILE_ACCESS  (-137),  // no permission to access file
    PI_FILE_IS_A_DIR   (-138),  // file is a directory
    PI_BAD_SHELL_STATUS (-139),  // bad shell return status
    PI_BAD_SCRIPT_NAME (-140),  // bad script name
    PI_BAD_SPI_BAUD    (-141),  // bad SPI baud rate, not 50-500k
    PI_NOT_SPI_GPIO    (-142),  // no bit bang SPI in progress on GPIO
    PI_BAD_EVENT_ID    (-143),  // bad event id
    PI_CMD_INTERRUPTED (-144),  // Used by Python
    PI_NOT_ON_BCM2711  (-145),  // not available on BCM2711
    PI_ONLY_ON_BCM2711 (-146),  // only available on BCM2711

    PI_PIGIF_ERR_0    (-2000),
    PI_PIGIF_ERR_99   (-2099),

    PI_CUSTOM_ERR_0   (-3000),
    PI_CUSTOM_ERR_999 (-3999);

    private int value;

    PiGpioError(int value){
        this.value  =value;
    }

    /**
     * <p>value.</p>
     *
     * @return a int.
     */
    public int value(){
        return this.value;
    }

    /**
     * <p>message.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String message(){
        switch (this){
            case UNKNOWN             : return "Unknown";
            case PI_INIT_FAILED      : return "pigpio initialisation failed";
            case PI_BAD_USER_GPIO    : return "GPIO not 0-31";
            case PI_BAD_GPIO         : return "GPIO not 0-53";
            case PI_BAD_MODE         : return "mode not 0-7";
            case PI_BAD_LEVEL        : return "level not 0-1";
            case PI_BAD_PUD          : return "pud not 0-2";
            case PI_BAD_PULSEWIDTH   : return "pulsewidth not 0 or 500-2500";
            case PI_BAD_DUTYCYCLE    : return "dutycycle not 0-range (default 255)";
            case PI_BAD_TIMER        : return "timer not 0-9";
            case PI_BAD_MS           : return "ms not 10-60000";
            case PI_BAD_TIMETYPE     : return "timetype not 0-1";
            case PI_BAD_SECONDS      : return "seconds < 0";
            case PI_BAD_MICROS       : return "micros not 0-999999";
            case PI_TIMER_FAILED     : return "gpioSetTimerFunc failed";
            case PI_BAD_WDOG_TIMEOUT : return "timeout not 0-60000";
            case PI_NO_ALERT_FUNC    : return "DEPRECATED";
            case PI_BAD_CLK_PERIPH   : return "clock peripheral not 0-1";
            case PI_BAD_CLK_SOURCE   : return "DEPRECATED";
            case PI_BAD_CLK_MICROS   : return "clock micros not 1: return 2: return 4: return 5: return 8: return or 10";
            case PI_BAD_BUF_MILLIS   : return "buf millis not 100-10000";
            case PI_BAD_DUTYRANGE    : return "dutycycle range not 25-40000";
            case PI_BAD_SIGNUM       : return "signum not 0-63";
            case PI_BAD_PATHNAME     : return "can't open pathname";
            case PI_NO_HANDLE        : return "no handle available";
            case PI_BAD_HANDLE       : return "unknown handle";
            case PI_BAD_IF_FLAGS     : return "ifFlags > 4";
            case PI_BAD_CHANNEL      : return "DMA channel not 0-14";
            case PI_BAD_SOCKET_PORT  : return "socket port not 1024-30000";
            case PI_BAD_FIFO_COMMAND : return "unknown fifo command";
            case PI_BAD_SECO_CHANNEL : return "DMA secondary channel not 0-14";
            case PI_NOT_INITIALISED  : return "function called before gpioInitialise";
            case PI_INITIALISED      : return "function called after gpioInitialise";
            case PI_BAD_WAVE_MODE    : return "waveform mode not 0-1";
            case PI_BAD_CFG_INTERNAL : return "bad parameter in gpioCfgInternals call";
            case PI_BAD_WAVE_BAUD    : return "baud rate not 50-250K(RX)/50-1M(TX)";
            case PI_TOO_MANY_PULSES  : return "waveform has too many pulses";
            case PI_TOO_MANY_CHARS   : return "waveform has too many chars";
            case PI_NOT_SERIAL_GPIO  : return "no bit bang serial read in progress on GPIO";
            case PI_BAD_SERIAL_STRUC : return "bad (null) serial structure parameter";
            case PI_BAD_SERIAL_BUF   : return "bad (null) serial buf parameter";
            case PI_NOT_PERMITTED    : return "no permission to update GPIO";
            case PI_SOME_PERMITTED   : return "no permission to update one or more GPIO";
            case PI_BAD_WVSC_COMMND  : return "bad WVSC subcommand";
            case PI_BAD_WVSM_COMMND  : return "bad WVSM subcommand";
            case PI_BAD_WVSP_COMMND  : return "bad WVSP subcommand";
            case PI_BAD_PULSELEN     : return "trigger pulse length not 1-100";
            case PI_BAD_SCRIPT       : return "invalid script";
            case PI_BAD_SCRIPT_ID    : return "unknown script id";
            case PI_BAD_SER_OFFSET   : return "add serial data offset > 30 minute";
            case PI_GPIO_IN_USE      : return "GPIO already in use";
            case PI_BAD_SERIAL_COUNT : return "must read at least a byte at a time";
            case PI_BAD_PARAM_NUM    : return "script parameter id not 0-9";
            case PI_DUP_TAG          : return "script has duplicate tag";
            case PI_TOO_MANY_TAGS    : return "script has too many tags";
            case PI_BAD_SCRIPT_CMD   : return "illegal script command";
            case PI_BAD_VAR_NUM      : return "script variable id not 0-149";
            case PI_NO_SCRIPT_ROOM   : return "no more room for scripts";
            case PI_NO_MEMORY        : return "can't allocate temporary memory";
            case PI_SOCK_READ_FAILED : return "socket read failed";
            case PI_SOCK_WRIT_FAILED : return "socket write failed";
            case PI_TOO_MANY_PARAM   : return "too many script parameters (> 10)";
            case PI_SCRIPT_NOT_READY : return "script initialising";
            case PI_BAD_TAG          : return "script has unresolved tag";
            case PI_BAD_MICS_DELAY   : return "bad MICS delay (too large)";
            case PI_BAD_MILS_DELAY   : return "bad MILS delay (too large)";
            case PI_BAD_WAVE_ID      : return "non existent wave id";
            case PI_TOO_MANY_CBS     : return "No more CBs for waveform";
            case PI_TOO_MANY_OOL     : return "No more OOL for waveform";
            case PI_EMPTY_WAVEFORM   : return "attempt to create an empty waveform";
            case PI_NO_WAVEFORM_ID   : return "no more waveform ids";
            case PI_I2C_OPEN_FAILED  : return "can't open I2C device";
            case PI_SER_OPEN_FAILED  : return "can't open serial device";
            case PI_SPI_OPEN_FAILED  : return "can't open SPI device";
            case PI_BAD_I2C_BUS      : return "bad I2C bus";
            case PI_BAD_I2C_ADDR     : return "bad I2C address";
            case PI_BAD_SPI_CHANNEL  : return "bad SPI channel";
            case PI_BAD_FLAGS        : return "bad i2c/spi/ser open flags";
            case PI_BAD_SPI_SPEED    : return "bad SPI speed";
            case PI_BAD_SER_DEVICE   : return "bad serial device name";
            case PI_BAD_SER_SPEED    : return "bad serial baud rate";
            case PI_BAD_PARAM        : return "bad i2c/spi/ser parameter";
            case PI_I2C_WRITE_FAILED : return "I2C write failed";
            case PI_I2C_READ_FAILED  : return "I2C read failed";
            case PI_BAD_SPI_COUNT    : return "bad SPI count";
            case PI_SER_WRITE_FAILED : return "ser write failed";
            case PI_SER_READ_FAILED  : return "ser read failed";
            case PI_SER_READ_NO_DATA : return "ser read no data available";
            case PI_UNKNOWN_COMMAND  : return "unknown command";
            case PI_SPI_XFER_FAILED  : return "spi xfer/read/write failed";
            case PI_BAD_POINTER      : return "bad (NULL) pointer";
            case PI_NO_AUX_SPI       : return "no auxiliary SPI on Pi A or B";
            case PI_NOT_PWM_GPIO     : return "GPIO is not in use for PWM";
            case PI_NOT_SERVO_GPIO   : return "GPIO is not in use for servo pulses";
            case PI_NOT_HCLK_GPIO    : return "GPIO has no hardware clock";
            case PI_NOT_HPWM_GPIO    : return "GPIO has no hardware PWM";
            case PI_BAD_HPWM_FREQ    : return "invalid hardware PWM frequency";
            case PI_BAD_HPWM_DUTY    : return "hardware PWM dutycycle not 0-1M";
            case PI_BAD_HCLK_FREQ    : return "invalid hardware clock frequency";
            case PI_BAD_HCLK_PASS    : return "need password to use hardware clock 1";
            case PI_HPWM_ILLEGAL     : return "illegal: return PWM in use for main clock";
            case PI_BAD_DATABITS     : return "serial data bits not 1-32";
            case PI_BAD_STOPBITS     : return "serial (half) stop bits not 2-8";
            case PI_MSG_TOOBIG       : return "socket/pipe message too big";
            case PI_BAD_MALLOC_MODE  : return "bad memory allocation mode";
            case PI_TOO_MANY_SEGS    : return "too many I2C transaction segments";
            case PI_BAD_I2C_SEG      : return "an I2C transaction segment failed";
            case PI_BAD_SMBUS_CMD    : return "SMBus command not supported by driver";
            case PI_NOT_I2C_GPIO     : return "no bit bang I2C in progress on GPIO";
            case PI_BAD_I2C_WLEN     : return "bad I2C write length";
            case PI_BAD_I2C_RLEN     : return "bad I2C read length";
            case PI_BAD_I2C_CMD      : return "bad I2C command";
            case PI_BAD_I2C_BAUD     : return "bad I2C baud rate: return not 50-500k";
            case PI_CHAIN_LOOP_CNT   : return "bad chain loop count";
            case PI_BAD_CHAIN_LOOP   : return "empty chain loop";
            case PI_CHAIN_COUNTER    : return "too many chain counters";
            case PI_BAD_CHAIN_CMD    : return "bad chain command";
            case PI_BAD_CHAIN_DELAY  : return "bad chain delay micros";
            case PI_CHAIN_NESTING    : return "chain counters nested too deeply";
            case PI_CHAIN_TOO_BIG    : return "chain is too long";
            case PI_DEPRECATED       : return "deprecated function removed";
            case PI_BAD_SER_INVERT   : return "bit bang serial invert not 0 or 1";
            case PI_BAD_EDGE         : return "bad ISR edge: return not 1: return 1: return or 2";
            case PI_BAD_ISR_INIT     : return "bad ISR initialisation";
            case PI_BAD_FOREVER      : return "loop forever must be last chain command";
            case PI_BAD_FILTER       : return "bad filter parameter";
            case PI_BAD_PAD          : return "bad pad number";
            case PI_BAD_STRENGTH     : return "bad pad drive strength";
            case PI_FIL_OPEN_FAILED  : return "file open failed";
            case PI_BAD_FILE_MODE    : return "bad file mode";
            case PI_BAD_FILE_FLAG    : return "bad file flag";
            case PI_BAD_FILE_READ    : return "bad file read";
            case PI_BAD_FILE_WRITE   : return "bad file write";
            case PI_FILE_NOT_ROPEN   : return "file not open for read";
            case PI_FILE_NOT_WOPEN   : return "file not open for write";
            case PI_BAD_FILE_SEEK    : return "bad file seek";
            case PI_NO_FILE_MATCH    : return "no files match pattern";
            case PI_NO_FILE_ACCESS   : return "no permission to access file";
            case PI_FILE_IS_A_DIR    : return "file is a directory";
            case PI_BAD_SHELL_STATUS : return "bad shell return status";
            case PI_BAD_SCRIPT_NAME  : return "bad script name";
            case PI_BAD_SPI_BAUD     : return "bad SPI baud rate: return not 50-500k";
            case PI_NOT_SPI_GPIO     : return "no bit bang SPI in progress on GPIO";
            case PI_BAD_EVENT_ID     : return "bad event id";
            case PI_CMD_INTERRUPTED  : return "command interrupted: return Python";
            case PI_NOT_ON_BCM2711   : return "not available on BCM2711";
            case PI_ONLY_ON_BCM2711  : return "only available on BCM2711";
        }
        return "Not listed";
    }

    /**
     * <p>from.</p>
     *
     * @param value a {@link java.lang.Number} object.
     * @return a {@link com.pi4j.library.pigpio.PiGpioError} object.
     */
    public static PiGpioError from(Number value){
        for(PiGpioError c : PiGpioError.values()){
            if(c.value() == value.intValue()) return c;
        }
        return UNKNOWN;
    }
}
