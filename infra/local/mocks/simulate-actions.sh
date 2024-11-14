#!/bin/bash

userIds=("user1" "user2" "user3" "user4" "user5")
#userIds=("user1")
productIds=("product1" "product2" "product3" "product4" "product5" "product6" "product7" "product8" "product9" "product10")
#productIds=("product1" "product2")
actions=("view" "add-to-cart" "purchase")

# script start time
start_time=$(date +%s)

# script total duration
total_duration=$((20 * 60))

generate_current_timestamp() {
  current_timestamp=$(date --utc +%Y-%m-%dT%H:%M:%SZ)
  echo $current_timestamp
}

while true
do
  # calculate elapsed time since start
  elapsed_time=$(($(date +%s) - start_time))
  # exit loop if script duration has terminated
  if [ "$elapsed_time" -ge "$total_duration" ]; then
    echo "Fin del periodo de 15 minutos. Se detiene la generación de eventos."
    break
  fi
  # select random values for each attribute
  userId=${userIds[$RANDOM % ${#userIds[@]}]}
  productId=${productIds[$RANDOM % ${#productIds[@]}]}
  action=${actions[$RANDOM % ${#actions[@]}]}
  # get current timestamp
  timestamp=$(generate_current_timestamp)
  # perform curl
  #curl --location 'http://localhost:8080/users/action' \
  #  --header 'Content-Type: application/json' \
  #  --data "{
  #    \"userId\": \"$userId\",
  #    \"productId\": \"$productId\",
  #    \"timestamp\": \"$timestamp\",
  #    \"action\": \"$action\"
  #  }"
  # perform curl
  curl --location "http://localhost:8080/user/$userId/actions" \
    --header 'Content-Type: application/json' \
    --data "{
      \"productId\": \"$productId\",
      \"timestamp\": \"$timestamp\",
      \"action\": \"$action\"
    }"
  sleep 10
done
