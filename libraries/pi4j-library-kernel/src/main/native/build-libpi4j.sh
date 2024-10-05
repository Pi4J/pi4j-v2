#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNA Native Binding Library for kernel
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
# build latest Pi4J kernel JNA Wrapper Library
# ----------------------------------------------

echo
echo "============================================================================="
echo " STARTED BUILDING Pi4J-Kernel JNA NATIVE LIBRARY: ('${ARCH}/pi4j-kernel/libpi4j-kernel')"
echo "============================================================================="
echo " - FOR ARCHITECTURE   : ${ARCH}"
echo " - USING COMPILER     : ${CC}"
echo " - USING CROSS PREFIX : ${CROSS_PREFIX}"
echo "-----------------------------------------------------------------------------"
echo


# set default kernel directory if not already defined
if [ -z $KERNEL_DIRECTORY ]; then
	KERNEL_DIRECTORY=kernel
fi

# ------------------------------------------------------
# BUILD LIBPI4J-KERNEL
# ------------------------------------------------------
echo
echo "====================================="
echo "BUILDING: ${ARCH}/pi4j-kernel/libpi4j-kernel"
echo "====================================="

# perform compile
mkdir -p lib/${ARCH}/pi4j-kernel
make clean all \
  --always-make \
  CROSS_PREFIX=${CROSS_PREFIX} \
  CC=${CC} \
  ARCH=${ARCH} \
  TARGET=lib/${ARCH}/pi4j-kernel/libpi4j-kernel.so $@

echo
echo "-----------------------------------------------------------------------------"
echo " FINISHED BUILDING Pi4J-kernel JNA NATIVE LIBRARY: ('${ARCH}/libpi4j-kernel')"
echo "-----------------------------------------------------------------------------"
echo