package com.pi4j.test;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutputProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpiodTest {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Attack remote now!");
        Thread.sleep(1000 * 10);
        Context pi4j = Pi4J.newContextBuilder().add(GpioDDigitalOutputProvider.newInstance()).build();
        //Context pi4j = Pi4J.newAutoContext();

        logger.info("\r\n\r\n-----------------------------------\r\n"
            + "Pi4J GPIOD - Runtime Information\r\n"
            + "-----------------------------------");
        pi4j.describe().print(System.out);

        DigitalOutputConfig config = DigitalOutput
            .newConfigBuilder(pi4j)
            .address(17)
            .shutdown(DigitalState.HIGH)
            .initial(DigitalState.HIGH)
            .build();
        DigitalOutput pin = pi4j.create(config);
        for(int i = 0; i < 5; i++) {
            Thread.sleep(1000 * 1);
            pin.state(DigitalState.LOW);
            Thread.sleep(1000 * 1);
            pin.state(DigitalState.HIGH);
        }

        // shutdown Pi4J
        pi4j.shutdown();
    }
}
