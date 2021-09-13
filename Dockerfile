FROM openjdk:8
ADD target/notes-application.jar notes-application.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "notes-application.jar"]
