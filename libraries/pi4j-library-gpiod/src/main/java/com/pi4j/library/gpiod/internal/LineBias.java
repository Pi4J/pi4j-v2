package com.pi4j.library.gpiod.internal;

public enum LineBias {
    AS_IS(1),
    DISABLE(2),
    PULL_UP(3),
    PULL_DOWN(4);
    final int val;

    public int getVal() {
        return val;
    }

    LineBias(int val) {
        this.val = val;
    }

    static LineBias fromInt(int val) {
        for (LineBias dir : LineBias.values()) {
            if (dir.val == val)
                return dir;
        }
        throw new IllegalStateException("Unexpected LINE_BIAS value: " + val);
    }
}
