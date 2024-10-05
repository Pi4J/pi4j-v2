

Creation
Application code:
SPIapiImpl functionsV1 = SPIapiIntrf.createSPIapiImpl();

                   return(SPIapiImpl.newInstance("libpi4j-kernel.so", "pi4j-kernel"));
                                 SPIapiImpl newInstance called with lib and so
                                     impl created   
                                          impl calls its initialize( uses name .so and library name ) 
                                                   The initialize sets functionsNative reference to 
                                                     the SO
Next create and initialize the spiApi_t structure.
   Call functionsV1.spiApi_init     opens SPI path and sets some SPI details.


Operation.
functionsV1.spiApi_write(SPIapi.spiApi_t spiApi, byte[] b, int offset, int len, int speed);
     In the Impl method. it uses the contained NativeFunc pointer to all the C code
      C linkage uses JNA to pass control to C library, upon completion returns to java test case





                                                
