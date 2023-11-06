FROM amazoncorretto:17-alpine-jdk
COPY ./target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT java -jar /app.jar