package com.pi4j.boardinfo.model;

import com.pi4j.boardinfo.definition.BoardModel;

public class DetectedBoard {

    private final BoardModel boardModel;
    private final OperatingSystem operatingSystem;
    private final JavaInfo javaInfo;

    public DetectedBoard(BoardModel boardModel, OperatingSystem operatingSystem, JavaInfo javaInfo) {
        this.boardModel = boardModel;
        this.operatingSystem = operatingSystem;
        this.javaInfo = javaInfo;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public JavaInfo getJavaInfo() {
        return javaInfo;
    }
}
