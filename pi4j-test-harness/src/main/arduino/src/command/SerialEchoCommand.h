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

#ifndef PI4J_COMMAND_SERIAL_H
#define PI4J_COMMAND_SERIAL_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>
#include <ArduinoJson.h>
#include "CommandsArgumentParser.h"
#include "main.h"

// create SERIAL command invocation handler
void serial_command_execute(SerialCommands* sender){
    DynamicJsonDocument doc(256);
    JsonObject response = doc.to<JsonObject>();

   // get BAUD bus
   int port = GetCommandSerialPortArgument(sender);

   // get BAUD bus
   int baud = GetCommandSerialBaudArgument(sender);

   // handle bus argument errors
   if(port < 0) {
      response["id"] = "error";
      response["errno"] = port;
      response["arg"] = "port";
      response["msg"] = GetCommandArgumentError(port);
   }

   // handle BAUD argument errors
   else if(baud < 0) {
      response["id"] = "error";
      response["errno"] = baud;
      response["arg"] = "baud";
      response["msg"] = GetCommandArgumentError(baud);
   }

   else{
      response["id"] = "serial";
      response["port"] = port;
      response["baud"] = baud;

      // end any existing serial echo
      if(serialEcho != nullptr){
         serialEcho->end();         
      }

      // setup which I2C bus to enable
      if(port == 3){  
         serialEcho = &Serial3;   // setup SERIAL UART3
      } else if(port == 2){
         serialEcho = &Serial2;   // setup SERIAL UART2
      } else {
         serialEcho = nullptr;
      }

      // start seria lecho
      if(serialEcho != nullptr && baud > 0){
         serialEcho->begin(baud);
      }
   }

   // output response
   serializeJson(doc, *sender->GetSerial());
   sender->GetSerial()->println();
}

// create SERIAL command variable
SerialCommand SerialEchoCommand = SerialCommand("serial", serial_command_execute);

#endif //PI4J_COMMAND_SERIAL_H
