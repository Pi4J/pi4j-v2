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

#ifndef PI4J_COMMAND_ECHO_H
#define PI4J_COMMAND_ECHO_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>

// create ECHO command invocation handler
void echo_command_execute(SerialCommands* sender){
    DynamicJsonDocument doc(256);
    JsonObject response = doc.to<JsonObject>();

   // get enabled argument
   int enable = GetCommandBooleanArgument(sender);

	// if a state was not provided, then return the current pin state
   if (enable == ERROR_COMMAND_ARGUMENT_MISSING){       
      enable = console_pipe.isEcho();
      response["id"] = "get";
      response["echo"] =  (enable) ?  "on" : "off";
   }    
   else if(enable < 0) {
      response["id"] = "error";
      response["errno"] = enable;
      response["arg"] = "state";
      response["msg"] = GetCommandArgumentError(enable);
   }
   else {      
      console_pipe.echo(enable); // update echo state on console pipe
      response["id"] = "set";
      response["echo"] =  (enable) ?  "on" : "off";
   }
   
   // output response
   serializeJson(doc, *sender->GetSerial());
   sender->GetSerial()->println();
}

// create ECHO command variable
SerialCommand EchoCommand = SerialCommand("echo", echo_command_execute);

#endif //PI4J_COMMAND_ECHO_H
