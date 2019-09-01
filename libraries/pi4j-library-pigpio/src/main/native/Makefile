#
# Makefile:
#	pi4j-pigpio - Pi4J Java (JNI) library wrapper for PIGPIO
#


#DEBUG	     = -g -O0
DEBUG	     = -O3
CC           = $(CROSS_PREFIX)gcc
AR           = $(CROSS_PREFIX)ar
RANLIB       = $(CROSS_PREFIX)ranlib
SIZE         = $(CROSS_PREFIX)size
STRIP        = $(CROSS_PREFIX)strip
SHLIB        = $(CC) -shared
STRIPLIB     = $(STRIP) --strip-unneeded
INCLUDE	     = -I. -Ipigpio                                              \
               -I/$(JAVA_HOME)/include                                   \
               -I/$(JAVA_HOME)/include/linux                             \
               -I/usr/lib/jvm/jdk-11-oracle/include                      \
               -I/usr/lib/jvm/jdk-11-oracle/include/linux                \
               -I/usr/lib/jvm/jdk-11-oracle-arm32-vfp-hflt/include       \
               -I/usr/lib/jvm/jdk-11-oracle-arm32-vfp-hflt/include/linux \
               -I/usr/lib/jvm/java-11-openjdk-amd64/include              \
               -I/usr/lib/jvm/java-11-openjdk-amd64/include/linux        \
               -I/usr/lib/jvm/java-11-openjdk-armhf/include              \
               -I/usr/lib/jvm/java-11-openjdk-armhf/include/linux        \
               -I/usr/local/include -I/usr/local/include/linux

CFLAGS      := $(DEBUG) -Wall $(INCLUDE) -Winline -pipe $(CARGS) -fPIC
LIBS         =  -L pigpio -lpigpio -lrt

TARGET=libpi4j-pigpio.so
SOVERSION    = 1

# Should not alter anything below this line
###############################################################################

SRC	=   com_pi4j_library_pigpio_internal_PIGPIO.c

OBJ =	$(SRC:.c=.o)

all:	$(OBJ)
	    @echo [LINK with DYNAMICALLY linked libraries]
	    @$(CC) $(OBJ) -shared -o $(TARGET).$(SOVERSION) $(INCLUDE) $(LIBS)
	    ln -fs $(TARGET).$(SOVERSION) $(TARGET)

.c.o:
	@echo [COMPILE] $<
	@$(CC) -c $(CFLAGS) $< -o $@

clean:
	rm -f $(OBJ) $(TARGET).$(SOVERSION) *~ core tags Makefile.bak

tags:	$(SRC)
	@echo [ctags]
	@ctags $(SRC)

depend:
	makedepend -Y $(SRC)

install:	$(TARGET)
	@echo [install]
	install -m 0755 -d /usr/local/lib
	install -m 0755 -d /usr/local/include
	install -m 0644 $(TARGET)	/usr/local/lib

uninstall:
	@echo [uninstall]
	rm -f /usr/local/lib/$(TARGET)

# DO NOT DELETE
com_pi4j_library_pigpio_internal_PIGPIO.o: com_pi4j_library_pigpio_internal_PIGPIO.h
