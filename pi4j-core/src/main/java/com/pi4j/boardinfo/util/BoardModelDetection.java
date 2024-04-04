package com.pi4j.boardinfo.util;

import com.pi4j.boardinfo.definition.BoardModel;
import com.pi4j.boardinfo.model.DetectedBoard;
import com.pi4j.boardinfo.model.JavaInfo;
import com.pi4j.boardinfo.model.OperatingSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardModelDetection {

    private static final Logger logger = LoggerFactory.getLogger(BoardModelDetection.class);

    private BoardModelDetection() {
        // Hide constructor
    }

    public static DetectedBoard getDetectedBoard() {
        var os = new OperatingSystem(System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"));
        logger.info("Detected OS: {}", os);

        var java = new JavaInfo(System.getProperty("java.version"),
                System.getProperty("java.runtime.version"),
                System.getProperty("java.vendor"),
                System.getProperty("java.vendor.version"));
        logger.info("Detected Java: {}", java);

        // Example output: c03111
        var boardVersionCode = getCommandOutput("cat /proc/cpuinfo | grep 'Revision' | awk '{print $3}'");
        var boardModelByBoardCode = BoardModel.getByBoardCode(boardVersionCode);
        if (boardModelByBoardCode != BoardModel.UNKNOWN) {
            logger.info("Detected board type {} by code: {}", boardModelByBoardCode.name(), boardVersionCode);
            return new DetectedBoard(boardModelByBoardCode, os, java);
        }

        // Example output: Raspberry Pi 4 Model B Rev 1.1
        var boardName = getCommandOutput("cat /proc/device-tree/model");
        boardModelByBoardCode = BoardModel.getByBoardName(boardName);
        if (boardModelByBoardCode != BoardModel.UNKNOWN) {
            logger.info("Detected board type {} by name: {}", boardModelByBoardCode.name(), boardName);
            return new DetectedBoard(boardModelByBoardCode, os, java);
        }

        // Maybe there are other ways how a board can be detected?
        // If so, this method can be further extended...
        logger.warn("Sorry, could not detect the board type");
        return new DetectedBoard(BoardModel.UNKNOWN, os, java);
    }

    private static String getCommandOutput(String command) {
        ExecUtil execUtil = new ExecUtil(command);
        if (!execUtil.isFinished() || !execUtil.getErrorMessage().isEmpty()) {
            logger.error("Could not execute '{}' to detect the board model: {}", command, execUtil.getErrorMessage());
            return "";
        }
        return execUtil.getOutputMessage();
    }
}
