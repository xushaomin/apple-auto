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

PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The application $SERVER_NAME does not started!"
else
  	if [ "$1" != "skip" ]; then
	    $BIN_DIR/dump.sh
	fi
	
	echo -e "Stopping the application $SERVER_NAME ...\c"
	for PID in $PIDS ; do
	    kill $PID > /dev/null 2>&1
	done
	
	COUNT=0
	while [ $COUNT -lt 1 ]; do    
	    echo -e ".\c"
	    sleep 1
	    COUNT=1
	    for PID in $PIDS ; do
	        PID_EXIST=`ps -f -p $PID | grep java`
	        if [ -n "$PID_EXIST" ]; then
	            COUNT=0
	            break
	        fi
	    done
	done
	echo "OK!"
fi


SERVER_COUNT=`storm list | grep $SERVER_NAME | wc -l`
if [ $SERVER_COUNT -lt 1 ]; then
    echo "ERROR: The storm worker $SERVER_NAME does not started!"
    exit 1
fi

echo -e "Stopping the storm worker $SERVER_NAME ...\c"

storm kill $SERVER_NAME > /dev/null 2>&1

SERVER_COUNT=1
while [ $SERVER_COUNT -gt 0 ]; do    
    echo -e ".\c"
    sleep 1
	SERVER_COUNT=`storm list | grep $SERVER_NAME | wc -l`
	if [ $SERVER_COUNT -lt 1 ]; then
        break
    fi
done

echo "OK!"