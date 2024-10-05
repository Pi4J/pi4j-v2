package com.pi4j.library.kernel.util;


//import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.TreeSet;

public class NativeLibraryLoader {

        private static final Set<String> loadedLibraries = new TreeSet<>();
        protected static final Logger logger = LoggerFactory.getLogger(NativeLibraryLoader.class);
        private static boolean initialized;

        // private constructor
	private NativeLibraryLoader() {
        // forbid object construction
    }

        public static synchronized void load(String fileName, String libName) {
        // check for debug property; if found enable all logging levels
        if (!initialized) {
            initialized = true;
        }

        // first, make sure that this library has not already been previously loaded
        if (loadedLibraries.contains(fileName)) {
            logger.warn("Library [" + fileName + "] has already been loaded; no need to load again.");
           // NativeLibrary lib = NativeLibraryLoader.getInstance(fileName);
            return;
        }

        // cache loaded library
        loadedLibraries.add(fileName);

        // determine if there is an overriding library path defined for native libraries
        String libpath = System.getProperty("pi4j.library.path");
        if(StringUtil.isNotNullOrEmpty(libpath, true)) {

            // if the overriding library path is set to "system", then attempt to use the system resolved library paths
            if (libpath.equalsIgnoreCase("system")) {
                logger.debug("Attempting to load library using {pi4j.library.path} system resolved library name: [" + libName + "]");
                try {
                    // load library from JVM system library path; based on library name
                    System.loadLibrary(libName);
                }
                catch (Exception ex){
                    //throw this error
                    throw new UnsatisfiedLinkError("Pi4J was unable load the native library [" +
                        libName + "] from the system defined library path.  The system property 'pi4j.library.path' is defined as [" +
                        libpath + "]. You can alternatively define the 'pi4j.library.path' " +
                        "system property to override this behavior and specify an absolute library path." +
                        "; UNDERLYING EXCEPTION: [" + ex.getClass().getName() + "]=" + ex.getMessage());
                }
            }

            // if the overriding library path is set to "local", then attempt to use the JAR local path to resolve library
            else if (libpath.equalsIgnoreCase("local")) {
                // get local directory path of JAR file
                try {
                    libpath = NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                } catch (URISyntaxException e) {
                    logger.error(e.getMessage(), e);
                    libpath = ".";
                }
                // build path based on lib directory and lib filename
                String path = Paths.get(libpath, fileName).toString();
                logger.debug("Attempting to load library using {pi4j.library.path} defined path: [" + path + "]");
                try {
                    // load library from local path of this JAR file
                    System.load(path);
                }
                catch (Exception ex){
                    //throw this error
                    throw new UnsatisfiedLinkError("Pi4J was unable load the native library [" +
                        libName + "] from the user defined library path.  The system property 'pi4j.library.path' is defined as [" +
                        libpath + "]. Please make sure the defined the 'pi4j.library.path' " +
                        "system property contains the correct absolute library path." +
                        "; UNDERLYING EXCEPTION: [" + ex.getClass().getName() + "]=" + ex.getMessage());
                }
            }

            // if the overriding library path is set to something else, then attempt to use the defined path to resolve library
            else {
                // build path based on lib directory and lib filename
                String path = Paths.get(libpath, fileName).toString();
                logger.debug("Attempting to load library using {pi4j.library.path} defined path: [" + path + "]");
                try {
                    // load library from user defined absolute path provided via pi4j.library.path}
                    System.load(path);
                } catch (UnsatisfiedLinkError ex) {
                    String exceptMessage;
                    // no guarantee the except pertains to ELF miss-match so check MSG content
                    if (ex.getMessage().contains("wrong ELF class")) {
                        exceptMessage = "Pi4J was unable to link the native library [" +
                            path + "] embedded  inside this JAR [" +
                            NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                            "].  The exception indicates a mismatch of architecture. armhf/ELFCLASS32 aarch64/ELFCLASS64 \n" +
                            " UNDERLYING EXCEPTION: [" + ex.getClass().getName() + "]=" + ex.getMessage();
                    } else {
                        exceptMessage = "Pi4J was unable to extract and load the native library [" +
                            path + "] from the embedded resources inside this JAR [" +
                            NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                            "]. to a temporary location on this system.  You can alternatively define the 'pi4j.library.path' " +
                            "system property to override this behavior and specify the library path.\n" +
                            " UNDERLYING EXCEPTION: [" + ex.getClass().getName() + "]=" + ex.getMessage();
                    }
                    throw new UnsatisfiedLinkError(exceptMessage);
                } catch (Exception ex) {
                    throw new UnsatisfiedLinkError("Pi4J was unable to extract and load the native library [" +
                        path + "] from the embedded resources inside this JAR [" +
                        NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                        "]. to a temporary location on this system.  You can alternatively define the 'pi4j.library.path' " +
                        "system property to override this behavior and specify the library path.\n" +
                        " UNDERLYING EXCEPTION: [" + ex.getClass().getName() + "]=" + ex.getMessage());
                }
            }
        }

        // if there is no overriding library path defined, then attempt to load native library from embedded resource
        else {
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

            // include the CPU architecture in the embedded path
            String path = "/lib/" + osArch + "/" + libName + "/" + fileName;
            logger.debug("Attempting to load library [" + fileName + "] using path: [" + path + "]");
            try {
                loadLibraryFromClasspath(path);
                logger.debug("Library [" + fileName + "] loaded successfully using embedded resource file: [" + path + "]");
            } catch (UnsatisfiedLinkError e) {
                logger.error("Unable to load [" + fileName + "] using path: [" + path + "]", e);
                String exceptMessage;
                // no guarantee the except pertains to ELF miss-match so check MSG content
                if (e.getMessage().contains("wrong ELF class")) {
                    exceptMessage = "Pi4J was unable to link the native library [" +
                        path + "] embedded  inside this JAR [" +
                        NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                        "].  The exception indicates a mismatch of architecture. armhf/ELFCLASS32 aarch64/ELFCLASS64 \n" +
                        " All native libraries must be of architecture " + osArch + " \n" +
                        " UNDERLYING EXCEPTION: [" + e.getClass().getName() + "]=" + e.getMessage();
                } else {
                    exceptMessage = "Pi4J was unable to extract and load the native library [" +
                        path + "] from the embedded resources inside this JAR [" +
                        NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                        "]. to a temporary location on this system.  You can alternatively define the 'pi4j.library.path' " +
                        "system property to override this behavior and specify the library path.\n" +
                        " UNDERLYING EXCEPTION: [" + e.getClass().getName() + "]=" + e.getMessage();
                }
                throw new UnsatisfiedLinkError(exceptMessage);
            } catch (Exception e) {
                logger.error("Unable to load [" + fileName + "] using path: [" + path + "]", e);
                throw new UnsatisfiedLinkError("Pi4J was unable to extract and load the native library [" +
                    path + "] from the embedded resources inside this JAR [" +
                    NativeLibraryLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                    "]. to a temporary location on this system.  You can alternatively define the 'pi4j.library.path' " +
                    "system property to override this behavior and specify the library path.\n" +
                    " UNDERLYING EXCEPTION: [" + e.getClass().getName() + "]=" + e.getMessage());
            }
        }
    }

