package com.pi4j.plugin.ffm;

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
import com.pi4j.plugin.ffm.common.FFMPermissionHelper;
import com.pi4j.plugin.ffm.providers.gpio.FFMDigitalInput;
import com.pi4j.plugin.ffm.providers.gpio.FFMDigitalOutput;
import com.pi4j.plugin.ffm.providers.i2c.FFMI2CFactory;
import com.pi4j.plugin.ffm.providers.pwm.FFMPwmHardware;
import com.pi4j.plugin.ffm.providers.spi.FFMSpi;

class FFMContext extends DefaultContext {

    protected FFMContext(ContextConfig config) {
        super(config);
    }

    @Override
    public IO create(IOConfig ioConfig, IOType ioType) {
        FFMPermissionHelper.checkUserPermissions(ioType);
        IO result = switch (ioType) {
            case DIGITAL_INPUT -> new FFMDigitalInput((DigitalInputConfig) ioConfig);
            case DIGITAL_OUTPUT -> new FFMDigitalOutput((DigitalOutputConfig) ioConfig);
            case PWM -> new FFMPwmHardware((PwmConfig) ioConfig);
            case I2C -> FFMI2CFactory.create((I2CConfig) ioConfig);
            case SPI -> new FFMSpi((SpiConfig) ioConfig);
        };
        register(result);
        return result;
    }

}
