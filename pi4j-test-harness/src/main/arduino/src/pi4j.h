/*
 *  **********************************************************************
 *  ORGANIZATION  :  Pi4J
 *  PROJECT       :  Pi4J :: TEST  :: Arduino Test Harness
 *  
 *  This file is part of the Pi4J project. More information about
 *  this project can be found here:  https://pi4j.com/
 *  **********************************************************************
 *  
 *  Copyright (C) 2012 - 2019 Pi4J
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Lesser Public License for more details.
 *  
 *  You should have received a copy of the GNU General Lesser Public
 *  License along with this program.  If not, see
 *  <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *  **********************************************************************
 */

#ifndef PI4J_H
#define PI4J_H


//
// SEE ARDUINO PIN DEFINITIONS FOR SAMD MICROCONTROLLER HERE:
// https://github.com/arduino/ArduinoCore-samd/blob/1.6.19/variants/arduino_zero/variant.cpp
//

// -----------------------------------------------------------------------------------------------------------
// GENERIC HELPER MACROS
// -----------------------------------------------------------------------------------------------------------

#define STRINGIZE2(s) #s
#define STRINGIZE(s) STRINGIZE2(s)

// -----------------------------------------------------------------------------------------------------------
// FIRMWARE METADATA DEFINITIONS
// -----------------------------------------------------------------------------------------------------------

// console boot header/banner
#ifndef PI4J_BANNER 
#define PI4J_CRLF        "\r\n"
#define PI4J_BANNER_LINE "======================================================="
#define PI4J_BANNER_L1   "                                                       "
#define PI4J_BANNER_L2   "                  The Pi4J Project                     "
#define PI4J_BANNER_L3   "                Arduino Test Harness                   "
#define PI4J_BANNER_L4   "                                                       "
#define PI4J_BANNER      PI4J_BANNER_LINE PI4J_CRLF PI4J_BANNER_L1 PI4J_CRLF PI4J_BANNER_L2 PI4J_CRLF PI4J_BANNER_L3 PI4J_CRLF PI4J_BANNER_L4 PI4J_CRLF PI4J_BANNER_LINE 
#endif

// copyright string
#ifndef PI4J_COPYRIGHT 
#define PI4J_COPYRIGHT   "COPYRIGHT: PI4J, LLC @ 2019, ALL RIGHTS RESERVED"
#endif

#ifndef BANNER 
#define BANNER                PI4J_BANNER
#endif

#ifndef COPYRIGHT 
#define COPYRIGHT             PI4J_COPYRIGHT
#endif

// -----------------------------------------------------------------------------------------------------------
// FIRMWARE METADATA DEFINITIONS
// -----------------------------------------------------------------------------------------------------------

// firmware name, version and last updated date
#ifndef FIRMWARE_NAME
#define FIRMWARE_NAME                  "Pi4J ARDUINO TEST HARNESS"
#endif

#ifndef FIRMWARE_VERSION
#define FIRMWARE_VERSION               0.0.0 (ALPHA) // <- this is passed in from build environment
#endif
#define FIRMWARE_VERSION_STRING        STRINGIZE(FIRMWARE_VERSION)

#ifndef FIRMWARE_DATE
#define FIRMWARE_DATE                  1900-01-01 // <- this is passed in from build environment
#endif
#define FIRMWARE_DATE_STRING           STRINGIZE(FIRMWARE_DATE)

#ifndef HARDWARE_VERSION
#define HARDWARE_VERSION               "0.1"
#endif

#ifndef FIRMWARE_BOOT_DELAY
#define FIRMWARE_BOOT_DELAY            0
#endif


// -----------------------------------------------------------------------------------------------------------
// FIRMWARE DEVELOPMENT FLAGS
// -----------------------------------------------------------------------------------------------------------

// -----------------------------------------------------------------------------------------------------------
// GENERIC TIME INTERVAL DEFINITIONS (all interval times are in milliseconds)
// -----------------------------------------------------------------------------------------------------------
#define ONE_SECOND                     1000
#define TEN_SECOND                     10*ONE_SECOND
#define ONE_MINUTE                     60*ONE_SECOND

// -----------------------------------------------------------------------------------------------------------
// DEBUGGING INTERFACE SETTINGS AND HARDWARE DEFINITIONS
// -----------------------------------------------------------------------------------------------------------

#define DEBUG_INTERFACE                SerialUSB
#define DEBUG_BAUD_RATE                115200             // Debug baud rate

// -----------------------------------------------------------------------------------------------------------
// INTERACTIVE DIAGNOSTICS CONSOLE DEFINITIONS
// -----------------------------------------------------------------------------------------------------------

#define CONSOLE_INTERFACE              Serial
#define CONSOLE_BAUD_RATE              115200


// -----------------------------------------------------------------------------------------------------------
// I2C DEFINITIONS
// -----------------------------------------------------------------------------------------------------------

#define I2C_SLAVE_ADDRESS              0x04

// -----------------------------------------------------------------------------------------------------------
// MISC GLOBAL INCLUDES
// -----------------------------------------------------------------------------------------------------------

// include global utility classes
//#include "util/Console.h"

// include runtime classes
//#include "runtime/Runtime.h"


#define ERROR_COMMAND_ARGUMENT_MISSING        -1
#define ERROR_COMMAND_ARGUMENT_INVALID        -2
#define ERROR_INVALID_PIN_OUT_OF_RANGE        -3 
#define ERROR_INVALID_PIN_RESTRICTED          -4
#define ERROR_INVALID_PIN_DISABLED            -5
#define ERROR_INVALID_I2C_BUS_OUT_OF_RANGE    -11
#define ERROR_INVALID_I2C_DEVICE_OUT_OF_RANGE -12
#define ERROR_UNSUPPORTED_SERIAL_PORT         -13
#define ERROR_UNSUPPORTED_SERIAL_BAUD_RATE    -14
#define ERROR_UNSUPPORTED_SPI_CHANNEL         -15 
#define ERROR_UNSUPPORTED_COMMAND             -99


#endif //PI4J_H
