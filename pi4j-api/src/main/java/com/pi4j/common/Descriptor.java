package com.pi4j.common;

import com.pi4j.common.impl.DescriptorImpl;

import java.io.PrintStream;

public interface Descriptor {
    Descriptor id(String id);
    Descriptor name(String name);
    Descriptor description(String description);
    Descriptor category(String category);
    Descriptor quantity(Integer quantity);
    Descriptor type(Class type);
    Descriptor metadata(String key, Object value);
    Descriptor metadata(String key, Object value, String description);
    Descriptor metadata(Metadata metadata);
    Descriptor parent(Descriptor parent);
    Descriptor value(Object value);

    String id();
    String name();
    String category();
    String description();
    Integer quantity();
    Object value();
    Class type();
    Metadatum metadata(String key);
    Metadata metadata();
    Descriptor parent();

    static Descriptor create(){
        return new DescriptorImpl();
    }

    Descriptor add(Descriptor descriptor);

    int size();
    boolean isEmpty();
    boolean isNotEmpty();

    void print(PrintStream stream);
}
