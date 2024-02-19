package com.pi4j.library.gpiod.internal;

public enum LineDirection {
    INPUT(1),
    OUTPUT(2);
    final int val;

    public int getVal() {
        return val;
    }

    LineDirection(int val) {
        this.val = val;
    }

    static LineDirection fromInt(int val) {
        for (LineDirection dir : LineDirection.values()) {
            if (dir.val == val)
                return dir;
        }
        throw new IllegalStateException("Unexpected LINE_DIRECTION value: " + val);
    }
}
