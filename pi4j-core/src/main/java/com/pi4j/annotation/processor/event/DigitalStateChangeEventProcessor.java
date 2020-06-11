package com.pi4j.annotation.processor.event;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DigitalStateChangeEventProcessor.java
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

import com.pi4j.annotation.OnEvent;
import com.pi4j.annotation.exception.AnnotationException;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.Digital;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import com.pi4j.io.gpio.digital.DigitalStateChangeListener;
import com.pi4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>DigitalChangeEventProcessor class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DigitalStateChangeEventProcessor implements OnEventProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** {@inheritDoc} */
    @Override
    public Class getEventType() {
        return DigitalStateChangeEvent.class;
    }

    /** {@inheritDoc} */
    @Override
    public void process(Context context, Object instance, OnEvent annotation, Method method) throws Exception {

        // validate that the 'ID' (value) attribute is not empty on this field annotation
        if (StringUtil.isNullOrEmpty(annotation.value()))
            throw new AnnotationException("The '@OnEvent' annotation is missing required 'value <ID>' attribute");

        // get I/O instance from registry
        Digital digital = context.registry().get(annotation.value(), Digital.class);

        // register a digital change event listener on this I/O instance
        digital.addListener((DigitalStateChangeListener) event -> {
            try {
                boolean accessible = method.canAccess(instance);
                if(!accessible) method.trySetAccessible();
                method.invoke(instance, event);
                if(!accessible) method.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        });
    }
}
