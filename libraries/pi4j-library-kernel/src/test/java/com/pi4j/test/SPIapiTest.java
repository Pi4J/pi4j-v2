package com.pi4j.test;


import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.exception.LifecycleException;
import com.pi4j.library.kernel.SPIapi;
import com.pi4j.library.kernel.SPIapiImpl;
import com.pi4j.library.kernel.SPIapiIntrf;
import com.pi4j.util.Console;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.Scanner;







public class SPIapiTest {


    public static void waitForInput() {
        int rval = 0;
        Scanner scan = new Scanner(System.in);

        System.out.println("Hit any key to continue   ");
        scan.next();

    }

    public static void main(String[] args) throws Exception {
        var console = new Console();
        Context pi4j = Pi4J.newAutoContext();

        console.title("<-- The Pi4J V2 Project Extension  -->", "Use JNA and the Kernel SPI APIs");

        System.setProperty("pi4j.library.path", "/home/pi/Pi4J_V2/pi4j-v2/libraries/pi4j-library-kernel/target/lib/aarch64/pi4j-kernel");

        int pixels = 1;
        int duty = 0;
        int freq = 1;
        int duration = 0;
        boolean doTest = false;

        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println("Performing ctl-C shutdown");
                try {
                    pi4j.shutdown();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
                // Thread.dumpStack();
                System.exit(2);
            }
        });

        for (int i = 0; i < args.length; i++) {
            String o = args[i];
            if (o.contentEquals("-duty")) {
                String a = args[i + 1];
                duty = Integer.parseInt(a);
                i++;
            } else if (o.contentEquals("-freq")) {
                String a = args[i + 1];
                freq = Integer.parseInt(a);
                i++;
            } else if (o.contentEquals("-duration")) {
                String a = args[i + 1];
                duration = Integer.parseInt(a);
                i++;
            } else if (o.contentEquals("-test")) {
                doTest = true;
            } else {
                console.println("  !!! Invalid Parm " + o);
                console.println("  -duty, -freq,  - duration  -test");
                System.exit(42);
            }
        }


        SPIapiImpl functionsV1 = SPIapiIntrf.createSPIapiImpl();
        SPIapi.spiApi_test dev_test = new SPIapi.spiApi_test();
        dev_test.waldo = 5;
        dev_test.where = 20;

        int returnVal = functionsV1.spiApi_render_test(dev_test);
        console.println("Render_test v1  :  " + dev_test.waldo + "  retVal  : " + returnVal);
        console.println("Render_test v1  :  " + dev_test.where + "  retVal  : " + returnVal);


        SPIapi.spiApi_t spiApi = new SPIapi.spiApi_t();


        spiApi.dev = new SPIapi.spiApi_device_p.ByReference();

        spiApi.spi_FD = 21;
        spiApi.max_freq = 64000000;
        spiApi.dev.driver_mode = 0;
        spiApi.bus = 0;
        spiApi.cs = 0;

        int testcaseSpeed = 8000000;

        console.println("B4 freq : " + spiApi.max_freq);

        console.println("B4 spi_FD : " + spiApi.spi_FD);

        console.println("B4 driver_mode:  " + spiApi.dev.driver_mode);


        int ret = functionsV1.spiApi_init(spiApi);

        console.println("freq : " + spiApi.max_freq);

        console.println("spi_FD : " + spiApi.spi_FD);

        console.println("driver_mode:  " + spiApi.dev.driver_mode);


        console.println("Render v1 :  " + functionsV1.spiApi_render(spiApi));

        byte[] redData = new byte[24+2];
        byte[] blueData = new byte[24];
        // RED
        for (int i = 0; i < 8; i++) {
            redData[i] = (byte) 0xc0;
        }
        for (int i = 8; i < 16; i++) {
            redData[i] = (byte) 0xf8;
        }

        for (int i = 16; i < 24; i++) {
            redData[i] = (byte) 0xc0;
        }

        byte[] offData = new byte[24];
        for (int i = 0; i < 24; i++) {
            offData[i] = (byte) 0xC0;
        }

        // blue
        for (int i = 0; i < 8; i++) {
            blueData[i] = (byte) 0xc0;
        }
        for (int i = 8; i < 16; i++) {
            blueData[i] = (byte) 0xc0;
        }

        for (int i = 16; i < 24; i++) {
            blueData[i] = (byte) 0xf8;
        }
        int speed = spiApi.max_freq;
        ret = functionsV1.spiApi_write(spiApi, redData, 0,  redData.length, testcaseSpeed);
        console.println("spi RED write  offset=0 ret value :  " + ret);

        waitForInput();

        ret = functionsV1.spiApi_write(spiApi, offData,0, offData.length, testcaseSpeed);
        console.println("spi OFF write ret value :  " + ret);
        waitForInput();

        // the way buffer size is tested, I made the buffer 2 bytes bigger than needed
        //  make the call with the correct buff size. Just that in operation
        // we are going to start the write at redData[2]
        ret = functionsV1.spiApi_write(spiApi, redData, 2,  24, testcaseSpeed);
        console.println("spi RED write offset=2 ret value :  " + ret);

        waitForInput();

        ret = functionsV1.spiApi_write(spiApi, offData,0, offData.length, testcaseSpeed);
        console.println("spi OFF write ret value :  " + ret);
        waitForInput();


        ret = functionsV1.spiApi_write(spiApi, redData, 0, redData.length, testcaseSpeed);
        console.println("spi  RED write ret value :  " + ret);
        waitForInput();

        ret = functionsV1.spiApi_write(spiApi, offData, 0, offData.length, testcaseSpeed);
        console.println("spi OFF write ret value :  " + ret);
        waitForInput();

        ret = functionsV1.spiApi_write(spiApi, blueData, 0, blueData.length, testcaseSpeed) ;
        console.println("spi BLUE write ret value :  " + ret);
        waitForInput();

        ret = functionsV1.spiApi_write(spiApi, offData, 0, offData.length, testcaseSpeed) ;
        console.println("spi OFF write ret value :  " + ret);
        waitForInput();


    }

}

