package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioConst.java
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

public interface PiGpioConst {
    // --------------------------------------
    // DEFAULT SOCKET CONNECTION PROPERTIES
    // --------------------------------------
    int DEFAULT_PORT    = 8888;
    String DEFAULT_HOST = "127.0.0.1";

    // ----------------------------------
    // PIGPIO PIN RANGE
    // ----------------------------------
    int PI_MIN_GPIO      = 0;
    int PI_MAX_GPIO      = 53;
    int PI_MAX_USER_GPIO = 31;

    // ----------------------------------
    // PIGPIO PIN LEVELS
    // ----------------------------------
    int PI_OFF     = 0;
    int PI_ON      = 1;

    int PI_CLEAR   = 0;
    int PI_SET     = 1;

    int PI_LOW     = 0;
    int PI_HIGH    = 1;

    // ----------------------------------
    // PIGPIO WATCHDOG TIMEOUT
    // ----------------------------------
    int PI_TIMEOUT = 2;

    // ----------------------------------
    // PIGPIO LIBRARY COMMANDS
    // ----------------------------------
    int PI_CMD_MODES = 0;
    int PI_CMD_MODEG = 1;
    int PI_CMD_PUD   = 2;
    int PI_CMD_READ  = 3;
    int PI_CMD_WRITE = 4;
    int PI_CMD_PWM   = 5;
    int PI_CMD_PRS   = 6;
    int PI_CMD_PFS   = 7;
    int PI_CMD_SERVO = 8;
    int PI_CMD_WDOG  = 9;
    int PI_CMD_BR1   = 10;
    int PI_CMD_BR2   = 11;
    int PI_CMD_BC1   = 12;
    int PI_CMD_BC2   = 13;
    int PI_CMD_BS1   = 14;
    int PI_CMD_BS2   = 15;
    int PI_CMD_TICK  = 16;
    int PI_CMD_HWVER = 17;
    int PI_CMD_NO    = 18;
    int PI_CMD_NB    = 19;
    int PI_CMD_NP    = 20;
    int PI_CMD_NC    = 21;
    int PI_CMD_PRG   = 22;
    int PI_CMD_PFG   = 23;
    int PI_CMD_PRRG  = 24;
    int PI_CMD_HELP  = 25;
    int PI_CMD_PIGPV = 26;
    int PI_CMD_WVCLR = 27;
    int PI_CMD_WVAG  = 28;
    int PI_CMD_WVAS  = 29;
    int PI_CMD_WVGO  = 30;
    int PI_CMD_WVGOR = 31;
    int PI_CMD_WVBSY = 32;
    int PI_CMD_WVHLT = 33;
    int PI_CMD_WVSM  = 34;
    int PI_CMD_WVSP  = 35;
    int PI_CMD_WVSC  = 36;
    int PI_CMD_TRIG  = 37;
    int PI_CMD_PROC  = 38;
    int PI_CMD_PROCD = 39;
    int PI_CMD_PROCR = 40;
    int PI_CMD_PROCS = 41;
    int PI_CMD_SLRO  = 42;
    int PI_CMD_SLR   = 43;
    int PI_CMD_SLRC  = 44;
    int PI_CMD_PROCP = 45;
    int PI_CMD_MICS  = 46;
    int PI_CMD_MILS  = 47;
    int PI_CMD_PARSE = 48;
    int PI_CMD_WVCRE = 49;
    int PI_CMD_WVDEL = 50;
    int PI_CMD_WVTX  = 51;
    int PI_CMD_WVTXR = 52;
    int PI_CMD_WVNEW = 53;
    int PI_CMD_I2CO  = 54;
    int PI_CMD_I2CC  = 55;
    int PI_CMD_I2CRD = 56;
    int PI_CMD_I2CWD = 57;
    int PI_CMD_I2CWQ = 58;
    int PI_CMD_I2CRS = 59;
    int PI_CMD_I2CWS = 60;
    int PI_CMD_I2CRB = 61;
    int PI_CMD_I2CWB = 62;
    int PI_CMD_I2CRW = 63;
    int PI_CMD_I2CWW = 64;
    int PI_CMD_I2CRK = 65;
    int PI_CMD_I2CWK = 66;
    int PI_CMD_I2CRI = 67;
    int PI_CMD_I2CWI = 68;
    int PI_CMD_I2CPC = 69;
    int PI_CMD_I2CPK = 70;
    int PI_CMD_SPIO  = 71;
    int PI_CMD_SPIC  = 72;
    int PI_CMD_SPIR  = 73;
    int PI_CMD_SPIW  = 74;
    int PI_CMD_SPIX  = 75;
    int PI_CMD_SERO  = 76;
    int PI_CMD_SERC  = 77;
    int PI_CMD_SERRB = 78;
    int PI_CMD_SERWB = 79;
    int PI_CMD_SERR  = 80;
    int PI_CMD_SERW  = 81;
    int PI_CMD_SERDA = 82;
    int PI_CMD_GDC   = 83;
    int PI_CMD_GPW   = 84;
    int PI_CMD_HC    = 85;
    int PI_CMD_HP    = 86;
    int PI_CMD_CF1   = 87;
    int PI_CMD_CF2   = 88;
    int PI_CMD_BI2CC = 89;
    int PI_CMD_BI2CO = 90;
    int PI_CMD_BI2CZ = 91;
    int PI_CMD_I2CZ  = 92;
    int PI_CMD_WVCHA = 93;
    int PI_CMD_SLRI  = 94;
    int PI_CMD_CGI   = 95;
    int PI_CMD_CSI   = 96;
    int PI_CMD_FG    = 97;
    int PI_CMD_FN    = 98;
    int PI_CMD_NOIB  = 99;
    int PI_CMD_WVTXM = 100;
    int PI_CMD_WVTAT = 101;
    int PI_CMD_PADS  = 102;
    int PI_CMD_PADG  = 103;
    int PI_CMD_FO    = 104;
    int PI_CMD_FC    = 105;
    int PI_CMD_FR    = 106;
    int PI_CMD_FW    = 107;
    int PI_CMD_FS    = 108;
    int PI_CMD_FL    = 109;
    int PI_CMD_SHELL = 110;
    int PI_CMD_BSPIC = 111;
    int PI_CMD_BSPIO = 112;
    int PI_CMD_BSPIX = 113;
    int PI_CMD_BSCX  = 114;
    int PI_CMD_EVM   = 115;
    int PI_CMD_EVT   = 116;
    int PI_CMD_PROCU = 117;

