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
#include <jni.h>

#ifndef _Included_com_pi4j_library_linuxfs_LinuxFS
#define _Included_com_pi4j_library_linuxfs_LinuxFS
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_getPosixFD
  (JNIEnv *env, jclass obj, jobject fileDescriptor);

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_errno
  (JNIEnv *env, jclass obj);

JNIEXPORT jstring JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_strerror
  (JNIEnv *env, jclass obj, jint errorNum);

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_directIOCTL
  (JNIEnv *env, jclass obj, jint fd, jlong command, jlong value);

JNIEXPORT jlong JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_mmap
  (JNIEnv *env, jclass obj, jint fd, jint length, jint prot, jint flags, jint offset);

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_munmapDirect
  (JNIEnv *env, jclass obj, jlong address, jlong capacity);

JNIEXPORT jint JNICALL Java_com_pi4j_library_linuxfs_LinuxFile_directIOCTLStructure
  (JNIEnv *env, jclass obj, jint fd, jlong command, jobject data, jint dataOffset, jobject offsetMap, jint offsetMapOffset, jint offsetCapacity);

#ifdef __cplusplus
}
#endif
#endif
