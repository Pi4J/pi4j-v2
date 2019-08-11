package com.pi4j.library.pigpio;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: PIGPIO Library
 * FILENAME      :  PiGpioPacket.java
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 *  typedef struct
 *  {
 *      uint32_t cmd;
 *      uint32_t p1;
 *      uint32_t p2;
 *      union
 *      {
 *          uint32_t p3;
 *          uint32_t ext_len;
 *          uint32_t res;
 *      };
 *  } cmdCmd_t;
 *
 *
 */
public class PiGpioPacket {

    private PiGpioCmd cmd;
    private int p1;
    private int p2;
    private int p3;

    public PiGpioPacket(PiGpioCmd cmd){
        this(cmd, 0, 0, 0);
    }

    public PiGpioPacket(PiGpioCmd cmd, int p1){
        this(cmd, p1, 0, 0);
    }

    public PiGpioPacket(PiGpioCmd cmd, int p1, int p2){
        this(cmd, p1, p2, 0);
    }

    public PiGpioPacket(PiGpioCmd cmd, int p1, int p2, int p3){
        this.cmd = cmd;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public PiGpioPacket(InputStream stream) throws IOException {
        this(stream.readNBytes(stream.available()));
    }

    public PiGpioPacket(byte[] data) throws IOException{
        ByteBuffer rx = ByteBuffer.wrap(data);
        rx.order(ByteOrder.LITTLE_ENDIAN);

        // check data length for minimum package size
        if(data.length < 16){
            throw new IOException("Insufficient number of data bytes bytes received; COUNT=" + data.length);
        }

        this.cmd = PiGpioCmd.from(rx.getInt(0)); // CMD
        this.p1 = rx.getInt(4);  // P1
        this.p2 = rx.getInt(8);  // P2
        this.p3 = rx.getInt(12); // P3

//        System.out.println("BYTES RX : "+  avail);
//        System.out.println(" - [CMD] : " + rx_cmd);
//        System.out.println(" - [P1]  : " + rx_p1);
//        System.out.println(" - [P2]  : " + rx_p2);
//        System.out.println(" - [P3]  : " + rx_p3);
    }

    @Override
    public String toString(){
        return String.format("CMD=%s(%d); P1=%d; P2=%d; P3=%d;", cmd().name(), cmd().value(), p1, p2, p3);
    }

    public PiGpioCmd cmd(){
        return this.cmd;
    }
    public PiGpioPacket cmd(PiGpioCmd cmd){
        this.cmd = cmd;
        return this;
    }

    public int p1(){
        return this.p1;
    }
    public PiGpioPacket p1(int p1){
        this.p1 = p1;
        return this;
    }

    public int p2(){
        return this.p2;
    }
    public PiGpioPacket p2(int p2){
        this.p2 = p2;
        return this;
    }

    public int p3(){
        return this.p3;
    }
    public PiGpioPacket p3(int p3){
        this.p3 = p3;
        return this;
    }

    public int result(){
        return p3();
    }
    public boolean success(){
        return p3() >= 0;
    }

    public byte[] toBytes(){
        // create byte array and byte buffer using LITTLE ENDIAN for the ARM platform
        byte[] bytes = new byte[16];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // place packet values into structured data bytes
        buffer.putInt(cmd().value());  // CMD
        buffer.putInt((p1()));  // <P1>
        buffer.putInt((p2()));  // <P2>
        buffer.putInt((p3()));  // <P3>

        // return byte array
        return bytes;
    }

//    public static int toUInt32(long value){
//        return (int)value;
//    }
//
//    public static long toLong(int x) {
//        return Integer.toUnsignedLong(x);
//    }
}


