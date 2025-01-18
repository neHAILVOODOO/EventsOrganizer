FROM openjdk:11
COPY build/libs/demo-0.0.1-SNAPSHOT.jar EventsOrganizer.jar
ENTRYPOINT ["java", "-jar", "EventsOrganizer.jar"]
EXPOSE 8080
