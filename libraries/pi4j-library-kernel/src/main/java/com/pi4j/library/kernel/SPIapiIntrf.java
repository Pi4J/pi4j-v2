package com.pi4j.library.kernel;

import com.sun.jna.Library;

import java.io.IOException;

public interface SPIapiIntrf extends Library {

    static interface jna_rc{
        public static final int x0 = 0;
        public static final int x1 = 1;
        public static final int x2 = 2;
        public static final int x3 = 3;
        public static final int x4 = 4;
        public static final int x5 = 5;
        public static final int x6 = 6;
        public static final int x7 = 7;
        public static final int x9 = 9;
        public static final int x10 = 10;
        public static final int x11 = 11;
        public static final int x12 = 12;
        public static final int x13 = 13;
        public static final int x14 = 14;
        public static final int xCount = 16;
    }
   int spiApi_render_test(SPIapi.spiApi_test spiApi);  // TODO *  SPIapi.spiApi_return_t




    int spiApi_write(SPIapi.spiApi_t spiApi, byte[] b, int offset, int len, int speed);

    public int spiApi_write_b(SPIapi.spiApi_t spiApi, byte b, int speed);

    public int spiApi_read_b(SPIapi.spiApi_t spiApi, byte b, int speed) ;

    public int spiApi_read(SPIapi.spiApi_t spiApi, byte[] buffer, int offset, int length, int speed) ;

    public int transfer(SPIapi.spiApi_t spiApi, byte[] write, int writeOffset, byte[] read, int readOffset, int numberOfBytes, int speed);





    int spiApi_init(SPIapi.spiApi_t spiApi);
    int spiApi_render(SPIapi.spiApi_t spiApi);  // TODO *


    public static SPIapiImpl newNativeInstance(String soName, String libName) throws java.io.IOException {
        SPIapiImpl rval = new SPIapiImpl(soName);
        rval.initialize(soName, libName);
        return(rval);
    }

    public static SPIapiImpl createSPIapiImpl() throws IOException {
        return(SPIapiImpl.newInstance("libpi4j-kernel.so", "pi4j-kernel"));
    }

    }



