package com.pi4j.library.gpiod.internal;

public class GpioChip {
    private final long cPtr;

    GpioChip(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }
}
