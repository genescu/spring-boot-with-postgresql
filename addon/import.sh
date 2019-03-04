#!/usr/bin/env bash

#username: postgres
#password: docker
#localhost:5432
#database:postgres

#psql -h localhost -U postgres -d postgres -c "\copy  ship  (name,imo,length,port_id,time_started,time_finished) from $SOURCE_PATH delimiter ',' CSV HEADER"

curl -X POST --form "file=@/home/georgeenescu/sftp-creative/ships/ships-demo/src/main/resources/upload/port_visits.csv" http://localhost:8090/upload