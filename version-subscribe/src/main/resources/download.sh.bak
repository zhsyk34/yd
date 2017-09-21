#!/bin/sh
HOST='192.168.1.101'
USER='ftpuser'
PASSWORD='ftpuser'
FILE='/home/ftp/log.sh'
ftp -n ${HOST} <<END_SCRIPT
quote USER ${USER}
quote PASS ${PASSWORD}
put ${FILE}
quit
END_SCRIPT
exit 0