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

#ifndef PI4J_UTILITY_STRING_H
#define PI4J_UTILITY_STRING_H

#include <string>
#include <strings.h>
#include <Arduino.h>
#include <iostream>
#include <cctype>
#include <cwctype>
#include <stdexcept>

class StringUtil 
{
   private:

   public:

    static bool isNumeric(String str){        
        for(byte i=0;i<str.length();i++){
            if(!isDigit(str.charAt(i))) return false;
        }
        return true; 
    }

    static std::string trim(const std::basic_string<char>& target){
        if( 0 != target.size() ) {  //if the size is 0
            std::string wspc (" \t\f\v\n\r");// These are the whitespaces (space, tab, CR, LF)
            //finding the last valid character        
            std::string::size_type posafter = target.find_last_not_of(wspc);
            //finding the first valid character
            std::string::size_type posbefore=target.find_first_not_of(wspc);

            if((-1 < (int)posafter) && (-1 < (int)posbefore)) //Just Wsp
            {
                std::string result;
                // Cut off the outside parts of found positions
                result = target.substr(posbefore,((posafter+1)-posbefore));
                return result;
            }
        }
        return target;
    }

    static void trimOn(std::basic_string<char>& target){
        if( 0 != target.size() ) {  //if the size is 0
            std::string wspc (" \t\f\v\n\r");// These are the whitespaces (space, tab, CR, LF)
            //finding the last valid character        
            std::string::size_type posafter = target.find_last_not_of(wspc);
            //finding the first valid character
            std::string::size_type posbefore=target.find_first_not_of(wspc);

            if((-1 < (int)posafter) && (-1 < (int)posbefore)) //Just Wsp
            {
                // Cut off the outside parts of found positions
                target = target.substr(posbefore,((posafter+1)-posbefore));
            }
        }
    }

    /**
     * FORMATTING HELPERS
     */
    static void printLeadingZeros(Stream& stream, uint16_t number, uint8_t digits=2) {
        if (digits >= 6 && number < 100000) stream.print("0"); // print a 0 before if the number is < than 100000
        if (digits >= 5 && number < 10000)  stream.print("0"); // print a 0 before if the number is < than 10000
        if (digits >= 4 && number < 1000)   stream.print("0"); // print a 0 before if the number is < than 1000
        if (digits >= 3 && number < 100)    stream.print("0"); // print a 0 before if the number is < than 100
        if (digits >= 2 && number < 10)     stream.print("0"); // print a 0 before if the number is < than 10
        stream.print(number);
    }


    static std::string toUpper(const std::basic_string<char>& s) {
        std::string modified = s;
        toUpperOn(modified);
        return modified;
    }

    static std::string toLower(const std::basic_string<char>& s) {
        std::string modified = s;
        toLowerOn(modified);
        return modified;
    }

    static void toUpperOn(std::basic_string<char>& s) {
        for (std::basic_string<char>::iterator p = s.begin();
                p != s.end(); ++p) {
            *p = toupper(*p); // toupper is for char
        }
    }

    static void toLowerOn(std::basic_string<char>& s) {
        for (std::basic_string<char>::iterator p = s.begin();
                p != s.end(); ++p) {
            *p = tolower(*p);
        }
    }

    static bool endsWith(const char *str, const char *suffix) {
        if (!str || !suffix)
            return 0;
        size_t lenstr = strlen(str);
        size_t lensuffix = strlen(suffix);
        if (lensuffix >  lenstr)
            return 0;
        return strncmp(str + lenstr - lensuffix, suffix, lensuffix) == 0;
    }   

    static bool endsWithIgnoreCase(const char *str, const char *suffix) {
        if (!str || !suffix)
            return 0;
        size_t lenstr = strlen(str);
        size_t lensuffix = strlen(suffix);

        // if no suffix provided, then always return success
        if(lensuffix <= 0){
            return 1; 
        }
        
        // if the length of the suffix exceeds the length of the target 
        // data, then always return a failure
        if (lensuffix >  lenstr)
            return 0;  
        return strncasecmp(str + lenstr - lensuffix, suffix, lensuffix) == 0;
    }   

    static std::string padRight(std::string const& str, size_t s, const char padchar = ' '){
        if ( str.size() < s )
            return str + std::string(s-str.size(), padchar);
        else
            return str;
    }

    static std::string padLeft(std::string const& str, size_t s, const char padchar = ' '){
        if ( str.size() < s )
            return std::string(s-str.size(), padchar) + str;
        else
            return str;
    }



};

#endif //PI4J_UTILITY_STRING_H
