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

import com.pi4j.library.pigpio.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
    private int p1 = 0;
    private int p2 = 0;
    private int p3 = 0;
    private byte[] data = new byte[0];

    public PiGpioPacket(){
    }

    public PiGpioPacket(PiGpioCmd cmd){
        this.cmd(cmd);
    }

    public PiGpioPacket(PiGpioCmd cmd, int p1){
        this.cmd(cmd).p1(p1);
    }

    public PiGpioPacket(PiGpioCmd cmd, int p1, int p2){
        this.cmd(cmd).p1(p1).p2(p2);
    }

    public PiGpioPacket(PiGpioCmd cmd, int p1, int p2, byte[] data){
        this.cmd(cmd).p1(p1).p2(p2).data(data);
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
    protected PiGpioPacket p3(int p3){
        this.p3 = p3;
        return this;
    }

    // the following methods are actaully returning the "P3" value as P3
    // is a C union and used for multiple purposes depending on context
    public int result(){
        return p3();
    }
    public boolean success(){
        return p3() >= 0;
    }

    public boolean hasData(){
        if(this.data == null) return false;
        return this.data.length > 0;
    }
    public int dataLength(){
        if(this.data == null) return 0;
        return this.data.length;
    }

    public byte[] data(){
        return this.data;
    }

    public PiGpioPacket data(CharSequence data){
        return this.data(data.toString().getBytes(StandardCharsets.US_ASCII));
    }

    public PiGpioPacket data(byte[] data) {
        return data(data, data.length);
    }

    public PiGpioPacket data(byte[] data, int length) {
        return data(data, 0, data.length);
    }

    public PiGpioPacket data(byte[] data, int offset, int length){
        // check for valid data
        if(data != null && data.length > 0 && length > 0) {
            this.p3 = length;
            this.data = Arrays.copyOfRange(data, offset, offset+length);
        }
        else{
            this.p3 = 0;
            this.data = new byte[0];
        }
        return this;
    }

    public PiGpioPacket data(int value){
        // check for valid value
        if(value > 0) {
            this.p3 = 4; // 4 bytes length
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putInt(value);
            this.data = buffer.array();
        }
        else{
            this.p3 = 0;
            this.data = new byte[0];
        }
        return this;
    }


    public PiGpioPacket data(byte value){
        // check for valid value
        if(value > 0) {
            this.p3 = 4; // 4 bytes length
            this.data = new byte[] { value, 0, 0, 0 }; // little endian
        }
        else{
            this.p3 = 0;
            this.data = new byte[0];
        }
        return this;
    }

    public String dataToString(){
        if(data == null) return null;
        if(data.length == 0) return "";
        return new String(data, StandardCharsets.US_ASCII);
    }

    public static PiGpioPacket decode(InputStream stream) throws IOException {
        return PiGpioPacket.decode(stream.readNBytes(stream.available()));
    }

    public static PiGpioPacket decode(byte[] data) throws IOException {
        ByteBuffer rx = ByteBuffer.wrap(data);
        rx.order(ByteOrder.LITTLE_ENDIAN);

        // check data length for minimum package size
        if(data.length < 16){
            throw new IOException("Insufficient number of data bytes bytes received; COUNT=" + data.length);
        }

        // parse packet parameters from raw received bytes
        PiGpioCmd cmd = PiGpioCmd.from(rx.getInt()); // CMD <4 bytes :: 0-3>
        int p1 = rx.getInt();                        // P1  <4 bytes :: 4-7>
        int p2 = rx.getInt();                        // P2  <4 bytes :: 8-11>
        int p3 = rx.getInt();                        // P3  <4 bytes :: 12-15>

        // create new packet
        PiGpioPacket packet = new PiGpioPacket(cmd, p1, p2)
                .p3(p3); // set RAW P3 value

        // apply any extra payload data (if available)
        int remaining = rx.remaining();

        // bounds check remaining byte count
        if(p3 < remaining) remaining = p3;

        //System.out.println("HAS-REMAINING: " + remaining);
        if(remaining > 0){
            var temp = new byte[remaining];
            rx.get(temp, 0, remaining);
            packet.data(temp);
        }
        return packet;
    }

    public static byte[] encode(PiGpioPacket packet){
        // create byte array and byte buffer using LITTLE ENDIAN for the ARM platform
        byte[] bytes = new byte[16 + packet.dataLength()];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // place packet values into structured data bytes
        buffer.putInt(packet.cmd().value());  // CMD
        buffer.putInt((packet.p1()));         // <P1>
        buffer.putInt((packet.p2()));         // <P2>
        buffer.putInt((packet.p3()));         // <P3>
        if(packet.data != null && packet.data.length > 0) {
            buffer.put(packet.data());        // <DATA>
        }

        // return byte array
        return bytes;
    }

    @Override
    public String toString(){
        //if(this.data != null && this.data.length > 0)
        if(p3() > 0)
            return String.format("CMD=%s(%d); P1=%d; P2=%d; P3=%d; PAYLOAD=[0x%s]", cmd().name(), cmd().value(), p1(), p2(), p3(), StringUtil.toHexString(data()));
        else
            return String.format("CMD=%s(%d); P1=%d; P2=%d; P3=%d", cmd().name(), cmd().value(), p1(), p2(), p3());
    }
}


