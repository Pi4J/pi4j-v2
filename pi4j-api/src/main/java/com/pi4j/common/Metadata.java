package com.pi4j.common;


import com.pi4j.common.impl.MetadataImpl;

import java.util.Collection;

public interface Metadata  {

//    static Metadata create(){
//        return new MetadataImpl();
//    }
//
//    static Metadata create(String key){
//        return create().key(key);
//    }
//
//    static Metadata create(String key, Object value){
//        return create(key).value(value);
//    }
//
//    static Metadata create(String key, Object value, String description){
//        return create(key, value).description(description);
//    }

    int size();
    boolean isEmpty();
    boolean contains(String key);
    Metadatum remove(String key);

    Metadata put(Metadatum metadatum);
    Metadata clear();
    Metadatum get(String key);
    Collection<Metadatum> all();

    default Metadata put(Collection<? extends Metadatum> c){
        c.forEach((m)->{
            put(m);
        });
        return this;
    }

    default Metadata put(Metadata metadata){
        metadata.all().forEach((m)->{
            put(m);
        });
        return this;
    }

    default boolean contains(Metadatum metadatum){
        return contains(metadatum.key());
    }

    default Object getValue(String key){
        return get(key).value();
    }

    default Metadata put(String key){
        return put(Metadatum.create(key));
    }
    default Metadata put(String key, Object value){
        return put(Metadatum.create(key, value));
    }
    default Metadata put(String key, Object value, String description){
        return put(Metadatum.create(key, value, description));
    }

    static Metadata create(){
        return new MetadataImpl();
    }
}
