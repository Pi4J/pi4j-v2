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

# ----------------------------------
# build latest Pi4J JNI Library
# ----------------------------------
echo
echo "============================"
echo "Pi4J JNI Build script"
echo "============================"
echo
echo "Compiling 'pi4j-pigpio' JNI library"

if [[ -n "$RPI_DOCKER_COMPILE" ]]; then
  docker run -v $(PWD):/build \
  raspberrypi-compiler make clean all --always-make CROSS_PREFIX="/rpi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/arm-linux-gnueabihf-"
elif [[ -n "$RPI_CROSS_COMPILE" ]]; then
  make clean all --always-make CROSS_PREFIX="/rpi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/arm-linux-gnueabihf-"
else
  make clean all --always-make
fi
