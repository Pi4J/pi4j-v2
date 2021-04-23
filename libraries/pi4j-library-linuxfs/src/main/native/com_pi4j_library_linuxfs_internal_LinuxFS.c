/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for LinuxFS Library
 * FILENAME      :  com_pi4j_library_linuxfs_internal_LinuxFS.c
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
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <jni.h>
#include <errno.h>
#include <stdint.h>
#include <sys/mman.h>

#include "com_pi4j_library_linuxfs_internal_LinuxFS.h"

int directIOCTLStructure
  (int fd, unsigned long command, void *data, size_t headOffset, uint32_t *offsetMap, uint32_t offsetSize);

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_getPosixFD
  (JNIEnv *env, jclass obj, jobject fileDescriptorObj) {
    jfieldID  fdFieldID;
    jclass    fdClass;
    int       fd = -1;

    // Get Class and Field information.
    fdClass = (*env)->FindClass(env, "java/io/FileDescriptor");

    if ( fdClass == NULL ) {
        // Unable to obtain Class information
        return -2;
    }

    fdFieldID = (*env)->GetFieldID(env, fdClass, "fd", "I");

    if ( fdFieldID == NULL ) {
        // Unable to obtain Field information
        return -3;
    }

    // Extract POSIX File Descriptor from Java FileDescriptor object
    fd = (*env)->GetIntField(env, fileDescriptorObj, fdFieldID);

    return fd;
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_errno
  (JNIEnv *env, jclass obj) {
    return errno;
}

JNIEXPORT jstring JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_strerror
  (JNIEnv *env, jclass obj, jint errorNum) {
    return (*env)->NewStringUTF(env, strerror(errorNum));
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_directIOCTL
  (JNIEnv *env, jclass obj, jint fd, jlong command, jlong value) {
    return ioctl(fd, command, value);
}

JNIEXPORT jlong JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_mmap
  (JNIEnv *env, jclass obj, jint fd, jint length, jint prot, jint flags, jint offset) {
    void *addr = mmap(NULL, length, prot, flags, fd, offset);

    if(addr == MAP_FAILED)
        return -1;

    return (jlong)(uintptr_t)addr;
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_munmapDirect
  (JNIEnv *env, jclass obj, jlong address, jlong capacity) {
    return munmap((void *)(uintptr_t)address, (size_t)capacity);
}

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_directIOCTLStructure
  (JNIEnv *env, jclass obj, jint fd, jlong command, jobject data, jint dataOffset, jobject offsetMap, jint offsetMapOffset, jint offsetCapacity) {
    uint8_t *dataBuffer = (uint8_t *)((*env)->GetDirectBufferAddress(env, data));
    uint32_t *offsetBuffer = (uint32_t *)((*env)->GetDirectBufferAddress(env, offsetMap));

    return directIOCTLStructure(fd, command, dataBuffer, (size_t)dataOffset, offsetBuffer + offsetMapOffset, offsetCapacity);
}

int directIOCTLStructure (int fd, unsigned long command, void *data, size_t headOffset, uint32_t *offsetMap, uint32_t offsetSize) {
    uint32_t i;

    //iterate through offsets, convert and apply pointers
    for(i = 0 ; i < offsetSize ; i += 2) {
      uint32_t pointerOffset = offsetMap[i];
      uint32_t pointingOffset = offsetMap[i + 1];

      void **ptr = (void **)(data + pointerOffset);

      //add in base ptr here to offset
      *ptr = data + pointingOffset;
    }

    return ioctl(fd, command, data + headOffset);
}