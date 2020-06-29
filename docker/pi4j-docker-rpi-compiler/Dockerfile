FROM ubuntu:18.04

MAINTAINER Robert Savage "robert@pi4j.com"

LABEL com.pi4j.docker.compiler.raspberrypi="{\"Description\":\"Pi4J Cross Compiler Container for Raspberry Pi\",\"Usage\":\"docker run -it -v ~/mybuild:/build pi4j/raspberrypi-compiler\",\"Version\":\"1.0.0\"}"

# make sure ubuntu is up to date and install required GIT binaries
RUN apt-get update -qq && \
    apt-get install -qq -y git

# get the latest RPI cross compiler tools from the GitHub repo
RUN git clone --progress --verbose https://github.com/raspberrypi/tools.git --depth=1 /rpi-tools

# install additional build tools and utilities as well as the OpenJDK
RUN apt-get install -qq -y build-essential && \
    apt-get install -qq -y tree && \
    apt-get install -qq -y nano && \
    apt install -qq -y openjdk-11-jdk

# configure the require environment variables for cross-compiling
ENV PATH="${PATH}:/rpi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin"
ENV ARCH="arm"
ENV CROSS_PREFIX="/rpi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/arm-linux-gnueabihf-"
ENV CCPREFIX="${CROSS_PREFIX}"
#ENV CC="arm-linux-gnueabihf-gcc"
ENV RPI_CROSS_COMPILE="true"
ENV JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64/"
ENV BUILD_FOLDER /build
ENV BUILD_TARGET="all"

# set the working path to the build mounted volume
WORKDIR ${BUILD_FOLDER}

CMD ["/bin/bash", "-c", "make clean ${BUILD_TARGET} --always-make -f ${BUILD_FOLDER}/Makefile CROSS_PREFIX=\"${CROSS_PREFIX}\""]
