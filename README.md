Holiday Information Service
===========================
Rest web service that provides information about holiday that occure for both countries after provided date.

This project requires Java 8 and Maven 3.x installed.

It uses Spring Boot as a framework.

Build project
--------------
This maven command builds and creates executable .jar file.
It is a spring-boot application and it does not require any additional files to run.

    mvn clean package

Run Unit Tests
---------------
To run unit test provided with project please execute below command.

    mvn clean test

Run Integration Tests
---------------------
To run integration tests provided with project please execute below command.

    mvn clean verify

Run Service
-----------
To run service please execute below command.

    mvn clean spring-boot:run

Access to web service
---------------------
When service is running it is available under address:

    http://localhost:8080/nextholiday

Example of usage:

    http://localhost:8080/nextholiday?firstCountry=US&secondCountry=PL&holidayDate=2015-04-01

Returns information about next holiday that will be in both countries.
    
    {
         date: “2016-03-25”,
         name1: ”Niedziela Wielkanocna”,
         name2: “Langfredag”
    }

Configuration
--------------
Project contains two configuration files:
- *holidayClient.properties* - stores two properties: 
  1) address of holiday information service (default value: https://holidayapi.com/v1/holidays)
  2) api Key to this service (dafault debug key: 8e614178-0d6f-452d-96f2-bc9c2e25fdd2).
To use real data, please provide proper key.
- *jssecacerts* - trust store file used for SSL communication with holidayapi.com web service

API documentation
------------------
When service is running there is available a Swagger documentation under address:
    
    http://localhost:8080/v2/api-docs
    
