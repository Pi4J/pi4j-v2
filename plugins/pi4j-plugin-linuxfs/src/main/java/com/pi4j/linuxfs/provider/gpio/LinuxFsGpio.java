package com.pi4j.linuxfs.provider.gpio;

import com.pi4j.io.gpio.digital.DigitalState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @see "https://www.kernel.org/doc/Documentation/gpio/sysfs.txt"
 */
public class LinuxFsGpio {

    public static String DEFAULT_SYSTEM_PATH = "/sys/class/gpio";

    protected final String systemPath;
    protected final int address;
    protected final String pinPath;

    public enum Direction{
        IN,
        OUT,
        UNKNOWN
    }

    public enum Edge{
        NONE,
        RISING,
        FALLING,
        BOTH,
        UNKNOWN
    }

    public LinuxFsGpio(String systemPath, int address){
        this.address = address;
        this.systemPath = systemPath;
        this.pinPath = Paths.get(systemPath, String.format("gpio%d", address)).toString();
    }

    public LinuxFsGpio(int address){
        this(DEFAULT_SYSTEM_PATH, address);
    }

    /**
     * Export GPIO pin by SoC address
     */
    public void export() throws IOException {
        var path = Paths.get(systemPath, "export");
        Files.writeString(path, Integer.toString(address));
    }

    public void unexport() throws IOException {
        var path = Paths.get(systemPath, "unexport");
        Files.writeString(path, Integer.toString(address));
    }

    public boolean isExported() throws IOException {
        return Files.exists(Paths.get(pinPath));
    }

    public boolean isInterruptSupported() throws IOException {
        return Files.exists(Paths.get(pinPath, "edge"));
    }

    public void direction(Direction direction) throws IOException {
        setDirection(direction);
    }
    public void setDirection(Direction direction) throws IOException {
        var path = Paths.get(pinPath, "direction");
        Files.writeString(path, direction.name().toLowerCase());
    }

    public Direction direction() throws IOException {
        return getDirection();
    }
    public Direction getDirection() throws IOException {
        var path = Paths.get(pinPath, "direction");
        switch(Files.readString(path).trim().toLowerCase()){
            case "in": return Direction.IN;
            case "out": return Direction.OUT;
            default: return Direction.UNKNOWN;
        }
    }

    public void state(DigitalState state) throws IOException {
        setState(state);
    }
    public void setState(DigitalState state) throws IOException {
        var path = Paths.get(pinPath,"value");
        Files.writeString(path, (state.isHigh() ? "1" : "0"));
    }

    public DigitalState state() throws IOException {
        return getState();
    }
    public DigitalState getState() throws IOException {
        var path = Paths.get(pinPath,"value");
        return DigitalState.parse(Files.readString(path).trim());
    }

    public void interruptEdge(Edge edge) throws IOException {
        setInterruptEdge(edge);
    }
    public void setInterruptEdge(Edge edge) throws IOException {
        var path = Paths.get(pinPath, "edge");
        Files.writeString(path, edge.name().toLowerCase());
    }


    public Edge interruptEdge() throws IOException {
        return getInterruptEdge();
    }
    public Edge getInterruptEdge() throws IOException {
        var path = Paths.get(pinPath, "edge");
        switch(Files.readString(path).trim().toLowerCase()){
            case "none": return Edge.NONE;
            case "rising": return Edge.RISING;
            case "falling": return Edge.FALLING;
            case "both": return Edge.BOTH;
            default: return Edge.UNKNOWN;
        }
    }


    public void activeLow(boolean enabled) throws IOException {
        setActiveLow(enabled);
    }
    public void setActiveLow(boolean enabled) throws IOException {
        var path = Paths.get(pinPath,"active_low");
        Files.writeString(path, (enabled ? "1" : "0"));
    }

    public boolean activeLow() throws IOException {
        return getActiveLow();
    }
    public boolean getActiveLow() throws IOException {
        var path = Paths.get(pinPath,"active_low");
        return Files.readString(path).trim().equalsIgnoreCase("1");
    }
}
