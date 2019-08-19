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

#ifndef PI4J_COMMAND_PINS_H
#define PI4J_COMMAND_PINS_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>
#include "CommandsArgumentParser.h"

// create PINS command invocation handler
void pins_command_execute(SerialCommands* sender){

    DynamicJsonDocument doc(1024*GPIO_MAX_PINS);
    JsonObject response = doc.to<JsonObject>();

    // get <ALL> argument
    int all = GetCommandBooleanArgument(sender);

	// check for missing or invalid <ALL> argument
    if (all != ERROR_COMMAND_ARGUMENT_MISSING && all < 0){
        response["id"] = "error";
        response["errno"] = all;
        response["arg"] = "all";
        response["msg"] = GetCommandArgumentError(all);
        serializeJson(doc, *sender->GetSerial());
        sender->GetSerial()->println();
		return;
	}

    response["id"] = "pins";
    JsonArray pinscontainer = response.createNestedArray("pins");

    // iterate pin states in cache
    int total, enabled, restricted, disabled, inputs, outputs;
    total = enabled = restricted = disabled = inputs = outputs = 0;
    for(int p = 0; p < GPIO_MAX_PINS; p++){

        if(all > 0 || (pins[p].enabled && !pins[p].restricted)){          
            int v = digitalRead(p);
            pins[p].value = v;
            
            // increment summary counters
            total++;
            if(pins[p].restricted)           restricted++;
            if(pins[p].enabled)              enabled++;
            if(!pins[p].enabled)             disabled++;
            if(pins[p].mode == OUTPUT)       outputs++;
            if(pins[p].mode == INPUT)        inputs++;
            if(pins[p].mode == INPUT_PULLUP) inputs++;

            // create nested pin object and insert into the container array
            JsonObject pincontainer = pinscontainer.createNestedObject();
            SerializePin(pincontainer, p, false);
        }
    }    

    // include summary totals
    response["total"] = total;
    response["restricted"] = restricted;
    response["disabled"] = disabled;
    response["enabled"] = enabled;
    response["inputs"] = inputs;
    response["outputs"] = outputs;

    // serialize and print    
    serializeJson(doc, *sender->GetSerial());
    sender->GetSerial()->println();
}

// create DOUT command variable
SerialCommand PinsCommand = SerialCommand("pins", pins_command_execute);

#endif //PI4J_COMMAND_PINS_H

