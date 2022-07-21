# mfp-2022
Microservices Fundamentals Mentoring Program 2022

## Module 1 - Microservice Architecture Overview

Run `$ docker-compose up` to create project containers

## Module 2 - Microservices Communication

## Module 3 - Testing

Execute following command to publish pacts from `resource.processor` to pact broker:
```
gradle pactPublish -p resource.processor
```
Uploaded pact could be found by URL `http://localhost:9292/`

## Module 4 - Containerization

Run following commands to build docker images for microservices:
```
gradle dockerBuild -p resource.processor
```
```
gradle dockerBuild -p resource.service
```
```
gradle dockerBuild -p song.service
```

To run standalone microservice container use command like this:
```
docker run -e POSTGRES_HOST=host.docker.internal -p 8082:8082 --name song-service mfp2022/song.service
```