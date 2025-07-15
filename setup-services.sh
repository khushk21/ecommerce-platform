# #!/usr/bin/env bash
# SERVICES=(
#   auth-service
#   user-service
#   seller-service
#   product-service
#   search-service
#   recommendation-service
#   cart-service
#   order-service
#   notification-service
#   api-gateway
# )
# for svc in "${SERVICES[@]}"; do
#   mkdir -p services/$svc/src/{main/java/com/example/$svc,main/resources,test/java}
#   touch services/$svc/{Dockerfile,pom.xml,README.md}
# done
# echo "All service skeletons created under services/..."

#!/bin/bash

SERVICES=(
  auth-service
  user-service
  seller-service
  product-service
  search-service
  recommendation-service
  cart-service
  order-service
  notification-service
  api-gateway
)

GROUP_ID="com.example"
JAVA_VERSION="17"

cd "$(dirname "$0")/services" || exit 1

for SERVICE in "${SERVICES[@]}"; do
  if [ -d "$SERVICE" ]; then
    echo "Directory $SERVICE already exists, skipping."
    continue
  fi
  curl https://start.spring.io/starter.zip \
    -d dependencies=web,actuator \
    -d type=maven-project \
    -d language=java \
    -d groupId="$GROUP_ID" \
    -d artifactId="$SERVICE" \
    -d name="$SERVICE" \
    -d javaVersion="$JAVA_VERSION" \
    -o "$SERVICE.zip"
  unzip "$SERVICE.zip" -d "$SERVICE"
  rm "$SERVICE.zip"
done