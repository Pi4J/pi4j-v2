package com.pi4j.example.i2c;

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
import com.pi4j.annotation.I2CAddress;
import com.pi4j.annotation.Name;
import com.pi4j.annotation.Register;
import com.pi4j.annotation.WithProvider;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.mock.provider.i2c.MockI2CProvider;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;

public class I2cExampleUsingDependencyInjection {

    private static final int I2C_BUS = 1;
    private static final int I2C_DEVICE = 0x04;
    private static final int I2C_DEVICE_REGISTER = 0x01;

    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        new RuntimeContainer().call();
    }

    public static class RuntimeContainer implements Callable<Void> {

        // create a digital output instance using the default digital output provider
        @Register("I2C-TEST-DI")
        @Name("My I2C Device")
        @I2CAddress(bus=I2C_BUS, device=I2C_DEVICE)
        @WithProvider(type= MockI2CProvider.class)
        private I2C i2c;

        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "PWM Example using Dependency Injection");

            // Initialize Pi4J with an auto context
            // An auto context includes AUTO-DETECT BINDINGS enabled
            // which will load all detected Pi4J extension libraries
            // (Platforms and Providers) in the class path
            // ...
            // Also, inject this class instance into the Pi4J context
            // for annotation processing and dependency injection
            Context pi4j = Pi4J.newAutoContext().inject(this);

            // we will be reading and writing to register address 0x01
            var register = i2c.register(I2C_DEVICE_REGISTER);

            // --> write a single (8-bit) byte value to the I2C device register
            register.write(0x0D);

            // <-- read a single (8-bit) byte value from the I2C device register
            byte readByte = register.readByte();

            // close I2C device  :: I2C closure will happen automatically when Pi4J shuts down
            //i2c.close();

            // shutdown Pi4J
            pi4j.shutdown();

            // we are done
            return null;
        }
    }
}
