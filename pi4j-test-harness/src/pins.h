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

#ifndef PI4J_PINS_H
#define PI4J_PINS_H

// include platform libraries
#include <string>
#include <Arduino.h>
#include <ArduinoJson.h>

// ------------------------------------------------------------------------------------------------------------------------------
// DEFINE CONSTANTS
// ------------------------------------------------------------------------------------------------------------------------------

#define GPIO_MAX_PINS                  54

#define PIN_MODE_INPUT                 INPUT         // 0x0
#define PIN_MODE_OUTPUT                OUTPUT        // 0x1
#define PIN_MODE_INPUT_PULLUP          INPUT_PULLUP  // 0x2
#define PIN_MODE_OUTPUT_LOW            0x3
#define PIN_MODE_OUTPUT_HIGH           0x4
#define PIN_MODE_DISABLE               0xF


// ------------------------------------------------------------------------------------------------------------------------------
// DEFINE FUNTION PROTOTYPES
// ------------------------------------------------------------------------------------------------------------------------------
void loop();
void setup();
void info(Stream* out);
void reboot();
void inititalize();
void reset();

// ------------------------------------------------------------------------------------------------------------------------------
// DEFINE RUNTIME COMPONENTS
// ------------------------------------------------------------------------------------------------------------------------------

struct PinCache {
    bool restricted = false;
    bool enabled =  false;
    int8_t mode = -1;
    int16_t value = -1;
    uint16_t counter = 0;

    String modeString(){
        switch (mode)
        {
            case PIN_MODE_INPUT: return "input"; break;
            case PIN_MODE_INPUT_PULLUP: return "input_pullup"; break;
            case PIN_MODE_OUTPUT: return "output"; break;
            case PIN_MODE_DISABLE: return "disabled"; break;
            default: return "unknown"; break;
        }
    }
};

PinCache pins[GPIO_MAX_PINS];

// void PrintPinStatus2(Stream* out, int pin, String header, bool includeErroNo = true){
//     PinCache p = pins[pin];

//     // return current pin state
//     out->print("<");
//     out->print(header);
//     out->print("> PIN=");
//     out->print(pin);

//     if(p.restricted){
//         if(includeErroNo){
//             out->print("; ERRNO=");
//             out->print(ERROR_INVALID_PIN_RESTRICTED);
//         }
//         out->print("; ACCESS=RESTRICTED; MSG=Access to this pin is restricted.");
//     }
//     else if(p.enabled == false){
//         if(includeErroNo){
//             out->print("; ERRNO=");
//             out->print(ERROR_INVALID_PIN_DISABLED);
//         }
//         out->print("; ACCESS=DISABLED; MSG=This pin is not currently in use.");
//     } 
//     else {
//         out->print("; MODE=");
//         out->print(p.mode);
//         out->print("; VALUE=");
//         out->print(p.value);
//         out->print("; CHANGES=");
//         out->print(p.counter);
//     }
//     out->println();        
// }


// void PrintPinStatus(Stream* out, int pin, String header, bool includeErroNo = true){
//     PinCache p = pins[pin];

//     // Allocate a temporary JsonDocument
//     // Use arduinojson.org/v6/assistant to compute the capacity.
//     DynamicJsonDocument doc(1024);


//     doc["type"] = header.c_str();
//     doc["pin"] = pin;

//     if(p.restricted){
//         if(includeErroNo){
//             doc["errno"] = ERROR_INVALID_PIN_RESTRICTED;
//         }
//         doc["access"] = "restricted";
//         doc["msg"] = "Access to this pin is restricted.";
//     }
//     else if(p.enabled == false){
//         if(includeErroNo){
//             doc["errno"] = ERROR_INVALID_PIN_DISABLED;
//         }
//         doc["access"] = "disabled";
//         doc["msg"] = "This pin is not currently in use.";
//     } 
//     else {
//         doc["mode"] = p.modeString();
//         doc["value"] = p.value;
//         doc["changes"] = p.counter;
//     }

//     serializeJsonPretty(doc, *out);
//     out->println();        
// }



void SerializePin(JsonObject& json, int pin, bool includeErroNo = true){
    PinCache p = pins[pin];

    json["pin"] = pin;

    if(p.restricted){
        if(includeErroNo){
            json["errno"] = ERROR_INVALID_PIN_RESTRICTED;
        }
        json["access"] = "restricted";
        json["msg"] = "Access to this pin is restricted.";
    }
    else if(p.enabled == false){
        if(includeErroNo){
            json["errno"] = ERROR_INVALID_PIN_DISABLED;
        }
        json["access"] = "disabled";
        json["msg"] = "This pin is not currently in use.";
    } 
    else {
        json["mode"] = p.modeString();
        json["value"] = p.value;
        json["changes"] = p.counter;
    }
}


#endif //PI4J_PINS_H