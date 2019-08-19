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

#ifndef PI4J_COMMAND_RESET_H
#define PI4J_COMMAND_RESET_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>

// create RESET command invocation handler
void reset_command_execute(SerialCommands* sender){
    // DynamicJsonDocument doc(1024);
    // JsonObject response = doc.to<JsonObject>();
    // response["id"] = "reset";
    // response["msg"] = "Resetting all I/O pins and testing states.";
    // serializeJson(doc, console);
    // console.println();
    reset();
}

// create RESET command variable
SerialCommand ResetCommand = SerialCommand("reset", reset_command_execute);

#endif //PI4J_COMMAND_RESET_H
