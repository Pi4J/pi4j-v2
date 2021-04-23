#!/bin/bash -e
###
# #%L
# **********************************************************************
# ORGANIZATION  :  Pi4J
# PROJECT       :  Pi4J :: JNI Native Binding Library for LinuxFS
# FILENAME      :  build-docker.sh
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
echo "**********************************************************************"
echo "*                                                                    *"
echo "*  COMPILE Pi4J NATIVE LIBRARIES USING Pi4J DOCKER BUILDER IMAGE     *"
echo "*                                                                    *"
echo "**********************************************************************"
echo "**********************************************************************"
echo

# validate compatible that "docker" exists
DOCKER=$(which docker)
if [[ ("$DOCKER" == "") ]]; then
    echo "This native build is requires that 'docker' (https://www.docker.com/) is "
    echo "installed and running on an Intel/AMD or ARM 64-bit platform."
    echo "BUILD ABORTED; REASON: Missing 'docker' executable."
    exit
fi

# set executable permissions on build scripts
chmod +x build.sh

# -------------------------------------------------------------
# BUILD NATIVE LIBRARIES USING THE Pi4J DOCKER BUILDER IMAGE
#   FOR ARMv6,ARMv7, ARMv8  32-BIT (ARMHF)
#   FOR ARMv8               64-BIT (ARM64)
# -------------------------------------------------------------
docker pull pi4j/pi4j-builder-native:2.0
docker run --user "$(id -u):$(id -g)" --rm --volume $(pwd):/build pi4j/pi4j-builder-native:2.0 $@
