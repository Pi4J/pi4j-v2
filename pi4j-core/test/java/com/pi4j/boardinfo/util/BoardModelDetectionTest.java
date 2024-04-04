package com.pi4j.boardinfo.util;

import com.pi4j.boardinfo.definition.PiModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardModelDetectionTest {

    @Test
    void testGetDetectedBoard() {
        var detectedBoard = BoardModelDetection.getDetectedBoard();

        assertAll(
                () -> assertEquals(detectedBoard.getOperatingSystem().getName(), System.getProperty("os.name")),
                () -> assertEquals(detectedBoard.getOperatingSystem().getVersion(), System.getProperty("os.version")),
                () -> assertEquals(detectedBoard.getOperatingSystem().getArchitecture(), System.getProperty("os.arch")),

                () -> assertEquals(detectedBoard.getJavaInfo().getVersion(), System.getProperty("java.version")),
                () -> assertEquals(detectedBoard.getJavaInfo().getRuntime(), System.getProperty("java.runtime.version")),
                () -> assertEquals(detectedBoard.getJavaInfo().getVendor(), System.getProperty("java.vendor")),
                () -> assertEquals(detectedBoard.getJavaInfo().getVendorVersion(), System.getProperty("java.vendor.version")),

                () -> assertEquals(detectedBoard.getBoardModel().getModel(), PiModel.UNKNOWN) // Only valid on PC, macOS or build server
        );
    }
}
