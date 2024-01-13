package com.pi4j.library.gpiod.internal;

import java.util.Iterator;

public class GpioChipIterator implements Iterator<GpioChip> {

    private final long cPtr;

    GpioChipIterator(long cPtr) {
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
    public GpioChip next() {
        return null;
    }
}
