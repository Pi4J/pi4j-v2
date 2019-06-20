package com.pi4j.common;

import com.pi4j.common.impl.MetadatumImpl;

public interface Metadatum extends Describable {

    Metadatum key(String key);
    Metadatum value(Object value);
    Metadatum description(String description);
    String key();
    Object value();
    String description();

    static Metadatum create(){
        return new MetadatumImpl();
    }

    static Metadatum create(String key){
        return create().key(key);
    }

    static Metadatum create(String key, Object value){
        return create(key).value(value);
    }

    static Metadatum create(String key, Object value, String description){
        return create(key, value).description(description);
    }

    @Override
    default Descriptor describe() {
        return Descriptor.create().name(key()).description(description()).value(value());
    }
}
