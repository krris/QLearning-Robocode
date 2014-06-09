#!/bin/sh
# Script to run battles faster from terminal.

pwd=`pwd`

./battleConfigurator.sh
cd ..

robocode=~/robo/robocode-1.9.2.1/
results=log/results.txt
battle=battles/generated.battle
log4jProperties=src/main/java/io/github/krris/qlearning/log4j.properties

args="-nodisplay -results $results -battle $battle"
jvmArgs="-Xmx1024m -Ddebug=true -DNOSECURITY=true -DWORKINGDIRECTORY=$robocode -Dlog4j.configuration=file:$log4jProperties"

java $jvmArgs -cp "libs/robocode.jar:libdeps/*" robocode.Robocode $* $args
cd /home/krris/programowanie/idea-robot/out/production/QLearning-Robocode
cd "${pwd}"
./printChart.sh
