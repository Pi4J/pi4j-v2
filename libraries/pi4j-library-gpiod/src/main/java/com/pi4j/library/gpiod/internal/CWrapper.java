package com.pi4j.library.gpiod.internal;

import java.util.Objects;

public class CWrapper {
    private final long cPointer;

    public CWrapper(long cPointer) {
        this.cPointer = cPointer;
    }

    long getCPointer() {
        return cPointer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CWrapper cWrapper = (CWrapper) o;
        return cPointer == cWrapper.cPointer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cPointer);
    }
}
