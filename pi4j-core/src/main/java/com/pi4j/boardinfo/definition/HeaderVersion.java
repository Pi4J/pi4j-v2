package com.pi4j.boardinfo.definition;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HeaderVersion {
    PICO("Pico", "Used on the Pico microcontroller", new ArrayList<>()),
    TYPE_1("Type 1", "Used on original Model B", Collections.singletonList(HeaderPins.HEADER_26_TYPE_1)),
    TYPE_2("Type 2", "Used on Model A and Model B (revision 2)", Arrays.asList(HeaderPins.HEADER_26_TYPE_2, HeaderPins.HEADER_8)),
    TYPE_3("Type 3", "Used on Model A+, B+, Pi Zero, Pi Zero W, Pi2B, Pi3B, Pi4B", Collections.singletonList(HeaderPins.HEADER_40)),
    COMPUTE("Compute Module", "54 GPIO", Arrays.asList(HeaderPins.COMPUTE_J5, HeaderPins.COMPUTE_J6)),
    UNKNOWN("Unknown", "", new ArrayList<>());

    private final String label;
    private final String description;
    private final List<HeaderPins> headerPins;

    HeaderVersion(String label, String description, List<HeaderPins> headerPins) {
        this.label = label;
        this.description = description;
        this.headerPins = headerPins;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public List<HeaderPins> getHeaderPins() {
        return headerPins;
    }
}
