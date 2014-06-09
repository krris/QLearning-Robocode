#!/bin/bash
# Configures 'generated.battle' file which is based on 'application.conf'.

# Get config variables
source config.sh

pwd=`pwd`
cd ${compiledProjectPath}
java -cp ".:${projectSourcePath}/libdeps/*" io/github/krris/qlearning/util/BattleConfigurator
cd "${pwd}"
