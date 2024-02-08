package com.pi4j.plugin.gpiod.provider.gpio.digital;

import com.pi4j.library.gpiod.internal.GpioChip;
import com.pi4j.library.gpiod.internal.GpioChipIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

import static java.text.MessageFormat.format;

public class ActiveGpioChip implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(ActiveGpioChip.class);
    private static final ActiveGpioChip instance;

    static {
        instance = new ActiveGpioChip();
    }

    public static ActiveGpioChip getInstance() {
        return instance;
    }

    private final GpioChip gpioChip;

    private ActiveGpioChip() {
        GpioChipIterator iterator = new GpioChipIterator();
        GpioChip found = null;
        while (iterator.hasNext()) {
            GpioChip current = iterator.next();
            if (current.getLabel().contains("pinctrl")) {
                found = current;
                iterator.noCloseCurrent();
                break;
            }
        }
        if (found == null)
            throw new IllegalStateException("Couldn't identify gpiochip!");

        this.gpioChip = found;
        logger.info("Using chip " + this.gpioChip.getName() + " " + this.gpioChip.getLabel());
    }

    public GpioChip getGpioChip() {
        if (this.gpioChip.isOpen())
            return this.gpioChip;

        throw new IllegalStateException(
            format("GPIO Chip {0} {1} is closed!", this.gpioChip.getName(), this.gpioChip.getLabel()));
    }

    public void close() {
        this.gpioChip.close();
    }
}
