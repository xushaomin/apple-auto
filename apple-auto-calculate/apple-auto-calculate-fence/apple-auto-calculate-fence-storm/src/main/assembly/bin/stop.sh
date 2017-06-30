#!/bin/bash
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

SERVER_NAME=`sed '/application.name/!d;s/.*=//' conf/system.properties | tr -d '\r'`

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

SERVER_COUNT=`storm list | grep $SERVER_NAME | wc -l`
if [ $SERVER_COUNT -lt 1 ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
    exit 1
fi

echo -e "Stopping the $SERVER_NAME ...\c"

storm kill $SERVER_NAME > /dev/null 2>&1

COUNT=0
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1
    COUNT=1
    PID_EXIST=`storm list | grep $SERVER_NAME`
    if [ -n "$PID_EXIST" ]; then
        COUNT=0
        break
    fi
done

echo "OK!"
echo "Worker: $SERVER_NAME"