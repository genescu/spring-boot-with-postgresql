#!/usr/bin/env bash

#username: postgres
#password: docker
#localhost:5432
#database:postgres

curl -X POST --form "file=@{PATH}/port_visits.csv" http://localhost:8090/upload
