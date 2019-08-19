package com.pi4j.io.pwm;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Pwm.java
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

import com.pi4j.io.IO;

import java.io.IOException;

public interface Pwm extends IO<Pwm, PwmConfig, PwmProvider> {

    static PwmConfigBuilder newConfigBuilder(){
        return PwmConfigBuilder.newInstance();
    }

    default int getAddress(){
        return config().address();
    }
    default int address(){
        return getAddress();
    }

    boolean isOn();
    Pwm on() throws IOException;
    Pwm off() throws IOException;

    default PwmType pwmType(){
        return config().getPwmType();
    }
    default PwmType getPwmType(){
        return pwmType();
    }

    default Pwm on(int dutyCycle) throws IOException{
        if(dutyCycle > 0) {
            setDutyCycle(dutyCycle);
            return on();
        }
        else{
            return off();
        }
    }

    default Pwm on(int dutyCycle, int frequency) throws IOException{
        if(dutyCycle > 0 && frequency  > 0) {
            setDutyCycle(dutyCycle);
            setFrequency(frequency);
            return on();
        }
        else{
            return off();
        }
    }

    default boolean isOff(){
        return !isOn();
    }

    default float getDutyCyclePercent() throws IOException{
        return getDutyCycle() * 100 / getRange();
    }
    default float dutyCyclePercent() throws IOException { return getDutyCyclePercent();}

    int getDutyCycle() throws IOException;
    default int dutyCycle() throws IOException { return getDutyCycle();}

    int getFrequency() throws IOException;
    default int frequency() throws IOException { return getFrequency();}

    void setDutyCycle(int dutyCycle) throws IOException;
    default Pwm dutyCycle(int dutyCycle) throws IOException { setDutyCycle(dutyCycle); return this; }

    default void setDutyCyclePercent(float percent) throws IOException{
        int dutyCycle = Math.round(this.range() * percent / 100);
        setDutyCycle(dutyCycle);
    }
    default Pwm dutyCyclePercent(int percent) throws IOException { setDutyCyclePercent(percent); return this; }

    void setFrequency(int frequency) throws IOException;
    default Pwm frequency(int frequency) throws IOException { setFrequency(frequency); return this; }

    int getRange() throws IOException;
    default int range() throws IOException { return getRange();}

    void setRange(int range) throws IOException;
    default Pwm range(int range) throws IOException { setRange(range); return this; }
}
