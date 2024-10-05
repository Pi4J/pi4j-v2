package com.pi4j.plugin.linuxfs.provider.spi;

import com.pi4j.context.Context;
import com.pi4j.exception.ShutdownException;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.io.spi.SpiProvider;
import com.pi4j.io.spi.SpiProviderBase;

/**
 * @author mpilone
 * @since 10/3/24.
 */
public class LinuxFsSpiProviderImpl extends SpiProviderBase
    implements LinuxFsSpiProvider {

    public LinuxFsSpiProviderImpl() {
        this.id = ID;
        this.name = NAME;
    }

    @Override
    public int getPriority() {
        // the linux FS driver should always be higher priority
        return 150;
    }

    @Override
    public Spi create(SpiConfig config) {
        Spi spi = new LinuxFsSpi(this, config);

        // Is this the right place to call open? Should we have a shared spi device like the I2CBus?
        spi.open();

        this.context.registry().add(spi);
        return spi;
    }

    @Override
    public SpiProvider shutdown(Context context) throws ShutdownException {

        // Is this the right place to call close?
        this.context.registry().allByType(LinuxFsSpi.class).values()
            .forEach(LinuxFsSpi::close);

        return super.shutdown(context);
    }
}
