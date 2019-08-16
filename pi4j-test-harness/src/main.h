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

#ifndef PI4J_MAIN_H
#define PI4J_MAIN_H

// include platform libraries
#include <string>

// include platform specific libraries
#include <Wire.h> // for I2C comms. (SDA, SCL)
//#include "wiring_private.h"

// include support for Interactive Serial Commands
// https://raw.githubusercontent.com/ppedro74/Arduino-SerialCommands
#include <SerialCommands.h>

// include Pi4J common  
#include "pi4j.h"

// include Pi4J utility classes
#include "util/Utils.h"

// ------------------------------------------------------------------------------------------------------------------------------
// DEFINE FUNTION PROTOTYPES
// ------------------------------------------------------------------------------------------------------------------------------
void loop();
void setup();
void info(Stream* out);
void reboot();
void inititalize();
void reset();
void receiveI2CData(int byteCount);
void sendI2CData();
void receiveI2CDataRaw(int byteCount);
void sendI2CDataRaw();


// create priped interface for interactive console (muxed serial ports)
StreamPipe console_pipe(&CONSOLE_INTERFACE, &DEBUG_INTERFACE);


// ------------------------------------------------------------------------------------------------------------------------------
// DEFINE RUNTIME COMPONENTS
// ------------------------------------------------------------------------------------------------------------------------------

// create NULL stream; used for debugging only
struct NullStream : public Stream{
    NullStream( void ) { return; }
    int available( void ) { return 0; }
    void flush( void ) { return; }
    int peek( void ) { return -1; }
    int read( void ){ return -1; }
    size_t write( uint8_t u_Data ){ return u_Data; }
} nullStream;


struct I2cRegister {
    uint8_t data[32] = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
    uint16_t length = 0;
};

struct I2cCache {
    I2cRegister reg[255];
    uint8_t address = 0;
    uint8_t buffer[32] = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
    uint16_t length = 0;
    bool rawMode = false;
    TwoWire* wire;
    void reset(){
        address = 0;
        length = 0;
        rawMode = false;
        memset(buffer, 0, sizeof buffer);
        wire = nullptr;
        for(int i = 0; i < 256; i++){
            reg[i].length = 0;            
            memset(reg[i].data, 0, sizeof reg[i].data);
        }
    }
};

I2cCache i2cCache;

#endif //PI4J_MAIN_H