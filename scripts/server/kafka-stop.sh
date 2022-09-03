#!/bin/bash
JAVA_HOME=/usr/local/jdk
KAFKA_HOME=/usr/local/kafka
export JAVA_HOME
export KAFKA_HOME

$KAFKA_HOME/bin/kafka-server-stop.sh