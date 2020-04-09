#!/bin/bash

if [ $# -gt 3 ]; then
  echo You passed more than 3 parameters
  exit
fi
if [ $# -lt 3 ]; then
  echo You passed less than 3 parameters
  exit
fi
GOOGLE_BOOKS_KEY=$1
MOVIE_DATABASE_KEY=$2
SENDGRID_API_KEY=$3

which docker docker-compose >/dev/null
exit_status=$?

if [ $exit_status -ne 0 ]; then
  echo Please install docker and docker-compose before running
  exit
fi

./mvnw clean package -Dspring.profiles.active=gh
mkdir -p target/dependency
cd target/dependency || exit
jar -xf ../*.jar
docker-compose build --build-arg APP_GOOGLE_BOOKS_KEY="$GOOGLE_BOOKS_KEY" --build-arg APP_MOVIE_DATABASE_KEY="$MOVIE_DATABASE_KEY" --build-arg SPRING_SENDGRID_API_KEY="$SENDGRID_API_KEY"
docker-compose up
