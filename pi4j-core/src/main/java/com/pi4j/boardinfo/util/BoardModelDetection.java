package com.pi4j.boardinfo.util;

import com.pi4j.boardinfo.definition.BoardModel;
import com.pi4j.boardinfo.model.BoardInfo;
import com.pi4j.boardinfo.model.JavaInfo;
import com.pi4j.boardinfo.model.OperatingSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class BoardModelDetection {

    private static final Logger logger = LoggerFactory.getLogger(BoardModelDetection.class);

    private static final BoardModelDetection instance;
    private final BoardInfo boardInfo;

    static {
        instance = new BoardModelDetection();
    }

    private BoardModelDetection() {
        var os = new OperatingSystem(System.getProperty("os.name"), System.getProperty("os.version"),
            System.getProperty("os.arch"));
        logger.info("Detected OS: {}", os);

        var java = new JavaInfo(System.getProperty("java.version"), System.getProperty("java.runtime.version"),
            System.getProperty("java.vendor"), System.getProperty("java.vendor.version"));
        logger.info("Detected Java: {}", java);

        // Example output: c03111
        var boardVersionCode = getBoardVersionCode();
        var boardModelByBoardCode = BoardModel.getByBoardCode(boardVersionCode);
        if (boardModelByBoardCode != BoardModel.UNKNOWN) {
            logger.info("Detected board type {} by code: {}", boardModelByBoardCode.name(), boardVersionCode);
            this.boardInfo = new BoardInfo(boardModelByBoardCode, os, java);
            return;
        }

        // Example output: Raspberry Pi 4 Model B Rev 1.1
        var boardName = getBoardName();
        boardModelByBoardCode = BoardModel.getByBoardName(boardName);
        if (boardModelByBoardCode != BoardModel.UNKNOWN) {
            logger.info("Detected board type {} by name: {}", boardModelByBoardCode.name(), boardName);
            this.boardInfo = new BoardInfo(boardModelByBoardCode, os, java);
            return;
        }

        // Maybe there are other ways how a board can be detected?
        // If so, this method can be further extended...
        logger.warn("Sorry, could not detect the board type");
        this.boardInfo = new BoardInfo(BoardModel.UNKNOWN, os, java);
    }

    public static BoardInfo current() {
        return instance.boardInfo;
    }

    public static String getBoardVersionCode() {
        var output = getCommandOutput("cat /proc/cpuinfo | grep 'Revision' | awk '{print $3}'");
        if (output.isSuccess()) {
            return output.getOutputMessage();
        }
        logger.error("Could not get the board version code: {}", output.getErrorMessage());
        return "";
    }

    public static String getBoardName() {
        var output = getCommandOutput("cat /proc/device-tree/model");
        if (output.isSuccess()) {
            return output.getOutputMessage();
        }
        logger.error("Could not get the board name: {}", output.getErrorMessage());
        return "";
    }

    private static class CommandResult {
        private final boolean success;
        private final String outputMessage;
        private final String errorMessage;

        public CommandResult(boolean success, String outputMessage, String errorMessage) {
            this.success = success;
            this.outputMessage = outputMessage;
            this.errorMessage = errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getOutputMessage() {
            return outputMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    private static CommandResult getCommandOutput(String command) {
        boolean finished = false;
        String outputMessage = "";
        String errorMessage = "";

        ProcessBuilder builder = new ProcessBuilder();
        builder.command("sh", "-c", command);

        try {
            Process process = builder.start();

            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            outputMessage = readStream(inputStream);
            errorMessage = readStream(errorStream);

            finished = process.waitFor(30, TimeUnit.SECONDS);
            outputStream.flush();
            outputStream.close();

            if (!finished) {
                process.destroyForcibly();
            }
        } catch (IOException ex) {
            errorMessage = "IOException: " + ex.getMessage();
        } catch (InterruptedException ex) {
            errorMessage = "InterruptedException: " + ex.getMessage();
        }

        if (!finished || !errorMessage.isEmpty()) {
            logger.error("Could not execute '{}' to detect the board model: {}", command, errorMessage);
            return new CommandResult(false, outputMessage, errorMessage);
        }

        return new CommandResult(true, outputMessage, errorMessage);
    }

    private static String readStream(InputStream inputStream) {
        StringBuilder rt = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rt.append(line);
            }
        } catch (Exception ex) {
            rt.append("ERROR: ").append(ex.getMessage());
        }
        return rt.toString();
    }
}
