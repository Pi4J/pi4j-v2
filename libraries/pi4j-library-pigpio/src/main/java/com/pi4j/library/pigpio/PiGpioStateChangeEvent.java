package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioStateChangeEvent.java
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

public class PiGpioStateChangeEvent {

    protected final PiGpioState state;
    protected final int pin;
    protected final long sequence;
    protected final long flags;
    protected final long tick;

    /**
     * Default constructor
     * @param state the value changed for this event instance
     */
    public PiGpioStateChangeEvent(final int pin,
                                  final PiGpioState state,
                                  final long sequence,
                                  final long flags,
                                  final long tick){
        this.state = state;
        this.pin = pin;
        this.sequence = sequence;
        this.flags = flags;
        this.tick = tick;
    }

    /**
     * The new state of this event
     * @return
     */
    public PiGpioState state() {
        return this.state;
    }

    /**
     * The pin number for this event
     * @return
     */
    public int pin() {
        return this.pin;
    }

    /**
     * The sequence number for this event
     * @return
     */
    public long sequence() {
        return this.sequence;
    }

    /**
     * The flags value for this event
     * @return
     */
    public long flags() {
        return this.flags;
    }

    /**
     * The tick time for this event
     * @return
     */
    public long tick() {
        return this.tick;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("<<STATE CHANGE EVENT>> PIN [");
        result.append(this.pin());
        result.append("] STATE: [");
        result.append(this.state().name());
        result.append("] (SEQ=");
        result.append(this.sequence());
        result.append("; TICK=");
        result.append(this.tick());
        result.append("; FLAGS=");
        result.append(this.flags());
        result.append(")");
        return result.toString();
    }
}
