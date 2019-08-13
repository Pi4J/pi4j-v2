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

#ifndef PI4J_COMMAND_INVALID_H
#define PI4J_COMMAND_INVALID_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>

// create INVALID command invocation handler

//This is the default handler, and gets called when no other command matches. 
// Note: It does not get called for one_key commands that do not match
void invalid_command_handler(SerialCommands* sender, const char* cmd){
  DynamicJsonDocument doc(1024);
  JsonObject response = doc.to<JsonObject>();
  response["id"] = "error";
  response["errno"] = ERROR_UNSUPPORTED_COMMAND;
  response["msg"] = "Invalid or unsupported command";
  response["cmd"] = cmd;

  // output response
  serializeJson(doc, *sender->GetSerial());
  sender->GetSerial()->println();
}

#endif //PI4J_COMMAND_INVALID_H
