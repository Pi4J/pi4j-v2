package com.pi4j.io.binding.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultAnalogBinding.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * 
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

import com.pi4j.io.binding.AnalogOutputBinding;
import com.pi4j.io.exception.IOBoundsException;
import com.pi4j.io.exception.IOIllegalValueException;
import com.pi4j.io.gpio.analog.AnalogOutput;
import com.pi4j.io.gpio.analog.AnalogValueChangeEvent;

/**
 * <p>AnalogBindingSync class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultAnalogBinding
        extends BindingBase<AnalogOutputBinding, AnalogOutput>
        implements AnalogOutputBinding {

    /**
     * Default Constructor
     *
     * @param member Variable argument list of analog outputs
     */
    public DefaultAnalogBinding(AnalogOutput ... member){
        super(member);
    }

    /** {@inheritDoc} */
    @Override
    public void process(AnalogValueChangeEvent event) {
        members.forEach((output)->{
            try {
                ((AnalogOutput)output).value(event.value());
            } catch (IOIllegalValueException | IOBoundsException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }
}
