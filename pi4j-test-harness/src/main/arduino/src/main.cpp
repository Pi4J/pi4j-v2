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

// include main header
#include "main.h"
#include "pins.h"
#include <Wire.h>


// ------------------------------------------------------------------------------------------------------------------------------
// INTERACTIVE COMMAND PROCESSOR AND COMMANDS
// ------------------------------------------------------------------------------------------------------------------------------

// create data buffer and command processor
char serial_command_buffer_[32];
//SerialCommands processor(&console_pipe, serial_command_buffer_, sizeof(serial_command_buffer_), "\r\n", " ");
SerialCommands processor(&CONSOLE_INTERFACE, serial_command_buffer_, sizeof(serial_command_buffer_), "\r\n", " ");

// include Pi4J interactive commands
#include "command/Commands.h"



/**
 * FIRMWARE STARTUP
 */
void setup() {

    // define default UART baud rate for serial interfaces
    // @see: https://www.arduino.cc/en/serial/begin
    CONSOLE_INTERFACE.begin(CONSOLE_BAUD_RATE);
    //DEBUG_INTERFACE.begin(DEBUG_BAUD_RATE);

    // initialize the interactive diagnostics console using the piped console serial ports
    //console.init(&console_pipe);
    console.init(&CONSOLE_INTERFACE);

    // this is an artificial delay to see this data in the debug console when using Arduino/PlatformIO IDE
    for(int d = FIRMWARE_BOOT_DELAY; d > 0; d--){
        console.print("... Pi4J test harness firmware will start in ");
        console.print(d);
        console.println(" second(s).");
        delay(1000);
    }

    // print firmware startup banner and program information
    info(&console);

    // pins zero and one are reserved for USB programming port
    pins[0].restricted = true;
    pins[1].restricted = true;

    // initialize firmware
    inititalize();
}

/**
 * INITIALIZE FIRMWARE
 */
void inititalize(){
    // configure interactive serial commands
    AddInteractiveCommands(processor);

    // display ready/running message
    DynamicJsonDocument doc(1024);
    JsonObject response = doc.to<JsonObject>();
    response["id"] = "ready";
    serializeJson(doc, console);
    console.println();

    // reset I2C cache
    i2cCache.reset();

    // // initialize i2c as slave
    // Wire.begin(I2C_SLAVE_ADDRESS);

    // // define callbacks for i2c communication
    // Wire.onReceive(receiveI2CData);
    // Wire.onRequest(sendI2CData);
    // i2cCache.wire = &Wire;
}

void reset(){

    DynamicJsonDocument doc(1024*GPIO_MAX_PINS);
    JsonObject response = doc.to<JsonObject>();
    response["id"] = "reset";
    JsonArray pinscontainer = response.createNestedArray("pins");

    // terminate all I2C buses
    Wire.end();
    Wire1.end();

    // terminate all SPI channels
    SPI.end(4);
    SPI.end(10);

    // termiante all SERIAL communication pins
    if(serialEcho != nullptr){
        serialEcho->end();
    }

    // reset pins states
    int total, inputs, outputs;
    total = inputs = outputs = 0;

    for(int p = 0; p < GPIO_MAX_PINS; p++){
        bool isEnabled = pins[p].enabled;
        if(isEnabled){
            total++;
            if(pins[p].mode == OUTPUT)       outputs++;
            if(pins[p].mode == INPUT)        inputs++;
            if(pins[p].mode == INPUT_PULLUP) inputs++;
            JsonObject pincontainer = pinscontainer.createNestedObject();
            SerializePin(pincontainer, p, false);
        }
        pins[p].enabled = false;
        pins[p].value = -1;
        pins[p].mode = -1;
        pins[p].counter = 0;

        // reset actual hardware pins to default mode
        if(!pins[p].restricted) pinMode(p, INPUT);
    }

    // reset I2C cache
    i2cCache.reset();

    // include summary totals
    response["total"] = total;
    response["inputs"] = inputs;
    response["outputs"] = outputs;

    serializeJson(doc, console);
    console.println();
}


void processSpi() {
    // only process if SPI is enabled
    if(!spi.enabled) return;

    // are we ready to receive data?
    if((SPI0->SPI_SR & SPI_SR_RDRF) != 0){
        // recevie data
        spi.rxCounter++;
        spi.rx = SPI0->SPI_RDR;
        spi.tx = spi.rx;
        // Serial.print("[RX]- 0x");
        // Serial.print(spi.rx);
        // Serial.print(" - ");        
        // Serial.println(spi.rxCounter);
        spi.pending = true;
    }

    // are we ready to transmit data?
    if((SPI0->SPI_SR & SPI_SR_TDRE) != 0 && spi.pending){
        // transmit data
        spi.txCounter++;
        SPI0->SPI_TDR = spi.tx;
        // Serial.print("[TX]- 0x");
        // Serial.print(spi.tx);   
        // Serial.print(" - ");        
        // Serial.println(spi.txCounter);
        spi.pending = false;
    }
}

/**
 * SERVICE LOOP
 */
