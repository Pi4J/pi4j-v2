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

#include "Console.h"

void Console::init(Stream* stream){
    if(stream == nullptr) return;
    this->_stream = stream; 
}

int Console::available( void ) { 
    if(this->_stream == nullptr) return -1;
    return this->_stream->available(); 
}
void Console::flush( void ) { 
    if(this->_stream == nullptr) return;
    this->_stream->flush(); 
}
int Console::peek( void ) { 
    if(this->_stream == nullptr) return -1;
    return this->_stream->peek(); 
}
int Console::read( void ){ 
    if(this->_stream == nullptr) return -1;
    return this->_stream->read(); 
}
size_t Console::write( uint8_t data ){ 
    if(this->_stream == nullptr) return -1;
    return this->_stream->write(data); 
}

size_t Console::print(const std::string &s){
    return this->_stream->print(s.c_str());
}
size_t Console::print(const __FlashStringHelper *fsh){
    return Stream::print(fsh);
}  
size_t Console::print(const String &str){
    return Stream::print(str);
}  
size_t Console::print(const char str[]){
    return Stream::print(str);
}  
size_t Console::print(char b){
    return Stream::print(b);
}  
size_t Console::print(unsigned char c, int base){
    return Stream::print(c, base);
}  
size_t Console::print(int n, int base){
    return Stream::print(n, base);
}  
size_t Console::print(unsigned int n, int base){
    return Stream::print(n, base);
}  
size_t Console::print(long n, int base){
    return Stream::print(n, base);
}  
size_t Console::print(unsigned long n, int base){
    return Stream::print(n, base);
}  
size_t Console::print(double n, int digits){
    return Stream::print(n, digits);
}  
size_t Console::print(const Printable& x){
    return Stream::print(x);
}  

size_t Console::println(const std::string &s){
    return this->_stream->println(s.c_str());
}
size_t Console::println(const __FlashStringHelper *fsh){
    return Stream::println(fsh);
}  
size_t Console::println(const String &str){
    return Stream::println(str);
}  
size_t Console::println(const char str[]){
    return Stream::println(str);
}  
size_t Console::println(char b){
    return Stream::println(b);
}  
size_t Console::println(unsigned char c, int base){
    return Stream::println(c, base);
}  
size_t Console::println(int n, int base){
    return Stream::println(n, base);
}  
size_t Console::println(unsigned int n, int base){
    return Stream::println(n, base);
}  
size_t Console::println(long n, int base){
    return Stream::println(n, base);
}  
size_t Console::println(unsigned long n, int base){
    return Stream::println(n, base);
}  
size_t Console::println(double n, int digits){
    return Stream::println(n, digits);
}  
size_t Console::println(const Printable& x){
    return Stream::println(x);
}  
size_t Console::println(void){
    return Stream::println();
}  

size_t Console::printHex(const char *data, const size_t length){    
    size_t size = 0;
    size+=this->print("0x"); 
    for (size_t i=0; i<length; i++) { 
        if (data[i]<0x10) { size+=this->print("0"); } 
        size+=this->print(data[i],HEX); 
        size+=this->print(" "); 
    }
    return size;
}
size_t Console::printHex(const uint8_t *data, const size_t length){
    size_t size = 0;
    size+=this->print("0x"); 
    for (size_t i=0; i<length; i++) { 
        if (data[i]<0x10) { size+=this->print("0"); } 
        size+=this->print(data[i],HEX); 
        size+=this->print(" "); 
    }
    return size;
}
size_t Console::printHex(const uint16_t *data, const size_t length){
    size_t size = 0;

    size+=this->print("0x"); 
    for (size_t i=0; i<length; i++)
    { 
        uint8_t MSB=byte(data[i]>>8);
        uint8_t LSB=byte(data[i]);
        
        if (MSB<0x10) {
            size+=this->print("0");
        } 
        size+=this->print(MSB,HEX); 
        size+=this->print(" "); 
        if (LSB<0x10) {
            size+=this->print("0");
        } 
        size+=this->print(LSB,HEX); 
        size+=this->print(" "); 
    }
    return size;
}
size_t Console::printHex(const std::string data, const size_t length){
    return this->printHex(data.c_str(), length);
}
size_t Console::printHex(const std::string data){
    return this->printHex(data.c_str(), data.length());
}
size_t Console::printHex(const String data, const size_t length){
    return this->printHex(data.c_str(), length);
}
size_t Console::printHex(const String data){
    return this->printHex(data.c_str(), data.length());
}


Console console;

