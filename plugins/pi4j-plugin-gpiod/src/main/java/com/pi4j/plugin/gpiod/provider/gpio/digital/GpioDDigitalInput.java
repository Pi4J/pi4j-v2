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

import java.util.concurrent.Future;

/**
 * <p>PiGpioDigitalOutput class.</p>
 *
 * @author Alexander Liggesmeyer (<a href="https://alexander.liggesmeyer.net/">https://alexander.liggesmeyer.net/</a>)
 * @version $Id: $Id
 */
public class GpioDDigitalInput extends DigitalInputBase implements DigitalInput {
    private static final Logger logger = LoggerFactory.getLogger(GpioDDigitalInput.class);
    private final long inputMaxWaitNs = 500 * 1000 * 1000; // 0,5 seconds
    private final GpioLine line;
    private final long debounceNs;
    private Future<?> inputListener;

    /**
     * <p>Constructor for GpioDDigitalInput.</p>
     *
     * @param line     a {@link com.pi4j.library.gpiod.internal.GpioLine} object.
     * @param provider a {@link DigitalInputProvider} object.
     * @param config   a {@link DigitalInputConfig} object.
     */
    public GpioDDigitalInput(GpioLine line, DigitalInputProvider provider, DigitalInputConfig config) {
        super(provider, config);
        this.line = line;
        if (config.getDebounce() == 0) {
            debounceNs = 0;
        } else {
            // Convert microseconds to nanoseconds
            debounceNs = 1000 * config.getDebounce();
        }
    }

    @Override
    public DigitalInput initialize(Context context) throws InitializeException {
        try {
            switch (config.getPull()) {
                case PULL_UP:
                    this.line.requestBothEdgeEventsFlags(config.getId(), GpioD.LINE_REQUEST_FLAG.BIAS_PULL_UP.getVal());
                    break;
                case PULL_DOWN:
                    this.line.requestBothEdgeEventsFlags(config.getId(),
                        GpioD.LINE_REQUEST_FLAG.BIAS_PULL_DOWN.getVal());
                    break;
                case OFF:
                    this.line.requestBothEdgeEventsFlags(config.getId(), GpioD.LINE_REQUEST_FLAG.BIAS_DISABLE.getVal());
                    break;
            }
        } catch (GpioDException e) {
            throw new InitializeException("Failed to initialize input " + this.id, e);
        }
        super.initialize(context);

        Runnable monitorThread = () -> {
            DigitalState lastState = null;
            while (!Thread.interrupted()) {
                long debounceNs = GpioDDigitalInput.this.debounceNs;
                // We have to use this function before calling eventRead() directly, since native methods can't be interrupted.
                // eventRead() is blocking and prevents thread interrupt while running
                while (!GpioDDigitalInput.this.line.eventWait(inputMaxWaitNs)) {
                    if (Thread.interrupted()) {
                        return;
                    }
                }
                GpioLineEvent lastEvent = GpioDDigitalInput.this.line.eventRead();
                long currentTime = System.nanoTime();

                // Perform debouncing
                // If the event is too new to be sure that it is debounced then ...
                while (lastEvent.getTimeNs() + debounceNs >= currentTime) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    // ... wait for remaining debounce time and watch out for new event(s)
                    if (GpioDDigitalInput.this.line.eventWait(
                        Math.min(inputMaxWaitNs, lastEvent.getTimeNs() + debounceNs - currentTime))) {
                        // Repeat if a second event occurred withing debounce interval
                        lastEvent = GpioDDigitalInput.this.line.eventRead();
                    }

                    currentTime = System.nanoTime();
                }

                // Apply event only if the new state is not the same as the last state.
                DigitalState newState = DigitalState.getState(lastEvent.getType() == GpioD.LINE_EVENT.RISING_EDGE);
                if (lastState != newState) {
                    lastState = newState;
                    GpioDDigitalInput.this.dispatch(new DigitalStateChangeEvent<>(GpioDDigitalInput.this, newState));
                }
            }
        };

        this.inputListener = context.submitTask(monitorThread);
        return this;
    }

    @Override
    public DigitalInput shutdown(Context context) throws ShutdownException {
        super.shutdown(context);
        if (this.inputListener != null) {
            if (!this.inputListener.cancel(true))
                logger.error("Failed to cancel input listener!");
            long start = System.currentTimeMillis();
            while (!inputListener.isDone()) {
                if (System.currentTimeMillis() - start > 5000L)
                    throw new IllegalArgumentException("Input listener didn't cancel in 5s");
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    logger.warn("Interrupted, while waiting for input listener to stop");
                }
            }
            logger.info("Shutdown input listener for " + this.id);
        }

        this.line.release();
        return this;
    }

    @Override
    public DigitalState state() {
        return DigitalState.getState(this.line.getValue());
    }
}
