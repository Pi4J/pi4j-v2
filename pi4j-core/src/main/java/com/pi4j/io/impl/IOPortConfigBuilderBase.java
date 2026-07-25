package com.pi4j.io.impl;

import com.pi4j.config.Config;
import com.pi4j.config.ConfigBuilder;
import com.pi4j.config.PortConfigBuilder;
import com.pi4j.config.impl.PortConfigBuilderBase;
import com.pi4j.context.Context;
import com.pi4j.io.IOConfig;
import com.pi4j.io.IOConfigBuilder;

/**
 * <p>Abstract AddressConfigBuilderBase class.</p>
 *
 * @param <BUILDER_TYPE>
 * @param <CONFIG_TYPE>
 */
public abstract class IOPortConfigBuilderBase<BUILDER_TYPE extends ConfigBuilder, CONFIG_TYPE extends Config>
    extends PortConfigBuilderBase<BUILDER_TYPE, CONFIG_TYPE>
    implements IOConfigBuilder<BUILDER_TYPE, CONFIG_TYPE>,
    PortConfigBuilder<BUILDER_TYPE, CONFIG_TYPE> {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected IOPortConfigBuilderBase(Context context) {
        super(context);
    }

}
