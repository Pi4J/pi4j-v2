package com.pi4j.library.gpiod.internal;

public class GpioLineRequest {
    private final long cPtr;

    GpioLineRequest(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }
}
