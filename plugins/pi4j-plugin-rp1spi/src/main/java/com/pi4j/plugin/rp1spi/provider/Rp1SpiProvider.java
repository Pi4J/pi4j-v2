package com.pi4j.plugin.rp1spi.provider;

import com.pi4j.io.spi.SpiProvider;



import com.pi4j.library.kernel.SPIapi;
import com.pi4j.plugin.rp1spi.Rp1SpiPlugin;



public interface Rp1SpiProvider extends SpiProvider {
    /** Constant <code>NAME="Rp1SpiPlugin.SPI_PROVIDER_NAME"</code> */
    String NAME = Rp1SpiPlugin.SPI_PROVIDER_NAME;
    /** Constant <code>ID="Rp1SpiPlugin.SPI_PROVIDER_ID"</code> */
    String ID = Rp1SpiPlugin.SPI_PROVIDER_ID;




    static Rp1SpiProvider newInstance() {
        return new Rp1SpiProviderImpl();
    }
}
