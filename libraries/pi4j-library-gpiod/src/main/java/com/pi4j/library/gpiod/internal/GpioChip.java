package com.pi4j.library.gpiod.internal;

import java.io.Closeable;

/**
 * <p>GpioChip</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioChip implements Closeable {
    private final long cPtr;

    GpioChip(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }

    public void close() {
        GpioD.chipClose(this);
    }

    public String getName() {
        return GpioD.chipGetName(this);
    }

    public String getLabel() {
        return GpioD.chipGetLabel(this);
    }

    public int getNumLines() {
        return GpioD.chipGetNumLines(this);
    }

    public GpioLine getLine(int offset) {
        return GpioD.chipGetLine(this, offset);
    }

    public GpioLineBulk getLines(int[] offsets) {
        GpioLineBulk bulk = new GpioLineBulk();
        GpioD.chipGetLines(this, offsets, bulk);
        return bulk;
    }

    public GpioLineBulk getLines() {
        GpioLineBulk bulk = new GpioLineBulk();
        GpioD.chipGetAllLines(this, bulk);
        return bulk;
    }

    public GpioLine getLine(String name) {
        return GpioD.chipGetLine(this, name);
    }

}
