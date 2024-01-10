package com.pi4j.library.gpiod.internal;

public class GpioDException extends RuntimeException {
    public GpioDException() {
        super();
    }

    public GpioDException(String msg) {
        super(msg);
    }
}
