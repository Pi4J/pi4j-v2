package com.pi4j.io.gpio.digital.impl;

import com.pi4j.context.Context;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.digital.*;
import com.pi4j.util.StringUtil;


public class DefaultDigitalOutputBuilder implements DigitalOutputBuilder {

    private final Context context;
    private final DigitalOutputConfigBuilder builder;

    /**
     * Creates a new instance of {@link DefaultDigitalOutputBuilder} with the specified context.
     *
     * @param context the context used to initialize the digital output
     * @return a new instance of {@link DigitalOutputBuilder}
     */
    public static DigitalOutputBuilder newInstance(Context context) {
        return new DefaultDigitalOutputBuilder(context);
    }

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected DefaultDigitalOutputBuilder(Context context) {
        super();
        this.context = context;
        this.builder = DigitalOutputConfigBuilder.newInstance();
    }

    @Override
    public DigitalOutputBuilder id(String id) {
        this.builder.id(id);
        return this;
    }

    @Override
    public DigitalOutputBuilder name(String name) {
        this.builder.name(name);
        return this;
    }

    @Override
    public DigitalOutputBuilder description(String description) {
        this.builder.description(description);
        return this;
    }

    @Override
    public DigitalOutputBuilder address(Integer bcm) {
        this.builder.bcm(bcm);
        return this;
    }

    @Override
    public DigitalOutputBuilder shutdown(DigitalState state) {
        this.builder.shutdown(state);
        return this;
    }

    @Override
    public DigitalOutputBuilder initial(DigitalState state) {
        this.builder.initial(state);
        return this;
    }


    @Override
    public DigitalOutput build() {

        // create I/O instance config
        DigitalOutputConfig config = this.builder.build();

        // use default digital output provider
        return context.create(config, IOType.DIGITAL_OUTPUT);
    }
}
