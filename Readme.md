# Getting Started

### Introduction
This project uses spring boot, spring security with JWT token, Embedded H2 in-memory database for spring-based CRUD operation
on account, customer and trnasactions.  It support basic CRUD operations on account and customer and transaction, with limit 
that the delete is actually soft delete and transation does not allow removal.  So transaction only allow create and find.  A
reverse transaction record is used ot offset the prior transaction.

### Guides
The operation details are in the attached JSON file that can be imported into POSTMAN:  
AtosProject.postman_collection.json

The user creation does not require
authentication.  However, once the user is create, the next step is to retrieve the JWT token based on the username/password. 
All subsequent CRUD operations require JWT authentication. 

TransactionService handles the account balance increae/decrease in an ACID way.