package com.pi4j.annotation.impl;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DefaultAnnotationEngine.java
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
import com.pi4j.annotation.processor.FieldProcessor;
import com.pi4j.annotation.processor.MethodProcessor;
import com.pi4j.context.Context;
import com.pi4j.event.Event;
import com.pi4j.exception.NotInitializedException;
import com.pi4j.io.gpio.analog.*;
import com.pi4j.io.gpio.digital.*;
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
import java.util.stream.Collectors;


/**
 * <p>
 * </p>
 *
 * @see <a href="http://www.pi4j.com/">http://www.pi4j.com/</a>
 * @author Robert Savage (<a
 *         href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 */
public class DefaultAnnotationEngine implements AnnotationEngine {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;

    // static singleton instance
    private static AnnotationEngine singleton = null;
    public static AnnotationEngine singleton(Context context){
        if(singleton == null){
            singleton = new DefaultAnnotationEngine(context);
        }
        return singleton;
    }

    // private constructor
    private DefaultAnnotationEngine(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    private static Map<Class<? extends Annotation>, List<Processor>> processors = null;

    @Override
    public void inject(Object... objects) throws AnnotationException {

        // lazy-load IO injector
        if(processors == null){
            processors = new ConcurrentHashMap<>();

            // detect available Pi4J annotation processors by scanning the classpath looking for service provider instances
            ServiceLoader<Processor> detectedProcessors = ServiceLoader.load(Processor.class);
            for (Processor processor : detectedProcessors) {
                if (processor != null) {
                    if(!processors.containsKey(processor.getAnnotationType())){
                        processors.put(processor.getAnnotationType(), new ArrayList<>());
                    }
                    processors.get(processor.getAnnotationType()).add(processor);
                }
            }
        }

        // iterate over the collection of class objects and perform reflective introspection
        // looking for Pi4J eligible annotation processors
        for(Object instance : objects) {
            // get object class
            Class instanceClass = instance.getClass();

//            Annotation rpa = instanceClass.getAnnotation(Register.class);
//            if(rpa != null){
//                if(Provider.class.isAssignableFrom(instanceClass)) {
//                    Provider prov = (Provider)instance;
//                    try {
//                        Pi4J.providers().add(prov);
//                    } catch (ProviderException e) {
//                        logger.error(e.getMessage(), e);
//                        throw new AnnotationException(e);
//                    } catch (NotInitializedException e) {
//                        logger.error(e.getMessage(), e);
//                        throw new AnnotationException(e);
//                    }
//                }
//            }

            // get object class defined fields
            Field[] fields = instanceClass.getDeclaredFields();

            // iterate over the fields looking for declared annotations
            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                // iterate over the annotations looking for Pi4J compatible injector
                for (Annotation annotation : annotations) {
                    //processInjectionAnnotation(instance, field, annotation);
                    processFieldAnnotations(instance, field, annotation);
                }
            }

            // get object class defined methods
            Method[] methods = instanceClass.getDeclaredMethods();

            // iterate method the methods looking for declared annotations
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                // iterate over the annotations looking for Pi4J compatible injector
                for (Annotation annotation : annotations) {
                    processMethodAnnotations(instance, method, annotation);
                }
            }

        }
    }

    private void processCustomMethodAnnotations(Object instance, Method method, Annotation annotation) throws AnnotationException {

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

    private void processCustomFieldAnnotations(Object instance, Field field, Annotation annotation) throws AnnotationException {

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
                    if (field.isAnnotationPresent(Name.class)) {
                        name = field.getAnnotation(Name.class);
                    }

                    Description description = null;
                    if (field.isAnnotationPresent(Description.class)) {
                        description = field.getAnnotation(Description.class);
                    }

                    ShutdownValue shutdownValue = null;
                    if (field.isAnnotationPresent(ShutdownValue.class)) {
                        shutdownValue = field.getAnnotation(ShutdownValue.class);
                    }

                    InitialValue initialValue = null;
                    if (field.isAnnotationPresent(InitialValue.class)) {
                        initialValue = field.getAnnotation(InitialValue.class);
                    }

                    StepValue stepValue = null;
                    if (field.isAnnotationPresent(StepValue.class)) {
                        stepValue = field.getAnnotation(StepValue.class);
                    }

                    Range range = null;
                    if (field.isAnnotationPresent(Range.class)) {
                        range = field.getAnnotation(Range.class);
                    }

                    AnalogOutputBuilder builder = AnalogOutput.builder();
                    if (registerAnnotation.value() != null) builder.id((registerAnnotation).value());
                    builder.address(address.value());

                    if (name != null) builder.name(name.value());
                    if (description != null) builder.description(description.value());
                    if (shutdownValue != null) builder.shutdown(shutdownValue.value());
                    if (initialValue != null) builder.initial(initialValue.value());
                    if (stepValue != null) builder.step(stepValue.value());
                    if (range != null) builder.min(range.min());
                    if (range != null) builder.max(range.max());

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

            // handle digital output
            if(DigitalOutput.class.isAssignableFrom(field.getType())) {

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
                    if (field.isAnnotationPresent(Name.class)) {
                        name = field.getAnnotation(Name.class);
                    }

                    Description description = null;
                    if (field.isAnnotationPresent(Description.class)) {
                        description = field.getAnnotation(Description.class);
                    }

                    ShutdownState shutdownState = null;
                    if (field.isAnnotationPresent(ShutdownState.class)) {
                        shutdownState = field.getAnnotation(ShutdownState.class);
                    }

                    InitialState initialState = null;
                    if (field.isAnnotationPresent(InitialState.class)) {
                        initialState = field.getAnnotation(InitialState.class);
                    }

                    DigitalOutputBuilder builder = DigitalOutput.builder();
                    if (registerAnnotation.value() != null) builder.id((registerAnnotation).value());
                    builder.address(address.value());

                    if (name != null) builder.name(name.value());
                    if (description != null) builder.description(description.value());
                    if (shutdownState != null) builder.shutdown(shutdownState.value());
                    if (initialState != null) builder.initial(initialState.value());

                    DigitalOutputProvider provider = null;
                    if (field.isAnnotationPresent(com.pi4j.annotation.Provider.class)) {
                        provider = ProviderAnnotationProcessor.instance(field, DigitalOutputProvider.class);
                    } else {
                        provider = Pi4J.providers().getDefault(DigitalOutputProvider.class);
                    }

                    // create I/O instance
                    DigitalOutput output = DigitalOutput.create(provider, builder.build());

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

        }
    }

    private void processFieldAnnotations(Object instance, Field field, Annotation annotation) throws AnnotationException {
        if(processors.containsKey(annotation.annotationType())){

            // get eligible annotation processors for this annotation type
            List<Processor> eligibleProcessors = processors.get(annotation.annotationType());

            boolean processed_successfully = false;
            for(Processor p : eligibleProcessors) {
                if(p instanceof FieldProcessor) {
                    var processor = (FieldProcessor)p;

                    // ensure that the annotation processor supports injecting the user/field-defined IO interface
                    if (processor.getTargetType().isAssignableFrom(field.getType())) {
                        // create the IO instance and inject the instance into the reflected field
                        boolean accessible = field.canAccess(instance);
                        if (!accessible) field.trySetAccessible();
                        try {
                            Object io = processor.process(instance, field, annotation);

                            // if a valid object was returned, then update the annotated field instance
                            if (io != null) field.set(instance, io);

                            if (!accessible) field.setAccessible(false);
                            processed_successfully = true;
                        } catch (ProviderException e) {
                            logger.error(e.getMessage(), e);
                            throw new AnnotationException(e);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            throw new AnnotationException(e);
                        }
                    }

                    // if there is only one eligible processor for this annotation type, then throw an AnnotationFormatError
                    else if (eligibleProcessors.size() == 1) {
                        throw new AnnotationFormatError("Annotation '@" + annotation.annotationType().getSimpleName() + "' can only be applied to fields of type: " + processor.getTargetType());
                    }
                }
            }

            // if processing injection was not successful, then throw AnnotationFormatError now
            if(!processed_successfully){
                throw new AnnotationFormatError("Annotation '@" + annotation.annotationType().getSimpleName() + "' could not be applied to field type: " + field.getType());
            }
        }
    }


    private void processMethodAnnotations(Object instance, Method method, Annotation annotation) throws AnnotationException {
        if(processors.containsKey(annotation.annotationType())){

            // get eligible annotation processors for this annotation type
            List<Processor> eligibleProcessors = processors.get(annotation.annotationType());

            // filter the annotated supporting processors to only method processors
            List<Processor> methodProcessors = eligibleProcessors.stream()
                    .filter((processor -> processor instanceof MethodProcessor))
                    .collect(Collectors.toList());

            boolean processed_successfully = false;
            for(Processor p : methodProcessors) {
                var processor = (MethodProcessor)p;

                if(processor.eligible(instance, method, annotation)){

                    // create the IO instance and inject the instance into the reflected field
                    boolean accessible = method.canAccess(instance);
                    if (!accessible) method.trySetAccessible();
                    try {
                        processor.process(instance, method, annotation);

                        if (!accessible) method.setAccessible(false);
                        processed_successfully = true;
                    } catch (ProviderException e) {
                        logger.error(e.getMessage(), e);
                        throw new AnnotationException(e);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new AnnotationException(e);
                    }
                }
            }

            // if processing injection was not successful, then throw AnnotationFormatError now
            if(!processed_successfully){
                throw new AnnotationFormatError("Annotation '@" + annotation.annotationType().getSimpleName() + "' could not be applied to method " + method.getDeclaringClass().getName() + "::" + method.getName() + "; no supporting annotation processor found.");
            }
        }
    }

}
