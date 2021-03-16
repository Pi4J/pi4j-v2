package com.pi4j.library.linuxfs.internal;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for LinuxFS Library
 * FILENAME      :  LinuxFs.java
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

import java.io.FileDescriptor;

import com.pi4j.library.linuxfs.util.NativeLibraryLoader;

public class LinuxFs {

    static {
        // Load the platform library
        NativeLibraryLoader.load("libpi4j-linuxfs.so", "pi4j-linuxfs");
    }

    public native int open(String filename, FileDescriptor fd);

    public native int close(int fd);

    public native int ioctl(int fd, long command, int value);

    public native int errno();

    public native String strerror(int code);
}