FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8000
CMD java -jar /app/app.jar