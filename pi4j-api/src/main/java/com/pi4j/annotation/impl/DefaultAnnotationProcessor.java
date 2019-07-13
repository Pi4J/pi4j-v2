package com.pi4j.annotation.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultAnnotationProcessor.java
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

import com.pi4j.Pi4J;
import com.pi4j.annotation.*;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.processor.AnnotationProcessor;
import com.pi4j.context.Context;
import com.pi4j.event.Event;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.gpio.analog.*;
import com.pi4j.io.gpio.digital.DigitalChangeEvent;
import com.pi4j.io.gpio.digital.DigitalChangeListener;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
import com.pi4j.registry.exception.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
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
public class DefaultAnnotationProcessor implements AnnotationProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;

    // static singleton instance
    private static AnnotationProcessor singleton = null;
    public static AnnotationProcessor singleton(Context context){
        if(singleton == null){
            singleton = new DefaultAnnotationProcessor(context);
        }
        return singleton;
    }

    // private constructor
    private DefaultAnnotationProcessor(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    private static Map<Class<? extends Annotation>, List<Injector>> injectors = null;

    @Override
    public void inject(Object... objects) throws AnnotationException {

        // lazy-load IO injectors
        if(injectors == null){
            injectors = new ConcurrentHashMap<>();

            // detect available IO injectors by scanning the classpath looking for service provider instances
            ServiceLoader<Injector> detectedInjectors = ServiceLoader.load(Injector.class);
            for (Injector injector : detectedInjectors) {
                if (injector != null) {
                    if(!injectors.containsKey(injector.getAnnotationType())){
                        injectors.put(injector.getAnnotationType(), new ArrayList<>());
                    }
                    injectors.get(injector.getAnnotationType()).add(injector);
                }
            }
        }

        // iterate over the collection of class objects and perform reflective introspection
        // looking for Pi4J eligible injection annotations
        for(Object instance : objects) {
            // get object class
            Class instanceClass = instance.getClass();
            Annotation rpa = instanceClass.getAnnotation(Register.class);
            if(rpa != null){
                if(Provider.class.isAssignableFrom(instanceClass)) {
                    Provider prov = (Provider)instance;
                    try {
                        Pi4J.providers().add(prov);
                    } catch (ProviderException e) {
                        logger.error(e.getMessage(), e);
                        throw new AnnotationException(e);
                    } catch (NotInitializedException e) {
                        logger.error(e.getMessage(), e);
                        throw new AnnotationException(e);
                    }
                }
            }

            // get object class defined fields
            Field[] fields = instanceClass.getDeclaredFields();

            // iterate over the fields looking for declared annotations
            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                // iterate over the annotations looking for Pi4J compatible injectors
                for (Annotation annotation : annotations) {
                    processInjectionAnnotation(instance, field, annotation);
                    processFieldAnnotations(instance, field, annotation);
                }
            }

            // get object class defined methods
            Method[] methods = instanceClass.getDeclaredMethods();

            // iterate method the methods looking for declared annotations
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                // iterate over the annotations looking for Pi4J compatible injectors
                for (Annotation annotation : annotations) {
                    processMethodAnnotations(instance, method, annotation);
                }
            }

        }
    }

    private void processMethodAnnotations(Object instance, Method method, Annotation annotation) throws AnnotationException {

        if(annotation.annotationType() == OnEvent.class){

            // validate parameter count
            if(method.getParameterCount() != 1) {
                throw new AnnotationException("The '@" +annotation.annotationType().getSimpleName() + "' annotated method must include (1) parameter extended from `Event`");
            }

            // validate parameter type
            if(!Event.class.isAssignableFrom(method.getParameters()[0].getType())) {
                throw new AnnotationException("The '@OnEvent' annotated method must include a parameter extending from `Event`");
            }

            // get method parameters
            Parameter[] parameters = method.getParameters();

            // get event annotation object
            OnEvent onEvent = (OnEvent)annotation;

            // handle specific implementations
            if(parameters[0].getType().isAssignableFrom(DigitalChangeEvent.class)) {

                try {
                    Pi4J.providers().digitalOutput().getDefault().get(onEvent.value()).addListener((DigitalChangeListener) event -> {
                        try {
                            method.trySetAccessible();
                            method.invoke(instance, event);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            logger.error(e.getMessage(), e);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            logger.error(e.getMessage(), e);
                        }
                    });
                } catch (ProviderException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (NotInitializedException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                }

            }

            else if(parameters[0].getType().isAssignableFrom(AnalogChangeEvent.class)) {

                try {
                    Pi4J.providers().analogOutput().getDefault().get(onEvent.value()).addListener((AnalogChangeListener) event -> {
                        try {
                            method.trySetAccessible();
                            method.invoke(instance, event);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            logger.error(e.getMessage(), e);
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                            logger.error(e.getMessage(), e);
                        }
                    });
                } catch (ProviderException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (NotInitializedException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                }
            }
            else {
                throw new AnnotationException("Unhandled '@OnEvent' annotation; unsupported method: " + method.getDeclaringClass().getName() + "::" + method.getName());
            }
        }
    }

    private void processFieldAnnotations(Object instance, Field field, Annotation annotation) throws AnnotationException {

        if(annotation.annotationType() == Register.class){
            if(Provider.class.isAssignableFrom(field.getType())) {
                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    Provider prov = (Provider) field.get(instance);
                    if(prov != null) Pi4J.providers().add(prov);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (NotInitializedException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (ProviderException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                }
            }
        }

        if(annotation.annotationType() == Register.class){
            Register registerAnnotation = (Register)annotation;

            // handle digital change listeners
            if(DigitalChangeListener.class.isAssignableFrom(field.getType())) {

                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    DigitalChangeListener listener = (DigitalChangeListener) field.get(instance);
                    if(listener != null) {
                        Pi4J.providers().digitalOutput().getDefault().get(registerAnnotation.value()).addListener(listener);
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (NotInitializedException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (ProviderException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                }
            }

            // handle analog change listeners
            if(AnalogChangeListener.class.isAssignableFrom(field.getType())) {

                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    AnalogChangeListener listener = (AnalogChangeListener) field.get(instance);
                    if(listener != null) {
                        Pi4J.providers().analogOutput().getDefault().get(registerAnnotation.value()).addListener(listener);
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (NotInitializedException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (ProviderException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                }
            }

            // handle analog output
            if(AnalogOutput.class.isAssignableFrom(field.getType())) {

                Class<? extends Provider> providerClass = null;

                // test for required peer annotations
                if (!field.isAnnotationPresent(Address.class)) {
                    throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");
                }
                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();


                    // all supported additional annotations for configuring the digital output
                    Address address = field.getAnnotation(Address.class);
                    Name name = null;
                    Description description = null;
                    ShutdownValue shutdownValue = null;

                    if (field.isAnnotationPresent(Name.class)) {
                        name = field.getAnnotation(Name.class);
                    }

                    if (field.isAnnotationPresent(Description.class)) {
                        description = field.getAnnotation(Description.class);
                    }

                    if (field.isAnnotationPresent(ShutdownValue.class)) {
                        shutdownValue = field.getAnnotation(ShutdownValue.class);
                    }

                    AnalogOutputBuilder builder = AnalogOutput.builder();
                    if (registerAnnotation.value() != null) builder.id((registerAnnotation).value());
                    builder.address(address.value());

                    if (name != null) builder.name(name.value());
                    if (description != null) builder.description(description.value());
                    if (shutdownValue != null) builder.shutdownValue(shutdownValue.value());

                    AnalogOutputProvider provider = null;
                    if (field.isAnnotationPresent(com.pi4j.annotation.Provider.class)) {
                        provider = ProviderAnnotationProcessor.instance(field, AnalogOutputProvider.class);
                    } else {
                        provider = Pi4J.providers().getDefault(AnalogOutputProvider.class);
                    }

                    // create I/O instance
                    AnalogOutput output = AnalogOutput.create(provider, builder.build());

                    // set the I/O instance reference back on the annotated object
                    field.set(instance, output);

                } catch (NotInitializedException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (ProviderException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (RegistryException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                    throw new AnnotationException(e);
                }
            }

//            // handle digital output
//            if(DigitalOutput.class.isAssignableFrom(field.getType())) {
//
//                Class<? extends Provider> providerClass = null;
//
//                // test for required peer annotations
//                if (!field.isAnnotationPresent(Address.class)) {
//                    throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");
//                }
//                try {
//                    boolean accessible = field.canAccess(instance);
//                    if (!accessible) field.trySetAccessible();
//
//
//                    // all supported additional annotations for configuring the digital output
//                    Address address = field.getAnnotation(Address.class);
//                    Name name = null;
//                    Description description = null;
//                    ShutdownValue shutdownValue = null;
//
//                    if (field.isAnnotationPresent(Name.class)) {
//                        name = field.getAnnotation(Name.class);
//                    }
//
//                    if (field.isAnnotationPresent(Description.class)) {
//                        description = field.getAnnotation(Description.class);
//                    }
//
//                    if (field.isAnnotationPresent(ShutdownValue.class)) {
//                        shutdownValue = field.getAnnotation(ShutdownValue.class);
//                    }
//
//                    AnalogOutputBuilder builder = AnalogOutput.builder();
//                    if (registerAnnotation.value() != null) builder.id((registerAnnotation).value());
//                    builder.address(address.value());
//
//                    if (name != null) builder.name(name.value());
//                    if (description != null) builder.description(description.value());
//                    if (shutdownValue != null) builder.shutdownValue(shutdownValue.value());
//
//                    DigitalOutputProvider provider = null;
//                    if (field.isAnnotationPresent(com.pi4j.annotation.Provider.class)) {
//                        provider = ProviderAnnotationProcessor.instance(field, DigitalOutputProvider.class);
//                    } else {
//                        provider = Pi4J.providers().getDefault(DigitalOutputProvider.class);
//                    }
//
//                    // create I/O instance
//                    DigitalOutput output = DigitalOutput.instance(provider, builder.build());
//
//                    // set the I/O instance reference back on the annotated object
//                    field.set(instance, output);
//
//                } catch (NotInitializedException e) {
//                    logger.error(e.getMessage(), e);
//                    throw new AnnotationException(e);
//                } catch (ProviderException e) {
//                    logger.error(e.getMessage(), e);
//                    throw new AnnotationException(e);
//                } catch (RegistryException e) {
//                    logger.error(e.getMessage(), e);
//                    throw new AnnotationException(e);
//                } catch (IllegalAccessException e) {
//                    logger.error(e.getMessage(), e);
//                    throw new AnnotationException(e);
//                }
//            }

        }
    }

    private void processInjectionAnnotation(Object instance, Field field, Annotation annotation) throws AnnotationException {
        if(injectors.containsKey(annotation.annotationType())){
            // get eligible injectors for this annotation type
            List<Injector> eligibleInjectors = injectors.get(annotation.annotationType());

            boolean injected_successfully = false;
            for(Injector injector : eligibleInjectors) {
                // ensure that the injector supports injecting the user/field-defined IO interface
                if (injector.getTargetType().isAssignableFrom(field.getType())) {
                    // create the IO instance and inject the instance into the reflected field
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    try {
                        Object io = injector.instance(field, annotation);
                        field.set(instance, io);
                        if (!accessible) field.setAccessible(false);
                        injected_successfully = true;
                    } catch (ProviderException e) {
                        logger.error(e.getMessage(), e);
                        throw new AnnotationException(e);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new AnnotationException(e);
                    }
                }

                // if there is only one eligible injector for this annotation type, then throw an AnnotationFormatError
                else if(eligibleInjectors.size() == 1){
                    throw new AnnotationFormatError("Annotation '@" + annotation.annotationType().getSimpleName() + "' can only be applied to fields of type: " + injector.getTargetType());
                }
            }

            // if not injection was successful, then throw AnnotationFormatError now
            if(!injected_successfully){
                throw new AnnotationFormatError("Annotation '@" + annotation.annotationType().getSimpleName() + "' could not be applied to field type: " + field.getType());
            }
        }
    }
}
