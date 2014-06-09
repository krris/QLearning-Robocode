#!/bin/bash
# Prints chart with rewards per round

# Get config variables
source config.sh

pwd=`pwd`
cd ${compiledProjectPath}

java -cp ".:${projectSourcePath}/libdeps/*" io/github/krris/qlearning/chart/Chart
cd "${pwd}"
