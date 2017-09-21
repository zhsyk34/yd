#!/bin/sh
DIR=/home/yd/JavaRun/log-server
cd ${DIR}
java -jar log-server.jar 1873 logback.xml &
