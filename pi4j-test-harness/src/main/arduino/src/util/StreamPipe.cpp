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

#include "StreamPipe.h"

// internal stream buffer
LoopbackStream buffer;

/*
* DEFAULT CONSTRUCTOR
*/   
StreamPipe::StreamPipe(Stream* source, Stream* pipe, bool echo){
    if(source == nullptr) return;
    if(pipe == nullptr) return;
    this->_source = source; 
    this->_pipe = pipe; 
    this->_echo = echo;
}

int StreamPipe::available( void ) { 
    return buffer.available();
}
void StreamPipe::flush( void ) { 
    buffer.flush();
}
int StreamPipe::peek( void ) { 
    return buffer.peek();
}
int StreamPipe::read( void ){ 
    return buffer.read();
}
size_t StreamPipe::write( uint8_t data ){ 
    // write data to source stream
    if(this->_source != nullptr)
        this->_source->write(data); 
    
    // write data to piped stream
    if(this->_pipe != nullptr)
        this->_pipe->write(data); 

    // return length of data written
    return 1;
}

size_t StreamPipe::print(const std::string &s){    
    // write data to source stream
    if(this->_source != nullptr)
        this->_source->print(s.c_str());

    // write data to piped stream
    if(this->_pipe != nullptr)
        this->_pipe->print(s.c_str());

    // return length of data written
    return s.length();
}
size_t StreamPipe::print(const __FlashStringHelper *fsh){
    return Stream::print(fsh);
}  
size_t StreamPipe::print(const String &str){
    return Stream::print(str);
}  
size_t StreamPipe::print(const char str[]){
    return Stream::print(str);
}  
size_t StreamPipe::print(char b){
    return Stream::print(b);
}  
size_t StreamPipe::print(unsigned char c, int base){
    return Stream::print(c, base);
}  
size_t StreamPipe::print(int n, int base){
    return Stream::print(n, base);
}  
size_t StreamPipe::print(unsigned int n, int base){
    return Stream::print(n, base);
}  
size_t StreamPipe::print(long n, int base){
    return Stream::print(n, base);
}  
size_t StreamPipe::print(unsigned long n, int base){
    return Stream::print(n, base);
}  
size_t StreamPipe::print(double n, int digits){
    return Stream::print(n, digits);
}  
size_t StreamPipe::print(const Printable& x){
    return Stream::print(x);
}  

size_t StreamPipe::println(const std::string &s){
    // write data to source stream
    if(this->_source != nullptr)
        this->_source->println(s.c_str());

    // write data to piped stream
    if(this->_pipe != nullptr)
        this->_pipe->println(s.c_str());

    // return length of data written
    return s.length();    
}
size_t StreamPipe::println(const __FlashStringHelper *fsh){
    return Stream::println(fsh);
}  
size_t StreamPipe::println(const String &str){
    return Stream::println(str);
}  
size_t StreamPipe::println(const char str[]){
    return Stream::println(str);
}  
size_t StreamPipe::println(char b){
    return Stream::println(b);
}  
size_t StreamPipe::println(unsigned char c, int base){
    return Stream::println(c, base);
}  
size_t StreamPipe::println(int n, int base){
    return Stream::println(n, base);
}  
size_t StreamPipe::println(unsigned int n, int base){
    return Stream::println(n, base);
}  
size_t StreamPipe::println(long n, int base){
    return Stream::println(n, base);
}  
size_t StreamPipe::println(unsigned long n, int base){
    return Stream::println(n, base);
}  
size_t StreamPipe::println(double n, int digits){
    return Stream::println(n, digits);
}  
size_t StreamPipe::println(const Printable& x){
    return Stream::println(x);
}  
size_t StreamPipe::println(void){
    return Stream::println();
}  

void StreamPipe::echo(bool enabled){
    this->_echo = enabled;
}

bool StreamPipe::isEcho(){
    return this->_echo;
}

void StreamPipe::loop(){

    // check source stream for data, if data is available, 
    // then read it and copy it in to the buffered stream
    if(this->_source != nullptr){
        while (this->_source->available()) {
            char b = this->_source->read();
            buffer.write(b);
            
            // should we echo back to the console?
            if(_echo){
                this->_source->write(b);
            }
        }
    }

    // check pipe stream for data, if data is available, 
    // then read it and copy it in to the buffered stream
    if(this->_pipe != nullptr){
        while (this->_pipe->available()) {
            char b = this->_pipe->read();
            buffer.write(b);

            // should we echo back to the console?
            if(_echo){
                this->_pipe->write(b);
            }
        }
    }
}
