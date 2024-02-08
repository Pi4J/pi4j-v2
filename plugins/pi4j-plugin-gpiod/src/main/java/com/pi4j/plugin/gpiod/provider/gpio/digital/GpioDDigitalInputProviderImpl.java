package com.pi4j.plugin.gpiod.provider.gpio.digital;

import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.exception.IOAlreadyExistsException;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalInputConfig;
import com.pi4j.io.gpio.digital.DigitalInputProvider;
import com.pi4j.io.gpio.digital.DigitalInputProviderBase;
import com.pi4j.library.gpiod.internal.GpioLine;

public class GpioDDigitalInputProviderImpl extends DigitalInputProviderBase implements GpioDDigitalInputProvider {
    private ActiveGpioChip chipClaim;

    /**
     * <p>Constructor for GpioDDigitalInputProviderImpl.</p>
     */
    public GpioDDigitalInputProviderImpl() {
        this.id = ID;
        this.name = NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DigitalInput create(DigitalInputConfig config) {
        // create new I/O instance based on I/O config
        GpioLine line = this.chipClaim.getGpioChip().getLine(config.address());
        GpioDDigitalInput digitalInput = new GpioDDigitalInput(line, this, config);
        this.context.registry().add(digitalInput);
        return digitalInput;
    }

    @Override
    public int getPriority() {
        // GpioD should be used if available
        return 150;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DigitalInputProvider initialize(Context context) throws InitializeException {
        DigitalInputProvider provider = super.initialize(context);
        this.chipClaim = ActiveGpioChip.getInstance();
        return provider;
    }

    @Override
    public DigitalInputProvider shutdown(Context context) throws ShutdownException {
        if (this.chipClaim != null)
            this.chipClaim.close();
        return super.shutdown(context);
    }
}
