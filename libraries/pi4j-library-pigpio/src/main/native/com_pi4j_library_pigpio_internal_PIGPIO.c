/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
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
#include <stdio.h>
#include <stdlib.h>
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

// *****************************************************************************************************
// *****************************************************************************************************
// GPIO IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

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

// *****************************************************************************************************
// *****************************************************************************************************
// PWM IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

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
 * Method:    gpioHardwarePWM
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioHardwarePWM
  (JNIEnv *env, jclass class, jint user_gpio, jint frequency, jint dutyCycle)
{
    return gpioHardwarePWM((unsigned)user_gpio, (unsigned)frequency, (unsigned)dutyCycle);
}

// *****************************************************************************************************
// *****************************************************************************************************
// SERVO IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

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


// *****************************************************************************************************
// *****************************************************************************************************
// I2C IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cOpen
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cOpen
  (JNIEnv *env, jclass class, jint i2CBus, jint i2cAddr, jint i2cFlags)
{
    return i2cOpen((unsigned)i2CBus, (unsigned)i2cAddr, (unsigned)i2cFlags);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cClose
  (JNIEnv *env, jclass class, jint handle)
{
    return i2cClose((unsigned)handle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteQuick
 * Signature: (IZ)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteQuick
  (JNIEnv *env, jclass class, jint handle, jboolean bit)
{
    unsigned bitVal = 0;
    if(bit) bitVal = 1;
    return i2cWriteQuick((unsigned)handle, bitVal);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteByte
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteByte
  (JNIEnv *env, jclass class, jint handle, jbyte bVal)
{
    return i2cWriteByte((unsigned)handle, (unsigned)bVal);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadByte
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadByte
  (JNIEnv *env, jclass class, jint handle)
{
    return i2cReadByte((unsigned)handle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteByteData
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteByteData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jint bVal)
{
    return i2cWriteByteData((unsigned)handle, (unsigned)i2cReg, (unsigned)bVal);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteWordData
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteWordData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jint wVal)
{
    return i2cWriteWordData((unsigned)handle, (unsigned)i2cReg, (unsigned)wVal);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadByteData
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadByteData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg)
{
    return i2cReadByteData((unsigned)handle, (unsigned)i2cReg);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadWordData
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadWordData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg)
{
    return i2cReadWordData((unsigned)handle, (unsigned)i2cReg);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cProcessCall
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cProcessCall
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, int wVal)
{
    return i2cProcessCall((unsigned)handle, (unsigned)i2cReg, (unsigned)wVal);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteBlockData
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteBlockData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C write operation into the native buffer array
    jint result = i2cWriteBlockData((unsigned)handle, (unsigned)i2cReg, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; abort preserving any changes back to the Java array
	(*env)->ReleaseByteArrayElements(env, data, buffer, JNI_ABORT);

    // return the result
	return result;

}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadBlockData
 * Signature: (II[BI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadBlockData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jbyteArray data, jint offset)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C read operation into the native buffer array
	jint result = i2cReadBlockData((unsigned)handle, (unsigned)i2cReg, (char *)offsetBuffer);

	// unpin the reserved memory for 'data'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, data, buffer, 0);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cBlockProcessCall
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cBlockProcessCall
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C read operation into the native buffer array
	jint result = i2cBlockProcessCall((unsigned)handle, (unsigned)i2cReg, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, data, buffer, 0);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadI2CBlockData
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadI2CBlockData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C read operation into the native buffer array
	jint result = i2cReadI2CBlockData((unsigned)handle, (unsigned)i2cReg, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, data, buffer, 0);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteI2CBlockData
 * Signature: (II[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteI2CBlockData
  (JNIEnv *env, jclass class, jint handle, jint i2cReg, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C write operation into the native buffer array
    jint result = i2cWriteI2CBlockData((unsigned)handle, (unsigned)i2cReg, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; abort preserving any changes back to the Java array
	(*env)->ReleaseByteArrayElements(env, data, buffer, JNI_ABORT);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cReadDevice
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cReadDevice
  (JNIEnv *env, jclass class, jint handle, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C read operation into the native buffer array
	jint result = i2cReadDevice((unsigned)handle, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, data, buffer, 0);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cWriteDevice
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cWriteDevice
  (JNIEnv *env, jclass class, jint handle, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual I2C write operation into the native buffer array
    jint result = i2cWriteDevice((unsigned)handle, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; abort preserving any changes back to the Java array
	(*env)->ReleaseByteArrayElements(env, data, buffer, JNI_ABORT);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    i2cSwitchCombined
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_i2cSwitchCombined
  (JNIEnv *env, jclass class, jint setting)
{
    i2cSwitchCombined(setting);
}

// *****************************************************************************************************
// *****************************************************************************************************
// SPI IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiOpen
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiOpen
  (JNIEnv *env, jclass class, jint spiChan, jint baud, jint spiFlags)
{
    return spiOpen((unsigned)spiChan, (unsigned)baud, (unsigned)spiFlags);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiClose
  (JNIEnv *env, jclass class, jint handle)
{
    return spiClose((unsigned)handle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiRead
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiRead
  (JNIEnv *env, jclass class, jint handle, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual SPI read operation into the native buffer array
	jint result = spiRead((unsigned)handle, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, data, buffer, 0);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiWrite
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiWrite
  (JNIEnv *env, jclass class, jint handle, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual SPI write operation into the native buffer array
	jint result = spiWrite((unsigned)handle, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; abort preserving any changes back to the Java array
	(*env)->ReleaseByteArrayElements(env, data, buffer, JNI_ABORT);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    spiXfer
 * Signature: (I[B[BI)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_spiXfer
  (JNIEnv *env, jclass class, jint handle, jbyteArray writeData, jint writeOffset, jbyteArray readData, jint readOffset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *writeBuffer = (*env)->GetByteArrayElements(env, writeData, 0);
    jbyte *readBuffer = (*env)->GetByteArrayElements(env, readData, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, writeData) - writeOffset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new write buffer pointer using the offset
    jbyte *offsetWriteBuffer = writeBuffer + writeOffset;

    // create a new read buffer pointer using the offset
    jbyte *offsetReadBuffer = readBuffer + readOffset;

    // perform the actual SPI read operation into the native buffer array
    jint result = spiXfer((unsigned)handle, (char *)offsetWriteBuffer, (char *)offsetReadBuffer, (unsigned)length);

	// unpin the reserved memory for 'writeData'; abort preserving any changes back to the Java array
	(*env)->ReleaseByteArrayElements(env, writeData, writeBuffer, JNI_ABORT);

	// unpin the reserved memory for 'readData'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, readData, readBuffer, 0);

    // return the result
	return result;
}


// *****************************************************************************************************
// *****************************************************************************************************
// SERIAL IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serOpen
 * Signature: (Ljava/lang/String;II)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serOpen
  (JNIEnv *env, jclass class, jstring device, jint baud, jint serFlags)
{
    // pin the memory and get a pointer to the underlying char arrat for 'device' string parameter
    const char *device_ptr = (*env)->GetStringUTFChars(env, device, NULL);

    // perform serial port open using PIGPIO library call
    jint result = serOpen((char *)device_ptr, (unsigned)baud, (unsigned)serFlags);

    // release/unpin memory
    (*env)->ReleaseStringUTFChars(env, device, device_ptr);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serClose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serClose
  (JNIEnv *env, jclass class, jint handle)
{
    // perform SERIAL port close using PIGPIO library call
    return serClose((unsigned)handle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serWriteByte
 * Signature: (IB)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serWriteByte
  (JNIEnv *env, jclass class, jint handle, jbyte byt)
{
    // convert value into unsigned int
    unsigned b = byt & 0xFF;

    // perform actual SERIAL write byte operation using PIGPIO library call
    jint result = serWriteByte((unsigned)handle, b);
    return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serReadByte
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serReadByte
  (JNIEnv *env, jclass class, jint handle)
{
    // perform actual SERIAL read byte operation using PIGPIO library call
    return serReadByte((unsigned)handle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serWrite
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serWrite
  (JNIEnv *env, jclass class, jint handle, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual SERIAL write operation into the native buffer array using PIGPIO library call
	jint result = serWrite((unsigned)handle, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; abort preserving any changes back to the Java array
	(*env)->ReleaseByteArrayElements(env, data, buffer, JNI_ABORT);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serRead
 * Signature: (I[BII)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serRead
  (JNIEnv *env, jclass class, jint handle, jbyteArray data, jint offset, jint count)
{
	// obtain a pointer to the elements of the array and pin the memory
    jbyte *buffer = (*env)->GetByteArrayElements(env, data, 0);

    // get the maximum size of the Java data array; subtract any offset value
    jsize max_length = (*env)->GetArrayLength(env, data) - offset;

    // bounds check to make sure byte count does not exceed max array length (minus offset)
    int length = (count > max_length) ? max_length : count;

    // create a new buffer pointer using the offset
    jbyte *offsetBuffer = buffer + offset;

    // perform the actual SERIAL read operation into the native buffer array using PIGPIO library call
	jint result = serRead((unsigned)handle, (char *)offsetBuffer, (unsigned)length);

	// unpin the reserved memory for 'data'; persist change to the Java array and free the native array
	(*env)->ReleaseByteArrayElements(env, data, buffer, 0);

    // return the result
	return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serDataAvailable
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serDataAvailable
  (JNIEnv *env, jclass class, jint handle)
{
    // perform actual SERIAL get available bytes using PIGPIO library call
    return serDataAvailable((unsigned)handle);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    serDrain
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_serDrain
  (JNIEnv *env, jclass class, jint handle)
{
    // get the number of bytes available
    int available = serDataAvailable((unsigned)handle);

    // only process further if there are bytes available to drain/purge
    if(available > 0){
        // create a temporary buffer
        char buffer[available];

        // read the bytes available into the temporary buffer
        available = serRead((unsigned)handle, buffer, (unsigned)available);

        // result number of bytes drained/purged
        return available;
    }
    else {
        return 0;
    }
}







// *****************************************************************************************************
// *****************************************************************************************************
// DELAY/SLEEP/TIMER IMPLEMENTATION
// *****************************************************************************************************
// *****************************************************************************************************

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTime
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTime
  (JNIEnv *env, jclass class, jint timetype, jintArray data)
{
    int seconds = 0;
    int micros = 0;
    int *seconds_ptr = &seconds;
    int *micros_ptr = &micros;

    // get time elements
    jint result = gpioTime((unsigned)timetype, seconds_ptr, micros_ptr);

    // on error; return immediately
    if(result < 0){
        return result;
    }

    // pin memory and get array pointer
    jint *data_ptr = (*env)->GetIntArrayElements(env, data, 0);

    // update array pointer with result time values
    data_ptr[0] = seconds;
    data_ptr[1] = micros;

    // unpin memory
    (*env)->ReleaseIntArrayElements(env, data, data_ptr, 0);

    // return result which should be '0'
    return result;
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioSleep
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioSleep
  (JNIEnv *env, jclass class, jint timetype, jint seconds, jint micros)
{
    return gpioSleep((unsigned)micros, seconds, micros);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioDelay
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioDelay
  (JNIEnv *env, jclass class, jlong micros)
{
    return gpioDelay((uint32_t)micros);
}

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioTick
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioTick
  (JNIEnv *env, jclass class)
{
    return gpioTick();
}


// *****************************************************************************************************
// *****************************************************************************************************
// CONFIGURATION IMPL
// *****************************************************************************************************
// *****************************************************************************************************

/*
 * Class:     com_pi4j_library_pigpio_internal_PIGPIO
 * Method:    gpioCfgInterfaces
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_pi4j_library_pigpio_internal_PIGPIO_gpioCfgInterfaces
  (JNIEnv *env, jclass class, jint flags)
{
    return gpioCfgInterfaces(flags);
}

