package com.pi4j.plugin.linuxfs.internal;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * C library functions.
 *
 * @author mpilone
 * @since 10/3/24.
 */
public interface LinuxLibC extends Library {

    // This class could extend c.s.j.platform.linux.LibC, but we're not using any
    // of that functionality right now so we can avoid the jna-platform dependency
    // until we need it.

    LinuxLibC INSTANCE = LinuxLibC.LibLoader.load();

    class LibLoader {
        static LinuxLibC load() {
            return Native.load("c", LinuxLibC.class);
        }
    }

    ///////////////////////////////////
    // fcntl.h
    int O_WRONLY = 00000001;
    int O_RDWR = 00000002;
    int O_NONBLOCK = 00004000;

    ///////////////////////////////////
    // ioctl.h
    int _IOC_NRBITS = 8;
    int _IOC_TYPEBITS = 8;
    int _IOC_SIZEBITS = 14;

    int _IOC_NRSHIFT = 0;
    int _IOC_TYPESHIFT = (_IOC_NRSHIFT + _IOC_NRBITS);
    int _IOC_SIZESHIFT = (_IOC_TYPESHIFT + _IOC_TYPEBITS);
    int _IOC_DIRSHIFT = (_IOC_SIZESHIFT + _IOC_SIZEBITS);

    byte _IOC_NONE = 0;
    byte _IOC_WRITE = 1;
    byte _IOC_READ = 2;

    static int _IOC(byte dir, byte type, byte nr, int size) {
        return (((dir) << _IOC_DIRSHIFT) |
                ((type) << _IOC_TYPESHIFT) |
                ((nr) << _IOC_NRSHIFT) |
                ((size) << _IOC_SIZESHIFT));
    }

    int ioctl(int filedes, long op, Object... args);

    int open(String pathname, int flags);

    int close(int fd);
}
