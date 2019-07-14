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

import com.pi4j.annotation.AnnotationEngine;
import com.pi4j.annotation.Processor;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.processor.FieldProcessor;
import com.pi4j.annotation.processor.MethodProcessor;
import com.pi4j.context.Context;
import com.pi4j.provider.exception.ProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                // iterate over the annotations looking for Pi4J compatible field processors
                for (Annotation annotation : annotations) {
                    processFieldAnnotations(instance, field, annotation);
                }
            }

            // get object class defined methods
            Method[] methods = instanceClass.getDeclaredMethods();

            // iterate method the methods looking for declared annotations
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                // iterate over the annotations looking for Pi4J compatible method processors
                for (Annotation annotation : annotations) {
                    processMethodAnnotations(instance, method, annotation);
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
