package com.pi4j.util.impl;

import com.pi4j.util.Descriptor;

import java.io.PrintStream;
import java.util.*;

public class DescriptorImpl implements Descriptor {

    private Set<Descriptor> children = Collections.synchronizedSet(new LinkedHashSet<>());
    private Descriptor parent;
    private String description;

    public DescriptorImpl(String description){
        this.parent = null;
        this.description = description;
    }

    public DescriptorImpl(Descriptor parent, String description){
        this.parent = parent;
        this.description = description;
    }

    public Descriptor add(String description){
        var child = new DescriptorImpl(this, description);
        children.add(child);
        return child;
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
        stream.println(this.description);

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
            ((DescriptorImpl)descriptor).print(stream, p, parents);

            // remove the parent level from the parent collection
            parents.remove(parents.size()-1);
        }
    }
}
