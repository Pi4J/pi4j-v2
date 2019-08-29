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
import java.net.SocketException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_HOST;
import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_PORT;

public abstract class PiGpioSocketBase extends PiGpioBase implements PiGpio {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Set<Integer> serialHandles = Collections.synchronizedSet(new HashSet<>());
    protected final Set<Integer> i2cHandles = Collections.synchronizedSet(new HashSet<>());
    protected final Set<Integer> spiHandles = Collections.synchronizedSet(new HashSet<>());
    protected final PiGpioSocketMonitor monitor;

    protected String host = DEFAULT_HOST;
    protected int port = DEFAULT_PORT;
    protected boolean initialized = false;
    protected boolean connected = false;
    protected Socket socket = null;

    // TODO :: IMPLEMENT CONNECTION MONITOR TO PROACTIVELY DETECT SOCKET DISCONNECTS AND AUTO-RETRY TO CONNECT IN BACKGROUND THREAD

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
        this.connected = false;
        this.initialized = false;
        this.monitor = new PiGpioSocketMonitor(this);
    }

    /**
     * Initializes the library.
     * (The Java implementation of this function does not return a value)
     *
     * gpioInitialise must be called before using the other library functions with the following exceptions:
     * - gpioCfg*
     * - gpioVersion
     * - gpioHardwareRevision
     *
     * @see "http://abyz.me.uk/rpi/pigpio/cif.html#gpioInitialise"
     */
    @Override
    public void initialize() throws IOException {
        logger.trace("[INITIALIZE] -> STARTED");
        if(!this.initialized) {
            // add a shutdown hook to perform any required clean up actions
            // when this library is instructed to shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    // properly terminate this library
                    shutdown();
                } catch (Exception e) {
                    logger.warn(e.getMessage() ,e);
                }
            }, "pigpio-shutdown"));

            // set initialized flag
            this.initialized = true;
        }
        else{
            logger.warn("[INITIALIZE] -- ALREADY INITIALIZED");
        }
        logger.trace("[INITIALIZE] <- FINISHED");
    }

    /**
     * Shutdown the library.
     *
     * Returns nothing.
     * Call before program exit.
     * This function resets the used DMA channels, releases memory, and terminates any running threads.
     */
    @Override
    public void shutdown() throws IOException {
        logger.trace("[SHUTDOWN] -> STARTED");
        if(this.initialized) {

            // close all open SPI handles
            spiHandles.forEach((handle) -> {
                try {
                    logger.trace("[SHUTDOWN] -- CLOSING OPEN SPI HANDLE: [{}]", handle);
                    spiClose(handle.intValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // close all open SERIAL handles
            serialHandles.forEach((handle) -> {
                try {
                    logger.trace("[SHUTDOWN] -- CLOSING OPEN SERIAL HANDLE: [{}]", handle);
                    serClose(handle.intValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // close all open I2C handles
            i2cHandles.forEach((handle) -> {
                try {
                    logger.trace("[SHUTDOWN] -- CLOSING OPEN I2C HANDLE: [{}]", handle);
                    i2cClose(handle.intValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // shutdown GPIO notifications monitor
        if(monitor != null && monitor.isConnected())
            monitor.shutdown();

        // shutdown connected socket
        if(socket != null && socket.isConnected())
            socket.close();

        // clear initialized flag
        this.initialized = false;
        logger.trace("[SHUTDOWN] <- FINISHED");
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
        return sendPacket(tx, this.socket);
    }
    protected PiGpioPacket sendPacket(PiGpioPacket tx, Socket sck) throws IOException {
        validateReady();
        try {
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
        catch (SocketException se){
            // socket is no longer connected
            this.connected = false;
            socket.close();
            socket = null;
            throw se;
        }
    }

    public void gpioNotifications(int pin, boolean enabled) throws IOException{
        logger.trace("[GPIO] -> {} Pin [{}] Notifications", (enabled ? "ENABLE" : "DISABLE"), pin);
        validateReady();
        this.monitor.enable(pin, enabled);
        logger.trace("[GPIO] <- Pin [PIN {}] Notifications [{}]", pin, (enabled ? "ENABLED" : "DISABLED"));
    }

    protected void disableNotifications() throws IOException {
        logger.trace("[GPIO] -> DISABLE ALL Pin Notifications");
        validateReady();
        this.monitor.disable();
        logger.trace("[GPIO] <- All Pin Notifications are DISABLED");
    }

    protected void validateReady() throws IOException {
        validateInitialized();
        validateConnection();
    }

    protected void validateInitialized() throws IOException {
        if(!this.initialized)
            throw new IOException("PIGPIO NOT INITIALIZED; make sure you call the PiGpio::initialize() function first.");
    }

    protected void validateConnection() throws IOException {
        // if not connected, attempt to reconnect
        if(socket == null || !this.connected){
            // attempt to connect to PiGpio Daemon on remote Raspberry Pi
            this.socket = new Socket(host, port);

            // update connection status flag
            this.connected = this.socket.isConnected();
        }
//            throw new IOException("PIGPIO NOT CONNECTED TO REMOTE HOST [" + this.host + ":" + this.port +
//                    "]; make sure the PiGpio Daemon is running on the remote Raspberry Pi and the host is accessible.");
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
