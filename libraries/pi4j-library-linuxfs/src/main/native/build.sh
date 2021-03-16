#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Binding Library for LinuxFS
# FILENAME      :  build.sh
#
# This file is part of the Pi4J project. More information about
# this project can be found here:  https://pi4j.com/
# **********************************************************************
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

echo
echo "**********************************************************************"
echo "*                                                                    *"
echo "*            Pi4J LinuxFS LIBRARY NATIVE BUILD <STARTED>              *"
echo "*                                                                    *"
echo "**********************************************************************"
echo

# ------------------------------------------------------
# VALIDATE BUILD PLATFORM; PRECONDITIONS
# ------------------------------------------------------

# validate compatible OS/Kernel
KERNEL=$(uname -s)
if [[ ("$KERNEL" != "Linux") ]]; then
    echo "This native build is only supported on Linux-based systems running on an Intel/AMD or ARM 64-bit platform."
    echo "BUILD ABORTED; REASON: KERNEL='$KERNEL'; EXPECTED='Linux'"
    echo "(NOTE: You can run this build using the Pi4J Docker Builder images from OSX, Windows, Linux.)"
    exit 1
fi

# validate compatible CPU architecture
ARCHITECTURE=$(uname -m)
if [[ (("$ARCHITECTURE" != "aarch64") && ("$ARCHITECTURE" != "amd64") && ("$ARCHITECTURE" != "x86_64")) ]]; then
    echo "This native build is only supported on Linux-based systems running on an Intel/AMD or ARM 64-bit platform."
    echo "BUILD ABORTED; REASON: ARCHITECTURE='$ARCHITECTURE'; EXPECTED='aarch64|amd64|x86_64'"
    exit 1
fi

# ------------------------------------------------------
# ENSURE DEPENDENCY SCRIPTS ARE EXECUTABLE
# ------------------------------------------------------
# set executable permissions on build scripts
chmod +x build-prerequisites.sh
chmod +x build-liblinuxfs.sh

# ------------------------------------------------------
# INSTALL BUILD PREREQUISITES
# ------------------------------------------------------
echo
echo "====================================="
echo " INSTALLING Pi4J BUILD PREREQUISITES "
echo "====================================="
echo

# if running inside a Pi4J Docker Builder image, then there is no need to install prerequisites
if [[ "${PI4J_BUILDER}" != "" ]]; then
   echo "Running inside a Pi4J Docker Builder image; [version=${PI4J_BUILDER}; arch=${PI4J_BUILDER_ARCH}]"
   echo "No need to check or install build environment prerequisites."
else
   # if this is a Linux-based system and a 64-bit Intel/AMD or ARM platform, then we can install the prerequisites
   # download and install development prerequisites
   ./build-prerequisites.sh
fi

# ------------------------------------------------------
# JAVA_HOME ENVIRONMENT VARIABLE
# ------------------------------------------------------
echo
echo "========================================="
echo " CHECKING JAVA_HOME ENVIRONMENT VARIABLE "
echo "========================================="
echo
if [[ -n "$JAVA_HOME" ]]; then
   echo "'JAVA_HOME' already defined as: $JAVA_HOME";
else
   export JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:bin/javac::")
   echo "'JAVA_HOME' was not defined; attempting to use: $JAVA_HOME";
fi

# ------------------------------------------------------
# BUILD NATIVE LIBRARIES FOR ARMv6,ARMv7,ARMv8 32-BIT (ARMHF)
# USING THE LOCALLY INSTALLED ARM CROSS-COMPILER
# ------------------------------------------------------
export CROSS_PREFIX=arm-linux-gnueabihf-
export CC=arm-linux-gnueabihf-gcc
export ARCH=armhf
./build-liblinuxfs.sh $@

# ------------------------------------------------------
# BUILD NATIVE LIBRARIES FOR ARMv8 64-BIT (ARM64)
# USING THE LOCALLY INSTALLED ARM64 CROSS-COMPILER
# ------------------------------------------------------
export CROSS_PREFIX=aarch64-linux-gnu-
export CC=aarch64-linux-gnu-gcc
export ARCH=aarch64
./build-liblinuxfs.sh $@

echo "======================================"
echo " Pi4J LinuxFS LIBRARY NATIVE ARTIFACTS "
echo "======================================"
tree -R lib

echo
echo "**********************************************************************"
echo "*                                                                    *"
echo "*           Pi4J LinuxFS LIBRARY NATIVE BUILD <FINISHED>              *"
echo "*                                                                    *"
echo "**********************************************************************"
echo