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

#ifndef PI4J_COMMAND_PIN_H
#define PI4J_COMMAND_PIN_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>
#include "CommandsArgumentParser.h"
#include <ArduinoJson.h>

// create PIN command invocation handler
void pin_command_execute(SerialCommands* sender){
    DynamicJsonDocument doc(1024);
    JsonObject response = doc.to<JsonObject>();

    // get <PIN> argument
    int pin = GetCommandPinArgument(sender);

	// check for missing or invalid <PIN> argument
    if (pin < 0){
        response["id"] = "error";
        response["errno"] = pin;
        response["arg"] = "pin";
        response["msg"] = GetCommandArgumentError(pin);
        serializeJson(doc, *sender->GetSerial());
        sender->GetSerial()->println();
        return;
	}

    // get pin <MODE> argument
    int mode = GetCommandPinModeArgument(sender);

    // ---------------------------------------------------------------------
    // GET PIN INFO
    // ---------------------------------------------------------------------
	// if a mode was not provided, then return the current pin state
    if (mode == ERROR_COMMAND_ARGUMENT_MISSING){

        // check to see if this pin is enabled
        if(!pins[pin].enabled){
            response["id"] = "error";
            SerializePin(response, pin);
            serializeJson(doc, *sender->GetSerial());
            sender->GetSerial()->println();
            return;
        }

        // get current pin state
        pins[pin].value = digitalRead(pin);

        // return current pin state
        response["id"] = "get";
        SerializePin(response, pin);
        serializeJson(doc, *sender->GetSerial());
        sender->GetSerial()->println();
        return;
    }

    // ---------------------------------------------------------------------
    // SET PIN ERROR
    // ---------------------------------------------------------------------
    // any other argument errors need to be notified
    if (mode < 0){
        response["id"] = "error";
        response["errno"] = mode;
        response["pin"] = pin;
        response["arg"] = "mode";
        response["msg"] = GetCommandArgumentError(mode);
        serializeJson(doc, *sender->GetSerial());
        sender->GetSerial()->println();
        return;

	}

    // ---------------------------------------------------------------------
    // DISABLE PIN
    // ---------------------------------------------------------------------
    // disable/reset pin tracking
    if (mode == PIN_MODE_DISABLE){
        
        // disable and reset pin state
        pins[pin].enabled = false;
        pins[pin].counter = 0;
        pins[pin].value   = -1;
        pins[pin].mode    = -1;

        // reset actual hardware pins to default mode
        pinMode(pin, INPUT);

        // return current pin state
        response["id"] = "set";
        SerializePin(response, pin, false);
        serializeJson(doc, *sender->GetSerial());
        sender->GetSerial()->println();
        return;
	}

    // ---------------------------------------------------------------------
    // ENABLE OUTPUT PIN (and set state to HIGH)
    // ---------------------------------------------------------------------
    // handle pin output HIGH
    else if (mode == PIN_MODE_OUTPUT_HIGH){

        // setup actual GPIO pin and initial state
        pinMode(pin, OUTPUT);
        digitalWrite(pin, HIGH);

        // update pin cache 
        pins[pin].mode  = OUTPUT;
        pins[pin].value = HIGH;
    }

    // ---------------------------------------------------------------------
    // ENABLE OUTPUT PIN (and set state to LOW)
    // ---------------------------------------------------------------------
    // handle pin output LOW
    else if (mode == PIN_MODE_OUTPUT_LOW){

        // setup actual GPIO pin and initial state
        pinMode(pin, OUTPUT);
        digitalWrite(pin, LOW);

        // update pin cache
        pins[pin].mode  = OUTPUT;
        pins[pin].value = LOW;
    }

    // ---------------------------------------------------------------------
    // ENABLE OUTPUT PIN (and set state to use provided value)
    // ---------------------------------------------------------------------
    // handle pin output
    else if (mode == OUTPUT){

        // get pin <VALUE> argument
        int value = GetCommandDigitalStateArgument(sender);

        // if a state was not provided, then return the current pin state
        if (value < 0){
            response["id"] = "error";
            response["errno"] = value;            
            response["pin"] = pin;
            response["mode"] = "output";
            response["arg"] = "value";
            response["msg"] = GetCommandArgumentError(value);
            serializeJson(doc, *sender->GetSerial());
            sender->GetSerial()->println();
            return;
        }

        // setup actual GPIO pin
        pinMode(pin, OUTPUT);
        digitalWrite(pin, value);

        // update pin cache mode
        pins[pin].mode = OUTPUT;
    }

    // ---------------------------------------------------------------------
    // ENABLE INPUT PIN (pull-down)
    // ---------------------------------------------------------------------
    // handle pin input
    else if (mode == INPUT){

        // setup actual GPIO pin
        pinMode(pin, INPUT);

        // update pin cache mode
        pins[pin].mode = INPUT;

    }

    // ---------------------------------------------------------------------
    // ENABLE INPUT PIN (pull-up)
    // ---------------------------------------------------------------------
    // handle pin input
    else if (mode == INPUT_PULLUP){

        // setup actual GPIO pin
        pinMode(pin, INPUT_PULLUP);

        // update pin cache mode
        pins[pin].mode = INPUT_PULLUP;

    }

    // enable and sync pin state
    pins[pin].enabled = true;
    pins[pin].value   = digitalRead(pin);
    pins[pin].counter = 0;

    // output status
    response["id"] = "set";
    SerializePin(response, pin);
    serializeJson(doc, *sender->GetSerial());
    sender->GetSerial()->println();
}

// create DOUT command variable
SerialCommand PinCommand = SerialCommand("pin", pin_command_execute);
SerialCommand PinShortCommand = SerialCommand("p", pin_command_execute);

#endif //PI4J_COMMAND_PIN_H

