package com.pi4j.library.gpiod.internal;

/**
 * <p>GpioLineRequest</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioLineRequest {
    private final long cPtr;

    GpioLineRequest(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }
}
