#!/bin/bash

BASE_DIR=$(dirname "$(realpath "$BASH_SOURCE")")

COMPOSE_DIR=$BASE_DIR/infra/local
CONNECTOR_DIR=$BASE_DIR/infra/local/connectors
MOCKS_DIR=$BASE_DIR/infra/local/mocks
SERVICES_DIR=$BASE_DIR/services

# create services docker images
echo ''
echo 'Building recommendation-system MS docker image ...'
echo ''
cd $SERVICES_DIR/recommendation-system-ms
docker build -t recommendation-system-ms:latest . > /dev/null 2>&1
cd $BASE_DIR
echo ''
echo 'Building website docker image ...'
echo ''
cd $SERVICES_DIR/website
docker build -t angular-website . > /dev/null 2>&1
cd $BASE_DIR


# create infrastructure (zookeeper, brokers, schema registry, connect, control-center, prometheus, grafana, postgresql, recommendation-system-ms)
echo ''
echo 'Installing infra ...'
echo ''
docker compose -f $COMPOSE_DIR/docker-compose.yml up -d


# Installing postgres sink connector and restart components
echo ''
echo 'Installing connectors ...'
echo ''
cd $CONNECTOR_DIR
./install-connectors.sh
cd $BASE_DIR

# show useful URLs
echo ''
echo 'Infrastructure ready!'
echo ''
echo 'Useful URLs:'
echo ' - Control Center: http://localhost:9021'
echo ' - Grafana: http://localhost:3000'
echo ' - PostgresQL: '
echo '      host: localhost:5432'
echo '      user: admin'
echo '      pass: 4dm1n'
echo '      database: recommendation-system'

# Run user interactions mock to simulate web users' interactions with products
echo ''
#echo 'Running web user interactions mock (20 minutes, one interaction each 10 seconds) ...'
#echo ''
#cd $MOCKS_DIR
#./simulate-actions.sh
cd $BASE_DIR