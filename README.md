# mfp-2022
Microservices Fundamentals Mentoring Program 2022

## Module 1 - Microservice Architecture Overview

Run `$ docker-compose up` to create project containers

## Module 2 - Microservices Communication

## Module 3 - Testing

Execute following command to publish pacts from `resource.processor` to pact broker:
```
$ gradle pactPublish -p resource.processor
```
Uploaded pact could be found by URL `http://localhost:9292/`