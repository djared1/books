# Getting Started

Required Technologies: Java 11 / Maven 3 / Spring Boot 5

### To Run the Unit Tests
* In order to run the unit tests an application such as "Docker Desktop" must be running
* run: `mvn clean install`

### To Run the application locally
* Create a docker container: `docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres`
* Run the flyway command: `mvn flyway:migrate`
* Start the spring-boot app: `mvn spring-boot:run`

Swagger documentation is available at: http://localhost:5000
