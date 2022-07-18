FROM openjdk:17-jdk-alpine3.14
WORKDIR /usr/app/
COPY build/libs/resource.service.jar app.jar
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "/usr/app/app.jar" ]