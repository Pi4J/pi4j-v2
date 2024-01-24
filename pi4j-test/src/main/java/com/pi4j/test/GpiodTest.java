package com.pi4j.test;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.IOType;
import com.pi4j.test.provider.TestAnalogInputProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpiodTest {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Context pi4j = Pi4J.newAutoContext();

        logger.info("\r\n\r\n-----------------------------------\r\n"
            + "Pi4J - Runtime Information\r\n"
            + "-----------------------------------");
        pi4j.describe().print(System.out);

        // shutdown Pi4J
        pi4j.shutdown();
    }
}