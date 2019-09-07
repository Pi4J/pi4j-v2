package com.pi4j.context;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Context.java
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

import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.common.Describable;
import com.pi4j.common.Descriptor;
import com.pi4j.config.Config;
import com.pi4j.config.ConfigBuilder;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.IO;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.analog.*;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CConfigBuilder;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.pwm.PwmConfigBuilder;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialConfigBuilder;
import com.pi4j.io.serial.SerialProvider;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiConfigBuilder;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.provider.Provider;
import com.pi4j.provider.Providers;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.provider.exception.ProviderInterfaceException;
import com.pi4j.provider.exception.ProviderNotFoundException;
import com.pi4j.registry.Registry;
import com.pi4j.util.PropertiesUtil;
import com.pi4j.util.StringUtil;

import java.util.Map;

/**
 * <p>Context interface.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public interface Context extends Describable {

    /**
     * <p>config.</p>
     *
     * @return a {@link com.pi4j.context.ContextConfig} object.
     */
    ContextConfig config();
    /**
     * <p>properties.</p>
     *
     * @return a {@link com.pi4j.context.ContextProperties} object.
     */
    ContextProperties properties();
    /**
     * <p>providers.</p>
     *
     * @return a {@link com.pi4j.provider.Providers} object.
     */
    Providers providers();
    /**
     * <p>registry.</p>
     *
     * @return a {@link com.pi4j.registry.Registry} object.
     */
    Registry registry();
    /**
     * <p>platforms.</p>
     *
     * @return a {@link com.pi4j.platform.Platforms} object.
     */
    Platforms platforms();

    /**
     * <p>shutdown.</p>
     *
     * @return a {@link com.pi4j.context.Context} object.
     * @throws com.pi4j.exception.LifecycleException if any.
     */
    Context shutdown() throws LifecycleException;
    /**
     * <p>inject.</p>
     *
     * @param objects a {@link java.lang.Object} object.
     * @return a {@link com.pi4j.context.Context} object.
     * @throws com.pi4j.annotation.exception.AnnotationException if any.
     */
    Context inject(Object... objects) throws AnnotationException;

    /**
     * <p>provider.</p>
     *
     * @param providerId a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderNotFoundException if any.
     */
    default <T extends Provider> T provider(String providerId) throws ProviderNotFoundException {
        return (T)providers().get(providerId);
    }

    /**
     * <p>provider.</p>
     *
     * @param providerId a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderNotFoundException if any.
     */
    default <T extends Provider> T provider(String providerId, Class<T> providerClass) throws ProviderNotFoundException {
        return (T)providers().get(providerId);
    }

    /**
     * <p>provider.</p>
     *
     * @param providerClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderNotFoundException if any.
     * @throws com.pi4j.provider.exception.ProviderInterfaceException if any.
     */
    default <T extends Provider> T provider(Class<T> providerClass) throws ProviderNotFoundException, ProviderInterfaceException {
        // return the default provider for this type from the default platform
        if(platform() != null && platform().hasProvider(providerClass))
            return platform().provider(providerClass);

        // return the default provider for this type (outside of default platform)
        if(providers().exists(providerClass))
            return providers().get(providerClass);

        // provider not found
        throw new ProviderNotFoundException(providerClass);
    }

    /**
     * <p>provider.</p>
     *
     * @param ioType a {@link com.pi4j.io.IOType} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderNotFoundException if any.
     */
    default <T extends Provider> T provider(IOType ioType) throws ProviderNotFoundException {
        // return the default provider for this type from the default platform
        if(platform() != null && platform().hasProvider(ioType))
            return platform().provider(ioType);

        // return the default provider for this type (outside of default platform)
        if(providers().exists(ioType))
            return providers().get(ioType);

        // provider not found
        throw new ProviderNotFoundException(ioType);
    }


    /**
     * <p>hasProvider.</p>
     *
     * @param providerId a {@link java.lang.String} object.
     * @return a boolean.
     */
    default boolean hasProvider(String providerId) {
        try {
            return providers().exists(providerId);
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * <p>hasProvider.</p>
     *
     * @param providerClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a boolean.
     */
    default <T extends Provider> boolean hasProvider(Class<T> providerClass) {
        return providers().exists(providerClass);
    }

    /**
     * <p>hasProvider.</p>
     *
     * @param ioType a {@link com.pi4j.io.IOType} object.
     * @param <T> a T object.
     * @return a boolean.
     */
    default <T extends Provider> boolean hasProvider(IOType ioType) {
        return providers().exists(ioType);
    }

    /**
     * <p>ain.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogInputProvider> T ain() throws ProviderException {
        return analogInput();
    }

    /**
     * <p>aout.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogOutputProvider> T aout() throws ProviderException{
        return analogOutput();
    }

    /**
     * <p>din.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalInputProvider> T din() throws ProviderException{
        return digitalInput();
    }

    /**
     * <p>dout.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalOutputProvider> T dout() throws ProviderException{
        return digitalOutput();
    }

    /**
     * <p>analogInput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogInputProvider> T analogInput() throws ProviderException {
        return this.provider(IOType.ANALOG_INPUT);
    }

    /**
     * <p>analogOutput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends AnalogOutputProvider> T analogOutput() throws ProviderException{
        return this.provider(IOType.ANALOG_OUTPUT);
    }

    /**
     * <p>digitalInput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalInputProvider> T digitalInput() throws ProviderException{
        return this.provider(IOType.DIGITAL_INPUT);
    }

    /**
     * <p>digitalOutput.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends DigitalOutputProvider> T digitalOutput() throws ProviderException{
        return this.provider(IOType.DIGITAL_OUTPUT);
    }

    /**
     * <p>pwm.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends PwmProvider> T pwm() throws ProviderException{
        return this.provider(IOType.PWM);
    }

    /**
     * <p>spi.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends SpiProvider> T spi() throws ProviderException{
        return this.provider(IOType.SPI);
    }

    /**
     * <p>i2c.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends I2CProvider> T i2c() throws ProviderException{
        return this.provider(IOType.I2C);
    }

    /**
     * <p>serial.</p>
     *
     * @param <T> a T object.
     * @return a T object.
     * @throws com.pi4j.provider.exception.ProviderException if any.
     */
    default <T extends SerialProvider> T serial() throws ProviderException{
        return this.provider(IOType.SERIAL);
    }

    /**
     * <p>platform.</p>
     *
     * @param <P> a P object.
     * @return a P object.
     */
    default <P extends Platform> P platform(){
        return platforms().getDefault();
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.analog.AnalogOutputConfig} object.
     * @return a {@link com.pi4j.io.gpio.analog.AnalogOutput} object.
     * @throws java.lang.Exception if any.
     */
    default AnalogOutput create(AnalogOutputConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.aout().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, AnalogOutputProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.analog.AnalogInputConfig} object.
     * @return a {@link com.pi4j.io.gpio.analog.AnalogInput} object.
     * @throws java.lang.Exception if any.
     */
    default AnalogInput create(AnalogInputConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.ain().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, AnalogInputProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalOutputConfig} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws java.lang.Exception if any.
     */
    default DigitalOutput create(DigitalOutputConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.dout().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, DigitalOutputProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalInputConfig} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalInput} object.
     * @throws java.lang.Exception if any.
     */
    default DigitalInput create(DigitalInputConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.din().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, DigitalInputProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.pwm.PwmConfig} object.
     * @return a {@link com.pi4j.io.pwm.Pwm} object.
     * @throws java.lang.Exception if any.
     */
    default Pwm create(PwmConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.pwm().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, PwmProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.i2c.I2CConfig} object.
     * @return a {@link com.pi4j.io.i2c.I2C} object.
     * @throws java.lang.Exception if any.
     */
    default I2C create(I2CConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.i2c().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, I2CProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.spi.SpiConfig} object.
     * @return a {@link com.pi4j.io.spi.Spi} object.
     * @throws java.lang.Exception if any.
     */
    default Spi create(SpiConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.spi().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, SpiProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.serial.SerialConfig} object.
     * @return a {@link com.pi4j.io.serial.Serial} object.
     * @throws java.lang.Exception if any.
     */
    default Serial create(SerialConfig config) throws Exception{
        String providerId = config.provider();
        if(StringUtil.isNullOrEmpty(providerId)) {
            return this.serial().create(config);
        }
        else{
            if(this.hasProvider(providerId)){
                return this.provider(providerId, SerialProvider.class).create(config);
            }
            else{
                throw new ProviderNotFoundException(providerId);
            }
        }
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.analog.AnalogOutputConfigBuilder} object.
     * @return a {@link com.pi4j.io.gpio.analog.AnalogOutput} object.
     * @throws java.lang.Exception if any.
     */
    default AnalogOutput create(AnalogOutputConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.analog.AnalogInputConfigBuilder} object.
     * @return a {@link com.pi4j.io.gpio.analog.AnalogInput} object.
     * @throws java.lang.Exception if any.
     */
    default AnalogInput create(AnalogInputConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalOutput} object.
     * @throws java.lang.Exception if any.
     */
    default DigitalOutput create(DigitalOutputConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.gpio.digital.DigitalInputConfigBuilder} object.
     * @return a {@link com.pi4j.io.gpio.digital.DigitalInput} object.
     * @throws java.lang.Exception if any.
     */
    default DigitalInput create(DigitalInputConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.pwm.PwmConfigBuilder} object.
     * @return a {@link com.pi4j.io.pwm.Pwm} object.
     * @throws java.lang.Exception if any.
     */
    default Pwm create(PwmConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.i2c.I2CConfigBuilder} object.
     * @return a {@link com.pi4j.io.i2c.I2C} object.
     * @throws java.lang.Exception if any.
     */
    default I2C create(I2CConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.spi.SpiConfigBuilder} object.
     * @return a {@link com.pi4j.io.spi.Spi} object.
     * @throws java.lang.Exception if any.
     */
    default Spi create(SpiConfigBuilder config) throws Exception{
        return create(config.build());
    }

    /**
     * <p>create.</p>
     *
     * @param config a {@link com.pi4j.io.serial.SerialConfigBuilder} object.
     * @return a {@link com.pi4j.io.serial.Serial} object.
     * @throws java.lang.Exception if any.
     */
    default Serial create(SerialConfigBuilder config) throws Exception{
        return create(config.build());
    }




    default <I extends IO>I create(IOType type, Config config) throws Exception{
        return (I)provider(type).create(config);
    }

    default <I extends IO>I create(IOType type, ConfigBuilder builder) throws Exception{
        return (I)provider(type).create((Config) builder.build());
    }

//    default <T extends IO>T create(String id, Properties properties) throws Exception {
//
//        Map<String,String> keys = PropertiesUtil.subKeys(properties, id);
//
//        System.out.println(keys);
//
//        if(keys.containsKey("type")){
//            String type = keys.get("type");
//            DigitalInputConfig config = DigitalInputConfig.newBuilder().load(keys).build();
//            return (T)create(config);
//        }
//        throw new Pi4JException("Invalid properties to create IO instance [" + id + "]");
//    }


//    default <T extends IO>T create(String id, Map<String,String> properties) throws Exception {
//
//        Map<String,String> keys = PropertiesUtil.subKeys(properties, id);
//
//        System.out.println(keys);
//
//        if(keys.containsKey("type")){
//            String type = keys.get("type");
//            DigitalInputConfig config = DigitalInputConfig.newBuilder().load(keys).build();
//            return (T)create(config);
//        }
//        throw new Pi4JException("Invalid properties to create IO instance [" + id + "]");
//    }

    default <T extends IO>T create(String key) throws Exception {
        return create(key, this.properties().all());
    }

    default <T extends IO>T create(IOType type, String id) throws Exception {
        return create(type, id, this.properties().all());
    }

    default <T extends IO>T create(String key, Map<String,String> properties) throws Exception {

        Map<String,String> keys = PropertiesUtil.subKeys(properties, key);
        Provider provider = null;

        // create by IO provider
        if(keys.containsKey("provider")){
            String providerId = keys.get("provider");
            provider = providers().get(providerId);
            if(provider == null) {
                throw new ProviderNotFoundException(providerId);
            }
        }

        // create by IO TYPE
        else if(keys.containsKey("type")){
            String type = keys.get("type");
            IOType ioType = IOType.valueOf(type);
            provider = providers().get(type);
            if(provider == null) {
                throw new ProviderNotFoundException(type);
            }
        }

        // create IO instance
        ConfigBuilder builder = provider.type().newConfigBuilder();
        builder.load(keys);
        return (T)provider.create((Config) builder.build());
    }

    default <T extends IO>T create(IOType type, String key, Map<String,String> properties) throws Exception {

        Map<String,String> keys = PropertiesUtil.subKeys(properties, key);
        Provider provider = null;

        // create by IO provider
        if(keys.containsKey("provider")){
            String providerId = keys.get("provider");
            provider = providers().get(providerId, type);
        } else {
            provider = providers().get(type);
        }

        if(provider == null) {
            throw new ProviderNotFoundException(type);
        }

        // create IO instance
        ConfigBuilder builder =  type.newConfigBuilder();
        builder.load(keys);
        return (T)provider.create((Config) builder.build());
    }

    /**
     * <p>describe.</p>
     *
     * @return a {@link com.pi4j.common.Descriptor} object.
     */
    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create()
                .category("CONTEXT")
                .name("Runtime Context")
                .type(this.getClass());

        descriptor.add(registry().describe());
        descriptor.add(platforms().describe());
        descriptor.add(providers().describe());
        descriptor.add(properties().describe());
        return descriptor;
    }
}
