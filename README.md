# **Someday I will**

## Overview
This app will allow users to search books, movies and tv series by entering keywords (title, author, etc.).
After creating new account, user will gain acces to his own watch/read later lists.

## User stories
 - [ ] User can create an account
 - [ ] User can upload his avatar (nice to have feature)
 - [ ] User can choose between movies or books on the front page
 - [ ] User can select his own watch later/ read later list or input query to find books/movies
 - [ ] User can click on movie/book to see details
 - [ ] User can add movie/book to his watch later/read later list

## Used tools & technologies
* Spring Boot 2.2.4
* Java 11
* MySQL
* Flyway
* Swagger
* Docker
* SendGrid
* Thymeleaf


## Setup:
To run project locally using Maven, Java and MySQL (remember to replace db url, password and username in application.properties with your own credentials):
```
$ mvn clean install -DskipTests
$ java --jar target/*jar
```
(Coming soon)
To run project locally using Docker and docker-compose:
```
$ docker build …..
$ docker-compose up
```

## Used APIs
* [Google Books API](https://developers.google.com/books)
* [The Movie Database API](https://developers.themoviedb.org/3/genres/get-tv-list)


### Status:
Work in progress. 
