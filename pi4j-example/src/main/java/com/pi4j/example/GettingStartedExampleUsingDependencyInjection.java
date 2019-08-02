package com.pi4j.example;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  GettingStartedExampleUsingDependencyInjection.java
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
import com.pi4j.annotation.*;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.platform.Platform;
import com.pi4j.platform.Platforms;
import com.pi4j.provider.Providers;
import com.pi4j.registry.Registry;
import com.pi4j.util.Console;

import java.util.concurrent.Callable;

public class GettingStartedExampleUsingDependencyInjection {

    // ************************************************************
    //
    // WELCOME TO Pi4J:
    //
    // Here we will use this getting started example to
    // demonstrate the basic fundamentals of the Pi4J library.
    //
    // This example is to introduce you to the boilerplate
    // logic and concepts required for all applications using
    // the Pi4J library.  This example won't actually perform
    // any I/O, there are plenty of additional examples in this
    // project that will dig deeper into each specific I/O.
    //
    // ************************************************************

    public static void main(String[] args) throws Exception {

        // Pi4J cannot perform dependency injection on static classes
        // we will create a container instance to run our example
        RuntimeContainer container = new GettingStartedExampleUsingDependencyInjection.RuntimeContainer();

        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'Pi4J' static class includes a few helper context
        // creators for the most common use cases.  The 'newAutoContext()'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'
        //
        // There is optionally a 'ContextBuilder' you can use to
        // build a custom context which may include disabling automatic
        // detection and loading of providers and platform if you
        // need/prefer to manually configure which 'Platforms' and
        // 'I/O Providers' should be used with Pi4J.
        //
        // After we initialize Pi4J, we can access the following
        // core parts of the system:
        //
        //  - Context
        //  - Platforms
        //  - Platform (Default Runtime Platform)
        //  - Providers (I/O Providers)
        //  - Registry (I/O Registry)
        //
        // ------------------------------------------------------------

        // ------------------------------------------------------------
        // Pi4J Dependnecy Injection
        // ------------------------------------------------------------
        // perform dependnecy injection on the container using the
        // (Context)::inject(Object ...) method.
        //
        // !! ATTENTION !!
        // To allow Pi4J to perform dependency injection on your class/objects, you must include
        // an 'opens' directive for each namespace you want Pi4J to inspect in the project's
        // 'module-info.java' file.  (Example:  'opens com.pi4j.example;')
        Pi4J.newAutoContext().inject(container);

        // invoke the container to start the application
        container.call();
    }

    public static class RuntimeContainer implements Callable<Void> {

        // ------------------------------------------------------------
        // Pi4J ANNOTATIONS
        // ------------------------------------------------------------
        //
        // To perform dependency injection, Pi4J supports the following
        // primary annotations:
        //
        // @Inject  -  This annotation decorates a class member/field
        //             to perform a runtime injection of a Pi4J
        //             object such as Context, Provider, Platform,
        //             I/O Instance and more.
        //
        // @Register  -  This annotation is used to register I/O
        //               Providers, Platforms, I/O Instances, etc.
        //               with the Pi4J runtime.
        // ------------------------------------------------------------

        // ------------------------------------------------------------
        // @Inject
        // ------------------------------------------------------------
        // We will use the '@Inject' annotation to perform
        // dependnecy injection on the class members/fields.
        //
        // The '@Inject' annotation will only work if Pi4J has been
        // initialized either via a call to 'Pi4J.initialize()' or via
        // the '@Initialize' annotation on the class, constructor or
        // field of type 'com.pi4j.context.Context'.
        //
        // NOTE: Dependency injection will only work when the
        //       class has been passed into the 'Pi4J.inject()'
        //       method.
        // ------------------------------------------------------------

