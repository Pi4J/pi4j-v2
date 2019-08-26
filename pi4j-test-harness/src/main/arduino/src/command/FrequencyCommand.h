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

#ifndef PI4J_COMMAND_FREQUENCY_H
#define PI4J_COMMAND_FREQUENCY_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>
#include <ArduinoJson.h>
#include "CommandsArgumentParser.h"
#include "main.h"

// create FREQUENCY command invocation handler
void frequency_command_execute(SerialCommands* sender){    
    DynamicJsonDocument doc(256);
    JsonObject response = doc.to<JsonObject>();

   // get PIN argument
   int pin = GetCommandPinArgument(sender);

   // handle pin argument errors
   if(pin < 0) {
      response["id"] = "error";
      response["errno"] = pin;
      response["arg"] = "pin";
      response["msg"] = GetCommandArgumentError(pin);
   } 
   else {
        // make sure to disable any HIGH signals on the pin
        pinMode(pin, OUTPUT);
        digitalWrite(pin, LOW);

        // make sure the reading pin is an input pin
        pinMode(pin, INPUT);
        pins[pin].enabled = true;
        pins[pin].mode = INPUT;

        // count the number of pulses and calculate the frequency in Hz
        long frequency = 500000/pulseIn(pin, HIGH);
        
        // prepare response
        response["id"] = "frequency";
        response["pin"] = pin;
        response["frequency"] = frequency;
        response["units"] = "Hz";
   }

   // output response
   serializeJson(doc, *sender->GetSerial());
   sender->GetSerial()->println();    
}

// create FREQUENCY command variable
SerialCommand FrequencyCommand = SerialCommand("frequency", frequency_command_execute);
SerialCommand FrequencyShortCommand = SerialCommand("f", frequency_command_execute);

#endif //PI4J_COMMAND_FREQUENCY_H
