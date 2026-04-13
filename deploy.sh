#!/bin/bash

if [ $# -eq 0 ]; then
  echo "Usage: $0 <environment>"
  echo "Supported environments: local"
  exit 1
fi

ENVIRONMENT=$1

if [ "$ENVIRONMENT" = "local" ]; then
  SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
  bash "$SCRIPT_DIR/build_and_deploy/local/iam_deploy.sh"
else
  echo "Error: Unknown environment '$ENVIRONMENT'"
  echo "Supported environments: local"
  exit 1
fi



