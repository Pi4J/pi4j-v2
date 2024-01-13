package com.pi4j.library.gpiod.internal;

import java.util.Iterator;

public class GpioLineIterator implements Iterator<GpioLine> {

    private final long cPtr;

    GpioLineIterator(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public GpioLine next() {
        return null;
    }
}
