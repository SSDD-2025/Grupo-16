#!/bin/bash

# Exit when an error is detected.
set -e

IMAGE_NAME="liveticket"
IMAGE_TAG="1.0.0"

# Image building using the Dockerfile.
docker build -f docker/Dockerfile -t fonssi29/liveticket:1.0.0 ./entrega1 