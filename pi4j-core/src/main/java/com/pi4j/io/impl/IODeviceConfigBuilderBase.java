package com.pi4j.io.impl;

import com.pi4j.config.Config;
import com.pi4j.config.ConfigBuilder;
import com.pi4j.config.DeviceConfigBuilder;
import com.pi4j.config.impl.DeviceConfigBuilderBase;
import com.pi4j.io.IOConfig;
import com.pi4j.io.IOConfigBuilder;

/**
 * <p>Abstract AddressConfigBuilderBase class.</p>
 *
 * @param <BUILDER_TYPE>
 * @param <CONFIG_TYPE>
 */
public abstract class IODeviceConfigBuilderBase<BUILDER_TYPE extends ConfigBuilder, CONFIG_TYPE extends Config>
        extends DeviceConfigBuilderBase<BUILDER_TYPE, CONFIG_TYPE>
        implements IOConfigBuilder<BUILDER_TYPE, CONFIG_TYPE>,
        DeviceConfigBuilder<BUILDER_TYPE, CONFIG_TYPE> {

    /**
     * PRIVATE CONSTRUCTOR
     */
    protected IODeviceConfigBuilderBase(){
    }

}
