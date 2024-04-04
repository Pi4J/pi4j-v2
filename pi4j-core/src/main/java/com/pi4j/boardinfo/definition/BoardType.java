package com.pi4j.boardinfo.definition;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoardType {
    ALL_IN_ONE_COMPUTER,
    MICROCONTROLLER,
    SINGLE_BOARD_COMPUTER,
    STACK_ON_COMPUTER,
    UNKNOWN
}
