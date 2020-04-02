## Project description

### Architecture
I have decided to separate the repository layer, and I've kept the domain 
and web layer together, as changing the database is fairly common. I've 
followed the [Hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
creating the necessary interfaces in the domain layer (e.g. `UserRepository`),
and providing concrete implementations in the outer layers (e.g. `UserH2Repository`).
That way the domain layer is abstracted away from the database, and one can
easily change the persistence mechanisms, as long as they comply with the API 
of the `UserRepository` interface. 

Another example is the `NotificationService` interface, which methods are 
called from the `UserService` in the domain layer. I've provided a dummy Kafka
streams implementation to show how one can override how the service sends
notifications.  

In the repository layer I have created a `UserEntity` class, which represents
a 1:1 copy of the `User` class in the domain layer, but with the needed JPA
specific annotations and validations. The class provides two adapter methods for 
converting the entity to and from the domain user object.

### API tests
In the `UsersApiTests` file I have added end to end API tests, which run in a 
test Spring container with an H2 database. With the help of Mock MVC, I make
real HTTP requests to the REST controller, persisting the user entities into the
database. Apart form testing the entire flow of the service, these tests serve
the purpose of contract regression tests, preventing the introduction of any
backward-incompatible changes to the API.

_Note_: The API tests run very slowly (around 3 seconds each) because the
`ApplicationContext` is refreshed before each test.

### Javadoc
I've added necessary documentation to the classes and methods I deemed more
complex.


## Interactive API documentation
With the help of [Swagger](https://swagger.io/), an interactive API documentation
is available at `<host>:<port>/swagger-ui.html#/user-controller`. You can view
all available endpoints, what they do, their return types and parameters.

By clicking the _Try it out_ button in the upper right-hand corner of each endpoint
you can construct your own request and execute it again the server. You can then view
the actual response below. 


## How to run

#### In a local environment
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
