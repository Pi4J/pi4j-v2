

/*   * Copyright (C) 2012 - 2024 Pi4J
 * %%
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*-
* #%L
* **********************************************************************
* ORGANIZATION  :  Pi4J
* PROJECT       :  Pi4J :: EXTENSION
* FILENAME      :  com_pi4j_library_kernel_internal.h
*
* This file is part of the Pi4J project. More information about
* this project can be found here:  https://pi4j.com/
* **********************************************************************
* %%
*/

#ifndef __com_pi4j_library_kernel_internal_H__
#define __com_pi4j_library_kernel_internal_H__

#ifdef __cplusplus
extern "C" {
#endif
#include <stddef.h>
#include <stdint.h>


#define spiApi_TARGET_FREQ                       800000   // Can go as low as 400000



typedef uint32_t spiApi_led_t;                   //< 0xWWRRGGBB




typedef struct spiApi_test  {
        int waldo;
        int where;
 }spiApi_test_t;


typedef struct spiApi_device
{
    int driver_mode;
    int CEx; // Chip enable low:0
    int CExResrv; // chip enable SPI1  Not used
    int AuxSPI; // Auxiliary SPI if 1, not used
    int wire; // 0:not 3 wire, 1 is 3 wire
    int bits; // bits before MISO begine, valid wire is 3
    int firstBitTX; // 1, LSB sent first, 0 LSB first
    int firstBitRX; // 1, LSB received first, 0 LSB first
    int wSize;  // 0 : 8 bit word. range 0-32
} spiApi_device_t;


typedef struct spiApi
{
     int max_freq;
     int spi_fd;
     int bus;
     int cs;
     struct spiApi_device *device;                //< Private data for driver use
 } spiApi_t;




            int spiApi_SUCCESS = 0;
            int spiApi_ERROR_GENERIC = -1;
            int spiApi_ERROR_OUT_OF_MEMORY = -2;
            int spiApi_ERROR_HW_NOT_SUPPORTED = -3;
            int spiApi_ERROR_MEM_LOCK = -4;
            int spiApi_ERROR_MMAP = -5;
            /*X(-6, spiApi_ERROR_MAP_REGISTERS, "Unable to map registers into userspace"),    \
            X(-7, spiApi_ERROR_GPIO_INIT, "Unable to initialize GPIO"),                     \
            X(-8, spiApi_ERROR_PWM_SETUP, "Unable to initialize PWM"),                      \
            X(-9, spiApi_ERROR_MAILBOX_DEVICE, "Failed to create mailbox device"),          \
            X(-10, spiApi_ERROR_DMA, "DMA error"),                                          \
            X(-11, spiApi_ERROR_ILLEGAL_GPIO, "Selected GPIO not possible"),                \
            X(-12, spiApi_ERROR_PCM_SETUP, "Unable to initialize PCM"),                     \
            */
            int spiApi_ERROR_SPI_SETUP = -13;
            int  spiApi_ERROR_SPI_TRANSFER = -14;


                                            //< Tear it all down
int spiApi_render_test(spiApi_test_t *spiApitest);




int spiApi_write(spiApi_t * spi, const void * buf, int offset, int len, int speed);
 int spiApi_write_b(spiApi_t * spi, char b, int speed);


    int spiApi_read_b(spiApi_t * spi, char b, int speed);

    int spiApi_read(spiApi_t * spi,const void *buffer, int offset, int length, int speed);


    int transfer(spiApi_t * spi, const void *write, int writeOffset,const void * read, int readOffset, int numberOfBytes, int speed);



 int spi_transfer(spiApi_t *spiApi);


int spiApi_init(spiApi_t *spiApi);                                  //< Initialize buffers/hardware
void spiApi_fini(spiApi_t *spiApi);                                             //< Tear it all down

#ifdef __cplusplus
}
#endif

#endif /* __com_pi4j_library_kernel_internal_H__ */
