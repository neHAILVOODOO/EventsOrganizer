FROM gradle:7.5.1-jdk11 AS build
COPY . /app
WORKDIR /app
RUN gradle clean build -x test

FROM openjdk:11
COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar EventsOrganizer.jar
ENTRYPOINT ["java", "-jar", "EventsOrganizer.jar"]
EXPOSE 8080

