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

#ifndef PI4J_STREAM_MUX_H
#define PI4J_STREAM_MUX_H

#include <string>
#include <Arduino.h>
#include "LoopbackStream.h"

class StreamPipe : public Stream
{
   protected:
    Stream* _source = nullptr;
    Stream* _pipe = nullptr;
    bool _echo = false;

   public:

    /*
     * DEFAULT CONSTRUCTOR
     */   
     StreamPipe(Stream* source, Stream* tap, bool echo=false);
     
     void loop( void );

     int available( void );
     void flush( void );
     int peek( void );
     int read( void );
     size_t write( uint8_t data );

     size_t print(const __FlashStringHelper *);
     size_t print(const String &s);
     size_t print(const char[]);
     size_t print(char);
     size_t print(unsigned char, int = DEC);
     size_t print(int, int = DEC);
     size_t print(unsigned int, int = DEC);
     size_t print(long, int = DEC);
     size_t print(unsigned long, int = DEC);
     size_t print(double, int = 2);
     size_t print(const Printable&);

     // add 'print' method for std::string
     size_t print(const std::string &s);

     size_t println(const __FlashStringHelper *);
     size_t println(const String &s);
     size_t println(const char[]);
     size_t println(char);
     size_t println(unsigned char, int = DEC);
     size_t println(int, int = DEC);
     size_t println(unsigned int, int = DEC);
     size_t println(long, int = DEC);
     size_t println(unsigned long, int = DEC);
     size_t println(double, int = 2);
     size_t println(const Printable&);
     size_t println(void);     

     // add 'println' method for std::string
     size_t println(const std::string &s);

     void echo(bool enabled);
     void echoOn() { echo(true); }
     void echoOff() { echo(false); }     
     bool isEcho();
};

#endif //PI4J_STREAM_MUX_H
