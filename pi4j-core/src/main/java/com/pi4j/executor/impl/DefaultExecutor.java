package com.pi4j.executor.impl;

import com.pi4j.executor.Executor;
import com.pi4j.platform.exception.PlatformNotFoundException;
import java.util.concurrent.ExecutorService;

public class DefaultExecutor implements Executor {

    private RuntimeExecutor runtimeExecutor = null;

    /**
     * <p>newInstance.</p>
     *
     * @param runtimeExecutor a {@link com.pi4j.executor.impl.RuntimeExecutor} object.
     * @return a {@link com.pi4j.platform.Platforms} object.
     * @throws com.pi4j.platform.exception.PlatformNotFoundException if any.
     */
    public static Executor newInstance(RuntimeExecutor runtimeExecutor) throws PlatformNotFoundException {
        return new DefaultExecutor(runtimeExecutor);
    }

    // private constructor
    private DefaultExecutor(RuntimeExecutor runtimeExecutor) throws PlatformNotFoundException {
        // set local reference
        this.runtimeExecutor = runtimeExecutor;
    }

    @Override
    public ExecutorService get() {
        return this.runtimeExecutor.get();
    }
}
