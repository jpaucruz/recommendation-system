#!/bin/bash

BASE_DIR=$(dirname "$(realpath "$BASH_SOURCE")")

docker compose -f $BASE_DIR/infra/local/docker-compose.yml down -v
