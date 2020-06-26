package com.pi4j.executor;

public interface Executor {

    void execute(Runnable runnable);

    void asyncShutdown();

}
