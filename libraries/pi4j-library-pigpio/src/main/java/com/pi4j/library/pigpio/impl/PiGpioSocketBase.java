package com.pi4j.library.pigpio.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioSocketBase.java
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

import com.pi4j.library.pigpio.PiGpio;
import com.pi4j.library.pigpio.PiGpioCmd;
import com.pi4j.library.pigpio.PiGpioPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_HOST;
import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_PORT;

public abstract class PiGpioSocketBase extends PiGpioBase implements PiGpio {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Set<Integer> serialHandles = Collections.synchronizedSet(new HashSet<>());
    protected Set<Integer> i2cHandles = Collections.synchronizedSet(new HashSet<>());
    protected Set<Integer> spiHandles = Collections.synchronizedSet(new HashSet<>());

    protected boolean initialized = false;
    protected Socket socket = null;
    protected String host = DEFAULT_HOST;
    protected int port = DEFAULT_PORT;

    protected final PiGpioSocketMonitor monitor;

    // 32 bits are used to store the last known states of pins 0-31
    //protected int pinState = 0b00000000000000000000000000000000;
    //protected int pinMonitor = 0b00000000000000000000000000000000;

    /**
     * ALTERNATE CONSTRUCTOR
     *
     * Connects to a user specified socket hostname/ip address and port.
     *
     * @param host hostname or IP address of the RaspberryPi to connect to via TCP/IP socket.
     * @param port TCP port number of the RaspberryPi to connect to via TCP/IP socket.
     * @throws IOException
     */
    protected PiGpioSocketBase(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.socket = new Socket(host, port);
        this.monitor =  new PiGpioSocketMonitor(this);
    }

    @Override
    public void shutdown() throws IOException {
        if (this.initialized) {
            // shutdown GPIO state monitor
            if(monitor != null) monitor.shutdown();
        }
    }

    protected PiGpioPacket sendCommand(PiGpioCmd cmd) throws IOException {
        return sendPacket(new PiGpioPacket(cmd));
    }

    protected PiGpioPacket sendCommand(PiGpioCmd cmd, int p1) throws IOException {
        return sendPacket(new PiGpioPacket(cmd, p1));
    }
    protected PiGpioPacket sendCommand(PiGpioCmd cmd, int p1, int p2) throws IOException {
        return sendPacket(new PiGpioPacket(cmd, p1, p2));
    }
    protected PiGpioPacket sendPacket(PiGpioPacket tx) throws IOException {

        // get socket streams
        var in = socket.getInputStream();
        var out = socket.getOutputStream();

        // transmit packet
        logger.trace("[TX] -> " + tx.toString());
        out.write(PiGpioPacket.encode(tx));
        out.flush();

        // wait until data has been received (timeout after 500 ms and throw exception)
        int millis = 0;
        try {
            while(in.available() < 16){
                if(millis > 500){   // timeout exception
                    throw new IOException("Command timed out; no response from host in 500 milliseconds");
                }
                millis+=5;
                Thread.sleep(5); // ... take a breath ..
            }
        } catch (InterruptedException e) {
            // wrap exception
            throw new IOException(e.getMessage(), e);
        }

        // read receive packet
        PiGpioPacket rx = PiGpioPacket.decode(in);
        logger.trace("[RX] <- " + rx.toString());
        return rx;
    }
    protected PiGpioPacket sendPacket(PiGpioPacket tx, Socket sck) throws IOException {

        // get socket streams
        var in = sck.getInputStream();
        var out = sck.getOutputStream();

        // transmit packet
        logger.trace("[TX] -> " + tx.toString());
        out.write(PiGpioPacket.encode(tx));
        out.flush();

        // wait until data has been received (timeout after 500 ms and throw exception)
        int millis = 0;
        try {
            while(in.available() < 16){
                if(millis > 500){   // timeout exception
                    throw new IOException("Command timed out; no response from host in 500 milliseconds");
                }
                millis+=5;
                Thread.sleep(5); // ... take a breath ..
            }
        } catch (InterruptedException e) {
            // wrap exception
            throw new IOException(e.getMessage(), e);
        }

        // read receive packet
        PiGpioPacket rx = PiGpioPacket.decode(in);
        logger.trace("[RX] <- " + rx.toString());
        return rx;
    }

    public void gpioNotifications(int pin, boolean enabled) throws IOException{
        this.monitor.enable(pin, enabled);
    }

    protected void disableNotifications() throws IOException {
        this.monitor.disable();
    }

//    protected void enableNotifications() throws IOException {
////        PiGpioPacket noib = new PiGpioPacket(NOIB);
//        var listener = new Socket(this.host, this.port);
//
//        //listener.getOutputStream().write(PiGpioPacket.encode(noib));
//
//        // get the current pin states for pins 0-31
//        PiGpioPacket tx = new PiGpioPacket(BR1);
//        PiGpioPacket rx = sendPacket(tx);
//        pinState = rx.p3();
//
//        ByteBuffer b = ByteBuffer.allocate(4);
//        b.order(ByteOrder.LITTLE_ENDIAN);
//        b.putInt(rx.p3());
//        System.out.println("[STATES] [" + Integer.toBinaryString(rx.p3()) + "]");
//
//        tx = new PiGpioPacket(NOIB);
//        rx = sendPacket(tx, listener);
//
//        tx = new PiGpioPacket(NB, rx.p3(), pinMonitor);
//        sendPacket(tx, listener);
//
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    var in = listener.getInputStream();
//                    System.out.println("START MONITOR");
//                    while(initialized && pinMonitor != 0) {
//                        var available = in.available();
//                        if(in.available() >= 12) {
//                            byte[] raw = in.readNBytes(12);
//                            ByteBuffer buffer = ByteBuffer.wrap(raw);
//                            buffer.order(ByteOrder.LITTLE_ENDIAN);
//
//                            final long sequence = Integer.toUnsignedLong(buffer.getShort());
//                            final long flags = Integer.toUnsignedLong(buffer.getShort());
//                            final long tick = Integer.toUnsignedLong(buffer.getInt());
//                            final int level = buffer.getInt();
//                            final BitSet levels =  BitSet.valueOf(buffer.array());
//
//
//                            System.out.println("[BYTES] [0x" + StringUtil.toHexString(buffer) + "]");
//                            System.out.println("[SEQUE] " + sequence);
//                            System.out.println("[FLAGS] " + flags);
//                            System.out.println("[TICK ] " + tick);
//                            System.out.println("[LEVEL] " + Integer.toBinaryString(level));
//                            System.out.println("[STATE] " + BitSet.valueOf(raw).get(0));
//                            System.out.println("--------------------------------------------------------");
//
//
//                            for (int i = 0; i < 32; i++) {
//
//                                int oldBit = (pinState >> i) & 1;
//                                int newBit = (level >> i) & 1;
//
//                                if(oldBit != newBit){
//                                    System.out.println("[BIT" + i + "] " + oldBit + " > " + newBit);
//                                    final PiGpioState state = PiGpioState.from(newBit);
//                                    final PiGpioStateChangeEvent event = new PiGpioStateChangeEvent(i, state, sequence, flags, tick);
//                                    dispatchEvent(event);
//                                }
//                            }
//
//                            // sync last known states
//                            pinState = level;
//                        }
//                    }
//                    listener.close();
//                    System.out.println("STOP MONITOR");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

}
