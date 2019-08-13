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

#include <SerialCommands.h>
#include <pi4j.h>
#include <Arduino.h>
#include "util/StringUtil.h"

#ifndef PI4J_COMMAND_ARGUMENT_PARSER_H
#define PI4J_COMMAND_ARGUMENT_PARSER_H

/**
 * GET I/O PIN VALUE FROM COMMANDS ARGUMENTS
 */
int GetCommandPinArgument(SerialCommands* sender){
	char* pin_str = sender->Next();
	if (pin_str == NULL){		
		return ERROR_COMMAND_ARGUMENT_MISSING; // -1 :: missing argument
	}

    // validate numeric string
    if(!StringUtil::isNumeric(pin_str)){
        return ERROR_COMMAND_ARGUMENT_INVALID; // -2 :: invalid argument
    }
	int pin = atoi(pin_str);

    // validate pin range
    if(pin >= GPIO_MAX_PINS){
        return ERROR_INVALID_PIN_OUT_OF_RANGE;
    }

    // validate restricted pins
    if(pin >= 0){
        if(pins[pin].restricted)
            return ERROR_INVALID_PIN_RESTRICTED;
    }

    return pin;
}

/**
 * GET I/O DIGITAL PIN STATE FROM COMMANDS ARGUMENTS
 */
int GetCommandDigitalStateArgument(SerialCommands* sender){

    // get state argument
	char* state_str = sender->Next();
	if (state_str == NULL){
		return ERROR_COMMAND_ARGUMENT_MISSING; // -1 :: missing argument
	}
    String state = String(state_str);
    if(state.equalsIgnoreCase("HIGH")){
        return 1;
    } else if(state.equalsIgnoreCase("HI")){
        return 1;
    } else if(state.equalsIgnoreCase("H")){
        return 1;
    } else if(state.equalsIgnoreCase("1")){
        return 1;
    } else if(state.equalsIgnoreCase("ON")){
        return 1;
    } else if(state.equalsIgnoreCase("LOW")){
        return 0;
    } else if(state.equalsIgnoreCase("LO")){
        return 0;
    } else if(state.equalsIgnoreCase("L")){
        return 0;
    } else if(state.equalsIgnoreCase("0")){
        return 0;
    } else if(state.equalsIgnoreCase("OFF")){
        return 0;
    } else {
		return ERROR_COMMAND_ARGUMENT_INVALID; // -2 :: invalid argument
    }
}


/**
 * GET "ON/OFF" or "ENABLE/DISABLE" FROM COMMANDS ARGUMENTS
 */
int GetCommandBooleanArgument(SerialCommands* sender){

    // get state argument
	char* state_str = sender->Next();
	if (state_str == NULL){
		return ERROR_COMMAND_ARGUMENT_MISSING; // -1 :: missing argument
	}
    String state = String(state_str);
    if(state.equalsIgnoreCase("TRUE")){
        return 1;
    } else if(state.equalsIgnoreCase("T")){
        return 1;
    } else if(state.equalsIgnoreCase("ENABLE")){
        return 1;
    } else if(state.equalsIgnoreCase("ON")){
        return 1;
    } else if(state.equalsIgnoreCase("YES")){
        return 0;
    } else if(state.equalsIgnoreCase("Y")){
        return 0;
    } else if(state.equalsIgnoreCase("1")){
        return 1;
    } else if(state.equalsIgnoreCase("FALSE")){
        return 0;
    } else if(state.equalsIgnoreCase("F")){
        return 0;
    } else if(state.equalsIgnoreCase("DISABLE")){
        return 0;
    } else if(state.equalsIgnoreCase("OFF")){
        return 0;
    } else if(state.equalsIgnoreCase("NO")){
        return 0;
    } else if(state.equalsIgnoreCase("N")){
        return 0;
    } else if(state.equalsIgnoreCase("0")){
        return 0;
    } else {
		return ERROR_COMMAND_ARGUMENT_INVALID; // -2 :: invalid argument
    }
}


