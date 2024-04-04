package com.pi4j.boardinfo.definition;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Soc {
    BCM2710A1(InstructionSet.ARM_V8),
    BCM2711(InstructionSet.ARM_V8),
    BCM2711C0(InstructionSet.ARM_V8),
    BCM2712(InstructionSet.ARM_V8),
    BCM2835(InstructionSet.ARM_V6),
    BCM2836(InstructionSet.ARM_V7),
    BCM2837(InstructionSet.ARM_V8),
    BCM2837B0(InstructionSet.ARM_V8),
    RP2040(InstructionSet.ARM_V6_M),
    UNKNOWN(InstructionSet.UNKNOWN);

    private final InstructionSet instructionSet;

    Soc(InstructionSet instructionSet) {
        this.instructionSet = instructionSet;
    }

    public InstructionSet getInstructionSet() {
        return instructionSet;
    }
}
