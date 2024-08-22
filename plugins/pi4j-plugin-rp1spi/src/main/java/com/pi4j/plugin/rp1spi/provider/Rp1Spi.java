package com.pi4j.plugin.rp1spi.provider;


import com.pi4j.context.Context;
import com.pi4j.exception.InitializeException;
import com.pi4j.io.spi.*;
import com.pi4j.library.kernel.SPIapi;
import com.pi4j.library.kernel.SPIapiImpl;
import com.pi4j.library.kernel.SPIapiIntrf;


public class Rp1Spi extends SpiBase implements Spi {

    protected SPIapiImpl functionsSpi = null;

    protected SPIapi.spiApi_t spiApi = null;
    protected static int SPI_BUS_MASK = 0x0100;
    protected static int SPI_MODE_MASK = 0x0003;

    /**
     * <p>Constructor for Rp1Spi </p>
     *
     * @param provider a {@link SpiProvider} object.
     * @param config   a {@link SpiConfig} object.
     *                 <p>
     *                 ------------------------------------------------------------------
     *                 spiFlags consists of the least significant 22 bits.
     *                 ------------------------------------------------------------------
     *                 21 20 19 18 17 16 15 14 13 12 11 10  9  8  7  6  5  4  3  2  1  0
     *                 b  b  b  b  b  b  R  T  n  n  n  n  W  A u2 u1 u0 p2 p1 p0  m  m
     *                 <p>
     *                 [mm]     defines the SPI mode.
     *                 <p>
     *                 Mode POL PHA
     *                 0    0   0
     *                 1    0   1
     *                 2    1   0
     *                 3    1   1
     *                 <p>
     *                 [px]     is 0 if CEx is active low (default) and 1 for active high.
     *                 [ux]     is 0 if the CEx GPIO is reserved for SPI (default) and 1 otherwise.
     *                 [A]      is 0 for the main SPI, 1 for the auxiliary SPI.
     *                 [W]      is 0 if the device is not 3-wire, 1 if the device is 3-wire. Main SPI only.
     *                 [nnnn]   defines the number of bytes (0-15) to write before switching the MOSI line to MISO to read data.
     *                 This field is ignored if W is not set. Main SPI only.
     *                 [T]      is 1 if the least significant bit is transmitted on MOSI first, the default (0) shifts the
     *                 most significant bit out first. Auxiliary SPI only.
     *                 [R]      is 1 if the least significant bit is received on MISO first, the default (0) receives the most
     *                 significant bit first. Auxiliary SPI only.
     *                 [bbbbbb] defines the word size in bits (0-32). The default (0) sets 8 bits per word. Auxiliary SPI only.
     */
    public Rp1Spi(SpiProvider provider, SpiConfig config) {
        super(provider, config);

        // set local reference instance
        try {
            this.functionsSpi = SPIapiIntrf.createSPIapiImpl();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
            // todo   log something...
        }

        this.spiApi = new SPIapi.spiApi_t();


        this.spiApi.dev = new SPIapi.spiApi_device_p.ByReference();


        // get configured SPI bus
        SpiBus bus = SpiBus.BUS_0;

        // get configured SPI mode
        SpiMode mode = SpiMode.MODE_0;

        int baud = config.baud();

        int channel = config.getChannel();

        SpiChipSelect cs = config.getChipSelect();



        // the default value for 'flags' is zero
        int flags = 0;

        // to_do Decode all possible flags to value used
        // in ioctl.
        //TO_do  if bits no longet applicable to we warn or error+out ?
        // if 'flags' were provided in the SPI config, then accept them
        if (config().flags() != null) {
            flags = config().flags().intValue();
        }

        // Mode
        if(config.modeUserProvided()){
            mode = config.mode();
            flags = (flags & (0xFFFFFFFF ^ SPI_MODE_MASK)); // clear Mode bits
            flags |= mode.getMode();
        }else {
            int mode_bits = flags & SPI_MODE_MASK;
            if(mode_bits == 0b01) {
                mode = SpiMode.MODE_1;
            }
            else if(mode_bits == 0b10) {
                mode = SpiMode.MODE_2;
            }
            else if(mode_bits == 0b11) {
                mode = SpiMode.MODE_3;
            }
        }

        // Bus
        if (config.busUserProvided()) {  // user provided, overwrite flags
            bus = config.bus();
        }
        else if ((config.flags() != null)) {  // use bus number from flags
            if((flags & SPI_BUS_MASK) == 0){
                bus = SpiBus.BUS_0;
            }
            else{
                bus = SpiBus.BUS_1;
            }
        }
        // update flags value with BUS bit ('A' 0x0000=BUS0; 0x0100=BUS1)
        if (bus == SpiBus.BUS_0) {
            flags = (flags & (0xFFFFFFFF ^ SPI_BUS_MASK)); // clear AUX bit
        } else if (bus == SpiBus.BUS_1) {
            flags = (flags & (0xFFFFFFFF ^ SPI_BUS_MASK)) | SPI_BUS_MASK; // set AUX bit
        }

        this.spiApi.max_freq = 64000000;

        this.spiApi.bus  = bus.getBus();

        this.spiApi.dev.driver_mode = mode.ordinal();

        // To_do
        // cs needs valid init value
        this.spiApi.cs = cs.ordinal();



     //   channel

     //   cs

        this.functionsSpi.spiApi_init(this.spiApi);


        // set open state flag
        this.isOpen = true;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Spi initialize(Context context) throws InitializeException {
        super.initialize(context);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        // todo ????   call fini  ???
        // this.functionsSpi.spiClose();
        super.close();
    }

    // -------------------------------------------------------------------
    // DEVICE TRANSFER FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int transfer(byte[] write, int writeOffset, byte[] read, int readOffset, int numberOfBytes) {
        return this.functionsSpi.transfer(this.spiApi, write, writeOffset, read, readOffset, numberOfBytes, this.spiApi.max_freq);
    }

    // -------------------------------------------------------------------
    // DEVICE WRITE FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int write(byte b) {
        return this.functionsSpi.spiApi_write_b(this.spiApi, b, this.spiApi.max_freq);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int write(byte[] data, int offset, int length) {
        return this.functionsSpi.spiApi_write(this.spiApi, data, offset, length, this.spiApi.max_freq);
    }


    // -------------------------------------------------------------------
    // RAW DEVICE READ FUNCTIONS
    // -------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() {
        byte b = 0;
        this.functionsSpi.spiApi_read_b(this.spiApi, b, this.spiApi.max_freq);
        return b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] buffer, int offset, int length) {
        return this.functionsSpi.spiApi_read(this.spiApi, buffer, offset, length, this.spiApi.max_freq);
    }


}


