package com.pi4j.library.gpiod.internal;

/**
 * <p>GpioChip</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioChip extends CWrapper {

    private final String name;
    private final String label;
    private final int numLines;

    GpioChip(long cPointer) {
        super(cPointer);
        this.name = GpioD.chipGetName(getCPointer());
        this.label = GpioD.chipGetLabel(getCPointer());
        this.numLines = GpioD.chipGetNumLines(getCPointer());
    }

    public String getName() {
        return this.name;
    }

    public String getLabel() {
        return this.label;
    }

    public int getNumLines() {
        return this.numLines;
    }
}
