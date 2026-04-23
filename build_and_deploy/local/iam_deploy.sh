CURRENT_DIR=$(pwd)
DB_PASSWORD="1054c64d63cc11079d00"
DB_USER="root"
DB_NAME="iam_db"
IDENTITY_PROJECT_PATH="${CURRENT_DIR}/identity"
PROXY="${CURRENT_DIR}/build_and_deploy/local/proxy"
DATABASE="${CURRENT_DIR}/build_and_deploy/local/database"
BOOTSTRAP="${CURRENT_DIR}/bootstrap"


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
podman build -f "${BOOTSTRAP}/Dockerfile" -t bootstrap

podman run --network iam_network --name database --detach -v ${HOME}/Documents/p_volume:/var/lib/postgresql/data -e POSTGRES_DB=${DB_NAME} -e POSTGRES_PASSWORD=${DB_PASSWORD} -e POSTGRES_USER=${DB_USER} database:latest
# Wait for the database to be ready
for i in $(seq 1 30); do
  if podman exec database pg_isready -U "${DB_USER}" -d "${DB_NAME}" >/dev/null 2>&1; then
    echo "Database is ready!"
    break
  fi
  sleep 1
done
echo ${DB_PASSWORD}
podman run --network iam_network --name bootstrap -e DB_PASSWORD=${DB_PASSWORD} -e DB_HOST=database -e DB_USER=${DB_USER} -e DB_NAME=${DB_NAME} bootstrap:latest
podman run --network iam_network --name identity --detach identity:latest
podman run -p 8080:80 --network iam_network --name proxy --detach proxy:latest
