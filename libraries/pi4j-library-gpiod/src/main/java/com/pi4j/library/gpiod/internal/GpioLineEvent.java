package com.pi4j.library.gpiod.internal;

public class GpioLineEvent {
    private final long cPtr;

    GpioLineEvent(long cPtr) {
        this.cPtr = cPtr;
    }

    public GpioLineEvent() {
        cPtr = GpioD.lineEventNew();
    }

    @Override
    protected void finalize() {
        GpioD.lineEventFree(this);
    }

    long getCPtr() {
        return this.cPtr;
    }

    public long getTimeNs() {
        return GpioD.lineEventGetTimespec(this);
    }

    public GpioD.LINE_EVENT getType() {
        return GpioD.lineEventGetType(this);
    }
}
