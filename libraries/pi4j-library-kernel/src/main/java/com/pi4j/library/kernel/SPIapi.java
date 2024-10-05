package com.pi4j.library.kernel;


import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.util.Console;
import com.sun.jna.*;
import com.sun.jna.Structure.*;


public class SPIapi {

   // Console console = new Console();
   // Context pi4j =  Pi4J.newAutoContext();






    @FieldOrder({"returnEnumGood","returnEnumFail"})
    public static class spiApi_return_t extends Structure {
        public int return_code;
    }

    @FieldOrder({"waldo","where"})
    public static class spiApi_test extends Structure {
        public int waldo;
        public int where;
    }


    @FieldOrder({"max_freq","spi_FD", "bus", "cs", "dev"})
    public static class spiApi_t extends Structure{
        public int max_freq;
        public int spi_FD;
        public int bus;
        public int cs;   //
        public spiApi_device_p.ByReference dev;

        public spiApi_t() {
            super();
        }
    }

    @FieldOrder({"driver_mode","CEx","CExResrv","AuxSPI","wire","bits","firstBitTX","firstBitRX","wSize"})
    public static class spiApi_device_p extends  Structure {
        public int driver_mode;
        public int CEx; // Chip enable low:0
        public int CExResrv; // chip enable SPI1  Not used
        public int AuxSPI; // Auxiliary SPI if 1, not used
        public int wire; // 0:not 3 wire, 1 is 3 wire
        public int bits; // bits before MISO begins, valid wire is 3
        public int firstBitTX; // 1, LSB sent first, 0 LSB first
        public int firstBitRX; // 1, LSB received first, 0 LSB first
        public int wSize;  // 0 : 8 bit word. range 0-32

        public spiApi_device_p(Pointer peer) {
            super(peer);
        }

        public spiApi_device_p() {
            super();
        }

        //ALIGN_NONE
        public static class ByReference extends spiApi_device_p implements Structure.ByReference {

        };

        public static class ByValue extends spiApi_device_p implements Structure.ByValue {
        };
    }


    /*
    typedef struct {
    uint32_t type;
#define RPI_HWVER_TYPE_UNKNOWN                   0
#define RPI_HWVER_TYPE_PI1                       1
#define RPI_HWVER_TYPE_PI2                       2
#define RPI_HWVER_TYPE_PI4                       3
    uint32_t hwver;
    uint32_t periph_base;
    uint32_t videocore_base;
    char *desc;
} rpi_hw_t;
     */





/*typedef struct spiApi_device
{
    int driver_mode;
    volatile uint8_t *pxl_raw;
    volatile dma_t *dma;
    volatile pwm_t *pwm;
    volatile pcm_t *pcm;
    int spi_fd;
    volatile dma_cb_t *dma_cb;
    uint32_t dma_cb_addr;
    volatile gpio_t *gpio;
    volatile cm_clk_t *cm_clk;
    videocore_mbox_t mbox;
    int max_count;
} spiApi_device_t; */


    // pointer
    @FieldOrder({"spi_mode","spi_FD"})
    public static class spiApi_device_p_old extends Structure{
        public int spi_mode;
        public int spi_FD;
      //  public Pointer p;


          /*  public spiApi_device_p() {
                super();
                // handle fixed-size array fields correctly
                ensureAllocated();
            }*/
    }





    }
