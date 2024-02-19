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

    public long getTimeNs() {
        return GpioD.lineEventGetTimespec(getCPointer());
    }

    public LineEvent getType() {
        return GpioD.lineEventGetType(getCPointer());
    }
}
