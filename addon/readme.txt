http://localhost:8090/ships/1
http://localhost:8090/upload

mvn clean package -U install

mvn com.coveo:fmt-maven-plugin:format

time_started = time arrived

time_finished = time left


Once you are done, please send us instructions:

- git repository where we can clone it. If you make it a public repo, please don't include the csv file as it contains real data

- Build instructions

- Configuration instructions

- Run instructions (how to start and at least one curl command for each API endpoint)


What we will do:

- We will try to run it and run some test queries

- We will import another dataset with more than 1 port-id and run some more tests

- When every thing works, we will review the code, otherwise we will ask you to fix or to update the instructions


Please also write at least 1 unit test and 1 integration test.


Now here is the project:


The idea is to build a simple API to return some aggregations about port visits.

Attached you will find a file with 3 years of port visits of large container ships (> 300m length) for the port of Hamburg (port_id = 2)

The ships are identified by their current name and the IMO number, which is a kind of serial number for a ship.

Columns:

- name (Name of the ship)
- imo (IMO number, kind of a serial number, can be used as ship-ID)
- length (ship length in meter)
- port_id (only 2 = Hamburg in the data, but code should work for any id)
- time_started (Time zone Europe/Berlin)
- time_finished (Time zone Europe/Berlin)

Tasks:

1) Import the file in a SQL database (Postgresql preferred)

2) Build an API (using Java) with the endpoints listed below

- Try to use SQL or JPQL wherever possible
- Use native SQL for at least one method

- Result should be JSON

If you want, you can also write a simple frontend to request and vizualize the results, but this is optional.



A) List vessels that have been in a port at a given time. Example: Which vessels have been in the port on 1.1.2016 at 12:00.

/port/{id}/{dateTime}
http://localhost:8090/port/2/04.01.2016%2020:55:23
http://localhost:8090/port/2/1.1.2016%2012:00

Input:
=> port_id
=> Timestamp

Returns: List of vessels that have been in the port at this timestamp.
=> Name
=> IMO
=> length
=> time_started

/port/{id}/{startTime}/{endTime}
http://localhost:8090/port/2/1.1.2016%2012:00/04.01.2017%2020:55:23


B) Time-period summary / aggregation

- Input:
=> Port-id
=> Start time
=> End time

Output:
=> Number of unique vessels (IMO number) during this time range
=> Average time in the port ::days,hours ::52.239155 seconds 1 minutes 17 hours 1 days
=> Minimum time in the port (include the IMO of the ship with minimum time)
=> Maximum time in the port (include the IMO of the ship with the maximum time)


C)  Summary by vessel:
- Input:
=> IMO number
=> Port-id
=> Start time
=> end time

- Output:
=> Number of port visits of this vessel in that time range
=> Average time in the port
=> Minimum time in the port
=> Maximum tine in the port
=> Earliest visit (start time) in this time range
=> Latest visit (start time) in this time range

D) Monthly summary
- Input
=> Port-id
=> Year
=> Month

- Output:
=> Number of total arrivals for that port
=> Number of unique vessels for arrivals
=> Avg duration of port visit
=> Sum of length of the ships of all port visits (how many meters arrived at this month)