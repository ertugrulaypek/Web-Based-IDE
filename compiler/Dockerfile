FROM openjdk:8-jdk-alpine
RUN apk update && apk add build-base
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8081
CMD java -jar /app/app.jar