package com.pi4j.provider.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultProviders.java
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

import com.pi4j.context.Context;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.analog.AnalogInputProvider;
import com.pi4j.io.gpio.analog.AnalogOutputProvider;
import com.pi4j.io.gpio.digital.DigitalInputProvider;
import com.pi4j.io.gpio.digital.DigitalOutputProvider;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.provider.Provider;
import com.pi4j.provider.ProviderGroup;
import com.pi4j.provider.Providers;
import com.pi4j.provider.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DefaultProviders implements Providers {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;

    // all detected/available providers
    private Map<String, Provider> providers = new ConcurrentHashMap<>();

    private ProviderGroup<AnalogInputProvider> _analogInput = new ProviderGroup<>(this, IOType.ANALOG_INPUT);
    private ProviderGroup<AnalogOutputProvider> _analogOutput = new ProviderGroup<>(this, IOType.ANALOG_OUTPUT);
    private ProviderGroup<DigitalInputProvider> _digitalInput = new ProviderGroup<>(this, IOType.DIGITAL_INPUT);
    private ProviderGroup<DigitalOutputProvider> _digitalOutput = new ProviderGroup<>(this, IOType.DIGITAL_OUTPUT);
    private ProviderGroup<PwmProvider> _pwm = new ProviderGroup<>(this, IOType.PWM);
    private ProviderGroup<SpiProvider> _spi = new ProviderGroup<>(this, IOType.SPI);
    private ProviderGroup<I2CProvider> _i2c = new ProviderGroup<>(this, IOType.I2C);
    private ProviderGroup<SerialProvider> _serial = new ProviderGroup<>(this, IOType.SERIAL);

    @Override
    public ProviderGroup<AnalogInputProvider> analogInput() { return _analogInput; }

    @Override
    public ProviderGroup<AnalogOutputProvider> analogOutput() { return _analogOutput; }

    @Override
    public ProviderGroup<DigitalInputProvider> digitalInput() { return _digitalInput; }

    @Override
    public ProviderGroup<DigitalOutputProvider> digitalOutput() { return _digitalOutput; }

    @Override
    public ProviderGroup<PwmProvider> pwm() { return _pwm; }

    @Override
    public ProviderGroup<SpiProvider> spi() { return _spi; }

    @Override
    public ProviderGroup<I2CProvider> i2c() { return _i2c; }

    @Override
    public ProviderGroup<SerialProvider> serial() { return _serial; }

    // static singleton instance
    public static Providers newInstance(Context context){
        return new DefaultProviders(context);
    }

    // private constructor
    private DefaultProviders(Context context) {

        // set local context reference
        this.context = context;

        // process auto-detect?
        if(context.config().autoDetectProviders()) {
            logger.trace("auto-detecting providers from the classpath.");

            // detect available providers by scanning the classpath looking for service io instances
            var detectedProviders = ServiceLoader.load(Provider.class);
            for (var providerInstance : detectedProviders) {
                if (providerInstance != null) {
                    logger.trace("auto-detected provider: [id={}; name={}; class={}]",
                            providerInstance.id(), providerInstance.name(), providerInstance.getClass().getName());
                    try {
                        // add provider instance
                        add(providerInstance);
                    } catch (Exception ex) {
                        // unable to initialize this provider instance
                        logger.error("unable to 'initialize()' auto-detected provider: [id={}; name={}]; {}",
                                providerInstance.id(), providerInstance.name(), ex.getMessage());
                        continue;
                    }
                }
            }

        }

        // process any additional configured providers
        Collection<Provider> additionalProviders = context.config().getProviders();
        if(additionalProviders != null && !additionalProviders.isEmpty()) {
            logger.trace("adding explicit provider: [count={}]", additionalProviders.size());
            for (Provider providerInstance : additionalProviders) {
                if (providerInstance != null) {
                    logger.trace("explicit provider: [id={}; name={}; class={}]",
                            providerInstance.id(), providerInstance.name(), providerInstance.getClass().getName());
                    try {
                        // add provider instance
                        add(providerInstance);
                    } catch (Exception ex) {
                        // unable to initialize this provider instance
                        logger.error("unable to 'initialize()' explicit provider: [id={}; name={}]; {}",
                                providerInstance.id(), providerInstance.name(), ex.getMessage());
                        continue;
                    }
                }
            }
        }
        logger.debug("providers loaded [{}]", providers.size());
    }

    protected void initializeProvider(Provider provider) throws ProviderInitializeException {

        // ensure the io object is valid
        if(provider == null) return;

        // attempt to initialize the io instance
        try {
            logger.trace("initializing provider [id={}; name={}; class={}]",
                    provider.id(), provider.name(), provider.getClass().getName());
            provider.initialize(context);
        } catch (Exception e) {
            logger.error("unable to 'initialize()' provider: [id={}; name={}]; {}",
                    provider.id(), provider.name(), e.getMessage());
            logger.error(e.getMessage(), e);
            throw new ProviderInitializeException(provider.id(), e);
        }
    }

    protected void shutdownProvider(Provider provider) throws ProviderTerminateException {

        // ensure the io object is valid
        if(provider == null) return;

        // attempt to shutdown the io instance
        try {
            logger.trace("calling 'shutdown()' provider [id={}; name={}; class={}]",
                    provider.id(), provider.name(), provider.getClass().getName());
            provider.shutdown(context);
        } catch (Exception e) {
            logger.error("unable to 'shutdown()' provider: [id={}; name={}]; {}",
                    provider.id(), provider.name(), e.getMessage());
            logger.error(e.getMessage(), e);
            throw new ProviderTerminateException(provider.id(), e);
        }
    }

    /**
     * Get all providers
     * @return
     */
    @Override
    public Map<String, Provider> all(){
        return Collections.unmodifiableMap(this.providers);
    }

    /**
     * Get all providers of a specified io class type.
     *
     * @param providerClass
     * @param <T>
     * @return
     * @throws ProviderException
     */
    @Override
    public <T extends Provider> Map<String, T> all(Class<T> providerClass) {

        // create a map <io-id, io-instance> of providers that extend of the given io class
        var result = new ConcurrentHashMap<String, T>();
        providers.values().stream().filter(providerClass::isInstance).forEach(p -> {
            result.put(p.id(), providerClass.cast(p));
        });
        return Collections.unmodifiableMap(result);
    }

    /**
     * Get all providers of a specified io class type.
     *
     * @param ioType
     * @param <T>
     * @return
     * @throws ProviderException
     */
    @Override
    public <T extends Provider> Map<String, T> all(IOType ioType) {

        // create a map <io-id, io-instance> of providers that match the given ProviderType
        var result = new ConcurrentHashMap<String, T>();
        providers.values().stream().filter(provider -> provider.isType(ioType)).forEach(provider -> {
            result.put(provider.id(), (T) provider);
        });
        return Collections.unmodifiableMap(result);
    }

    @Override
    public boolean exists(String providerId) {

        // return true if the managed io map contains the given io-id
        if(providers.containsKey(providerId)){
            return true;
        }
        return false;
    }

    @Override
    public <T extends Provider> boolean exists(String providerId, Class<T> providerClass) {

        // return true if the managed io map contains the given io-id and io-class
        var subset = all(providerClass);
        if(subset.containsKey(providerId)){
            return true;
        }
        return false;
    }

    @Override
    public <T extends Provider> boolean exists(String providerId, IOType ioType) {

        // return true if the managed io map contains the given io-id and io-type
        var subset = all(ioType);
        if(subset.containsKey(providerId)){
            return true;
        }
        return false;
    }

    @Override
    public Provider get(String providerId) throws ProviderNotFoundException {
        // return the io instance from the managed io map that contains the given io-id
        if(providers.containsKey(providerId)){
            return providers.get(providerId);
        }
        throw new ProviderNotFoundException(providerId);
    }

    @Override
    public <T extends Provider> T get(String providerId, Class<T> providerClass) throws ProviderNotFoundException {
        // return the io instance from the managed io map that contains the given io-id and io-class
        var subset = all(providerClass);
        if(subset.containsKey(providerId)){
            return (T)subset.get(providerId);
        }
        throw new ProviderNotFoundException(providerClass);
    }

    @Override
    public <T extends Provider> T get(String providerId, IOType ioType) throws ProviderNotFoundException {
        // return the io instance from the managed io map that contains the given io-id and io-type
        var subset = all(ioType);
        if(subset.containsKey(providerId)){
            return (T)subset.get(providerId);
        }
        throw new ProviderNotFoundException(providerId);
    }

    @Override
    public <T extends Provider> T get(Class<T> providerClass) throws ProviderNotFoundException {
        // return the provider instance from the managed provider map that contains the given provider-class
        var subset = all(providerClass);
        if(subset.isEmpty()){
            throw new ProviderNotFoundException(providerClass);
        }
        // return first instance found
        return (T)subset.values().iterator().next();
    }

    @Override
    public <T extends Provider> boolean exists(Class<T> providerClass) {
        // return the provider instance from the managed provider map that contains the given provider-class
        return !(all(providerClass).isEmpty());
    }

    @Override
    public <T extends Provider> T get(IOType ioType) throws ProviderNotFoundException {
        // return the provider instance from the managed provider map that contains the given provider-class
        var subset = all(ioType);
        if(subset.isEmpty()){
            throw new ProviderNotFoundException(ioType);
        }
        // return first instance found
        return (T)subset.values().iterator().next();
    }

    @Override
    public <T extends Provider> boolean exists(IOType ioType){
        // return the provider instance from the managed provider map that contains the given provider-class
        return !(all(ioType).isEmpty());
    }

    private <T extends Provider> Providers add(T ... provider) throws ProviderInitializeException, ProviderAlreadyExistsException {
        return add(Arrays.asList(provider));
    }

    private <T extends Provider> Providers add(Collection<T> provider) throws ProviderAlreadyExistsException, ProviderInitializeException {
        logger.trace("invoked 'add()' io [count={}]", provider.size());

        // iterate the given provider collection
        for(var providerInstance : provider) {

            if(providerInstance != null) {
                logger.trace("adding io to managed io map [id={}; name={}; class={}]",
                        providerInstance.id(), providerInstance.name(), providerInstance.getClass().getName());

                // ensure requested io id does not already exist in the managed set
                if (exists(providerInstance.id())) {
                    throw new ProviderAlreadyExistsException(providerInstance.id());
                }

                // attempt to initialize the new io instance
                initializeProvider(providerInstance);

                // add new io to managed set
                providers.put(providerInstance.id(), providerInstance);
                logger.debug("added io to managed io map [id={}; name={}; class={}]",
                        providerInstance.id(), providerInstance.name(), providerInstance.getClass().getName());
// TODO :: Remove Default Provider impl from Providers
//                // add a default io based on io ranking score
//                if (!hasDefault(providerInstance.getClass())) {
//                    setDefault(providerInstance.id());
//                }
            }
        }

        return this;
    }

    private <T extends Provider> void remove(String providerId) throws ProviderNotFoundException, ProviderTerminateException {

        logger.trace("invoked 'remove() io' [id={}]", providerId);

        // ensure requested io id does exist in the managed set
        if(!providers.containsKey(providerId)){
            logger.warn("unable to remove io [id={}]; id not found in managed providers map.", providerId);
            throw new ProviderNotFoundException(providerId);
        }

        // get existing io instance
        var oldProvider = providers.get(providerId);

        // attempt to shutdown old io instance
        shutdownProvider(oldProvider);

        // remove from managed set
        var removedProvider = providers.remove(providerId);
        if(removedProvider != null) {
            logger.debug("removed io from managed io map [id={}; name={}; class={}]",
                    removedProvider.id(), removedProvider.name(), removedProvider.getClass().getName());
        }

// TODO :: Remove Default Provider impl from Providers
        // also remove from default set if exists
//        try {
//            removeDefault(providerId);
//        }
//        catch (Exception ex){}
    }

//    @Override
//    public <T extends Provider> Map<ProviderType, T> defaults() throws ProviderException {
//        var defaults = new ConcurrentHashMap<ProviderType, T>();
//        for(var providerId : defaultProviders.values()){
//            Provider provider = get(providerId);
//            defaults.put(provider.type(), (T) provider);
//        }
//        return defaults;
//    }
//
//    @Override
//    public <T extends Provider> T getDefault(Class<T> providerClass) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        // iterate the managed default set of providers looking for a default io with a matching io-class
//        for(var instance : defaults().values()){
//            if(providerClass.isAssignableFrom(instance.getClass())){
//                return (T) instance;
//            }
//        }
//
//        throw new ProviderNotFoundException(providerClass);
//    }
//
//    @Override
//    public <T extends Provider> T getDefault(ProviderType providerType) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        // iterate the managed default set of providers looking for a default io with a matching io-type
//        if(defaultProviders.containsKey(providerType)){
//            var providerId = defaultProviders.get(providerType);
//            return get(providerId, providerType);
//        }
//
//        throw new ProviderNotFoundException(providerType);
//    }
//
//    @Override
//    public <T extends Provider> boolean hasDefault(Class<T> providerClass) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        try {
//            return getDefault(providerClass) != null;
//        }
//        catch (ProviderNotFoundException e){
//            return false;
//        }
//    }
//
//    @Override
//    public <T extends Provider> boolean hasDefault(ProviderType providerType) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        try {
//            return getDefault(providerType) != null;
//        }
//        catch (ProviderNotFoundException e){
//            return false;
//        }
//    }
//
//    @Override
//    public void setDefault(String providerId) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        // ensure requested io id is detected
//        if(!providers.containsKey(providerId)){
//            throw new ProviderNotFoundException();
//        }
//
//        logger.trace("invoked 'setDefault()' for io [id={}]", providerId);
//
//        // get io instance and check to see if the default has already been assigned
//        var provider = providers.get(providerId);
////        if(defaultProviders.containsKey(io.type())){
////            throw new ProviderAlreadyAssignedException(defaultProviders.get(io.type()));
////        }
//
//        // assign new io instance by io class
//        defaultProviders.put(provider.type(), providerId);
//
//        logger.debug("set default io for io type [{}] to [id={}; name={}; class={}]",
//                provider.type(), provider.id(), provider.name(), provider.getClass().getName());
//    }
//
//    @Override
//    public void removeDefault(String providerId) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        logger.trace("invoked 'removeDefault()' for io [id={}]", providerId);
//
//        // ensure requested io id is detected
//        var it = defaultProviders.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<ProviderType, String> instance = (Map.Entry<ProviderType, String>) it.next();
//            ProviderType instanceType = instance.getKey();
//            String instanceId = instance.getValue();
//            if(instanceId.equals(providerId)){
//                defaultProviders.remove(instanceType);
//                logger.debug("removed default io for [type={}; id={}]", instanceType, instanceId);
//                return;
//            }
//        }
//        throw new ProviderNotFoundException();
//    }
//
//    @Override
//    public void removeDefault(ProviderType providerType) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        logger.trace("invoked 'removeDefault()' for io [type={}]", providerType);
//
//        // ensure requested io id is detected
//        if(!defaultProviders.containsKey(providerType))
//            throw new ProviderNotFoundException();
//
//        // remove default io type
//        var providerId = defaultProviders.remove(providerType.toString());
//        logger.debug("removed default io for [type={}; id={}]", providerType, providerId);
//    }
//
//    @Override
//    public <T extends Provider> void removeDefault(Class<T> providerClass) throws ProviderException {
//
//        // ensure providers have been initialized
//        if(!initialized) throw new ProvidersNotInitializedException();
//
//        logger.trace("invoked 'removeDefault()' for io [class={}]", providerClass.getName());
//
//        // ensure requested io id is detected
//        for(var provider : defaults().values()){
//            if(providerClass.isAssignableFrom(provider.getClass())){
//                removeDefault(provider.id());
//                return;
//            }
//        }
//
//        throw new ProviderNotFoundException();
//    }

    @Override
    public void shutdown(Context context) throws ProvidersNotInitializedException, ProviderTerminateException {
        logger.trace("invoked 'shutdown();'");

        ProviderTerminateException providerException = null;
        // iterate over all providers and invoke the shutdown method on each
        var providerIds = providers.keySet();
        for(var providerId : providerIds){
            try {
                remove(providerId);
            } catch (Exception e) {
                providerException = new ProviderTerminateException(providerId, e);
            }
        }

        // clear all providers
        providers.clear();

        // throw exception if
        if(providerException != null) throw providerException;
    }
}
