# Resource Processor

Service is used to process MP3 source data.
This service doesn't have web interface and used for data processing.
Service implements MP3 parsing, able to extract MP3 metadata for storing of this data using songs metadata api.
Service listens to kafka topic, uploads song files, parsing its metadata and saves it on song service.

To build docker image run `gradle dockerBuild` command