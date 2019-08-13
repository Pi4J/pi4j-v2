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


#ifndef PI4J_CONSOLE_H
#define PI4J_CONSOLE_H

#define CONSOLE_NAME_LEFT '['
#define CONSOLE_NAME_RIGHT "] "

//#define __PRINTLN(a) console.println()
#define __PRINTNAME(a) console.printTs(CONSOLE_NAME_LEFT), console.print(a), console.print(CONSOLE_NAME_RIGHT)
#define __PRINT4(a, b, c, d) __PRINTNAME(a), console.print(b), console.print(' '), console.print(c), console.print(' '), console.print(d)
#define __PRINT3(a, b, c) __PRINTNAME(a), console.print(b), console.print(' '), console.print(c)
#define __PRINT2(a, b) __PRINTNAME(a), console.print(b)
#define _GET_OVERRIDE(_1, _2, _3, _4, NAME, ...) NAME
#define CPRINT(...) _GET_OVERRIDE(__VA_ARGS__, __PRINT4, __PRINT3, __PRINT2)(__VA_ARGS__)
#define CPRINTLN(...) _GET_OVERRIDE(__VA_ARGS__, __PRINT4, __PRINT3, __PRINT2)(__VA_ARGS__), console.println()

// #define CPRINT(...) 
// #define CPRINTLN(...) 

#include <string>
#include <map>
#include <Arduino.h>
#include "ConsoleCommand.h"

class Console : public Stream
{
   protected:
    Stream* _stream = nullptr;
    std::map<char,ConsoleCommand> _commands;
    void (*_promptCommandInvokeCallback)();
    bool _promptPending;

   public:
     void init(Stream* stream);

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
     
     // print buffer as hexadecimal output
     size_t printHex(const char *data, const size_t length);
     size_t printHex(const uint8_t *data, const size_t length);
     size_t printHex(const uint16_t *data, const size_t length);
     size_t printHex(const std::string data, const size_t length);
     size_t printHex(const String data, const size_t length);
     size_t printHex(const std::string data);
     size_t printHex(const String data);

     // add 'println' method for std::string
     size_t println(const std::string &s);
};


extern Console console;

#endif //PI4J_CONSOLE_H
