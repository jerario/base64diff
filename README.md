#Base 64 Diff

A simple project that compares two different input that receive two
encoded in Base 64.

##Technology
* Gradle
* SpringBoot
* Mockito (for testing)
* JUnit (for testing)
* Lombok (for getters and setters generation)
* H2 Database (for saving data)

##How to use it

###Loading Data

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
 
 ###Comparing Data
 
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
 
##How to run it
### Build it
You can build it using gradle wrapper
    
    ./gradlew build
    
### Run it
You can run it with

    java -jar build/libs/diff-1.0-SNAPSHOT.jar 


