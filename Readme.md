# Getting Started

### Introduction
This project uses spring boot, spring security with JWT token, Embedded H2 in-memory database for spring-based CRUD operation
on account, customer and transactions.  It support basic CRUD operations on account and customer and transaction, with limit 
that the delete is actually soft delete and an existing transaction does not allow removal.  So transaction only allow create and find.  A
reverse transaction record is used to offset the prior transaction.

### Guides
The operation details are in the attached JSON file that can be imported into POSTMAN:  
AtosProject.postman_collection.json

The user creation does not require
authentication.  However, once the user is create, the next step is to retrieve the JWT token based on the username/password. 
All subsequent CRUD operations require JWT authentication. 

TransactionService handles the account balance increae/decrease in an ACID way.

The Embedded H2 database can be accessed at URL: http://localhost:8080/h2-console/
username:      sa
password:      password
JDBC URL: 	   jdbc:h2:mem:testdb
Setting Name:  Generic H2 (Embedded)

## Swagger-UI
The Swagger-UI URL is http://localhost:8080/swagger-ui.html

## Command Line Usage
If you want to run the applicaiton in commmand line, please have Java8+ and maven installed.  Then from the project directory, run:
mvn package
java -jar target/AtosSpringBoot-0.0.1-SNAPSHOT.jar