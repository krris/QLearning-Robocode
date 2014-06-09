#!/bin/sh
# Configuration file

# Direcotry where Robocode is installed
robocodePath='/home/krris/robo/robocode-1.9.2.1'

pwd=`pwd`
cd ..
rootFolder=`pwd`
cd ${pwd}

# Project root folder
projectSourcePath="${rootFolder}"
# Place where the compiled project is located
compiledProjectPath="${rootFolder}/out"
