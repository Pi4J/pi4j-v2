package com.pi4j.context;


/**
 * Immutable, read-only view of the settings used to create a Pi4J {@link Context}. Produced by a
 * {@link ContextBuilder} (via {@link ContextBuilder#toConfig()}) and exposed through {@link Context#config()},
 * it captures auto-detection flags, the default platform and the user-supplied properties.
 *
 * @see ContextBuilder
 * @see Context
 */
public interface ContextConfig {
    /**
     * Indicates whether mock plugins should be auto-detected on the classpath.
     *
     * @return {@code true} if mock plugin auto-detection is enabled, {@code false} otherwise
     */
    boolean autoDetectMockPlugins();

    /**
     * Indicates whether a JVM shutdown hook is registered to shut the context down automatically when the
     * JVM terminates.
     *
     * @return {@code true} if the shutdown hook is enabled, {@code false} otherwise
     */
    boolean enableShutdownHook();

    /**
     * Indicates whether provider implementations should be auto-detected on the classpath.
     *
     * @return {@code true} if provider auto-detection is enabled, {@code false} otherwise
     */
    boolean autoDetectProviders();

    /**
     * Bean-style accessor for {@link #autoDetectProviders()}.
     *
     * @return {@code true} if provider auto-detection is enabled, {@code false} otherwise
     */
    default boolean getAutoDetectProviders() { return autoDetectProviders(); };

    /**
     * Bean-style accessor for {@link #autoDetectProviders()}.
     *
     * @return {@code true} if provider auto-detection is enabled, {@code false} otherwise
     */
    default boolean isAutoDetectProviders() { return autoDetectProviders(); };

}
