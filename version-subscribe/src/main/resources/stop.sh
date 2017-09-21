#!/bin/sh
#shutdown supermarket.jar
ps -aux | grep -e -jar.*supermarket\.jar$ | grep -v grep | awk '{print $2}' | xargs kill
