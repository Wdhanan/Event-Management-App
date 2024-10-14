FROM openjdk:17-jdk

WORKDIR /app

COPY target/EventManagementSystem-1.0.0.jar /app/EventManagementSystem.jar

EXPOSE 8080

CMD ["java", "-jar", "EventManagementSystem.jar"]


