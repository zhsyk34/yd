#!/bin/sh
#init-context
export JAVA_HOME=/soft/jdk
export PATH=$PATH:${JAVA_HOME}/bin:${JAVA_HOME}/jre/bin
#jar dir
DIR=/soft/eStore
cd ${DIR}
#start supermarket.jar
java -jar supermarket.jar &