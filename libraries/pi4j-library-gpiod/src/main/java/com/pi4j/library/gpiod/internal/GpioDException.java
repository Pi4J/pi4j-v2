package com.pi4j.library.gpiod.internal;

/**
 * <p>GpioDException</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioDException extends RuntimeException {
    public GpioDException() {
        super();
    }

    public GpioDException(String msg) {
        super(msg);
    }
}
