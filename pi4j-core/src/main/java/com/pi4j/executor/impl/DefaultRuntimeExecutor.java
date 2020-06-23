package com.pi4j.executor.impl;

import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.platform.exception.PlatformNotFoundException;
import com.pi4j.runtime.Runtime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRuntimeExecutor implements RuntimeExecutor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    protected Runtime runtime = null;
    private ExecutorService executorService = null;

    /**
     * <p>newInstance.</p>
     *
     * @param runtime a {@link com.pi4j.runtime.Runtime} object.
     * @return a {@link com.pi4j.platform.Platforms} object.
     * @throws PlatformNotFoundException if any.
     */
    public static RuntimeExecutor newInstance(Runtime runtime) {
        return new DefaultRuntimeExecutor(runtime);
    }

    // private constructor
    private DefaultRuntimeExecutor(Runtime runtime) {
        this.runtime = runtime;
        this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    }

    @Override
    public ExecutorService get() {
        return this.executorService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuntimeExecutor shutdown() throws ShutdownException {
        logger.trace("invoked 'shutdown();'");

        ShutdownException shutdownException = null;

        try {
            this.executorService.shutdown();
        } catch (SecurityException e) {
            shutdownException = new ShutdownException("Could not shut down the executor service", e);
        }

        // throw exception if
        if (shutdownException != null) {
            throw shutdownException;
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuntimeExecutor initialize(ExecutorService executorService) throws InitializeException {
        this.executorService = executorService;
        return this;
    }

}
