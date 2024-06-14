package com.pi4j.library.gpiod.internal;

public enum LineRequest {
    DIRECTION_AS_IS(1),
    DIRECTION_INPUT(2),
    DIRECTION_OUTPUT(3),
    EVENT_FALLING_EDGE(4),
    EVENT_RISING_EDGE(5),
    EVENT_BOTH_EDGES(6);
    final int val;

    public int getVal() {
        return val;
    }

    LineRequest(int val) {
        this.val = val;
    }

    static LineRequest fromInt(int val) {
        for (LineRequest dir : LineRequest.values()) {
            if (dir.val == val)
                return dir;
        }
        throw new IllegalStateException("Unexpected LINE_REQUEST value: " + val);
    }
}