void loop() {
    processor.ReadSerial();
    //console_pipe.loop();

    // for(int p = 0; p < GPIO_MAX_PINS; p++){
    //     if(pins[p].enabled &&
    //       !pins[p].restricted &&
    //       (pins[p].mode == INPUT || pins[p].mode == INPUT_PULLUP)){
    //         byte v = digitalRead(p);
    //         if(v != pins[p].value){

    //             // update pin cache
    //             pins[p].value = v;
    //             pins[p].counter++;

    //             // print pin status
    //             DynamicJsonDocument doc(1024);
    //             JsonObject response = doc.to<JsonObject>();
    //             response["id"] = "change";
    //             SerializePin(response, p);
    //             serializeJson(doc, console);
    //             console.println();
    //         }
    //     }    
    // }

    // SERIAL ECHO ROUTINE
    if(serialEcho != nullptr){
        if (serialEcho->available()){
            int byt = serialEcho->read();
            //Serial.print("[SERIAL-ECHO] ");
            //Serial.println(byt);
            serialEcho->write(byt);  // ECHO DATA BACK TO CALLER
        }
    }

    // SPI ECHO ROUTINE
    processSpi();
}

/**
 * SYSTEM INFO
 */
void info(Stream* out){
    DynamicJsonDocument doc(1024);
    JsonObject response = doc.to<JsonObject>();
    response["id"] = "info";
    response["name"] = FIRMWARE_NAME;
    response["version"] = FIRMWARE_VERSION_STRING;
    response["date"] = FIRMWARE_DATE_STRING;
    response["copyright"] = COPYRIGHT;
    serializeJson(doc, *out);
    out->println();
}

/**
 * HARD REBOOT SYSTEM (in one second)
 */
void reboot() {
    console.println();
    console.println(F("****************************************************"));
    console.println(F("REBOOT!"));
    console.println(F("****************************************************"));
    console.println(F("The system has started a REBOOT and will be"));
    console.println(F("restarted in one second."));
    console.println(F("****************************************************"));
    console.println();
    NVIC_SystemReset();
}


// callback for sending data
void sendI2CData(){
    uint8_t address = i2cCache.address;
    uint8_t length = i2cCache.reg[address].length;
    //  console.print("--> I2C <REGISTER:");
    //  console.print(address);
    //  console.print("> SEND [");
    //  console.print(length);
    //  console.println("] BYTES");

    // for(int n = 0; n < length; n++){
    //     console.print(" --> ");
    //     console.println(i2cCache.reg[address].data[n]);
    // }

    i2cCache.wire->write(i2cCache.reg[address].data, length);
}

// callback for sending data
void sendI2CDataRaw(){
    // console.print("--> I2C <RAW> SEND [");
    // console.print(i2cCache.length);
    // console.println("] BYTES");
    // for(int n = 0; n < i2cCache.length; n++){
    //     console.print(" --> ");
    //     console.println(i2cCache.buffer[n]);
    // }

    i2cCache.wire->write(i2cCache.buffer, sizeof(i2cCache.buffer));
}

// -------------------------------------------
// WRITING RAW BYTE VALUES
// -------------------------------------------
// callback for received data
void receiveI2CDataRaw(int byteCount){
    // console.print("<-- I2C <RAW> RECEIVE [");
    // console.print(byteCount);
    // console.println("] BYTES");
    // if(byteCount == 0) return;

    // clear buffer store
    memset(i2cCache.buffer, 0, sizeof i2cCache.buffer);

    // read all available bytes from the I2C bus
    byteCount = i2cCache.wire->readBytes(i2cCache.buffer, byteCount);
    i2cCache.length = byteCount;
}

// -------------------------------------------
// WRITING REGISTERS
// -------------------------------------------
// callback for received data
void receiveI2CData(int byteCount){
    if(byteCount == 0) return; // ignore any zero byte callbacks
    uint8_t address = i2cCache.wire->read();
    uint8_t length = byteCount - 1; // substract for address byte
    // console.print("--> I2C <REGISTER:");
    // console.print(address);
    // console.print("> RECEIVE [");
    // console.print(length);
    // console.print("] BYTES");
    // if(length == 0){
    //     console.print("; GET");
    // } else {
    //     console.print("; SET");
    // }
    // console.println();

    // update active register address
    i2cCache.address = address;

    // if a data payload is included, then we need to cache
    // the value in the  register's data store
    if(length > 0){

        // clear register data store
        memset(i2cCache.reg[address].data, 0, sizeof i2cCache.reg[address].data);

        // update register data length
        i2cCache.reg[address].length = length;

        // read all available bytes from the I2C bus into the register data store
        i2cCache.wire->readBytes(i2cCache.reg[address].data, length);

        // for(int n = 0; n < length; n++){
        //     console.print(" <-- ");
        //     console.println(i2cCache.reg[address].data[n]);
        // }

    }

    //     // display ready/running message
    //     DynamicJsonDocument doc(512);
    //     JsonObject response = doc.to<JsonObject>();
    //     response["id"] = "i2c";
    //     response["value"] = i2cValue;
    //     serializeJson(doc, console);
    //     console.println();
}

