package com.pi4j.boardinfo.definition;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InstructionSet {
    ARM_V6_M("ARMv6-M"),
    ARM_V6("ARMv6"),
    ARM_V7("ARMv7"),
    ARM_V8("ARMv8"),
    UNKNOWN("Unknown");

    private final String label;

    InstructionSet(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
