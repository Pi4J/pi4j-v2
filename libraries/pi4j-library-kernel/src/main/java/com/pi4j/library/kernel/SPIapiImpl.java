package com.pi4j.library.kernel;

import com.pi4j.library.kernel.util.NativeLibraryLoader;
import com.pi4j.library.kernel.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static com.sun.jna.NativeLibrary.getInstance;


public class SPIapiImpl  implements SPIapiIntrf {

    SPIapiIntrf functionsNative = null;


    public int x = 42;
    public SPIapiImpl(String fileName) throws  java.io.IOException {
    }

    public SPIapiImpl() {
    }

    static {
        // Load the platform library
        NativeLibraryLoader.load("libpi4j-kernel.so", "pi4j-kernel");
//        System.setProperty("pi4j.library.path", "/lib/aarch64/pi4j-kernel");
        System.setProperty("pi4j.library.path", "/home/pi/Pi4J_V2/pi4j-v2/libraries/pi4j-library-kernel/target/lib/aarch64/pi4j-kernel");

    }


    public static SPIapiImpl newInstance(String fileName, String libName) throws IOException {
        SPIapiImpl impl = new SPIapiImpl();
        impl.initialize(fileName, libName);
        return(impl);
    }

    @Override
    //SPIapi.spiApi_return_t
    public int spiApi_render_test(SPIapi.spiApi_test spiApi) {
        int ret = jna_rc.x0;
        ret = this.functionsNative.spiApi_render_test(spiApi);

        return ret;
    }





    @Override
    public int spiApi_render(SPIapi.spiApi_t spiApi) {
        return 0;
    }

    @Override
    public int spiApi_init(SPIapi.spiApi_t spiApi) {
        int ret =  0;
        ret = this.functionsNative.spiApi_init(spiApi);

        return ret;
    }


    public int spiApi_write(SPIapi.spiApi_t spiApi, byte[] b, int offset, int len, int speed){
     // todo test len within buffer
        Objects.checkFromIndexSize(offset, len, b.length);
        return  this.functionsNative.spiApi_write(spiApi, b, offset,len, speed);
    }

    @Override
    public int spiApi_write_b(SPIapi.spiApi_t spiApi, byte b, int speed) {
        return 0;
    }

    @Override
    public int spiApi_read_b(SPIapi.spiApi_t spiApi, byte b, int speed) {
        return 0;
    }

    @Override
    public int spiApi_read(SPIapi.spiApi_t spiApi, byte[] buffer, int offset, int length,  int speed) {
        Objects.checkFromIndexSize(offset, length, buffer.length);
      return 0;
    }

    @Override
    public int transfer(SPIapi.spiApi_t spiApi, byte[] write, int writeOffset, byte[] read, int readOffset, int numberOfBytes, int speed) {
        Objects.checkFromIndexSize(writeOffset, numberOfBytes, write.length);
        Objects.checkFromIndexSize(readOffset, numberOfBytes, read.length);
        return 0;
    }

    public void initialize(String fileName, String libName) throws java.io.IOException {
        // the following allows my usage of the JNA code, rather than cloning my own version
        Path workingDirectory=Paths.get(".").toAbsolutePath();
        String classPath = System.getProperty("java.class.path");
        System.out.println("properties : " + System.getProperties());

        System.out.println("classpath" + classPath);

        System.out.println("working DIR " + workingDirectory);
        // get CPU architecture from system properties
        String osArch = System.getProperty("os.arch").toLowerCase();

        // sanitize CPU architecture string
        switch (osArch) {
            case "arm":
                osArch = "armhf";
                break;
            case "arm64":
                osArch = "aarch64";
                break;
            case "aarch64":
                break;
            default:
                throw new IllegalStateException("Pi4J has detected and UNKNOWN/UNSUPPORTED 'os.arch' : [" +
                    osArch + "]; only 'arm|armhf' and 'arm64|aarch64' are supported.");
        }
       // String possibleJnaPath = workingDirectory+"/lib/" +osArch + "/pi4j-kernel/";
       // String possibleJnaPath = "/lib/" + osArch + "/" + libName + "/" + fileName;
        String possibleJnaPath = "/lib/" +osArch + "/pi4j-kernel/";
        // Also, handle the user relying upon "pi4j.library.path" to locate so's
        String pi4jLibpath = System.getProperty("pi4j.library.path");

        if(StringUtil.isNotNullOrEmpty(pi4jLibpath, true)){
            possibleJnaPath += ":"+pi4jLibpath;
            System.out.println("libPath " + pi4jLibpath);
        }
        System.out.println(" possible JNA " + possibleJnaPath);
// TO_DO
        // Determine the JNA path in the NativeLibrarylOADER
        System.setProperty("jna.library.path", possibleJnaPath);
        NativeLibraryLoader.load(fileName, libName);
        NativeLibrary lib = NativeLibrary.getInstance(fileName); //SPIapiIntrf.getCLibName(LIBPROPFILE));
        SPIapiIntrf INSTANCE = Native.load( SPIapiIntrf.class);
        this.functionsNative = INSTANCE; //Native.loadLibrary(extractFile(fileName), String.valueOf(SPIapiIntrf.class));
    }


    static final int DEFAULT_BUFFER_SIZE = 8192;

    private static void copyInputStreamToFile(InputStream inputStream, File file)
        throws com.pi4j.io.exception.IOException, java.io.IOException {
        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }

}