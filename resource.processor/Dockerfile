FROM openjdk:17-jdk-alpine3.14
WORKDIR /usr/app/
COPY build/libs/resource.processor.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/usr/app/app.jar" ]