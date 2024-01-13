package com.pi4j.library.gpiod.internal;

public class GpioLineEvent {
    private final long cPtr;

    GpioLineEvent(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }
}