        // ------------------------------------------------------------
        // Pi4J Context             (INJECTED VIA DEPENDENCY INJECTION)
        // ------------------------------------------------------------
        // When you initialize Pi4J, a 'Context' is returned.  You can
        // also directly obtain the context from the Pi4J static helper
        // class.
        //
        // The 'Context' is a shared container for all Pi4J runtime
        // information, state, extensions and lifecycle.  From the
        // `Context` object you can access just about any part of
        // the Pi4J infrastructure and system.
        //
        // The 'Context' is a static singleton in the lifecycle of
        // your application.
        // ------------------------------------------------------------
        @Inject
        Context pi4j;

        // ------------------------------------------------------------
        // Pi4J Platforms           (INJECTED VIA DEPENDENCY INJECTION)
        // ------------------------------------------------------------
        // Platforms are intended to represent the hardware platform
        // where Pi4J is running.  In most cases this will be the
        // 'RaspberryPi' platform, but Pi4J supports and extensible
        // set of platforms thus additional platforms such as
        // 'BananaPi', 'Odroid', etc can be added.
        //
        // Platforms represent the physical layout of a system's
        // hardware I/O capabilities and what I/O providers the
        // target platform supports.  For example, a 'RaspberryPi'
        // platform supports `Digital` inputs and outputs, PWM, I2C,
        // SPI, and Serial but does not support a default provider
        // for 'Analog' inputs and outputs.
        //
        // Platforms also provide validation for the I/O pins and
        // their capabilities for the target hardware.
        // ------------------------------------------------------------
        @Inject
        Platforms platforms;

        // ------------------------------------------------------------
        // Pi4J Platform (Default Platform) (INJECTED VIA DEPENDENCY INJECTION)
        // ------------------------------------------------------------
        // A single 'default' platform is auto-assigned during Pi4J
        // initialization based on a weighting value provided by each
        // platform implementation at runtime.  Additionally, you can
        // override this behavior and assign your own 'default' platform
        // anytime after initialization.
        //
        // The default platform is a single platform instance from the
        // managed platforms collection that will serve to define the
        // default I/O providers that Pi4J will use for each given I/O
        // interface when creating and registering I/O instances.
        // ------------------------------------------------------------
        @Inject
        Platform platform;

        // ------------------------------------------------------------
        // Pi4J Providers           (INJECTED VIA DEPENDENCY INJECTION)
        // ------------------------------------------------------------
        // Providers are intended to represent I/O implementations
        // and provide access to the I/O interfaces available on
        // the system.  Providers 'provide' concrete runtime
        // implementations of I/O interfaces such as:
        //
        // - DigitalInput
        // - DigitalOutput
        // - AnalogInput
        // - AnalogOutput
        // - PWM
        // - I2C
        // - SPI
        // - SERIAL
        //
        // Each platform will have a default set of providers assigned
        // to it to serve as the default providers that will be used
        // on a given platform's hardware I/O.  However, you are not
        // limited to the providers that a platform provides, you can
        // instantiate I/O interfaces using any provider that has
        // been registered on the Pi4J system.  A good example of
        // this is the 'AnalogInput' and 'AnalogOutput' I/O interfaces.
        // The 'RaspberryPi' does not inherently support analog I/O
        // hardware, but with an attached ADC (Analog to Digital Converter)
        // or DAC (Digital to Analog converter) chip attached to a
        // data bus (I2C/SPI) you may wish to use Pi4J to read/write
        // to these analog hardware interfaces.
        //
        // Providers allow for a completely flexible and extensible
        // infrastructure enabling third-parties to build and extend
        // the capabilities of Pi4J by writing your/their own
        // Provider implementation libraries.
        // ------------------------------------------------------------
        @Inject
        Providers providers;

