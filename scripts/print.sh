#!/bin/sh
pwd=`pwd`
cd /home/krris/programowanie/idea-robot/out/production/QLearning-Robocode
java -cp ".:/home/krris/programowanie/idea-robot/QLearning-Robocode/libdeps/*" io/github/krris/qlearning/chart/Chart
cd "${pwd}"
