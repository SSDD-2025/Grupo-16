# Exit on any error
$ErrorActionPreference = "Stop"

# Build Docker image
Write-Host "Building Docker Image..."
docker build -f "docker/Dockerfile" -t "fonssi29/liveticket:1.0.0" "./entrega1"
Write-Host 'Image "fonssi29/liveticket:1.0.0" built successfully'