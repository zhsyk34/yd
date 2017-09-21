#!/bin/sh
DIR=/home/yd/scripts/log-server
PORT=1873
cd ${DIR}
java -jar log-server.jar ${PORT} logback.xml &
