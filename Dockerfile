FROM openjdk:11-jdk-slim
MAINTAINER Uladzimir

COPY target/user_service-1.0.0.jar user_service-1.0.0.jar
ENTRYPOINT ["java","-jar","/user_service-1.0.0.jar"]
