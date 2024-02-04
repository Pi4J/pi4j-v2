#
# Makefile:
#	pi4j-gpiod - Pi4J Java (JNI) library wrapper for gpiod
#

#DEBUG	     = -g -O0
ARCH        := armhf
DEBUG	       = -O3
CC           = $(CROSS_PREFIX)gcc
AR           = $(CROSS_PREFIX)ar
RANLIB       = $(CROSS_PREFIX)ranlib
SIZE         = $(CROSS_PREFIX)size
STRIP        = $(CROSS_PREFIX)strip
SHLIB        = $(CC) -shared
STRIPLIB     = $(STRIP) --strip-unneeded
INCLUDE	     = -I. -Igpiod                                               \
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
               -I/usr/include

CFLAGS      := $(DEBUG) -Wall $(INCLUDE) -Winline -pipe $(CARGS) -fPIC
LIBS         =  -L lib/$(ARCH)/pi4j-gpiod -L gpiod -lgpiod -lrt

TARGET=libpi4j-gpiod.so

# Should not alter anything below this line
###############################################################################

SRC	=   com_pi4j_library_gpiod_internal_GpioD.c

OBJ =	$(SRC:.c=.o)

all:	$(OBJ)
	    @echo [LINK with DYNAMICALLY linked libraries]
	    @$(CC) $(OBJ) -shared -o $(TARGET) $(INCLUDE) $(LIBS)

.c.o:
	@echo [COMPILE] $<
	@$(CC) -c $(CFLAGS) $< -o $@

clean:
	rm -f $(OBJ) $(TARGET) *~ core tags Makefile.bak

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
com_pi4j_library_gpiod_internal_GpioD.o: com_pi4j_library_gpiod_internal_GpioD.h
