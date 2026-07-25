package com.pi4j.plugin.ffm;

import com.pi4j.context.Context;
import com.pi4j.context.ContextConfig;
import com.pi4j.exception.InitializeException;
import com.pi4j.extension.Plugin;

import java.util.Arrays;

/**
 * Pi4J {@link Plugin} entry point for the FFM (Foreign Function &amp; Memory) native I/O backend.
 */
public class FFMPlugin implements Plugin {


    @Override
    public Context createContext(ContextConfig config) throws InitializeException {
        return new FFMContext(config);
    }
}
