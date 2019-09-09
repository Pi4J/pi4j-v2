package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogBase.java
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
import com.pi4j.event.EventManager;
import com.pi4j.io.binding.AnalogBinding;
import com.pi4j.io.binding.Bindable;
import com.pi4j.io.gpio.GpioBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Abstract AnalogBase class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public abstract class AnalogBase<ANALOG_TYPE
        extends Analog<ANALOG_TYPE, CONFIG_TYPE, PROVIDER_TYPE>,
        CONFIG_TYPE extends AnalogConfig<CONFIG_TYPE>,
        PROVIDER_TYPE extends AnalogProvider>
        extends GpioBase<ANALOG_TYPE, CONFIG_TYPE, PROVIDER_TYPE>
        implements Analog<ANALOG_TYPE, CONFIG_TYPE, PROVIDER_TYPE>,
        Bindable<ANALOG_TYPE, AnalogBinding> {

    // internal listeners collection
    protected final EventManager<ANALOG_TYPE, AnalogValueChangeListener> valueChangeEventManager = new EventManager(this);

    // internal bindings collection
    protected List<AnalogBinding> bindings = Collections.synchronizedList(new ArrayList<>());

    /**
     * <p>Constructor for AnalogBase.</p>
     *
     * @param provider a PROVIDER_TYPE object.
     * @param config a CONFIG_TYPE object.
     */
    public AnalogBase(PROVIDER_TYPE provider, CONFIG_TYPE config){
        super(provider, config);
    }

    /** {@inheritDoc} */
    @Override
    public ANALOG_TYPE addListener(AnalogValueChangeListener... listener) {
        valueChangeEventManager.add(listener);
        return (ANALOG_TYPE)this;
    }

    /** {@inheritDoc} */
    @Override
    public ANALOG_TYPE removeListener(AnalogValueChangeListener... listener) {
        valueChangeEventManager.add(listener);
        return (ANALOG_TYPE)this;
    }

    /** {@inheritDoc} */
    @Override
    public ANALOG_TYPE bind(AnalogBinding... binding) {
        bindings.addAll(Arrays.asList(binding));
        return (ANALOG_TYPE)this;
    }

    /** {@inheritDoc} */
    @Override
    public ANALOG_TYPE unbind(AnalogBinding ... binding) {
        bindings.removeAll(Arrays.asList(binding));
        return (ANALOG_TYPE)this;
    }

    /**
     * Dispatch AnalogInputEvent on analog input changes
     *
     * @param event AnalogInputEvent
     */
    protected void dispatch(AnalogValueChangeEvent event){
        valueChangeEventManager.dispatch(event);
        bindings.forEach((binding) -> {
            try {
                binding.process(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public ANALOG_TYPE shutdown(Context context){
        // remove all listeners
        valueChangeEventManager.clear();

        // remove all bindings
        bindings.clear();

        // return this instance
        return (ANALOG_TYPE) this;
    }

}
