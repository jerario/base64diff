# Base 64 Diff
![technology Go](https://img.shields.io/badge/Technology-spring--boot-blue.svg)

A simple project that compares two different input that receive two
encoded in Base 64.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

In order to run the application you only need Java 1.8

### Running in Intellij

#### Install Lambok Plugin
*Lombok is java a library that allow you to never write another getter, setter or equals method again*

##### Enable Annotation Processing

Set it under: 
```
->Preferences (Cmd + ,)
-->Build, Execution, Deployment
--->Compiler
---->Annotation Processors
------>Enable annotation processing
```

And install Lombok plugin for IntelliJ:

```
->Preferences -> Plugins
-->Search for "Lombok Plugin"
--->Click Browse repositories...
---->Choose Lombok Plugin
----->Install
------>Restart IntelliJ
```

## How to use it

### How to run it
#### Build it
You can build it using gradle wrapper
    
    ./gradlew build
    
#### Run it
You can run it with

    java -jar build/libs/diff-1.0-SNAPSHOT.jar 

### Documentation
You can view the documentation in the following endpoint:

    <host>/doc

Note: if you are running in localhost the endpoint will be

    localhost:8080/doc

Documentation can be regenerated using aglio

    aglio -i src/main/java/com/joaera/diff/doc/docA.apib -o src/main/resources/templates/docs.html

### Loading Data

It provides two endpoint for uploading via POST both string:

    <host>/v1/diff/<ID>/left
    
And:

    <host>/v1/diff/<ID>/right

Both endpoints receive the same JSON structure:
     
     {"data":<any Base 64 content>}
 
 For example:
     
     curl -X POST -d '{"data":"This is an example"}' localhost:8989/v1/diff/1/right
     curl -X POST -d '{"data":"This is 4n 3xampl3"}' localhost:8989/v1/diff/1/left

Note: You can upload as many different data as you want providing different IDs.
 
 ### Comparing Data
 
 You can compare data using the following endpoint
 
     <host>/v1/diff/<ID>/
     
 This endpoint will compare left data and right data loading with the ID.
 
 Following the example, the response will be: 
 
    {
        "message": "Data is different",
        "difference": {
            "offsets": [
                8,
                11,
                17
            ],
            "length": 18
        }
    }
 
## Technology
* Gradle
* Spring-boot
* Mockito (for testing)
* JUnit (for testing)
* Lombok (for getters and setters generation)
* H2 Database (for saving data)
* Apiary (for documentation)

