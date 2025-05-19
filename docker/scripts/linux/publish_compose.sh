#!/bin/bash
# Abort script execution on error
set -e

# Variables
USERNAME="liveticket"
TAG_VERSION="1.0.0"
COMPOSE_FILE="docker-compose.local.yml" # This script should be executed in the directory where the COMPOSE_FILE is
APP_NAME="liveticket"

#Publication format
PUBLICATION=$USERNAME/$APP_NAME-compose:$TAG_VERSION

# Login the user
docker login -u "$USERNAME"

# Inform the user of the process
echo "⚙️ Publishing the Docker Compose file"

# Publish the Docker Compose
docker compose -f $COMPOSE_FILE publish $PUBLICATION

# Notify via bash
echo "✅ Compose published correctly in docker.io/$PUBLICATION"