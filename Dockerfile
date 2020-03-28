FROM openjdk:12-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ARG APP_GOOGLE_BOOKS_KEY
ARG APP_MOVIE_DATABASE_KEY
ARG SPRING_SENDGRID_API_KEY
ENV APP_GOOGLE_BOOKS_KEY $APP_GOOGLE_BOOKS_KEY
ENV APP_MOVIE_DATABASE_KEY $APP_MOVIE_DATABASE_KEY
ENV SPRING_SENDGRID_API_KEY $SPRING_SENDGRID_API_KEY
ENTRYPOINT ["java","-jar","/app.jar"]
