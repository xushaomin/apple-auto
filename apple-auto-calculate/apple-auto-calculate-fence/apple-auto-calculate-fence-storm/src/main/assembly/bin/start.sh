#!/bin/bash
ENV=$1
if [ -z "$ENV" ]; then
    echo "ERROR:PLEASE SPEC ENV ARGS,SUCH AS dev,test,beta,release..  "
    echo "app exit"
    exit 1
fi
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

SERVER_NAME=`sed '/application.name/!d;s/.*=//' conf/system.properties | tr -d '\r'`
LOGS_FILE=`sed '/log4j.appender.logToFile.File/!d;s/.*=//' conf/log4j.properties | tr -d '\r'`

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi

PIDS=`ps -f | grep java | grep "$CONF_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

SERVER_COUNT=`storm list | grep  $SERVER_NAME | wc -l`
if [ $SERVER_COUNT -gt 0 ]; then
    echo "ERROR: The $SERVER_NAME already running!"
    exit 1
fi

LOGS_DIR=""
if [ -n "$LOGS_FILE" ]; then
    LOGS_DIR=`dirname $LOGS_FILE`
else
    LOGS_DIR=$DEPLOY_DIR/logs
fi
if [ ! -d $LOGS_DIR ]; then
    mkdir -p $LOGS_DIR
fi
STDOUT_FILE=$LOGS_DIR/stdout.log

LIB_DIR=$DEPLOY_DIR/lib
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'`

echo -e "Starting the $SERVER_NAME ...\c"
nohup storm jar $LIB_JARS com.appleframework.boot.Main env=$ENV name=$SERVER_NAME config-factory=com.appleframework.config.PropertyConfigurerFactory java-container=com.appleframework.auto.fence.calculate.KafkaTopology > $STDOUT_FILE 2>&1 &
COUNT=0
while [ $COUNT -lt 1 ]; do    
    echo -e ".\c"
    sleep 1 
    COUNT=`storm list | grep  $SERVER_NAME | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
echo "STDOUT: $STDOUT_FILE"