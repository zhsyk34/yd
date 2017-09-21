#!/bin/sh
SAVE_PATH=/home/yd/Workspace/eStore
FILE_NAME=supermarket.jar
FTP_SERVER=ftp://47.94.252.28/eStore

#setting version var
VERSION=$1
#download supermarket.jar with ftp
if [ -z ${VERSION+x} ];then
    echo "版本号未设置";
else
    echo "当前请求版本号:'${VERSION}',开始下载...";
    wget -N --no-passive-ftp -P ${SAVE_PATH} ${FTP_SERVER}/${VERSION}/${FILE_NAME}
fi