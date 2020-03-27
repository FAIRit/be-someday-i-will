# **Someday I will**

## Overview
This app will allow users to search books, movies and tv series by entering keywords (title, author, etc.).
After creating new account, user will gain access to his own watch/read later lists.

## Live demo (deployed to AWS Elastic BeanStalk): [click here](http://somedayiwill-env.eba-3hdq2v4t.us-west-2.elasticbeanstalk.com/swagger-ui.html#/)

## User stories
 - [x] User can create an account
 - [x] User can upload his avatar
 - [ ] User can choose between movies or books on the front page
 - [ ] User can select his own watch later/ read later list or input query to find books/movies
 - [ ] User can click on movie/book to see details
 - [x] User can add movie/book to his watch later/read later list

## Used tools & technologies
* Spring Boot 2.2.4
* Java 11
* Maven
* JWT
* MySQL
* Flyway
* Swagger 2
* Docker (including Docker Compose)
* SendGrid
* Thymeleaf
* Lombok
* Mockito
* JUnit 5
* RestAssured
* Testcontainers
* GitHub Actions


## Setup:
To run project locally using Maven, Java and MySQL (remember to replace db url, password and username in application.properties with your own credentials):
```
$ mvn package
$ java --jar target/*jar
```

To run project locally using Docker and docker-compose:
```
$ docker-compose up
```

## Used APIs
* [Google Books API](https://developers.google.com/books)
* [The Movie Database API](https://developers.themoviedb.org/3/genres/get-tv-list)


### Status:
Work in progress. 
