package com.pi4j.boardinfo.model;

public class OperatingSystem {

    private final String name;
    private final String version;
    private final String architecture;

    public OperatingSystem(String name, String version, String architecture) {
        this.name = name;
        this.version = version;
        this.architecture = architecture;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getArchitecture() {
        return architecture;
    }

    @Override
    public String toString() {
        return "Name: " + name
            + ", version: " + version
            + ", architecture: " + architecture;
    }
}
