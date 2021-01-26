#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Binding Library for PIGPIO
# FILENAME      :  build-libpi4j.sh
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

# ----------------------------------------------
# build latest Pi4J PiGPIO JNI Wrapper Library
# ----------------------------------------------

echo
echo "============================================================================="
echo " STARTED BUILDING Pi4J-PIGPIO JNI NATIVE LIBRARY: ('${ARCH}/libpi4j-pigpio')"
echo "============================================================================="
echo " - FOR ARCHITECTURE   : ${ARCH}"
echo " - USING COMPILER     : ${CC}"
echo " - USING CROSS PREFIX : ${CROSS_PREFIX}"
echo "-----------------------------------------------------------------------------"
echo

# ------------------------------------------------------
# BUILD LIBPIGPIO DEPENDENCY LIBRARY
# ------------------------------------------------------

# determine if the pigpio library has already been cloned and compiled on this system
if [[ -d "pigpio" ]] && [[ -f "lib/$ARCH/libpigpio.so" ]] ; then
    echo "The 'pigpio' library already exists; if you wish to rebuild, run a CLEAN build."
else
    ./build-libpigpio.sh $@
fi

# ------------------------------------------------------
# BUILD LIBPI4J-PIGPIO
# ------------------------------------------------------
echo
echo "====================================="
echo "BUILDING: ${ARCH}/libpi4j-pigpio"
echo "====================================="

# perform compile
make clean all \
  --always-make \
  CROSS_PREFIX=${CROSS_PREFIX} \
  CC=${CC} \
  ARCH=${ARCH} \
  TARGET=lib/${ARCH}/libpi4j-pigpio.so $@

echo
echo "-----------------------------------------------------------------------------"
echo " FINISHED BUILDING Pi4J-PIGPIO JNI NATIVE LIBRARY: ('${ARCH}/libpi4j-pigpio')"
echo "-----------------------------------------------------------------------------"
echo