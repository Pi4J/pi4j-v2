package com.pi4j.plugin.rp1spi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.pi4j.boardinfo.util.BoardInfoHelper;
import com.pi4j.extension.Plugin;
import com.pi4j.extension.PluginService;
import com.pi4j.plugin.rp1spi.provider.Rp1SpiProvider;

import com.pi4j.provider.Provider;




public class Rp1SpiPlugin implements Plugin {

    /**
     * Constant <code>NAME=KernelSpi"</code>
     */
    public static final String NAME = "KernelSpi";
    /**
     * Constant <code>ID="KernelSpi"</code>
     */
    public static final String ID = "rp1";

    /**
     * Constant <code>SPI_PROVIDER_NAME="NAME +  SPI Provider"</code>
     */
    public static final String SPI_PROVIDER_NAME = NAME + " Spi Provider";
    /**
     * Constant <code>SPI_OUTPUT_PROVIDER_ID="ID + -spi"</code>
     */
    public static final String SPI_PROVIDER_ID = ID + "-spi";


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(PluginService service) {


        // create & define supported Linux file system I/O providers that will be exposed to Pi4J via this plugin
        Provider[] providers = {
            Rp1SpiProvider.newInstance()
        };

        // register the  SPI Provider with the plugin service
        service.register(providers);
    }
}
