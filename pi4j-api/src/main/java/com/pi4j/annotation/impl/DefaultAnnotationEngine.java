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
import com.pi4j.context.Context;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
public class DefaultAnnotationEngine implements AnnotationEngine {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Context context = null;

    // static singleton instance
    public static AnnotationEngine newInstance(Context context){
        return new DefaultAnnotationEngine(context);
    }

    // private constructor
    private DefaultAnnotationEngine(Context context) {
        // forbid object construction

        // set local context reference
        this.context = context;
    }

    private static Map<Class<? extends Annotation>, List<Processor>> processors = null;


//    public static void processInitialize(Context context, Object ... objects) throws AnnotationException{
//
//        // iterate over the collection of class objects and perform reflective introspection
//        // looking for Pi4J eligible annotation processors
//        for(Object instance : objects) {
//            // get object class
//            Class instanceClass = instance.getClass();
//
//            // iterate over the class level annotations looking for Pi4J @Initialize
//            Annotation[] classAnnotations = instanceClass.getDeclaredAnnotations();
//            for (Annotation annotation : classAnnotations) {
//                if(annotation.annotationType().isAssignableFrom(Initialize.class)){
//                    System.out.println("@INITIALIZE (CLASS)" + instanceClass.getName());
//
//                    try {
//                        Pi4J.initialize(((Initialize)annotation).autoDetect());
//                    } catch (AlreadyInitializedException e){
//                      // ignore exception
//                    } catch (Pi4JException e) {
//                        throw new AnnotationException(e);
//                    }
//
//                }
//            }
//
//            // iterate over the annotated constructors looking for Pi4J @Initialize
//            Constructor[] constructors  = instanceClass.getDeclaredConstructors();
//            for(Constructor constructor : constructors) {
//                Annotation[] constructorAnnotations = constructor.getDeclaredAnnotations();
//                for (Annotation annotation : constructorAnnotations) {
//                    if (annotation.annotationType().isAssignableFrom(Initialize.class)) {
//                        System.out.println("@INITIALIZE (CTOR)" + constructor.getName());
//
//                        try {
//                            Pi4J.initialize(((Initialize)annotation).autoDetect());
//                        } catch (AlreadyInitializedException e){
//                            // ignore exception
//                        } catch (Pi4JException e) {
//                            throw new AnnotationException(e);
//                        }
//
//                    }
//                }
//            }
//
//            // iterate over the annotated fields looking for Pi4J @Initialize
//            Field[] fields  = instanceClass.getDeclaredFields();
//            for(Field field : fields) {
//                Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
//                for (Annotation annotation : fieldAnnotations) {
//                    if (annotation.annotationType().isAssignableFrom(Initialize.class)) {
//                        System.out.println("@INITIALIZE (FIELD)" + field.getName() + "::" + field.getType().toString());
//
//                        try {
//                            Pi4J.initialize(((Initialize)annotation).autoDetect());
//                        } catch (AlreadyInitializedException e){
//                            // ignore exception
//                        } catch (Pi4JException e) {
//                            throw new AnnotationException(e);
//                        }
//
//                        // if the field is of type `Context`, then lets also inject the Pi4J Context object into the field.
//                        if(field.getType().isAssignableFrom(Context.class)){
//
//                            try {
//                                // attempt to use this annotation processor on this annotated field instance
//                                boolean accessible = field.canAccess(instance);
//                                if (!accessible) field.trySetAccessible();
//
//                                // set Context field
//                                field.set(instance, context);
//
//                                // restore accessibility settings
//                                if (!accessible) field.setAccessible(false);
//
//                            }
//                            catch (Exception e){
//                                StringBuilder message = new StringBuilder(e.getMessage());
//                                message.append(" <");
//                                message.append("Annotation '@");
//                                message.append(annotation.annotationType().getSimpleName());
//                                message.append("' could not be applied to: ");
//                                message.append(field.getDeclaringClass().getName());
//                                message.append("::(");
//                                message.append(field.getType().getName());
//                                message.append(")");
//                                message.append(field.getName());
//                                message.append("@");
//                                message.append(annotation.annotationType().getName());
//                                message.append(">");
//                                throw new AnnotationException(message.toString(), e);
//                            }
//                        }
//                    }
//                }
//            }
//
//
//
//        }
//    }

