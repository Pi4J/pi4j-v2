package com.pi4j.library.gpiod.internal;

import java.util.Iterator;

/**
 * <p>GpioChipIterator</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioChipIterator extends CWrapper implements Iterator<GpioChip> {

    private boolean noCloseCurrent;
    private GpioChip next;
    private GpioChip current;

    GpioChipIterator(long cPointer) {
        super(cPointer);
    }

    public GpioChipIterator() {
        this(GpioD.chipIterNew());
    }


    @Override
    protected void finalize() {
        if(next == null) {
            if(noCloseCurrent) {
                GpioD.chipIterFreeNoClose(this);
            } else {
                GpioD.chipIterFree(this);
            }
        } else {
            if(!noCloseCurrent) {
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
            if(noCloseCurrent) {
                current = GpioD.chipIterNextNoClose(this);
            } else {
                current = GpioD.chipIterNext(this);
            }
        } else {
            if(current != null && !noCloseCurrent) {
                GpioD.chipClose(current);
            }
            current = next;
            noCloseCurrent = false;
            next = null;
        }
        return current;
    }

    public void noCloseCurrent() {
        this.noCloseCurrent = true;
    }
}
