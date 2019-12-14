# Trkr-server

OSX Requirements
- brew install coreutils

## To build

Run "./gradlew" which is the same as running "./gradlew clean swaggerClean generateSwaggerCode generateSwaggerUI build"
This will produce the jar "build/libs/trkr-server-<version>.jar"

## To run the jar.
The jar expects the following environment variables to be set to be able to run.
 * DB_TYPE - Which database the server will connect to. Options are "Neo4j", and "OrientDB"
 * DB_URI - The protocol and endpoint used to connect to the database.

From the main directory simple run "docker-compose -f ./compose/compose-neo4j.yml up" after the gradle build steps