    @Override
    public void inject(Object... objects) throws AnnotationException {

        // lazy-load IO injector
        if(processors == null){
            processors = new ConcurrentHashMap<>();

            // detect available Pi4J annotation processors by scanning the classpath looking for service provider instances
            ServiceLoader<Processor> detectedProcessors = ServiceLoader.load(Processor.class);
            for (Processor processor : detectedProcessors) {
                if (processor != null) {
                    if(!processors.containsKey(processor.annotationType())){
                        processors.put(processor.annotationType(), new ArrayList<>());
                    }
                    processors.get(processor.annotationType()).add(processor);
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
                    // now we only care about annotations that have been registered
                    // via the annotation processors services -- IGNORE ANY OTHERS
                    if(processors.containsKey(annotation.annotationType())){
                        processFieldAnnotations(instance, field, annotation);
                    }
                }
            }

            // get object class defined methods
            Method[] methods = instanceClass.getDeclaredMethods();

            // iterate method the methods looking for declared annotations
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                // iterate over the annotations looking for Pi4J compatible method processors
                for (Annotation annotation : annotations) {
                    // now we only care about annotations that have been registered
                    // via the annotation processors services -- IGNORE ANY OTHERS
                    if(processors.containsKey(annotation.annotationType())) {
                        processMethodAnnotations(instance, method, annotation);
                    }
                }
            }

        }
    }

    private void processFieldAnnotations(Object instance, Field field, Annotation annotation) throws AnnotationException {

        // get eligible annotation processors for this annotation type
        var eligibleProcessors = processors.get(annotation.annotationType());

        boolean processed_successfully = false;
        for(Processor processor : eligibleProcessors) {

            try {
                // attempt to use this annotation processor on this annotated field instance
                boolean accessible = field.canAccess(instance);
                if (!accessible) field.trySetAccessible();

                // ensure that the annotation processor supports this annotated field instance
                if (processor.isEligible(context, instance, annotation, field)) {

                    // perform annotation processing on this annotated field instance using the annotation processor
                    Object io = processor.process(context, instance, annotation, field);

                    // if a valid object was returned, then update the annotated field instance
                    if (io != null) field.set(instance, io);

                    // restore accessibility settings
                    if (!accessible) field.setAccessible(false);

                    // set success flag
                    processed_successfully = true;
                }
            }
            catch (Exception e){
                String msg = e.getMessage();
                if(StringUtil.isNullOrEmpty(msg)) msg = e.getClass().getName();
                StringBuilder message = new StringBuilder(msg);
                message.append(" <");
                message.append("Annotation '@");
                message.append(annotation.annotationType().getSimpleName());
                message.append("' could not be applied to: ");
                message.append(field.getDeclaringClass().getName());
                message.append("::(");
                message.append(field.getType().getName());
                message.append(")");
                message.append(field.getName());
                message.append("@");
                message.append(annotation.annotationType().getName());
                message.append(">");
                logger.error(message.toString(), e);
                throw new AnnotationException(message.toString(), e);
            }

            // once processed successfully ; exit the loop
            if(processed_successfully) break;
        }

        // if processing injection was not successful, then throw AnnotationFormatError now
        if(!processed_successfully){
            StringBuilder message = new StringBuilder();
            message.append("No valid annotation processor could be found for '@");
            message.append(annotation.annotationType().getSimpleName());
            message.append("' <");
            message.append(field.getDeclaringClass().getName());
            message.append("::(");
            message.append(field.getType().getName());
            message.append(")");
            message.append(field.getName());
            message.append("@");
            message.append(annotation.annotationType().getName());
            message.append(">");
            logger.error(message.toString());
            throw new AnnotationException(message.toString());
        }
    }

    private void processMethodAnnotations(Object instance, Method method, Annotation annotation) throws AnnotationException {

        // get eligible annotation processors for this annotation type
        List<Processor> eligibleProcessors = processors.get(annotation.annotationType());

        boolean processed_successfully = false;
        for(Processor processor : eligibleProcessors) {

            try {
                // attempt to use this annotation processor on this annotated method instance
                boolean accessible = method.canAccess(instance);
                if (!accessible) method.trySetAccessible();

                // ensure that the annotation processor supports this annotated method instance
                if (processor.isEligible(context, instance, annotation, method)) {

                    // perform annotation processing on this annotated method instance using the annotation processor
                    processor.process(context, instance, annotation, method);

                    // restore accessibility settings
                    if (!accessible) method.setAccessible(false);

                    // set success flag
                    processed_successfully = true;
                }
            }
            catch (Exception e){
                StringBuilder message = new StringBuilder(e.getMessage());
                message.append(" <");
                message.append("Annotation '@");
                message.append(annotation.annotationType().getSimpleName());
                message.append("' could not be applied to: ");
                message.append(method.getDeclaringClass().getName());
                message.append("::");
                message.append(method.getName());
                message.append("@");
                message.append(annotation.annotationType().getName());
                message.append(">");
                logger.error(message.toString(), e);
                throw new AnnotationException(message.toString(), e);
            }

            // once processed successfully ; exit the loop
            if(processed_successfully) break;
        }

        // if processing injection was not successful, then throw AnnotationFormatError now
        if(!processed_successfully){
            StringBuilder message = new StringBuilder();
            message.append("No valid annotation processor could be found for '@");
            message.append(annotation.annotationType().getSimpleName());
            message.append("' <");
            message.append(method.getDeclaringClass().getName());
            message.append("::");
            message.append(method.getName());
            message.append("@");
            message.append(annotation.annotationType().getName());
            message.append(">");
            logger.error(message.toString());
            throw new AnnotationException(message.toString());
        }
    }
}
