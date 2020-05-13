# Widget API

This is a web service (HTTP REST API) to work with widgets and includes basic CRUD operations.

* Create widget rule: 
    1. If a z-index is not specified, the widget moves to the foreground. 
    2. If the existing z-index is specified, then the new widget shifts all widgets with the same and greater index upwards.

* Update widget rule: 
    1. You cannot change widget id.
    2. You cannot delete widget attributes.
    3. All changes to widgets must occur atomically. That is, if we change the XY coordinates of the widget, then we should not get an intermediate state during concurrent reading.

* After running application you can click below link to see API related documentation:

    1. http://localhost:8080/swagger-ui.html#

* Additionally this api:
    1. Supports filtering. if you want to apply a `area` filter while getting a list of widgets you should specify the url like below: 
        1. `/widgets?lowerLeft=0:0&upperRight=100:150`
    
    2. Supports pagination and self link. Default page size is `10`. Maximum page size is `500`. (You can change this configuration from application.yml file) 
    3. Can work with two different storage type. In memory storage and database. It is specified as a config within the application.yml file. 
    Also you can change this config from `docker-compose.yml` file without touching application.yml
        1. For memory: `storage.type: memory`
        2. For database: `storage.type: database`

Technologies and libraries are basically like below:

1. `Embeded H2` for database
2. `Spring data and JPA` for persistence and crud support
3. `Spring Hateoas` for pagination and link support to response dto
4. `Spring Web` for rest api
5. `Swagger` for API documentation
6. `Junit 5` for tests
7. `Lombok` for data and logging support to get rid of boilerplate code (like getters and setters)
8. `Docker` for running app in container
9. `Jacoco` for test coverage


How to run locally with docker compose : 
1. `./start-app.sh` --> running tests, building application jar, and start docker-compose
2. `./stop-app.sh` --> stop docker container

How to run locally with mvn:
1. `mvn clean install` --> run tests and build application artifact
2. `mvn spring-boot:run` --> run application

How to run locally with docker : 
1. `mvn clean install` --> run tests and build application artifact
2. `docker build . -t widget-image` --> build docker image
3. `docker run -p 8080:8080  widgwet-image` --> run docker image