        // ------------------------------------------------------------
        // Pi4J Registry            (INJECTED VIA DEPENDENCY INJECTION)
        // ------------------------------------------------------------
        // Providers are intended to represent I/O implementations
        // and provide access to the I/O interfaces available on
        // the system.  Providers 'provide' concrete runtime
        // implementations of I/O interfaces such as:
        //
        // - DigitalInput
        // - DigitalOutput
        // - AnalogInput
        // - AnalogOutput
        // - PWM
        // - I2C
        // - SPI
        // - SERIAL
        //
        // Each platform will have a default set of providers assigned
        // to it to serve as the default providers that will be used
        // on a given platform's hardware I/O.  However, you are not
        // limited to the providers that a platform provides, you can
        // instantiate I/O interfaces using any provider that has
        // been registered on the Pi4J system.  A good example of
        // this is the 'AnalogInput' and 'AnalogOutput' I/O interfaces.
        // The 'RaspberryPi' does not inherently support analog I/O
        // hardware, but with an attached ADC (Analog to Digital Converter)
        // or DAC (Digital to Analog converter) chip attached to a
        // data bus (I2C/SPI) you may wish to use Pi4J to read/write
        // to these analog hardware interfaces.
        //
        // Providers allow for a completely flexible and extensible
        // infrastructure enabling third-parties to build and extend
        // the capabilities of Pi4J by writing your/their own
        // Provider implementation libraries.
        // ------------------------------------------------------------
        @Inject
        Registry registry;

        // ------------------------------------------------------------
        // @Register
        // ------------------------------------------------------------
        // We will use the '@Register' annotation to perform
        // the registration of an I/O instance using dependnecy
        // injection.
        //
        // The '@Register' annotation will only work if Pi4J has been
        // initialized either via a call to 'Pi4J.initialize()' or via
        // the '@Initialize' annotation on the class, constructor or
        // field of type 'com.pi4j.context.Context'.
        //
        // NOTE: '@Register' can be used to register the following
        //       with Pi4J:
        //        - I/O Instances
        //        - Providers
        //        - Platforms
        // ------------------------------------------------------------

        // Here we will create an I/O interface for a (GPIO) digital output pin.
        // More comprehensive examples and descriptions are provided in other examples;
        // we just want to include a single I/O instance here as a simple example for
        // this demonstration.  Since no specific 'provider' is defined, Pi4J will
        // use the default `DigitalOutputProvider` for the current default platform.

        // create a digital output instance using the default platform's
        // digital output provider
        @Register("my-digital-output-1")
        @Address(1)
        @Name("My Digital Output #1")
        @ShutdownState(DigitalState.HIGH)
        @InitialState(DigitalState.HIGH)
        private DigitalOutput output;


        /**
         * This method invokes the Pi4J example application runtime .
         * @return nothing
         * @throws Exception
         */
        @Override
        public Void call() throws Exception {

            // create Pi4J console wrapper/helper
            // (This is a utility class to abstract some of the boilerplate stdin/stdout code)
            final var console = new Console();

            // print program title/header
            console.title("<-- The Pi4J Project -->", "Getting Started Example Using Dependency Injections");

            // let's print out to the console the detected and loaded
            // platforms that Pi4J detected when it was initialized.
            console.box("Pi4J PLATFORMS");
            platforms.describe().print(System.out);
            console.println();

            // let's print out to the console the detected and loaded
            // platforms that Pi4J detected when it was initialized.
            console.box("Pi4J DEFAULT PLATFORM");
            platform.describe().print(System.out);
            console.println();

            // let's print out to the console the detected and loaded
            // providers that Pi4J detected when it was initialized.
            console.box("Pi4J PROVIDERS");
            providers.describe().print(System.out);
            console.println();

            // let's print out to the console the detected and loaded
            // I/O interfaces registered with Pi4J and included in the 'Registry'.
            console.box("Pi4J REGISTRY");
            registry.describe().print(System.out);
            console.println();

            // ------------------------------------------------------------
            // Terminate the Pi4J library
            // ------------------------------------------------------------
            // We we are all done and want to exit our application, we must
            // call the 'shutdown()' function on the Pi4J static helper class.
            // This will ensure that all I/O instances are properly shutdown,
            // released by the the system and shutdown in the appropriate
            // manner.  Terminate will also ensure that any background
            // threads/processes are cleanly shutdown and any used memory
            // is returned to the system.

            // shutdown Pi4J
            pi4j.shutdown();

            // dont return anything
            return null;
        }
    }
}
