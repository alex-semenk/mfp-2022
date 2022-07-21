FROM openjdk:17-jdk-alpine3.14
WORKDIR /usr/app/
COPY build/libs/song.service.jar app.jar
EXPOSE 8082
ENTRYPOINT [ "java", "-jar", "/usr/app/app.jar" ]