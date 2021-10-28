FROM adoptopenjdk/openjdk11:alpine
MAINTAINER "Igor Silva"
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]