package com.pi4j.library.gpiod.internal;

public class GpioD {
    // C library: https://git.kernel.org/pub/scm/libs/libgpiod/libgpiod.git/tree/include/gpiod.h?h=v1.6.x


    GpioChip chipOpen(String path) {
        Long ptr = c_gpiod_chip_open(path);
        if(ptr == null) {
            throw new GpioDException("c_gpiod_chip_open failed!");
        }
        return new GpioChip(ptr);
    }

    private native Long c_gpiod_chip_open(String path);

    void chipClose(GpioChip chip) {
        c_gpiod_chip_close(chip.getCPtr());
    }

    private native void c_gpiod_chip_close(long chipPtr);

    String chipGetName(GpioChip chip) {
        return c_gpiod_chip_name(chip.getCPtr());
    }

    private native String c_gpiod_chip_name(long chipPtr);

    String chipGetLabel(GpioChip chip) {
        return c_gpiod_chip_label(chip.getCPtr());
    }

    private native String c_gpiod_chip_label(long chipPtr);

    int chipGetNumLines(GpioChip chip) {
        return c_gpiod_chip_num_lines(chip.getCPtr());
    }

    private native int c_gpiod_chip_num_lines(long chipPtr);

    GpioLine chipGetLine(GpioChip chip, int offset) {
        Long linePtr = c_gpiod_chip_get_line(chip.getCPtr(), offset);
        if (linePtr == null) {
            throw new GpioDException("c_gpiod_chip_get_line failed!");
        }
        return new GpioLine(linePtr);
    }
    private native Long c_gpiod_chip_get_line(long chipPtr, int offset);

    void chipGetLines(GpioChip chip, int[] offsets, GpioLineBulk lineBulk) {
        if(c_gpiod_chip_get_lines(chip.getCPtr(), offsets, offsets.length, lineBulk.getCPtr()) < 0) {
            throw new GpioDException("c_gpiod_chip_get_lines failed!");
        }
    }

    private native int c_gpiod_chip_get_lines(long chipPtr, int[] offsets, int numOffsets, long lineBulkPtr);

    void chipGetAllLines(GpioChip chip, GpioLineBulk lineBulk) {
        if(c_gpiod_chip_get_all_lines(chip.getCPtr(), lineBulk.getCPtr()) < 0) {
            throw new GpioDException("c_gpiod_chip_get_all_lines failed!");
        }
    }

    private native int c_gpiod_chip_get_all_lines(long chipPtr, long lineBulkPtr);

    GpioLine chipGetLine(GpioChip chip, String name) {
        Long linePtr = c_gpiod_chip_find_line(chip.getCPtr(), name);
        if (linePtr == null) {
            throw new GpioDException("c_gpiod_chip_find_line failed!");
        }
        return new GpioLine(linePtr);
    }

    private native Long c_gpiod_chip_find_line(long chipPtr, String name);




}
