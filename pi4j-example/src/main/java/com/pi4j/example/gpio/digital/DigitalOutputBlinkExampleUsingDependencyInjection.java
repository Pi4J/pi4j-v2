package com.pi4j.example.gpio.digital;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  DigitalOutputBlinkExampleUsingDependencyInjection.java
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

import com.pi4j.Pi4J;
import com.pi4j.annotation.Address;
import com.pi4j.annotation.InitialState;
import com.pi4j.annotation.Register;
import com.pi4j.annotation.ShutdownState;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <p>DigitalOutputBlinkExampleUsingDependencyInjection class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class DigitalOutputBlinkExampleUsingDependencyInjection {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        new RuntimeContainer().call();
    }

    public static class RuntimeContainer implements Callable<Void> {

        // create a digital output instance using the default digital output provider
        @Register("My Digital Output Pin")
        @Address(3)  // pin number 3
        @ShutdownState(DigitalState.HIGH)
        @InitialState(DigitalState.HIGH)
        private DigitalOutput output;

        @Override
        public Void call() throws Exception {

            // Initialize Pi4J with an auto context
            // An auto context includes AUTO-DETECT BINDINGS enabled
            // which will load all detected Pi4J extension libraries
            // (Platforms and Providers) in the class path
            // ...
            // Also, inject this class instance into the Pi4J context
            // for annotation processing and dependency injection
            Context pi4j = Pi4J.newAutoContext().inject(this);

            // blink the output for 10 seconds
            output.blink(1, 10, TimeUnit.SECONDS);

            // shutdown Pi4J
            pi4j.shutdown();

            // we are done
            return null;
        }
    }
}
