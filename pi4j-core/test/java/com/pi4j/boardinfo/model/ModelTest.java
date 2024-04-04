package com.pi4j.boardinfo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelTest {

    @Test
    void testStringOutputFromOperatingSystem() {
        var os = new OperatingSystem("aaa", "bbb", "ccc");
        assertEquals("Name: aaa, version: bbb, architecture: ccc", os.toString());
    }

    @Test
    void testStringOutputFromJavaInfo() {
        var java = new JavaInfo("aaa", "bbb", "ccc", "ddd");
        assertEquals("Version: aaa, runtime: bbb, vendor: ccc, vendor version: ddd", java.toString());
    }
}
