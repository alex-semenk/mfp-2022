# Resource Service

Resource service provides CRUD REST API for files.
This service is used for data storing.
Service is using S3 storage emulator from localstack.
Service tracks resources (with resource location) in underlying database.

To build docker image run `gradle dockerBuild` command