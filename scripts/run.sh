#!/bin/bash
# Script to run Robocode battles (with LearningRobot) from terminal.

# Get config variables
source config.sh

# Configure 'generated.battle' file
pwd=`pwd`
./battleConfigurator.sh
cd ..

# Directory where robocode is installed
robocode=~/robo/robocode-1.9.2.1/
results=log/results.txt
battle=battles/generated.battle
log4jProperties=src/main/java/io/github/krris/qlearning/log4j.properties

# Robocode arguments
args="-nodisplay -results $results -battle $battle"

# JVM arguments
jvmArgs="-Xmx1024m -Ddebug=true -DNOSECURITY=true -DWORKINGDIRECTORY=$robocode -Dlog4j.configuration=file:$log4jProperties"

java ${jvmArgs} -cp "${robocodePath}/libs/robocode.jar:libdeps/*" robocode.Robocode $* ${args}
# ATTENTION - change to place where the compiled project is located
cd ${compiledProjectPath}

cd "${pwd}"
./printChart.sh
