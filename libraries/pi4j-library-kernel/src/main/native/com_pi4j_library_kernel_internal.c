
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
* FILENAME      :  com_pi4j_library_kernel_internal.c
*
* This file is part of the Pi4J project. More information about
* this project can be found here:  https://pi4j.com/
* **********************************************************************
* %%
*/


#include <stddef.h>
#include <stdint.h>

#include <linux/types.h>

#include <linux/spi/spidev.h>
#include <string.h>
#include <sys/ioctl.h>




#include <stdio.h>


#include <stdlib.h>



#include <unistd.h>
#include <sys/stat.h>

#include <fcntl.h>
#include <linux/spi/spi.h>

#include "com_pi4j_library_kernel_internal.h"

#include <stdlib.h>



#define OSC_FREQ                                 19200000   // crystal frequency



/**
 * Cleanup previously allocated device memory and buffers.
 *
 * @param    com_pi4j_library_kernel_internal  com_pi4j_library_kernel_internal instance pointer.
 *
 * @returns  None
 */
void spiApi_cleanup(spiApi_t *spiApi)
{
  fprintf(stderr, "Enter spiApi_cleanup");
    spiApi_device_t *device = spiApi->device;


    if (device && (spiApi->spi_fd > 0))
    {
        close(spiApi->spi_fd);
    }

    if (device) {
        free(device);
    }
    spiApi->device = NULL;
}





int spi_transfer(spiApi_t *spiApi)
{
    int ret = spiApi_SUCCESS;
    struct spi_ioc_transfer tr;

    memset(&tr, 0, sizeof(struct spi_ioc_transfer));
    tr.tx_buf = 0;  // todo get buffer from spiApi (unsigned long)spiApi->device->pxl_raw;
    tr.rx_buf = 0;
    tr.len = 0;  // todo get buffer length from spiApi     >max_count, spiApi->freq);

    ret = ioctl(spiApi->spi_fd, SPI_IOC_MESSAGE(1), &tr);
    if (ret < 1)
    {
        fprintf(stderr, "Can't send spi message");
        return spiApi_ERROR_SPI_TRANSFER;
    }

    return ret;
}


/*
 *
 * Application API Functions
 *
 */

static int spi_init(spiApi_t *spiApi)
{
   fprintf(stderr, "Enter spi_init  \n");

  int spi_fd;
    static uint8_t mode;
    static uint8_t bits = 8;
    uint32_t speed = spiApi->max_freq;
    spiApi_device_t *device = spiApi->device;


fprintf(stderr, "spiApi \n");
    unsigned char  *p = (unsigned char *)spiApi;
    int i;
    for (i = 0; i < 32 ; i++)
        fprintf(stderr, "%02x ", p[i]);

    fprintf(stderr, "\n device %02x \n",device);
    p = (unsigned char *)device;
    for (i = 0; i < 32 ; i++)
        fprintf(stderr, "%02x ", p[i]);
    fprintf(stderr, " \n");


   // use bus and chip select supplied by user via the provider

    char  spiOpen[30];
    int rc =  sprintf(spiOpen,"/dev/spidev%d.%d",spiApi->bus, spiApi->cs);
    if (rc < 0) {
        fprintf(stderr, "Cannot create required string  /dev/spidev?.? \n");
        return spiApi_ERROR_SPI_SETUP;
    }
       spi_fd = open(spiOpen, O_RDWR);
    if (spi_fd < 0) {
        fprintf(stderr, "Cannot open  /dev/spidev%d.%d     Review config.txt update\n",  spiApi->bus, spiApi->cs);
        return spiApi_ERROR_SPI_SETUP;
    }

    fprintf(stderr, "C spiApi->max_freq    %d\n", spiApi->max_freq);
    fprintf(stderr, "C device->spi_fd    %d\n", spiApi->spi_fd);
    fprintf(stderr, "C device->driver_mode    %d\n", device->driver_mode);
    fprintf(stderr, "C spiApi->device->driver_mode    %d\n", spiApi->device->driver_mode);



    spiApi->spi_fd = spi_fd;
    mode = device->driver_mode;

    fprintf(stderr, "Opened %s   FD %d \n " , spiOpen,  spi_fd);
    fprintf(stderr, "Opened /dev/%s  Device FD %d \n " ,spiOpen, spiApi->spi_fd);
     // SPI mode
    if (ioctl(spi_fd, SPI_IOC_WR_MODE, &mode) < 0)
    {
        return spiApi_ERROR_SPI_SETUP;
    }
    if (ioctl(spi_fd, SPI_IOC_RD_MODE, &mode) < 0)
    {
        return spiApi_ERROR_SPI_SETUP;
    }

    // Bits per word
    if (ioctl(spi_fd, SPI_IOC_WR_BITS_PER_WORD, &bits) < 0)
    {
        return spiApi_ERROR_SPI_SETUP;
    }
    if (ioctl(spi_fd, SPI_IOC_RD_BITS_PER_WORD, &bits) < 0)
    {
        return spiApi_ERROR_SPI_SETUP;
    }

    // Max speed Hz
    if (ioctl(spi_fd, SPI_IOC_WR_MAX_SPEED_HZ, &speed) < 0)
    {
        return spiApi_ERROR_SPI_SETUP;
    }
    if (ioctl(spi_fd, SPI_IOC_RD_MAX_SPEED_HZ, &speed) < 0)
    {
        return spiApi_ERROR_SPI_SETUP;
    }

    return spiApi_SUCCESS;
}

