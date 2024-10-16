#!/usr/bin/env bash

# . ./scripts/parse_env.sh

# docker_folder="./docker/"

# if [[ $1 = "main" ]]; then
#     file="$docker_folder/Dockerfile.prod"
# elif [[ $1 = "development" ]]; then
#     file="$docker_folder/Dockerfile.production"
# elif [[ $1 = "test" ]]; then
#     file="$docker_folder/Dockerfile.test"
# fi

# container="${CONTAINER_NAME}"
# if [[ $1 = "test" ]]; then
#     container="${container}-test"
# fi

# docker="docker build -f $file -t $container ."

# eval "${docker}"

docker build -t agave-api -f ./docker/Dockerfile.prod .
docker tag agave-api:latest polygontechxyz/agave-api:latest
docker push polygontechxyz/agave-api:latest
