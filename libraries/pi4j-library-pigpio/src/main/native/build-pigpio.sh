#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Binding Library for PIGPIO
# FILENAME      :  build.sh
#
# This file is part of the Pi4J project. More information about
# this project can be found here:  https://pi4j.com/
# **********************************************************************
# %%
# Copyright (C) 2012 - 2019 Pi4J
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


echo "============================"
echo "Building PIGPIO Library   "
echo "============================"
echo "REPOSITORY : $PIGPIO_REPO"
echo "BRANCH     : $PIGPIO_BRANCH"
echo "DIRECTORY  : $PIGPIO_DIRECTORY"

# ----------------------------------
# clone PIGPIO from github
# ----------------------------------
rm -rf $PIGPIO_DIRECTORY
git clone $PIGPIO_REPO -b $PIGPIO_BRANCH $PIGPIO_DIRECTORY

#rm -f pigpio.tar
#wget abyz.me.uk/rpi/pigpio/pigpio.tar
#tar xf pigpio.tar


# ----------------------------------
# build latest PIGPIO
# ----------------------------------
echo
echo "============================"
echo "PIGPIO Build script"
echo "============================"
echo
echo "Compiling PIGPIO library"

echo "-----------------------------------------------------------"
echo "Compiling 'libpigpio' library <STARTED>"
echo "PI4J NATIVE COMPILER: $PI4J_NATIVE_COMPILER"
echo "-----------------------------------------------------------"
cd $PIGPIO_DIRECTORY
if [[ "$PI4J_NATIVE_COMPILER" == "DOCKER-COMPILER" || "$PI4J_NATIVE_COMPILER" == "docker-compiler"  ]]; then
  docker run --env BUILD_TARGET=lib -v $(PWD):/build raspberrypi-compiler
elif [[ "$PI4J_NATIVE_COMPILER" == "CROSS-COMPILER" || "$PI4J_NATIVE_COMPILER" == "cross-compiler" ]]; then
  make clean lib --always-make CROSS_PREFIX=${CROSS_PREFIX}
else
  make clean lib --always-make
fi
echo "-----------------------------------------------------------"
echo "Compiling 'libpigpio' <FINISHED>"
echo "-----------------------------------------------------------"
