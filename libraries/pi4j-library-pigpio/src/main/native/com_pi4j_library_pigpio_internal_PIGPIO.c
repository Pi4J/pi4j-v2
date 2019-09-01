/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  com_pi4j_library_pigpio_internal_PIGPIO.c
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
#include <jni.h>
#include <pigpio.h>
#include "com_pi4j_library_pigpio_internal_PIGPIO.h"

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioVersion
  (JNIEnv *env, jclass class)
{
    return gpioVersion();
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioHardwareRevision
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioHardwareRevision
  (JNIEnv *env, jclass class)
{
    return gpioHardwareRevision();
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioInitialise
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioInitialise
  (JNIEnv *env, jclass class)
{
    return gpioInitialise();
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTerminate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTerminate
  (JNIEnv *env, jclass class)
{
    return gpioTerminate();
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetMode
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetMode
  (JNIEnv *env, jclass class, jint gpio, jint mode)
{
    return gpioSetMode((unsigned)gpio, (unsigned)mode);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetMode
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetMode
  (JNIEnv *env, jclass class, jint gpio)
{
    return gpioGetMode((unsigned)gpio);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPullUpDown
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPullUpDown
  (JNIEnv *env, jclass class, jint gpio, jint pud)
{
    return gpioSetPullUpDown((unsigned)gpio, (unsigned)pud);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioRead
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioRead
  (JNIEnv *env, jclass class, jint gpio)
{
    return gpioRead((unsigned)gpio);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioWrite
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioWrite
  (JNIEnv *env, jclass class, jint gpio, jint level)
{
    return gpioWrite((unsigned)gpio, (unsigned)level);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioPWM
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioPWM
  (JNIEnv *env, jclass class, jint user_gpio, jint dutycycle)
{
    return gpioPWM((unsigned)user_gpio, (unsigned)dutycycle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMdutycycle
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMdutycycle
  (JNIEnv *env, jclass class, jint user_gpio)
{
    return gpioGetPWMdutycycle((unsigned)user_gpio);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPWMrange
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPWMrange
  (JNIEnv *env, jclass class, jint user_gpio, jint range)
{
    return gpioSetPWMrange((unsigned)user_gpio, (unsigned)range);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMrange
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMrange
  (JNIEnv *env, jclass class, jint user_gpio)
{
    return gpioGetPWMrange((unsigned)user_gpio);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMrealRange
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMrealRange
  (JNIEnv *env, jclass class, jint user_gpio)
{
    return gpioGetPWMrealRange((unsigned)user_gpio);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPWMfrequency
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPWMfrequency
  (JNIEnv *env, jclass class, jint user_gpio, jint frequency)
{
    return gpioSetPWMfrequency((unsigned)user_gpio, (unsigned)frequency);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMfrequency
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMfrequency
  (JNIEnv *env, jclass class, jint user_gpio)
{
    return gpioGetPWMfrequency((unsigned)user_gpio);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioServo
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioServo
  (JNIEnv *env, jclass class, jint user_gpio, jint pulsewidth)
{
    return gpioServo((unsigned)user_gpio, (unsigned)pulsewidth);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetServoPulsewidth
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetServoPulsewidth
  (JNIEnv *env, jclass class, jint user_gpio)
{
    return gpioGetServoPulsewidth((unsigned)user_gpio);
}


