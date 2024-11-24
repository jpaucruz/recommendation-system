#!/bin/bash

declare -A TOPICS=(
    ["user_actions"]="--partitions 3 --replication-factor 2"
    ["user_recommendations"]="--partitions 3 --replication-factor 2"
)

SCHEMA_REGISTRY_URL="http://schema-registry:8081"
SCHEMA_FILES_PATH="/scripts/schemas"

# wait for kafka to be ready
sleep 20

# create topics
for TOPIC in "${!TOPICS[@]}"; do
    kafka-topics --create --topic "$TOPIC" --bootstrap-server broker1:29092 ${TOPICS[$TOPIC]}
done

sleep 20

# configure schemas
for TOPIC in "${!TOPICS[@]}"; do
    SCHEMA_FILE="$SCHEMA_FILES_PATH/${TOPIC}.avsc"
    if [[ -f "$SCHEMA_FILE" ]]; then
        echo "Registrando esquema para el topic $TOPIC desde $SCHEMA_FILE..."
        curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
            --data "{\"schema\": \"$(cat "$SCHEMA_FILE" | sed 's/"/\\"/g' | tr -d '\n')\"}" \
            "$SCHEMA_REGISTRY_URL/subjects/${TOPIC}-value/versions"
    else
        echo "Esquema para $TOPIC no encontrado en $SCHEMA_FILE, omitiendo..."
    fi
done

sleep 20
