package com.pi4j.library.pigpio.util;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: JNI Wrapper for PIGPIO Library
 * FILENAME      :  NativeLibraryLoader.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
			return;
		}

		loadedLibraries.add(fileName);

		// determine if there is an overriding library path defined for native libraries
		String libpath = System.getProperty("pi4j.library.path");
        if(StringUtil.isNotNullOrEmpty(libpath, true)) {

            // if the overriding library path is set to "system", then attempt to use the system resolved library paths
            if (libpath.equalsIgnoreCase("system")) {
                logger.debug("Attempting to load library using {pi4j.library.path} system resolved library name: [" + libName + "]");
                System.loadLibrary(libName);
            }
            // if the overriding library path is set to something else, then attempt to use the defined path to resolve library
            else {
                String path = Paths.get(libpath, fileName).toString();
                logger.debug("Attempting to load library using {pi4j.library.path} defined path: [" + path + "]");
                System.load(path);
            }
        }

        // if there is no overriding library path defined, then attempt to load native library from embedded resource
        else {
            String path = "/lib/" + fileName;
            logger.debug("Attempting to load library [" + fileName + "] using path: [" + path + "]");
            try {
                loadLibraryFromClasspath(path);
                logger.debug("Library [" + fileName + "] loaded successfully using embedded resource file: [" + path + "]");
            } catch (Exception | UnsatisfiedLinkError e) {
                logger.error("Unable to load [" + fileName + "] using path: [" + path + "]", e);
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
}
