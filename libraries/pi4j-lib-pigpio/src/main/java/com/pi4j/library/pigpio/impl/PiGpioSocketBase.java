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

import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_HOST;
import static com.pi4j.library.pigpio.PiGpioConst.DEFAULT_PORT;

public abstract class PiGpioSocketBase extends PiGpioBase implements PiGpio {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Socket socket = null;
    protected String host = DEFAULT_HOST;
    protected int port = DEFAULT_PORT;

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
        this.socket = new Socket(host, port);
    }

    protected PiGpioPacket sendCommand(PiGpioCmd cmd) throws IOException {
        return sendCommand(new PiGpioPacket(cmd));
    }
    protected PiGpioPacket sendCommand(PiGpioCmd cmd, int p1) throws IOException {
        return sendCommand(new PiGpioPacket(cmd, p1));
    }
    protected PiGpioPacket sendCommand(PiGpioCmd cmd, int p1, int p2) throws IOException {
        return sendCommand(new PiGpioPacket(cmd, p1, p2));
    }
    protected PiGpioPacket sendCommand(PiGpioCmd cmd, int p1, int p2, byte data) throws IOException {
        return sendCommand(new PiGpioPacket(cmd, p1, p2).data(data));
    }
    protected PiGpioPacket sendCommand(PiGpioCmd cmd, int p1, int p2, byte[] data) throws IOException {
        return sendCommand(new PiGpioPacket(cmd, p1, p2, data));
    }
    protected PiGpioPacket sendCommand(PiGpioPacket tx) throws IOException {

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
}
