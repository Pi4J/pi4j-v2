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
import com.pi4j.annotation.impl.WithAnnotationProcessor;
import com.pi4j.context.Context;
import com.pi4j.io.pwm.Pwm;
import com.pi4j.io.pwm.PwmPreset;
import com.pi4j.io.pwm.PwmPresetBuilder;
import com.pi4j.io.pwm.PwmProvider;
import com.pi4j.platform.Platform;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

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

        // validate that the 'ID' (value) attribute is not empty on this field annotation
        if (StringUtil.isNullOrEmpty(annotation.value()))
            throw new AnnotationException("Missing required 'value <ID>' attribute");

        // make sure the field instance is null; we can only register our own dynamically created I/O instances
        if(field.get(instance) != null)
            throw new AnnotationException("This @Register annotated instance is not null; it must be NULL " +
                    "to register a new I/O instance.  If you just want to access an existing I/O instance, " +
                    "use the '@Inject(id)' annotation instead.");

        // create I/O config builder
        var builder = Pwm.newConfigBuilder();
        if (annotation.value() != null) builder.id((annotation).value());

        // test for required additional annotations
        if (!field.isAnnotationPresent(Address.class))
            throw new AnnotationException("Missing required '@Address' annotation for this I/O type.");

        // all supported additional annotations for configuring the digital output
        Address address = field.getAnnotation(Address.class);
        builder.address(address.value());

        Name name = null;
        if (field.isAnnotationPresent(Name.class)) {
            name = field.getAnnotation(Name.class);
            if (name != null) builder.name(name.value());
        }

        Description description = null;
        if (field.isAnnotationPresent(Description.class)) {
            description = field.getAnnotation(Description.class);
            if (description != null) builder.description(description.value());
        }

        ShutdownValue shutdownValue = null;
        if (field.isAnnotationPresent(ShutdownValue.class)) {
            shutdownValue = field.getAnnotation(ShutdownValue.class);
            if (shutdownValue != null) builder.shutdown(shutdownValue.value());
        }

        InitialValue initialValue = null;
        if (field.isAnnotationPresent(InitialValue.class)) {
            initialValue = field.getAnnotation(InitialValue.class);
            if (initialValue != null) builder.initial(initialValue.value());
        }

        Frequency frequency = null;
        if (field.isAnnotationPresent(Frequency.class)) {
            frequency = field.getAnnotation(Frequency.class);
            if (frequency != null) builder.frequency(frequency.value());
        }

        DutyCycle dutyCycle = null;
        if (field.isAnnotationPresent(DutyCycle.class)) {
            dutyCycle = field.getAnnotation(DutyCycle.class);
            if (dutyCycle != null){
                if(dutyCycle.value() >= 0) {
                    builder.dutyCycle(dutyCycle.value());
                }
            }
        }

        WithPwmType pwmType = null;
        if (field.isAnnotationPresent(WithPwmType.class)) {
            pwmType = field.getAnnotation(WithPwmType.class);
            if (pwmType != null) builder.pwmType(pwmType.value());
        }

        AddPwmPresets pwmPresets = null;
        if (field.isAnnotationPresent(AddPwmPresets.class)) {
            pwmPresets = field.getAnnotation(AddPwmPresets.class);
            AddPwmPreset[] presets  = pwmPresets.value();
            for(AddPwmPreset preset : presets){
                PwmPresetBuilder presetBuilder = PwmPreset.newBuilder(preset.name());
                if(preset.dutyCycle() >= 0)
                    presetBuilder.dutyCycle(preset.dutyCycle());
                if(preset.frequency() >= 0)
                    presetBuilder.frequency(preset.frequency());

                // add applyPreset to PWM config builder
                builder.preset(presetBuilder.build());
            }
        }

        // get designated platform to use to register this IO (if provided)
        Platform platform = null;
        if (field.isAnnotationPresent(WithPlatform.class)) {
            platform = WithAnnotationProcessor.getPlatform(context, field);
        }

        // get designated provider to use to register this IO (if provided)
        PwmProvider provider = null;
        if (field.isAnnotationPresent(WithProvider.class)) {
            provider = WithAnnotationProcessor.getProvider(context, platform, field, PwmProvider.class);
        }

        // if a provider was found, then create PWM IO instance using that provider
        if(provider != null){
            return provider.create(builder.build());
        }

        // if no provider was found, then create PWM IO instance using defaults
        else {
            if(platform != null)
                return platform.provider(PwmProvider.class).create(builder.build());
            else
                return context.provider(PwmProvider.class).create(builder.build());
        }
    }
}
