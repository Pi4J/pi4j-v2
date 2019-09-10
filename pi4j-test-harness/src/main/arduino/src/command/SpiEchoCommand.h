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

#ifndef PI4J_COMMAND_SPI_H
#define PI4J_COMMAND_SPI_H

#include <string>
#include <Arduino.h>
#include <SerialCommands.h>
#include <ArduinoJson.h>
#include "CommandsArgumentParser.h"
#include "main.h"
#include <SPI.h>

void spi_slave_begin(uint8_t pin) {
  SPI.begin(pin);
  REG_SPI0_CR = SPI_CR_SWRST;       // reset SPI
  REG_SPI0_CR = SPI_CR_SPIEN;       // enable SPI
  REG_SPI0_MR = SPI_MR_MODFDIS;     // slave and no modefault

  //REG_SPI0_CR   = 0x00000001;   // SPI Enable 
  //REG_SPI0_MR   = 0x00000000;   // Slave-Mode, Fixed Peripheral Select, 
                                // Chip  Select directly connected
                                // Mode Fault detection enabled, 
                                // Loopback disabled  
  REG_SPI0_WPMR = 0x00000000;       // Write Protection disabled
  REG_SPI0_CSR = SPI_MODE0;         // DLYBCT=0, DLYBS=0, SCBR=0, 8 bit transfer
}

// create SPI command invocation handler
void spi_command_execute(SerialCommands* sender){
    DynamicJsonDocument doc(256);
    JsonObject response = doc.to<JsonObject>();

   // get CHANNEL bus
   int channel = GetCommandSpiChannelArgument(sender);

   // handle channel argument errors
   if(channel < 0) {
      response["id"] = "error";
      response["errno"] = channel;
      response["arg"] = "channel";
      response["msg"] = GetCommandArgumentError(channel);
   }

   else{
      // end any existing SPI echo
      if(spi.enabled && spi.select != 0){
         SPI.end(spi.select);
      }

      // reset SPI parameters
      spi.bus = 0;
      spi.select = 0;
      spi.rx = 0;
      spi.tx = 0;
      spi.enabled = false;

      // setup which SPI CS pin to enable
      spi.bus = 0;
      spi.select = channel;
      spi.enabled = true;
      spi_slave_begin(channel);

      response["id"] = "spi";
      response["bus"] = spi.bus;
      response["select"] = spi.select;
      response["channel"] = channel;
      response["enabled"] = spi.enabled;
   }

   // output response
   serializeJson(doc, *sender->GetSerial());
   sender->GetSerial()->println();
}

// create SPI command variable
SerialCommand SpiEchoCommand = SerialCommand("spi", spi_command_execute);

#endif //PI4J_COMMAND_SPI_H
