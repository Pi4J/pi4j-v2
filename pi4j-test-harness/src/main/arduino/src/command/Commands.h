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

#ifndef PI4J_COMMANDS_H
#define PI4J_COMMANDS_H

#include "main.h"
#include <ArduinoJson.h>
#include "CommandsArgumentParser.h"
#include "EchoCommand.h"
#include "FrequencyCommand.h"
#include "I2cCommand.h"
#include "InfoCommand.h"
#include "InvalidCommand.h"
#include "PinCommand.h"
#include "PinsCommand.h"
#include "RebootCommand.h"
#include "ResetCommand.h"
#include "SerialEchoCommand.h"


/**
 * ADD INTERACTIVE COMMAND TO THE SERIAL COMMAND PROCESSOR
 */
void AddInteractiveCommands(SerialCommands& processor){
    
    // add default command handler for unrecognized commands
    processor.SetDefaultHandler(invalid_command_handler);

    // add Pi4J interactive commands
    processor.AddCommand(&EchoCommand);
    processor.AddCommand(&FrequencyCommand);
    processor.AddCommand(&FrequencyShortCommand);
    processor.AddCommand(&RebootCommand);
    processor.AddCommand(&ResetCommand);
    processor.AddCommand(&I2cCommand);
    processor.AddCommand(&InfoCommand);
    processor.AddCommand(&InfoCommandKey);
    processor.AddCommand(&PinCommand);
    processor.AddCommand(&PinsCommand);
    processor.AddCommand(&PinShortCommand);
    processor.AddCommand(&SerialEchoCommand);
}

#endif // PI4J_COMMANDS_H
