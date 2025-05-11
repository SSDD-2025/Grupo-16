#!/bin/bash

# Exit when an error is detected.
set -e

DOCKER_USER="fonssi29"
IMAGE_NAME="liveticket"
IMAGE_TAG="1.0.0"

# Image Name.
FULL_IMAGE_NAME="$DOCKER_USER/$IMAGE_NAME:$IMAGE_TAG"

docker login

docker tag "$IMAGE_NAME:$IMAGE_TAG" "$FULL_IMAGE_NAME"

echo "Pushing image to Docker Hub..."
docker push "$FULL_IMAGE_NAME"
echo "Image successfully pushed: $FULL_IMAGE_NAME"