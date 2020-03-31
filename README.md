## Project description


## Interactive API documentation
With the help of [Swagger](https://swagger.io/), an interactive API documentation
is available at `<host>:<port>/swagger-ui.html#/user-controller`. You can view
all available endpoints, what they do, their return types and parameters.

By clicking the _Try it out_ button in the upper right-hand corner of each endpoint
you can construct your own request and execute it again the server. You can then view
the actual response below. 

## How to run

#### From terminal

1. Have JDK 11 installed and $JAVA_HOME set accordingly
2. Go to the root of the project
3. Run `./mvnw clean install` (takes about 45-60 seconds 
because of API tests). Run with `-DskipTests` to skip tests.
4. Run `java -jar target/users-service-0.0.1-SNAPSHOT.jar`
5. Go to `http://localhost:8080/actuator/health` to verify
that the service is up and running.


#### With Docker

1. Start Docker daemon
2. Run `docker build -t user-service .` as root
3. Run `docker run -p 8080:8080 user-service` as root
4. Go to `http://localhost:8080/actuator/health` to verify
that the service is up and running.


## Run manual requests

#### Postman
In the root of the project I have included a sample Postman
request collection in the `Faceit Coding Task.postman_collection.json`
file. You can import it in Postman and run the sample requests.

#### Swagger UI
As mentioned above, you can go to `<host>:<port>/swagger-ui.html#/user-controller`
and run HTTP requests from there. It also provides API documentation.
