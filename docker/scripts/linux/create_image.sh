#!/bin/bash

# Exit when an error is detected.
set -e

# Image building using the Dockerfile.
echo "Building Docker Image..."
docker build -f docker/Dockerfile -t fonssi29/liveticket:1.0.0 ./entrega1
echo "Image "fonssi29/liveticket:1.0.0" built succesfully"