package com.pi4j.io.binding.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultDigitalBinding.java
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

import com.pi4j.io.binding.DigitalOutputBinding;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>DigitalBindingSync class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultDigitalBinding
        extends BindingBase<DigitalOutputBinding, DigitalOutput>
        implements DigitalOutputBinding {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean inverted = false;

    /**
     * Default Constructor
     *
     * @param member Variable argument list of analog outputs
     */
    public DefaultDigitalBinding(DigitalOutput ... member){
        super(member);
    }

    /** {@inheritDoc} */
    @Override
    public void process(DigitalStateChangeEvent event) {
        members.forEach((target)->{
            try {
                if(inverted){
                    ((DigitalOutput)target).state(DigitalState.getInverseState(event.state()));
                } else {
                    ((DigitalOutput)target).state(event.state());
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public DigitalOutputBinding invertedState(boolean inverted) {
        this.inverted = inverted;
        return this;
    }

    @Override
    public boolean invertedState() {
        return this.inverted;
    }
}
