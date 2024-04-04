package com.pi4j.boardinfo.definition;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Cpu {
    ARM1176JZF_S("ARM1176JZF-S"),
    CORTEX_A53("Cortex-A53"),
    CORTEX_A7("Cortex-A7"),
    CORTEX_A72("Cortex-A72"),
    CORTEX_A76("Cortex-A76"),
    CORTEX_MO_PLUS("Cortex-M0+"),
    UNKNOWN("Unknown"),
    ;

    private final String label;

    Cpu(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
