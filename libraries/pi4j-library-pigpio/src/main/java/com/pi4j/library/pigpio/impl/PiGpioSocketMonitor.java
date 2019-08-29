package com.pi4j.library.pigpio.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioSocketMonitor.java
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

import com.pi4j.library.pigpio.PiGpioCmd;
import com.pi4j.library.pigpio.PiGpioPacket;
import com.pi4j.library.pigpio.PiGpioState;
import com.pi4j.library.pigpio.PiGpioStateChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PiGpioSocketMonitor  {

    public static String NAME = "pigpio-monitor";

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final PiGpioSocketBase piGpio;
    protected Socket listener = null;
    protected boolean shutdown = false;
    protected Integer handle = null;
    protected Thread monitoringThread = null;

    // 32 bits are used to store the last known states of pins 0-31
    protected int pinState = 0b00000000000000000000000000000000;
    protected int pinMonitor = 0b00000000000000000000000000000000;


    public PiGpioSocketMonitor(PiGpioSocketBase piGpio) throws IOException {
        this.piGpio = piGpio;
    }

    public void shutdown(){
        this.shutdown = true;
        try {
            disable();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return (listener != null && listener.isConnected());
    }

    public void enable(int pin, boolean enabled) throws IOException{

        // update pin monitor
        if(enabled) {
            pinMonitor |= 1 << pin;
        } else {
            pinMonitor &= ~(1 << pin);
        }

        // start the monitoring thread if its not currently running
        if(pinMonitor != 0){
            if(monitoringThread == null || !monitoringThread.isAlive()){
                startMonitoringThread();
            } else {
                // update specific pin set to monitor
                PiGpioPacket tx = new PiGpioPacket(PiGpioCmd.NB, this.handle, pinMonitor);
                piGpio.sendPacket(tx, listener);
            }
        }
    }

    protected void disable() throws IOException {
        // reset pin monitoring flags
        pinMonitor = 0b00000000000000000000000000000000;
    }

    private void startMonitoringThread(){
        // create monitoring thread
        monitoringThread = new Thread(NAME) {
            @Override
            public void run() {
                logger.trace("[THREAD] STARTED");

                // continue running this thread until this monitor is 'shutdown'
                while (!shutdown && pinMonitor > 0){

                    // create new listener socket instance
                    try {
                        logger.debug("[SOCKET] attempting to connect to: {}:{}", piGpio.host, piGpio.port);
                        listener = new Socket(piGpio.host, piGpio.port);
                        listener.setSoTimeout(1000);

                        // check to see if we connected successfully
                        if(listener.isConnected()) {

                            logger.debug("[SOCKET] successfully connected");

                            // get the current pin states for pins 0-31
                            PiGpioPacket tx = new PiGpioPacket(PiGpioCmd.BR1);
                            PiGpioPacket rx = piGpio.sendPacket(tx, listener);
                            pinState = rx.p3();
                            logger.trace("[GPIO] current pin states [BR1] <{}>", Integer.toBinaryString(pinState));

//                        ByteBuffer b = ByteBuffer.allocate(4);
//                        b.order(ByteOrder.LITTLE_ENDIAN);
//                        b.putInt(rx.p3());
//                        System.out.println("[STATES] [" + Integer.toBinaryString(rx.p3()) + "]");

                            // enable socket notifications for pins 0-31
                            tx = new PiGpioPacket(PiGpioCmd.NOIB);
                            rx = piGpio.sendPacket(tx, listener);
                            handle = rx.p3();
                            logger.trace("[NOTIFY] enable socket notifications [NOIB]; HANDLE={}", handle);

                            // enable specific pin set to monitor
                            tx = new PiGpioPacket(PiGpioCmd.NB, handle, pinMonitor);
                            piGpio.sendPacket(tx, listener);
                            logger.trace("[NOTIFY] enable pin notifications [NB] <{}>", Integer.toBinaryString(pinMonitor));

                            // get the input stream from the listener socket
                            var in = listener.getInputStream();

                            boolean disconnected = false;
                            byte[] raw = new byte[12];

                            // continue reading from the socket until the socket has become disconnected,
                            // this monitor is being shutdown or until no pins are actively being monitored
                            while (!disconnected && !shutdown && pinMonitor != 0) {

                                try {
                                    int result = in.read(raw, 0, raw.length);

                                    // check for end of stream error code
                                    if(result == -1){
                                        logger.warn("[SOCKET] failed to read socket stream; socket is not connected.");
                                        disconnected = true;   // set disconnected flag
                                        if(!listener.isClosed()) listener.close();  // close listener socket
                                        break;
                                    }

                                    // process the data read buffer if we have a sufficient number of bytes
                                    if (result >= 12) {
                                        // wrap the raw data in a byte buffer to process/decode the results (Little Endian)
                                        ByteBuffer buffer = ByteBuffer.wrap(raw);
                                        buffer.order(ByteOrder.LITTLE_ENDIAN);

                                        // decode each event data element from the buffer
                                        final long sequence = Integer.toUnsignedLong(buffer.getShort());
                                        final long flags = Integer.toUnsignedLong(buffer.getShort());
                                        final long tick = Integer.toUnsignedLong(buffer.getInt());
                                        final int newPinState = buffer.getInt();

//                        final BitSet levels = BitSet.valueOf(buffer.array());
//                        System.out.println("[BYTES] [0x" + StringUtil.toHexString(buffer) + "]");
//                        System.out.println("[SEQUE] " + sequence);
//                        System.out.println("[FLAGS] " + flags);
//                        System.out.println("[TICK ] " + tick);
//                        System.out.println("[LEVEL] " + Integer.toBinaryString(level));
//                        System.out.println("[STATE] " + BitSet.valueOf(raw).get(0));
//                        System.out.println("--------------------------------------------------------");
                                        logger.trace("[NOTIFY] SEQ={}; FLAGS={}; TICK={}; STATES=[{}]",
                                                sequence, flags, tick, Integer.toBinaryString(newPinState));

                                        // iterate over the 32 bits in the level value
                                        for (int i = 0; i < 32; i++) {
                                            // determine if this pin is enabled for events
                                            // if it is, then process the event; else ignore it
                                            int pinNotify = (pinMonitor >> i) & 1;
                                            if(pinNotify > 0) {
                                                // get the states for this pin from the 32-bit value
                                                int oldState = (pinState >> i) & 1;
                                                int newState = (newPinState >> i) & 1;

                                                // compare the old state value with the new state value to determine if there is a change
                                                if (oldState != newState) {
                                                    // if there is a change detected, then create a new change event and dispatch it
                                                    final PiGpioState state = PiGpioState.from(newState);
                                                    final PiGpioStateChangeEvent event = new PiGpioStateChangeEvent(i, state, sequence, flags, tick);
                                                    logger.trace("[DISPATCH] PiGpioStateChangeEvent(PIN={}; FLAGS={}; TICK={}; STATE=[{}]",
                                                            i, flags, tick, newPinState);
                                                    piGpio.dispatchEvent(event);
                                                }
                                            }
                                        }

                                        // cache the new;y read pins states for future comparisons
                                        pinState = newPinState;
                                    }
                                }
                                catch (SocketTimeoutException ste){
                                    // ignore this timeout and continue reading
                                }
                            }

                            // if the socket is not disconnected, then attempt to send a few clean up
                            // instructions to the PiGpio daemon to disable notifications for this socket
                            if(!disconnected) {
                                // disable pin monitoring on socket for all pins (0-32)
                                logger.trace("[NOTIFY] disable pin notifications [NB] <ALL PINS 0-31>");
                                tx = new PiGpioPacket(PiGpioCmd.NB, handle, 0b00000000000000000000000000000000);
                                piGpio.sendPacket(tx, listener);

                                // close the notification handle
                                logger.trace("[NOTIFY] disable socket notifications [NC]; HANDLE={}", handle);
                                tx = new PiGpioPacket(PiGpioCmd.NC, handle);
                                piGpio.sendPacket(tx, listener);
                            }
                        }
                        else {
                            logger.warn("[SOCKET] Listener socket failed to connect");
                        }
                    }
                    catch (SocketException se) {
                        // the listener socket was closed
                    }
                    catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }

                    // clear handle
                    handle = null;

                    // make sure the listener is closed at this point
                    if(!listener.isClosed()) {
                        try {
                            listener.close();
                        } catch (IOException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                    // attempt to reconnect if we are not shutting down
                    if(!shutdown && pinMonitor > 0) {
                        // sleep for 5 seconds
                        try {
                            logger.debug("[SOCKET] will attempt to reconnect in 5 seconds");
                            for(int i = 0; i < 50; i++) {
                                Thread.sleep(100);
                                if(shutdown) break;
                            }

                        } catch (InterruptedException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                logger.debug("[THREAD] ENDED");
            }
        };

        // start thread
        monitoringThread.start();
    }
}
