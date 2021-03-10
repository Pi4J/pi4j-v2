package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  PiGpioConst.java
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
 * <p>PiGpioConst interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface PiGpioConst {
    // --------------------------------------
    // DEFAULT SOCKET CONNECTION PROPERTIES
    // --------------------------------------
    /** Constant <code>DEFAULT_PORT=8888</code> */
    int DEFAULT_PORT    = 8888;
    /** Constant <code>DEFAULT_HOST="127.0.0.1"</code> */
    String DEFAULT_HOST = "127.0.0.1";

    // ----------------------------------
    // PIGPIO PIN RANGE
    // ----------------------------------
    /** Constant <code>PI_MIN_GPIO=0</code> */
    int PI_MIN_GPIO      = 0;
    /** Constant <code>PI_MAX_GPIO=53</code> */
    int PI_MAX_GPIO      = 53;
    /** Constant <code>PI_MAX_USER_GPIO=31</code> */
    int PI_MAX_USER_GPIO = 31;

    // ----------------------------------
    // PIGPIO PIN LEVELS
    // ----------------------------------
    /** Constant <code>PI_OFF=0</code> */
    int PI_OFF     = 0;
    /** Constant <code>PI_ON=1</code> */
    int PI_ON      = 1;

    /** Constant <code>PI_CLEAR=0</code> */
    int PI_CLEAR   = 0;
    /** Constant <code>PI_SET=1</code> */
    int PI_SET     = 1;

    /** Constant <code>PI_LOW=0</code> */
    int PI_LOW     = 0;
    /** Constant <code>PI_HIGH=1</code> */
    int PI_HIGH    = 1;

    // ----------------------------------
    // PIGPIO WATCHDOG TIMEOUT
    // ----------------------------------
    /** Constant <code>PI_TIMEOUT=2</code> */
    int PI_TIMEOUT = 2;

    // ----------------------------------
    // PIGPIO LIBRARY COMMANDS
    // ----------------------------------
    /** Constant <code>PI_CMD_MODES=0</code> */
    int PI_CMD_MODES = 0;
    /** Constant <code>PI_CMD_MODEG=1</code> */
    int PI_CMD_MODEG = 1;
    /** Constant <code>PI_CMD_PUD=2</code> */
    int PI_CMD_PUD   = 2;
    /** Constant <code>PI_CMD_READ=3</code> */
    int PI_CMD_READ  = 3;
    /** Constant <code>PI_CMD_WRITE=4</code> */
    int PI_CMD_WRITE = 4;
    /** Constant <code>PI_CMD_PWM=5</code> */
    int PI_CMD_PWM   = 5;
    /** Constant <code>PI_CMD_PRS=6</code> */
    int PI_CMD_PRS   = 6;
    /** Constant <code>PI_CMD_PFS=7</code> */
    int PI_CMD_PFS   = 7;
    /** Constant <code>PI_CMD_SERVO=8</code> */
    int PI_CMD_SERVO = 8;
    /** Constant <code>PI_CMD_WDOG=9</code> */
    int PI_CMD_WDOG  = 9;
    /** Constant <code>PI_CMD_BR1=10</code> */
    int PI_CMD_BR1   = 10;
    /** Constant <code>PI_CMD_BR2=11</code> */
    int PI_CMD_BR2   = 11;
    /** Constant <code>PI_CMD_BC1=12</code> */
    int PI_CMD_BC1   = 12;
    /** Constant <code>PI_CMD_BC2=13</code> */
    int PI_CMD_BC2   = 13;
    /** Constant <code>PI_CMD_BS1=14</code> */
    int PI_CMD_BS1   = 14;
    /** Constant <code>PI_CMD_BS2=15</code> */
    int PI_CMD_BS2   = 15;
    /** Constant <code>PI_CMD_TICK=16</code> */
    int PI_CMD_TICK  = 16;
    /** Constant <code>PI_CMD_HWVER=17</code> */
    int PI_CMD_HWVER = 17;
    /** Constant <code>PI_CMD_NO=18</code> */
    int PI_CMD_NO    = 18;
    /** Constant <code>PI_CMD_NB=19</code> */
    int PI_CMD_NB    = 19;
    /** Constant <code>PI_CMD_NP=20</code> */
    int PI_CMD_NP    = 20;
    /** Constant <code>PI_CMD_NC=21</code> */
    int PI_CMD_NC    = 21;
    /** Constant <code>PI_CMD_PRG=22</code> */
    int PI_CMD_PRG   = 22;
    /** Constant <code>PI_CMD_PFG=23</code> */
    int PI_CMD_PFG   = 23;
    /** Constant <code>PI_CMD_PRRG=24</code> */
    int PI_CMD_PRRG  = 24;
    /** Constant <code>PI_CMD_HELP=25</code> */
    int PI_CMD_HELP  = 25;
    /** Constant <code>PI_CMD_PIGPV=26</code> */
    int PI_CMD_PIGPV = 26;
    /** Constant <code>PI_CMD_WVCLR=27</code> */
    int PI_CMD_WVCLR = 27;
    /** Constant <code>PI_CMD_WVAG=28</code> */
    int PI_CMD_WVAG  = 28;
    /** Constant <code>PI_CMD_WVAS=29</code> */
    int PI_CMD_WVAS  = 29;
    /** Constant <code>PI_CMD_WVGO=30</code> */
    int PI_CMD_WVGO  = 30;
    /** Constant <code>PI_CMD_WVGOR=31</code> */
    int PI_CMD_WVGOR = 31;
    /** Constant <code>PI_CMD_WVBSY=32</code> */
    int PI_CMD_WVBSY = 32;
    /** Constant <code>PI_CMD_WVHLT=33</code> */
    int PI_CMD_WVHLT = 33;
    /** Constant <code>PI_CMD_WVSM=34</code> */
    int PI_CMD_WVSM  = 34;
    /** Constant <code>PI_CMD_WVSP=35</code> */
    int PI_CMD_WVSP  = 35;
    /** Constant <code>PI_CMD_WVSC=36</code> */
    int PI_CMD_WVSC  = 36;
    /** Constant <code>PI_CMD_TRIG=37</code> */
    int PI_CMD_TRIG  = 37;
    /** Constant <code>PI_CMD_PROC=38</code> */
    int PI_CMD_PROC  = 38;
    /** Constant <code>PI_CMD_PROCD=39</code> */
    int PI_CMD_PROCD = 39;
    /** Constant <code>PI_CMD_PROCR=40</code> */
    int PI_CMD_PROCR = 40;
    /** Constant <code>PI_CMD_PROCS=41</code> */
    int PI_CMD_PROCS = 41;
    /** Constant <code>PI_CMD_SLRO=42</code> */
    int PI_CMD_SLRO  = 42;
    /** Constant <code>PI_CMD_SLR=43</code> */
    int PI_CMD_SLR   = 43;
    /** Constant <code>PI_CMD_SLRC=44</code> */
    int PI_CMD_SLRC  = 44;
    /** Constant <code>PI_CMD_PROCP=45</code> */
    int PI_CMD_PROCP = 45;
    /** Constant <code>PI_CMD_MICS=46</code> */
    int PI_CMD_MICS  = 46;
    /** Constant <code>PI_CMD_MILS=47</code> */
    int PI_CMD_MILS  = 47;
    /** Constant <code>PI_CMD_PARSE=48</code> */
    int PI_CMD_PARSE = 48;
    /** Constant <code>PI_CMD_WVCRE=49</code> */
    int PI_CMD_WVCRE = 49;
    /** Constant <code>PI_CMD_WVDEL=50</code> */
    int PI_CMD_WVDEL = 50;
    /** Constant <code>PI_CMD_WVTX=51</code> */
    int PI_CMD_WVTX  = 51;
    /** Constant <code>PI_CMD_WVTXR=52</code> */
    int PI_CMD_WVTXR = 52;
    /** Constant <code>PI_CMD_WVNEW=53</code> */
    int PI_CMD_WVNEW = 53;
    /** Constant <code>PI_CMD_I2CO=54</code> */
    int PI_CMD_I2CO  = 54;
    /** Constant <code>PI_CMD_I2CC=55</code> */
    int PI_CMD_I2CC  = 55;
    /** Constant <code>PI_CMD_I2CRD=56</code> */
    int PI_CMD_I2CRD = 56;
    /** Constant <code>PI_CMD_I2CWD=57</code> */
    int PI_CMD_I2CWD = 57;
    /** Constant <code>PI_CMD_I2CWQ=58</code> */
    int PI_CMD_I2CWQ = 58;
    /** Constant <code>PI_CMD_I2CRS=59</code> */
    int PI_CMD_I2CRS = 59;
    /** Constant <code>PI_CMD_I2CWS=60</code> */
    int PI_CMD_I2CWS = 60;
    /** Constant <code>PI_CMD_I2CRB=61</code> */
    int PI_CMD_I2CRB = 61;
    /** Constant <code>PI_CMD_I2CWB=62</code> */
    int PI_CMD_I2CWB = 62;
    /** Constant <code>PI_CMD_I2CRW=63</code> */
    int PI_CMD_I2CRW = 63;
    /** Constant <code>PI_CMD_I2CWW=64</code> */
    int PI_CMD_I2CWW = 64;
    /** Constant <code>PI_CMD_I2CRK=65</code> */
    int PI_CMD_I2CRK = 65;
    /** Constant <code>PI_CMD_I2CWK=66</code> */
    int PI_CMD_I2CWK = 66;
    /** Constant <code>PI_CMD_I2CRI=67</code> */
    int PI_CMD_I2CRI = 67;
    /** Constant <code>PI_CMD_I2CWI=68</code> */
    int PI_CMD_I2CWI = 68;
    /** Constant <code>PI_CMD_I2CPC=69</code> */
    int PI_CMD_I2CPC = 69;
    /** Constant <code>PI_CMD_I2CPK=70</code> */
    int PI_CMD_I2CPK = 70;
    /** Constant <code>PI_CMD_SPIO=71</code> */
    int PI_CMD_SPIO  = 71;
    /** Constant <code>PI_CMD_SPIC=72</code> */
    int PI_CMD_SPIC  = 72;
    /** Constant <code>PI_CMD_SPIR=73</code> */
    int PI_CMD_SPIR  = 73;
    /** Constant <code>PI_CMD_SPIW=74</code> */
    int PI_CMD_SPIW  = 74;
    /** Constant <code>PI_CMD_SPIX=75</code> */
    int PI_CMD_SPIX  = 75;
    /** Constant <code>PI_CMD_SERO=76</code> */
    int PI_CMD_SERO  = 76;
    /** Constant <code>PI_CMD_SERC=77</code> */
    int PI_CMD_SERC  = 77;
    /** Constant <code>PI_CMD_SERRB=78</code> */
    int PI_CMD_SERRB = 78;
    /** Constant <code>PI_CMD_SERWB=79</code> */
    int PI_CMD_SERWB = 79;
    /** Constant <code>PI_CMD_SERR=80</code> */
    int PI_CMD_SERR  = 80;
    /** Constant <code>PI_CMD_SERW=81</code> */
    int PI_CMD_SERW  = 81;
    /** Constant <code>PI_CMD_SERDA=82</code> */
    int PI_CMD_SERDA = 82;
    /** Constant <code>PI_CMD_GDC=83</code> */
    int PI_CMD_GDC   = 83;
    /** Constant <code>PI_CMD_GPW=84</code> */
    int PI_CMD_GPW   = 84;
    /** Constant <code>PI_CMD_HC=85</code> */
    int PI_CMD_HC    = 85;
    /** Constant <code>PI_CMD_HP=86</code> */
    int PI_CMD_HP    = 86;
    /** Constant <code>PI_CMD_CF1=87</code> */
    int PI_CMD_CF1   = 87;
    /** Constant <code>PI_CMD_CF2=88</code> */
    int PI_CMD_CF2   = 88;
    /** Constant <code>PI_CMD_BI2CC=89</code> */
    int PI_CMD_BI2CC = 89;
    /** Constant <code>PI_CMD_BI2CO=90</code> */
    int PI_CMD_BI2CO = 90;
    /** Constant <code>PI_CMD_BI2CZ=91</code> */
    int PI_CMD_BI2CZ = 91;
    /** Constant <code>PI_CMD_I2CZ=92</code> */
    int PI_CMD_I2CZ  = 92;
    /** Constant <code>PI_CMD_WVCHA=93</code> */
    int PI_CMD_WVCHA = 93;
    /** Constant <code>PI_CMD_SLRI=94</code> */
    int PI_CMD_SLRI  = 94;
    /** Constant <code>PI_CMD_CGI=95</code> */
    int PI_CMD_CGI   = 95;
    /** Constant <code>PI_CMD_CSI=96</code> */
    int PI_CMD_CSI   = 96;
    /** Constant <code>PI_CMD_FG=97</code> */
    int PI_CMD_FG    = 97;
    /** Constant <code>PI_CMD_FN=98</code> */
    int PI_CMD_FN    = 98;
    /** Constant <code>PI_CMD_NOIB=99</code> */
    int PI_CMD_NOIB  = 99;
    /** Constant <code>PI_CMD_WVTXM=100</code> */
    int PI_CMD_WVTXM = 100;
    /** Constant <code>PI_CMD_WVTAT=101</code> */
    int PI_CMD_WVTAT = 101;
    /** Constant <code>PI_CMD_PADS=102</code> */
    int PI_CMD_PADS  = 102;
    /** Constant <code>PI_CMD_PADG=103</code> */
    int PI_CMD_PADG  = 103;
    /** Constant <code>PI_CMD_FO=104</code> */
    int PI_CMD_FO    = 104;
    /** Constant <code>PI_CMD_FC=105</code> */
    int PI_CMD_FC    = 105;
    /** Constant <code>PI_CMD_FR=106</code> */
    int PI_CMD_FR    = 106;
    /** Constant <code>PI_CMD_FW=107</code> */
    int PI_CMD_FW    = 107;
    /** Constant <code>PI_CMD_FS=108</code> */
    int PI_CMD_FS    = 108;
    /** Constant <code>PI_CMD_FL=109</code> */
    int PI_CMD_FL    = 109;
    /** Constant <code>PI_CMD_SHELL=110</code> */
    int PI_CMD_SHELL = 110;
    /** Constant <code>PI_CMD_BSPIC=111</code> */
    int PI_CMD_BSPIC = 111;
    /** Constant <code>PI_CMD_BSPIO=112</code> */
    int PI_CMD_BSPIO = 112;
    /** Constant <code>PI_CMD_BSPIX=113</code> */
    int PI_CMD_BSPIX = 113;
    /** Constant <code>PI_CMD_BSCX=114</code> */
    int PI_CMD_BSCX  = 114;
    /** Constant <code>PI_CMD_EVM=115</code> */
    int PI_CMD_EVM   = 115;
    /** Constant <code>PI_CMD_EVT=116</code> */
    int PI_CMD_EVT   = 116;
    /** Constant <code>PI_CMD_PROCU=117</code> */
    int PI_CMD_PROCU = 117;

    // ----------------------------------
    // GPIO PIN MODES
    // ----------------------------------
    /** Constant <code>PI_INPUT=0</code> */
    int PI_INPUT  = 0;
    /** Constant <code>PI_OUTPUT=1</code> */
    int PI_OUTPUT = 1;
    /** Constant <code>PI_ALT0=4</code> */
    int PI_ALT0   = 4;
    /** Constant <code>PI_ALT1=5</code> */
    int PI_ALT1   = 5;
    /** Constant <code>PI_ALT2=6</code> */
    int PI_ALT2   = 6;
    /** Constant <code>PI_ALT3=7</code> */
    int PI_ALT3   = 7;
    /** Constant <code>PI_ALT4=3</code> */
    int PI_ALT4   = 3;
    /** Constant <code>PI_ALT5=2</code> */
    int PI_ALT5   = 2;

    // ----------------------------------
    // GPIO PIN PULL OPTIONS
    // ----------------------------------
    /** Constant <code>PI_PUD_OFF=0</code> */
    int PI_PUD_OFF  = 0;
    /** Constant <code>PI_PUD_DOWN=1</code> */
    int PI_PUD_DOWN = 1;
    /** Constant <code>PI_PUD_UP=2</code> */
    int PI_PUD_UP   = 2;

    // ----------------------------------
    // GPIO ISR EDGE OPTIONS
    // ----------------------------------
    int PI_RISING_EDGE  = 0;
    int PI_FALLING_EDGE = 1;
    int PI_EITHER_EDGE  = 2;

    // ----------------------------------
    // GPIO PWM
    // ----------------------------------
    /* dutycycle: 0-range */
    /** Constant <code>PI_DEFAULT_DUTYCYCLE_RANGE=255</code> */
    int PI_DEFAULT_DUTYCYCLE_RANGE  = 255;

    /* range: 25-40000 */
    /** Constant <code>PI_MIN_DUTYCYCLE_RANGE=25</code> */
    int PI_MIN_DUTYCYCLE_RANGE      = 25;
    /** Constant <code>PI_MAX_DUTYCYCLE_RANGE=40000</code> */
    int PI_MAX_DUTYCYCLE_RANGE      = 40000;

    // ----------------------------------
    // GPIO SERVO
    // ----------------------------------

    /* pulsewidth: 0, 500-2500 */
    /** Constant <code>PI_SERVO_OFF=0</code> */
    int PI_SERVO_OFF                = 0;
    /** Constant <code>PI_MIN_SERVO_PULSEWIDTH=500</code> */
    int PI_MIN_SERVO_PULSEWIDTH     = 500;
    /** Constant <code>PI_MAX_SERVO_PULSEWIDTH=2500</code> */
    int PI_MAX_SERVO_PULSEWIDTH     = 2500;

    // ----------------------------------
    // GPIO HARDWARE PWM
    // ----------------------------------
    /** Constant <code>PI_HW_PWM_MIN_FREQ=1</code> */
    int PI_HW_PWM_MIN_FREQ          = 1;
    /** Constant <code>PI_HW_PWM_MAX_FREQ=125000000</code> */
    int PI_HW_PWM_MAX_FREQ          = 125000000;
    /** Constant <code>PI_HW_PWM_MAX_FREQ_2711=187500000</code> */
    int PI_HW_PWM_MAX_FREQ_2711     = 187500000;
    /** Constant <code>PI_HW_PWM_RANGE=1000000</code> */
    int PI_HW_PWM_RANGE             = 1000000;

    // ----------------------------------
    // DELAYS
    // ----------------------------------
    /** Constant <code>PI_MAX_MICS_DELAY=1000000</code> */
    int PI_MAX_MICS_DELAY           = 1000000; /* 1 second */
    /** Constant <code>PI_MAX_MILS_DELAY=60000</code> */
    int PI_MAX_MILS_DELAY           = 60000;   /* 60 seconds */

    // ----------------------------------
    // TIME TYPES
    // ----------------------------------
    int PI_TIME_RELATIVE            = 0;
    int PI_TIME_ABSOLUTE            = 1;

    // ----------------------------------
    // SIGNAL NUMBERS
    // ----------------------------------
    int SIGHUP = 1;
    int SIGINT = 2;
    int SIGQUIT = 3;
    int SIGKILL = 9;
    int SIGTERM = 15;
    int SIGSTOP = 19;
    int SIGTSTP = 20;
}
