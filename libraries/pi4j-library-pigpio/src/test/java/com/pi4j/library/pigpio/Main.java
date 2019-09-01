package com.pi4j.library.pigpio;
/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  Main.java
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

public class Main {

    private static String SERIAL_DEVICE = "/dev/ttyS0";
    private static int BAUD_RATE = 9600;

    public static void main(String[] args) throws Exception {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        PiGpio pig = PiGpio.newSocketInstance("rpi3bp"); //"10.1.2.222"
        pig.initialize();

//        // set pin ALT0 modes for I2C BUS<1> usage on RPI3B
//        pig.gpioSetMode(2, PiGpioMode.ALT0);
//        pig.gpioSetMode(3, PiGpioMode.ALT0);

//
//        for(int x = 0; x < 100; x++) {
//            try {
//                pig.i2cClose(x);
//            }
//            catch (IOException e){
//                continue;
//            }
//        }

        pig.addListener(new PiGpioStateChangeListener() {
            @Override
            public void onChange(PiGpioStateChangeEvent event) {
                System.out.println(event);
            }
        });

        pig.gpioNotifications(0, true);
        System.out.println("READY.");

        System.in.read();
        System.out.println("REMOVED LISTENERS.");
        pig.gpioNotifications(0, false);


        System.in.read();
        System.in.read();
        System.out.println("ADDED LISTENERS.");
        pig.gpioNotifications(0, true);


        System.in.read();
        System.in.read();
        System.out.println("SHUTDOWN NOW");
        pig.shutdown();

        //pig.initialize();
        //pig.gpioSetPWMfrequency(4, 5000);


        //pig.gpioHardwarePWM(13, 50000000, 500000);

        //pig.gpioSetPWMfrequency(2, 830);
        //pig.gpioPWM(2, 128);

        //var frequency = pig.gpioGetPWMfrequency(4);
        //System.out.println("FREQUENCY: " + frequency);

        //System.in.read();

//        pig.gpioHardwarePWM(18, 0,0);


        // SINGLE RAW BYTE
//        pig.i2cWriteByte(handle, (byte)0xD);
//        byte b = pig.i2cReadByte(handle);
//        System.out.println("[BYTE]" + Byte.toUnsignedInt(b));

        // SINGLE WORD
        //pig.i2cWriteWordData(handle, 2, 1024);
//        for(int x = 0; x < 50; x++) {
//            int word = pig.i2cReadWordData(handle, 0x00);
//            System.out.println("[WORD]" + word);
//            Thread.sleep(50);
//        }

        // DATA BLOCK
        //pig.i2cWriteBlockData(handle, 2, "Hello World!");
        //byte[] data = pig.i2cReadBlockData(2 , 0x02);
        //byte[] data = pig.i2cBlockProcessCall(handle, 2, "Hello World!");
        //System.out.println("[BLOCK]" + Arrays.toString(data));

//        pig.i2cWriteI2CBlockData(handle, 2, "Hello World!");
//        byte[] rx = pig.i2cReadI2CBlockData(handle, 2, 32);
//        System.out.println("[BLOCK] <"  + rx.length + "> " + Arrays.toString(rx));
//
//
//        int max = 32;
//        byte[] data = new byte[max];
//        for(int i = 0; i < max; i++) {
//            data[i] = (byte)i;
//        }
//
//        pig.i2cWriteDevice(handle, data);

        //Thread.sleep(200);
        //byte rx[] = pig.i2cReadDevice(handle, data.length);

        //System.out.println("[RAW-READ] <" + rx.length + "> " + rx);


//        // RAW I2C READ/WRITE
//        for(int i = 0; i <= 100; i++) {
//            String data = "Hello World! " + i;
//            pig.i2cWriteDevice(handle, data);
//            //Thread.sleep(200);
//            String rx = pig.i2cReadDeviceToString(handle, data.length());
//            System.out.println("[RAW-READ] <" + rx.length() + "> " + rx);
//        }

//
//        pig.gpioWrite(4, PiGpioState.LOW);

//        // CLOSE
//        pig.i2cClose(handle);



//        pig.gpioSetMode(4, PiGpioMode.OUTPUT);
//
//        for(int x = 0; x < 100; x++) {
//            pig.gpioWrite(4, PiGpioState.LOW);
//            //pig.gpioWrite(3, PiGpioState.LOW);
//            pig.gpioWrite(4, PiGpioState.HIGH);
//            //pig.gpioWrite(3, PiGpioState.HIGH);
//        }

//        pig.gpioPWM(2, 50);
//        Thread.sleep(1000);;
//        pig.gpioPWM(2, 0);


//        pig.gpioSetMode(4, PiGpioMode.INPUT);
//        pig.gpioSetPullUpDown(4, PiGpioPud.DOWN);
//
//        for(int x = 0; x < 100000; x++) {
//            var value = pig.gpioRead(4);
//            System.out.println(value);
//        }
//


//        pig.initialize();
//
//        System.out.println("---------------------------------------------------------------------");
//        System.out.println("Raspberry Pi - Hardware Revision  : " + pig.gpioHardwareRevisionString());
//        System.out.println("Raspberry Pi - PIGPIO Lib Version : " + pig.gpioVersion());
//        System.out.println("---------------------------------------------------------------------");
//
//        pig.gpioSetMode(28, PiGpioMode.OUTPUT);
//        pig.gpioWrite(28, PiGpioState.HIGH);
//
//        for (int p = 0; p <= 31; p++) {
//            System.out.println("  GPIO " + p + "; MODE=" + pig.gpioGetMode(p) + "; STATE=" + pig.gpioRead(p));
//        }

//        pig.gpioTick();

    }
}
