package com.pi4j.util;

import com.pi4j.util.impl.DescriptorImpl;

import java.io.PrintStream;

public interface Descriptor {

    static Descriptor create(String description){
        return new DescriptorImpl(description);
    }

    Descriptor add(String description);
    int size();
    boolean isEmpty();
    boolean isNotEmpty();
    void print(PrintStream stream);
}
