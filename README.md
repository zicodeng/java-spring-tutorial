# Java Spring Tutorial

Source code is largely based on this LinkedIn tutorial: [Learning Spring with Spring Boot](https://www.linkedin.com/learning/learning-spring-with-spring-boot-13886371)

## Server

Server is ready at http://localhost:8080/

## Web Pages

Room reservations page: http://localhost:8080/reservations or http://localhost:8080/reservations?date=2022-01-01

Guests page: http://localhost:8080/guests

## RESTful Endpoints

Get room reservations: `GET /api/reservations` or `GET /api/reservations?date=2022-01-01`

Get guests: `GET /api/guests`

Add a new guest: `POST /api/guests`, e.g. `curl -X POST http://localhost:8080/api/guests -H 'Content-Type: application/json' -d '{"lastName": "Obama", "firstName": "Barack"}'`
