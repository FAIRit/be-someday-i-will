# **Someday I will**

## Overview
This app will allow users to search books, movies and tv series by entering keywords (title, author, etc.).
After creating new account, user will gain access to his own watch/read later lists.

## Live demo (deployed to AWS Elastic BeanStalk): [click here](http://somedayapp-env.eba-dsmnvmrd.us-west-2.elasticbeanstalk.com/swagger-ui.html#/)

## User stories
 - [x] User can create/update/delete an account
 - [x] User can log in using JWT token
 - [x] User can upload/update/delete an avatar
 - [x] User can add movie/book to his watch later/read later list
 
 To do on the frontend side:
 - [ ] User can choose between movies or books on the front page
 - [ ] User can select his own watch later/ read later list or input query to find books/movies
 - [ ] User can click on movie/book to see details

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
* SpotBugs
* Mockito
* JUnit 5
* RestAssured
* Testcontainers
* GitHub Actions


## Setup:
To run this project you'll need docker and docker-compose to be installed on your computer. 

***IMPORTANT:*** To make this application work you have to provide valid Google API key, Sendgrid API key and MDBDatabase API key.

```
$ ./run.sh GOOGLE_BOOKS_KEY MOVIE_DATABASE_KEY SENDGRID_API_KEY
```

## Used APIs
* [Google Books API](https://developers.google.com/books)
* [The Movie Database API](https://developers.themoviedb.org/3/genres/get-tv-list)


### Status:
It works! 


### About me:
[Check out my portfolio!](http://somedayapp-env.eba-dsmnvmrd.us-west-2.elasticbeanstalk.com/)
