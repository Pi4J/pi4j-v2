package com.pi4j.library.gpiod.internal;

import java.util.Objects;

public class GpioLine {
    private final long cPtr;

    GpioLine(long cPtr) {
        this.cPtr = cPtr;
    }

    long getCPtr() {
        return this.cPtr;
    }

    public int getOffset() {
        return GpioD.lineGetOffset(this);
    }

    public String getName() {
        return GpioD.lineGetName(this);
    }

    public String getConsumer() {
        return GpioD.lineGetConsumer(this);
    }

    public GpioD.LINE_DIRECTION getDirection() {
        return GpioD.lineGetDirection(this);
    }

    public GpioD.LINE_ACTIVE_STATE getActiveState() {
        return GpioD.lineGetActiveState(this);
    }

    public GpioD.LINE_BIAS getBias() {
        return GpioD.lineGetBias(this);
    }

    public boolean isUsed() {
        return GpioD.lineIsUsed(this);
    }

    public boolean isOpenDrain() {
        return GpioD.lineIsOpenDrain(this);
    }

    public boolean isOpenSource() {
        return GpioD.lineIsOpenSource(this);
    }

    public void update() {
        GpioD.lineUpdate(this);
    }

    public void request(GpioLineRequest config, int defaultVal) {
        GpioD.lineRequest(this, config, defaultVal);
    }

    public void requestInput(String consumer) {
        GpioD.lineRequestInput(this, consumer);
    }

    public void requestOutput(String consumer, int defaultVal) {
        GpioD.lineRequestOutput(this, consumer, defaultVal);
    }

    public void requestRisingEdgeEvents(String consumer) {
        GpioD.lineRequestRisingEdgeEvents(this, consumer);
    }

    public void requestFallingEdgeEvents(String consumer) {
        GpioD.lineRequestFallingEdgeEvents(this, consumer);
    }

    public void requestBothEdgeEvents(String consumer) {
        GpioD.lineRequestBothEdgeEvents(this, consumer);
    }

    public void requestInputFlags(String consumer, int flags) {
        GpioD.lineRequestInputFlags(this, consumer, flags);
    }

    public void requestOutputFlags(String consumer, int flags, int defaultValue) {
        GpioD.lineRequestOutputFlags(this, consumer, flags, defaultValue);
    }

    public void requestRisingEdgeEventsFlags(String consumer, int flags) {
        GpioD.lineRequestRisingEdgeEventsFlags(this, consumer, flags);
    }

    public void requestFallingEdgeEventsFlags(String consumer, int flags) {
        GpioD.lineRequestFallingEdgeEventsFlags(this, consumer, flags);
    }

    public void requestBothEdgeEventsFlags(String consumer, int flags) {
        GpioD.lineRequestBothEdgeEventsFlags(this, consumer, flags);
    }

    public void release() {
        GpioD.lineRelease(this);
    }

    public boolean isRequested() {
        return GpioD.lineIsRequested(this);
    }

    public boolean isFree() {
        return GpioD.lineIsFree(this);
    }

    public int getValue() {
        return GpioD.lineGetValue(this);
    }

    public void setValue(int value) {
        GpioD.lineSetValue(this, value);
    }

    public void setConfig(GpioD.LINE_REQUEST direction, int flags, int value) {
        GpioD.lineSetConfig(this, direction, flags, value);
    }

    public void setFlags(int flags) {
        GpioD.lineSetFlags(this, flags);
    }

    public void setDirectionInput() {
        GpioD.lineSetDirectionInput(this);
    }

    public void setDirectionOutput(int value) {
        GpioD.lineSetDirectionOutput(this, value);
    }

    public int eventWait(long timeoutNs) {
        return GpioD.lineEventWait(this, timeoutNs);
    }

    public GpioLineEvent eventRead() {
        GpioLineEvent event = new GpioLineEvent();
        GpioD.lineEventRead(this, event);
        return event;
    }

    public GpioLineEvent[] eventReadMultiple(int maxRead) {
        return GpioD.lineEventReadMultiple(this, maxRead);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GpioLine gpioLine = (GpioLine) o;
        return cPtr == gpioLine.cPtr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cPtr);
    }
}
