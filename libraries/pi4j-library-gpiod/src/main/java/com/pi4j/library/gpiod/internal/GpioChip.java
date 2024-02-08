package com.pi4j.library.gpiod.internal;

import java.io.Closeable;

/**
 * <p>GpioChip</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioChip extends CWrapper implements Closeable {

    private boolean open;

    public GpioChip(long cPointer) {
        super(cPointer);
        this.open = true;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void close() {
        if (!this.open)
            return;
        GpioD.chipClose(this);
        this.open = false;
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
