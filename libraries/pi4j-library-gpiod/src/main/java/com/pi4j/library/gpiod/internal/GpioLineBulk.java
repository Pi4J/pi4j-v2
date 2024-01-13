package com.pi4j.library.gpiod.internal;

public class GpioLineBulk {
    private final long cPtr;

    GpioLineBulk(long cPtr) {
        this.cPtr = cPtr;
    }

    public GpioLineBulk() {
        this(GpioD.lineBulkNew());
    }

    @Override
    protected void finalize() {
        GpioD.lineBulkFree(this);
    }

    long getCPtr() {
        return this.cPtr;
    }

    public void add(GpioLine line) {
        GpioD.lineBulkAdd(this, line);
    }

    public GpioLine getLine(int index) {
        return GpioD.lineBulkGetLine(this, index);
    }

    public int getNumLines() {
        return GpioD.lineBulkGetNumLines(this);
    }

    public void request(GpioLineRequest config, int[] defaultVals) {
        GpioD.lineRequestBulk(this, config, defaultVals);
    }

    public void requestInput(String consumer) {
        GpioD.lineRequestBulkInput(this, consumer);
    }

    public void requestOutput(String consumer, int[] defaultVals) {
        GpioD.lineRequestBulkOutput(this, consumer, defaultVals);
    }

    public void requestRisingEdgeEvents(String consumer) {
        GpioD.lineRequestBulkRisingEdgeEvents(this, consumer);
    }

    public void requestFallingEdgeEvents(String consumer) {
        GpioD.lineRequestBulkFallingEdgeEvents(this, consumer);
    }

    public void requestBothEdgeEvents(String consumer) {
        GpioD.lineRequestBulkBothEdgeEvents(this, consumer);
    }

    public void requestInputFlags(String consumer, int flags) {
        GpioD.lineRequestBulkInputFlags(this, consumer, flags);
    }

    public void requestOutputFlags(String consumer, int flags, int[] defaultValue) {
        GpioD.lineRequestBulkOutputFlags(this, consumer, flags, defaultValue);
    }

    public void requestRisingEdgeEventsFlags(String consumer, int flags) {
        GpioD.lineRequestBulkRisingEdgeEventFlags(this, consumer, flags);
    }

    public void requestFallingEdgeEventsFlags(String consumer, int flags) {
        GpioD.lineRequestBulkFallingEdgeEventFlags(this, consumer, flags);
    }

    public void requestBothEdgeEventsFlags(String consumer, int flags) {
        GpioD.lineRequestBulkBothEdgeEventFlags(this, consumer, flags);
    }

    public void release() {
        GpioD.lineBulkRelease(this);
    }

    public int[] getValues() {
        return GpioD.lineBulkGetValues(this);
    }

    public void setValue(int[] values) {
        GpioD.lineBulkSetValue(this, values);
    }

    public void setConfig(GpioD.LINE_REQUEST direction, int flags, int[] values) {
        GpioD.lineBulkSetConfig(this, direction, flags, values);
    }

    public void setFlags(int flags) {
        GpioD.lineBulkSetFlags(this, flags);
    }

    public void setDirectionInput() {
        GpioD.lineSetDirectionOutputBulk(this);
    }

    public void setDirectionOutput(int[] values) {
        GpioD.lineSetDirectionOutputBulk(this, values);
    }

    public GpioLineBulk eventWait(long timeoutNs) {
        GpioLineBulk bulk = new GpioLineBulk();
        if(GpioD.lineBulkEventWait(this, timeoutNs, bulk)) {
            return bulk;
        }
        return null;
    }

    //TODO event read


}
