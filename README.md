# IAM

Identity and Access Management reference implementation.

## Overview

This repository currently includes a Spring Boot-based identity service and local container deployment scripts.

- `identity` service is a Spring Boot 4 application.
- Local deployment uses Podman with a dedicated network: `iam_network`.
- A local NGINX proxy container forwards traffic to the identity service.

## Project Layout

- `identity/` - Java service (Gradle, Spring Boot)
- `build_and_deploy/local/` - local deployment scripts and proxy config
- `build_and_deploy/local/iam_deploy.sh` - builds app, ensures Podman network exists, builds images, runs containers
- `build_and_deploy/local/proxy/` - NGINX proxy Dockerfile and `nginx.conf`
- `deploy.sh` - environment-based entrypoint (`local` supported)

## Prerequisites

From the current repository setup:

- Linux shell environment (bash)
- Podman installed and available on `PATH`
- Java toolchain compatible with `identity/build.gradle` (Java 25)
- Network access to pull base images (for example `docker.io/library/eclipse-temurin:25`)

## Local Deployment

Run from repository root:

```bash
chmod +x ./deploy.sh
chmod +x ./build_and_deploy/local/iam_deploy.sh
./deploy.sh local
```

What `local` deployment does:

1. Builds the identity service using `./gradlew clean build`
2. Checks for Podman network `iam_network` and creates it if missing
3. Builds proxy and identity images
4. Starts containers:
   - `identity` (attached to `iam_network`)
   - `proxy` (port mapping `8080:80`, attached to `iam_network`)

## Run Identity Service Only (without proxy)

```bash
cd ./identity
./gradlew clean bootJar
podman build -t identity .
podman run --rm -p 8080:8080 --name identity identity:latest
```

## Troubleshooting

- Short image name resolution error in Podman:
  - Use fully-qualified image names in Dockerfiles (already set for identity service).
- "image is in use by a container":
  - Remove dependent containers before removing/rebuilding images.
- Deployment script path issues:
  - Run `./deploy.sh local` from repository root so relative paths resolve correctly.

## Next Steps

- Add additional IAM modules (authn, authz, policy, audit) under separate services/packages.
- Add health/readiness endpoints and container health checks.
- Add integration tests for proxy-to-identity routing and deployment scripts.
- Add environment profiles (`dev`, `staging`, `prod`) to `deploy.sh`.
