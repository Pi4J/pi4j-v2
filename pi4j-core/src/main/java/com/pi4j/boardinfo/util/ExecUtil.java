package com.pi4j.boardinfo.util;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class ExecUtil {

    private boolean finished = false;
    private String outputMessage = "";
    private String errorMessage = "";

    public ExecUtil(String command) {
        execute(command);
    }

    public boolean isFinished() {
        return finished;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void execute(String command) {
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
    }

    private String readStream(InputStream inputStream) {
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
