#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Binding Library for PIGPIO
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
# set default PIGPIO repository URL if not already defined
if [ -z $PIGPIO_REPO ]; then
    PIGPIO_REPO=https://github.com/joan2937/pigpio
fi
# set default PIGPIO repository branch if not already defined
if [ -z $PIGPIO_BRANCH ]; then
	PIGPIO_BRANCH=V71
fi
# set default PIGPIO directory if not already defined
if [ -z $PIGPIO_DIRECTORY ]; then
	PIGPIO_DIRECTORY=pigpio
fi

echo "====================================="
echo "BUILDING: ${ARCH}/libpigpio"
echo "====================================="
echo "REPOSITORY : $PIGPIO_REPO"
echo "BRANCH     : $PIGPIO_BRANCH"
echo "DIRECTORY  : $PIGPIO_DIRECTORY"

# ----------------------------------
# clone PIGPIO from github
# ----------------------------------
rm -rf $PIGPIO_DIRECTORY
git clone $PIGPIO_REPO -b $PIGPIO_BRANCH $PIGPIO_DIRECTORY --single-branch --depth 1

#rm -f pigpio.tar
#wget abyz.me.uk/rpi/pigpio/pigpio.tar
#tar xf pigpio.tar

# ----------------------------------
# build latest PIGPIO
# ----------------------------------
cd $PIGPIO_DIRECTORY
make clean lib --always-make \
  CROSS_PREFIX=${CROSS_PREFIX} \
  CC=$CC \
  ARCH=$ARCH

#echo "Copying PIGPIO library files to parent 'lib' folder"
mkdir -p ../lib/${ARCH}
cp libpigpio.so ../lib/${ARCH}/libpigpio.so
cp libpigpiod_if.so ../lib/${ARCH}/libpigpiod_if.so
cp libpigpiod_if2.so ../lib/${ARCH}/libpigpiod_if2.so
