package com.pi4j.plugin.linuxfs.provider.spi;

import com.pi4j.io.spi.SpiProvider;
import com.pi4j.plugin.linuxfs.LinuxFsPlugin;

/**
 * <p>LinuxFsSpiProvider interface.</p>
 *
 * @author mpilone
 * @since 10/4/24.
 */
public interface LinuxFsSpiProvider extends SpiProvider {

    /** {@link LinuxFsPlugin#SPI_PROVIDER_NAME} */
    String NAME = LinuxFsPlugin.SPI_PROVIDER_NAME;
    /** {@link LinuxFsPlugin#SPI_PROVIDER_ID} */
    String ID = LinuxFsPlugin.SPI_PROVIDER_ID;

    /**
     * <p>newInstance.</p>
     *
     * @return a {@link LinuxFsSpiProviderImpl} object.
     */
    static LinuxFsSpiProviderImpl newInstance() {
        return new LinuxFsSpiProviderImpl();
    }

}
