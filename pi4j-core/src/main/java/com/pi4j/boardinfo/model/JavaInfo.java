package com.pi4j.boardinfo.model;

public class JavaInfo {

    private final String version;
    private final String runtime;
    private final String vendor;
    private final String vendorVersion;

    public JavaInfo(String version, String runtime, String vendor, String vendorVersion) {
        this.version = version;
        this.runtime = runtime;
        this.vendor = vendor;
        this.vendorVersion = vendorVersion;
    }

    public String getVersion() {
        return version;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getVendor() {
        return vendor;
    }

    public String getVendorVersion() {
        return vendorVersion;
    }

    @Override
    public String toString() {
        return "Version: " + version
            + ", runtime: " + runtime
            + ", vendor: " + vendor
            + ", vendor version: " + vendorVersion;
    }
}
