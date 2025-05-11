# Exit when an error is detected
$ErrorActionPreference = "Stop"

# Variables
$DOCKER_USER = "fonssi29"
$IMAGE_NAME = "liveticket"
$IMAGE_TAG = "1.0.0"

# Image Name
$FULL_IMAGE_NAME = "${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"

# Login to Docker
docker login

# Push the image to Docker Hub
Write-Host "Pushing image to Docker Hub..."
docker push "$FULL_IMAGE_NAME"
Write-Host "Image successfully pushed: $FULL_IMAGE_NAME"