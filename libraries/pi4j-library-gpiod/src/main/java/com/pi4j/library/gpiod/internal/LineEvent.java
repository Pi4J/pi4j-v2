package com.pi4j.library.gpiod.internal;

public enum LineEvent {
    RISING_EDGE(1),
    FALLING_EDGE(2);
    final int val;

    public int getVal() {
        return val;
    }

    LineEvent(int val) {
        this.val = val;
    }

    static LineEvent fromInt(int val) {
        for (LineEvent dir : LineEvent.values()) {
            if (dir.val == val)
                return dir;
        }
        throw new IllegalStateException("Unexpected LINE_EVENT value: " + val);
    }
}
