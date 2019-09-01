package com.pi4j.io.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  DigitalBase.java
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

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.gpio.GpioBase;
import com.pi4j.io.gpio.digital.binding.DigitalBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Abstract DigitalBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class DigitalBase<DIGITAL_TYPE extends Digital<DIGITAL_TYPE, CONFIG_TYPE, PROVIDER_TYPE>,
        CONFIG_TYPE extends DigitalConfig<CONFIG_TYPE>,
        PROVIDER_TYPE extends DigitalProvider>
        extends GpioBase<DIGITAL_TYPE, CONFIG_TYPE, PROVIDER_TYPE>
        implements Digital<DIGITAL_TYPE, CONFIG_TYPE, PROVIDER_TYPE> {


    // internal listeners collection
    protected List<DigitalChangeListener> listeners = Collections.synchronizedList(new ArrayList<>());

    // internal bindings collection
    protected List<DigitalBinding> bindings = Collections.synchronizedList(new ArrayList<>());

    /**
     * <p>Constructor for DigitalBase.</p>
     *
     * @param provider a PROVIDER_TYPE object.
     * @param config a CONFIG_TYPE object.
     */
    public DigitalBase(PROVIDER_TYPE provider, CONFIG_TYPE config){
        super(provider,config);
    }

    /** {@inheritDoc} */
    @Override
    public DIGITAL_TYPE addListener(DigitalChangeListener... listener) {
        listeners.addAll(Arrays.asList(listener));
        return (DIGITAL_TYPE)this;
    }

    /** {@inheritDoc} */
    @Override
    public DIGITAL_TYPE removeListener(DigitalChangeListener... listener) {
        listeners.removeAll(Arrays.asList(listener));
        return (DIGITAL_TYPE)this;
    }

    /** {@inheritDoc} */
    @Override
    public DIGITAL_TYPE bind(DigitalBinding ... binding) {
        bindings.addAll(Arrays.asList(binding));
        return (DIGITAL_TYPE)this;
    }

    /** {@inheritDoc} */
    @Override
    public DIGITAL_TYPE unbind(DigitalBinding ... binding) {
        bindings.removeAll(Arrays.asList(binding));
        return (DIGITAL_TYPE)this;
    }

    /**
     * Dispatch DigitalChangeEvent on digital input state changes
     *
     * @param event DigitalChangeEvent
     */
    protected void dispatch(DigitalChangeEvent event){
        listeners.forEach((listener) -> {
            listener.onChange(event);
        });
        bindings.forEach((binding) -> {
            binding.process(event);
        });
    }

    /** {@inheritDoc} */
    @Override
    public DIGITAL_TYPE shutdown(Context context) throws ShutdownException {
        // remove all listeners
        listeners.clear();

        // remove all bindings
        bindings.clear();

        // return this instance
        return (DIGITAL_TYPE) this;
    }
}
