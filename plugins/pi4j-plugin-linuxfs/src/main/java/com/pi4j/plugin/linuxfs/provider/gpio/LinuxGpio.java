package com.pi4j.plugin.linuxfs.provider.gpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: PLUGIN   :: LinuxFS I/O Providers
 * FILENAME      :  LinuxGpio.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import com.pi4j.io.gpio.digital.DigitalState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @see "https://www.kernel.org/doc/Documentation/gpio/sysfs.txt"
 */
public class LinuxGpio {

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

    public LinuxGpio(String systemPath, int address){
        this.address = address;
        this.systemPath = systemPath;
        this.pinPath = Paths.get(systemPath, String.format("gpio%d", address)).toString();
    }

    public LinuxGpio(int address){
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
