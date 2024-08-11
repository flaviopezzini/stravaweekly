FROM amazoncorretto:17

ADD target/strava-weekly-0.0.1.jar /home/server.jar

ENV PORT 8080
EXPOSE 8080

CMD java -XX:MaxRAM=128m -jar /home/server.jar
