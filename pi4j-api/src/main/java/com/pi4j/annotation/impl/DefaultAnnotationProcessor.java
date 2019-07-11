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
import com.pi4j.annotation.Injector;
import com.pi4j.annotation.OnEvent;
import com.pi4j.annotation.RegisterListener;
import com.pi4j.annotation.RegisterProvider;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.processor.AnnotationProcessor;
import com.pi4j.context.Context;
import com.pi4j.event.Event;
import com.pi4j.io.gpio.analog.AnalogChangeEvent;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.io.gpio.digital.DigitalChangeEvent;
import com.pi4j.io.gpio.digital.DigitalChangeListener;
import com.pi4j.provider.Provider;
import com.pi4j.provider.exception.ProviderException;
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
    public void inject(Object... objects) throws ProviderException, AnnotationException {

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
            Annotation rpa = instanceClass.getAnnotation(RegisterProvider.class);
            if(rpa != null){
                if(Provider.class.isAssignableFrom(instanceClass)) {
                    Provider prov = (Provider)instance;
                    Pi4J.providers().add(prov);
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

    private void processMethodAnnotations(Object instance, Method method, Annotation annotation) throws ProviderException, AnnotationException {

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

            }

            else if(parameters[0].getType().isAssignableFrom(AnalogChangeEvent.class)) {

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
            }

            else {
                throw new AnnotationException("Unhandled '@OnEvent' annotation; unsupported method: " + method.getDeclaringClass().getName() + "::" + method.getName());
            }
        }
    }

    private void processFieldAnnotations(Object instance, Field field, Annotation annotation) throws ProviderException, AnnotationException {

        if(annotation.annotationType() == RegisterProvider.class){
            if(Provider.class.isAssignableFrom(field.getType())) {
                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    Provider prov = (Provider) field.get(instance);
                    if(prov != null) Pi4J.providers().add(prov);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if(annotation.annotationType() == RegisterListener.class){
            RegisterListener registerListener = (RegisterListener)annotation;

            // handle digital change listeners
            if(DigitalChangeListener.class.isAssignableFrom(field.getType())) {

                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    DigitalChangeListener listener = (DigitalChangeListener) field.get(instance);
                    if(listener != null) {
                        Pi4J.providers().digitalOutput().getDefault().get(registerListener.value()).addListener(listener);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
            }

            // handle analog change listeners
            if(AnalogChangeListener.class.isAssignableFrom(field.getType())) {

                try {
                    boolean accessible = field.canAccess(instance);
                    if (!accessible) field.trySetAccessible();
                    AnalogChangeListener listener = (AnalogChangeListener) field.get(instance);
                    if(listener != null) {
                        Pi4J.providers().analogOutput().getDefault().get(registerListener.value()).addListener(listener);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
            }

        }
    }

    private void processInjectionAnnotation(Object instance, Field field, Annotation annotation) throws ProviderException, AnnotationException {
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
                    } catch (ProviderException pe) {
                        throw pe;
                    } catch (Exception e) {
                        throw new ProviderException(e);
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
