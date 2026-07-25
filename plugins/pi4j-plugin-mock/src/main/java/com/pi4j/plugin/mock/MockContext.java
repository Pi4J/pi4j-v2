package com.pi4j.plugin.mock;

import com.pi4j.context.ContextConfig;
import com.pi4j.context.impl.DefaultContext;
import com.pi4j.io.IO;
import com.pi4j.io.IOConfig;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.pwm.PwmConfig;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalInput;
import com.pi4j.plugin.mock.provider.gpio.digital.MockDigitalOutput;
import com.pi4j.plugin.mock.provider.i2c.MockI2C;
import com.pi4j.plugin.mock.provider.pwm.MockPwm;
import com.pi4j.plugin.mock.provider.spi.MockSpi;

class MockContext extends DefaultContext {

    MockContext(ContextConfig config) {
        super(config);
    }

    @Override
    public IO create(IOConfig config, IOType type) {
        IO result = switch (type) {
            case DIGITAL_INPUT -> new MockDigitalInput((DigitalInputConfig) config);
            case DIGITAL_OUTPUT -> new MockDigitalOutput((DigitalOutputConfig) config);
            case PWM -> new MockPwm((PwmConfig) config);
            case I2C -> new MockI2C((I2CConfig) config);
            case SPI -> new MockSpi((SpiConfig) config);
        };
        register(result);
        return result;
    }
}
