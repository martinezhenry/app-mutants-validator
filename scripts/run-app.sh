#!/usr/bin/env bash


DETACH=$([[ -n "$1" && "$1" == "0" ]] && echo "" || echo "-d")


## getting script dir
BASEDIR=$(dirname "$0")

## moving to BASEDIR or exit
cd "$BASEDIR" || exit

## moving to root app dir
cd ..

## Running with docker-compose
docker-compose up --remove-orphans --force-recreate --build "$DETACH" -p app-mutant