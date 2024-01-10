package com.pi4j.library.gpiod.internal;

public class GpioLineBulk {
    private final long cPtr;

    GpioLineBulk(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }
}
