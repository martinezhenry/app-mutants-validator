#!/usr/bin/env bash

## getting script dir
BASEDIR=$(dirname "$0")

## moving to BASEDIR or exit
cd "$BASEDIR" || exit

## moving to root app dir
cd ..

## Running with docker-compose
docker-compose up --force-recreate --build  -d