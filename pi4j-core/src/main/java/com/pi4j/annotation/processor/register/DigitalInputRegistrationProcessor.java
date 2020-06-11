package com.pi4j.annotation.processor.register;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalInputRegistrationProcessor.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2020 Pi4J
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

import com.pi4j.annotation.*;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.IOConfigAnnotations;
import com.pi4j.context.Context;
import com.pi4j.io.binding.DigitalBinding;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>DigitalInputRegistrationProcessor class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DigitalInputRegistrationProcessor implements RegisterProcessor<DigitalInput> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** {@inheritDoc} */
    @Override
    public boolean isEligible(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure this field is of type 'DigitalInput'
        if(!DigitalInput.class.isAssignableFrom(field.getType()))
            return false;

        // this processor can process this annotated instance
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public DigitalInput process(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = DigitalInputConfigBuilder.newInstance(context);
        if (StringUtil.isNotNullOrEmpty(annotation.value())) {
            builder.id((annotation).value());
        } else {
            builder.id(field.getName());
        }

        // process all supported optional configuration annotations for this I/O type
        IOConfigAnnotations.processIOConfigAnnotations(builder, field);

        // process additional optional configuration annotations that
        // may be unique to this particular I/O instance type

        if (field.isAnnotationPresent(Pull.class)) {
            Pull pull = field.getAnnotation(Pull.class);
            if (pull != null) builder.pull(pull.value());
        }

        if (field.isAnnotationPresent(Debounce.class)) {
            Debounce debounce = field.getAnnotation(Debounce.class);
            if (debounce != null) builder.debounce(debounce.value(), debounce.unit());
        }

        // use the Pi4J context to create this IO instance
        DigitalInput input = context.create(builder.build());

        // collecting binding members
        List<AddBinding> bindings = new ArrayList<>();

        // get single-binding annotations
        if (field.isAnnotationPresent(AddBinding.class)) {
            AddBinding bindingAnnotation  = field.getAnnotation(AddBinding.class);
            bindings.add(bindingAnnotation);
        }
        // get multi-binding annotations
        if (field.isAnnotationPresent(AddBindings.class)) {
            AddBindings bindingsAnnotation  = field.getAnnotation(AddBindings.class);
            bindings.addAll(List.of(bindingsAnnotation.value()));
        }

        // process all bindings proposed for this object instance
        for(AddBinding binding : bindings){
            String [] ids = binding.value();
            for(String id : ids) {

                Field f = field.getDeclaringClass().getDeclaredField(id);
                boolean originalAccess = f.canAccess(instance);
                if(!originalAccess) f.setAccessible(true);
                if (f != null) {
                    Object bndg = f.get(instance);

                    // add IO member to group as long as it supports the required group interface
                    if (DigitalBinding.class.isInstance(bndg)) {
                        input.bind((DigitalBinding) bndg);
                    } else {
                        throw new AnnotationException("This @AddBinding annotated instance [" + id + "]" +
                                "does not support the required interface [DigitalBinding] to be added to this Digital Input " +
                                "<" + field.getDeclaringClass() + "::" + field.getName() + ">");
                    }
                } else {
                    throw new AnnotationException("This @AddBinding annotated instance [" + id + "]" +
                            "could not be located in the IO registry and could not be bound to this Digital Input " +
                            "<" + field.getDeclaringClass() + "::" + field.getName() + ">");
                }
                if(!originalAccess) f.setAccessible(false);
            }
        }

        return input;
    }
}
