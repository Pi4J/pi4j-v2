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

#ifndef PI4J_COMMAND_I2C_H
#define PI4J_COMMAND_I2C_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>
#include <Wire.h>
#include <ArduinoJson.h>
#include "CommandsArgumentParser.h"
#include "main.h"

// create ECHO command invocation handler
void i2c_command_execute(SerialCommands* sender){
    DynamicJsonDocument doc(256);
    JsonObject response = doc.to<JsonObject>();

   // get I2C bus
   int bus = GetCommandI2cBusArgument(sender);

   // get I2C device 
   int device = GetCommandI2cDeviceArgument(sender);

   // get I2C "raw" mode data processing
   int rawMode = GetCommandBooleanArgument(sender);

   // handle bus argument errors
   if(bus < 0) {
      response["id"] = "error";
      response["errno"] = bus;
      response["arg"] = "bus";
      response["msg"] = GetCommandArgumentError(bus);
   }

   // handle device argument errors
   else if(device < 0) {
      response["id"] = "error";
      response["errno"] = bus;
      response["arg"] = "device";
      response["msg"] = GetCommandArgumentError(device);
   }

   else{
      response["id"] = "i2c";
      response["bus"] = bus;
      response["device"] = device;
      response["raw"] = (bool)rawMode;

      // default callback handlers for Register operations
      void (*i2cReceive_ptr)(int) = &receiveI2CData; 
      void (*i2cSend_ptr)(void) = &sendI2CData; 

      // if raw mode is enabled, then swap function pointers 
      // for the raw data processing callbacks
      if(rawMode > 0){
         i2cReceive_ptr = &receiveI2CDataRaw; 
         i2cSend_ptr = &sendI2CDataRaw; 
      }

      // end I2C on existing bus if previously assigned
      if(i2cCache.wire != nullptr){
         i2cCache.wire->end();
      }

      // reset I2C cache
      i2cCache.reset();

      // setup which I2C bus to enable
      if(bus == 0){  
         i2cCache.wire = &Wire;   // setup I2C BUS 0
      } else if(bus == 1){
         i2cCache.wire = &Wire1;  // setup I2C BUS 1
      }

      // initialize i2c as slave
      i2cCache.wire->begin(device);

      // define callbacks for i2c communication
      i2cCache.wire->onReceive(i2cReceive_ptr);
      i2cCache.wire->onRequest(i2cSend_ptr);
   }

   // output response
   serializeJson(doc, *sender->GetSerial());
   sender->GetSerial()->println();
}

// create I2C command variable
SerialCommand I2cCommand = SerialCommand("i2c", i2c_command_execute);

#endif //PI4J_COMMAND_I2C_H
