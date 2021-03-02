package com.pi4j.io.binding.impl;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (CORE)
 * FILENAME      :  DefaultOnOffBinding.java
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

import com.pi4j.io.OnOff;
import com.pi4j.io.binding.OnOffBinding;
import com.pi4j.io.exception.IOException;
import com.pi4j.io.gpio.analog.AnalogValueChangeEvent;
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>DigitalBindingSync class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DefaultOnOffBinding
        extends BindingBase<OnOffBinding, OnOff>
        implements OnOffBinding {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Integer onValueThreshold = 1;
    protected Integer offValueThreshold = 0;

    /**
     * Default Constructor
     *
     * @param member Variable argument list of digital outputs
     */
    public DefaultOnOffBinding(OnOff ... member){
        super(member);
    }

    /** {@inheritDoc} */
    @Override
    public void process(DigitalStateChangeEvent event) {
        boolean on = event.source().isOn();
        members.forEach((target)->{
            try {
                if(on){
                    if(target.isOff()) target.on();
                } else {
                    if(target.isOn()) target.off();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void process(AnalogValueChangeEvent event) {
        Boolean state = null;

        // ignore change event if ON and OFF thresholds are the same
        if(onValueThreshold == offValueThreshold){
            return;
        }

        if(onValueThreshold > offValueThreshold){
            // greater than or equals
            if(event.value() >= onValueThreshold) {
                state = true;
            }
            else if(event.value() <= offValueThreshold) {
                state = false;
            }
        }
        // handle inverted on/off values
        else {
            // less than or equals
            if(event.value() <= onValueThreshold) {
                state = true;
            }
            else if(event.value() >= offValueThreshold) {
                state = false;
            }
        }

        // if a state was not determined, then bail out
        if(state == null) return;

        // update all target ON/OFF I/O instances based on the determined state
        final boolean onState = state;
        members.forEach((target)->{
            try {
                if(onState){
                    if(target.isOff()) target.on();
                } else {
                    if(target.isOn()) target.off();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public OnOffBinding analogValueThresholdOn(int value) {
        this.onValueThreshold = value;
        return this;
    }

    @Override
    public OnOffBinding analogValueThresholdOff(int value) {
        this.offValueThreshold = value;
        return this;
    }

    @Override
    public int analogValueThresholdOn() {
        return this.onValueThreshold;
    }

    @Override
    public int analogValueThresholdOff() {
        return this.offValueThreshold;
    }
}
