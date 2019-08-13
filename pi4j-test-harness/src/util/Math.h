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

#ifndef PI4J_UTILITY_MATH_H
#define PI4J_UTILITY_MATH_H

#include <stdlib.h>
#include "itoa.h"

class Math 
{
   private:

   public:

    static char *ftoa(char *a, double f, int precision)
    {
        long p[] = {0,10,100,1000,10000,100000,1000000,10000000,100000000};
        
        char *ret = a;
        long number = (long)f;
        itoa(number, a, 10);
        while (*a != '\0') a++;
        *a++ = '.';
        long decimal = abs((long)((f - number) * p[precision]));
        itoa(decimal, a, 10);
        return ret;
    }
};

#endif //PI4J_UTILITY_MATH_H
