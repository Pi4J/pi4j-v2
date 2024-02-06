package com.pi4j.library.gpiod.internal;

import java.util.Iterator;

/**
 * <p>GpioLineIterator</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioLineIterator extends CWrapper implements Iterator<GpioLine> {

    private GpioLine next;

    GpioLineIterator(long cPointer) {
        super(cPointer);
    }

    public GpioLineIterator(GpioChip chip) {
        this(GpioD.lineIterNew(chip));
    }

    @Override
    public boolean hasNext() {
        if(next == null) {
            next = GpioD.lineIterNext(this);
        }
        return next != null;
    }

    @Override
    public GpioLine next() {
        GpioLine current;
        if(next == null) {
            next = GpioD.lineIterNext(this);
        }
        current = next;
        next = null;
        return current;
    }
}
