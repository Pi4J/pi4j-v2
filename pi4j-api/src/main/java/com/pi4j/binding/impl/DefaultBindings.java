package com.pi4j.binding.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultBindings.java
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

import com.pi4j.binding.Binding;
import com.pi4j.binding.Bindings;
import com.pi4j.binding.exception.*;
import com.pi4j.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DefaultBindings implements Bindings {

    private boolean initialized = false;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;

    // all detected/available providers
    private Map<String, Binding> bindings = new ConcurrentHashMap<>();

    // static singleton instance
    private static Bindings singleton = null;
    public static Bindings singleton(Context context){
        if(singleton == null){
            singleton = new DefaultBindings(context);
        }
        return singleton;
    }

    // private constructor
    private DefaultBindings(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    protected void initializeBinding(Binding binding) throws BindingInitializeException {

        // ensure the binding object is valid
        if(binding == null) return;

        // attempt to initialize the binding instance
        try {
            logger.trace("initializing binding [id={}; name={}; class={}]",
                    binding.id(), binding.name(), binding.getClass().getName());
            binding.initialize(context);
        } catch (Exception e) {
            logger.error("unable to 'initialize()' binding: [id={}; name={}]; {}",
                    binding.id(), binding.name(), e.getMessage());
            logger.error(e.getMessage(), e);
            throw new BindingInitializeException(binding.id(), e);
        }
    }

    protected void terminateBinding(Binding binding) throws BindingTerminateException {

        // ensure the binding object is valid
        if(binding == null) return;

        // attempt to terminate the binding instance
        try {
            logger.trace("terminating binding [id={}; name={}; class={}]",
                    binding.id(), binding.name(), binding.getClass().getName());
            binding.terminate(context);
        } catch (Exception e) {
            logger.error("unable to 'terminate()' binding: [id={}; name={}]; {}",
                    binding.id(), binding.name(), e.getMessage());
            logger.error(e.getMessage(), e);
            throw new BindingTerminateException(binding.id(), e);
        }
    }

    /**
     * Get all bindings
     * @return
     */
    @Override
    public Map<String, Binding> all(){
        return Collections.unmodifiableMap(this.bindings);
    }

    /**
     * Get all bindings of a specified binding class type.
     *
     * @param bindingClass
     * @param <T>
     * @return
     * @throws BindingException
     */
    @Override
    public <T extends Binding> Map<String, T> all(Class<T> bindingClass) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        // create a map <binding-id, binding-instance> of bindings that extend of the given binding class/interface
        var result = new ConcurrentHashMap<String, T>();
        bindings.values().stream().filter(bindingClass::isInstance).forEach(p -> {
            result.put(p.id(), bindingClass.cast(p));
        });

        if(result.size() <= 0) throw new BindingNotFoundException(bindingClass);
        return Collections.unmodifiableMap(result);
    }

    @Override
    public boolean exists(String bindingId) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        // return true if the managed io map contains the given io-id
        if(bindings.containsKey(bindingId)){
            return true;
        }
        return false;
    }

    @Override
    public <T extends Binding> boolean exists(String bindingId, Class<T> bindingClass) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        // return true if the managed binding map contains the given binding-id and binding-class
        var subset = all(bindingClass);
        if(subset.containsKey(bindingId)){
            return true;
        }
        return false;
    }

    @Override
    public Binding get(String bindingId) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        // return the binding instance from the managed binding map that contains the given binding-id
        if(bindings.containsKey(bindingId)){
            return bindings.get(bindingId);
        }
        throw new BindingNotFoundException(bindingId);
    }

    @Override
    public <T extends Binding> T get(String bindingId, Class<T> bindingClass) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        // return the binding instance from the managed binding map that contains the given binding-id and binding-class
        var subset = all(bindingClass);
        if(subset.containsKey(bindingId)){
            return (T)subset.get(bindingId);
        }
        throw new BindingNotFoundException(bindingId);
    }

    @Override
    public <T extends Binding> Bindings add(T ... binding) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        logger.trace("invoked 'add()' binding [count={}]", binding.length);

        // iterate the given binding array
        for(var bindingInstance : binding) {

            logger.trace("adding binding to managed binding map [id={}; name={}; class={}]",
                    bindingInstance.id(), bindingInstance.name(), bindingInstance.getClass().getName());

            // ensure requested binding id does not already exist in the managed set
            if (exists(bindingInstance.id())) {
                throw new BindingAlreadyExistsException(bindingInstance.id());
            }

            // attempt to initialize the new binding instance
            initializeBinding(bindingInstance);

            // add new binding to managed set
            bindings.put(bindingInstance.id(), bindingInstance);
            logger.debug("added binding to managed binding map [id={}; name={}; class={}]",
                    bindingInstance.id(), bindingInstance.name(), bindingInstance.getClass().getName());
        }

        return this;
    }

    @Override
    public <T extends Binding> void replace(T binding) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        logger.trace("invoked 'replace()' binding [id={}] with [{}]", binding.id(), binding);

        // ensure requested binding id does exist in the managed set
        if(exists(binding.id())){
            throw new BindingNotFoundException(binding.id());
        }

        // get existing binding instance
        Binding oldBinding = bindings.get(binding.id());

        // attempt to terminate old binding instance
        terminateBinding(oldBinding);

        // attempt to initialize the new binding instance
        initializeBinding(binding);

        // add new binding to managed set
        bindings.put(binding.id(), binding);

        logger.debug("replaced binding in managed binding map [id={}; name={}; class={}]",
                binding.id(), binding.name(), binding.getClass().getName());
    }

    @Override
    public <T extends Binding> void remove(String bindingId) throws BindingException {

        // ensure bindings have been initialized
        if(!initialized) throw new BindingsNotInitialized();

        logger.trace("invoked 'remove() binding' [id={}]", bindingId);

        // ensure requested binding id does exist in the managed set
        if(!bindings.containsKey(bindingId)){
            logger.warn("unable to remove binding [id={}]; id not found in managed binding map.", bindingId);
            throw new BindingNotFoundException(bindingId);
        }

        // get existing binding instance
        Binding oldBinding = bindings.get(bindingId);

        // attempt to terminate old binding instance
        terminateBinding(oldBinding);

        // remove from managed set
        var removedBinding = bindings.remove(bindingId);
        if(removedBinding != null) {
            logger.debug("removed binding from managed binding map [id={}; name={}; class={}]",
                    removedBinding.id(), removedBinding.name(), removedBinding.getClass().getName());
        }
    }

    @Override
    public void initialize(Context context, boolean autoDetect) throws BindingException {

        // ensure bindings have not been initialized
        if(initialized) throw new BindingsAlreadyInitialized();

        // set initialized flag
        initialized = true;
        logger.trace("invoked 'initialize()' [autoDetect={}]", autoDetect);

        // process auto-detect?
        if(autoDetect) {
            logger.trace("auto-detecting Pi4J bindings from the classpath.");

            // detect available bindings by scanning the classpath looking for service io instances
            ServiceLoader<Binding> detectedBindings = ServiceLoader.load(Binding.class);
            for (Binding bindingInstance : detectedBindings) {
                if (bindingInstance != null) {
                    logger.trace("auto-detected binding: [id={}; name={}; class={}]",
                            bindingInstance.id(), bindingInstance.name(), bindingInstance.getClass().getName());
                    try {
                        // add binding instance
                        add(bindingInstance);
                    } catch (Exception ex) {
                        // unable to initialize this binding instance
                        logger.error("unable to 'initialize()' auto-detected binding: [id={}; name={}]; {}",
                                bindingInstance.id(), bindingInstance.name(), ex.getMessage());
                        continue;
                    }
                }
            }

            // if no bindings found, throw exception
            logger.debug("auto-detected and loaded [{}] bindings", bindings.size());
        }
    }

    @Override
    public void terminate(Context context) throws BindingException {

        // ensure bindings have not been initialized
        if(!initialized) throw new BindingsAlreadyInitialized();

        logger.trace("invoked 'terminate();'");

        BindingException bindingException = null;
        // iterate over all providers and invoke the terminate method on each
        var bindingIds = bindings.keySet();
        for(var bindingId : bindingIds){
            try {
                remove(bindingId);
            } catch (Exception e) {
                bindingException = new BindingTerminateException(bindingId, e);
            }
        }

        // clear all bindings
        bindings.clear();

        // set initialized flag
        initialized = false;

        // throw exception if
        if(bindingException != null) throw bindingException;
    }
}
