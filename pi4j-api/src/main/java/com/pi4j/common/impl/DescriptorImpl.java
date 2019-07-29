package com.pi4j.common.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DescriptorImpl.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.common.Descriptor;
import com.pi4j.common.Metadata;
import com.pi4j.common.Metadatum;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DescriptorImpl implements Descriptor {

    private Set<Descriptor> children = Collections.synchronizedSet(new LinkedHashSet());
    private Descriptor parent;

    private String id;
    private String name;
    private String description;
    private String category;
    private Integer quantity;
    private Class type;
    private Object value;
    private Metadata metadata = Metadata.create();
    public DescriptorImpl(){
        this.parent = null;
    }

    @Override
    public Descriptor id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public Descriptor name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Descriptor description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Descriptor category(String category) {
        this.category = category;
        return this;
    }

    @Override
    public Descriptor quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public Descriptor value(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public Descriptor metadata(String key, Object value) {
        this.metadata.put(key,value);
        return this;
    }

    @Override
    public Descriptor metadata(String key, Object value, String description) {
        this.metadata.put(key, value, description);
        return this;
    }

    @Override
    public Descriptor metadata(Metadata metadata) {
        this.metadata.put(metadata);
        return this;
    }

    @Override
    public Descriptor type(Class type) {
        this.type = type;
        return this;
    }

    @Override
    public Descriptor parent(Descriptor parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public String category() {
        return this.category;
    }

    @Override
    public Integer quantity() {
        return this.quantity;
    }

    @Override
    public Object value() {
        return this.value;
    }

    @Override
    public Metadatum metadata(String key) {
        return this.metadata.get(key);
    }

    @Override
    public Metadata metadata() {
        return this.metadata;
    }

    @Override
    public Class type() {
        return this.type;
    }

    @Override
    public Descriptor parent() {
        return this.parent;
    }

    @Override
    public Descriptor add(Descriptor descriptor) {
        if(descriptor != null) {
            this.children.add(descriptor.parent(this));
        }
        return this;
    }

    public int size(){
        return children.size();
    }

    public boolean isEmpty(){
        return children.isEmpty();
    }

    public boolean isNotEmpty(){
        return !children.isEmpty();
    }

    public void print(PrintStream stream){
        print(stream, "", new ArrayList<>());
    }
    private void print(PrintStream stream, String prefix, ArrayList<Boolean> parents){

        // print this descriptors description text (preceded by prefix)
        stream.print(prefix);

        // include category
        if(this.category != null){
            stream.print(this.category);
            stream.print(": ");
        }

        // include quantity
        if(this.quantity != null){
            stream.print("[");
            stream.print(this.quantity.toString());
            stream.print("] ");
        }

        // include name
        if(this.name != null) {
            stream.print("\"");
            stream.print(this.name);
            stream.print("\" ");
        }

        // include value
        if(this.value != null) {
            stream.print("= ");
            stream.print(this.value.toString());
            stream.print(" ");
        }

        // include ID
        if(this.id != null){
            stream.print("{");
            stream.print(this.id);
            stream.print("} ");
        }

        // include class type
        if(this.type != null){
            stream.print("<");
            stream.print(this.type.getName());
            stream.print("> ");
        }

        // include description
        if(this.description != null){
            stream.print("{");
            stream.print(this.description);
            stream.print("} ");
        }

        stream.println();

//        if(this.properties.size() > 0){
//            this.properties.forEach((key, value)->{
//                stream.print(prefix);
//                stream.print(key.toUpperCase());
//                stream.print(" = ");
//                stream.print(value);
//                stream.println();
//            });
//        }


        //var children = Collections.synchronizedSet(new LinkedHashSet<>());
        //children.addAll(this.children);

        var children = this.children.stream().collect(Collectors.toSet());

        this.metadata.all().forEach((metadatum)-> {

            children.add(metadatum.describe());

//            children.add(Descriptor.create().name(metadatum.key())
//                    .value(metadatum.value())
//                    .description(metadatum.description()));
        });

        // loop over this descriptor's children
        var iterator = children.iterator();
        while (iterator.hasNext()) {

            // get the child descriptor instance
            var descriptor = iterator.next();
            String p = "";

            // iterate over the provided parents collection of boolean states
            // which represent if the parent has more children to describe
            for(Boolean parentHasNext : parents){
                p += (parentHasNext) ? "│ " : "  ";
            };

            // append to the prefix the graphical tree structure for this branch
            // depending on if there are more children remaining to describe
            p += (iterator.hasNext()) ? "├─" : "└─";

            // add a parent level to the parent collection
            parents.add(iterator.hasNext());

            // describe this child branch/node
            if(descriptor instanceof Descriptor)
                ((DescriptorImpl)descriptor).print(stream, p, parents);
            //if(descriptor instanceof )

            // remove the parent level from the parent collection
            parents.remove(parents.size()-1);
        }
    }
}
