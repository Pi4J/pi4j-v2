package com.pi4j.boardinfo.model;

public class JvmMemory {

    private static double mb = 1024.0 * 1024.0;

    private final long total;
    private final long free;
    private final long used;
    private final long max;

    public JvmMemory(Runtime runtime) {
        total = runtime.totalMemory();
        free = runtime.freeMemory();
        used = runtime.totalMemory() - runtime.freeMemory();
        max = runtime.maxMemory();
    }

    public long getTotal() {
        return total;
    }

    public long getFree() {
        return free;
    }

    public long getUsed() {
        return used;
    }

    public long getMax() {
        return max;
    }

    public double getTotalInMb() {
        return total / mb;
    }

    public double getFreeInMb() {
        return free / mb;
    }

    public double getUsedInMb() {
        return used / mb;
    }

    public double getMaxInMb() {
        return max / mb;
    }
}
