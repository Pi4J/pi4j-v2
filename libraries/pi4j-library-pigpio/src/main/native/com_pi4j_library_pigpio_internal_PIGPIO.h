/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  com_pi4j_library_pigpio_internal_PIGPIO.h
 * 
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 *
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
/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_pi4j_library_pigpio_internal_PIGPIO */

#ifndef _Included_com_pi4j_library_pigpio_internal_PIGPIO
#define _Included_com_pi4j_library_pigpio_internal_PIGPIO
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioInitialise
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioInitialise
  (JNIEnv *, jclass);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTerminate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTerminate
  (JNIEnv *, jclass);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetMode
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetMode
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetMode
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetMode
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPullUpDown
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPullUpDown
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioRead
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioRead
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioWrite
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioWrite
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioPWM
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioPWM
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMdutycycle
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMdutycycle
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPWMrange
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPWMrange
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMrange
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMrange
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMrealRange
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMrealRange
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPWMfrequency
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPWMfrequency
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPWMfrequency
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPWMfrequency
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioServo
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioServo
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetServoPulsewidth
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetServoPulsewidth
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetAlertFunc
 * Signature: (ILcom/pi4j/library/pigpio/internal/PiGpioAlertCallback;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetAlertFunc
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetAlertFuncEx
 * Signature: (ILcom/pi4j/library/pigpio/internal/PiGpioAlertCallbackEx;Ljava/lang/Object;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetAlertFuncEx
  (JNIEnv *, jclass, jint, jobject, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetISRFunc
 * Signature: (IIILcom/pi4j/library/pigpio/internal/PiGpioAlertCallback;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetISRFunc
  (JNIEnv *, jclass, jint, jint, jint, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetISRFuncEx
 * Signature: (IIILcom/pi4j/library/pigpio/internal/PiGpioAlertCallbackEx;Ljava/lang/Object;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetISRFuncEx
  (JNIEnv *, jclass, jint, jint, jint, jobject, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioNotifyOpen
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioNotifyOpen
  (JNIEnv *, jclass);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioNotifyOpenWithSize
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioNotifyOpenWithSize
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioNotifyBegin
 * Signature: (IJ)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioNotifyBegin
  (JNIEnv *, jclass, jint, jlong);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioNotifyPause
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioNotifyPause
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioNotifyClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioNotifyClose
  (JNIEnv *, jclass, jint);

///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioSerialReadOpen
// * Signature: (III)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSerialReadOpen
//  (JNIEnv *, jclass, jint, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioSerialReadInvert
// * Signature: (II)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSerialReadInvert
//  (JNIEnv *, jclass, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioSerialRead
// * Signature: (I[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSerialRead
//  (JNIEnv *, jclass, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioSerialReadClose
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSerialReadClose
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cOpen
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cOpen
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cClose
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteQuick
 * Signature: (IZ)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteQuick
  (JNIEnv *, jclass, jint, jboolean);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteByte
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteByte
  (JNIEnv *, jclass, jint, jbyte);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadByte
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadByte
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteByteData
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteByteData
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteWordData
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteWordData
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadByteData
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadByteData
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadWordData
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadWordData
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cProcessCall
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cProcessCall
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteBlockData
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteBlockData
  (JNIEnv *, jclass, jint, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadBlockData
 * Signature: (II[BI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadBlockData
  (JNIEnv *, jclass, jint, jint, jbyteArray, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cBlockProcessCall
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cBlockProcessCall
  (JNIEnv *, jclass, jint, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadI2CBlockData
 * Signature: (II[BI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadI2CBlockData
  (JNIEnv *, jclass, jint, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteI2CBlockData
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteI2CBlockData
  (JNIEnv *, jclass, jint, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadDevice
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadDevice
  (JNIEnv *, jclass, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteDevice
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteDevice
  (JNIEnv *, jclass, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cSwitchCombined
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cSwitchCombined
  (JNIEnv *, jclass, jint);

///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    i2cZip
// * Signature: (I[BI[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cZip
//  (JNIEnv *, jclass, jint, jbyteArray, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    bbI2COpen
// * Signature: (III)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_bbI2COpen
//  (JNIEnv *, jclass, jint, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    bbI2CClose
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_bbI2CClose
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    bbI2CZip
// * Signature: (I[BI[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_bbI2CZip
//  (JNIEnv *, jclass, jint, jbyteArray, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    bbSPIOpen
// * Signature: (IIIIII)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_bbSPIOpen
//  (JNIEnv *, jclass, jint, jint, jint, jint, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    bbSPIClose
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_bbSPIClose
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    bbSPIXfer
// * Signature: (I[B[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_bbSPIXfer
//  (JNIEnv *, jclass, jint, jbyteArray, jbyteArray, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiOpen
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiOpen
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiClose
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiRead
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiRead
  (JNIEnv *, jclass, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiWrite
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiWrite
  (JNIEnv *, jclass, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiXfer
 * Signature: (I[BI[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiXfer
  (JNIEnv *, jclass, jint, jbyteArray, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serOpen
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serOpen
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serClose
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serWriteByte
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serWriteByte
  (JNIEnv *, jclass, jint, jbyte);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serReadByte
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serReadByte
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serWrite
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serWrite
  (JNIEnv *, jclass, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serRead
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serRead
  (JNIEnv *, jclass, jint, jbyteArray, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serDataAvailable
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serDataAvailable
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serDrain
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serDrain
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTrigger
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTrigger
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetWatchdog
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetWatchdog
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioNoiseFilter
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioNoiseFilter
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGlitchFilter
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGlitchFilter
  (JNIEnv *, jclass, jint, jint);

///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioStoreScript
// * Signature: (Ljava/lang/String;)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioStoreScript
//  (JNIEnv *, jclass, jstring);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioRunScript
// * Signature: (II[I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioRunScript
//  (JNIEnv *, jclass, jint, jint, jintArray);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioUpdateScript
// * Signature: (II[I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioUpdateScript
//  (JNIEnv *, jclass, jint, jint, jintArray);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioScriptStatus
// * Signature: (I[I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioScriptStatus
//  (JNIEnv *, jclass, jint, jintArray);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioStopScript
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioStopScript
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioDeleteScript
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioDeleteScript
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetSignalFunc
 * Signature: (ILcom/pi4j/library/pigpio/internal/PiGpioSignalCallback;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetSignalFunc
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetSignalFuncEx
 * Signature: (ILcom/pi4j/library/pigpio/internal/PiGpioSignalCallbackEx;Ljava/lang/Object;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetSignalFuncEx
  (JNIEnv *, jclass, jint, jobject, jobject);

///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioRead_Bits_0_31
// * Signature: ()I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioRead_1Bits_10_131
//  (JNIEnv *, jclass);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioRead_Bits_32_53
// * Signature: ()I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioRead_1Bits_132_153
//  (JNIEnv *, jclass);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioWrite_Bits_0_31_Clear
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioWrite_1Bits_10_131_1Clear
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioWrite_Bits_32_53_Clear
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioWrite_1Bits_132_153_1Clear
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioWrite_Bits_0_31_Set
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioWrite_1Bits_10_131_1Set
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioWrite_Bits_32_53_Set
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioWrite_1Bits_132_153_1Set
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioHardwareClock
// * Signature: (IJ)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioHardwareClock
//  (JNIEnv *, jclass, jint, jlong);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioHardwarePWM
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioHardwarePWM
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTime
 * Signature: (I[I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTime
  (JNIEnv *, jclass, jint, jintArray);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSleep
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSleep
  (JNIEnv *, jclass, jint, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioDelay
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioDelay
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTick
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTick
  (JNIEnv *, jclass);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioHardwareRevision
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioHardwareRevision
  (JNIEnv *, jclass);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioVersion
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioVersion
  (JNIEnv *, jclass);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioGetPad
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioGetPad
  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSetPad
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSetPad
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    eventMonitor
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_eventMonitor
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    eventSetFunc
 * Signature: (ILcom/pi4j/library/pigpio/internal/PiGpioEventCallback;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_eventSetFunc
  (JNIEnv *, jclass, jint, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    eventSetFuncEx
 * Signature: (ILcom/pi4j/library/pigpio/internal/PiGpioEventCallbackEx;Ljava/lang/Object;)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_eventSetFuncEx
  (JNIEnv *, jclass, jint, jobject, jobject);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    eventTrigger
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_eventTrigger
  (JNIEnv *, jclass, jint);

///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    shell
// * Signature: (Ljava/lang/String;Ljava/lang/String;)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_shell
//  (JNIEnv *, jclass, jstring, jstring);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    fileOpen
// * Signature: (Ljava/lang/String;I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_fileOpen
//  (JNIEnv *, jclass, jstring, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    fileClose
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_fileClose
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    fileWrite
// * Signature: (I[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_fileWrite
//  (JNIEnv *, jclass, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    fileRead
// * Signature: (I[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_fileRead
//  (JNIEnv *, jclass, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    fileSeek
// * Signature: (III)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_fileSeek
//  (JNIEnv *, jclass, jint, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    fileList
// * Signature: (Ljava/lang/String;Ljava/lang/String;I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_fileList
//  (JNIEnv *, jclass, jstring, jstring, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgBufferSize
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgBufferSize
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgClock
// * Signature: (III)I
// */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgClock
  (JNIEnv *, jclass, jint, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgDMAchannel
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgDMAchannel
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgDMAchannels
// * Signature: (II)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgDMAchannels
//  (JNIEnv *, jclass, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgPermissions
// * Signature: (J)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgPermissions
//  (JNIEnv *, jclass, jlong);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgSocketPort
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgSocketPort
//  (JNIEnv *, jclass, jint);

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioCfgInterfaces
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgInterfaces
  (JNIEnv *, jclass, jint);

///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgMemAlloc
// * Signature: (I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgMemAlloc
//  (JNIEnv *, jclass, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgNetAddr
// * Signature: (I[I)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgNetAddr
//  (JNIEnv *, jclass, jint, jintArray);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgInternals
// * Signature: (II)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgInternals
//  (JNIEnv *, jclass, jint, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgGetInternals
// * Signature: ()J
// */
//JNIEXPORT jlong JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgGetInternals
//  (JNIEnv *, jclass);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCfgSetInternals
// * Signature: (J)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgSetInternals
//  (JNIEnv *, jclass, jlong);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCustom1
// * Signature: (II[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCustom1
//  (JNIEnv *, jclass, jint, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    gpioCustom2
// * Signature: (I[BI[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCustom2
//  (JNIEnv *, jclass, jint, jbyteArray, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    getBitInBytes
// * Signature: (I[BI)I
// */
//JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_getBitInBytes
//  (JNIEnv *, jclass, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    putBitInBytes
// * Signature: (I[BI)V
// */
//JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_putBitInBytes
//  (JNIEnv *, jclass, jint, jbyteArray, jint);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    time_time
// * Signature: ()D
// */
//JNIEXPORT jdouble JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_time_1time
//  (JNIEnv *, jclass);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    time_sleep
// * Signature: (D)V
// */
//JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_time_1sleep
//  (JNIEnv *, jclass, jdouble);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    rawDumpWave
// * Signature: ()V
// */
//JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_rawDumpWave
//  (JNIEnv *, jclass);
//
///*
// * Class:     com_pi4j_library_pigpio_internal_PIGPIO
// * Method:    rawDumpScript
// * Signature: (I)V
// */
//JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_rawDumpScript
//  (JNIEnv *, jclass, jint);


#ifdef __cplusplus
}
#endif
#endif
