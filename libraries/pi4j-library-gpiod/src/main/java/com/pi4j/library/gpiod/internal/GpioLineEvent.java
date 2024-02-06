package com.pi4j.library.gpiod.internal;

/**
 * <p>GpioLineEvent</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioLineEvent extends CWrapper {

    GpioLineEvent(long cPointer) {
        super(cPointer);
    }

    public GpioLineEvent() {
        this(GpioD.lineEventNew());
    }

    @Override
    protected void finalize() {
        GpioD.lineEventFree(this);
    }

    public long getTimeNs() {
        return GpioD.lineEventGetTimespec(this);
    }

    public GpioD.LINE_EVENT getType() {
        return GpioD.lineEventGetType(this);
    }
}
