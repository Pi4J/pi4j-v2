package com.pi4j.extension;

import com.pi4j.context.Context;
import com.pi4j.context.ContextConfig;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;

/**
 * A loadable Pi4J component, typically discovered on the classpath
 */
public interface Plugin  {
    /**
     * Creates a context for this plugin.
     *
     * @throws InitializeException if the plugin fails to initialize or register its contributions
     */
    Context createContext(ContextConfig config) throws InitializeException;

    /**
     * Indicates whether this plugin provides mock (simulated) implementations rather than real
     * hardware access. Mock plugins are intended for testing and should generally not be used in
     * production. The default implementation returns {@code false}.
     *
     * @return {@code true} if this is a mock plugin, otherwise {@code false}
     */
    default boolean isMock() {
        return false;
    }
}
