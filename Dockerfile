#FROM amazoncorretto:17
FROM eclipse-temurin:17-jre-alpine

ADD target/strava-weekly-0.0.1.jar /home/server.jar

ENV PORT 8080
EXPOSE 8080

CMD java -XX:MaxRAM=256m -jar /home/server.jar
