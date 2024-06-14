package com.pi4j.boardinfo.definition;

/**
 * List of pin types in a header.
 */
public enum PinType {
    POWER("Power", 0x990000),
    GROUND("Ground", 0x000000),
    DIGITAL("Digital", 0x009900),
    DIGITAL_AND_PWM("Digital and PWM", 0xff7f00),
    DIGITAL_NO_PULL_DOWN("Digital without pulldown", 0x800080);

    private final String label;
    private final int color;

    PinType(String label, int color) {
        this.label = label;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public int getColor() {
        return color;
    }
}
