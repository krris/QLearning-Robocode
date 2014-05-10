#!/bin/sh
# Script to run battles faster from terminal.

pwd=`pwd`

robocode=~/robo/robocode-obstacle/
results=~/log/results.txt
battle=battles/test.battle
log4jProperties=/src/main/java/io/github/krris/qlearning/log4j.properties

args="-nodisplay -results $results -battle $battle"
jvmArgs="-Xmx1024m -Ddebug=true -DNOSECURITY=true -Dsun.io.useCanonCaches=false -Dlog4j.configuration=$log4jProperties"

#cd $robocode
cd ..
java $jvmArgs -cp "libs/robocode.jar:libdeps/*" robocode.Robocode $*  $args
cd "${pwd}"
