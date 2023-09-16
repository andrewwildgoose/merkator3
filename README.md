# merkator front end

This repo contains the back end of the merkator application.  This application provides a solution to enable the adventure enthusiasts to plan a trip which may last over several days or contain several routes within it.

## Running the back end application

To run this code after pulling it from the repo, you'll need to create a application.properties file in the /src/main/resources file and add values to the global variables.

```env
# This is what your application.properties file will need to look like 
#server
server.port=[replace with server port to expose the application on]

#mongodb
spring.data.mongodb.uri=[replace with mongodb connection URI]
spring.data.mongodb.database=[replace with database name]

#logging
logging.level.org.springframework.data=debug
logging.level.org.springframework.security=DEBUG

#large file upload
spring.servlet.multipart.max-file-size=256MB
spring.servlet.multipart.max-request-size=256MB
spring.servlet.multipart.enabled=true

#JWT Secret
merkator.api.secretKey=[replace with secret key for encoding JWT]

#Test JWT Token - expired token provided for testing
merkator.api.testJwt=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhLndpbGRnb29zZUB5YWhvby5jby51ayIsImlhdCI6MTY5MTc0ODQ4MywiZXhwIjoxNjkxNzQ5OTIzfQ.CfecQk0tOEuMettzLz4h0ZxBb6XMF3Z-osRptHuqFDs

#MapBox API Key
merkator.api.mapBoxKey=[replace with mapbox access token]
```

More details on these environment variables can be found in the appendices of the project report. 

An executable JAR file will also be uploaded directly with the project files.