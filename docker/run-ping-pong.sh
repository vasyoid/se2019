#!/bin/bash

sleep 10s && cd /tmp/sqs-ping-pong && ./gradlew build && java -jar ./build/libs/sqs-ping-pong.jar $A $B $NUMBER
