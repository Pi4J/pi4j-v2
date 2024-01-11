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

    private native void c_gpiod_line_bulk_add(long lineBulkPtr, long linePtr);

    private native long c_gpiod_line_bulk_get_line(long lineBulkPtr, int offset);

    private native int c_gpiod_line_bulk_num_lines(long lineBulkPtr);


    enum LINE_DIRECTION {
        INPUT(1), OUTPUT(2);
        final int val;

        LINE_DIRECTION(int val) {
            this.val = val;
        }

        static LINE_DIRECTION fromInt(int val) {
            for (LINE_DIRECTION dir : LINE_DIRECTION.values()) {
                if (dir.val == val) {
                    return dir;
                }
            }
            throw new IllegalStateException("Unexpected LINE_DIRECTION value: " + val);
        }
    }

    enum LINE_ACTIVE_STATE {
        HIGH(1), LOW(2);
        final int val;

        LINE_ACTIVE_STATE(int val) {
            this.val = val;
        }

        static LINE_ACTIVE_STATE fromInt(int val) {
            for (LINE_ACTIVE_STATE dir : LINE_ACTIVE_STATE.values()) {
                if (dir.val == val) {
                    return dir;
                }
            }
            throw new IllegalStateException("Unexpected LINE_ACTIVE_STATE value: " + val);
        }
    }

    enum LINE_BIAS {
        AS_IS(1), DISABLE(2), PULL_UP(3), PULL_DOWN(4);
        final int val;

        LINE_BIAS(int val) {
            this.val = val;
        }

        static LINE_BIAS fromInt(int val) {
            for (LINE_BIAS dir : LINE_BIAS.values()) {
                if (dir.val == val) {
                    return dir;
                }
            }
            throw new IllegalStateException("Unexpected LINE_BIAS value: " + val);
        }
    }

    private native int gpiod_line_offset(long linePtr);

    private native String gpiod_line_name(long linePtr);

    private native int gpiod_line_direction(long linePtr);

    private native int gpiod_line_active_state(long linePtr);

    private native int gpiod_line_bias(long linePtr);

    private native boolean gpiod_line_is_used(long linePtr);

    private native boolean gpiod_line_is_open_drain(long linePtr);

    private native boolean gpiod_line_is_open_source(long linePtr);

    private native int gpiod_line_update(long linePtr);

    private native boolean gpiod_line_needs_update(long linePtr);

    enum LINE_REQUEST {
        DIRECTION_AS_IS(1), DIRECTION_INPUT(2), DIRECTION_OUTPUT(3), EVENT_FALLING_EDGE(4),
        EVENT_RISING_EDGE(5), EVENT_BOTH_EDGES(6);
        final int val;

        LINE_REQUEST(int val) {
            this.val = val;
        }

        static LINE_REQUEST fromInt(int val) {
            for (LINE_REQUEST dir : LINE_REQUEST.values()) {
                if (dir.val == val) {
                    return dir;
                }
            }
            throw new IllegalStateException("Unexpected LINE_REQUEST value: " + val);
        }
    }

    enum LINE_REQUEST_FLAG {
        OPEN_DRAIN((byte) 1), OPEN_SOURCE((byte) (1 << 1)), ACTIVE_LOW((byte) (1 << 2)), BIAS_DISABLE((byte) (1 << 3)),
        PULL_DOWN((byte) (1 << 4)), PULL_UP((byte) (1 << 5));
        final byte val;

        LINE_REQUEST_FLAG(byte val) {
            this.val = val;
        }

        static LINE_REQUEST_FLAG fromByte(byte val) {
            for (LINE_REQUEST_FLAG dir : LINE_REQUEST_FLAG.values()) {
                if (dir.val == val) {
                    return dir;
                }
            }
            throw new IllegalStateException("Unexpected LINE_REQUEST_FLAG value: " + val);
        }
    }

}
