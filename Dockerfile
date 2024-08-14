#FROM amazoncorretto:17
#FROM gcr.io/distroless/java17
#FROM openjdk:17-jre-slim
FROM amazoncorretto:8u422-alpine3.17-jre

ADD target/strava-weekly-0.0.1.jar /home/server.jar

ENV PORT 8080
EXPOSE 8080

CMD java -XX:MaxRAM=256m -jar /home/server.jar
