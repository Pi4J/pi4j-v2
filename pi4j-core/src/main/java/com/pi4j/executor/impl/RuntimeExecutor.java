package com.pi4j.executor.impl;

import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.executor.Executor;
import java.util.concurrent.ExecutorService;

public interface RuntimeExecutor extends Executor {

    /**
     * <p>shutdown.</p>
     *
     * @return a {@link com.pi4j.executor.impl.RuntimeExecutor} object.
     * @throws com.pi4j.exception.ShutdownException if any.
     */
    RuntimeExecutor shutdown() throws ShutdownException;

    /**
     * <p>initialize.</p>
     *
     * @param executorService a {@link java.util.concurrent.ExecutorService} object.
     * @return a {@link com.pi4j.executor.impl.RuntimeExecutor} object.
     * @throws com.pi4j.exception.InitializeException if any.
     */
    RuntimeExecutor initialize(ExecutorService executorService) throws InitializeException;
}
