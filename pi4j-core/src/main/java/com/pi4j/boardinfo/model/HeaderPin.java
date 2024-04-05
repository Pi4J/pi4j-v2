package com.pi4j.boardinfo.model;

import com.pi4j.boardinfo.definition.PinFunction;
import com.pi4j.boardinfo.definition.PinType;

/**
 * Describes a pin in the header.
 */
public class HeaderPin {
    private final int pinNumber;
    private final PinType pinType;
    private final PinFunction pinFunction;
    private final Integer bcmNumber;
    private final Integer wiringPiNumber;
    private final String name;
    private final String remark;

    public HeaderPin(int pinNumber, PinType pinType, String name) {
        this(pinNumber, pinType, null, null, null, name, "");
    }

    public HeaderPin(int pinNumber, PinType pinType, PinFunction pinFunction, Integer bcmNumber, Integer wiringPiNumber, String name) {
        this(pinNumber, pinType, pinFunction, bcmNumber, wiringPiNumber, name, "");
    }

    public HeaderPin(int pinNumber, PinType pinType, PinFunction pinFunction, Integer bcmNumber, Integer wiringPiNumber, String name, String remark) {
        this.pinNumber = pinNumber;
        this.pinType = pinType;
        this.pinFunction = pinFunction;
        this.bcmNumber = bcmNumber;
        this.wiringPiNumber = wiringPiNumber;
        this.name = name;
        this.remark = remark;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public PinType getPinType() {
        return pinType;
    }

    public PinFunction getPinFunction() { return pinFunction; }

    public Integer getBcmNumber() { return bcmNumber; }

    public Integer getWiringPiNumber() {
        return wiringPiNumber;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }
}
