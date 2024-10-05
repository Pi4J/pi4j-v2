package com.pi4j.plugin.rp1spi.provider;

import com.pi4j.boardinfo.util.BoardInfoHelper;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiProviderBase;

public class Rp1SpiProviderImpl extends SpiProviderBase implements Rp1SpiProvider {


    public Rp1SpiProviderImpl() {
        this.id = ID;
        this.name = NAME;
    }

    @Override
    public int getPriority() {
         return BoardInfoHelper.usesRP1() ? 150 : 25;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Spi create(SpiConfig config) {
        //synchronized (this.piGpio) {

            // create new I/O instance based on I/O config
            Rp1Spi spi = new Rp1Spi( this, config);
            this.context.registry().add(spi);
            return spi;
        //}
    }
}