/**
 * Shut down and cleanup memory.
 *
 * @param    spiApi  spiApi instance pointer.
 *
 * @returns  None
 */
void spiApi_fini(spiApi_t *spiApi)
{
 fprintf(stderr, "Enter spiApi_fini");

      spiApi_cleanup(spiApi);
}

/**
 * Allocate and initialize memory, buffers, pages,.
 *
 * @param    spiApi  spiApi instance pointer.
 *
 * @returns  0 on success, -1 otherwise.
 */
int spiApi_init(spiApi_t *spiApi)
{
    spiApi_device_t *device = spiApi->device;

    fprintf(stderr," spiApi_init  \n");

  fprintf(stderr, "spiApi \n");
    unsigned char  *p = (unsigned char *)spiApi;
    int i;
    for (i = 0; i < 32 ; i++)
        fprintf(stderr, "%02x ", p[i]);

    fprintf(stderr, "\n device %02x \n", device);
    p = (unsigned char *)device;
    for (i = 0; i < 32 ; i++)
        fprintf(stderr, "%02x ", p[i]);
    fprintf(stderr, " \n");

 fprintf(stderr, "\n device driver mode : %02x \n", spiApi->device->driver_mode);

 fprintf(stderr, "\n");

    int ret = spiApi_SUCCESS;

 // Use the structure allocated in the Java code
   // spiApi->device = malloc(sizeof(*spiApi->device));
    if (!spiApi->device)
    {
        return spiApi_ERROR_OUT_OF_MEMORY;
    }
   // memset(spiApi->device, 0, sizeof(*spiApi->device));
    device = spiApi->device;

     fprintf(stderr," B4 spiInit -> Driver Mode ???  %d \n", device->driver_mode);


        fprintf(stderr," yes Driver in SPI Mode    \n");
        ret =  spi_init(spiApi);


    return ret;
}

 int spiApi_write_b( spiApi_t * spi, char b, int speed){
 int ret = spiApi_SUCCESS;
 return ret;
 }


    int spiApi_read_b( spiApi_t * spi, char b, int speed){
    int ret = spiApi_SUCCESS;
    return ret;
    }

    int spiApi_read( spiApi_t * spi,const void * buffer, int offset, int length, int speed){
    int ret = spiApi_SUCCESS;
    return ret;
    }


    int transfer( spiApi_t * spi,const void * write, int writeOffset,const void * read, int readOffset, int numberOfBytes, int speed){
    int ret = spiApi_SUCCESS;
    return ret;
    }


int spiApi_write( spiApi_t * spi, const void * buf,int offset, int len, int speed){
 fprintf(stderr, ">>> entered spiApi_write \n");

  fprintf(stderr, "\n Write buffer data len %d,  speed %d,   pointer %02x \n", len,  speed,  buf);

  int ret = spiApi_SUCCESS;
  uint8_t spiBufTx[len];
  uint8_t spiBufRx[len];

  unsigned char  * p = (unsigned char *)buf;
  for(int i = 0; i < len;i++){
    spiBufTx[(i)] = p[i+offset];
  }
    for (int i = 0; i < len ; i++)
        fprintf(stderr, "%02x ", p[i]);
    fprintf(stderr, " \n");


   struct spi_ioc_transfer tr;
     memset (&tr, 0, sizeof(tr));
    tr.tx_buf = (unsigned long)&spiBufTx;
    tr.rx_buf = (unsigned long)&spiBufRx;
    tr.len = len;
    tr.speed_hz = speed;
    tr.delay_usecs = 0;
      tr.bits_per_word = 8;

      ret = ioctl(spi->spi_fd, SPI_IOC_MESSAGE(1), &tr);
 // int spi_write(struct spi_device * spi, const void * buf, int len, )
  if (ret < 1)
     {
         fprintf(stderr, "Can't send spi message");
         return spiApi_ERROR_SPI_TRANSFER;
     }
      return ret;
}



int spiApi_render_test(spiApi_test_t *spiApitest){
  int ret = spiApi_SUCCESS;
  int what = spiApitest->waldo;

 fprintf(stderr, "spiApi_test_t \n");
    unsigned char  *p = (unsigned char *)spiApitest;
    int i;
    for (i = 0; i < 32 ; i++)
        fprintf(stderr, "%02x ", p[i]);
 fprintf(stderr, "\n");

  fprintf(stderr, "Before spiApi_render_testtt ran  %d    \n", what);

  what = 21;
  spiApitest->waldo = what;
  what = spiApitest->waldo;
  fprintf(stderr, "After spiApi_render_testttt  %d   RC  %d \n", what, ret);

 spiApitest->where =  spiApitest->where *2;
return(ret);
}





