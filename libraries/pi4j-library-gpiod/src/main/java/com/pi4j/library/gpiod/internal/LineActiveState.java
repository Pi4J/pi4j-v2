package com.pi4j.library.gpiod.internal;

public enum LineActiveState {
    HIGH(1),
    LOW(2);
    final int val;

    public int getVal() {
        return val;
    }

    LineActiveState(int val) {
        this.val = val;
    }

    static LineActiveState fromInt(int val) {
        for (LineActiveState dir : LineActiveState.values()) {
            if (dir.val == val)
                return dir;
        }
        throw new IllegalStateException("Unexpected LINE_ACTIVE_STATE value: " + val);
    }
}
