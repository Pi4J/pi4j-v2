package com.pi4j.plugin.linuxfs.i2c;

@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}