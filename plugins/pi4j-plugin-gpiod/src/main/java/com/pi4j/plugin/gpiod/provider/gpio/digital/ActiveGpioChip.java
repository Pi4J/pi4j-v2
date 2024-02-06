package com.pi4j.plugin.gpiod.provider.gpio.digital;

import com.pi4j.library.gpiod.internal.GpioChip;
import com.pi4j.library.gpiod.internal.GpioChipIterator;

import java.io.Closeable;

public class ActiveGpioChip implements Closeable {
    private static GpioChip gpioChip;
    private static int claimsOpen = 0;

    public ActiveGpioChip() {
        if(gpioChip == null) {
            GpioChipIterator iterator = new GpioChipIterator();
            GpioChip found = null;
            while (iterator.hasNext()) {
                GpioChip current = iterator.next();
                if(current.getLabel().contains("pinctrl")) {
                    found = current;
                    iterator.noCloseCurrent();
                    break;
                }
            }
            if(found == null) {
                throw new IllegalStateException("Couldn't identify gpiochip!");
            }
            ActiveGpioChip.gpioChip = found;
        }
        ActiveGpioChip.claimsOpen += 1;
    }

    public GpioChip getGpioChip() {
        return ActiveGpioChip.gpioChip;
    }

    public void close() {
        ActiveGpioChip.claimsOpen -= 1;
        if(ActiveGpioChip.claimsOpen == 0) {
            ActiveGpioChip.gpioChip.close();
            ActiveGpioChip.gpioChip = null;
        } else if (ActiveGpioChip.claimsOpen < 0) {
            ActiveGpioChip.claimsOpen = 0;
            throw new IllegalStateException("ActiveGpioChip.chipsOpen < 0");
        }
    }
}
