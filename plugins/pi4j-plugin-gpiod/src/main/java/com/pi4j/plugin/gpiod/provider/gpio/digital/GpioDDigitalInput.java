package com.pi4j.plugin.gpiod.provider.gpio.digital;

import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.library.gpiod.internal.GpioD;
import com.pi4j.library.gpiod.internal.GpioDException;
import com.pi4j.library.gpiod.internal.GpioLine;
import com.pi4j.library.gpiod.internal.GpioLineEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>PiGpioDigitalOutput class.</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioDDigitalInput extends DigitalInputBase implements DigitalInput {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ExecutorService executor = Executors.newSingleThreadExecutor(r -> new Thread(r, "Pi4J.GPIO.Monitor"));
    private final GpioLine line;

    /**
     * <p>Constructor for GpioDDigitalInput.</p>
     *
     * @param line a {@link com.pi4j.library.gpiod.internal.GpioLine} object.
     * @param provider a {@link DigitalInputProvider} object.
     * @param config   a {@link DigitalInputConfig} object.
     */
    public GpioDDigitalInput(GpioLine line, DigitalInputProvider provider, DigitalInputConfig config) {
        super(provider, config);
        this.line = line;
        if(config.getDebounce() != null && config.getDebounce() != 0) {
            throw new IllegalArgumentException("Debouncing is not supported yet!");
        }
    }

    @Override
    public DigitalInput initialize(Context context) throws InitializeException {
        try {
            this.line.requestInput(config.getId());
            switch (config.getPull()) {
                case PULL_UP:
                    this.line.requestInputFlags(config.getId(), GpioD.LINE_BIAS.PULL_UP.getVal());
                    break;
                case PULL_DOWN:
                    this.line.requestInputFlags(config.getId(), GpioD.LINE_BIAS.PULL_DOWN.getVal());
                    break;
                case OFF:
                    this.line.requestInputFlags(config.getId(), GpioD.LINE_BIAS.DISABLE.getVal());
                    break;
            }
        } catch (GpioDException e) {
            logger.error(e.getMessage(), e);
            throw new InitializeException(e);
        }
        super.initialize(context);

        Runnable monitorThread = () -> {
            while (true) {
                GpioLineEvent event = this.line.eventRead();
                DigitalState newState;
                if(event.getType() == GpioD.LINE_EVENT.RISING_EDGE) {
                    newState = DigitalState.HIGH;
                } else {
                    newState = DigitalState.LOW;
                }
                GpioDDigitalInput.this.dispatch(new DigitalStateChangeEvent(GpioDDigitalInput.this, newState));

            }
        };
        executor.submit(monitorThread);
        return this;
    }

    @Override
    public DigitalInput shutdown(Context context) throws ShutdownException {
        super.shutdown(context);
        executor.shutdown();
        this.line.release();
        return this;
    }

    @Override
    public DigitalState state() {
        return DigitalState.getState(this.line.getValue());
    }

}
