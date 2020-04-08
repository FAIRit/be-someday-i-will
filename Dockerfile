FROM openjdk:12-jdk-alpine
COPY *.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=eb","-jar","/app.jar"]
EXPOSE 5000

