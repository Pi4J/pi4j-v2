package com.pi4j.annotation.processor.register;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  PwmRegistrationProcessor.java
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

import com.pi4j.annotation.*;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.annotation.impl.IOConfigAnnotations;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmPreset;
import com.pi4j.io.pwm.PwmPresetBuilder;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>PwmRegistrationProcessor class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class PwmRegistrationProcessor implements RegisterProcessor<Pwm> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** {@inheritDoc} */
    @Override
    public boolean isEligible(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure this field is of type 'Pwm'
        if(!Pwm.class.isAssignableFrom(field.getType()))
            return false;

        // this processor can process this annotated instance
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Pwm process(Context context, Object instance, Register annotation, Field field) throws Exception {

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = Pwm.newConfigBuilder(context);
        if (StringUtil.isNotNullOrEmpty(annotation.value())) {
            builder.id((annotation).value());
        } else {
            builder.id(field.getName());
        }

        // process all supported optional configuration annotations for this I/O type
        IOConfigAnnotations.processIOConfigAnnotations(builder, field);

        // process additional optional configuration annotations that
        // may be unique to this particular I/O instance type

        if (field.isAnnotationPresent(ShutdownValue.class)) {
            ShutdownValue shutdownValue = field.getAnnotation(ShutdownValue.class);
            if (shutdownValue != null) builder.shutdown(shutdownValue.value());
        }

        if (field.isAnnotationPresent(InitialValue.class)) {
            InitialValue initialValue = field.getAnnotation(InitialValue.class);
            if (initialValue != null) builder.initial(initialValue.value());
        }

        if (field.isAnnotationPresent(Frequency.class)) {
            Frequency frequency = field.getAnnotation(Frequency.class);
            if (frequency != null) builder.frequency(frequency.value());
        }

        if (field.isAnnotationPresent(DutyCycle.class)) {
            DutyCycle dutyCycle = field.getAnnotation(DutyCycle.class);
            if (dutyCycle != null){
                if(dutyCycle.value() >= 0) {
                    builder.dutyCycle(dutyCycle.value());
                }
            }
        }

        if (field.isAnnotationPresent(WithPwmType.class)) {
            WithPwmType pwmType = field.getAnnotation(WithPwmType.class);
            if (pwmType != null) builder.pwmType(pwmType.value());
        }

        // add presets members
        List<AddPwmPreset> presets = new ArrayList<>();

        // get single-preset annotations
        if (field.isAnnotationPresent(AddPwmPreset.class)) {
            AddPwmPreset presetAnnotation  = field.getAnnotation(AddPwmPreset.class);
            presets.add(presetAnnotation);
        }
        // get multi-preset annotations
        if (field.isAnnotationPresent(AddPwmPresets.class)) {
            AddPwmPresets presetsAnnotation  = field.getAnnotation(AddPwmPresets.class);
            presets.addAll(List.of(presetsAnnotation.value()));
        }

        // process presets
        for(AddPwmPreset preset : presets){
            PwmPresetBuilder presetBuilder = PwmPreset.newBuilder(preset.name());
            if(preset.dutyCycle() >= 0)
                presetBuilder.dutyCycle(preset.dutyCycle());
            if(preset.frequency() >= 0)
                presetBuilder.frequency(preset.frequency());

            // add applyPreset to PWM config builder
            builder.preset(presetBuilder.build());
        }

        // use the Pi4J context to create this IO instance
        return context.create(builder.build());
    }
}
