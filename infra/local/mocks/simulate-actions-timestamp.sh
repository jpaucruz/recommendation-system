#!/bin/bash

generate_current_timestamp() {
  current_timestamp=$(date --utc +%Y-%m-%dT%H:%M:%SZ)
  echo $current_timestamp
}

generate_current_timestamp