/**
 * GET PIN MODE ARGUMENT
 */
int GetCommandPinModeArgument(SerialCommands* sender){

    // get mode argument
	char* mode_str = sender->Next();
	if (mode_str == NULL){
		return ERROR_COMMAND_ARGUMENT_MISSING; // -1 :: missing argument
	}
    String mode = String(mode_str);
    if(mode.equalsIgnoreCase("INPUT")){
        return INPUT;
    } else if(mode.equalsIgnoreCase("IN")){
        return INPUT;
    } else if(mode.equalsIgnoreCase("I")){
        return INPUT;
    } else if(mode.equalsIgnoreCase("INPUT_PULLUP")){
        return INPUT_PULLUP;
    } else if(mode.equalsIgnoreCase("IN_PULLUP")){
        return INPUT_PULLUP;
    } else if(mode.equalsIgnoreCase("IN_UP")){
        return INPUT_PULLUP;
    } else if(mode.equalsIgnoreCase("IUP")){
        return INPUT_PULLUP;
    } else if(mode.equalsIgnoreCase("IU")){
        return INPUT_PULLUP;
    } else if(mode.equalsIgnoreCase("OUTPUT")){
        return OUTPUT;
    } else if(mode.equalsIgnoreCase("OUT")){
        return OUTPUT;
    } else if(mode.equalsIgnoreCase("O")){
        return OUTPUT;
    } else if(mode.equalsIgnoreCase("HIGH")){
        return PIN_MODE_OUTPUT_HIGH;
    } else if(mode.equalsIgnoreCase("HI")){
        return PIN_MODE_OUTPUT_HIGH;
    } else if(mode.equalsIgnoreCase("H")){
        return PIN_MODE_OUTPUT_HIGH;
    } else if(mode.equalsIgnoreCase("ON")){
        return PIN_MODE_OUTPUT_HIGH;
    } else if(mode.equalsIgnoreCase("1")){
        return PIN_MODE_OUTPUT_HIGH;
    } else if(mode.equalsIgnoreCase("LOW")){
        return PIN_MODE_OUTPUT_LOW;
    } else if(mode.equalsIgnoreCase("LO")){
        return PIN_MODE_OUTPUT_LOW;
    } else if(mode.equalsIgnoreCase("L")){
        return PIN_MODE_OUTPUT_LOW;
    } else if(mode.equalsIgnoreCase("OFF")){
        return PIN_MODE_OUTPUT_LOW;
    } else if(mode.equalsIgnoreCase("0")){
        return PIN_MODE_OUTPUT_LOW;
    } else if(mode.equalsIgnoreCase("DISABLE")){
        return PIN_MODE_DISABLE;
    } else if(mode.equalsIgnoreCase("STOP")){
        return PIN_MODE_DISABLE;
    } else if(mode.equalsIgnoreCase("CANCEL")){
        return PIN_MODE_DISABLE;
    } else if(mode.equalsIgnoreCase("RESET")){
        return PIN_MODE_DISABLE;
    } else {
		return ERROR_COMMAND_ARGUMENT_INVALID; // -2 :: invalid argument
    }
}

String GetCommandArgumentError(int error){
    switch(error){
        case ERROR_COMMAND_ARGUMENT_MISSING : {return "MISSING ARGUMENT"; break;}
        case ERROR_COMMAND_ARGUMENT_INVALID : {return "INVALID ARGUMENT"; break;}
        case ERROR_INVALID_PIN_OUT_OF_RANGE : {return "INVALID PIN NUMBER; OUT OF ACCEPTED RANGE"; break;}
        case ERROR_INVALID_PIN_RESTRICTED   : {return "INVALID PIN NUMBER; RESTRICTED PIN"; break;}
        default: return "UNKNOWN ARGUMENT ERROR";
    }
}

#endif //PI4J_COMMAND_ARGUMENT_PARSER_H