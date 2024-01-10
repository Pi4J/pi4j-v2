package com.pi4j.library.gpiod.internal;

public class GpioLine {
    private final long cPtr;

    GpioLine(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }
}
