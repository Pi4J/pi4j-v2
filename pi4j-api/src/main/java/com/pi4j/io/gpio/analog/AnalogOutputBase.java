package com.pi4j.io.gpio.analog;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  AnalogOutputBase.java
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
import com.pi4j.io.exception.IOException;
import com.pi4j.io.exception.IOIllegalValueException;
import com.pi4j.io.exception.IOBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AnalogOutputBase extends AnalogBase<AnalogOutput, AnalogOutputConfig> implements AnalogOutput {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Integer value = 0;

    public AnalogOutputBase(AnalogOutputConfig config){
        super(config);
        if(this.id == null) this.id = "AOUT-" + config.address();
        if(this.name == null) this.name = "AOUT-" + config.address();

        // update the analog value to the initial value if an initial value was configured
        if(config().initialValue() != null){
            try {
                value(config().initialValue());
            } catch (IOIllegalValueException e) {
                logger.error(e.getMessage() ,e);
            } catch (IOBoundsException e) {
                logger.error(e.getMessage() ,e);
            }
        }
    }

    @Override
    public AnalogOutput stepUp(){
        try {
            return step(config().stepValue());
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }
    }

    @Override
    public AnalogOutput stepDown(){
        try {
            return step(0-config().stepValue());
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }
    }

    @Override
    public AnalogOutput step(Integer value) throws IOIllegalValueException, IOBoundsException {

        // validate value
        if(value == null)
            throw new IOIllegalValueException();

        Integer oldValue = this.value();
        Integer newValue = value + oldValue; // increment value by step increment
        if(config().range() != null){
            newValue = config().range().sanitize(newValue);
        }
        return this.value(newValue);
    }


    @Override
    public AnalogOutput value(Integer value) throws IOIllegalValueException, IOBoundsException {

        // validate value
        if(value == null)
            throw new IOIllegalValueException();

        // validate value bounds
        if(config().range() != null) {
            if(!config().range().validate(value)){
                throw new IOBoundsException(value, config().range().min(), config().range().max());
            }
        }

        // check to see of there is a value change; if there is then we need
        // to update the internal value variable and dispatch the change event
        if(this.value().intValue() != value.intValue()){
            // cache copy of old value for change event
            Integer oldValue = this.value();

            // update current/new value
            this.value = value;

            // dispatch value change event
            this.dispatch(new AnalogChangeEvent<AnalogOutputBase>(this, this.value(), oldValue));
        }
        return this;
    }

    @Override
    public AnalogOutput terminate(Context context){
        // update the analog value to the shutdown value if a shutdown value is configured
        if(config().shutdownValue() != null){
            try {
                value(config().shutdownValue());
            } catch (IOIllegalValueException e) {
                logger.error(e.getMessage() ,e);
            } catch (IOBoundsException e) {
                logger.error(e.getMessage() ,e);
            }
        }
        return this;
    }

    @Override
    public Integer value() {
        return this.value;
    }
}
