#!/usr/bin/env bash

## getting script dir
BASEDIR=$(dirname "$0")

## moving to BASEDIR or exit
cd "$BASEDIR" || exit

docker pull "$IMAGE"

docker-compose -p mutant -f ./deploy/docker-compose.yml up --remove-orphans --force-recreate --build -d


