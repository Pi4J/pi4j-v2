package com.pi4j.library.gpiod.internal;

public enum LineRequestFlag {
    OPEN_DRAIN((byte) 1),
    OPEN_SOURCE((byte) (1 << 1)),
    ACTIVE_LOW((byte) (1 << 2)),
    BIAS_DISABLE((byte) (1 << 3)),
    BIAS_PULL_DOWN((byte) (1 << 4)),
    BIAS_PULL_UP((byte) (1 << 5));
    final byte val;

    LineRequestFlag(byte val) {
        this.val = val;
    }

    public byte getVal() {
        return val;
    }

    static LineRequestFlag fromByte(byte val) {
        for (LineRequestFlag dir : LineRequestFlag.values()) {
            if (dir.val == val)
                return dir;
        }
        throw new IllegalStateException("Unexpected LINE_REQUEST_FLAG value: " + val);
    }
}