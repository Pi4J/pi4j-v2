package com.pi4j.library.gpiod.internal;

/**
 * <p>GpioLineEvent</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
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
