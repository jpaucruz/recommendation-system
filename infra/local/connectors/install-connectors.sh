#!/bin/bash

CONTAINER_NAME="connect"
CONNECTOR_ZIP_PATH="./confluentinc-kafka-connect-jdbc-10.8.0.zip"
KAFKA_CONNECT_DIR="/usr/share/java"
CONNECTOR_NAME="jdbc-sink"
CONNECTOR_CONFIG_FILE="connector-config.json"

echo '  - Unzipping the JDBC connector ...'
unzip -qo "$CONNECTOR_ZIP_PATH" -d /tmp/kafka-connect-jdbc > /dev/null
echo ''

echo '  - Copying connector JAR files to Kafka Connect container ...'
docker cp /tmp/kafka-connect-jdbc/* "$CONTAINER_NAME:$KAFKA_CONNECT_DIR" > /dev/null
echo ''

echo '  - Restarting services (connect and control center) ...'
docker restart "$CONTAINER_NAME" > /dev/null
sleep 60
docker restart control-center > /dev/null
sleep 60
echo ''

echo '  - Checking that the JDBC connector is available ...'
curl -X GET http://localhost:8083/connector-plugins > /dev/null 2>&1 | grep "io.confluent.connect.jdbc.JdbcSinkConnector" > /dev/null 2>&1
echo ''

echo '  - Creating a configuration file for the JDBC connector ...'
cat > "$CONNECTOR_CONFIG_FILE" <<EOL
{
  "name": "$CONNECTOR_NAME",
  "config": {
    "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
    "tasks.max": "1",
    "connection.url": "jdbc:postgresql://postgres:5432/recommendation-system",
    "connection.user": "admin",
    "connection.password": "4dm1n",
    "topics": "user_recommendations",
    "table.name.format": "recommendations",
    "key.converter.schemas.enable": "false",
    "value.converter.schemas.enable": "true",

    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url": "http://schema-registry:8081",

    "auto.create": "false",
    "auto.evolve": "true",
    "insert.mode": "upsert",

    "pk.mode": "record_value",
    "pk.fields": "id"
  }
}
EOL
echo ''

echo '  - Loading the JDBC connector in Kafka Connect ...'
curl -X POST -H "Content-Type: application/json" --data @$CONNECTOR_CONFIG_FILE http://localhost:8083/connectors > /dev/null 2>&1
sleep 15
echo ''

echo '  - Checking connector state ...'
curl -X GET http://localhost:8083/connectors/$CONNECTOR_NAME/status > /dev/null 2>&1
echo ''
