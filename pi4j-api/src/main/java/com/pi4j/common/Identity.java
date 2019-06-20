package com.pi4j.common;

public interface Identity extends Describable {
    String id();
    String name();
    String description();
    Metadata metadata();

    default String getId(){
        return id();
    }

    default String getName(){
        return name();
    }

    default String getDescription(){
        return description();
    }

    default Metadata getMetadata(){
        return metadata();
    }

    @Override
    default Descriptor describe() {
        return Descriptor.create()
                .id(id())
                .name(name())
                .description(description())
                .metadata(metadata());
    }
}
