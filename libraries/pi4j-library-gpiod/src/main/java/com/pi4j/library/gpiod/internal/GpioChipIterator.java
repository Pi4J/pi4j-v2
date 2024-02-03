package com.pi4j.library.gpiod.internal;

import java.util.Iterator;

/**
 * <p>GpioChipIterator</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioChipIterator implements Iterator<GpioChip> {

    private final long cPtr;
    private boolean noCoseCurrent;
    private GpioChip next;
    private GpioChip current;

    GpioChipIterator(long cPtr) {
        this.cPtr = cPtr;
    }

    public GpioChipIterator() {
        this(GpioD.chipIterNew());
    }

    long getCPtr() {
        return this.cPtr;
    }

    @Override
    protected void finalize() {
        if(next == null) {
            if(noCoseCurrent) {
                GpioD.chipIterFreeNoClose(this);
            } else {
                GpioD.chipIterFree(this);
            }
        } else {
            if(!noCoseCurrent) {
                GpioD.chipClose(current);
            }
            GpioD.chipIterFree(this);
        }
    }

    @Override
    public boolean hasNext() {
        if(next == null) {
            next = GpioD.chipIterNextNoClose(this);
        }
        return next != null;
    }

    @Override
    public GpioChip next() {
        if(next == null) {
            if(noCoseCurrent) {
                current = GpioD.chipIterNextNoClose(this);
            } else {
                current = GpioD.chipIterNext(this);
            }
        } else {
            if(current != null && !noCoseCurrent) {
                GpioD.chipClose(current);
            }
            current = next;
            noCoseCurrent = false;
            next = null;
        }
        return current;
    }

    public void noCloseCurrent() {
        this.noCoseCurrent = true;
    }
}