    // ----------------------------------
    // GPIO PIN MODES
    // ----------------------------------
    int PI_INPUT  = 0;
    int PI_OUTPUT = 1;
    int PI_ALT0   = 4;
    int PI_ALT1   = 5;
    int PI_ALT2   = 6;
    int PI_ALT3   = 7;
    int PI_ALT4   = 3;
    int PI_ALT5   = 2;

    // ----------------------------------
    // GPIO PIN PULL OPTIONS
    // ----------------------------------
    int PI_PUD_OFF  = 0;
    int PI_PUD_DOWN = 1;
    int PI_PUD_UP   = 2;

    // ----------------------------------
    // GPIO PWM
    // ----------------------------------
    /* dutycycle: 0-range */
    int PI_DEFAULT_DUTYCYCLE_RANGE  = 255;

    /* range: 25-40000 */
    int PI_MIN_DUTYCYCLE_RANGE      = 25;
    int PI_MAX_DUTYCYCLE_RANGE      = 40000;

    // ----------------------------------
    // GPIO SERVO
    // ----------------------------------

    /* pulsewidth: 0, 500-2500 */
    int PI_SERVO_OFF                = 0;
    int PI_MIN_SERVO_PULSEWIDTH     = 500;
    int PI_MAX_SERVO_PULSEWIDTH     = 2500;

    // ----------------------------------
    // GPIO HARDWARE PWM
    // ----------------------------------
    int PI_HW_PWM_MIN_FREQ          = 1;
    int PI_HW_PWM_MAX_FREQ          = 125000000;
    int PI_HW_PWM_MAX_FREQ_2711     = 187500000;
    int PI_HW_PWM_RANGE             = 1000000;

    // ----------------------------------
    // DELAYS
    // ----------------------------------
    int PI_MAX_MICS_DELAY           = 1000000; /* 1 second */
    int PI_MAX_MILS_DELAY           = 60000;   /* 60 seconds */

}
