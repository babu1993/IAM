CURRENT_DIR=$(pwd)
DB_PASSWORD=$(openssl rand -hex 10)
IDENTITY_PROJECT_PATH="${CURRENT_DIR}/identity"
PROXY="${CURRENT_DIR}/build_and_deploy/local/proxy"
DATABASE="${CURRENT_DIR}/build_and_deploy/local/database"

echo "Building Identity..."

cd "$IDENTITY_PROJECT_PATH"
./gradlew clean build

echo "Building completed..."

cd "$CURRENT_DIR"

echo "Container Building..."

NETWORK_NAME="iam_network"
if ! podman network exists "$NETWORK_NAME"; then
  echo "Creating Podman network: $NETWORK_NAME"
  podman network create "$NETWORK_NAME"
else
  echo "Podman network already exists: $NETWORK_NAME"
fi

podman build -f "${PROXY}/Dockerfile" -t proxy
podman build -f "${IDENTITY_PROJECT_PATH}/Dockerfile" -t identity
podman build -f "${DATABASE}/Dockerfile" -t database .

podman run --network iam_network --name database --detach -v ${HOME}/Documents/p_volume:/var/lib/postgresql/data -e POSTGRES_DB=iam_db -e POSTGRES_PASSWORD=${DB_PASSWORD} database:latest
podman run --network iam_network --name identity --detach identity:latest
podman run -p 8080:80 --network iam_network --name proxy --detach proxy:latest