        /**
         * Loads library from classpath
         *
         * The file from classpath is copied into system temporary directory and then loaded. The temporary file is
         * deleted after exiting. Method uses String as filename because the pathname is
         * "abstract", not system-dependent.
         *
         * @param path
         *            The file path in classpath as an absolute path, e.g. /package/File.ext (could be inside jar)
         * @throws IOException
         *             If temporary file creation or read/write operation fails
         * @throws IllegalArgumentException
         *             If source file (param path) does not exist
         * @throws IllegalArgumentException
         *             If the path is not absolute or if the filename is shorter than three characters (restriction
         *             of {@see File#createTempFile(java.lang.String, java.lang.String)}).
         */
        public static void loadLibraryFromClasspath(String path) throws IOException {
        Path inputPath = Paths.get(path);

        String absPath = String.valueOf(String.valueOf(inputPath.toAbsolutePath()));
        System.out.println("\n\nabsPath  : " + absPath + "  \n\n");


        if (!inputPath.isAbsolute()) {
            throw new IllegalArgumentException("The path has to be absolute, but found: " + inputPath);
        }

        String fileNameFull = inputPath.getFileName().toString();
        int dotIndex = fileNameFull.indexOf('.');
        if (dotIndex < 0 || dotIndex >= fileNameFull.length() - 1) {
            throw new IllegalArgumentException("The path has to end with a file name and extension, but found: " + fileNameFull);
        }

        String fileName = fileNameFull.substring(0, dotIndex);
        String extension = fileNameFull.substring(dotIndex);

        Path target = Files.createTempFile(fileName, extension);
        File targetFile = target.toFile();
        targetFile.deleteOnExit();

        try (InputStream source = NativeLibraryLoader.class.getResourceAsStream(inputPath.toString())) {
            if (source == null) {
                throw new FileNotFoundException("File " + inputPath + " was not found in classpath.");
            }
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
        // Finally, load the library
        System.load(target.toAbsolutePath().toString());
    }


    public static final NativeLibrary getInstanceX(String libraryName){
        NativeLibrary the_Lib = null;
        // first, make sure that this library has already been previously loaded, if NOT, error
        if (!loadedLibraries.contains(libraryName)) {
            logger.error("Library [" + libraryName + "] has NOT already been loaded;");
            System.exit(100);
        }
        synchronized (loadedLibraries){

        }
       return the_Lib;
   }

}
