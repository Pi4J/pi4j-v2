package com.pi4j.example;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: EXAMPLE  :: Sample Code
 * FILENAME      :  GettingStartedExample2.java
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
import com.pi4j.context.Context;
import com.pi4j.io.gpio.analog.*;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiProviderBase;
import com.pi4j.mock.platform.MockPlatform;
import com.pi4j.mock.provider.gpio.analog.MockAnalogInputProvider;
import com.pi4j.mock.provider.gpio.analog.MockAnalogOutputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalInputProvider;
import com.pi4j.mock.provider.gpio.digital.MockDigitalOutputProvider;
import com.pi4j.mock.provider.i2c.MockI2CProvider;
import com.pi4j.mock.provider.pwm.MockPwmProvider;
import com.pi4j.mock.provider.serial.MockSerialProvider;
import com.pi4j.mock.provider.spi.MockSpiProvider;

public class GettingStartedExample2 {

    public static void main(String[] args) throws Exception {


        class MyCustomADCProvider extends AnalogInputProviderBase implements AnalogInputProvider {

            @Override
            public AnalogInput create(AnalogInputConfig config) throws Exception {
                return null;
            }
        }

        class MyCustomSPIProvider extends SpiProviderBase
        {

            @Override
            public Spi create(SpiConfig config) throws Exception {
                return null;
            }
        }

        Context pi4j = Pi4J.newContextBuilder()
                .add(new MockPlatform())
                .add(MockAnalogInputProvider.newInstance(),
                        MockAnalogOutputProvider.newInstance(),
                        MockSpiProvider.newInstance(),
                        MockPwmProvider.newInstance(),
                        MockSerialProvider.newInstance(),
                        MockI2CProvider.newInstance(),
                        MockDigitalInputProvider.newInstance(),
                        MockDigitalOutputProvider.newInstance())
                .add(new MyCustomADCProvider(/* implements AnalogInputProvider, id="my-adc-prov" */))
                .add(new MyCustomSPIProvider(/* implements SpiProvider, id="my-spi-prov" */))
                .build();


        // create I/O using using simple static helper/convenience method
        AnalogInput ain1 = pi4j.ain().create(1, "my-custom-name-1");

        // create I/O config
        AnalogInputConfig config = AnalogInputConfigBuilder.newInstance()
                .address(2)
                .name("my-custom-name-2")
                .build();

        // create I/O using config object
        AnalogInput ain2 = pi4j.ain().create(config);

        // create I/O using an I/O builder
//        DigitalOutput dout = DigitalOutputBuilder.newInstance(pi4j)
//                .provider("my-custom-digital-output-provider")
//                .address(1)
//                .name("my-digital-output")
//                .build();


        //pi4j.provider(IOType.DIGITAL_OUTPUT).create({...})
        //pi4j.provider(MockDigitalOutputProvider.class).create({...})


//        AnalogInput ain = AnalogInputBuilder.newInstance(pi4j)
//                .address(2)
//                .name("my-custom-name").build();
//
//                pi4j.ain().create(2, "my-cjustom-name");


        pi4j.describe().print(System.out);

//        ain1.describe().print(System.out);
//        ain2.describe().print(System.out);

        //AnalogInput ain = AnalogInput.create(pi4j, 2);
        //AnalogInput.create()
//
//        AnalogInput ain0  = pi4j.provider(ANALOG_INPUT).create(0);
//
//        AnalogInput ain1  = pi4j.analogInput().create();
//
//        AnalogInput ain1  = pi4j.provider("my-spi-prov").create(1);
//        AnalogInput ain2  = pi4j.provider(MyCustomADCProvider.class).create(2);
//
//
//        //this initiation should throw the exception when the pin is already used by another provider
//        AnalogInput ain2 = pi4j.platform().analogOutput().create({})
//
//
//        DigitalOutput dout = pi4j.provider(SPI).digitalOuput().create({config}
//
//                DigitalOutput dout1 = pi4j.provider(SPI).getDigitalOutput("unique_name")
//                // shutdown Pi4J
        pi4j.shutdown();
    }
}
