#!/usr/bin/env bash
docker run -d --name mongo \
    -e MONGO_INITDB_ROOT_USERNAME=root \
    -e MONGO_INITDB_ROOT_PASSWORD=root \
    -p 27017:27017 \
    mongo