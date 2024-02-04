#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Binding Library for GPIOD
# FILENAME      :  build-libpigpio.sh
#
# This file is part of the Pi4J project. More information about
# this project can be found here:  https://pi4j.com/
# **********************************************************************
# %%
# Copyright (C) 2012 - 2021 Pi4J
# %%
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# #L%
###

# -- RASPBERRY PI --
# set default GPIOD repository URL if not already defined
if [ -z $GPIOD_REPO ]; then
    GPIOD_REPO=https://git.kernel.org/pub/scm/libs/libgpiod/libgpiod.git
fi
# set default GPIOD repository branch if not already defined
if [ -z $GPIOD_BRANCH ]; then
	GPIOD_BRANCH=v1.6.x
fi
# set default GPIOD directory if not already defined
if [ -z $GPIOD_DIRECTORY ]; then
	GPIOD_DIRECTORY=gpiod
fi

echo "====================================="
echo "BUILDING: ${ARCH}/libgpiod"
echo "====================================="
echo "REPOSITORY       : $GPIOD_REPO"
echo "BRANCH           : $GPIOD_BRANCH"
echo "DIRECTORY        : $GPIOD_DIRECTORY"
echo "HOST ARCHITECTURE: ${HOST}"

# ----------------------------------
# clone GPIOD from github
# ----------------------------------
rm -rf $GPIOD_DIRECTORY
git -c http.sslVerify=false clone $GPIOD_REPO -b $GPIOD_BRANCH $GPIOD_DIRECTORY --single-branch --depth 1

#rm -f pigpio.tar
#wget abyz.me.uk/rpi/pigpio/pigpio.tar
#tar xf pigpio.tar

# ----------------------------------
# build latest GPIOD
# ----------------------------------
cd $GPIOD_DIRECTORY

./autogen.sh --enable-tools=no --prefix=$(pwd)/install --host=${HOST}

make clean all install \
  CROSS_PREFIX=${CROSS_PREFIX} \
  CC=$CC \
  ARCH=$ARCH

#echo "Copying GPIOD library files to parent 'lib' folder"
mkdir -p ../lib/${ARCH}/pi4j-gpiod
cp install/lib/libgpiod.so ../lib/${ARCH}/pi4j-gpiod/libgpiod.so
